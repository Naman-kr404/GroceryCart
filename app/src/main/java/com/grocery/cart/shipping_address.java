package com.grocery.cart;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.grocery.cart.databinding.ActivityShippingAddressBinding;

public class shipping_address extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityShippingAddressBinding binding;
    private boolean isSatelliteView = false;
    public static double latitude;
    public static double longitude;
    public static String txtAddress;
    EditText hAddress;
    TextView lat;
    TextView longi;
    Button submitAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityShippingAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Button btnToggleView = findViewById(R.id.btnToggleView);
        hAddress=findViewById(R.id.hAddress);
        lat=findViewById(R.id.lat);
        longi=findViewById(R.id.longi);
        btnToggleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleMapType();
            }
        });
        submitAdd=findViewById(R.id.submitAdd);
        submitAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hAddress.getText().toString().isEmpty()){
                    txtAddress=hAddress.getText().toString();
                    Intent intent=new Intent(shipping_address.this, SelectedItems.class);
                    startActivity(intent);
//                SelectedItems.addressView.setVisibility(View.VISIBLE);
                }
                else {
                    Toast.makeText(shipping_address.this, "Select shipping address", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng).title("Selected location"));
                 latitude=latLng.latitude;
                 longitude=latLng.longitude;
                lat.setText(String.valueOf(latitude));
                longi.setText(String.valueOf(longitude));
            }
        });

        // Enable the map type control to allow users to switch between map types (e.g., satellite view)
//        mMap.getUiSettings().setMapTypeControlEnabled(true);
        LatLng sydney = new LatLng(25.6664002, 85.8363044);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Dalsingh Sarai"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15.0f));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
    private void toggleMapType() {
        if (isSatelliteView) {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        } else {
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }
        isSatelliteView = !isSatelliteView; // Toggle the flag
    }
}