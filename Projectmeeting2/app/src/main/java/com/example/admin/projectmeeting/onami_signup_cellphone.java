package com.example.admin.projectmeeting;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class onami_signup_cellphone extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onami_signup_cellphone);

        final Intent intentPhone=new Intent(this,onami_signup_univ.class);
        Random random=new Random(System.currentTimeMillis());
        final int secureNumber = random.nextInt(8999)+1000; // 인증번호 전송할 때 숫자 4자리 1000부터 9999까지 선언!

        Button sendBtn = (Button) findViewById(R.id.btn_send);
        Button verifyBtn= (Button)findViewById(R.id.btn_verify);
        EditText et = (EditText) findViewById(R.id.id_phoneNumber);
        final String a=String.valueOf(secureNumber);//인증번호가 일치하는지 알아보기 위해 폰번호를 String으로 변환
        // 핸드폰 번호 따오는 알고리즘
        TelephonyManager systemService = (TelephonyManager)getSystemService    (Context.TELEPHONY_SERVICE);
        String PhoneNumber = systemService.getLine1Number();
        PhoneNumber = PhoneNumber.substring(PhoneNumber.length()-10,PhoneNumber.length());
        PhoneNumber="0"+PhoneNumber;
        // 얻어온 전화번호에 자동으로 하이픈(-) 추가
        PhoneNumber = PhoneNumberUtils.formatNumber(PhoneNumber);
        et.setText(PhoneNumber);
        // 해당 에디트텍스트를 사용자입력 금지시킴
        et.setEnabled(false);

        verifyBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                switch(v.getId()){
                    case R.id.btn_verify:
                        //밑2줄은 문자로 온 인증번호를 String으로 변환
                        TextView number=(TextView)findViewById(R.id.certificateNumber);
                        String b=number.getText().toString();
                        if(a.equals(b)){//자기자신에게 온 4자리번호와 사용자가 입력한 4자리 번호가 같으면 다음화면으로 이동
                            startActivity(intentPhone);
                        }
                        else{
                            Toast.makeText(onami_signup_cellphone.this, "인증번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                        }
                    break;
                }
            }
        });

        //인증번호 전송 버튼을 눌렀을때 실행되는 함수
        sendBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                EditText addrTxt = (EditText) onami_signup_cellphone.this.findViewById(R.id.id_phoneNumber);
                //자신의 핸드폰번호를 EditText로 받아온다.
                try {
                    // 2
                    sendSMS(addrTxt.getText().toString(), String.valueOf(secureNumber));//sendSMS함수에 인자로 핸드폰번호와 인증번호를 입력해준다.

                    Toast.makeText(onami_signup_cellphone.this, "SMS 발송 완료", Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    Toast.makeText(onami_signup_cellphone.this, e.toString(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
    }

    //SMS를 보낼때
    private void sendSMS(String phoneNumber, String message) {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);

        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver(){
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

}



