package com.example.myapplication.Sim;


import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.myapplication.Board.BolistAct;
import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class sim extends AppCompatActivity implements OnMapReadyCallback {
    static String rlist ="";
    static String rlist1 ="";
    static Double lat2;
    static Double lon2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boitem_list2);

        String b2 = getIntent().getExtras().getString("b2");
        String b3= getIntent().getExtras().getString("b3");
        String b4= getIntent().getExtras().getString("b4");
        String b5= getIntent().getExtras().getString("b5");

        TextView t1 = (TextView)findViewById(R.id.textView_list_name);
        TextView t2 = (TextView)findViewById(R.id.textView_list_location);
        TextView t3 = (TextView)findViewById(R.id.textView_list_date);
        TextView t4 = (TextView)findViewById(R.id.textView_list_Detail);

        t1.setText(b2);
        t2.setText(b3);
        t3.setText(b4);
        t4.setText(b5);

        String [] split = b3.split("/");
        rlist =split[0];
        rlist1 =split[1];

        FragmentManager fragmentManager = getFragmentManager();

        MapFragment mapFragment = (MapFragment)fragmentManager

                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        lat2 = Double.parseDouble(rlist);
        lon2 = Double.parseDouble(rlist1);


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
                Intent intent = new Intent( sim.this, LoginActivity.class);

                startActivity( intent );
                break;
            case R.id.home:
                Intent intent2 = new Intent( sim.this, BolistAct.class);

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

        map.animateCamera(CameraUpdateFactory.zoomTo(10));

    }
}
