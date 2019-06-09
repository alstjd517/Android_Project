//package com.example.myapplication.Sim;
//
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListAdapter;
//import android.widget.ListView;
//import android.widget.SimpleAdapter;
//import android.widget.TextView;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.HashMap;
//
//
//public class simain extends Activity {
//
//    String myJSON;
//
//    private static final String TAG_RESULTS = "result";
//    private static final String TAG_ID = "id";
//    private static final String TAG_NAME = "name";
//    private static final String TAG_ADD = "location";
//    private static final String TAG_Date = "date";
//
//
//    JSONArray peoples = null;
//
//    ArrayList<HashMap<String, String>> personList;
//    ListView list;
//
//    private EditText mEditText1;
//    private EditText mEditText2;
//    private EditText mEditText3;
//    static ArrayList<simst> a = new ArrayList<simst>();
//    static ArrayList<resultST>  re =   new ArrayList<resultST>();
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.list_item);
//        //list = (ListView) findViewById(R.id.listView);
//        personList = new ArrayList<HashMap<String, String>>();
//        mEditText1 = (EditText) findViewById(R.id.f1);
//        mEditText2 = (EditText) findViewById(R.id.f2);
//        mEditText3 = (EditText) findViewById(R.id.f2);
//
//        getData("http://203.234.62.111/a/PHP_connection.php"); //수정 필요
//        Button buttonInsert = (Button) findViewById(R.id.button);
//        buttonInsert.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //             getData("http://203.234.62.111/a/PHP_connection.php"); //수정 필요
//                String Name = mEditText1.getText().toString();
//                String Date = mEditText2.getText().toString();
//                String loc = mEditText3.getText().toString();
//                simst ss = new simst();
//
//                ArrayList<simst> input = new ArrayList<simst>();
//                ss.setName(Name);
//                ss.setDate(Date);
//                ss.setLoc(loc);
//                input.add(ss);
//                double stand = 0;
//                double compar = 0;
//                double numer = 0;
//
//                stand += Math.pow(Double.parseDouble(input.get(0).getDate()), 2);
//                stand += Math.pow(Double.parseDouble(input.get(0).getLoc()), 2);
//                for (int i = 0; i < a.size(); i++) {
//                    if (input.get(0).getName().equals(a.get(i).getName())) {
//                        compar += Math.pow(Double.parseDouble(a.get(i).getDate()), 2);
//                        compar += Math.pow(Double.parseDouble(a.get(i).getLoc()), 2);
//                        numer += (Double.parseDouble(a.get(i).getDate()) * Double.parseDouble(input.get(0).getDate()));
//                        numer += (Double.parseDouble(a.get(i).getLoc()) * Double.parseDouble(input.get(0).getLoc()));
//                        double denominator = Math.sqrt(compar) * Math.sqrt(stand);
//                        double outcome = numer / denominator;
//                        resultST st = new resultST();
//                        st.setName(a.get(i).getId());
//                        st.setMeasure(outcome);
//                        re.add(st);
//                    }
//                    compar = 0;
//                    numer = 0;
//                }
//                Collections.sort(re);
//                String bb = String.valueOf(re.get((re.size()-1)).getName());
//                TextView profile = (TextView) findViewById(R.id.idtest);
//                profile.setText(bb);
//
//            }
//
//        });
//
//
//
//
//    }
//
//
//    protected void showList() {
//        try {
//            JSONObject jsonObj = new JSONObject(myJSON);
//            peoples = jsonObj.getJSONArray(TAG_RESULTS);
//
//            for (int i = 0; i < peoples.length(); i++) {
//                JSONObject c = peoples.getJSONObject(i);
//                String id = c.getString(TAG_ID);
//                String name = c.getString(TAG_NAME);
//                String location = c.getString(TAG_ADD);
//                String date = c.getString(TAG_Date);
//                HashMap<String, String> persons = new HashMap<String, String>();
//                simst ss = new simst();
//                ss.setId(id);
//                ss.setName(name);
//                ss.setDate(date);
//                ss.setLoc(location);
//                a.add(ss);
//    /*            persons.put(TAG_ID, id);
//                persons.put(TAG_NAME, name);
//                persons.put(TAG_ADD, location);
//                persons.put(TAG_Date, date);
//
//                personList.add(persons);*/
//            }
//
//           /* ListAdapter adapter = new SimpleAdapter(
//                    sumin.this, personList, R.layout.list_item,
//                    new String[]{TAG_ID, TAG_NAME, TAG_ADD, TAG_Date},
//                    new int[]{R.id.id, R.id.name, R.id.location, R.id.Date}
//            );
//
//            list.setAdapter(adapter);
//*/
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public void getData(String url) {
//        class GetDataJSON extends AsyncTask<String, Void, String> {
//
//            @Override
//            protected String doInBackground(String... params) {
//
//                String uri = params[0];
//
//                BufferedReader bufferedReader = null;
//                try {
//                    URL url = new URL(uri);
//                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
//                    StringBuilder sb = new StringBuilder();
//
//                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
//
//                    String json;
//                    while ((json = bufferedReader.readLine()) != null) {
//                        sb.append(json + "\n");
//                    }
//
//                    return sb.toString().trim();
//
//                } catch (Exception e) {
//                    return null;
//                }
//
//
//            }
//
//            @Override
//            protected void onPostExecute(String result) {
//                myJSON = result;
//                showList();
//            }
//        }
//        GetDataJSON g = new GetDataJSON();
//        g.execute(url);
//    }
//
//}