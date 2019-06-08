package com.example.myapplication.User;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.myapplication.Board.BolistAct;
import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.userlistmg);
        //Button managementButton = (Button)findViewById(R.id.manage);
/*
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String userPassword = intent.getStringExtra("userPassword");

        //admin 계정이 아니면 버튼 안보이게 함
        if(!id.equals("admin")){
            //managementButton.setEnabled(false);
            managementButton.setVisibility(View.GONE);
        }
*/
        //MANAGE버튼이 눌리면 여기로 옴
        // managementButton.setOnClickListener(new View.OnClickListener() {
        //          @Override
        //      public void onClick(View view) {
        new BackgroundTask().execute();
        //     }
        //   });


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
                Intent intent = new Intent( UserActivity.this, LoginActivity.class);

                startActivity( intent );
                break;
            case R.id.home:
                Intent intent2 = new Intent( UserActivity.this, BolistAct.class);

                startActivity( intent2 );
        }
        return super.onOptionsItemSelected(item);
    }

    //모든회원에 대한 정보를 가져오기 위한 쓰레드
    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            //List.php은 파싱으로 가져올 웹페이지
            target = "http://220.68.27.140/lost_admin/List.php";
        }

        @Override
        protected String doInBackground(Void... voids) {

            try{
                URL url = new URL(target);//URL 객체 생성

                //URL을 이용해서 웹페이지에 연결하는 부분
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

                //바이트단위 입력스트림 생성 소스는 httpURLConnection
                InputStream inputStream = httpURLConnection.getInputStream();

                //웹페이지 출력물을 버퍼로 받음 버퍼로 하면 속도가 더 빨라짐
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;

                //문자열 처리를 더 빠르게 하기 위해 StringBuilder클래스를 사용함
                StringBuilder stringBuilder = new StringBuilder();

                //한줄씩 읽어서 stringBuilder에 저장함
                while((temp = bufferedReader.readLine()) != null){
                    stringBuilder.append(temp + "\n");//stringBuilder에 넣어줌
                }

                //사용했던 것도 다 닫아줌
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();//trim은 앞뒤의 공백을 제거함

            }catch(Exception e){
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            Intent intent = new Intent(UserActivity.this, ManagementActivity2.class);
            intent.putExtra("userList2", result);//파싱한 값을 넘겨줌
            UserActivity.this.startActivity(intent);//ManagementActivity로 넘어감

        }

    }

}