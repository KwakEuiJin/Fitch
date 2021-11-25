package org.app.gimalpro;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper_body extends SQLiteOpenHelper {
    private static final int DB_version =1;
    private static final String DB_name ="User_body.db";


    public DBHelper_body(@Nullable Context context) {
        super(context, DB_name, null, DB_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Body (NUMBER INTEGER PRIMARY KEY AUTOINCREMENT, ID TEXT NOT NULL, Height REAL NOT NULL, Weight REAL NOT NULL, Muscle REAL NOT NULL, Fat REAL NOT NULL, Muscle_level INTEGER NOT NULL, Fat_level INTEGER NOT NULL, Kcal REAL DEFAULT 0)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }



    public ArrayList<Bodyitem> selectBody(){
        ArrayList<Bodyitem> bodyitems = new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
        String _ID = MainActivity.UserID;
        Cursor cursor = db.rawQuery("SELECT * FROM Body WHERE ID='"+_ID+"'",null);
        if (cursor.getCount() !=0){    //if문의 getCount가 0이 아니라는 의미는 db에 정보가 있다는 뜻
            while(cursor.moveToNext()){
                int NUMBER = cursor.getInt(cursor.getColumnIndexOrThrow("NUMBER"));
                String ID = cursor.getString(cursor.getColumnIndexOrThrow("ID"));
                Double Height = cursor.getDouble(cursor.getColumnIndexOrThrow("Height"));
                Double Weight = cursor.getDouble(cursor.getColumnIndexOrThrow("Weight"));
                Double Muscle = cursor.getDouble(cursor.getColumnIndexOrThrow("Muscle"));
                Double Fat = cursor.getDouble(cursor.getColumnIndexOrThrow("Fat"));
                int Muscle_level= cursor.getInt(cursor.getColumnIndexOrThrow("Muscle_level"));
                int Fat_level= cursor.getInt(cursor.getColumnIndexOrThrow("Fat_level"));
                Double Kcal = cursor.getDouble(cursor.getColumnIndexOrThrow("Kcal"));

                Bodyitem bodyitem = new Bodyitem();
                bodyitem.setNUMBER(NUMBER);
                bodyitem.setID(ID);
                bodyitem.setHeight(Height);
                bodyitem.setWeight(Weight);
                bodyitem.setMuscle(Muscle);
                bodyitem.setFat(Fat);
                bodyitem.setMuscle_level(Muscle_level);
                bodyitem.setFat_level(Fat_level);
                bodyitem.setKcal(Kcal);
                bodyitems.add(bodyitem);
            }
    }
        cursor.close();
        return bodyitems;
    }



    public void insertBody(String _ID,double _Height,double _Weight, double _Muscle, double _Fat,int _Muscle_level, int _Fat_level){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO Body(ID ,Height, Weight, Muscle,Fat,Muscle_level,Fat_level) VALUES ('"+_ID+"','"+_Height+"', '"+ _Weight+"','"+_Muscle+"','"+_Fat+"','"+_Muscle_level+"','"+_Fat_level+"');");
    }
    public void updateBody(String _ID,double _Height, double _Weight ,double _Muscle,  double _Fat,double _NUMBER,int _Muscle_level, int _Fat_level ){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE Body SET Height ='"+_Height+"',Weight='"+_Weight+"',Muscle ='"+_Muscle+"',Fat='"+_Fat+"',Muscle_level='"+_Muscle_level+"',Fat_level='"+_Fat_level+"' WHERE ID='"+_ID+"' AND NUMBER ='"+_NUMBER+"'");
    }
    public void updateKcal(String _ID,Double _Kcal){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE Body SET Kcal='"+_Kcal+"' WHERE ID='"+_ID+"'");
    }

    public void deleteBody(String _ID,int _NUMBER){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM Body WHERE ID = '"+_ID+"' AND NUMBER='"+_NUMBER+"'");
    }
}
