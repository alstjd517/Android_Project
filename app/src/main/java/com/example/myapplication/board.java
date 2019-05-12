package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class board extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board);


    }
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.write:
                Intent intent = new Intent(getApplicationContext(),board1.class);   // 완료 후 다음 위치
                startActivity(intent);

        }
    }


}
