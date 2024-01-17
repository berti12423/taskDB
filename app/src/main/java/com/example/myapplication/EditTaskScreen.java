package com.example.myapplication;

import static com.example.myapplication.MainActivity.u;
import static com.example.myapplication.ManagerDB.readDatabase;
import static com.example.myapplication.ManagerDB.ref;
import static com.example.myapplication.ManagerDB.userList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.common.internal.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditTaskScreen extends AppCompatActivity implements View.OnClickListener {

    Button btnUpdate,btnDelete;
    EditText taskNameEditText;
    TextView selectedDateTextView;
    Calendar selectedDateTime;
    Intent intent;
    RadioGroup radioGroup;
    int i;
    String recurrence;
    String  priority;
    public static final String TASK_UPDATED_ACTION = "com.example.myapplication.TASK_UPDATED";
    RecyclerView recyclerView;
    //hrkmjni

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task_screen);

        intent = getIntent();
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate= findViewById(R.id.btnUpdate);
        selectedDateTextView = findViewById(R.id.tvSelectedDateTime);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        radioGroup = findViewById(R.id.rgRecurre);
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
        int[] priorityButtonIds = {R.id.rb_1, R.id.rb_2, R.id.rb_3, R.id.rb_4, R.id.rb_5};

        for (int i = 0; i < priorityButtonIds.length; i++) {
            if (view.getId() == priorityButtonIds[i] && checked) {
                priority = String.valueOf(i + 1);
            }
        }
    }

    @Override
    public void onClick(View v) {

        if (btnDelete == v)
        {

            u.getTasklist().remove(i);
            ref.child("data").setValue(userList);

        }
        if (btnUpdate == v)
        {
            String taskName = taskNameEditText.getText().toString().trim();
            if (!taskName.isEmpty())
            {
                u.getTasklist().get(i).setTaskName(taskName);
            }

            if(recurrence!=null)
            {
                u.getTasklist().get(i).setPriority(recurrence);
            }
            if (!selectedDateTextView.getText().toString().isEmpty())
            {
                SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                u.getTasklist().get(i).setCalendar(dateTimeFormat.format(selectedDateTime.getTime()));
            }
            if (recyclerView != null && recyclerView.getAdapter() != null) {
                recyclerView.getAdapter().notifyDataSetChanged();
            }
            if (priority!=null)
            {
                u.getTasklist().get(i).setPriority(priority);
            }

            Intent taskUpdatedIntent = new Intent(TASK_UPDATED_ACTION);
            sendBroadcast(taskUpdatedIntent);
            ref.child("data").setValue(userList);


        }
        Intent taskUpdatedIntent = new Intent(TASK_UPDATED_ACTION);
        sendBroadcast(taskUpdatedIntent);
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
                                EditTaskScreen.this,
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