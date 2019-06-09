package com.example.myapplication.Board;

import android.app.FragmentManager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.example.myapplication.Admin;
import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;
import com.google.android.gms.maps.CameraUpdateFactory;

import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.MapFragment;

import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.MarkerOptions;



public class Map2 extends AppCompatActivity

        implements OnMapReadyCallback {
    static double lat2 = 0;
    static double lon2 = 0;



    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.map2);


        FragmentManager fragmentManager = getFragmentManager();

        MapFragment mapFragment = (MapFragment)fragmentManager

                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        String lat = getIntent().getExtras().getString("lat");
        String lon = getIntent().getExtras().getString("lon");
        lat2 = Double.parseDouble(lat);
        lon2 = Double.parseDouble(lon);

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.logout:
                Intent intent = new Intent( Map2.this, LoginActivity.class);

                startActivity( intent );
                break;
            case R.id.home:
                Intent intent2 = new Intent( Map2.this, BolistAct.class);

                startActivity( intent2 );
        }
        return super.onOptionsItemSelected(item);
    }


    @Override

    public void onMapReady(final GoogleMap map) {


        LatLng SEOUL = new LatLng(lat2, lon2);


        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.position(SEOUL);

        //markerOptions.title("서울");

        markerOptions.snippet(lat2 + ", " + lon2);

        map.addMarker(markerOptions);


        map.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));

        map.animateCamera(CameraUpdateFactory.zoomTo(15));

    }


}