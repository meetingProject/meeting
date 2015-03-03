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
 * Created by admin on 2015-02-06.
 */
public class onami_signup_univ extends Activity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onami_signup_univ);
    }

    public void onClick(View view){

        Intent intent=new Intent(this,onami_signup_picture.class);
        switch(view.getId())
        {
            case R.id.btn_gotoPicture:
                startActivity(intent);
                break;
            case R.id.id_registrationNumber:
                popup(R.id.id_registrationNumber,"학번을 입력하세요");
                break;
        }
    }
    //학번 입력시 팝업창으로 입력할 수 있도록 했다.
    public void popup(final int id, String title) {
        Context mContext = getApplicationContext();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

        //R.layout.dialog는 xml 파일명이고  R.id.popup은 보여줄 레이아웃 아이디
        View layout = inflater.inflate(R.layout.activity_popuppersonal, (ViewGroup) findViewById(R.id.popupId));
        AlertDialog.Builder aDialog = new AlertDialog.Builder(onami_signup_univ.this);

        aDialog.setTitle(title); //타이틀바 제목
        aDialog.setView(layout); //dialog.xml 파일을 뷰로 셋팅

        aDialog.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        //확인 버튼 클릭 시 누른 학번이 그대로 TextView에 나타나도록 했다.
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
