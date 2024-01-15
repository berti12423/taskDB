package com.example.myapplication;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class New_task extends AppCompatActivity implements View.OnClickListener {
    Button btncancel;
    Button btnCreate;

    EditText etTaskName;
    Intent intent, intent2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        btncancel = findViewById(R.id.btnCancel);
        btncancel.setOnClickListener(this);

        btnCreate = findViewById(R.id.btncreate);
        btnCreate.setOnClickListener(this);

        etTaskName = findViewById(R.id.etTaskName);
        etTaskName.getText();

        intent = new Intent(this, MainScreen.class);
        intent2 = new Intent(this, New_Task_Screen.class);


    }

    @Override
    public void onClick(View v) {
        if (v == btncancel)
        {
            startActivityForResult(intent, 0);
        }
        if (v==btnCreate)
        {
            startActivityForResult(intent2, 0);
        }
    }
}