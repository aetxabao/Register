package com.pmdm.register;

import org.json.JSONException;
import org.json.JSONObject;

public class Register {

    public static final String TABLE_REGISTER = "register";

    public static final String REGISTER_ID = "_id";
    public static final String REGISTER_NAME = "name";
    public static final String REGISTER_VALUE = "value";

    public static final int REGISTER_ID_POS = 0;
    public static final int REGISTER_NAME_POS = 1;
    public static final int REGISTER_VALUE_POS = 2;

    public static final String[] REGISTER_COLUMNS = new String[]{
            REGISTER_ID, REGISTER_NAME, REGISTER_VALUE };

    private int _id = 0;
    private String name = "";
    private double value = 0.0;

    public Register(int _id, String name, double value) {
        this._id = _id;
        this.name = name;
        this.value = value;
    }

    public Register(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public static Register createFromJSON(String json){
        Register register = null;
        if (json == null) return null;
        if (json.trim().length() == 0) return null;
        try {
            JSONObject object = new JSONObject(json);
            register = new Register(object.getInt(REGISTER_ID),
                                    object.getString(REGISTER_NAME),
                                    object.getDouble(REGISTER_VALUE));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return register;
    }

    public String getJSON(){
        String json = null;
        JSONObject object = new JSONObject();
        try {
            object.put(REGISTER_ID, get_id());
            object.put(REGISTER_NAME, getName());
            object.put(REGISTER_VALUE, getValue());
            json = object.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    public boolean equals(Object object){
        boolean b = false;
        if (object == null) return false;
        Register r = (Register)object;
        b = ((_id==r.get_id())&&(name.equals(r.getName()))&&(value==r.getValue()));
        return b;
    }

}
