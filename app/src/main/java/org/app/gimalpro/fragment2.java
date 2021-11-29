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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class fragment2 extends Fragment {
    private DBHelper_body dbHelper_body;
    private ArrayList<Bodyitem> bodyitems;
    private View view;
    private  String user_gender="남성"; // 디폴트값
    private Double userHeight=172.0; // 디폴트값
    private Double userWeight=50.0; //디폴트값 - setprogressbar()에서 키와 몸무게 받음
    private Double user_kcal=0.0;
    private int userFatlv;
    private int userMuslv;
    private int user_age=21;
    private DBHelp_health_list dbHelpHealthlist;
    private ArrayList<Todoitem> todoitems;
    private Adapter2 adapter;

    ProgressBar progressBar;
    //임시
    TextView textView1,textView2;
    RecyclerView rv_health;

    ImageButton bt_running, bt_weighttraing, bt_pushup, bt_squat, bt_witmom; // 무산소 운동
    ImageButton bt_cycle, bt_jul, bt_mountin, bt_walking; // 유산소 운동
    LinearLayout btn_cycle, btn_jul, btn_mountin, btn_walking,btn_running, btn_weighttraing, btn_pushup, btn_squat, btn_witmom;



    @Nullable
    @Override



    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment2, container, false);
        //db 객체 선언
        dbHelper_body=new DBHelper_body(getContext());
        dbHelpHealthlist=new DBHelp_health_list(getContext());

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
        rv_health = view.findViewById(R.id.rv_health);


        String currentdate =new SimpleDateFormat("yyyy년 MM월 dd일").format(new Date());
        Toast.makeText(getContext(), currentdate, Toast.LENGTH_SHORT).show();
        loadRecentdb(currentdate);


        try {
            bodyitems = dbHelper_body.selectBody();
            userWeight = bodyitems.get(bodyitems.size()-1).getWeight();
            user_kcal = bodyitems.get(bodyitems.size()-1).getKcal();
            userHeight = bodyitems.get(bodyitems.size()-1).getHeight();
            userFatlv=bodyitems.get(bodyitems.size()-1).getFat_level();
            userMuslv=bodyitems.get(bodyitems.size()-1).getMuscle_level();
        } catch (ArrayIndexOutOfBoundsException e){
            Toast.makeText(getContext(), "신체정보를 입력하세요", Toast.LENGTH_SHORT).show();
        }

        bt_gone();
        buttonstart();
        setProgressBar();


        //버튼이벤트
        try {
            //달리기
            bt_running.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),Timer.class);
                    Double kcal=(8.0*userWeight)/3600;
                    intent.putExtra("kcal",kcal);
                    startActivity(intent);

                }
            });
            //그 외 근력운동
            bt_weighttraing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),Timer.class);
                    Double kcal=(5.5*userWeight)/3600;
                    intent.putExtra("kcal",kcal);

                    startActivity(intent);
                }
            });
            //푸쉬업
            bt_pushup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),Timer.class);
                    Double kcal=(6.0*userWeight)/3600;
                    intent.putExtra("kcal",kcal);
                    startActivity(intent);
                }
            });
            //스쿼트
            bt_squat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),Timer.class);
                    Double kcal=(6.0*userWeight)/3600;
                    intent.putExtra("kcal",kcal);
                    startActivity(intent);
                }
            });
            //사이클
            bt_cycle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),Timer.class);
                    Double kcal=(5.5*userWeight)/3600;
                    intent.putExtra("kcal",kcal);
                    startActivity(intent);
                }
            });


            //조깅
            bt_walking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),Timer.class);
                    Double kcal=(6.0*userWeight)/3600;
                    intent.putExtra("kcal",kcal);
                    startActivity(intent);
                }
            });

            //윗몸일으키기
            bt_witmom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),Timer.class);
                    Double kcal=(5.0*userWeight)/3600;
                    intent.putExtra("kcal",kcal);
                    startActivity(intent);
                }
            });

            //줄넘기
            bt_jul.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),Timer.class);
                    Double kcal=(10*userWeight)/3600;
                    intent.putExtra("kcal",kcal);
                    startActivity(intent);
                }
            });

            //마운틴클라이머
            bt_mountin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),Timer.class);
                    Double kcal=(10*userWeight)/3600;
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

        // switch가 ON 상태일 때

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
            if(userMuslv==userFatlv){

                //특이케이스 처리 - 근육량, 지방량이 모두 0인 경우
                if(userMuslv==0 && userFatlv==0){

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
                else if(userMuslv== 2 && userFatlv==2){

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
            else if(userMuslv > userFatlv){

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
            else if(userMuslv < userFatlv){

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
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        String currentdate =new SimpleDateFormat("yyyy년 MM월 dd일").format(new Date());
        try {
            bodyitems = dbHelper_body.selectBody();
            userWeight = bodyitems.get(bodyitems.size()-1).getWeight();
            user_kcal = bodyitems.get(bodyitems.size()-1).getKcal();
            setProgressBar();
            reloadRecentdb(currentdate);
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
        //지방량에 따라서 소비해야하는 칼로리의 양을 계산

        textView1=view.findViewById(R.id.textView1);
        textView2=view.findViewById(R.id.textView2);
        Intent intent = getActivity().getIntent();
        int gicho;
        user_age=intent.getIntExtra("userAge",20);
        user_gender = intent.getStringExtra("userGender");

        if (user_gender.contains("남성")){
            int hwaldong=30;
            if (userFatlv==2){
                hwaldong=40;
            }

            else if (userFatlv==1){
                hwaldong=35;
            }

            else {
                hwaldong=25;
            }

            gicho= (int) ((((userHeight-100)*0.9)*hwaldong)-(66.47+(13.75*userWeight)+(5*userHeight)-(6.76*user_age)));
        }
        else {
            int hwaldong=30;
            if (userFatlv==2){
            hwaldong=40;
        }

        else if (userFatlv==1){

            hwaldong=35;
        }

        else {
            hwaldong=25;
        }
            gicho= (int) ((((userHeight-100)*0.9)*hwaldong)-(655.1+(9.56*userWeight)+(1.85*userHeight)-(4.68*user_age)));
        }

        String percent_view = String.format("%.1f %s",(user_kcal/gicho*100),"%");
        int percent = (int) (user_kcal/1);

        progressBar=view.findViewById(R.id.progressBar);
        progressBar.setProgress(percent);
        progressBar.setMax(gicho);
        textView1.setText(percent_view);
        textView2.setText(String.format("%.2f %s %d %s",user_kcal,"Kcal /",gicho,"Kcal "));
    }

    private void loadRecentdb(String _futuredate) {
        //저장되어있던 db가져오는 함수
        todoitems= dbHelpHealthlist.getTodolist(_futuredate);
        if (adapter==null){
            adapter = new Adapter2(todoitems,getContext());
            rv_health.setAdapter(adapter);
        }
    }

    private void reloadRecentdb(String _futuredate) {
        //저장되어있던 db가져오는 함수
        rv_health = view.findViewById(R.id.rv_health);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rv_health.setLayoutManager(linearLayoutManager);
        todoitems= dbHelpHealthlist.getTodolist(_futuredate);
        adapter = new Adapter2(todoitems,getContext());
        rv_health.setAdapter(adapter);

    }



    }







