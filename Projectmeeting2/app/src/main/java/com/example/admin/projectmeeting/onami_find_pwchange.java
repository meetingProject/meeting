package com.example.admin.projectmeeting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by admin on 2015-02-24.
 */
public class onami_find_pwchange extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onami_find_pwchange);
    }

    //비밀번호를 변경한다. 완료버튼을 누르면 다시 로그인화면으로 이동한다.
    public void onClick(View view){
        Intent intentMoveJoin=new Intent(this,MainActivity.class);
        EditText passWord = (EditText) findViewById(R.id.id_newPassword);
        EditText passWordAgain = (EditText) findViewById(R.id.id_newPasswordConfirm);
        String a = passWord.getText().toString();
        String b = passWordAgain.getText().toString();

        switch(view.getId()) {
            case R.id.btn_moveToLogin:
                if(a.length()<7)
                    Toast.makeText(this, "비밀번호 7자리 이상 입력해주세요!", Toast.LENGTH_SHORT).show();
                else if (!a.equals(b))
                    Toast.makeText(this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                else
                    startActivity(intentMoveJoin);

                break;
        }
    }
}
