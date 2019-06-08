package com.example.myapplication.Board;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class BolistAct extends Activity {

    String myJSON;

    private static final String TAG_RESULTS = "result";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_ADD = "location";
    private static final String TAG_Date = "date";


    JSONArray peoples = null;

    ArrayList<HashMap<String, String>> personList;
    static ArrayList<BoSt> al = new ArrayList<BoSt>();
    static ArrayList<String> aa = new ArrayList<String>();


    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bolist);
        list = (ListView) findViewById(R.id.listView);
        personList = new ArrayList<HashMap<String, String>>();
        getData("http://203.234.62.111/a/PHP_connection.php"); //수정 필요

        Button btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BolistAct.this, BoMA.class);
                startActivity(intent);
            }
        });

   /*     ListView listView = (ListView) findViewById(R.id.listView);
       listView.setOnItemClickListener(n);*/

        //   ListView listView = (ListView) findViewById(R.id.listView);
        BoAdapter adapter = new BoAdapter(this, R.layout.bolist_item, al);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Intent intent = new Intent( getApplicationContext(), keyMA.class);
                intent.putExtra("id", al.get(position).getMember_id());
                startActivity(intent);
            }
        });


    }


    protected void showList() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);
                String id = c.getString(TAG_ID);
                String name = c.getString(TAG_NAME);
                String location = c.getString(TAG_ADD);
                String date = c.getString(TAG_Date);
                BoSt b = new BoSt();
                b.setMember_id(id);
                al.add(b);
                HashMap<String, String> persons = new HashMap<String, String>();

                persons.put(TAG_ID, id);
                persons.put(TAG_NAME, name);
                persons.put(TAG_ADD, location);
                persons.put(TAG_Date, date);

                personList.add(persons);
            }

            ListAdapter adapter = new SimpleAdapter(
                    BolistAct.this, personList, R.layout.bolist_item,
                    new String[]{TAG_ID, TAG_NAME, TAG_ADD, TAG_Date},
                    new int[]{R.id.id, R.id.name, R.id.location, R.id.Date}
            );

            list.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }


            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }

}