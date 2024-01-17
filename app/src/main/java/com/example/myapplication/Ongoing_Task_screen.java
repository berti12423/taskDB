package com.example.myapplication;

import static com.example.myapplication.EditTaskScreen.TASK_UPDATED_ACTION;
import static com.example.myapplication.MainActivity.u;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class Ongoing_Task_screen extends AppCompatActivity implements View.OnClickListener,TaskAdapter.OnButtonClickListener {

    // Pass in intent the task list!!!!
    Intent intent,intent2,intent3;
    Button btnBack, editBTN;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ongoing_task_screen);


        intent = getIntent();
        intent2 = new  Intent(this, MainScreen.class);
        intent3 = new Intent(this,EditTaskScreen.class);
        btnBack = findViewById(R.id.btnBack);
        IntentFilter filter = new IntentFilter(TASK_UPDATED_ACTION);
        registerReceiver(taskUpdatedReceiver, filter);
        btnBack.setOnClickListener(this);
        // Lookup the recyclerview in activity layout
        recyclerView = findViewById(R.id.recyclerView);

        if (recyclerView == null) {
            Log.e("Ongoing_Task_screen", "RecyclerView is null");
            return;
        }

        ArrayList<Task> taskList = u.getTasklist();

        // Ensure taskList is not null
        if (taskList != null) {
            // Create adapter passing in the sample user data
            TaskAdapter adapter = new TaskAdapter(taskList);
            adapter.setOnButtonClickListener((TaskAdapter.OnButtonClickListener) this);
            // Attach the adapter to the recyclerview to populate items
            recyclerView.setAdapter(adapter);

            // Set layout manager to position the items
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            Log.e("Ongoing_Task_screen", "TaskList is null");
            // Log an error or handle the case where taskList is null
        }
    }

    private BroadcastReceiver taskUpdatedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Refresh the list or perform any necessary actions when a task is updated
            refreshTaskList();
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(taskUpdatedReceiver);
    }
    private void refreshTaskList() {
        // Retrieve the updated task list and refresh the adapter
        ArrayList<Task> updatedTaskList = u.getTasklist();
        if (updatedTaskList != null && recyclerView != null && recyclerView.getAdapter() != null) {
            TaskAdapter adapter = (TaskAdapter) recyclerView.getAdapter();
            adapter.setmTasks(updatedTaskList);
            adapter.notifyDataSetChanged();
        }
    }
    @Override
    public void onButtonClick(int position) {
        intent3.putExtra("pos", position);
        startActivityForResult(intent3, 0);



    }
    @Override
    public void onClick(View view) {
        if (view == btnBack) {
            // Start the second activity for a result
            startActivityForResult(intent2,0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }
}