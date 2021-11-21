package org.app.gimalpro;

import android.app.Dialog;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HealthActivity extends AppCompatActivity {

    private RecyclerView rv_todo;
    private ArrayList<Todoitem> todoitems;
    private DBHelp_health_list dbHelpHealthlist;
    private Adapter adapter;
    CalendarView calendar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        //테이블이 빈경우 재생성
        try {
            setInit();
        }
        catch (SQLiteException e){
            dbHelpHealthlist.onCreate(dbHelpHealthlist.getWritableDatabase());
        }
        //최소날짜 지정
        setCalendar(calendar);
            }

    private void setInit() {
        dbHelpHealthlist = new DBHelp_health_list(this);
        rv_todo = findViewById(R.id.rv_todo);
        todoitems = new ArrayList<>();

        //캘린더 코드
        calendar = findViewById(R.id.calendar);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Dialog dialog = new Dialog(HealthActivity.this, android.R.style.Theme_Material_Light_Dialog);
                dialog.setContentView(R.layout.dialog);
                EditText et_title = dialog.findViewById(R.id.et_title);
                EditText et_content = dialog.findViewById(R.id.et_content);
                Button btn_ok = dialog.findViewById(R.id.bt_ok);
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //db insert
                        String futureTime = String.format("%d년 %d월 %d일 ",year,month,dayOfMonth);
                        String currentTime =new SimpleDateFormat("yyyy-MM/dd HH:mm:ss").format(new Date());//현재시간 받아오기

                        dbHelpHealthlist.insertTodo(LoginActivity.UserID,et_title.getText().toString(),et_content.getText().toString(),currentTime,futureTime);
                        //ui insert
                        Todoitem item = new Todoitem();
                        item.setTitle(et_title.getText().toString());
                        item.setContent(et_content.getText().toString());
                        item.setWritedate(futureTime);

                        adapter.additem(item);
                        rv_todo.smoothScrollToPosition(0);
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "할 일 목록이 추가되었습니다.", Toast.LENGTH_SHORT).show();

                    }
                });
                dialog.show();
            }
        });

        //아래함수
        loadRecentdb();

    }

    public void setCalendar(CalendarView calendar) {
        this.calendar = calendar;
        long selectedDate = calendar.getDate();
        calendar.setMinDate(selectedDate);
    }

    private void loadRecentdb() {
        //저장되어있던 db가져오는 함수
        todoitems= dbHelpHealthlist.getTodolist();
        if (adapter==null){
            adapter = new Adapter(todoitems,this);
            rv_todo.setHasFixedSize(true);
            rv_todo.setAdapter(adapter);


        }
    }
}