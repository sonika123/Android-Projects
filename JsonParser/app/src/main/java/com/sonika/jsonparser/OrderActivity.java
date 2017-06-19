package com.sonika.jsonparser;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.sonika.jsonparser.Pojo.FoodDetails;

import static android.Manifest.permission_group.SMS;
import static android.R.layout.simple_spinner_dropdown_item;

/**
 * Created by sonika on 6/15/2017.
 */
public class OrderActivity extends AppCompatActivity {
    EditText editText_name, editText_address, editText_phone;
    Button button_msg, button_email;
    Spinner spinner_quantity;
    String squantity, sname, saddress, sphone;

    String quantity[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        final String[] address={ "suwaldeepak@gmail.com"};

        final FoodDetails foodDetails = (FoodDetails) getIntent().getSerializableExtra("key");

        editText_address = (EditText) findViewById(R.id.activity_order_address);
        editText_name = (EditText) findViewById(R.id.activity_order_name);
        editText_phone = (EditText) findViewById(R.id.activity_order_phone);
        button_msg = (Button) findViewById(R.id.activity_order_btnmsg);
        button_email = (Button) findViewById(R.id.activity_order_btnemail);

        spinner_quantity = (Spinner) findViewById(R.id.spinner_quantity);

        ArrayAdapter cc = new ArrayAdapter(this, android.R.layout.simple_spinner_item, quantity);
        cc.setDropDownViewResource(simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner_quantity.setAdapter(cc);


        button_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sname = editText_name.getText().toString();
                saddress = editText_address.getText().toString();
                sphone = editText_phone.getText().toString();
                squantity = (String) spinner_quantity.getSelectedItem();

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + 1234567890));
                intent.putExtra("sms_body", sname + " " + "want to order" + " " + foodDetails.getName() + " " + "," + "Quantity" + squantity + " " + "at" + " " + saddress);
                startActivity(intent);
            }
        });

        button_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                composeEmail(address, "Order");
            }
        });
    }

    public void composeEmail(String[] addresses, String subject) {
        final FoodDetails foodDetails = (FoodDetails) getIntent().getSerializableExtra("key");
        sname = editText_name.getText().toString();
        saddress = editText_address.getText().toString();
        sphone = editText_phone.getText().toString();
        squantity = (String) spinner_quantity.getSelectedItem();

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, sname +" wants to order " +foodDetails.getName() + " of quantity: " +squantity + " at " +saddress);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
