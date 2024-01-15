package com.example.myapplication;

import static com.example.myapplication.MainActivity.u;
import static com.example.myapplication.ManagerDB.readDatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class EditTaskScreen extends AppCompatActivity implements View.OnClickListener {

    Button btnUpdate,btnDelete;
    EditText taskNameEditText;
    Intent intent;
    int i;
    String recurrence;
    //hrkmjni

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task_screen);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate= findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        i = intent.getIntExtra("pos", 0);
        taskNameEditText = findViewById(R.id.taskNameEditText);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        if (view.getId() == R.id.rb_daily) {
            if (checked) {
                recurrence = "Daily";
            }
        } else if (view.getId() == R.id.rb_weekly) {
            if (checked) {
                recurrence = "Weekly";
            }
        } else if (view.getId() == R.id.rb_monthly) {
            if (checked) {
                recurrence = "Monthly";
            }
        }
    }

    @Override
    public void onClick(View v) {

        if (btnDelete == v)
        {

            u.getTasklist().remove(i);
            readDatabase();
            finish();
        }
        if (btnUpdate == v)
        {
            String taskName = taskNameEditText.getText().toString().trim();
            if (!taskName.isEmpty())
            {
                u.getTasklist().get(i).setTaskName(taskName);
            }
        }
    }
}