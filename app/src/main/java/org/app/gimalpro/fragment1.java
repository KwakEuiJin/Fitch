package org.app.gimalpro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.SharedElementCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class fragment1 extends Fragment {
    private View view;
    TextView tv_id,tv_body;
    private DBHelper_body dbHelper_body;
    private ArrayList<Bodyitem> bodyitems;
    String userGender;
    Button bt_health;
    String url_set="https://img.youtube.com/vi/";
    String url_file="/0.jpg";
    ImageView imv_ut1,imv_ut2;
    ImageView imv_sup1,imv_sup2;
    String url_u1_id="lKwZ2DU4P-A";
    String url_u2_id="Iaa8YNDRbhg";
    String url_sup1="https://static.thcdn.com/images/small/webp/widgets/83-kr/32/1-052732.png";
    String url_sup2="https://static.thcdn.com/images/small/webp/widgets/83-kr/42/4-052742.png";


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
        imv_ut1=view.findViewById(R.id.imv_ut1);
        imv_ut2=view.findViewById(R.id.imv_ut2);
        imv_sup1=view.findViewById(R.id.imv_sup1);
        imv_sup2=view.findViewById(R.id.imv_sup2);

        Glide.with(getContext()).load(url_set+url_u1_id+url_file).into(imv_ut1);
        Glide.with(getContext()).load(url_set+url_u2_id+url_file).into(imv_ut2);
        Glide.with(getContext()).load(url_sup1).into(imv_sup1);
        Glide.with(getContext()).load(url_sup2).into(imv_sup2);

        imv_ut1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UtubePlay.class);
                intent.putExtra("id",url_u1_id);
                startActivity(intent);
            }

        });




        Intent intent = getActivity().getIntent();
        userGender = intent.getStringExtra("userGender");
        tv_id.setText(MainActivity.UserID+"님 환영합니다.");
        try {
            if (bodyitems.get(bodyitems.size()-1).getID().isEmpty()){
                tv_body.setText("신체정보 추가하기");}
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
