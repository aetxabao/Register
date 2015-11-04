package com.pmdm.register;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected TextView txtName;
    protected TextView txtValue;
    protected TextView txtRegisters;

    protected String name;
    protected double value;

    private RegisterDbAdapter rda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtName = (TextView) findViewById(R.id.TxtName);
        txtValue = (TextView) findViewById(R.id.TxtValue);
        txtRegisters = (TextView) findViewById(R.id.TxtRegisters);

        rda = new RegisterDbAdapter(this);
        rda.open();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        rda.close();
    }

    protected boolean getNameValueFromGUI(){
        value = -1.0;
        name = txtName.getText().toString();
        try {
            value = Double.parseDouble(txtValue.getText().toString());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void onBtnInsertClick(View v){
        if (!getNameValueFromGUI()){
            Toast.makeText(getApplicationContext(),"Incorrect value!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Register register = new Register(name,value);
        try {
            if (rda.insert(register)==-1){
                Toast.makeText(getApplicationContext(),"Name exists,\nplease, try update.",
                        Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (SQLException e) {
            txtRegisters.setText("onBtnInsertClick : " + e.toString());
            return;
        }
        Toast.makeText(getApplicationContext(),"Insert "+name+" "+value,
                Toast.LENGTH_SHORT).show();
    }

    public void onBtnUpdateClick(View v){
        if (!getNameValueFromGUI()){
            Toast.makeText(getApplicationContext(),"Incorrect value!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Register register = new Register(name,value);
        try {
            if (rda.update(register)==0){
                Toast.makeText(getApplicationContext(),"'" + name + "' doesn't exist.",
                        Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (SQLException e) {
            txtRegisters.setText("onBtnUpdateClick : " + e.toString());
            return;
        }
        Toast.makeText(getApplicationContext(),"Updated "+name+" "+value,
                Toast.LENGTH_SHORT).show();
    }

    public void onBtnDeleteClick(View v){
        getNameValueFromGUI();
        try {
            if (rda.delete(name)==0){
                Toast.makeText(getApplicationContext(),"'" + name + "' doesn't exist.",
                        Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (SQLException e) {
            txtRegisters.setText("onBtnDeleteClick : " + e.toString());
            return;
        }
        Toast.makeText(getApplicationContext(),"Deleted "+name,
                Toast.LENGTH_SHORT).show();
    }

    public void onBtnGetValueClick(View v){
        getNameValueFromGUI();
        try {
            if ((value = rda.getValue(name))<0){
                Toast.makeText(getApplicationContext(),"'" + name + "' doesn't exist.",
                        Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (SQLException e) {
            txtRegisters.setText("onBtnGetValueClick : " + e.toString());
            return;
        }
        txtValue.setText(String.valueOf(value));
        Toast.makeText(getApplicationContext(),"GetValue "+name+" "+value,
                Toast.LENGTH_SHORT).show();
    }

    public void onBtnListByNameClick(View v){
        List<Register> list = null;
        try {
            list = rda.selectRegistersOrderedByName();
        } catch (SQLException e) {
            txtRegisters.setText("onBtnListByNameClick : " + e.toString());
            return;
        }
        StringBuilder sb = new StringBuilder();
        for(Register register:list){
            sb.append(register.getName()+"-->"+register.getValue()+"\n");
        }
        txtRegisters.setText(sb.toString());
        Toast.makeText(getApplicationContext(),"ListByName",
                Toast.LENGTH_SHORT).show();
    }

    public void onBtnListByValueClick(View v){
        List<Register> list = null;
        try {
            list = rda.selectRegistersOrderedByValue();
        } catch (SQLException e) {
            txtRegisters.setText("onBtnListByValueClick : " + e.toString());
            return;
        }
        StringBuilder sb = new StringBuilder();
        for(Register register:list){
            sb.append(register.getValue()+"-->"+register.getName()+"\n");
        }
        txtRegisters.setText(sb.toString());
        Toast.makeText(getApplicationContext(),"ListByValue",
                Toast.LENGTH_SHORT).show();
    }

}
