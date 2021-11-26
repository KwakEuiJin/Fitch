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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

import javax.xml.namespace.QName;

public class fragment4 extends Fragment {
    private View view;
    RecyclerView l_view;
    Middle_list M_l;
    ArrayList<Toeat> toeats;
    private DBHelp_supplementlist dbHelpSupplementlist;
    Button btn2, btn3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment4,container,false);

        l_view = (RecyclerView) view.findViewById(R.id.list2);
        lstart();

        btn2=view.findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Supplement.class);
                startActivity(intent);
            }
        });

        btn3=view.findViewById(R.id.button3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),SupplementPlus.class);
                startActivity(intent);
            }
        });

        return view;

    }
    private void lstart() {
        dbHelpSupplementlist = new DBHelp_supplementlist(getContext());
        toeats = new ArrayList<Toeat>();
        l_view.setAdapter(M_l);

        loaddb();
    }
    private void loaddb() {
        toeats= dbHelpSupplementlist.getToeatlist();
        if (M_l==null){
            M_l = new Middle_list(toeats, getContext());
            l_view.setHasFixedSize(true);
        }
    }

}