package com.example.admin.projectmeeting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by user on 2015-02-05.
 */
public class onami_joinroom extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onami_joinroom);

        /* -- onami_joinroom component */
        final Button btn_agreeJoinRoom = (Button) findViewById(R.id.btn_agreeJoinRoom);
        final Button btn_cancleJoinRoom = (Button) findViewById(R.id.btn_cancleJoinRoom);
        final EditText edit_captinNickName = (EditText) findViewById(R.id.id_UserInputMakerId); // 유저가 입력한 대표 아이디
        final EditText edit_room_password = (EditText) findViewById(R.id.id_UserRoomPassword); // 유저가 입력한 대표방 비밀번호

        /* -- onami_joinroom intent list */
        final Intent intent_onami_selectoption = new Intent(this,onami_selectoption.class);
        final Intent intent_onami_maderoom = new Intent(this,onami_maderoom.class);

        View.OnClickListener listener = new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // 이 곳에 버튼을 눌렀을 때 동작 설정
                switch(view.getId())
                {
                    /* -- onami_maderoom 이동*/
                    case R.id.btn_agreeJoinRoom :
                        // 방을 생성합니다.
                        view = TabMeetingRoomActivity.MeetingRoomGroup.getLocalActivityManager()
                                .startActivity("MeetingRoomActivity", intent_onami_maderoom.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                .getDecorView();
                        TabMeetingRoomActivity.MeetingRoomGroup.replaceView(view);
                        break;
                    /* -- onami_selectoption 이동*/
                    case R.id.btn_cancleJoinRoom :
                        // 취소하여 이전 화면으로 돌아갑니다.
                        view = TabMeetingRoomActivity.MeetingRoomGroup.getLocalActivityManager()
                                .startActivity("MeetingRoomActivity", intent_onami_selectoption.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                .getDecorView();
                        TabMeetingRoomActivity.MeetingRoomGroup.replaceView(view);
                        break;

                }
            }
        };
        /* -- 버튼에 액션 리스너 적용 */
        btn_agreeJoinRoom.setOnClickListener(listener);
        btn_cancleJoinRoom.setOnClickListener(listener);

    }
}
