package com.example.admin.projectmeeting;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by admin on 2015-02-15.
 */
public class onami_find_id extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onami_find_id);
    }
    //아이디를 찾을경우 종정시를 파싱하기 때문에 종정시를 통한 학번과 비밀번호를 입력한다.
    public void onClick(View view){
        Intent intentJoin=new Intent(this,onami_find_pw.class);
        switch (view.getId()){
            case R.id.id_findRegistrationNumber:
                popup(R.id.id_findRegistrationNumber,"학번을 입력하세요.");
                break;
            case R.id.btn_findId:
                informId(R.id.btn_findId,"고객님의 아이디 입니다.");//아이디 찾기버튼을 누르면 사용자의 아이디를 팝업창으로 보여준다
                break;
            case R.id.btn_findPassword:
                startActivity(intentJoin);
                break;
        }
    }

    public void popup(final int id, String title) {
        Context mContext = getApplicationContext();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

        //R.layout.dialog는 xml 파일명이고  R.id.popup은 보여줄 레이아웃 아이디
        View layout = inflater.inflate(R.layout.activity_popuppersonal, (ViewGroup) findViewById(R.id.popupId));
        AlertDialog.Builder aDialog = new AlertDialog.Builder(onami_find_id.this);

        aDialog.setTitle(title); //타이틀바 제목
        aDialog.setView(layout); //dialog.xml 파일을 뷰로 셋팅

        aDialog.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        aDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                EditText edit = (EditText) ((AlertDialog) dialog).findViewById(R.id.enterId);
                TextView text = (TextView) findViewById(id);
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

    //아이디를 알려줄 함수
    public void informId(final int id, String title){
        Context mContext = getApplicationContext();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

        //R.layout.dialog는 xml 파일명이고  R.id.popup은 보여줄 레이아웃 아이디
        View layout = inflater.inflate(R.layout.activity_findidpopup, (ViewGroup) findViewById(R.id.findId));
        AlertDialog.Builder aDialog = new AlertDialog.Builder(onami_find_id.this);

        aDialog.setTitle(title); //타이틀바 제목
        aDialog.setView(layout); //dialog.xml 파일을 뷰로 셋팅
        String a="aaa";
        aDialog.setMessage(a);
        aDialog.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        aDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        //그냥 닫기버튼을 위한 부분
        //팝업창 생성
        AlertDialog ad = aDialog.create();
        ad.show();//보여줌!
    }
}
