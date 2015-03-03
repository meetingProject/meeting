package com.example.admin.projectmeeting;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;

/**
 * onami tabview로써, 밑의 탭 부분과 전체 레이아웃을 구성한다.
 */
public class onami_tabview extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onami_tabview);

        Resources res = this.getResources();

        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);

        Intent intent;
        TabHost.TabSpec spec;

        /*
            탭 추가를 원하다면 밑을 따서 수정하도록 한다.
        */

        /* -- 미팅룸 탭 */
        intent = new Intent().setClass(this,TabMeetingRoomActivity.class);
        spec = tabHost.newTabSpec("MeetingRoom").
                setIndicator("미팅룸",res.getDrawable(R.drawable.makemetting)).
                setContent(intent);
        tabHost.addTab(spec);

        /* -- 메신저 탭*/
        intent = new Intent().setClass(this,onami_chat_list.class);
        spec = tabHost.newTabSpec("Messanger").
                setIndicator("메신저",res.getDrawable(R.drawable.message)).
                setContent(intent);
        tabHost.addTab(spec);

        /* -- 세팅 탭 */
        intent = new Intent().setClass(this,onami_setting.class);
        spec = tabHost.newTabSpec("Setting").
                setIndicator("세팅",res.getDrawable(R.drawable.setting)).
                setContent(intent);
        tabHost.addTab(spec);

        /* -- 선택되어진 처음 탭 -> 미팅룸으로 보여지도록 함 */
        tabHost.setCurrentTab(0);
    }
    public void onClick(View view)
    {
        finish();
    }
}
