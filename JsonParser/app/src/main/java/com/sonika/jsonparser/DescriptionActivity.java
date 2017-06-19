package com.sonika.jsonparser;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sonika.jsonparser.Adapters.FoodItemsListAdapter;
import com.sonika.jsonparser.JsonHelper.JsonParser;
import com.sonika.jsonparser.Pojo.FoodDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DescriptionActivity extends AppCompatActivity {
    TextView textView_desc;
    ImageView imageView_img;
    Button button_ordercall, button_ordermsg_email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description_activity);

        final FoodDetails foodDetails = (FoodDetails) getIntent().getSerializableExtra("individual");
        textView_desc = (TextView) findViewById(R.id.desc_description);
        imageView_img = (ImageView) findViewById(R.id.desc_image);
        button_ordercall = (Button) findViewById(R.id.desc_btncall);
        button_ordermsg_email = (Button) findViewById(R.id.desc_btnmsg_email);


        Glide.with(DescriptionActivity.this).load(foodDetails.getImage()).into(imageView_img);
        textView_desc.setText(foodDetails.getDescription());


        button_ordermsg_email.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        button_ordermsg_email.setBackgroundColor(Color.BLUE);
                        break;
                    case MotionEvent.ACTION_UP:
                        button_ordermsg_email.setBackgroundColor(Color.CYAN);
                        Intent i = new Intent(DescriptionActivity.this, OrderActivity.class);
                        i.putExtra("key", foodDetails);
                        startActivity(i);
                        break;
                }
                return true;
            }

        });

        button_ordercall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:7890"));
                if (ActivityCompat.checkSelfPermission(DescriptionActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);

            }
        });


    }
}