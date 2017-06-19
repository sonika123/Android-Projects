package com.sonika.jsonparser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sonika.jsonparser.JsonHelper.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    EditText memail, mpassword;
    String semail, spassword;
    Button mlogin;
    TextView new_signup;
    int flag;

    SharedPreferences sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        memail = (EditText) findViewById(R.id.activity_login_email);
        mpassword = (EditText) findViewById(R.id.activity_login_password);
        mlogin = (Button) findViewById(R.id.activity_login_login);
        new_signup = (TextView) findViewById(R.id.activity_login_signup);

        new_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(i);
            }
        });

        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (memail.length()<0 || mpassword.length()<0)
                {
                    Toast.makeText(LoginActivity.this, "Please, fill all the fields! ", Toast.LENGTH_SHORT).show();
                }
                else {
                    semail = memail.getText().toString();
                    spassword = mpassword.getText().toString();
                    new loginAsyncTask().execute();

                }

            }
        });

    }
    class loginAsyncTask extends AsyncTask<String, String, String> {
        ProgressDialog mprogressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mprogressDialog=new ProgressDialog(LoginActivity.this);
            mprogressDialog.setMessage("Please wait");
            mprogressDialog.setCancelable(false);
            mprogressDialog.show();

          }
        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> loginHashMap = new HashMap<>();
            loginHashMap.put("email", semail);
            loginHashMap.put("password", spassword);
            JsonParser jsonParser = new JsonParser();
            JSONObject jsonObject = jsonParser.performPostCI("http://kinbech.6te.net/ResturantFoods/api/login", loginHashMap);

            try {

                if (jsonObject == null) {
                    flag = 1;
                } else if (jsonObject.getString("status").equals("success")) {

                    flag = 2;
                } else {
                    flag = 3;
                }
            } catch (JSONException e) {
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mprogressDialog.dismiss();
            if (flag == 1) {
                Toast.makeText(LoginActivity.this, "Server/Network issue", Toast.LENGTH_SHORT).show();

            } else if (flag == 2)
            {

                Toast.makeText(LoginActivity.this, "Success" , Toast.LENGTH_SHORT).show();
                Intent i = new Intent(LoginActivity.this, FooditemsActivity.class);
                startActivity(i);

                sm = getSharedPreferences("USER_LOGIN", 0);
                SharedPreferences.Editor editor = sm.edit();
                editor.putString("email", semail);
                editor.putString("password", spassword);
                editor.commit();

            }
            else {
                Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        }
    }}
