package com.project.athlo_app;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
import com.project.athlo_app.Adapter.EventAdapter;
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
 * Use the {@link FinishedTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FinishedTaskFragment extends Fragment {



    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private List<event> eventList;
      static   TextView title;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FinishedTaskFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FinishedTaskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FinishedTaskFragment newInstance(String param1, String param2) {
        FinishedTaskFragment fragment = new FinishedTaskFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_finished_task, container, false);
        SharedPreferences athlo_pref = getActivity().getSharedPreferences("athlo_user", Activity.MODE_PRIVATE);
        String token = athlo_pref.getString("token", "");
        recyclerView=view.findViewById(R.id.recycler_view_events);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // Initialize event list
        eventList = new ArrayList<>();
        eventList=new ArrayList<>();

        String id =getArguments().getString("id");



        String url="https://athlonet-api.vercel.app/api/athlonet/event/organizer/" + id;
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
                    eventAdapter = new EventAdapter(eventList);
                    recyclerView.setAdapter(eventAdapter);


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


        // Sample data
//        eventList.add(new event("1", "Athlonet Championship", "Annual sports event", "Football", "Organizer123",
//                "Mumbai", false, true, "Upcoming",
//                new Date(), new Date(), new Date()));
//
//        eventList.add(new event("2", "City Marathon", "A running competition", "Marathon", "Organizer456",
//                "Delhi", true, false, "Completed",
//                new Date(), new Date(), new Date()));

        // Set up adapter


        Toast.makeText(getContext(), getArguments().getString("id"), Toast.LENGTH_SHORT).show();
        title= view.findViewById(R.id.club_name);
        title.setText(getArguments().getString("title"));



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