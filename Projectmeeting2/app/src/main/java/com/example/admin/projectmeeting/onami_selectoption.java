package com.example.admin.projectmeeting;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * 인터페이스 : 옵션 선택 부분 [방 생성/ 방 참가]
 */
public class onami_selectoption extends ActivityGroup {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.onami_selectoption);

        /* -- onami_selectoption.xml 에서 버튼을 따온다 */
        final Button btn_JoinMeetingRoom = (Button) findViewById(R.id.btn_JoinMeetingRoom);
        final Button btn_MakeMeetingRoom = (Button) findViewById(R.id.btn_MakeMeetingRoom);

        /* -- intent 목록 */
        final Intent intent_onami_makeroom = new Intent(onami_selectoption.this,onami_makeroom.class);
        final Intent intent_onami_joinroom = new Intent(onami_selectoption.this,onami_joinroom.class);

        View.OnClickListener listener = new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // 이 곳에 버튼을 눌렀을 때 동작 설정
                switch(view.getId())
                {
                    /* -- 유저 'Make Meeting Room' 선택 시 */
                    case R.id.btn_MakeMeetingRoom :
                        view = TabMeetingRoomActivity.MeetingRoomGroup.getLocalActivityManager()
                                .startActivity("onami_makeroom", intent_onami_makeroom.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                .getDecorView();

                        TabMeetingRoomActivity.MeetingRoomGroup.replaceView(view); // histroy로 연결하는 부분
                        break;

                    /* -- 유저 'Join Meeting Room' 선택 시 */
                    case R.id.btn_JoinMeetingRoom :
                        view = TabMeetingRoomActivity.MeetingRoomGroup.getLocalActivityManager()
                                .startActivity("onami_joinroom", intent_onami_joinroom.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                .getDecorView();

                        TabMeetingRoomActivity.MeetingRoomGroup.replaceView(view); // histroy로 연결하는 부분
                        break;

                }
            }
        };

        /* -- 버튼에 대한 액션리스너 추가 부분 */
        btn_MakeMeetingRoom.setOnClickListener(listener);
        btn_JoinMeetingRoom.setOnClickListener(listener);
    }
}