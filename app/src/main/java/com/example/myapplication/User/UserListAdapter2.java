package com.example.myapplication.User;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;

import org.json.JSONObject;

import java.util.List;


public class UserListAdapter2 extends BaseAdapter {

    private Context context2;
    private List<User2> userList2;
    private Activity parentActivity;//회원삭제 강의때 추가
    private List<User2> saveList2;


    //여기서 Actvitivy parentActivity가 추가됨 회원삭제 및 관리자기능 예제

    public UserListAdapter2(Context context2, List<User2> userList2, Activity parentActivity, List<User2> saveList2){
        this.context2 = context2;

        this.userList2 = userList2;

        this.parentActivity = parentActivity;//회원삭제 강의때 추가

        this.saveList2 = saveList2;//회원검색 강의때 추가

    }


    //출력할 총갯수를 설정하는 메소드

    @Override
    public int getCount() {
        return userList2.size();
    }


    //특정한 유저를 반환하는 메소드

    @Override
    public Object getItem(int i) {
        return userList2.get(i);
    }
    //아이템별 아이디를 반환하는 메소드

    @Override

    public long getItemId(int i) {
        return i;
    }


    //가장 중요한 부분

    //int i 에서 final int i 로 바뀜 이유는 deleteButton.setOnClickListener에서 이 값을 참조하기 때문

    @Override

    public View getView(final int i, View view, ViewGroup viewGroup) {

        View v = View.inflate(context2, R.layout.userlist_tool, null);


        //뷰에 다음 컴포넌트들을 연결시켜줌

        //final추가 안붙이면 에러남 리스너로 전달하고 싶은 지역변수는 final로 처리해야됨

        final TextView id = (TextView)v.findViewById(R.id.userID);
        final TextView passwd= (TextView)v.findViewById(R.id.userPassword);
        final TextView name = (TextView)v.findViewById(R.id.userName);
        final TextView num = (TextView)v.findViewById(R.id.userNum);
        final TextView gender = (TextView)v.findViewById(R.id.userGender);


        id.setText(userList2.get(i).getId());
        passwd.setText(userList2.get(i).getPasswd());
        name.setText(userList2.get(i).getName());
        gender.setText(userList2.get(i).getGender());
        num.setText(userList2.get(i).getNum());

        //이렇게하면 findViewWithTag를 쓸 수 있음 없어도 되는 문장임

        // v.setTag(userList.get(i).getId());
        //   v.setTag(userList.get(i).getType());
        //v.setTag(userList.get(i).getMoney());
        //    v.setTag(userList.get(i).getMemo());
        //    v.setTag(userList.get(i).getDate());

        //삭제 버튼 객체 생성
        Button deleteButton = (Button)v.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //4. 콜백 처리부분(volley 사용을 위한 ResponseListener 구현 부분)
                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override

                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            //받아온 값이 success면 정상적으로 서버로부터 값을 받은 것을 의미함
                            if(success){
                                userList2.remove(i);//리스트에서 해당부분을 지워줌
                                //saveList는 찾아서 해줘야됨 이게 기준이기 때문임
                                for(int i = 0; i < saveList2.size(); i++){
                                    //  if(saveList.get(i).getId().equals(id.getText().toString()) && saveList.get(i).getType().equals(type.getText().toString())){
                                    if(saveList2.get(i).getId().equals(id.getText().toString())){
                                        saveList2.remove(i);
                                        break;
                                    }
                                }
                                notifyDataSetChanged();//데이터가 변경된 것을 어댑터에 알려줌
                            }
                        }

                        catch(Exception e){
                            e.printStackTrace();
                        }
                    }

                };


                //volley 사용법

                //1. RequestObject를 생성한다. 이때 서버로부터 데이터를 받을 responseListener를 반드시 넘겨준다.

                //위에서 userID를 final로 선언해서 아래 처럼 가능함

                DeleteRequest2 deleteRequest = new DeleteRequest2(id.getText().toString(), responseListener);

                //2. RequestQueue를 생성한다.

                //여기서 UserListAdapter는 Activity에서 상속받은 클래스가 아니므로 Activity값을 생성자로 받아서 사용한다

                RequestQueue queue = Volley.newRequestQueue(parentActivity);

                //3. RequestQueue에 RequestObject를 넘겨준다.

                queue.add(deleteRequest);

            }//onclick

        });



        //만든뷰를 반환함

        return v;

    }

}