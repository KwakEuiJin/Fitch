package org.app.gimalpro;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Adapter2 extends RecyclerView.Adapter<Adapter2.ViewHolder> {

    private ArrayList<Todoitem> todoitems;
    private Context context;
    private DBHelp_health_list dbHelpHealthlist;
    private String exercise;

    public Adapter2(ArrayList<Todoitem> todoitems, Context context) {
        this.todoitems = todoitems;
        this.context = context;
        dbHelpHealthlist = new DBHelp_health_list(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list2,parent,false);
        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_title.setText(todoitems.get(position).getTitle());
        holder.tv_content.setText(todoitems.get(position).getContent());
        holder.tv_date.setText((position+1)+")");
        int number = todoitems.get(position).getNUMBER();
        holder.aSwitch.setOnCheckedChangeListener(null);

        if (todoitems.get(position).getSwitch_chek()==0){
            holder.aSwitch.setChecked(false);
        }
        else {
            holder.aSwitch.setChecked(true);
        }
        holder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dbHelpHealthlist.updateTodo_switch(MainActivity.UserID, 1, number);
                }
                else {
                    dbHelpHealthlist.updateTodo_switch(MainActivity.UserID, 0, number);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return todoitems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title;
        private TextView tv_content;
        private TextView tv_date;
        private Switch aSwitch;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_content=itemView.findViewById(R.id. tv_content);
            tv_date=itemView.findViewById(R.id.tv_date);
            aSwitch=itemView.findViewById(R.id.switch1);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int curpos = getAdapterPosition(); //아이템 위치
                    Todoitem todoitem = todoitems.get(curpos);
                    if (aSwitch.isChecked()){
                        todoitem.setSwitch_chek(1);}
                    else {
                        todoitem.setSwitch_chek(0);
                    }

                }
            });

        }

        }
    //새로운 게시글 작성 함수
    public void additem(Todoitem _item){
        todoitems.add(0,_item); //항상 리스트의 첫재쭐에 넣음
        notifyItemInserted(0);


    }





}
