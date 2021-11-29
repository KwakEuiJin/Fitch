package org.app.gimalpro;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelp_supplementlist extends SQLiteOpenHelper {
    private static final int DB_version =1;
    private static final String DB_name ="User.db_sup";


    public DBHelp_supplementlist(@Nullable Context context) {
        super(context, DB_name, null, DB_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Toeatlist (num INTEGER PRIMARY KEY AUTOINCREMENT, id TEXT NOT NULL, name TEXT NOT NULL, nut TEXT NOT NULL, switch_sup INTEGER DEFAULT 0)");
    }

    public ArrayList<Toeat> getToeatlist(){
        ArrayList<Toeat> toeats = new ArrayList<>();
        String _id=MainActivity.UserID;
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Toeatlist WHERE id='"+_id+"' ",null);
        if (cursor.getCount() !=0){    //if문의 getCount가 0이 아니라는 의미는 db에 정보가 있다는 뜻
            while(cursor.moveToNext()){
                int num = cursor.getInt(cursor.getColumnIndexOrThrow("num"));
                String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String nut= cursor.getString(cursor.getColumnIndexOrThrow("nut"));
                int switch_sup = cursor.getInt(cursor.getColumnIndexOrThrow("switch_sup"));

                Toeat toeat =new Toeat();
                toeat.setNum(num);
                toeat.setId(id);
                toeat.setName(name);
                toeat.setNut(nut);
                toeat.setSwitch_sup(switch_sup);
                toeats.add(toeat);


            }
        }
        cursor.close();
        return toeats;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
    public void insertToeat(String _id,String _name, String _nut){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO Toeatlist(id ,name, nut) VALUES ('"+_id+"','"+_name+"', '"+_nut+"');");
    }

    public void updateToeat(String _id,String _name, String _nut){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE Toeatlist SET name = '"+_name+"',nut='"+_nut+"' WHERE id='"+_id+"'");

    }
    public void updateswtich_sup(String _id,int _swtich_sup, int number){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE Toeatlist SET switch_sup = '"+_swtich_sup+"' WHERE id='"+_id+"' AND num = '"+number+"'");

    }


    public void deleteToeat(String _id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM Toeatlist WHERE id = '"+_id+"'");
    }
}
