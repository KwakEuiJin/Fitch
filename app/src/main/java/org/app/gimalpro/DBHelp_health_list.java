package org.app.gimalpro;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelp_health_list extends SQLiteOpenHelper {

    private static final int DB_version =1;
    private static final String DB_name ="User.db";


    public DBHelp_health_list(@Nullable Context context) {
        super(context, DB_name, null, DB_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Todolist (NUMBER INTEGER PRIMARY KEY AUTOINCREMENT, ID TEXT NOT NULL, title TEXT NOT NULL, content TEXT NOT NULL, writedate TEXT NOT NULL ,futuredate TEXT NOT NULL, switch INTEGER DEFAULT 0  )");


    }

    //select문 할일 목록 조회
    public ArrayList<Todoitem> getTodolist(String _futuredate){
        ArrayList<Todoitem> todoitems = new ArrayList<>();
        String _ID=MainActivity.UserID;
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Todolist WHERE ID='"+_ID+"' AND futuredate = '"+_futuredate+"'",null);
        if (cursor.getCount() !=0){    //if문의 getCount가 0이 아니라는 의미는 db에 정보가 있다는 뜻
            while(cursor.moveToNext()){
                int NUMBER = cursor.getInt(cursor.getColumnIndexOrThrow("NUMBER"));
                String ID = cursor.getString(cursor.getColumnIndexOrThrow("ID"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
                String writedate = cursor.getString(cursor.getColumnIndexOrThrow("writedate"));
                String futuredate = cursor.getString(cursor.getColumnIndexOrThrow("futuredate"));
                int switch_check = cursor.getInt(cursor.getColumnIndexOrThrow("switch"));

                Todoitem todoitem =new Todoitem();
                todoitem.setNUMBER(NUMBER);
                todoitem.setID(ID);
                todoitem.setTitle(title);
                todoitem.setContent(content);
                todoitem.setWritedate(writedate);
                todoitem.setFuturedate(futuredate);
                todoitem.setSwitch_chek(switch_check);
                todoitems.add(todoitem);


            }
        }
        cursor.close();
        return todoitems;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
        //insert안에서 오토로 올려주는 애는 굳이 인설트 안해도됨(할일 목록을 널는 코드)
     public void insertTodo(String _ID,String _title, String _content, String _writedate,String _futuredate){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO Todolist(ID ,title, content, writedate,futuredate) VALUES ('"+_ID+"','"+_title+"', '"+_content+"','"+_writedate+"','"+_futuredate+"');");
    }

    //update문 할일 목록 업데이트
    public void updateTodo(String _ID,String _title, String _content ,String _writedate, String _beforedate){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE Todolist SET title = '"+_title+"',content='"+_content+"',writedate = '"+_writedate+"' WHERE writedate='"+_beforedate+"' AND ID='"+_ID+"'");

    }

    public void updateTodo_switch(String _ID, int _switch, int _Number){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE Todolist  SET switch ='"+_switch+"' WHERE NUMBER='"+_Number+"' AND ID='"+_ID+"'");

    }

    //delete문 할일목록 삭제
    public void deleteTodo(String _beforedate,String _ID){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM Todolist WHERE writedate = '"+_beforedate+"' AND ID = '"+_ID+"'");
    }

}
