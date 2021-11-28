package org.app.gimalpro;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private ArrayList<Todoitem> todoitems;
    private Context context;
    private DBHelp_health_list dbHelpHealthlist;
    private String exercise;

    public Adapter(ArrayList<Todoitem> todoitems, Context context) {
        this.todoitems = todoitems;
        this.context = context;
        dbHelpHealthlist = new DBHelp_health_list(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_title.setText(todoitems.get(position).getTitle());
        holder.tv_content.setText(todoitems.get(position).getContent());
        holder.tv_date.setText(todoitems.get(position).getFuturedate());

    }

    @Override
    public int getItemCount() {
        return todoitems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title;
        private TextView tv_content;
        private TextView tv_date;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_content=itemView.findViewById(R.id. tv_content);
            tv_date=itemView.findViewById(R.id.tv_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int curpos = getAdapterPosition(); //아이템 위치
                    Todoitem todoitem = todoitems.get(curpos);

                    String[] strChoiceitems = {"수정하기","삭제하기"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("원하는 작업을 선택하시오");
                    builder.setItems(strChoiceitems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int position) {
                            if (position ==0){
                                //수정(update)
                                Dialog dialog = new Dialog(context, android.R.style.Theme_Material_Light_Dialog);
                                dialog.setContentView(R.layout.dialog);
                                Spinner spinner = dialog.findViewById(R.id.spinner);
                                EditText et_content = dialog.findViewById(R.id.et_content);
                                Button btn_ok = dialog.findViewById(R.id.bt_ok);
                                //스피너 예전 데이터로 셀렉션 시켜놓는 코드
                                int spinner_id=0;
                                if(todoitem.getTitle().contains("조깅")){
                                    spinner_id=1;
                                }
                                else if (todoitem.getTitle().contains("푸쉬업")){
                                    spinner_id=2;
                                }
                                spinner.setSelection(spinner_id);
                                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        exercise=spinner.getSelectedItem().toString();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                                et_content.setText(todoitem.getContent());

                                //커서를 텍스트 마지막으로 이동하여 입력할때 편리함 추구
                                et_content.setSelection(et_content.getText().length());

                                btn_ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //db update
                                        String tittle = exercise;
                                        String content = et_content.getText().toString();
                                        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());//현재시간 받아오기
                                        String beforedate = todoitem.getWritedate();
                                        String futuredate = todoitem.getFuturedate();
                                        dbHelpHealthlist.updateTodo(MainActivity.UserID,tittle,content,currentTime,beforedate);

                                        //ui update
                                        todoitem.setTitle(tittle);
                                        todoitem.setContent(content);
                                        todoitem.setWritedate(futuredate);
                                        notifyItemChanged(curpos,todoitem);
                                        dialog.dismiss();
                                        Toast.makeText(context.getApplicationContext(), "목록수정 완료", Toast.LENGTH_SHORT).show();


                                    }
                                });
                                dialog.show();
                            }
                            else if (position ==1){
                                //테이블 삭제
                                String beforedate = todoitem.getWritedate();
                                dbHelpHealthlist.deleteTodo(beforedate,MainActivity.UserID);

                                //delete ui
                                todoitems.remove(curpos);
                                notifyItemRemoved(curpos);
                                Toast.makeText(context, "목록이 제거되었습니다", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                    builder.show();
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
