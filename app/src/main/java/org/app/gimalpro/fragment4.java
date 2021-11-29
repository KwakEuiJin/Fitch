package org.app.gimalpro;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class fragment4 extends Fragment {
    private View view;
    private ArrayList<Toeat> toeats;
    private Middle2 middle2;
    private DBHelp_supplementlist dbHelpSupplementlist;
    Button btn2, bt1, bt2, bt3, bt4;
    public static int FatLevel,MuscleLevel,goal;
    public static String rurl;
    RecyclerView rv_supplement;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment4,container,false);
        
        dbHelpSupplementlist=new DBHelp_supplementlist(getContext());
        toeats=dbHelpSupplementlist.getToeatlist();
        rv_supplement=view.findViewById(R.id.rv_supplement);

        loadRecentdb();

        btn2=view.findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Supplement.class);
                startActivity(intent);
            }
        });

        FatLevel=MoreSupplement.F_L;
        MuscleLevel=MoreSupplement.M_L;
        rurl="https://www.myprotein.co.kr/ranges/myvitamins/products.list";
        if(FatLevel==0 && MuscleLevel==0){goal=0;}
        else if(FatLevel==1 && MuscleLevel==0){goal=0;}
        else if(FatLevel==2 && MuscleLevel==0){goal=1;}
        else if(FatLevel==2 && MuscleLevel==1){goal=1;}
        else if(FatLevel==2 && MuscleLevel==2){goal=1;}

        bt1=view.findViewById(R.id.bt1);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(goal==0){rurl="https://www.myprotein.co.kr/goal-selector/male-goals/build-muscle-req/bm-none.list";}
                else{rurl="https://www.myprotein.co.kr/goal-selector/male-goals/lose-weight-req/lw-none.list"; }
                Intent intent = new Intent(getActivity(),SupplementPlus.class);
                startActivity(intent);
            }
        });
        bt2=view.findViewById(R.id.bt2);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(goal==0){rurl="https://www.myprotein.co.kr/goal-selector/male-goals/build-muscle-req/bm-vegan.list";}
                else{rurl="https://www.myprotein.co.kr/goal-selector/male-goals/lose-weight-req/lw-vegan.list";}
                Intent intent = new Intent(getActivity(),SupplementPlus.class);
                startActivity(intent);
            }
        });
        bt3=view.findViewById(R.id.bt3);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(goal==0){rurl="https://www.myprotein.co.kr/goal-selector/male-goals/build-muscle-req/bm-vegetarian.list";}
                else{rurl="https://www.myprotein.co.kr/goal-selector/male-goals/lose-weight-req/lw-vegetarian.list";}
                Intent intent = new Intent(getActivity(),SupplementPlus.class);
                startActivity(intent);
            }
        });
        bt4=view.findViewById(R.id.bt4);
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(goal==0){rurl="https://www.myprotein.co.kr/goal-selector/male-goals/build-muscle-req/bm-gluten-free.list";}
                else{rurl="https://www.myprotein.co.kr/goal-selector/male-goals/lose-weight-req/lw-gluten-free.list";}
                Intent intent = new Intent(getActivity(),SupplementPlus.class);
                startActivity(intent);
            }
        });

        return view;

    }

    private void loadRecentdb() {
        //저장되어있던 db가져오는 함수
        toeats= dbHelpSupplementlist.getToeatlist();
        if (middle2==null){
            middle2 = new Middle2(toeats,getContext());
            rv_supplement.setAdapter(middle2);
        }
    }

}