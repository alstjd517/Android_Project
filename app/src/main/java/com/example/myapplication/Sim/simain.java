package com.example.myapplication.Sim;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;


public class simain extends Activity {

    String myJSON;

    private static final String TAG_RESULTS = "result";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_ADD = "location";
    private static final String TAG_Date = "date";
    private static final String TAG_Detail = "Detail";


    JSONArray peoples = null;

    ArrayList<HashMap<String, String>> personList;
    ListView list;

    private EditText mEditText1;
    private EditText mEditText2;
    private EditText mEditText3;
    static ArrayList<simst> a = new ArrayList<simst>();
    static ArrayList<simst> result = new ArrayList<simst>();
    static ArrayList<resultST>  re =   new ArrayList<resultST>();
    static ArrayList<Double>  rlist =   new ArrayList<Double>();
    Double[] words = new Double[4];
    static String bb2;
    static String bb3;
    static String bb4;
    static String bb5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        //list = (ListView) findViewById(R.id.listView);
        personList = new ArrayList<HashMap<String, String>>();
        mEditText1 = (EditText) findViewById(R.id.f1);
        mEditText2 = (EditText) findViewById(R.id.f2);
        mEditText3 = (EditText) findViewById(R.id.f2);

        Button btn = (Button)findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(simain.this,sim.class);
                intent.putExtra("b2",bb2);
                intent.putExtra("b3",bb3);
                intent.putExtra("b4",bb4);
                intent.putExtra("b5",bb5);

                startActivity(intent);
                finish();
            }
        });

        getData("http://203.234.62.111/a/PHP_connectionlist.php"); //수정 필요
        Button buttonInsert = (Button) findViewById(R.id.button);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //             getData("http://203.234.62.111/a/PHP_connection.php"); //수정 필요
                String name = mEditText1.getText().toString();
                String Date = mEditText2.getText().toString();
                String loc = mEditText3.getText().toString();
                simst ss = new simst();
                String[] ll = loc.split("/");
                String[] dd = Date.split("/");
                ArrayList<simst> input = new ArrayList<simst>();
                input.clear();
                ss.setName(name);
                ss.setDate(dd[0]);
                ss.setDate1(dd[1]);
                ss.setDate2(dd[1]);
                ss.setLoc(ll[0]);
                ss.setLoc1(ll[1]);
                input.add(ss);
                double stand = 0;
                double compar = 0;
                double numer = 0;
                double standDate = (Double.parseDouble(input.get(0).getDate1())*30+Double.parseDouble(input.get(0).getDate2()));
                stand = Math.pow(standDate, 2)
                        +Math.pow(Double.parseDouble(input.get(0).getLoc()), 2)
                        +Math.pow(Double.parseDouble(input.get(0).getLoc1()), 2);


                for (int i = 0; i < a.size(); i++) {
                    if (input.get(0).getName().contains(a.get(i).getName())) {
                        double comDate = (Double.parseDouble(a.get(i).getDate1())*30+Double.parseDouble(a.get(i).getDate2()));
                        compar = Math.pow(comDate, 2)
                                + Math.pow(Double.parseDouble(a.get(i).getLoc()), 2)
                                + Math.pow(Double.parseDouble(a.get(i).getLoc1()), 2);

                        numer = (comDate * standDate)
                                + (Double.parseDouble(a.get(i).getLoc()) * Double.parseDouble(input.get(0).getLoc()))
                                + (Double.parseDouble(a.get(i).getLoc1()) * Double.parseDouble(input.get(0).getLoc1()));
                        ;
                        double denominator = (Math.sqrt(compar) * Math.sqrt(stand));
                        double outcome = numer / denominator;



                        if(re.isEmpty()){
                            simst sim = new simst();
                            sim.setName(a.get(i).getName());
                            sim.setLoc(a.get(i).getDate()+"/"+a.get(i).getDate1()+"/"+a.get(i).getDate2());
                            sim.setDate(a.get(i).getLoc()+"/"+a.get(i).getLoc1());
                            sim.setDetail(a.get(i).getDetail());
                            result.add(sim);
                            resultST st = new resultST();
                            st.setName(a.get(i).getId());
                            st.setMeasure(outcome);
                            re.add(st);


                        }else if(!re.isEmpty() && re.get(0).getMeasure()< outcome){
                            re.clear();
                            result.clear();
                            simst sim = new simst();
                            sim.setName(a.get(i).getName());
                            sim.setLoc(a.get(i).getDate()+"/"+a.get(i).getDate1()+"/"+a.get(i).getDate2());
                            sim.setDate(a.get(i).getLoc()+"/"+a.get(i).getLoc1());
                            sim.setDetail(a.get(i).getDetail());
                            result.add(sim);
                            resultST st = new resultST();
                            st.setName(a.get(i).getId());
                            st.setMeasure(outcome);
                            re.add(st);

                        }
                    }


                }

                bb2 = String.valueOf(result.get(0).getName());
                TextView profile2 = (TextView) findViewById(R.id.name);
                profile2.setText(bb2);

                bb3 = String.valueOf(result.get(0).getDate());
                TextView profile3 = (TextView) findViewById(R.id.date);
                profile3.setText(bb3);

                bb4 = String.valueOf(result.get(0).getLoc());
                TextView profile4 = (TextView) findViewById(R.id.loc);
                profile4.setText(bb4);

                bb5 = String.valueOf(result.get(0).getDetail());
                TextView profile5 = (TextView) findViewById(R.id.Detail);
                profile5.setText(bb5);
                result.clear();

                re.clear();
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
                String Detail = c.getString(TAG_Detail);
                HashMap<String, String> persons = new HashMap<String, String>();
                simst ss = new simst();
                String[] loc = location.split("/");
                String[] dat = date.split("/");
                ss.setId(id);
                ss.setName(name);
                ss.setDate(dat[0]);
                ss.setDate1(dat[1]);
                ss.setDate2(dat[2]);
                ss.setLoc(loc[0]);
                ss.setLoc1(loc[1]);
                ss.setDetail(Detail);
                a.add(ss);
            }
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