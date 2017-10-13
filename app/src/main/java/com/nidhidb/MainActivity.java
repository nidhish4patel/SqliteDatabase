package com.nidhidb;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nidhidb.database.DBHelper;
import com.nidhidb.model.UserModel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context = MainActivity.this;

    private EditText etname, etmob, etaddress;

    private Button btsave, btget;

    DBHelper dbHelper;

    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(context);


        etname = (EditText) findViewById(R.id.editText_name);
        etaddress = (EditText) findViewById(R.id.editText_address);
        etmob = (EditText) findViewById(R.id.editText_mobile);

        btsave = (Button) findViewById(R.id.button_save);
        btsave.setOnClickListener(this);

        btget = (Button) findViewById(R.id.button_get);
        btget.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_save:
                if (etname.length() > 0 && etmob.length() >= 10 && etaddress.length() > 0) {

                    userModel = new UserModel();
                    userModel.setName(etname.getText().toString());
                    userModel.setMobile(etmob.getText().toString());
                    userModel.setAddress(etaddress.getText().toString());
                    long id = dbHelper.saveData(userModel);
                    if(id>0){
                        Toast.makeText(context, "Successfully inserted", Toast.LENGTH_SHORT).show();
                        etname.setText("");
                        etmob.setText("");
                        etaddress.setText("");
                    }else {
                        Toast.makeText(context, "Failed to insert", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(context, "Enter all details", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.button_get:
                if (etname.length() > 0) {
                    UserModel userModel = dbHelper.getData(etname.getText().toString());
                    if(userModel!=null && userModel.getName()!=null){
                        etname.setText(userModel.getName());
                        etmob.setText(userModel.getMobile());
                        etaddress.setText(userModel.getAddress());

                    }else{
                        etname.setText("");
                        Toast.makeText(context, "No Such User Exists", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(context, "Enter Name", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
