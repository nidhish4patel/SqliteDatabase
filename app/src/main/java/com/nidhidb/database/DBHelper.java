package com.nidhidb.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nidhidb.model.UserModel;

/**
 * Created by nidhi on 6/14/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "nidhish";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME="registration";

    public static final String COLUMN_USER_NAME = "name";
    public static final String COLUMN_USER_MOB = "mobile";
    public static final String COLUMN_USER_ADDRESS = "address";


    //create table tablename(column1 datatype (10), column2 datatype(50))

    public static final String CREATE_TABLE = "create table "+TABLE_NAME+"("
            +COLUMN_USER_NAME +" VARCHAR(50),"
            +COLUMN_USER_MOB +" VARCHAR(15),"
            +COLUMN_USER_ADDRESS +" VARCHAR(500))";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //used to create database.
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //to update db on diff versions of apk.
        db.execSQL("drop table if exists "+TABLE_NAME);
        onCreate(db);
    }


    public long saveData(UserModel userModel){
        //Open database.
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_NAME,userModel.getName());
        contentValues.put(COLUMN_USER_MOB, userModel.getMobile());
        contentValues.put(COLUMN_USER_ADDRESS,userModel.getAddress());
        long id = db.insert(TABLE_NAME,null,contentValues);
        db.close();
        return id;
    }

    public UserModel getData(String name){
        //Open database.
        SQLiteDatabase db = getWritableDatabase();
        UserModel userModel = new UserModel();
        Cursor cursor = db.rawQuery("select * from "+TABLE_NAME+" where "+COLUMN_USER_NAME+"='"+name+"'",null);
        if(cursor!=null) {
            while (cursor.moveToNext()) {
                userModel.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                userModel.setMobile(cursor.getString(cursor.getColumnIndex(COLUMN_USER_MOB)));
                userModel.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ADDRESS)));
            }
        }
        return userModel;
    }


}
