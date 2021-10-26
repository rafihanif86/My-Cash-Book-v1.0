package com.example.mycashbookv10;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, "myCashBookV1.0", null, 1);
        db = this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE session(id integer PRIMARY KEY, login text, username text)");
        db.execSQL("CREATE TABLE user(id integer PRIMARY KEY AUTOINCREMENT, username text, password)");
        db.execSQL("CREATE TABLE kas(id integer PRIMARY KEY AUTOINCREMENT, userid text, jenis text, jumlah text, keterangan text, tanggal datetime)");
        db.execSQL("INSERT INTO session(id, login) VALUES(1, 'kosong')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS session");
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS kas");
        onCreate(db);
    }

    //check session
    public Boolean checkSession(){
        Cursor cursor = db.rawQuery("SELECT * FROM session WHERE id = 1 AND login != 'kosong' ", null);
        return cursor.getCount() > 0;
    }

    //upgrade session
    public Boolean upgradeSession(String sessionValues, int id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("login", sessionValues);
        long update = db.update("session", contentValues, "id=" + id, null);
        return update != -1;
    }

    //insert User
    public boolean insertUser(String username, String password){
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long insert = db.insert("user", null, contentValues);
        return insert != -1;
    }

    //check login
    public boolean checkLogin(String username, String password){
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE username = ? AND password = ?", new String[]{username, password});
        Log.d("Data", "onCreate: " + cursor);
        return cursor.getCount() > 0;
    }

    //search user username
    public Cursor userByUsername(String username){
        Cursor cur = db.rawQuery("SELECT * FROM user WHERE username = ?", new String[]{username});
        return cur;
    }

    //search user id
    public Cursor userById(String id){
        Cursor cur = db.rawQuery("SELECT * FROM user WHERE id = ?", new String[]{id});
        return cur;
    }

    public Cursor sessionUser(){
        Cursor cursor = db.rawQuery("SELECT * FROM session WHERE id = 1 AND login != 'kosong' ", null);
        return cursor;
    }

    //insert Pemasukan
    public boolean insertCashFlow(String tanggal, String userId, String jenis, String nominal, String keterangan){
        ContentValues contentValues = new ContentValues();
        contentValues.put("userid", userId);
        contentValues.put("jenis", jenis);
        contentValues.put("jumlah", nominal);
        contentValues.put("keterangan", keterangan);
        contentValues.put("tanggal", tanggal);
        long insert = db.insert("kas", null, contentValues);
        return insert != -1;
    }

    //jumlah pemasukan
    public Cursor jumlahKas(String id, String jenis){
        Cursor cur = db.rawQuery("SELECT id, SUM(jumlah) AS pemasukan FROM kas WHERE jenis = ? AND userid = ?", new String[]{jenis, id});
        return cur;
    }

    //ubah password
    public boolean updatePassword(String userId, String password){
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", password);
        long update = db.update("user", contentValues, "id =" + userId, null);
        return update != -1;
    }

    //jumlah pemasukan
    public Cursor kas(String id){
        Cursor cur = db.rawQuery("SELECT * FROM kas WHERE userid = ? ORDER BY tanggal DESC", new String[]{id});
        return cur;
    }
}
