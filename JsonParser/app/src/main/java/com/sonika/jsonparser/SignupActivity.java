package com.sonika.jsonparser;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sonika.jsonparser.JsonHelper.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class SignupActivity  extends AppCompatActivity{
    EditText mName, mPassword, mPhone, mAddress, mEmail;
    String sName, sPassword, sPhone, sAddress, sEmail;
    Button signup;
    int flag=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mName = (EditText) findViewById(R.id.activity_signup_name);
        mAddress = (EditText) findViewById(R.id.activity_signup_address);
        mEmail = (EditText) findViewById(R.id.activity_signup_email);
        mPassword = (EditText) findViewById(R.id.activity_signup_password);
        mPhone = (EditText) findViewById(R.id.activity_signup_phone);
        signup = (Button) findViewById(R.id.activity_signup_signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mName.length()<0 || mAddress.length()<0 || mEmail.length()<0 || mPhone.length()<0 || mPassword.length()<0)
                {
                    Toast.makeText(SignupActivity.this, "Please, fill all the fields! ", Toast.LENGTH_SHORT).show();
                }
                else {
                    sName = mName.getText().toString();
                    sAddress = mAddress.getText().toString();
                    sEmail = mEmail.getText().toString();
                    sPassword = mPassword.getText().toString();
                    sPhone = mPhone.getText().toString();
                    new registerAsyncTask().execute();
                }
            }
        });
    }
    class registerAsyncTask extends AsyncTask<String, String, String> {

        ProgressDialog mprogressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mprogressDialog = new ProgressDialog(SignupActivity.this);
            mprogressDialog.setMessage("please wait");
            mprogressDialog.setCancelable(false);
            mprogressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> registerActivityHashMap = new HashMap<>();
            registerActivityHashMap.put("email", sEmail);
            registerActivityHashMap.put("password", sPassword);
            registerActivityHashMap.put("name", sName);
            registerActivityHashMap.put("phone", sPhone);
            registerActivityHashMap.put("address", sAddress);

            JsonParser jsonParser = new JsonParser();
            JSONObject jsonObject = jsonParser.performPostCI("http://kinbech.6te.net/ResturantFoods/api/register", registerActivityHashMap);

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
                Toast.makeText(SignupActivity.this, "Network issue", Toast.LENGTH_SHORT).show();

            } else if (flag == 2) {
                Toast.makeText(SignupActivity.this, "Registration success", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(SignupActivity.this, "Invalid user Try again ", Toast.LENGTH_SHORT).show();
            }

        }
    }

}
