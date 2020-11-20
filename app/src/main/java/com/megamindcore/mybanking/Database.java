package com.megamindcore.mybanking;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;



public  class Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAMED = "users_data.db";
    public static final String basic_users_list  = "basic_users_list";


    public static final String Ton_1 = "Account_no";
    public static final String Ton_2 = "Name";
    public static final String Ton_3 = "Balance";
    public static final String Ton_4 = "Email";


    public Database(Context context) { super(context, DATABASE_NAMED, null, 1); }


    public static void openDataBase() {
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + basic_users_list +"(Account_no TEXT PRIMARY KEY,Name TEXT,Balance TEXT,Email TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+basic_users_list);
        onCreate(db);
    }





    public boolean insert_user_data(String Account_no ,String Name,String Balance,String Email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Ton_1,Account_no);
        contentValues.put(Ton_2,Name);
        contentValues.put(Ton_3,Balance);
        contentValues.put(Ton_4,Email);
        long result = db.insert(basic_users_list ,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }



    public boolean update_user_data(String Account_no ,String Name,String Balance,String Email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Ton_1,Account_no);
        contentValues.put(Ton_2,Name);
        contentValues.put(Ton_3,Balance);
        contentValues.put(Ton_4,Email);
        long result = db.update(basic_users_list ,contentValues, "Account_no= '" + Account_no+"'", null);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor Get_basic_users_list (int start,int end) {
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