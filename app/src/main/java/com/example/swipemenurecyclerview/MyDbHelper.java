package com.example.swipemenurecyclerview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyDbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME= "Database12.db";
    public static final String TABLE_NAME= "Register";
    public static final String COL_1= "Id";
    public static final String COL_2= "Name";
    public static final String COL_3= "Email";
    public static final String COL_4= "Phone";

    public MyDbHelper(Context c){
        super(c, DB_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

       String cr="create table "+ TABLE_NAME +"(Id integer primary key autoincrement, Name text, Email text, Phone text)";
       //String c1="create table Register(Id integer primary key autoincrement, Name text, Email text, Phone text)";
       db.execSQL(cr);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS Register");
    }

    public boolean insertData(String name, String email,String phone){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COL_2,name);
        cv.put(COL_3,email);
        cv.put(COL_4,phone);
        long result=db.insert(TABLE_NAME,null,cv);
        db.close();

        if(result==-1)
            return false;
        else
            return true;


    }


    public Cursor getAllData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * From "+ TABLE_NAME,null);
        return res;
    }

    public Integer deleteData(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        int i= db.delete(TABLE_NAME,"ID=?",new String[] {id});
        return i;
    }

    public int fetchSpecificData(String email){
        SQLiteDatabase db=this.getWritableDatabase();
        int id = 0;
        String cr="Select "+COL_1+" From "+ TABLE_NAME +" where "+COL_3+"="+"'"+email+"'";
       Cursor res=db.rawQuery(cr,null);
       while(res.moveToNext()){
           if(res!=null && res.getCount()>0){
                id=res.getInt(0);
           }
       }
        return id;
    }
}
