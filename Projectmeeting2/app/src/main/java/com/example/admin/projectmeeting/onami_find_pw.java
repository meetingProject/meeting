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
 * Created by admin on 2015-02-17.
 */
public class onami_find_pw extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onami_find_pw);
    }

    //아이디찾기와 똑같이 전 화면에서 찾은 아이디 학번 비밀번호 순으로 입력
    public void onClick(View view) {
        Intent intentChangePassword=new Intent(this,onami_find_pwchange.class);
           switch(view.getId()){
               case R.id.id_findfindId:
                   popup(R.id.id_findfindId,"아이디를 입력하세요");
                   break;
               case R.id.id_findRegistrationNumber:
                   popup(R.id.id_findRegistrationNumber,"학번을 입력하세요");
                   break;
               case R.id.btn_Next:
                   startActivity(intentChangePassword);
                   break;
           }

    }
    public void popup(final int id, String title) {
        Context mContext = getApplicationContext();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

        //R.layout.dialog는 xml 파일명이고  R.id.popup은 보여줄 레이아웃 아이디
        View layout = inflater.inflate(R.layout.activity_popuppersonal, (ViewGroup) findViewById(R.id.popupId));
        AlertDialog.Builder aDialog = new AlertDialog.Builder(onami_find_pw.this);

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
}
