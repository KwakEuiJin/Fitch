package org.app.gimalpro;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Random;

public class fragment2 extends Fragment {
    private Bodyitem bodyitem;
    private DBHelper_body dbHelper_body;
    private ArrayList<Bodyitem> bodyitems;
    private View view;
    String user_gender="남성"; // 디폴트값
    Double userHeight=172.0; // 디폴트값
    Double userWeight=50.0; //디폴트값 - setprogressbar()에서 키와 몸무게 받음
    Double user_kcal=0.0;
    int user_age=21;
    ProgressBar progressBar;
    //임시
    TextView textView1,textView2;

    ImageButton bt_running, bt_weighttraing, bt_pushup, bt_squat, bt_witmom; // 무산소 운동
    ImageButton bt_cycle, bt_jul, bt_mountin, bt_walking;
    LinearLayout btn_cycle, btn_jul, btn_mountin, btn_walking,btn_running, btn_weighttraing, btn_pushup, btn_squat, btn_witmom;    // 유산소 운동

    TextView text_view_explain;


    @Nullable
    @Override



    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment2, container, false);
        //db 객체 선언
        dbHelper_body=new DBHelper_body(getContext());

        //무산소
        bt_weighttraing = view.findViewById(R.id.bt_weighttraining);
        bt_pushup = view.findViewById(R.id.bt_pushup);
        bt_squat = view.findViewById(R.id.bt_squat);
        bt_witmom = view.findViewById(R.id.bt_witmom);

        // 유산소 운동 종류
        bt_running = view.findViewById(R.id.bt_running);
        bt_cycle = view.findViewById(R.id.bt_cycle);
        bt_jul = view.findViewById(R.id.bt_jul);
        bt_mountin = view.findViewById(R.id.bt_mountin);
        bt_walking = view.findViewById(R.id.bt_walking);


        try {
            bodyitems = dbHelper_body.selectBody();
            userWeight = bodyitems.get(bodyitems.size()-1).getWeight();
            user_kcal = bodyitems.get(bodyitems.size()-1).getKcal();
            userHeight = bodyitems.get(bodyitems.size()-1).getHeight();

        } catch (ArrayIndexOutOfBoundsException e){

        }

        bt_gone();
        buttonstart();

        setProgressBar();


        //버튼이벤트
        try {
            //달리기
            btn_running.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),Timer.class);
                    Double kcal=(8.0*userWeight)/3600;
                    intent.putExtra("kcal",kcal);
                    startActivity(intent);

                }
            });
            //그 외 근력운동
            btn_weighttraing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),Timer.class);
                    Double kcal=(5.5*userWeight)/3600;
                    intent.putExtra("kcal",kcal);

                    startActivity(intent);
                }
            });
            //푸쉬업
            btn_pushup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),Timer.class);
                    Double kcal=(6.0*userWeight)/3600;
                    intent.putExtra("kcal",kcal);
                    startActivity(intent);
                }
            });
            //스쿼트
            btn_squat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),Timer.class);
                    Double kcal=(6.0*userWeight)/3600;
                    intent.putExtra("kcal",kcal);
                    startActivity(intent);
                }
            });
            //사이클
            btn_cycle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),Timer.class);
                    Double kcal=(5.5*userWeight)/3600;
                    intent.putExtra("kcal",kcal);
                    startActivity(intent);
                }
            });


            //조깅
            btn_walking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),Timer.class);
                    Double kcal=(6.0*userWeight)/3600;
                    intent.putExtra("kcal",kcal);
                    startActivity(intent);
                }
            });
        }
        catch (ArrayIndexOutOfBoundsException e){
            Toast.makeText(getContext(), "신체정보를 입력해주세요", Toast.LENGTH_SHORT).show();
        }




        return view;
    }




    //버튼이벤트
    public void buttonstart(){
        bodyitem = new Bodyitem();

        String str_muscle_lvl = "";
        String str_fat_lvl = "";

        // switch가 ON 상태일 때

        bodyitems = dbHelper_body.selectBody();
        try {
            str_muscle_lvl = Integer.toString(bodyitems.get(bodyitems.size() - 1).getMuscle_level());
            str_fat_lvl = Integer.toString(bodyitems.get(bodyitems.size() - 1).getFat_level());
            Toast.makeText(getContext(), "근육레벨: " + str_muscle_lvl + " 지방레벨: " + str_fat_lvl, Toast.LENGTH_LONG).show();


        }catch (ArrayIndexOutOfBoundsException e){
            Toast.makeText(getContext(), "신체정보를 입력하시오", Toast.LENGTH_SHORT).show();
        }

        //유산소+무산소 4개 추천하기 위해 a라는 배열에 4개의 랜덤 숫자 생성 (ex. 1 3 2 4, 1 4 2 3, ...)
        int count = 4; // 난수 생성 갯수
        int rand_list[] = new int[count];
        Random r = new Random();

        for(int i=0; i<count; i++){
            rand_list[i] = r.nextInt(4) + 1; // 1 ~ 4까지의 난수
            for(int j=0; j<i; j++){
                if(rand_list[i] == rand_list[j]){
                    i--;
                }
            }
        }


        // 근육 레벨과 지방 레벨 비교 후 3가지 case에 따라 알맞은 운동 조합 추천
        // 1. 근육 레벨 == 지방 레벨 -> (무산소2, 유산소2)
        try {
            if(Integer.parseInt(str_muscle_lvl)==Integer.parseInt(str_fat_lvl)){

                //특이케이스 처리 - 근육량, 지방량이 모두 0인 경우
                if(Integer.parseInt(str_muscle_lvl)==0 && Integer.parseInt(str_fat_lvl)==0){
                    text_view_explain.setText("당신은 근육량과 지방량이 모두 부족합니다. \n먼저 무산소 운동 세 개, 유산소 운동 한 개를 추천해줄게요");
                    text_view_explain.setTextSize(20);

                    //첫번째, 두번째, 세번째로 나온 랜덤숫자는 추천할 무산소운동의 index
                    for(int i=0; i< 3; i++){
                        if(rand_list[i]==1){
                           btn_running.setVisibility(View.VISIBLE);
                        }
                        else if(rand_list[i]==2){
                            btn_weighttraing.setVisibility(View.VISIBLE);
                        }
                        else if(rand_list[i]==3){
                            btn_pushup.setVisibility(View.VISIBLE);
                        }
                        else if(rand_list[i]==4){
                            btn_squat.setVisibility(View.VISIBLE);
                        }
                    }

                    //네번째로 나온 랜덤숫자는 추천할 유산소운동의 index
                    for(int i= 3; i< count; i++){
                        if(rand_list[i]==1){
                            btn_cycle.setVisibility(View.VISIBLE);
                            btn_running.setVisibility(View.VISIBLE);
                        }
                        else if(rand_list[i]==2){
                           btn_jul.setVisibility(View.VISIBLE);
                        }
                        else if(rand_list[i]==3){
                           btn_mountin.setVisibility(View.VISIBLE);
                        }
                        else if(rand_list[i]==4){
                            btn_walking.setVisibility(View.VISIBLE);
                        }
                    }

                }

                //특이케이스 처리 - 근육량, 지방량이 모두 2인 경우
                else if(Integer.parseInt(str_muscle_lvl)== 2 && Integer.parseInt(str_fat_lvl)==2){
                    text_view_explain.setText("근육량과 지방량이 많습니다. \n균형을 위해 먼저 유산소 운동 네 개를 추천해줄게요");
                    text_view_explain.setTextSize(20);

                    //세번째, 네번째로 나온 랜덤숫자는 추천할 유산소운동의 index
                    for(int i= 0; i< count; i++){
                        if(rand_list[i]==1){
                            btn_cycle.setVisibility(View.VISIBLE);
                            btn_running.setVisibility(View.VISIBLE);
                        }
                        else if(rand_list[i]==2){
                            btn_jul.setVisibility(View.VISIBLE);
                        }
                        else if(rand_list[i]==3){
                           btn_mountin.setVisibility(View.VISIBLE);
                        }
                        else if(rand_list[i]==4){
                            btn_walking.setVisibility(View.VISIBLE);
                        }
                    }
                }

                //근육량, 지방량이 모두 1인 경우
                else {
                    text_view_explain.setText("당신은 근육량과 지방량의 균형이 알맞습니다.\n무산소 운동 두 개, 유산소 운동 두 개를 추천해줄게요");
                    text_view_explain.setTextSize(20);
                    //첫번째, 두번째로 나온 랜덤숫자는 추천할 무산소운동의 index
                    for (int i = 0; i < 2; i++) {
                        if (rand_list[i] == 1) {
                           btn_witmom.setVisibility(View.VISIBLE);
                        } else if (rand_list[i] == 2) {
                           btn_weighttraing.setVisibility(View.VISIBLE);
                        } else if (rand_list[i] == 3) {
                            btn_pushup.setVisibility(View.VISIBLE);
                        } else if (rand_list[i] == 4) {
                            btn_squat.setVisibility(View.VISIBLE);
                        }
                    }

                    //세번째, 네번째로 나온 랜덤숫자는 추천할 유산소운동의 index
                    for (int i = 2; i < count; i++) {
                        if(rand_list[i]==1){
                            btn_cycle.setVisibility(View.VISIBLE);
                            btn_running.setVisibility(View.VISIBLE);
                        }
                        else if(rand_list[i]==2){
                            btn_jul.setVisibility(View.VISIBLE);
                        }
                        else if(rand_list[i]==3){
                            btn_mountin.setVisibility(View.VISIBLE);
                        }
                        else if(rand_list[i]==4){
                            btn_walking.setVisibility(View.VISIBLE);
                        }
                    }
                }

            }

            // 2. 근육 레벨 > 지방 레벨  -> (무산소3, 유산소1)
            else if(Integer.parseInt(str_muscle_lvl) > Integer.parseInt(str_fat_lvl)){

                text_view_explain.setText("당신은 상대적으로 지방량이 적습니다.\n무산소 운동 세 개, 유산소 운동 한 개를 추천해줄게요");
                text_view_explain.setTextSize(20);

                //첫번째, 두번째, 세번째로 나온 랜덤숫자는 추천할 무산소운동의 index
                for(int i=0; i< 3; i++){
                    if (rand_list[i] == 1) {
                        btn_witmom.setVisibility(View.VISIBLE);
                    } else if (rand_list[i] == 2) {
                        btn_weighttraing.setVisibility(View.VISIBLE);
                    } else if (rand_list[i] == 3) {
                        btn_pushup.setVisibility(View.VISIBLE);
                    } else if (rand_list[i] == 4) {
                        btn_squat.setVisibility(View.VISIBLE);
                    }
                }

                //네번째로 나온 랜덤숫자는 추천할 유산소운동의 index
                for(int i= 3; i< count; i++){
                    if(rand_list[i]==1){
                        btn_cycle.setVisibility(View.VISIBLE);
                        btn_running.setVisibility(View.VISIBLE);
                    }
                    else if(rand_list[i]==2){
                        btn_jul.setVisibility(View.VISIBLE);
                    }
                    else if(rand_list[i]==3){
                        btn_mountin.setVisibility(View.VISIBLE);
                    }
                    else if(rand_list[i]==4){
                        btn_walking.setVisibility(View.VISIBLE);
                    }
                }
            }

            // 3. 근육 레벨 < 지방 레벨  -> (무산소1, 유산소3)
            else if(Integer.parseInt(str_muscle_lvl) < Integer.parseInt(str_fat_lvl)){

                text_view_explain.setText("당신은 상대적으로 지방량이 많습니다.\n 무산소 운동 한 개, 유산소 운동 세 개를 추천해줄게요");
                text_view_explain.setTextSize(20);

                //첫번째로 나온 랜덤숫자는 추천할 무산소운동의 index
                for(int i=0; i< 1; i++){
                    if (rand_list[i] == 1) {
                        btn_witmom.setVisibility(View.VISIBLE);
                    } else if (rand_list[i] == 2) {
                        btn_weighttraing.setVisibility(View.VISIBLE);
                    } else if (rand_list[i] == 3) {
                        btn_pushup.setVisibility(View.VISIBLE);
                    } else if (rand_list[i] == 4) {
                        btn_squat.setVisibility(View.VISIBLE);
                    }
                }

                //두번째, 세번째, 네번째로 나온 랜덤숫자는 추천할 유산소운동의 index
                for(int i= 1; i< count; i++){
                    if(rand_list[i]==1){
                        btn_cycle.setVisibility(View.VISIBLE);
                        btn_running.setVisibility(View.VISIBLE);
                    }
                    else if(rand_list[i]==2){
                        btn_jul.setVisibility(View.VISIBLE);
                    }
                    else if(rand_list[i]==3){
                        btn_mountin.setVisibility(View.VISIBLE);
                    }
                    else if(rand_list[i]==4){
                        btn_walking.setVisibility(View.VISIBLE);
                    }
                }
            }
        } catch (NumberFormatException e){
            Toast.makeText(getContext(), "신체정보를 입력하시면 운동을 추천해드립니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            bodyitems = dbHelper_body.selectBody();
            userWeight = bodyitems.get(bodyitems.size()-1).getWeight();
            user_kcal = bodyitems.get(bodyitems.size()-1).getKcal();
            setProgressBar();
        } catch (ArrayIndexOutOfBoundsException e){

        }
    }

    public void bt_gone(){
        //무산소
        btn_running = view.findViewById(R.id.btn_running);
        btn_weighttraing = view.findViewById(R.id.btn_weighttraining);
        btn_pushup = view.findViewById(R.id.btn_pushup);
        btn_squat = view.findViewById(R.id.btn_squat);
        btn_witmom = view.findViewById(R.id.btn_witmom);

        // 유산소 운동 종류
        btn_cycle = view.findViewById(R.id.btn_cycle);
        btn_jul = view.findViewById(R.id.btn_jul);
        btn_mountin = view.findViewById(R.id.btn_mountin);
        btn_walking = view.findViewById(R.id.btn_walking);

        // 설명창
        text_view_explain = view.findViewById(R.id.TextViewExplain);

        btn_witmom.setVisibility(View.GONE);
        btn_running.setVisibility(View.GONE);
        btn_weighttraing.setVisibility(View.GONE);
        btn_pushup.setVisibility(View.GONE);
        btn_squat.setVisibility(View.GONE);
        btn_cycle.setVisibility(View.GONE);
        btn_jul.setVisibility(View.GONE);
        btn_mountin.setVisibility(View.GONE);
        btn_walking.setVisibility(View.GONE);
    }

    public void setProgressBar(){
        textView1=view.findViewById(R.id.textView1);
        textView2=view.findViewById(R.id.textView2);
        Intent intent = getActivity().getIntent();
        int gicho;
        user_age=intent.getIntExtra("userAge",20);
        user_gender = intent.getStringExtra("userGender");
        Toast.makeText(getContext(), userHeight+" "+userWeight+" "+user_age, Toast.LENGTH_SHORT).show();

        if (user_gender.contains("남성")){
            int hwaldong=35;
            if (((userHeight-99))<=userWeight){
                hwaldong=40;
            }

            else if (((userHeight-99)*0.9)<=userWeight){
                hwaldong=35;
            }

            else {
                hwaldong=30;
            }

            gicho= (int) ((((userHeight-100)*0.9)*hwaldong)-(66.47+(13.75*userWeight)+(5*userHeight)-(6.76*user_age)));
        }
        else {
            gicho= (int) ((((userHeight-100)*0.9)*40)-(655.1+(9.56*userWeight)+(1.85*userHeight)-(4.68*user_age)));
        }


        String percent_view = String.format("%.1f %s",(user_kcal/gicho*100),"%");
        int percent = (int) (user_kcal/1);

        progressBar=view.findViewById(R.id.progressBar);
        progressBar.setProgress(percent);
        progressBar.setMax(gicho);
        textView1.setText(percent_view);
        textView2.setText(String.format("%.2f %s %d %s",user_kcal,"Kcal /",gicho,"Kcal "));
    }

    }







