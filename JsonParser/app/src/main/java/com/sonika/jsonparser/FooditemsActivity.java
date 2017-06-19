package com.sonika.jsonparser;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.sonika.jsonparser.Adapters.FoodItemsListAdapter;
import com.sonika.jsonparser.JsonHelper.JsonParser;
import com.sonika.jsonparser.Pojo.FoodDetails;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FooditemsActivity extends AppCompatActivity {
    ListView mlist;
    List<FoodDetails> foodDetailsList = new ArrayList<>();
    FoodDetails foodDetails;
    Button logout;

    CarouselView carouselView;
    int[] sampleImages = {R.drawable.murgha, R.drawable.burger, R.drawable.momo, R.drawable.fries, R.drawable.pizza};

    int flag = 0;

    SharedPreferences sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fooditems_list);
        mlist = (ListView) findViewById(R.id.list_fooditems);

        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);

        logout = (Button) findViewById(R.id.btnlogout);



        new FoodItemAsyncTask().execute();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sm = getSharedPreferences("loggedInUsers", 0);
                SharedPreferences.Editor editor1 = sm.edit();
                editor1.clear();
                editor1.commit();
                Intent i = new Intent(FooditemsActivity.this, LoginActivity.class);
                startActivity(i);

            }
        });



    }


    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

    class FoodItemAsyncTask extends AsyncTask<String, String, String> {
        ProgressDialog mprogressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mprogressDialog = new ProgressDialog(FooditemsActivity.this);
            mprogressDialog.setMessage("Please wait");
            mprogressDialog.setCancelable(false);
            mprogressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> loginHashMap = new HashMap<>();
            JsonParser jsonParser = new JsonParser();
            JSONObject jsonObject = jsonParser.performPostCI("http://kinbech.6te.net/ResturantFoods/api/showMenuList", loginHashMap);

            try {

                if (jsonObject == null) {
                    flag = 1;
                } else if (jsonObject.getString("status").equals("success")) {
                    JSONArray jsonArray=jsonObject.getJSONArray("data");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject dataObject = jsonArray.getJSONObject(i);
                        String id = dataObject.getString("id");
                        String description = dataObject.getString("details");
                        String price = dataObject.getString("price");
                        String materials = dataObject.getString("materials");
                        String image = dataObject.getString("image");
                        String name = dataObject.getString("name");

                        foodDetails = new FoodDetails(id, name, price, description, image, materials );
                        foodDetailsList.add(foodDetails);
                        flag = 2;
                    }
                }

                else
                {
                    flag = 3;
                }

            } catch (JSONException e) {

            }


            return  null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mprogressDialog.dismiss();
            if (flag == 1) {
                Toast.makeText(FooditemsActivity.this, "Server/Network issue", Toast.LENGTH_SHORT).show();

            } else if (flag == 2) {
                Toast.makeText(FooditemsActivity.this, "Success", Toast.LENGTH_SHORT).show();
                FoodItemsListAdapter foodItemsListAdapter = new FoodItemsListAdapter(FooditemsActivity.this, R.layout.items_list_adapter,foodDetailsList);
                mlist.setAdapter(foodItemsListAdapter);
                mlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        FoodDetails one = foodDetailsList.get(position);
                        Intent i = new Intent(FooditemsActivity.this, DescriptionActivity.class);
                        i.putExtra("individual", one);
                        startActivity(i);
                    }
                });

               } else {
                Toast.makeText(FooditemsActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        }

    }


}