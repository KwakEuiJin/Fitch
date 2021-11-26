package org.app.gimalpro;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Middle_list extends RecyclerView.Adapter<Middle_list.PVH> {
    ArrayList<Toeat> toeats;
    Context context;
    DBHelp_supplementlist dbHelpSupplementlist;

    public Middle_list(ArrayList<Toeat> toeats, Context context){
        this.toeats = toeats;
        this.context = context;
        dbHelpSupplementlist = new DBHelp_supplementlist(context);
    }
    @NonNull
    @Override
    public PVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View hd  = LayoutInflater.from(parent.getContext()).inflate(R.layout.supplement_list,parent,false);
        return new PVH(hd);
    }

    @Override
    public void onBindViewHolder(@NonNull PVH holder, int position) {
        holder.name.setText(toeats.get(position).getname());
        holder.nutrition.setText(toeats.get(position).getnut());

    }

    @Override
    public int getItemCount() {
        return toeats.size();
    }

    public class PVH extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView nutrition;
        public PVH(@NonNull View i) {
            super(i);
            name = i.findViewById(R.id.name);
            nutrition = i.findViewById(R.id.nutrition);

            Dialog dialog = new Dialog(context, android.R.style.Theme_Material_Light_Dialog);
            dialog.setContentView(R.layout.supplement_content);
            EditText tvName = dialog.findViewById(R.id.tvName);
            EditText tvNut = dialog.findViewById(R.id.tvNut);

            int a = getAdapterPosition();
            Toeat toeat = toeats.get(a);
            tvName.setText(toeat.getname());
            tvNut.setText(toeat.getnut());
        }
    }

    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }

}
