package org.app.gimalpro;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Supplement extends AppCompatActivity {

    private RecyclerView addspm;
    private Button btnadd;
    private ArrayList<Toeat> toeats;
    private DBHelp_supplementlist dbHelpSupplementlist;
    private Middle middle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_supplement);

        setStart();
    }
    private void setStart() {
        dbHelpSupplementlist = new DBHelp_supplementlist(this);
        addspm = findViewById(R.id.addsupplement_rv);
        btnadd = findViewById(R.id.btn_add);
        toeats = new ArrayList<>();

        loaddb();

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(Supplement.this, android.R.style.Theme_Material_Light_Dialog);
                dialog.setContentView(R.layout.supplement_content);
                EditText etName = dialog.findViewById(R.id.etName);
                EditText etNut = dialog.findViewById(R.id.etNut);
                Button btn_fin = dialog.findViewById(R.id.btn_fin);
                btn_fin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dbHelpSupplementlist.insertToeat(LoginActivity.UserID,etName.getText().toString(),etNut.getText().toString());

                        Toeat item = new Toeat();
                        item.setname(etName.getText().toString());
                        item.setnut(etNut.getText().toString());

                        middle.addlist(item);
                        addspm.smoothScrollToPosition(0);
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "영양제가 추가되었습니다.", Toast.LENGTH_SHORT).show();

                    }
                });
                dialog.show();

            }
        });
    }
    private void loaddb() {
        toeats= dbHelpSupplementlist.getToeatlist();
        if (middle==null){
            middle = new Middle(toeats, this);
            addspm.setHasFixedSize(true);
            addspm.setAdapter(middle);


        }
    }
}
