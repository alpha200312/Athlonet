package com.project.athlo_app;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.chip.Chip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profile extends Fragment {

    TextView username_TextView;
    TextView email_TextView;




    public profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profile.
     */
    // TODO: Rename and change types and number of parameters
    public static profile newInstance(String param1, String param2) {
        profile fragment = new profile();

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
        View view=inflater.inflate(R.layout.fragment_profile, container, false);
         TextView username = view.findViewById(R.id.username); // Must be a TextView
        TextView email = view.findViewById(R.id.userEmail);   // Must be a TextView
        Chip sportChip = view.findViewById(R.id.sportChip);   // Use Chip instead of TextView
        Chip collegeChip = view.findViewById(R.id.collegeChip);
        Chip roleBadge = view.findViewById(R.id.roleBadge);

        // Fix potential casting issues
        View ageStatView = view.findViewById(R.id.ageStat);
        TextView ageTextView = ageStatView.findViewById(R.id.statValue);
        View experienceStatView = view.findViewById(R.id.experienceStat);
        TextView experienceStat = experienceStatView.findViewById(R.id.statValue);

        View memStatView = view.findViewById(R.id.memberStat);
        TextView memberStat = memStatView.findViewById(R.id.statValue);

        // Retrieve token from SharedPreferences
        SharedPreferences athlo_pref = getActivity().getSharedPreferences("athlo_user", Activity.MODE_PRIVATE);
        String token = athlo_pref.getString("token", "");

        if (token.isEmpty()) {
            Toast.makeText(getActivity(), "Authorization token is missing!", Toast.LENGTH_SHORT).show();
            return view; // Stop execution if token is missing
        }

        // API URL
        String url = "https://athlonet-api.vercel.app/api/athlonet/user/auth/profile";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {


                        if (response.getBoolean("success")) {
                            JSONObject profile = response.getJSONObject("profile");
                            Toast.makeText(getActivity(),""+profile,Toast.LENGTH_SHORT).show();

                            // Extracting data
                            String userName = profile.getString("username");
                            username.setText(userName);

                            String userEmail = profile.getString("email");
                            email.setText(userEmail);
//                            String userRole = profile.getString("role");
//                            roleBadge.setText(userRole);
                            String userSport = profile.getString("sport");
                            sportChip.setText(userSport);
                            String userCollege = profile.getString("college");
                            collegeChip.setText(userCollege);
                            int userAge = profile.getInt("age");
                            ageTextView.setText(String.valueOf(userAge));
                            int userExperience = profile.getInt("experience");
                            experienceStat.setText(String.valueOf(userExperience));








                            Toast.makeText(getActivity(), "Profile Loaded", Toast.LENGTH_SHORT).show();

                            // Extract Achievements
                            JSONArray achievementsArray = profile.getJSONArray("achievements");
                            ArrayList<String> achievements = new ArrayList<>();
                            for (int i = 0; i < achievementsArray.length(); i++) {
                                achievements.add(achievementsArray.getString(i));
                            }

                            // Extract Followed Organizations
                            JSONArray followedOrganizationsArray = profile.getJSONArray("followedOrganizations");
                            ArrayList<String> followedOrganizations = new ArrayList<>();
                            for (int i = 0; i < followedOrganizationsArray.length(); i++) {
                                followedOrganizations.add(followedOrganizationsArray.getString(i));
                            }

                            // Extract Teams
                            JSONArray teamsArray = profile.getJSONArray("teams");
                            ArrayList<String> teams = new ArrayList<>();
                            for (int i = 0; i < teamsArray.length(); i++) {
                                teams.add(teamsArray.getString(i));
                            }
                            memberStat.setText(String.valueOf(teams.size()));
                            // Update UI on the main thread

                            Log.d("Profile", "Username: " + userName);
                            Log.d("Profile", "Email: " + userEmail);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("API_ERROR", "JSON Parsing Error: " + e.getMessage());
                    }
                },
                error -> {
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                        String errorResponse = new String(networkResponse.data);
                        Log.e("API_ERROR", "Error Response: " + errorResponse);
                    }
                    error.printStackTrace();
                    Toast.makeText(getActivity(), "Authentication Failed!", Toast.LENGTH_SHORT).show();
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization",  token);


                return headers;
            }
        };

        // Set retry policy
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000, // Increased timeout
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        // Add request to queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);


        return  view;

    }
}