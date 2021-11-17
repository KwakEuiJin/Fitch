package org.app.gimalpro;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class fragment2 extends Fragment {
    private Bodyitem bodyitem;
    private DBHelper_body dbHelper_body;
    private ArrayList<Bodyitem> bodyitems;

    private View view;

    Switch switchstart;
    Button  btnanaerobic, btnaerobic;  // 운동 일지 추가, 무산소 운동, 유산소 운동

    Button btn_running, btn_weighttraing, btn_jump, btn_squat; // 무산소 운동
    Button btn_cycle, btn_swimming, btn_hiking, btn_walking; // 유산소 운동


    @Nullable
    @Override



    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        bodyitem = new Bodyitem();
        dbHelper_body = new DBHelper_body(getContext());

        view = inflater.inflate(R.layout.fragment2, container, false);
        switchstart = view.findViewById(R.id.switchstart);

        // 무산소 운동, 유산소 운동
        btnanaerobic = view.findViewById(R.id.btnanaerobic);
        btnaerobic = view.findViewById(R.id.btnaerobic);

        // 무산소 운동 종류
        btn_running = view.findViewById(R.id.btn_running);
        btn_weighttraing = view.findViewById(R.id.btn_weighttraining);
        btn_jump = view.findViewById(R.id.btn_jump);
        btn_squat = view.findViewById(R.id.btn_squat);

        // 유산소 운동 종류
        btn_cycle = view.findViewById(R.id.btn_cycle);
        btn_swimming = view.findViewById(R.id.btn_swimming);
        btn_hiking = view.findViewById(R.id.btn_hiking);
        btn_walking = view.findViewById(R.id.btn_walking);

        switchstart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (switchstart.isChecked() == true) {
                    btnanaerobic.setVisibility(View.VISIBLE); // 무산소
                    btnaerobic.setVisibility(View.VISIBLE);  // 유산소
                    bodyitems = dbHelper_body.selectBody();
                    try {
                        String str_muscle = Double.toString(bodyitems.get(bodyitems.size() - 1).getMuscle());
                        String str_fat = Double.toString(bodyitems.get(bodyitems.size() - 1).getFat());

                        Toast.makeText(getContext(), "string muscle:" + str_muscle + " fat: " + str_fat, Toast.LENGTH_LONG).show();
                    }catch (ArrayIndexOutOfBoundsException e){
                        Toast.makeText(getContext(), "신체정보를 입력하시오", Toast.LENGTH_LONG).show();
                    }


                } else {
                    btnanaerobic.setVisibility(View.INVISIBLE);
                    btnaerobic.setVisibility(View.INVISIBLE);
                    btn_running.setVisibility(View.INVISIBLE);
                    btn_weighttraing.setVisibility(View.INVISIBLE);
                    btn_jump.setVisibility(View.INVISIBLE);
                    btn_squat.setVisibility(View.INVISIBLE);
                    btn_cycle.setVisibility(View.INVISIBLE);
                    btn_swimming.setVisibility(View.INVISIBLE);
                    btn_hiking.setVisibility(View.INVISIBLE);
                    btn_walking.setVisibility(View.INVISIBLE);

                }
            }
        });
        // 무산소 운동
        btnanaerobic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnanaerobic.isClickable()) {
                    btn_running.setVisibility(View.VISIBLE);
                    btn_weighttraing.setVisibility(View.VISIBLE);
                    btn_jump.setVisibility(View.VISIBLE);
                    btn_squat.setVisibility(View.VISIBLE);
                    btnaerobic.setVisibility(View.INVISIBLE);

                } else {
                    btn_cycle.setVisibility(View.INVISIBLE);
                    btn_swimming.setVisibility(View.INVISIBLE);
                    btn_hiking.setVisibility(View.INVISIBLE);
                    btn_walking.setVisibility(View.INVISIBLE);

                }


                // 유산소 운동
                btnaerobic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (btnaerobic.isClickable()) {
                            btn_cycle.setVisibility(View.VISIBLE);
                            btn_swimming.setVisibility(View.VISIBLE);
                            btn_hiking.setVisibility(View.VISIBLE);
                            btn_walking.setVisibility(View.VISIBLE);
                            btnanaerobic.setVisibility(View.INVISIBLE);

                        } else {
                            btn_running.setVisibility(View.INVISIBLE);
                            btn_weighttraing.setVisibility(View.INVISIBLE);
                            btn_jump.setVisibility(View.INVISIBLE);
                            btn_squat.setVisibility(View.INVISIBLE);

                        }

                    }
                });




            }

        });
        return view;
    }
}
