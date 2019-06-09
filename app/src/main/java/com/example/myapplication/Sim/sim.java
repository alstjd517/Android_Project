package com.example.myapplication.Sim;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class sim extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sumin);

        String b2 = getIntent().getExtras().getString("b2");
        String b3= getIntent().getExtras().getString("b3");
        String b4= getIntent().getExtras().getString("b4");
        String b5= getIntent().getExtras().getString("b5");

        TextView t1 = (TextView)findViewById(R.id.textView2);
        TextView t2 = (TextView)findViewById(R.id.textView3);
        TextView t3 = (TextView)findViewById(R.id.textView4);
        TextView t4 = (TextView)findViewById(R.id.textView5);

        t1.setText(b2);
        t2.setText(b3);

    }
}
