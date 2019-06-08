package com.example.myapplication.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import com.example.myapplication.Board.BolistAct;
import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ManagementActivity2 extends AppCompatActivity {


    private ListView listView2;

    private UserListAdapter2 adapter2;

    private List<User2> userList2;

    private List<User2> saveList2;//회원검색 기능용 복사본


    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.userlist);

/*        TextView userListTextView = (TextView)findViewById(R.id.listView);


        //ManagementActivity는 MainActivity에서 호출되므로 호출시 넘겨준 데이터를 뿌려주는 역할을 한다

        Intent intent = getIntent();

        //intent.putExtra("userList", result); 에서 userList에 저장했으므로 아래와 같이 쓰게됨

        userListTextView.setText(intent.getStringExtra("userList"));*/


        Intent intent = getIntent();


        listView2 = (ListView)findViewById(R.id.listView);

        userList2 = new ArrayList<User2>();

        saveList2 = new ArrayList<User2>();


        //어댑터 초기화부분 userList와 어댑터를 연결해준다.

        //회원 삭제 및 관리자 기능 아래 부분 수정됨

        //adapter = new UserListAdapter(getApplicationContext(), userList);

        adapter2 = new UserListAdapter2(getApplicationContext(), userList2, this, saveList2);//로 수정됨

        listView2.setAdapter(adapter2);


        try{

            //intent로 값을 가져옵니다 이때 JSONObject타입으로 가져옵니다

            JSONObject jsonObject = new JSONObject(intent.getStringExtra("userList2"));



            //List.php 웹페이지에서 response라는 변수명으로 JSON 배열을 만들었음..

            JSONArray jsonArray = jsonObject.getJSONArray("response");

            int count = 0;


            String id, passwd, name, gender, num;


            //JSON 배열 길이만큼 반복문을 실행

            while(count < jsonArray.length()){

                //count는 배열의 인덱스를 의미

                JSONObject object = jsonArray.getJSONObject(count);


                id = object.getString("id");//여기서 ID가 대문자임을 유의

                passwd = object.getString("passwd");

                name = object.getString("name");

               num = object.getString("num");
                gender = object.getString("gender");


                //값들을 User클래스에 묶어줍니다

                User2 user2 = new User2(id, passwd, name, num, gender);


                if(!id.equals("admin"))//관리자계정은 삭제하지않게 하기위해서 씀

                {

                    userList2.add(user2);//리스트뷰에 값을 추가해줍니다

                    saveList2.add(user2);//여기도 똑같이 값을 추가해줍니다. 회원검색기능용

                }

                count++;

            }



        }catch(Exception e){

            e.printStackTrace();

        }


        EditText search = (EditText)findViewById(R.id.search);

        search.addTextChangedListener(new TextWatcher() {

            @Override

            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }


            @Override

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                searchUser(charSequence.toString());//회원 검색 기능용

            }


            @Override

            public void afterTextChanged(Editable editable) {


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
                Intent intent = new Intent( ManagementActivity2.this, LoginActivity.class);

                startActivity( intent );
                break;
            case R.id.home:
                Intent intent2 = new Intent( ManagementActivity2.this, BolistAct.class);

                startActivity( intent2 );
        }
        return super.onOptionsItemSelected(item);
    }


    public void searchUser(String search){

        userList2.clear();

        for(int i = 0; i < saveList2.size(); i++){

            if(saveList2.get(i).getId().contains(search) || saveList2.get(i).getNum().contains(search) || saveList2.get(i).getGender().contains(search) || saveList2.get(i).getName().contains(search)){//contains메소드로 search 값이 있으면 true를 반환함

                userList2.add(saveList2.get(i));

            }

        }

        adapter2.notifyDataSetChanged();//어댑터에 값일 바뀐것을 알려줌
    }
}