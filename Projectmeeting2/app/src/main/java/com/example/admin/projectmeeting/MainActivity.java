package com.example.admin.projectmeeting;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    ArrayList<ListItem> listItem = new ArrayList<ListItem>();

    // 서버에서 제이슨형식 데이터를 받는 클래스
    private phpDown task;
    // 서버에서 제이슨으로 받기 위한 php주소
    final private String urlPath = "http://ec2-54-178-146-165.ap-northeast-1.compute.amazonaws.com/appdata.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        task = new phpDown(); // 인스턴스 생성
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onami_login); // 레이아웃 xml파일 호출
        task.execute(urlPath); // 데이터 받기 송신
    }


    public void onClick(View view)
    {
        /* -- 액티비티 이동을 위한 intent 모음*/
        Intent intent_onami_signup_id = new Intent(this,onami_signup_id.class);
        Intent intent_onami_find_id = new Intent(this,onami_find_id.class);
        Intent intent_onami_tabview = new Intent(this,onami_tabview.class);

        /* -- switch 문을 통해 버튼 R.id.로 따오기 */
        switch(view.getId())
        {
            case R.id.btn_signup:
                startActivity(intent_onami_signup_id);
                break;
            case R.id.btn_findIdandPassWord:
                startActivity(intent_onami_find_id);
                break;
            case R.id.btn_login:
                if(isValidId())
                    startActivity(intent_onami_tabview);
                break;
        }
    }

    /* 입력 받은 id와 password의 유효성을 검사한다.*/
    public boolean isValidId() {

        /* -- EditText에서 입력 받은 값 id/pass word에 저장한다.*/
        EditText et_id = (EditText) findViewById(R.id.edit_id);
        EditText et_password = (EditText) findViewById(R.id.edit_password);
        String input_id = et_id.getText().toString();
        String input_password = et_password.getText().toString();
        /* -- */

        /* -- 입력 받은 값에 대해 유효성을 검사한다.*/
        boolean checkingValid = true; // 유효성에 대해 값을 체킹하는 변수
        if(input_id.length() < 1 ) { // 공백 조사
            Toast.makeText(MainActivity.this, "아이디가 공백입니다.", Toast.LENGTH_SHORT).show();
            checkingValid = false;
        }else if(input_password.length() <1 ) { // 공백 조사
            Toast.makeText(MainActivity.this, "비밀번호가 공백입니다.", Toast.LENGTH_SHORT).show();
            checkingValid = false;
        }else if(input_password.length() < 7) { // 비밀번호가 7자리 이상인지 조사
            Toast.makeText(MainActivity.this,"비밀번호를 7자리 이상 입력해주세요!" ,Toast.LENGTH_SHORT).show();
            checkingValid = false;
        }

        if(checkingValid == false)
            return false;
        /*  유효성 검사 끝 --           */

        /* 유효성을 마치고 입력받은 값을 데이터베이스의 서버 값과 조회를 통해 비교한다. */
        /* 수정해야됨 - MySQL 쿼리의 search 를 통해 조사를 수행해야 한다. 지금은 for문을 통해 동작하고 있음 */
        for (int i = 0; i < listItem.size(); i++) {
            if (listItem.get(i).getData(0).equals(input_id)) {
                if (listItem.get(i).getData(1).equals(input_password)) {
                    Toast.makeText(MainActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                    return true;
                }else {
                    Toast.makeText(MainActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }
        Toast.makeText(MainActivity.this, "가입된 아이디가 없습니다.", Toast.LENGTH_SHORT).show();
        return false;
        /* 조회 끝 -- */
    }

    private class phpDown extends AsyncTask<String, Integer,String> {
        @Override
        protected String doInBackground(String... urls) {
            StringBuilder jsonHtml = new StringBuilder();
            try{
                // 연결 url 설정
                URL url = new URL(urls[0]);
                // 커넥션 객체 생성
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                // 연결되었으면.
                if(conn != null){
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);
                    // 연결되었음 코드가 리턴되면.
                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        for(;;){
                            // 웹상에 보여지는 텍스트를 라인단위로 읽어 저장.
                            String line = br.readLine();
                            if(line == null) break;
                            // 저장된 텍스트 라인을 jsonHtml에 붙여넣음
                            jsonHtml.append(line + "\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                }
            } catch(Exception ex){
                ex.printStackTrace();
            }
            return jsonHtml.toString();
        }

        protected void onPostExecute(String str){
            String id;
            String password;
            try{
                JSONObject root = new JSONObject(str);
                JSONArray ja = root.getJSONArray("results");
                for(int i=0; i<ja.length(); i++){
                    JSONObject jo = ja.getJSONObject(i);
                    id = jo.getString("id");
                    password = jo.getString("password");
                    listItem.add(new ListItem(id,password));
                }
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
    }
}

/* 제이슨 형식으로 받아온 데이터를 ListItem 으로 저장한다.*/
class ListItem {
    private String[] mData; // 제이슨으로 받은 데이터를 저장할 문자열

    /* -- constructor       */
    public ListItem(String[] data ){
        mData = data;
    }

    public ListItem(String id,String password){ //
        mData = new String[2];
        mData[0] = id;
        mData[1] = password;
    }
    /*  construcotr -- */


    public String[] getData(){
        return mData;
    }

    public String getData(int index){
        return mData[index];
    }

    public void setData(String[] data){
        mData = data;
    }

}

