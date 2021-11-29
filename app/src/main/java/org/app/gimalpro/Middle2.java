package org.app.gimalpro;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Middle2 extends RecyclerView.Adapter<Middle2.VH> {
    private ArrayList<Toeat> toeats;
    private Context context;
    private DBHelp_supplementlist dbHelpSupplementlist;

    public Middle2(ArrayList<Toeat> toeats, Context context) {
        this.toeats = toeats;
        this.context = context;
        dbHelpSupplementlist = new DBHelp_supplementlist(context);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View hd  = LayoutInflater.from(parent.getContext()).inflate(R.layout.supplement_list,parent,false);
        return new VH(hd);
    }
    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.name.setText(toeats.get(position).getName());
        holder.nutrition.setText(toeats.get(position).getNut());
        holder.tv_number.setText((position+1)+")");
        int number = toeats.get(position).getNum();
        holder.switch_sup.setOnCheckedChangeListener(null);

        if (toeats.get(position).getSwitch_sup()==0){
            holder.switch_sup.setChecked(false);
        }
        else {
            holder.switch_sup.setChecked(true);
        }
        holder.switch_sup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dbHelpSupplementlist.updateswtich_sup(MainActivity.UserID, 1, number);
                }
                else {
                    dbHelpSupplementlist.updateswtich_sup(MainActivity.UserID, 0, number);
                }
            }
        });

    }

    @Override
    public int getItemCount() { return toeats.size(); }

    public class VH extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView nutrition,tv_number;
        private Switch switch_sup;

        public VH(@NonNull View i){
            super(i);
            name=i.findViewById(R.id.name);
            nutrition=i.findViewById(R.id.nutrition);
            tv_number=i.findViewById(R.id.tv_number);
            switch_sup=i.findViewById(R.id.switch_sup);
        }

    }
}
