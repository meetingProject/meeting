package com.example.admin.projectmeeting;

/**
 * Created by admin on 2015-02-25.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by user on 2015-02-05.
 */
public class onami_makeroom extends Activity implements AdapterView.OnItemSelectedListener{
    final String[] member_num = {"3","4","5"}; // 미팅 인원
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.onami_makeroom);

            /* -- onami_makeroom component */
            final Spinner spinner_membernum = (Spinner) findViewById(R.id.spinner_memberNum);
            final Button btn_agreeMakeRoom = (Button) findViewById(R.id.btn_agreeMakeRoom);
            final Button btn_cancleMakeRoom = (Button) findViewById(R.id.btn_cancleMakeRoom);
            final EditText edit_roompassword = (EditText) findViewById(R.id.id_roomPassword);

           /* -- onami_makeroom intent list */
            final Intent intent_onami_selectoption = new Intent(this,onami_selectoption.class);
            final Intent intent_onami_maderoom = new Intent(this,onami_maderoom.class);

            /* -- 입력한 패스워드에 대한 유효성 검사 */
            edit_roompassword.addTextChangedListener(new TextWatcher() {
                String beforeText;
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    beforeText = s.toString();
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}
                @Override
                public void afterTextChanged(Editable s) {
                    if (s.toString().length() > 4) // 유저가 4자리를 입력하려고 할 경우
                        edit_roompassword.setText(beforeText);
                }
            });
            /* 유효성 검사 종료 -- */

            /* -- spinner 설정 부분 */
            spinner_membernum.setVisibility(View.VISIBLE); // spinner를 보이도록 한다.
            ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_item,member_num); //문자열 어댑터 선언
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_membernum.setAdapter(adapter); // 어댑터 적용
            spinner_membernum.setOnItemSelectedListener(this); // 리스너 적용
            /* spinner 설정 종료 -- */


            View.OnClickListener listener = new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    // 이 곳에 버튼을 눌렀을 때 동작 설정
                    switch(view.getId())
                    {
                        /* -- onami_made room 이동  */
                        case R.id.btn_agreeMakeRoom :
                            // 방을 생성합니다.
                            view = TabMeetingRoomActivity.MeetingRoomGroup.getLocalActivityManager()
                                    .startActivity("MakeAMeetingRoomActivity1", intent_onami_maderoom.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                    .getDecorView();
                            TabMeetingRoomActivity.MeetingRoomGroup.replaceView(view);
                            break;
                        /* -- onami_selectoption 이동 */
                        case R.id.btn_cancleMakeRoom :
                            // 취소하여 이전 화면으로 돌아갑니다.
                            view = TabMeetingRoomActivity.MeetingRoomGroup.getLocalActivityManager()
                                    .startActivity("MakeAMeetingRoomActivity1", intent_onami_selectoption.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                    .getDecorView();
                            TabMeetingRoomActivity.MeetingRoomGroup.replaceView(view);
                            break;

                    }
                }
            };

            /* -- 버튼에 액션 리스너 적용*/
            btn_agreeMakeRoom.setOnClickListener(listener);
            btn_cancleMakeRoom.setOnClickListener(listener);
    }

    /* -- back 호출 함수 */
    @Override
    public void onBackPressed() {
        TabMeetingRoomActivity parent = ((TabMeetingRoomActivity)getParent());
        parent.onBackPressed();
    }

    /* -- 스피너 선택시 호출되는 함수*/
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView spinner_select = (TextView)view; //스피너에 보여지고 있는 값
        TextView textview_memebernum = (TextView)findViewById(R.id.text_memberNum); // 선택된 값과 id값을 보내기 위한 텍스트 뷰 선언
        //선택된 값 보기
        textview_memebernum.setText("미팅 인원 :" + spinner_select.getText());
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}

