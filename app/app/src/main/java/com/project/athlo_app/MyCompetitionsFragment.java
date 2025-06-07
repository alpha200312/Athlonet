package com.project.athlo_app;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.project.athlo_app.Adapter.MyCompetitionsAdapter;
import com.project.athlo_app.DataModels.event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyCompetitionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyCompetitionsFragment extends Fragment {



    RecyclerView recyclerView;
    private List<event> eventList;
    private MyCompetitionsAdapter myCompetitionsAdapter;


    public MyCompetitionsFragment() {

    }


    public static MyCompetitionsFragment newInstance(String param1, String param2) {
        MyCompetitionsFragment fragment = new MyCompetitionsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_my_competitions, container, false);
        SharedPreferences athlo_pref = getActivity().getSharedPreferences("athlo_user", Activity.MODE_PRIVATE);
        String token = athlo_pref.getString("token", "");
        recyclerView=view.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // Initialize event list
        eventList = new ArrayList<>();






        String url="https://athlonet-api.vercel.app/api/athlonet/event/mycompetitions";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null, response -> {

            try {


                if(response.getBoolean("success")){
                    JSONArray jsonArray=response.getJSONArray("competitions");
                    for(int i=0;i< jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);


                        // Extract organizer info safely
                        JSONObject organizerObject = jsonObject.getJSONObject("organizer");

                        Date startDate = parseDate(jsonObject.getString("startDate"));
                        Date endDate = parseDate(jsonObject.getString("endDate"));
                        Date createdAt = parseDate(jsonObject.getString("createdAt"));
                        String organizerId = organizerObject.getString("_id");
                        String organizerName = organizerObject.getString("name");
                        event eventItem = new event(
                                jsonObject.getString("_id"),
                                jsonObject.getString("name"),
                                jsonObject.getString("description"),
                                jsonObject.getString("sport"),
                                organizerId,
                                jsonObject.getString("location"),
                                jsonObject.getBoolean("isPrivate"),
                                jsonObject.getBoolean("notifications"),
                                jsonObject.getString("status"),
                                startDate,
                                endDate,createdAt

                        );

                        eventList.add(eventItem);

                    }
                    myCompetitionsAdapter = new MyCompetitionsAdapter(eventList);
                    recyclerView.setAdapter(myCompetitionsAdapter);


                }

            }catch (JSONException e){
                e.printStackTrace();

            }
        },error -> {
            Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_SHORT).show();

        }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", token);
                return headers;
            }
        };
        RetryPolicy retryPolicy=new DefaultRetryPolicy(3000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(retryPolicy);
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);


        return view;
    }

    private Date parseDate(String dateString) {

        try {
            // Use SimpleDateFormat for older Android versions
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            sdf.setLenient(false);
            return sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Handle the error appropriately
        }
    }
}