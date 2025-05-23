package com.grocery.cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SelectedItems extends AppCompatActivity {
    public static ArrayList<seletedContactModel> arrContacts=new ArrayList<>();
    int REQUEST_CALL_CODE=1;
    RelativeLayout addressView;
    TextView lat1;
    TextView longi1;
    TextView hAddress;
    public static int flag=0;
    FirebaseDatabase db;
    String s;
    StringBuilder details=new StringBuilder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_items);
        EditText phone=findViewById(R.id.phone);
        db=FirebaseDatabase.getInstance();
        Toolbar toolbar=findViewById(R.id.toolbar);
        addressView=findViewById(R.id.addressView);
        hAddress=findViewById(R.id.hAddress);
        lat1=findViewById(R.id.lat1);
        longi1=findViewById(R.id.longi1);
//        EditText address=findViewById(R.id.address);
//        EditText pincode=findViewById(R.id.pincode);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //back button in toolbar
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        Button buyBtn=findViewById(R.id.buyBtn);
        Button btnMdfy=findViewById(R.id.btnMdfy);
        btnMdfy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectedItems.super.onBackPressed();
            }
        });
        Button addressBtn=findViewById(R.id.addressBtn);
        RecyclerView rView=findViewById(R.id.rView);
        rView.setLayoutManager(new LinearLayoutManager(this));
        selectedAdapter adapter=new selectedAdapter(this, arrContacts);
        rView.setAdapter(adapter);
        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                for(int i=0;i< arrContacts.size(); i++){
                        s=arrContacts.get(i).product_info+", "+"Rs:"+arrContacts.get(i).price+"; "+"Quantity: "+arrContacts.get(i).nQuantity;
                        details.append(s+"\n");
                    }
                details.append("ADDRESS: "+String.valueOf(shipping_address.latitude)+"; "+String.valueOf(shipping_address.longitude)+"\n"+"Location :"+shipping_address.txtAddress+"\n");

                if (!phone.getText().toString().equals("")){
                    if (phone.getText().toString().length()>9){
                        details.append("Mob: "+phone.getText().toString());
                        DatabaseReference node=db.getReference("Order_Details");
                        OrderDetailsModel od=new OrderDetailsModel(details.toString());
                        node.push().setValue(od);
                        AlertDialog helpDialog = new AlertDialog.Builder(SelectedItems.this).create();
                        helpDialog.setTitle("Success !");
                        helpDialog.setIcon(R.drawable.baseline_check_circle_24);
                        helpDialog.setMessage("Order Placed Successfully...");
                        helpDialog.setButton(DialogInterface.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent=new Intent(SelectedItems.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
                        helpDialog.show();

                    }
                    else {
                        Toast.makeText(SelectedItems.this, "Incorrect Mobile number", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(SelectedItems.this, "Enter your mobile number", Toast.LENGTH_SHORT).show();
                }
//                if (ContextCompat.checkSelfPermission(SelectedItems.this, android.Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
//                    SmsManager smsManager=SmsManager.getDefault();
//                    for(int i=0;i< arrContacts.size(); i++){
//                        String s=arrContacts.get(i).product_info+", "+"Rs:"+arrContacts.get(i).price+"; "+"Quantity: "+arrContacts.get(i).nQuantity +"\n";
//                        details.append(s);
//                    }
//                    details.append("ADDRESS: "+String.valueOf(shipping_address.latitude)+"; "+String.valueOf(shipping_address.longitude));
//
//                    smsManager.sendTextMessage("6204597338", null, "Order Details"+"\n"+details, null, null);
//                    Toast.makeText(SelectedItems.this, "Ordered Successfully ", Toast.LENGTH_SHORT).show();
//                    Intent intent=new Intent(SelectedItems.this, MainActivity.class);
//                    startActivity(intent);
//                }else {
//                    ActivityCompat.requestPermissions(SelectedItems.this, new String[]{Manifest.permission.SEND_SMS}, REQUEST_CALL_CODE);//ask for permission
//                }

            }
        });
        if (flag==0){
            addressBtn.setVisibility(View.VISIBLE);
            buyBtn.setVisibility(View.GONE);
            addressView.setVisibility(View.GONE);
        }
        else {
            addressBtn.setVisibility(View.GONE);
            buyBtn.setVisibility(View.VISIBLE);
            addressView.setVisibility(View.VISIBLE);
            btnMdfy.setVisibility(View.GONE);
            lat1.setText(String.valueOf(shipping_address.latitude));
            longi1.setText(String.valueOf(shipping_address.longitude));
            hAddress.setText(shipping_address.txtAddress);
        }
        addressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SelectedItems.this, shipping_address.class);
                startActivity(intent);
                flag=1;
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId= item.getItemId();
        if(itemId==android.R.id.home){
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}