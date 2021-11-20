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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BodyActivity extends AppCompatActivity {
    private DBHelper_body dbHelper_body;
    private ArrayList<Bodyitem> bodyitems;
    TextView tv_height;
    TextView tv_weight;
    TextView tv_muscle;
    TextView tv_fat;
    Button bt_input;
    Button bt_update;
    Button bt_f2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body);
        tv_height=findViewById(R.id.tv_height);
        tv_weight=findViewById(R.id.tv_weight);
        tv_muscle=findViewById(R.id.tv_muscle);
        tv_fat=findViewById(R.id.tv_fat);
        bt_f2=findViewById(R.id.bt_f2);

        dbHelper_body=new DBHelper_body(this);

        loadRecentdb();


        //프래그먼트 2로 이동
        bt_f2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            intent.putExtra("f2","f2");
            startActivity(intent);
            }
        });


        //신체정보 입력 버튼 이벤트
        bt_input=findViewById(R.id.bt_input);
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

                        if(et_height.length()==0||et_weight.length()==0||et_muscle.length()==0||et_fat.length()==0){
                            tv_height.setText("(CM)");
                            tv_weight.setText("(KG)");
                            tv_muscle.setText("(KG)");
                            tv_fat.setText("(KG)");

                        }


                        else {
                            Toast.makeText(getApplicationContext(), userGender, Toast.LENGTH_SHORT).show();


                            //제지방량 구하기
                            if (userGender.contains("남성")){
                                mFat= ((1.10  * userWeight ) - ( 128 * ( Math.pow(userWeight,2)) / (Math.pow(userHeight,2))));

                            }
                            else{
                                mFat=((1.07  * userWeight ) - ( 128 * ( Math.pow(userWeight,2)) / (Math.pow(userHeight,2))));
                            }
                            //체지방량 모를때
                            if (userFat==0){
                                userFat=userWeight-mFat;

                            }

                            //근격골량 모를 때
                            if(userMuscle==0){
                                mMuscle = mFat-2.7;
                                userMuscle=mMuscle*0.577;
                            }
                            //체지방율 구하기, 체지방 등급선정
                            if (userGender.contains("남성")){
                                Fat_b=(userFat/userWeight)*100;
                                if (Fat_b<15){
                                    userFat_level=0;
                                }
                                else if (Fat_b<25){
                                    userFat_level=1;
                                }
                                else {
                                    userFat_level=2;
                                }
                            }


                            else{
                                Fat_b=(userFat/userWeight)*100;
                                if (Fat_b<25){
                                    userFat_level=0;
                                }
                                else if (Fat_b<35){
                                    userFat_level=1;
                                }
                                else {
                                    userFat_level=2;
                                }

                            }

                            //근격골율 구하기
                            if (userGender.contains("남성")){
                                Muscle_b = (userMuscle/userWeight)*100;
                                if (Muscle_b<32){
                                    userMuscle_level=0;
                                }
                                else if (Muscle_b<40){
                                    userMuscle_level=1;
                                }
                                else {
                                    userMuscle_level=2;
                                }
                            }


                            else{
                                Muscle_b = (userMuscle/userWeight)*100;
                                if (Muscle_b<26.5){
                                    userMuscle_level=0;
                                }
                                else if (Muscle_b<32.5){
                                    userMuscle_level=1;
                                }
                                else {
                                    userMuscle_level=2;
                                }}

                        //db insert
                        dbHelper_body.insertBody(LoginActivity.UserID,userHeight,userWeight,userMuscle,userFat,userMuscle_level,userFat_level);

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
                        tv_muscle.setText(String.valueOf(item.getMuscle()));
                        tv_fat.setText(String.valueOf(item.getFat()));
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "새로운 신체정보가 추가되었습니다.", Toast.LENGTH_SHORT).show();







                        }

                    }
                });
                dialog.show();

            }
        });



        //길게누르면 신체정보 삭제(임의로 넣어둔 기능)
        tv_height.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                bodyitems = dbHelper_body.selectBody();
                //db delete
                dbHelper_body.deleteBody(LoginActivity.UserID,bodyitems.get(bodyitems.size()-1).getNUMBER());
                tv_height.setText("(CM)");
                tv_weight.setText("(KG)");
                tv_muscle.setText("(KG)");
                tv_fat.setText("(KG)");

                return true;
            }
        });

    //신체정보 수정하기 기능
        //update
        bt_update=findViewById(R.id.bt_update);
        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bodyitems = dbHelper_body.selectBody();
                Dialog dialog = new Dialog(BodyActivity.this, android.R.style.Theme_Material_Light_Dialog);
                dialog.setContentView(R.layout.dialog_body_update);
                EditText et_height = dialog.findViewById(R.id.et_height);
                EditText et_weight = dialog.findViewById(R.id.et_weight);
                EditText et_muscle = dialog.findViewById(R.id.et_muscle);
                EditText et_fat = dialog.findViewById(R.id.et_fat);
                Button button =dialog.findViewById(R.id.bt_okbodyup);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Double height=Double.parseDouble(et_height.getText().toString());
                        Double weight=Double.parseDouble(et_weight.getText().toString());
                        Double muscle=Double.parseDouble(et_muscle.getText().toString());
                        Double fat=Double.parseDouble(et_fat.getText().toString());
                        Double userHeight=Double.parseDouble(et_height.getText().toString());
                        Double userWeight=Double.parseDouble(et_weight.getText().toString());
                        Double userMuscle=Double.parseDouble(et_muscle.getText().toString());
                        Double userFat=Double.parseDouble(et_fat.getText().toString());
                        if(et_height.length()==0||et_weight.length()==0||et_muscle.length()==0||et_fat.length()==0){
                            tv_height.setText("CM");
                            tv_weight.setText("KG");
                            tv_muscle.setText("KG");
                            tv_fat.setText("KG");
                            Toast.makeText(getApplicationContext(), "값을 입력하세요", Toast.LENGTH_SHORT).show();

                        }
                        else{


                            //유저의 신체정보 등급 나누기
                            Intent intent = getIntent();

                            String userGender = intent.getStringExtra("userGender");
                            Double mMuscle=0.0; //근육량
                            Double Muscle_b=(userMuscle/userWeight)*100; //근격골 비율
                            int userMuscle_level=0; //근격골율 등급
                            Double mFat = 0.0; //제지방량
                            Double Fat_b=(userFat/userWeight)*100; //체지방 비율
                            int userFat_level=0; //체지방율 등급

                            //제지방량 구하기
                            if (userGender.contains("남성")){
                                mFat= ((1.10  * userWeight ) - ( 128 * ( Math.pow(userWeight,2)) / (Math.pow(userHeight,2))));

                            }
                            else{
                                mFat=((1.07  * userWeight ) - ( 128 * ( Math.pow(userWeight,2)) / (Math.pow(userHeight,2))));
                            }
                            //체지방량 모를때
                            if (userFat==0){
                                userFat=userWeight-mFat;
                            }

                            //근격골량 모를 때
                            if(userMuscle==0){
                                mMuscle = mFat-2.7;
                                userMuscle=mMuscle*0.577;
                            }
                            //체지방율 구하기, 체지방 등급선정
                            if (userGender.contains("남성")){
                                if (Fat_b<15){
                                    userFat_level=0;
                                }
                                else if (Fat_b<25){
                                    userFat_level=1;
                                }
                                else {
                                    userFat_level=2;
                                }
                            }


                            else{
                                if (Fat_b<25){
                                    userFat_level=0;
                                }
                                else if (Fat_b<35){
                                    userFat_level=1;
                                }
                                else {
                                    userFat_level=2;
                                }

                            }

                            //근격골율 구하기
                            if (userGender.contains("남성")){
                                if (Muscle_b<32){
                                    userMuscle_level=0;
                                }
                                else if (Muscle_b<40){
                                    userMuscle_level=1;
                                }
                                else {
                                    userMuscle_level=2;
                                }
                            }


                            else{
                                if (Muscle_b<26.5){
                                    userMuscle_level=0;
                                }
                                else if (Muscle_b<32.5){
                                    userMuscle_level=1;
                                }
                                else {
                                    userMuscle_level=2;
                                }}






                        //update
                        if(!bodyitems.isEmpty()){
                        dbHelper_body.updateBody(LoginActivity.UserID,height,weight,muscle,fat, bodyitems.get(0).getNUMBER(),userMuscle_level,userFat_level);
                        Bodyitem item = new Bodyitem();
                        item.setHeight(Double.parseDouble(et_height.getText().toString()));
                        item.setWeight(Double.parseDouble(et_weight.getText().toString()));
                        item.setMuscle(Double.parseDouble(et_muscle.getText().toString()));
                        item.setFat(Double.parseDouble(et_fat.getText().toString()));
                        item.setMuscle_level(userMuscle_level);
                        item.setFat_level(userFat_level);
                        tv_height.setText(String.valueOf(item.getHeight()));
                        tv_weight.setText(String.valueOf(item.getWeight()));
                        tv_muscle.setText(String.valueOf(item.getMuscle()));
                        tv_fat.setText(String.valueOf(item.getFat()));
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "신체정보가 업데이트 되었습니다.", Toast.LENGTH_SHORT).show();}

                        else {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "신체정보를 먼저 추가하세요!", Toast.LENGTH_SHORT).show();
                        }}
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
                tv_muscle.setText(String.valueOf(bodyitems.get(bodyitems.size()-1).getMuscle()));
            }
            if ((tv_fat.getText()).equals("(KG)")) {
                tv_fat.setText(String.valueOf(bodyitems.get(bodyitems.size()-1).getFat()));
            }
        }
        else{
            tv_height.setText("(CM)");
            tv_weight.setText("(KG)");
            tv_muscle.setText("(KG)");
            tv_fat.setText("(KG)");
        }
    }


}