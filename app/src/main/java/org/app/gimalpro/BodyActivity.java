package org.app.gimalpro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BodyActivity extends AppCompatActivity {
    private DBHelper_body dbHelper_body;
    private ArrayList<Bodyitem> bodyitems;
    String userGender;
    TextView tv_height;
    TextView tv_weight;
    TextView tv_muscle;
    TextView tv_fat;
    Button bt_input;
    Button bt_update;
    Button bt_f2;
    TextView tv_mus_b,tv_fat_b;
    ProgressBar progressBar_mus,progressBar_fat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body);
        tv_height=findViewById(R.id.tv_height);
        tv_weight=findViewById(R.id.tv_weight);
        tv_muscle=findViewById(R.id.tv_muscle);
        tv_fat=findViewById(R.id.tv_fat);
        bt_f2=findViewById(R.id.bt_f2);
        bt_input=findViewById(R.id.bt_input);
        bt_update=findViewById(R.id.bt_update);
        tv_mus_b=findViewById(R.id.tv_mus_b);
        tv_fat_b=findViewById(R.id.tv_fat_b);
        progressBar_mus=findViewById(R.id.progressBar_mus);
        progressBar_fat=findViewById(R.id.progressBar_fat);

        Intent intent = getIntent();
        userGender = intent.getStringExtra("userGender");

        dbHelper_body=new DBHelper_body(this);

        loadRecentdb();
        setprogressbar_body();
        if (bodyitems.isEmpty()){
            bt_input.setEnabled(true);
            bt_update.setEnabled(false);
        }
        else {
            bt_input.setEnabled(false);
            bt_update.setEnabled(true);
        }


        //프래그먼트 2로 이동
        bt_f2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            intent.putExtra("f2","f2");
            intent.putExtra("userGender",userGender);
            startActivity(intent);
            }
        });


        //신체정보 입력 버튼 이벤트
        bt_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(BodyActivity.this, android.R.style.Theme_Material_Light_Dialog);
                dialog.setContentView(R.layout.dialog_body_insert);
                EditText et_height = dialog.findViewById(R.id.et_height);
                EditText et_weight = dialog.findViewById(R.id.et_weight);
                EditText et_muscle = dialog.findViewById(R.id.et_muscle);
                EditText et_fat = dialog.findViewById(R.id.et_fat);



                Button btn_okbody = dialog.findViewById(R.id.bt_okbody);
                btn_okbody.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if(et_height.length()==0||et_weight.length()==0||et_muscle.length()==0||et_fat.length()==0){
                            tv_height.setText("(CM)");
                            tv_weight.setText("(KG)");
                            tv_muscle.setText("(KG)");
                            tv_fat.setText("(KG)");
                            Toast.makeText(getApplicationContext(), "값을 입력하세요", Toast.LENGTH_SHORT).show();
                        }


                        else {
                            Double userHeight=Double.parseDouble(et_height.getText().toString());
                            Double userWeight=Double.parseDouble(et_weight.getText().toString());
                            Double userMuscle=Double.parseDouble(et_muscle.getText().toString());
                            Double userFat=Double.parseDouble(et_fat.getText().toString());
                            //유저의 신체정보 등급 나누기
                            Double mMuscle=0.0; //근육량
                            Double Muscle_b=0.0; //근격골 비율
                            int userMuscle_level=0; //근격골율 등급
                            Double mFat = 0.0; //제지방량
                            Double Fat_b=0.0; //체지방 비율
                            int userFat_level=0; //체지방율 등급


                            //제지방량 구하기
                            if (userGender.contains("남성")){
                                mFat= ((1.10  * userWeight ) - ( 128 * (( Math.pow(userWeight,2)) / (Math.pow(userHeight,2)))));

                            }
                            else{
                                mFat=((1.07  * userWeight ) - ( 128 * (( Math.pow(userWeight,2)) / (Math.pow(userHeight,2)))));
                            }
                            //체지방량 모를때
                            if (userFat==0){
                                userFat=userWeight-mFat;

                            }

                            //골격근량 모를 때
                            if(userMuscle==0){
                                if (userGender.contains("남성")){
                                mMuscle = mFat-5;
                                userMuscle=mMuscle*0.577;}
                                else { mMuscle = mFat-4.5;
                                    userMuscle=mMuscle*0.577;}
                            }
                            //체지방율 구하기, 체지방 등급선정
                            if (userGender.contains("남성")){
                                Fat_b=(userFat/userWeight)*100;
                                if (Fat_b<15){
                                    userFat_level=0;
                                }
                                else if (Fat_b<23){
                                    userFat_level=1;
                                }
                                else {
                                    userFat_level=2;
                                }
                            }


                            else{
                                Fat_b=(userFat/userWeight)*100;
                                if (Fat_b<22){
                                    userFat_level=0;
                                }
                                else if (Fat_b<27){
                                    userFat_level=1;
                                }
                                else {
                                    userFat_level=2;
                                }

                            }

                            //골격근율 구하기
                            if (userGender.contains("남성")){
                                Muscle_b = (userMuscle/userWeight)*100;
                                if (Muscle_b<40){
                                    userMuscle_level=0;
                                }
                                else if (Muscle_b<48){
                                    userMuscle_level=1;
                                }
                                else {
                                    userMuscle_level=2;
                                }
                            }


                            else{
                                Muscle_b = (userMuscle/userWeight)*100;
                                if (Muscle_b<35){
                                    userMuscle_level=0;
                                }
                                else if (Muscle_b<45){
                                    userMuscle_level=1;
                                }
                                else {
                                    userMuscle_level=2;
                                }}

                        //db insert
                        dbHelper_body.insertBody(MainActivity.UserID,userHeight,userWeight,userMuscle,userFat,userMuscle_level,userFat_level);

                        //ui insert
                        Bodyitem item = new Bodyitem();
                        item.setHeight(userHeight);
                        item.setWeight(userWeight);
                        item.setMuscle(userMuscle);
                        item.setFat(userFat);
                        item.setMuscle_level(userMuscle_level);
                        item.setFat_level(userFat_level);
                        tv_height.setText(String.valueOf(item.getHeight()));
                        tv_weight.setText(String.valueOf(item.getWeight()));
                        tv_muscle.setText(String.format("%.2f",item.getMuscle()));
                        tv_fat.setText(String.format("%.2f",item.getFat()));
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "새로운 신체정보가 추가되었습니다.", Toast.LENGTH_SHORT).show();
                        bt_input.setEnabled(false);
                        bt_update.setEnabled(true);
                        }
                        setprogressbar_body();
                    }
                });
                dialog.show();

            }
        });

    //신체정보 수정하기 기능
        //update
        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bodyitems = dbHelper_body.selectBody();
                Dialog dialog = new Dialog(BodyActivity.this, android.R.style.Theme_Material_Light_Dialog);
                dialog.setContentView(R.layout.dialog_body_insert);
                EditText et_height = dialog.findViewById(R.id.et_height);
                EditText et_weight = dialog.findViewById(R.id.et_weight);
                EditText et_muscle = dialog.findViewById(R.id.et_muscle);
                EditText et_fat = dialog.findViewById(R.id.et_fat);

                if (!bodyitems.isEmpty()){
                    et_height.setText(String.format("%.1f",bodyitems.get(bodyitems.size()-1).getHeight()));
                    et_weight.setText(String.format("%.1f",bodyitems.get(bodyitems.size()-1).getWeight()));
                    et_fat.setText(String.format("%.2f",bodyitems.get(bodyitems.size()-1).getFat()));
                    et_muscle.setText(String.format("%.2f",bodyitems.get(bodyitems.size()-1).getMuscle()));
                }

                Button button =dialog.findViewById(R.id.bt_okbody);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(et_height.length()==0||et_weight.length()==0||et_muscle.length()==0||et_fat.length()==0){
                            tv_height.setText("(CM)");
                            tv_weight.setText("(KG)");
                            tv_muscle.setText("(KG)");
                            tv_fat.setText("(KG)");
                            Toast.makeText(getApplicationContext(), "값을 입력하세요", Toast.LENGTH_SHORT).show();

                        }
                        else{
                            Double userHeight=Double.parseDouble(et_height.getText().toString());
                            Double userWeight=Double.parseDouble(et_weight.getText().toString());
                            Double userMuscle=Double.parseDouble(et_muscle.getText().toString());
                            Double userFat=Double.parseDouble(et_fat.getText().toString());
                            //유저의 신체정보 등급 나누기
                            Intent intent = getIntent();
                            String userGender = intent.getStringExtra("userGender");
                            Double mMuscle=0.0; //근육량
                            Double Muscle_b=0.0; //근격골 비율
                            int userMuscle_level=0; //근격골율 등급
                            Double mFat = 0.0; //제지방량
                            Double Fat_b=0.0; //체지방 비율
                            int userFat_level=0; //체지방율 등급

                            //제지방량 구하기
                            if (userGender.contains("남성")){
                                mFat= ((1.10 * userWeight ) - (128 * (( Math.pow(userWeight,2)) / (Math.pow(userHeight,2)))));

                            }
                            else{
                                mFat=((1.07  * userWeight ) - ( 128 * (( Math.pow(userWeight,2)) / (Math.pow(userHeight,2)))));
                            }
                            //체지방량 모를때
                            if (userFat==0){
                                userFat=userWeight-mFat;
                            }

                            //골격근량 모를 때
                            if(userMuscle==0){
                                if (userGender.contains("남성")){
                                    mMuscle = mFat-5;
                                    userMuscle=mMuscle*0.577;}
                                else{
                                    mMuscle = mFat-4.5;
                                    userMuscle=mMuscle*0.577;
                                }
                            }
                            //체지방율 구하기, 체지방 등급선정
                            if (userGender.contains("남성")){
                                Fat_b=(userFat/userWeight)*100;
                                if (Fat_b<15){
                                    userFat_level=0;
                                }
                                else if (Fat_b<23){
                                    userFat_level=1;
                                }
                                else {
                                    userFat_level=2;
                                }
                            }


                            else{
                                Fat_b=(userFat/userWeight)*100;
                                if (Fat_b<22){
                                    userFat_level=0;
                                }
                                else if (Fat_b<27){
                                    userFat_level=1;
                                }
                                else {
                                    userFat_level=2;
                                }

                            }
                            //골격근율 구하기
                            if (userGender.contains("남성")){
                                Muscle_b = ((userMuscle/userWeight)*100);
                                if (Muscle_b<40){
                                    userMuscle_level=0;
                                }
                                else if (Muscle_b<48){
                                    userMuscle_level=1;
                                }
                                else {
                                    userMuscle_level=2;
                                }
                            }


                            else{
                                Muscle_b = ((userMuscle/userWeight)*100);
                                if (Muscle_b<35){
                                    userMuscle_level=0;
                                }
                                else if (Muscle_b<45){
                                    userMuscle_level=1;
                                }
                                else {
                                    userMuscle_level=2;
                                }}

                        //update
                        if(!bodyitems.isEmpty()){
                        dbHelper_body.updateBody(MainActivity.UserID,userHeight,userWeight,userMuscle,userFat, bodyitems.get(bodyitems.size()-1).getNUMBER(),userMuscle_level,userFat_level);
                        Bodyitem item = new Bodyitem();
                        item.setHeight(userHeight);
                        item.setWeight(userWeight);
                        item.setMuscle(userMuscle);
                        item.setFat(userFat);
                        item.setMuscle_level(userMuscle_level);
                        item.setFat_level(userFat_level);
                        tv_height.setText(String.valueOf(item.getHeight()));
                        tv_weight.setText(String.valueOf(item.getWeight()));
                        tv_muscle.setText(String.format("%.2f",item.getMuscle()));
                        tv_fat.setText(String.format("%.2f",item.getFat()));

                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "신체정보가 업데이트 되었습니다.", Toast.LENGTH_SHORT).show();}

                        else {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "신체정보를 먼저 추가하세요!", Toast.LENGTH_SHORT).show();
                        }}
                        setprogressbar_body();
                    }
                });
                dialog.show();



            }

        });




    }

    private void loadRecentdb() {
        bodyitems = dbHelper_body.selectBody();

        if(!bodyitems.isEmpty()) {
            if ((tv_height.getText()).equals("(CM)")) {
                tv_height.setText(String.valueOf(bodyitems.get(bodyitems.size()-1).getHeight()));
            }
            if ((tv_weight.getText()).equals("(KG)")) {
                tv_weight.setText(String.valueOf(bodyitems.get(bodyitems.size()-1).getWeight()));
            }
            if ((tv_muscle.getText()).equals("(KG)")) {
                tv_muscle.setText(String.format("%.2f",bodyitems.get(bodyitems.size()-1).getMuscle()));
            }
            if ((tv_fat.getText()).equals("(KG)")) {
                tv_fat.setText(String.format("%.2f",bodyitems.get(bodyitems.size()-1).getFat()));
            }
        }
        else{
            tv_height.setText("(CM)");
            tv_weight.setText("(KG)");
            tv_muscle.setText("(KG)");
            tv_fat.setText("(KG)");
        }
    }

    public void setprogressbar_body(){
        bodyitems=dbHelper_body.selectBody();
        Double muscle_b_progress;
        Double fat_b_progress;
        int userWeight_progress;
        if (!bodyitems.isEmpty()){
            userWeight_progress= (int) (bodyitems.get(bodyitems.size()-1).getWeight()*1);
            muscle_b_progress= ((bodyitems.get(bodyitems.size()-1).getMuscle()/userWeight_progress)*100);
            fat_b_progress= ((bodyitems.get(bodyitems.size()-1).getFat()/userWeight_progress)*100);
            progressBar_mus.setProgress((int) (muscle_b_progress*1));
            progressBar_fat.setProgress((int) (fat_b_progress*1));
            tv_mus_b.setText(String.format("%.1f %s",muscle_b_progress,"%"));
            tv_fat_b.setText(String.format("%.1f %s",fat_b_progress,"%"));
        }
        else {
            progressBar_mus.setProgress(0);
            progressBar_fat.setProgress(0);
        }
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.putExtra("userGender",userGender);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setprogressbar_body();
    }
}