package org.app.gimalpro;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Middle extends RecyclerView.Adapter<Middle.VH> {
    private ArrayList<Toeat> toeats;
    private Context context;
    private DBHelp_supplementlist dbHelpSupplementlist;

    public Middle(ArrayList<Toeat> toeats, Context context) {
        this.toeats = toeats;
        this.context = context;
        dbHelpSupplementlist = new DBHelp_supplementlist(context);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View hd  = LayoutInflater.from(parent.getContext()).inflate(R.layout.supplement_list2,parent,false);
        return new VH(hd);
    }
    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.name.setText(toeats.get(position).getName());
        holder.nutrition.setText(toeats.get(position).getNut());

    }

    @Override
    public int getItemCount() { return toeats.size(); }

    public class VH extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView nutrition;

        public VH(@NonNull View i){
            super(i);
            name=i.findViewById(R.id.name);
            nutrition=i.findViewById(R.id.nutrition);

            i.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int a = getAdapterPosition();
                    Toeat toeat = toeats.get(a);

                    String[] strChoiceitems = {"수정하기","삭제하기"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("원하는 작업을 선택하시오");
                    builder.setItems(strChoiceitems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int position) {
                            if (position ==0){
                                //수정
                                Dialog dialog = new Dialog(context, android.R.style.Theme_Material_Light_Dialog);
                                dialog.setContentView(R.layout.supplement_content);
                                EditText tvName = dialog.findViewById(R.id.tvName);
                                EditText tvNut = dialog.findViewById(R.id.tvNut);
                                Button btn_fin = dialog.findViewById(R.id.btn_fin);

                                tvName.setText(toeat.getName());
                                tvNut.setText(toeat.getNut());
                                tvName.setSelection(tvName.getText().length());

                                btn_fin.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String Name = tvName.getText().toString();
                                        String Nut = tvNut.getText().toString();
                                        dbHelpSupplementlist.updateToeat(MainActivity.UserID,Name,Nut);

                                        //ui update
                                        toeat.setName(Name);
                                        toeat.setNut(Nut);
                                        notifyItemChanged(a,toeat);
                                        dialog.dismiss();
                                        Toast.makeText(context.getApplicationContext(), "목록수정 완료", Toast.LENGTH_SHORT).show();


                                    }
                                });
                                dialog.show();
                            }
                            else if (position ==1){
                                dbHelpSupplementlist.deleteToeat(MainActivity.UserID);

                                toeats.remove(a);
                                notifyItemRemoved(a);
                                Toast.makeText(context, "목록제거 완료", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                    builder.show();
                }
            });
        }

    }
    public void addlist(Toeat _item){
        toeats.add(0,_item);
        notifyItemInserted(0);

    }
}
