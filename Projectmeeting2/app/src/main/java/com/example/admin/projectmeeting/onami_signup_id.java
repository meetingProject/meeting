package com.example.admin.projectmeeting;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;

// 이벤트리스너의 onItemClick 메소드에서 넘어오는 AdapterView 를 사용하기 위해 추가

/**
 * Created by admin on 2015-02-06.
 */
public class onami_signup_id extends Activity implements View.OnClickListener {
    SendPost sendPost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sendPost = new SendPost();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onami_signup_id);
    }
    @Override
    public void onClick(View view) {
        final Intent intent_Join = new Intent(this,onami_signup_cellphone.class); // 다음화면은 signup_cellphone으로 넘어간다.

        //*밑 4줄은 비밀번호와 비밀번호 확인을 해서 서로 일치한지 비교해주기 위해 둘 모두 String으로 변환하여 비교해준다.
        EditText passWord = (EditText) findViewById(R.id.id_passWord);
        EditText passWordAgain = (EditText) findViewById(R.id.id_passWordAgain);
        String a = passWord.getText().toString();
        String b = passWordAgain.getText().toString();

        //signup_id에 아이디들을 스위치문으로 받아와준다.
        switch (view.getId()) {
            case R.id.Id:
                popup(R.id.Id, "아이디입력");
                break;
            case R.id.id_NickName:
                popup(R.id.id_NickName,"별명을 입력 해주세요");
                break;
            case R.id.id_enterYear:
                popup(R.id.id_enterYear, "입학년도를 4자리로 입력해주세요");
                break;
            case R.id.id_major:
                popup(R.id.id_major,"학과를 입력하세요!");
                break;
            case R.id.btn_agreeJoin:
               if(a.length()<7)
                    Toast.makeText(this,"비밀번호 7자리 이상 입력해주세요!",Toast.LENGTH_SHORT).show();
               else if (!a.equals(b))
                    Toast.makeText(this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
               else {
                   sendPost.execute();
                   startActivity(intent_Join);
               }
               break;
        }

    }
    //밑에는 popup을 만들어주는 함수이다.
    public void popup(final int id, String title) {
        Context mContext = getApplicationContext();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

        //R.layout.dialog는 xml 파일명이고  R.id.popup은 보여줄 레이아웃 아이디
        View layout = inflater.inflate(R.layout.activity_popuppersonal, (ViewGroup) findViewById(R.id.popupId));
        AlertDialog.Builder aDialog = new AlertDialog.Builder(onami_signup_id.this);

        aDialog.setTitle(title); //타이틀바 제목
        aDialog.setView(layout); //dialog.xml 파일을 뷰로 셋팅

        aDialog.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        //밑에는 팝업창에서 정보들을 입력한 후 확인을 누르면 EditText자리에 그대로 String으로 나타내주기 위한 함수이다.
        aDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                EditText edit = (EditText) ((AlertDialog) dialog).findViewById(R.id.enterId); //팝업창에 EditText부분에 아이디
                TextView text = (TextView) findViewById(id);//각 항목에 대한 id를 받아온다.
                String symbolvalue;
                symbolvalue = edit.getText().toString();
                edit.requestFocus();

                text.setText(symbolvalue);
            }
        });
        //그냥 닫기버튼을 위한 부분
        //팝업창 생성
        AlertDialog ad = aDialog.create();
        ad.show();//보여줌!
    }
    private class SendPost extends AsyncTask<Void, Void, String> {
        protected String doInBackground(Void... unused) {
            String content = executeClient();
            return content;
        }

        protected void onPostExecute(String result) {
            // 모두 작업을 마치고 실행할 일 (메소드 등등)
        }

        // 실제 전송하는 부분
        public String executeClient() {
            EditText et_id = (EditText )findViewById(R.id.Id);
            EditText et_nickname = (EditText )findViewById(R.id.id_NickName);
            EditText et_password = (EditText )findViewById(R.id.id_passWord);
            EditText et_enteryear = (EditText )findViewById(R.id.id_enterYear);
            EditText et_major = (EditText )findViewById(R.id.id_major);

            String id = et_id.getText().toString();
            String nickname = et_nickname.getText().toString();
            String password = et_password.getText().toString();
            String enteryear = et_enteryear.getText().toString();
            String major = et_major.getText().toString();

            ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
            post.add(new BasicNameValuePair("id", id));
            post.add(new BasicNameValuePair("nickname", nickname));
            post.add(new BasicNameValuePair("password", password));
            post.add(new BasicNameValuePair("styear", enteryear));
            post.add(new BasicNameValuePair("major", major));

            // 연결 HttpClient 객체 생성
            HttpClient client = new DefaultHttpClient();

            // 객체 연결 설정 부분, 연결 최대시간 등등
            HttpParams params = client.getParams();
            HttpConnectionParams.setConnectionTimeout(params, 5000);
            HttpConnectionParams.setSoTimeout(params, 5000);

            // Post객체 생성
            HttpPost httpPost = new HttpPost("http://ec2-54-178-146-165.ap-northeast-1.compute.amazonaws.com/senddata.php");

            try {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(post, "UTF-8");
                httpPost.setEntity(entity);
                client.execute(httpPost);
                return EntityUtils.getContentCharSet(entity);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

