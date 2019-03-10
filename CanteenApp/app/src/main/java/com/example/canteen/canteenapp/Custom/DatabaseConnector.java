package com.example.canteen.canteenapp.Custom;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by VARAD on 12/03/2017.
 */

public class DatabaseConnector extends SQLiteOpenHelper {
     final static String Database_name="Canteen.db";
     final static String Table_name="Orders";
     final static String Col1="Username";
     final static String Col2="Menu";
     final static String Col3="Price";
     final static String Col4="Quantity";
     final static String Col5="Image";
    public DatabaseConnector(Context context) {
        super(context, Database_name ,null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Orders (Username TEXT,Menu TEXT,Price INTEGER,Quantity INTEGER,Image INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Table_name);
        onCreate(db);
    }

    public Boolean onInsert(String username,String menu,String price,String quantity,int imgResource)
    {
        int p=Integer.parseInt(price);
        int q=Integer.parseInt(quantity);
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Col1,username);
        contentValues.put(Col2,menu);
        contentValues.put(Col3,p);
        contentValues.put(Col4,q);
        contentValues.put(Col5,imgResource);
        long result=sqLiteDatabase.insert(Table_name,null,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor onExtract(String username)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from Orders where Username = ?",new String[]{username});
        return cursor;
    }

    public int onDelete(String username,String menu,String price,String quantity)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(Table_name, "Username = ? and Menu = ? and Price = ? and Quantity = ?",new String[] {username,menu,
                price,quantity});
    }
}
