package org.app.gimalpro;

import android.app.Dialog;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HealthActivity extends AppCompatActivity {

    private RecyclerView rv_todo;
    private ArrayList<Todoitem> todoitems;
    private DBHelp_health_list dbHelpHealthlist;
    private Adapter adapter;
    CalendarView calendar;
    public static String futuredate;
    Button bt_insert;
    int _year,_month,_dayOfMonth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        dbHelpHealthlist = new DBHelp_health_list(this);
        rv_todo = findViewById(R.id.rv_todo);
        todoitems = new ArrayList<>();

        //캘린더 코드
        calendar = findViewById(R.id.calendar);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                _year =year;
                _month=month+1;
                _dayOfMonth=dayOfMonth;
                String futureTime = String.format("%d년 %d월 %d일",_year,_month,_dayOfMonth);
                futuredate = futureTime;
                reloadRecentdb(futuredate);
                Toast.makeText(getApplicationContext(), "변경", Toast.LENGTH_SHORT).show();
            }
        });

        bt_insert=findViewById(R.id.bt_insert);
        bt_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(HealthActivity.this, android.R.style.Theme_Material_Light_Dialog);
                dialog.setContentView(R.layout.dialog);
                Spinner spinner = dialog.findViewById(R.id.spinner);
                EditText et_content = dialog.findViewById(R.id.et_content);

                Button btn_ok = dialog.findViewById(R.id.bt_ok);
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //db insert
                        String futureTime = String.format("%d년 %d월 %d일",_year,_month,_dayOfMonth);
                        futuredate = futureTime;
                        String currentTime =new SimpleDateFormat("yyyy-MM/dd HH:mm:ss").format(new Date());//현재시간 받아오기
                        String exercise = spinner.getSelectedItem().toString();
                        dbHelpHealthlist.insertTodo(MainActivity.UserID,exercise,et_content.getText().toString(),currentTime,futureTime);
                        //ui insert
                        Todoitem item = new Todoitem();
                        item.setTitle(exercise);
                        item.setContent(et_content.getText().toString());
                        item.setFuturedate(futureTime);

                        adapter.additem(item);
                        rv_todo.smoothScrollToPosition(0);
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "할 일 목록이 추가되었습니다.", Toast.LENGTH_SHORT).show();


                    }
                });
                dialog.show();
                loadRecentdb(futuredate);
            }
        });


        //최소날짜 지정
        setCalendar(calendar);
        //아래함수
        loadRecentdb(futuredate);
            }


    public void setCalendar(CalendarView calendar) {
        this.calendar = calendar;
        long selectedDate = calendar.getDate();
        DateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
        DateFormat dateFormat_year = new SimpleDateFormat("yyyy");
        DateFormat dateFormat_month = new SimpleDateFormat("MM");
        DateFormat dateFormat_day = new SimpleDateFormat("dd");

        futuredate=dateFormat.format(selectedDate);
        _year= Integer.parseInt(dateFormat_year.format(selectedDate));
        _month=Integer.parseInt(dateFormat_month.format(selectedDate));
        _dayOfMonth=Integer.parseInt(dateFormat_day.format(selectedDate));

        Toast.makeText(getApplicationContext(), futuredate, Toast.LENGTH_SHORT).show();
        calendar.setMinDate(selectedDate);
    }

    private void loadRecentdb(String _futuredate) {
        //저장되어있던 db가져오는 함수
        todoitems= dbHelpHealthlist.getTodolist(_futuredate);
        if (adapter==null){
            adapter = new Adapter(todoitems,this);
            rv_todo.setHasFixedSize(true);
            rv_todo.setAdapter(adapter);
        }
    }

    private void reloadRecentdb(String _futuredate) {
        //저장되어있던 db가져오는 함수
       LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
       rv_todo.setLayoutManager(linearLayoutManager);
       todoitems= dbHelpHealthlist.getTodolist(_futuredate);
       adapter = new Adapter(todoitems,this);
       rv_todo.setHasFixedSize(true);
       rv_todo.setAdapter(adapter);

    }



    @Override
    protected void onResume() {
        super.onResume();
        loadRecentdb(futuredate);
    }
}