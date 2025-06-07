package com.project.athlo_app;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.project.athlo_app.Adapter.OrganizationAdapter;
import com.project.athlo_app.DataModels.event;
import com.project.athlo_app.DataModels.organization;
import com.project.athlo_app.UtilsService.SharedPreferenceclass;
import com.project.athlo_app.interfaces.Recycler_view_listener_home;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeFragment extends Fragment implements Recycler_view_listener_home {

   RecyclerView recyclerView;
   List<event> eventList;

   SharedPreferenceclass sharedPreferenceclass;
   ProgressBar progressBar;

   ArrayList <organization>arrayList;
   OrganizationAdapter organizationAdapter;









    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
       recyclerView=view.findViewById(R.id.recycle_view);
       progressBar=view.findViewById(R.id.progress_bar);
       recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
       recyclerView.setHasFixedSize(true);





       getOrganization();



        return view;

    }

    private void getOrganization() {
        arrayList=new ArrayList<>();
        progressBar.setVisibility(View.VISIBLE);
        String url="https://athlonet-api.vercel.app/api/athlonet/organization/all";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,response -> {

            try {

                if(response.getBoolean("success")){
                    Toast.makeText(getActivity(),""+response,Toast.LENGTH_SHORT).show();

                    JSONArray jsonArray=response.getJSONArray("organizations");
                    for(int i=0;i< jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        organization organization=new organization(jsonObject.getString("_id"),
                                jsonObject.getString("name"),
                                jsonObject.getString("address"));

                        arrayList.add(organization);


                    }

                    organizationAdapter=new OrganizationAdapter(getActivity(),arrayList,this);
                    recyclerView.setAdapter(organizationAdapter);




                    progressBar.setVisibility(View.GONE);



                }

            }catch (JSONException e){
                e.printStackTrace();

            }
        },error -> {}
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                //headers.put("Authorization", token);
                return headers;
            }
        };
        RetryPolicy retryPolicy=new DefaultRetryPolicy(3000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(retryPolicy);
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);





    }

    private void showAlertDialog() {

        Toast.makeText(getActivity(),"inside the floasting butoonm",Toast.LENGTH_SHORT).show();

        LayoutInflater inflater=getLayoutInflater();
        View alertLayout=inflater.inflate(R.layout.custom_dialog_layout,null);

        final EditText title_field = alertLayout.findViewById(R.id.title);
        final EditText description_field = alertLayout.findViewById(R.id.description);

        final AlertDialog dialog =new AlertDialog.Builder(getActivity())
                .setView(alertLayout)
                .setTitle("Add Task")
                .setPositiveButton("Add",null)
                .setNegativeButton("cancel",null)
                .create();

        dialog.setOnShowListener(dialogInterface -> {
            Button button=((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);

            button.setOnClickListener(view -> Toast.makeText(getActivity(),"posaitive button",Toast.LENGTH_SHORT).show());
        });



        dialog.show();

    }

    @Override
    public void get_events(int position) {
        FinishedTaskFragment finishedTaskFragment=new FinishedTaskFragment();
        Bundle bundle=new Bundle();
        bundle.putString("id",arrayList.get(position).getId());


        bundle.putString("title",arrayList.get(position).getOrganization_name());
        finishedTaskFragment.setArguments(bundle);




        FragmentManager manager= getActivity().getSupportFragmentManager();
        FragmentTransaction ft=manager.beginTransaction();
        ft.replace(R.id.content,finishedTaskFragment);
        ft.commit();





    }
}