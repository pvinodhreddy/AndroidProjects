package com.dogs.pet.mylocation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.net.PortUnreachableException;

public class MyDBHandler extends SQLiteOpenHelper{

    SQLiteDatabase database;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DriveMe.db";

   // CAR TABLE COLUMNS

    public static final String TABLE_NAME_CAR = "Car";
    public static final String CAR_ID = "ID";
    public static final String CAR_NAME = "Name";
    public static final String CAR_TYPE = "Type";
    public static final String CAR_DATETIME = "DATETIME";


    // USER TABLE COLUMNS

    public static final String TABLE_NAME_USER = "User";
    public static final String USER_ID = "ID";
    public static final String USER_EMAIL = "EMAIL";
    public static final String USER_PASSWORD = "PASSWORD";
    public static final String USER_FNAME = "FNAME";
    public static final String USER_LNAME = "LNAME";
    public static final String USER_GENDER = "GENDER";
    public static final String USER_MOBILE = "MOBILE";
    public static final String USER_DATETIME = "DATETIME";


    // LOCATION TABLE COLUMNS

    public static final String TABLE_NAME_LOCATION = "Location";
    public static final String LOCATION_ID = "ID";
    public static final String LOCATION_SOURCE = "LOCATION_SRC";
    public static final String LOCATION_DESTINATION = "LOCATION_DEST";
    public static final String LOCATION_AMOUNT = "AMOUNT";
    public static final String LOCATION_CAR_ID = "CAR_ID";
    public static final String LOCATION_USER_ID = "USER_ID";
    public static final String LOCATION_DATETIME = "DATETIME";








    public MyDBHandler(Context context) {


       super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //context.deleteDatabase(DATABASE_NAME);
        database = getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NAME_CAR +" (" + CAR_ID +" TEXT PRIMARY KEY, " + CAR_NAME +" TEXT, " + CAR_TYPE + " TEXT, " +  CAR_DATETIME + " DATETIME )");
        db.execSQL("CREATE TABLE " + TABLE_NAME_USER +" (" + USER_ID +" TEXT PRIMARY KEY, " + USER_EMAIL +" TEXT, " + USER_PASSWORD + " TEXT, " + USER_FNAME + " TEXT, " + USER_LNAME + " TEXT, " + USER_GENDER + " TEXT, " + USER_MOBILE + " TEXT, " +  USER_DATETIME + " DATETIME )");
        db.execSQL("CREATE TABLE " + TABLE_NAME_LOCATION +" (" + LOCATION_ID +" TEXT PRIMARY KEY, " + LOCATION_SOURCE +" TEXT, " + LOCATION_DESTINATION + " TEXT, " + LOCATION_AMOUNT + " TEXT, " + LOCATION_USER_ID + " TEXT, " + LOCATION_CAR_ID + " TEXT, " +  LOCATION_DATETIME + " DATETIME )");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_CAR);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_USER);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_LOCATION);
        onCreate(db);
    }

    public boolean insertuser (String Uid, String Uemail, String Upwd, String Ufname, String Ulname, String Ugender, String Umobile, String Udatetime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_ID, Uid);
        contentValues.put(USER_EMAIL, Uemail);
        contentValues.put(USER_PASSWORD, Upwd);
        contentValues.put(USER_FNAME, Ufname);
        contentValues.put(USER_LNAME, Ulname);
        contentValues.put(USER_GENDER, Ugender);
        contentValues.put(USER_MOBILE, Umobile);
        contentValues.put(USER_DATETIME, Udatetime);
        db.insert(TABLE_NAME_USER, null, contentValues);
        return true;
    }

    public boolean insertlocation (String Lid, String Lsource, String Ldest, String Lamount, String carid, String userid, String Ldatetime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LOCATION_ID, Lid);
        contentValues.put(LOCATION_SOURCE, Lsource);
        contentValues.put(LOCATION_DESTINATION, Ldest);
        contentValues.put(LOCATION_AMOUNT, Lamount);
        contentValues.put(LOCATION_CAR_ID, carid);
        contentValues.put(LOCATION_USER_ID, userid);
        contentValues.put(LOCATION_DATETIME, Ldatetime);
        db.insert(TABLE_NAME_LOCATION, null, contentValues);
        return true;
    }

    public boolean insertcar (String Cid, String Cname, String Ctype, String Cdatetime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CAR_ID, Cid);
        contentValues.put(CAR_NAME, Cname);
        contentValues.put(CAR_TYPE, Ctype);
        contentValues.put(CAR_DATETIME, Cdatetime);
        db.insert(TABLE_NAME_LOCATION, null, contentValues);
        return true;
    }

    public boolean Deleteuser(String demail) {
        SQLiteDatabase db = this.getWritableDatabase();
           db.execSQL("delete from "+TABLE_NAME_USER+" where EMAIL='"+demail+"'");
        //return db.delete(TABLE_NAME_USER, EMAIL +  "=" + id, null) > 0;
        return true;
    }

    public Cursor getuserdata(String uemail) {
        SQLiteDatabase db = this.getWritableDatabase();
      // Cursor res = db.rawQuery(" select * from "+TABLE_NAME_USER+" where EMAIL='"+uemail+"'",null);
        return db.query(TABLE_NAME_USER, new String[] { USER_EMAIL, USER_PASSWORD }, USER_EMAIL+"=?", new String[]{uemail}, null,null, null);

    }



}

