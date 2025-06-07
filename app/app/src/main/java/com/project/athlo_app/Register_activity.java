package com.project.athlo_app;

import android.content.Intent;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Register_activity extends AppCompatActivity {


    private Button login_btn;
    private EditText name_et;
    private EditText email_et;
    private EditText password_et;
    private String name,email,password;
   private ProgressBar progressBar;




    private Button register;
    private utilsService utilsService;
    private View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        utilsService=new utilsService();
        
        name_et=findViewById(R.id.name_ET);
       
        email_et=findViewById(R.id.email_ET);
        
        password_et=findViewById(R.id.password_ET);
        progressBar=findViewById(R.id.progress_bar);

        String url="http://192.168.153.106:3000/athlonet/auth/register";

        register=findViewById(R.id.Register);



        //login button to transfer from register page to login page
        login_btn=findViewById(R.id.loginBtn);
        login_btn.setOnClickListener(view -> startActivity(new Intent(Register_activity.this,login_activity.class)));
        //end

       // register user on platform

        register.setOnClickListener(view -> {
            utilsService.hidekeyboard(view,Register_activity.this);
            name=name_et.getText().toString();
            email=email_et.getText().toString();
            password=password_et.getText().toString();
            if(isvalidate(view)){
                try {
                    registerUser(view);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            }


        });





         }

    private void registerUser(View view) throws JSONException {

        progressBar.setVisibility(View.VISIBLE);
        String api = "https://athlonet-api.vercel.app/api/athlonet/user/auth/register";
//        String api = "http://192.168.114.106:3000/athlonet/auth/register";
        Map<String ,String > params=new HashMap<>();
        params.put("username",name);
        params.put("email",email);
        params.put("password",password);


        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, api, new JSONObject(params), response -> {
            try {
                if(response.getBoolean("success")){
                    String token=response.getString("token");

                    startActivity(new Intent(Register_activity.this,splash_activity.class));

                }
                String message =response.getString("msg");
                Toast.makeText(getApplicationContext(),""+message,Toast.LENGTH_SHORT).show();

                Toast.makeText(getApplicationContext(),"user registered",Toast.LENGTH_SHORT).show();
                utilsService.showSnackBar(view,"user registered");
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
                    Toast.makeText(Register_activity.this,obj.getString("msg"),Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);

    }

    public boolean isvalidate(View view) {
        boolean isValid;
        if(!TextUtils.isEmpty(name)){

            if(!TextUtils.isEmpty(email)){
                if(!TextUtils.isEmpty(password)){
                    isValid=true;
                }else{
                    utilsService.showSnackBar(view,"Enter password..");
                    isValid=false;
                }
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
}