package com.example.admin.projectmeeting;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

/**
 * 전체적인 탭뷰 레이아웃을 고정시키고
 * 이동부분을 history로 저장하여 움직인다.
 */

public class TabMeetingRoomActivity extends ActivityGroup {
    public static TabMeetingRoomActivity MeetingRoomGroup;
    private ArrayList<View> history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        history = new ArrayList<View>(); // View 부분을 배열리스트로 저장할 변수
        MeetingRoomGroup = this;

        // 첫 번째 액티비티는 미팅룸 옵션[ 방 생성/ 방 참가]을 선택할 수 있는 class로 연결시킨다.
        /* -- 연결 부분 */
        Intent intent = new Intent(TabMeetingRoomActivity.this, onami_selectoption.class);
        View view = getLocalActivityManager().startActivity("MeetingRoomActivity", intent
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
        replaceView(view);
        /* 연결 종료 -- */
    }

    // Back Key가 눌러졌을 경우에 대한 처리
    public void back() {
        if (history.size() > 0) { // 이전의 history가 있다면
            history.remove(history.size() - 1);
            if (history.size() == 0) // history가 없다면 동작없음
                finish();
            else
                setContentView(history.get(history.size() - 1)); // 이전의 history 화면으로 보내준다.
        } else { // 이전의 history가 없다면 동작없음
            finish();
        }
    }

    // Back Key에 대한 Event Handler
    @Override
    public void onBackPressed() {
        MeetingRoomGroup.back();
        return;
    }

    // 새로운 Level의 Activity를 추가하는 경우
    public void replaceView(View view) {
        history.add(view);
        setContentView(view);
    }
}