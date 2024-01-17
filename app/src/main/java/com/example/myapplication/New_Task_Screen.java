package com.example.myapplication;

import static com.example.myapplication.MainActivity.u;
import static com.example.myapplication.ManagerDB.addTasktoDB;
import static com.example.myapplication.ManagerDB.readDatabase;
import static com.example.myapplication.ManagerDB.ref;
import static com.example.myapplication.ManagerDB.userList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class New_Task_Screen extends AppCompatActivity implements View.OnClickListener {

    RadioGroup radioGroup;
    TextView selectedDateTextView;
    EditText taskNameEditText;
    Button createButton;
    Button btnCancelCreate;
    Intent intent;
    String recurrence = "None", priority;

    RadioButton rb_monthly,rb_weekly,rb_daily;

    Calendar selectedDateTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task_screen);

        selectedDateTextView = findViewById(R.id.tvSelectedDateTime);

        taskNameEditText = findViewById(R.id.taskNameEditText);
        createButton = findViewById(R.id.btnCreate2);

        btnCancelCreate = findViewById(R.id.btnCancelCreate);
        btnCancelCreate.setOnClickListener(this);
        createButton.setOnClickListener(this);
        intent = new Intent(this, MainScreen.class);

        radioGroup = findViewById(R.id.rgRecurre);






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
        int[] priorityButtonIds = {R.id.rb_1, R.id.rb_2, R.id.rb_3, R.id.rb_4, R.id.rb_5};

        for (int i = 0; i < priorityButtonIds.length; i++) {
            if (view.getId() == priorityButtonIds[i] && checked) {
                priority = String.valueOf(i + 1);
            }
        }

    }

    @Override
    public void onClick(View v) {
        if (isValidTaskName() && v == createButton) {

            createTask();
        } else {
            Toast.makeText(New_Task_Screen.this, "Please enter a valid task name", Toast.LENGTH_SHORT).show();
        }
        if (v == btnCancelCreate)
        {
            setResult(RESULT_CANCELED);
            finish();
        }


    }

    private boolean isValidTaskName() {
        // Get the task name from the EditText
        String taskName = taskNameEditText.getText().toString().trim();

        // Perform validation
        return !taskName.isEmpty(); // Basic check for non-empty task name
    }
    private void createTask() {

        // Get the task name from the EditText
        String taskName = taskNameEditText.getText().toString().trim();

        // Check if task name is empty
        if (taskName.isEmpty()) {
            Toast.makeText(this, "Please enter a task name", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if priority is selected
        if (priority == null || priority.isEmpty()) {
            Toast.makeText(this, "Please select a priority", Toast.LENGTH_SHORT).show();
            return;
        }


        // Check if date and time are selected
        if (selectedDateTextView.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please select a due date and time", Toast.LENGTH_SHORT).show();
            return;
        }

        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        String formattedDateTime = dateTimeFormat.format(selectedDateTime.getTime());
        Task task = new Task(taskName, recurrence,priority,formattedDateTime);

        intent.putExtra("taskObject", task);

        addTasktoDB(u,task);

        setResult(RESULT_OK, intent);         // Set the result with the intent
        finish();
    }

    public void showDateTimePickerDialog(View view) {
        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        TimePickerDialog timePickerDialog = new TimePickerDialog(
                                New_Task_Screen.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        selectedDateTime = Calendar.getInstance();
                                        selectedDateTime.set(year, monthOfYear, dayOfMonth, hourOfDay, minute);
                                        updateSelectedDateTime(selectedDateTime);
                                    }
                                },
                                calendar.get(Calendar.HOUR_OF_DAY),
                                calendar.get(Calendar.MINUTE),
                                true
                        );
                        timePickerDialog.show();
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }
    private void updateSelectedDateTime(Calendar selectedDateTime) {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        String formattedDateTime = dateTimeFormat.format(selectedDateTime.getTime());
        selectedDateTextView.setText(formattedDateTime);
    }
}