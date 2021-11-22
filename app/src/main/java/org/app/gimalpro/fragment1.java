package org.app.gimalpro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.SharedElementCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class fragment1 extends Fragment {
    private View view;
    TextView tv_id,tv_body;
    private DBHelper_body dbHelper_body;
    private ArrayList<Bodyitem> bodyitems;
    Button bt_health;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment1,container,false);
        tv_id=view.findViewById(R.id.tv_id);
        tv_body=view.findViewById(R.id.tv_body);
        bt_health = view.findViewById(R.id.bt_health);
        dbHelper_body = new DBHelper_body(getContext());
        bodyitems = new ArrayList<>();
        bodyitems=dbHelper_body.selectBody();

        Intent intent = getActivity().getIntent();
        String userGender = intent.getStringExtra("userGender");
        tv_id.setText(MainActivity.UserID+"님 환영합니다.");
        try {
            if (bodyitems.get(bodyitems.size()-1).getID().isEmpty()){
                tv_body.setText("신체정보를 입력해주세요");}
            else {
                tv_body.setText("신체정보 업데이트");
            }
        } catch (ArrayIndexOutOfBoundsException e){
            tv_body.setText("신체정보를 입력해주세요");
        }



        tv_body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),BodyActivity.class);
                intent.putExtra("userGender",userGender);
                startActivity(intent);



            }
        });

        //임시로 만든 일정추가 액티비티 전환버튼
        bt_health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(),HealthActivity.class);
                startActivity(intent1);
            }
        });




        return view;

    }


}
