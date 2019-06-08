package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class BoMA extends AppCompatActivity {

    private static String IP_ADDRESS = "203.234.62.111";
    private static String TAG = "myapplication";
    Intent intent;
    private EditText mEditTextName;
    private EditText mEditTextlocation;
    private EditText mEditTextdate;
    private EditText mEditTextDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boam);

        mEditTextName = (EditText) findViewById(R.id.editText_main_name);
        mEditTextlocation = (EditText) findViewById(R.id.editText_main_location);
        mEditTextdate = (EditText) findViewById(R.id.editText_main_date);
        mEditTextDetail = (EditText) findViewById(R.id.editText_main_detail);


        Button buttonInsert = (Button) findViewById(R.id.button_main_insert);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = mEditTextName.getText().toString();
                String location = mEditTextlocation.getText().toString();
                String date = mEditTextdate.getText().toString();
                String Detail = mEditTextDetail.getText().toString();

                InsertData task = new InsertData();
                task.execute("http://" + IP_ADDRESS + "/a/insert.php", name, location, date,Detail);


               /* mEditTextName.setText("");
                mEditTextlocation.setText("ff");
                mEditTextdate.setText("");*/

                intent = new Intent(getApplicationContext(), listAct.class);   // 완료 후 다음 위치
                startActivity(intent);

            }
        });

    }



    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(BoMA.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            //   mTextViewResult.setText(result);
            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String name = (String) params[1];
            String location = (String) params[2];
            String date = (String) params[3];
            String Detail = (String) params[4];

            String serverURL = (String) params[0];
            String postParameters = "name=" + name + "&location=" + location + "&date=" + date + "&Detail=" + Detail ;


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }
}


