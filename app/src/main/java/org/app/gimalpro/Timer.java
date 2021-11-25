package org.app.gimalpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Timer extends AppCompatActivity {  TextView myOutput;
    TextView tv_cal;
    Button bt_start;
    Button bt_pause,bt_end;
    DBHelper_body dbHelper_body;
    private ArrayList<Bodyitem> bodyitems;
    Double user_Kcal;

    int sec,min,mis;
    //시간이 남으면 시간도 적용
    Double sec_kcal;

    final static int Start =0;
    final static int Pause =1;

    int cur_Status = Start; //현재의 상태를 저장할변수를 초기화함.
    int myCount=1;
    long myBaseTime;
    long myPauseTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_timer);

        dbHelper_body=new DBHelper_body(this);

        Intent intent = getIntent();
        sec_kcal=intent.getDoubleExtra("kcal",0.5);
        myOutput = findViewById(R.id.time_out);
        tv_cal=findViewById(R.id.tv_cal);
        bt_start = findViewById(R.id.bt_start);
        bt_pause = findViewById(R.id.bt_pause);
        bt_end=findViewById(R.id.bt_end);
        bt_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long now = SystemClock.elapsedRealtime();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                myBaseTime += (now- myPauseTime);
                intent.putExtra("f2","f2");
                bodyitems = dbHelper_body.selectBody();
                try {
                    user_Kcal=bodyitems.get(bodyitems.size()-1).getKcal();
                } catch (ArrayIndexOutOfBoundsException e){
                    user_Kcal=0.0;
                }
                user_Kcal+=getCal();
                dbHelper_body.updateKcal(MainActivity.UserID,user_Kcal);
                finish();
            }
        });





    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void myOnClick(View v){
        switch(v.getId()){
            case R.id.bt_start: //시작버튼을 클릭했을때 현재 상태값에 따라 다른 동작을 할수있게끔 구현.
                switch(cur_Status){
                    case Start:
                        //myTimer이라는 핸들러를 빈 메세지를 보내서 호출
                        myTimer.sendEmptyMessage(0);
                        myBaseTime = SystemClock.elapsedRealtime();
                        System.out.println(myBaseTime);
                        cur_Status = Start; //현재상태를 런상태로 변경
                        bt_start.setEnabled(false);
                        bt_end.setEnabled(false);
                        break;
                    case Pause:
                        long now = SystemClock.elapsedRealtime();
                        myTimer.sendEmptyMessage(0);
                        myBaseTime += (now- myPauseTime);
                        cur_Status=Start;
                        bt_pause.setText("멈춤");
                        bt_start.setEnabled(false);
                        bt_end.setEnabled(false);
                        break;



                }
                break;
            case R.id.bt_pause:
                switch(cur_Status){
                    case Start:
                        myTimer.removeMessages(0); //핸들러 메세지 제거
                        myPauseTime = SystemClock.elapsedRealtime();
                        tv_cal.setText(String.format("%.1f %s",getCal(),"kcal"));
                        myCount++; //카운트 증가
                        cur_Status = Pause;
                        bt_start.setEnabled(true);
                        bt_pause.setText("초기화");
                        bt_end.setEnabled(true);
                        break;

                    case Pause:
                        myTimer.removeMessages(0);
                        myOutput.setText("00:00:00");
                        tv_cal.setText("0 kcal");
                        cur_Status = Start;
                        bt_pause.setText("멈춤");
                        bt_end.setEnabled(true);
                        bt_start.setEnabled(true);


                }
                break;

        }
    }



    Handler myTimer = new Handler(){
        public void handleMessage(Message msg){
            myOutput.setText(getTimeOut());
            tv_cal.setText(String.format("%.1f %s",getCal(),"kcal"));
            //sendEmptyMessage 는 비어있는 메세지를 Handler 에게 전송하는겁니다.
            myTimer.sendEmptyMessage(0);
        }
    };



    //현재시간을 계속 구해서 출력하는 메소드
    String getTimeOut(){
        long now = SystemClock.elapsedRealtime(); //애플리케이션이 실행되고나서 실제로 경과된 시간(??)^^;
        long outTime = now - myBaseTime;
        sec= (int) ((outTime/1000)%60);
        min= (int) (outTime/1000/60);
        mis= (int)((outTime%1000)/10);
        String easy_outTime = String.format("%02d:%02d:%02d", min, sec, mis);
        return easy_outTime;

    }
    Double getCal(){
        long now = SystemClock.elapsedRealtime(); //애플리케이션이 실행되고나서 실제로 경과된 시간;
        long outTime = now - myBaseTime;
        int time = (int) ((outTime/1000)%60);
        Double cal=time*sec_kcal;
        return cal;
    }

}