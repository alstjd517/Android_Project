package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.write:
                Intent intent = new Intent(getApplicationContext(),board1.class);   // 완료 후 다음 위치
                startActivity(intent);

        }
    }


}
