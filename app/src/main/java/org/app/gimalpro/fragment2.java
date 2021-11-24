package org.app.gimalpro;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
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
    Double user_kcal=0.0;
    //임시
    TextView textView;


    Switch switchstart;
    Button  btnanaerobic, btnaerobic;  // 운동 일지 추가, 무산소 운동, 유산소 운동

    Button btn_running, btn_weighttraing, btn_pushup, btn_squat; // 무산소 운동
    Button btn_cycle, btn_swimming, btn_hiking, btn_walking; // 유산소 운동

    TextView text_view_explain;


    @Nullable
    @Override



    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment2, container, false);

        //임시
        textView=view.findViewById(R.id.textView);

        buttonstart();
        Double userWeight = bodyitems.get(bodyitems.size()-1).getWeight();


        //버튼이벤트
        try {
            //달리기
            btn_running.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),Timer.class);
                    Double kcal=(8.0*userWeight)/3600;
                    intent.putExtra("kcal",kcal);
                    startActivityForResult(intent,0);
                }
            });
            //그 외 근력운동
            btn_weighttraing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),Timer.class);
                    startActivity(intent);
                }
            });
            //푸쉬업
            btn_pushup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),Timer.class);
                    startActivity(intent);
                }
            });
            //스쿼트
            btn_squat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),Timer.class);
                    startActivity(intent);
                }
            });
            //사이클
            btn_cycle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),Timer.class);
                    startActivity(intent);
                }
            });


            //조깅
            btn_walking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),Timer.class);
                    startActivity(intent);
                }
            });
        } catch (ArrayIndexOutOfBoundsException e){
            Toast.makeText(getContext(), "신체정보를 입력해주세요", Toast.LENGTH_SHORT).show();
        }



        return view;
    }




    //버튼이벤트
    public void buttonstart(){
        bodyitem = new Bodyitem();
        dbHelper_body = new DBHelper_body(getContext());

        // 무산소 운동, 유산소 운동
        btnanaerobic = view.findViewById(R.id.btnanaerobic);
        btnaerobic = view.findViewById(R.id.btnaerobic);

        // 무산소 운동 종류
        btn_running = view.findViewById(R.id.btn_running);
        btn_weighttraing = view.findViewById(R.id.btn_weighttraining);
        btn_pushup = view.findViewById(R.id.btn_pushup);
        btn_squat = view.findViewById(R.id.btn_squat);

        // 유산소 운동 종류
        btn_cycle = view.findViewById(R.id.btn_cycle);
        btn_swimming = view.findViewById(R.id.btn_swimming);
        btn_hiking = view.findViewById(R.id.btn_hiking);
        btn_walking = view.findViewById(R.id.btn_walking);

        // 설명창
        text_view_explain = view.findViewById(R.id.TextViewExplain);

        btn_walking.setVisibility(view.VISIBLE);
        btn_hiking.setVisibility(view.VISIBLE);
        btn_swimming.setVisibility(view.VISIBLE);
        btn_running.setVisibility(view.VISIBLE);
        btn_squat.setVisibility(view.VISIBLE);
        btn_pushup.setVisibility(view.VISIBLE);
        btn_weighttraing.setVisibility(view.VISIBLE);
        btn_cycle.setVisibility(view.VISIBLE);

        //버튼 배경색을 회색으로, 버튼 글자색은 잘 안보이는 흰끼도는 색으로.
        btn_walking.setBackgroundColor(Color.parseColor("#f2f5fa"));
        btn_walking.setTextColor(Color.parseColor("#cfd8e8"));

        btn_hiking.setBackgroundColor(Color.parseColor("#f2f5fa"));
        btn_hiking.setTextColor(Color.parseColor("#cfd8e8"));

        btn_swimming.setBackgroundColor(Color.parseColor("#f2f5fa"));
        btn_swimming.setTextColor(Color.parseColor("#cfd8e8"));

        btn_running.setBackgroundColor(Color.parseColor("#f2f5fa"));
        btn_running.setTextColor(Color.parseColor("#cfd8e8"));

        btn_squat.setBackgroundColor(Color.parseColor("#f2f5fa"));
        btn_squat.setTextColor(Color.parseColor("#cfd8e8"));

        btn_pushup.setBackgroundColor(Color.parseColor("#f2f5fa"));
        btn_pushup.setTextColor(Color.parseColor("#cfd8e8"));

        btn_weighttraing.setBackgroundColor(Color.parseColor("#f2f5fa"));
        btn_weighttraing.setTextColor(Color.parseColor("#cfd8e8"));

        btn_cycle.setBackgroundColor(Color.parseColor("#f2f5fa"));
        btn_cycle.setTextColor(Color.parseColor("#cfd8e8"));


        String str_muscle_lvl = "";
        String str_fat_lvl = "";

        // switch가 ON 상태일 때

        btnanaerobic.setVisibility(View.VISIBLE); // 무산소
        btnaerobic.setVisibility(View.VISIBLE);  // 유산소
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
                            btn_running.setBackgroundColor(Color.parseColor("#b0c9f5"));
                            btn_running.setTextColor(Color.parseColor("#000000"));
                        }
                        else if(rand_list[i]==2){
                            btn_weighttraing.setBackgroundColor(Color.parseColor("#b0c9f5"));
                            btn_weighttraing.setTextColor(Color.parseColor("#000000"));
                        }
                        else if(rand_list[i]==3){
                            btn_pushup.setBackgroundColor(Color.parseColor("#b0c9f5"));
                            btn_pushup.setTextColor(Color.parseColor("#000000"));
                        }
                        else if(rand_list[i]==4){
                            btn_squat.setBackgroundColor(Color.parseColor("#b0c9f5"));
                            btn_squat.setTextColor(Color.parseColor("#000000"));
                        }
                    }

                    //네번째로 나온 랜덤숫자는 추천할 유산소운동의 index
                    for(int i= 3; i< count; i++){
                        if(rand_list[i]==1){
                            btn_cycle.setBackgroundColor(Color.parseColor("#b0c9f5"));
                            btn_cycle.setTextColor(Color.parseColor("#000000"));
                        }
                        else if(rand_list[i]==2){
                            btn_swimming.setBackgroundColor(Color.parseColor("#b0c9f5"));
                            btn_swimming.setTextColor(Color.parseColor("#000000"));
                        }
                        else if(rand_list[i]==3){
                            btn_hiking.setBackgroundColor(Color.parseColor("#b0c9f5"));
                            btn_hiking.setTextColor(Color.parseColor("#000000"));
                        }
                        else if(rand_list[i]==4){
                            btn_walking.setBackgroundColor(Color.parseColor("#b0c9f5"));
                            btn_walking.setTextColor(Color.parseColor("#000000"));
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
                            btn_cycle.setBackgroundColor(Color.parseColor("#b0c9f5"));
                            btn_cycle.setTextColor(Color.parseColor("#000000"));
                        }
                        else if(rand_list[i]==2){
                            btn_swimming.setBackgroundColor(Color.parseColor("#b0c9f5"));
                            btn_swimming.setTextColor(Color.parseColor("#000000"));
                        }
                        else if(rand_list[i]==3){
                            btn_hiking.setBackgroundColor(Color.parseColor("#b0c9f5"));
                            btn_hiking.setTextColor(Color.parseColor("#000000"));
                        }
                        else if(rand_list[i]==4){
                            btn_walking.setBackgroundColor(Color.parseColor("#b0c9f5"));
                            btn_walking.setTextColor(Color.parseColor("#000000"));
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
                            btn_running.setBackgroundColor(Color.parseColor("#b0c9f5"));
                            btn_running.setTextColor(Color.parseColor("#000000"));
                        } else if (rand_list[i] == 2) {
                            btn_weighttraing.setBackgroundColor(Color.parseColor("#b0c9f5"));
                            btn_weighttraing.setTextColor(Color.parseColor("#000000"));
                        } else if (rand_list[i] == 3) {
                            btn_pushup.setBackgroundColor(Color.parseColor("#b0c9f5"));
                            btn_pushup.setTextColor(Color.parseColor("#000000"));
                        } else if (rand_list[i] == 4) {
                            btn_squat.setBackgroundColor(Color.parseColor("#b0c9f5"));
                            btn_squat.setTextColor(Color.parseColor("#000000"));
                        }
                    }

                    //세번째, 네번째로 나온 랜덤숫자는 추천할 유산소운동의 index
                    for (int i = 2; i < count; i++) {
                        if (rand_list[i] == 1) {
                            btn_cycle.setBackgroundColor(Color.parseColor("#b0c9f5"));
                            btn_cycle.setTextColor(Color.parseColor("#000000"));
                        } else if (rand_list[i] == 2) {
                            btn_swimming.setBackgroundColor(Color.parseColor("#b0c9f5"));
                            btn_swimming.setTextColor(Color.parseColor("#000000"));
                        } else if (rand_list[i] == 3) {
                            btn_hiking.setBackgroundColor(Color.parseColor("#b0c9f5"));
                            btn_hiking.setTextColor(Color.parseColor("#000000"));
                        } else if (rand_list[i] == 4) {
                            btn_walking.setBackgroundColor(Color.parseColor("#b0c9f5"));
                            btn_walking.setTextColor(Color.parseColor("#000000"));
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
                    if(rand_list[i]==1){
                        btn_running.setBackgroundColor(Color.parseColor("#b0c9f5"));
                        btn_running.setTextColor(Color.parseColor("#000000"));
                    }
                    else if(rand_list[i]==2){
                        btn_weighttraing.setBackgroundColor(Color.parseColor("#b0c9f5"));
                        btn_weighttraing.setTextColor(Color.parseColor("#000000"));
                    }
                    else if(rand_list[i]==3){
                        btn_pushup.setBackgroundColor(Color.parseColor("#b0c9f5"));
                        btn_pushup.setTextColor(Color.parseColor("#000000"));
                    }
                    else if(rand_list[i]==4){
                        btn_squat.setBackgroundColor(Color.parseColor("#b0c9f5"));
                        btn_squat.setTextColor(Color.parseColor("#000000"));
                    }
                }

                //네번째로 나온 랜덤숫자는 추천할 유산소운동의 index
                for(int i= 3; i< count; i++){
                    if(rand_list[i]==1){
                        btn_cycle.setBackgroundColor(Color.parseColor("#b0c9f5"));
                        btn_cycle.setTextColor(Color.parseColor("#000000"));
                    }
                    else if(rand_list[i]==2){
                        btn_swimming.setBackgroundColor(Color.parseColor("#b0c9f5"));
                        btn_swimming.setTextColor(Color.parseColor("#000000"));
                    }
                    else if(rand_list[i]==3){
                        btn_hiking.setBackgroundColor(Color.parseColor("#b0c9f5"));
                        btn_hiking.setTextColor(Color.parseColor("#000000"));
                    }
                    else if(rand_list[i]==4){
                        btn_walking.setBackgroundColor(Color.parseColor("#b0c9f5"));
                        btn_walking.setTextColor(Color.parseColor("#000000"));
                    }
                }
            }

            // 3. 근육 레벨 < 지방 레벨  -> (무산소1, 유산소3)
            else if(Integer.parseInt(str_muscle_lvl) < Integer.parseInt(str_fat_lvl)){

                text_view_explain.setText("당신은 상대적으로 지방량이 많습니다.\n 무산소 운동 한 개, 유산소 운동 세 개를 추천해줄게요");
                text_view_explain.setTextSize(20);

                //첫번째로 나온 랜덤숫자는 추천할 무산소운동의 index
                for(int i=0; i< 1; i++){
                    if(rand_list[i]==1){
                        btn_running.setBackgroundColor(Color.parseColor("#b0c9f5"));
                        btn_running.setTextColor(Color.parseColor("#000000"));
                    }
                    else if(rand_list[i]==2){
                        btn_weighttraing.setBackgroundColor(Color.parseColor("#b0c9f5"));
                        btn_weighttraing.setTextColor(Color.parseColor("#000000"));
                    }
                    else if(rand_list[i]==3){
                        btn_pushup.setBackgroundColor(Color.parseColor("#b0c9f5"));
                        btn_pushup.setTextColor(Color.parseColor("#000000"));
                    }
                    else if(rand_list[i]==4){
                        btn_squat.setBackgroundColor(Color.parseColor("#b0c9f5"));
                        btn_squat.setTextColor(Color.parseColor("#000000"));
                    }
                }

                //두번째, 세번째, 네번째로 나온 랜덤숫자는 추천할 유산소운동의 index
                for(int i= 1; i< count; i++){
                    if(rand_list[i]==1){
                        btn_cycle.setBackgroundColor(Color.parseColor("#b0c9f5"));
                        btn_cycle.setTextColor(Color.parseColor("#000000"));
                    }
                    else if(rand_list[i]==2){
                        btn_swimming.setBackgroundColor(Color.parseColor("#b0c9f5"));
                        btn_swimming.setTextColor(Color.parseColor("#000000"));
                    }
                    else if(rand_list[i]==3){
                        btn_hiking.setBackgroundColor(Color.parseColor("#b0c9f5"));
                        btn_hiking.setTextColor(Color.parseColor("#000000"));
                    }
                    else if(rand_list[i]==4){
                        btn_walking.setBackgroundColor(Color.parseColor("#b0c9f5"));
                        btn_walking.setTextColor(Color.parseColor("#000000"));
                    }
                }
            }
        } catch (NumberFormatException e){
            Toast.makeText(getContext(), "신체정보를 입력하시면 운동을 추천해드립니다.", Toast.LENGTH_SHORT).show();
            btnanaerobic.setVisibility(View.VISIBLE);
            btnaerobic.setVisibility(View.VISIBLE);
            btn_running.setVisibility(View.VISIBLE);
            btn_weighttraing.setVisibility(View.VISIBLE);
            btn_pushup.setVisibility(View.VISIBLE);
            btn_squat.setVisibility(View.VISIBLE);
            btn_cycle.setVisibility(View.VISIBLE);
            btn_swimming.setVisibility(View.VISIBLE);
            btn_hiking.setVisibility(View.VISIBLE);
            btn_walking.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            if (resultCode != Activity.RESULT_OK) {
                return;
            }
            user_kcal = data.getExtras().getDouble("user_kcal");
            textView.setText(user_kcal.toString());
        }
    }



}


