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
import androidx.fragment.app.Fragment;

public class fragment1 extends Fragment {
    private View view;
    Button bt_inputbody;
    TextView tv_gender;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment1,container,false);
        bt_inputbody=view.findViewById(R.id.bt_inputbody);

        Intent intent = getActivity().getIntent();
        String userGender = intent.getStringExtra("userGender");


        bt_inputbody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),BodyActivity.class);
                intent.putExtra("userGender",userGender);
                startActivity(intent);



            }
        });








        return view;

    }
}
