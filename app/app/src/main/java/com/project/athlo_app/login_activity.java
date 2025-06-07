package com.project.athlo_app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.project.athlo_app.UtilsService.SharedPreferenceclass;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class login_activity extends AppCompatActivity {
        private Button register_btn;
        private Button login_btn;
        private EditText email_et;
        private EditText password_et;
        private SharedPreferenceclass sharedPreferenceclass;

        private  String email,password;



private utilsService utilsService;

        private ProgressBar progressBar;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        utilsService=new utilsService();
        sharedPreferenceclass=new SharedPreferenceclass(this);


        // Intent to tranfer from login activity to register activity
        register_btn=findViewById(R.id.Register_btn);
        login_btn=findViewById(R.id.Login_btn);
        email_et=findViewById(R.id.email_ET);
        password_et=findViewById(R.id.password_ET);
        progressBar=findViewById(R.id.progress_bar);



        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login_activity.this,Register_activity.class));

            }
        });
        // end

        login_btn.setOnClickListener(view -> {
            utilsService.hidekeyboard(view,login_activity.this);
            email=email_et.getText().toString();
            password=password_et.getText().toString();
            if(isvalidate(view)){
                loginUser(view);

            }

        });





    }

    private void loginUser(View view) {


            progressBar.setVisibility(View.VISIBLE);
        String api = "https://athlonet-api.vercel.app/api/athlonet/user/auth/login";
           // String api = "http://192.168.114.106:3000/athlonet/auth/login";
            Map<String ,String > params=new HashMap<>();
            params.put("email",email);
            params.put("password",password);


            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, api, new JSONObject(params), response -> {
                try {
                    if(response.getBoolean("success")){
                        String token=response.getString("token");
                        sharedPreferenceclass.setValue_string("token",token);
                        startActivity(new Intent(login_activity.this,splash_activity.class));

                    }
                   Toast.makeText(getApplicationContext(),"user login successfully"+ response.getString("token"),Toast.LENGTH_SHORT).show();

                    progressBar.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();

                    progressBar.setVisibility(View.GONE);
                }

            }, error -> {
                NetworkResponse response=error.networkResponse;
                if(error instanceof ServerError && response!=null){
                    try {
                        String res=new String(response.data, HttpHeaderParser.parseCharset(response.headers,"utf-8"));
                        JSONObject obj=new JSONObject(res);
                        Toast.makeText(login_activity.this,obj.getString("msg"),Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }catch (JSONException | UnsupportedEncodingException je){
                        je.printStackTrace();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String>headers=new HashMap<>();
                    headers.put("Content-Type","application/json");
                    return headers;

                }
            };

            //set the policy
            int socketTime=3000;
            RetryPolicy policy=new DefaultRetryPolicy(socketTime,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjectRequest.setRetryPolicy(policy);

            //request add

            RequestQueue requestQueue= Volley.newRequestQueue(this);
            requestQueue.add(jsonObjectRequest);

        }



    public boolean isvalidate(View view) {
        boolean isValid;
        if(!TextUtils.isEmpty(email)){


            if(!TextUtils.isEmpty(password)){
               isValid=true;

            }else{
                utilsService.showSnackBar(view,"Enter email..");
                isValid=false;

            }
        }else{
            utilsService.showSnackBar(view,"Enter name..");
            isValid=false;

        }
        return isValid;
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences athlo_pref=getSharedPreferences("athlo_user", Activity.MODE_PRIVATE);

        if(athlo_pref.contains("token")){
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
    }
}
