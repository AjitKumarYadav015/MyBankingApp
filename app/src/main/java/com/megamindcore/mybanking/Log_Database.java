package com.megamindcore.mybanking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public  class Log_Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAMED = "log_transfer_data.db";
    public static final String basic_users_list  = "log_users_list";


    public static final String Ton_1 = "Timestamp";
    public static final String Ton_2 = "Sender_Account_no";
    public static final String Ton_3 = "Sender_Name";
    public static final String Ton_4 = "Receiver_Account_no";
    public static final String Ton_5 = "Receiver_Name";
    public static final String Ton_6 = "Balance";


    public Log_Database(Context context) { super(context, DATABASE_NAMED, null, 1); }


    public static void openDataBase() {
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + basic_users_list +"(Timestamp TEXT PRIMARY KEY,Sender_Account_no TEXT,Sender_Name TEXT,Receiver_Account_no TEXT,Receiver_Name TEXT,Balance TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+basic_users_list);
        onCreate(db);
    }





    public boolean insert_log_data(String Timestamp ,String Sender_Account_no,String Sender_Name,String Receiver_Account_no,String Receiver_Name,String Balance){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Ton_1,Timestamp);
        contentValues.put(Ton_2,Sender_Account_no);
        contentValues.put(Ton_3,Sender_Name);
        contentValues.put(Ton_4,Receiver_Account_no);
        contentValues.put(Ton_5,Receiver_Name);
        contentValues.put(Ton_6,Balance);
        long result = db.insert(basic_users_list ,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }



    public boolean update_log_data(String Timestamp ,String Sender_Account_no,String Sender_Name,String Receiver_Account_no,String Receiver_Name,String Balance){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Ton_1,Timestamp);
        contentValues.put(Ton_2,Sender_Account_no);
        contentValues.put(Ton_3,Sender_Name);
        contentValues.put(Ton_4,Receiver_Account_no);
        contentValues.put(Ton_5,Receiver_Name);
        contentValues.put(Ton_6,Balance);
        long result = db.update(basic_users_list ,contentValues, "Account_no= '" + Timestamp+"'", null);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor Get_log_users (int start,int end) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+basic_users_list+" Order By "+Ton_1+" Desc "+ " LIMIT "+start+","+end+" ",null);
        return res;
    }

    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ basic_users_list);
        db.close();
    }


    public boolean isTableExists(String tableName, boolean openDb) {

        SQLiteDatabase myDb = this.getReadableDatabase();

        if(openDb) {
            if(myDb == null || !myDb.isOpen()) {
                myDb = getReadableDatabase();
            }

            if(!myDb.isReadOnly()) {
                myDb.close();
                myDb = getReadableDatabase();
            }
        }
        Cursor cursor = myDb.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+tableName+"'", null);
        if(cursor!=null) {
            if(cursor.getCount()>0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }


}