package com.example.myapplication.Board;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.LoginActivity;
import com.example.myapplication.PersonalData;
import com.example.myapplication.R;
import com.example.myapplication.UsersAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import android.app.FragmentManager;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;


import com.google.android.gms.maps.CameraUpdateFactory;

import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.MapFragment;

import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.MarkerOptions;

public class keyMA extends AppCompatActivity
        {

    private static String IP_ADDRESS = "203.234.62.111";
    private static String TAG = "phpexample";

    //  private EditText mEditTextName;
    //  private EditText mEditTextCountry;
    private TextView mTextViewResult;
    private ArrayList<PersonalData> mArrayList;
    private UsersAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private String mJsonString;
    private String country;
    private String location;
    static String rlist ="";
    static String rlist1 ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keyam);
        mTextViewResult = (TextView) findViewById(R.id.textView_main_result);
        mRecyclerView = (RecyclerView) findViewById(R.id.listView_main_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());


        mArrayList = new ArrayList<>();

        mAdapter = new UsersAdapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        Intent intent = getIntent();

        TextView profile = findViewById(R.id.id);
        mArrayList.clear();
        mAdapter.notifyDataSetChanged();
        String Keyword = intent.getStringExtra("id");


        GetData task = new GetData();
        task.execute("http://" + IP_ADDRESS + "/a/query.php", Keyword);

        Button btn = (Button)findViewById(R.id.button5);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(keyMA.this, Map2.class);
                intent.putExtra("lat",rlist);
                intent.putExtra("lon",rlist1);
                startActivity(intent);
                finish();
            }
        });

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
                Intent intent = new Intent( keyMA.this, LoginActivity.class);

                startActivity( intent );
                break;
            case R.id.home:
                Intent intent2 = new Intent( keyMA.this, BolistAct.class);

                startActivity( intent2 );
        }
        return super.onOptionsItemSelected(item);
    }


    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(keyMA.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);
            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String name = (String) params[1];
            country = (String) params[2];
            String date = (String) params[3];
            String Detail = (String) params[4];

            String serverURL = (String) params[0];
            String postParameters = "name=" + name + "country=" + country + "date=" + date+"Detail="+Detail;


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


    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(keyMA.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);
            Log.d(TAG, "response - " + result);

            if (result == null) {

                mTextViewResult.setText(errorString);
            } else {

                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = "id=" + params[1];


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }


    private void showResult() {

        String TAG_JSON = "webnautes";
        String TAG_ID = "id";
        String TAG_NAME = "name";
        String TAG_COUNTRY = "location";
        String TAG_date = "date";
        String TAG_Detail = "Detail";


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                String id = item.getString(TAG_ID);
                String name = item.getString(TAG_NAME);
                location = item.getString(TAG_COUNTRY);
                String date = item.getString(TAG_date);
                String Detail = item.getString(TAG_Detail);
                String [] split = location.split("/");
               rlist =split[0];
                rlist1 =split[1];
                System.out.println(rlist1);
                PersonalData personalData = new PersonalData();

                personalData.setMember_id(id);
                personalData.setMember_name(name);
                personalData.setMember_address(location);
                personalData.setMember_date(date);
                personalData.setMember_Detail(Detail);
                mArrayList.add(personalData);
                mAdapter.notifyDataSetChanged();

            }


        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

}