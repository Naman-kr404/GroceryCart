package com.grocery.cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    dbAdapter adapter;
    public static final int REQUEST_CALL_CODE = 1;
    ArrayList<String> arrayList=new ArrayList<>();
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnSub=findViewById(R.id.btnSub);
        Toolbar toolbar=findViewById(R.id.toolbar);
        AutoCompleteTextView acTxtView=findViewById(R.id.acTxtView);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //back button in toolbar
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SelectedItems.arrContacts.size()>0) {
                    Intent intent = new Intent(MainActivity.this, SelectedItems.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(MainActivity.this, "Select an item from list", Toast.LENGTH_SHORT).show();
                }
            }
        });
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference = FirebaseDatabase.getInstance().getReference().child("contacts");
        FirebaseRecyclerOptions<ContactModel> options =
                new FirebaseRecyclerOptions.Builder<ContactModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("contacts"), ContactModel.class)
                        .build();
        adapter=new dbAdapter(options);
        recyclerView.setAdapter(adapter);

        ArrayAdapter<String> acAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        acTxtView.setAdapter(acAdapter);
        acTxtView.setThreshold(1);
        acTxtView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String searchTerm = acTxtView.getText().toString();
                if (!searchTerm.isEmpty()) {
                    Query searchQuery = databaseReference.orderByChild("product_info").equalTo(searchTerm); // Search filter
                    FirebaseRecyclerOptions<ContactModel> newOptions =
                            new FirebaseRecyclerOptions.Builder<ContactModel>()
                                    .setQuery(searchQuery, ContactModel.class)
                                    .build();

                    adapter.updateOptions(newOptions);

//                adapter.startListening();
                }
//                else {
//                    // If the search term is empty, reset the options to show all data
//                    FirebaseRecyclerOptions<ContactModel> defaultOptions =
//                            new FirebaseRecyclerOptions.Builder<ContactModel>()
//                                    .setQuery(FirebaseDatabase.getInstance().getReference().child("contacts"), ContactModel.class)
//                                    .build();
//                    adapter.updateOptions(defaultOptions);
//                }
                recyclerView.setAdapter(adapter);
            }
        });
        acTxtView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int after) {
                String searchTerm = acTxtView.getText().toString().trim();

                if (searchTerm.isEmpty()) {
                    // If the text is empty, revert to the original (unfiltered) options
                    FirebaseRecyclerOptions<ContactModel> defaultOptions =
                            new FirebaseRecyclerOptions.Builder<ContactModel>()
                                    .setQuery(FirebaseDatabase.getInstance().getReference().child("contacts"), ContactModel.class)
                                    .build();
                    adapter.updateOptions(defaultOptions);
                    recyclerView.setAdapter(adapter);
                }
//                else {
//                    // Apply the search filter when the text is not empty
//                    Query searchQuery = databaseReference.orderByChild("product_info").equalTo(searchTerm);
//                    FirebaseRecyclerOptions<ContactModel> newOptions =
//                            new FirebaseRecyclerOptions.Builder<ContactModel>()
//                                    .setQuery(searchQuery, ContactModel.class)
//                                    .build();
//                    adapter.updateOptions(newOptions);
//                    recyclerView.setAdapter(adapter);
//                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Clear the list before adding new data to avoid duplicates
                arrayList.clear();

                // Extract the "coordinates" array from the snapshot
                for (DataSnapshot root : dataSnapshot.getChildren()) {
                    String productInfo = root.child("product_info").getValue(String.class);
                    if (productInfo != null) {
                        arrayList.add(productInfo);
                    }
                }
                acAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle potential errors
                Log.e("FirebaseError", "Database error: " + databaseError.getMessage());
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId= item.getItemId();
        if(itemId==android.R.id.home){
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    //    @Override
//    protected void onStop() {
//        super.onStop();
//        adapter.stopListening();
//    }
    public void requestSMSPermission(String info, String price, String quantity){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            sendSMS(info, price, quantity);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, REQUEST_CALL_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void sendSMS(String info, String price, String quantity) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage("+916204190993", null, info + " " + "Rs: " + price + " " + "Quantity: " + quantity, null, null);
        Toast.makeText(this, "sms sent", Toast.LENGTH_SHORT).show();
    }
}