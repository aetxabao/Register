package com.pmdm.register;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.pmdm.register.Register.*;

public class RegisterDbAdapter {

    private Context context;
    private RegisterDbHelper dbHelper;
    private SQLiteDatabase db;

    public RegisterDbAdapter(Context context){
        this.context = context;
    }

    public RegisterDbAdapter open(){
        dbHelper = new RegisterDbHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    public List<Register> selectRegistersOrderedByName() throws SQLException{
        Cursor c = db.query(true, TABLE_REGISTER, REGISTER_COLUMNS,
                null, null, null, null, REGISTER_NAME + " ASC", null);
        return getRegisters(c);
    }

    public List<Register> selectRegistersOrderedByValue() throws SQLException{
        Cursor c = db.query(true, TABLE_REGISTER, REGISTER_COLUMNS,
                null, null, null, null, REGISTER_VALUE + " ASC", null);
        return getRegisters(c);
    }

    public List<Register> getRegisters(Cursor c) throws SQLException{
        List<Register> list = new ArrayList<Register>();
        if (c != null){
            c.moveToFirst();
            if (c.getCount()>0){
                for(int i=0;i<c.getCount();i++){
                    int _id = c.getInt(REGISTER_ID_POS);
                    String name = c.getString(REGISTER_NAME_POS);
                    double value = c.getDouble(REGISTER_VALUE_POS);
                    Register register = new Register(_id,name,value);
                    list.add(register);
                    c.moveToNext();
                }
            }
            c.close();
        }
        return list;
    }

    public double getValue(String name) throws SQLException{
        double d = -1.0;
        Cursor c = db.query(true, TABLE_REGISTER, REGISTER_COLUMNS,
                REGISTER_NAME + "='" + name + "'", null, null, null, null, null);
        if (c!=null){
            if (c.getCount()>0){
                c.moveToFirst();
                d = c.getDouble(REGISTER_VALUE_POS);
            }
        }
        return d;
    }

    public long insert(Register register) throws SQLException{
        return db.insert(TABLE_REGISTER, null, getContentValues(register));
    }

    public int update(Register register) throws SQLException{
        return db.update(TABLE_REGISTER, getContentValues(register),
                REGISTER_NAME + "='" + register.getName() + "'", null);
    }

    protected ContentValues getContentValues(Register register){
        ContentValues values = new ContentValues();
        values.put(REGISTER_NAME, register.getName());
        values.put(REGISTER_VALUE, register.getValue());
        return values;
    }

    public int delete(String name) throws SQLException{
        return db.delete(TABLE_REGISTER, REGISTER_NAME + "='" + name + "'", null);
    }

}
