package com.pmdm.register;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RegisterDbHelper extends SQLiteOpenHelper {

    private static int version = 1;
    private static String name = "Register.db";
    private static SQLiteDatabase.CursorFactory factory = null;

    private static String sqlCreateTableRegister = "CREATE TABLE REGISTER(" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT NOT NULL UNIQUE," +
            "value REAL)";

    private static String sqlDropTableRegister =
            "DROP TABLE IF EXISTS REGISTER";

    public RegisterDbHelper(Context context) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreateTableRegister);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(sqlDropTableRegister);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
