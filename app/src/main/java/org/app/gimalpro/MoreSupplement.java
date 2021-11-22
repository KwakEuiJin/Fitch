package org.app.gimalpro;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MoreSupplement extends SQLiteOpenHelper {
    private static final int DB_version =1;
    private static final String DB_name ="User_body.db2";
    public static int F_L, M_L;

    public MoreSupplement(@Nullable Context context){
        super(context, DB_name, null, DB_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Body (NUMBER INTEGER PRIMARY KEY AUTOINCREMENT, ID TEXT NOT NULL, Height REAL NOT NULL, Weight REAL NOT NULL, Muscle REAL NOT NULL, Fat REAL NOT NULL, Muscle_level INTEGER NOT NULL, Fat_level INTEGER NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public void start(){

        SQLiteDatabase db=getReadableDatabase();
        String _ID = LoginActivity.UserID;
        Cursor cursor = db.rawQuery("SELECT * FROM Body WHERE ID='"+_ID+"'",null);
        if (cursor.getCount() !=0) {
            while (cursor.moveToNext()) {
                int NUMBER = cursor.getInt(cursor.getColumnIndexOrThrow("NUMBER"));
                String ID = cursor.getString(cursor.getColumnIndexOrThrow("ID"));
                int Muscle_level = cursor.getInt(cursor.getColumnIndexOrThrow("Muscle_level"));
                int Fat_level = cursor.getInt(cursor.getColumnIndexOrThrow("Fat_level"));

                Bodyitem FMitem = new Bodyitem();
                FMitem.setNUMBER(NUMBER);
                FMitem.setID(ID);
                FMitem.setFat_level(Fat_level);
                FMitem.setMuscle_level(Muscle_level);
                F_L=Fat_level;
                M_L=Muscle_level;
            }
        }
        cursor.close();

    }

}
