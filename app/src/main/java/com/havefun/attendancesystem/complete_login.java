package com.havefun.attendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.havefun.attendancesystem.R;

public class complete_login extends AppCompatActivity {
EditText username,email,phone,address,dateofbirth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complete_login);
        initializeVars();
    }
/*
** Initialization vars and objects part
 */
    private void initializeVars() {
        username=(EditText)findViewById(R.id.username);
        email=(EditText)findViewById(R.id.email);
        phone=(EditText)findViewById(R.id.phone);
        address=(EditText)findViewById(R.id.address);
        dateofbirth=(EditText)findViewById(R.id.dateofbirth);
    }
}
