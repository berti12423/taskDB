package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainScreen extends AppCompatActivity implements View.OnClickListener {


    Intent intent, intent2, intent3;
    static ArrayList<Task> tasklist = new ArrayList<Task>();
    Button newtaskbtn, ongoingbtn;
    TextView tasktv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        intent = new Intent(this, New_Task_Screen.class);
        intent2 = new Intent(this, Ongoing_Task_screen.class);
        intent3 = new Intent(this, MainActivity.class);

        newtaskbtn=findViewById(R.id.newtaskbtn);
        newtaskbtn.setOnClickListener(this);

        ongoingbtn = findViewById(R.id.ongoingbtn);
        ongoingbtn.setOnClickListener(this);
        tasktv = findViewById(R.id.taskview);





    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            Task receivedTask = (Task) data.getSerializableExtra("taskObject");
            addToList(receivedTask);
        }
    }

    public void addToList(Task task)
    {
        tasklist.add(task);
    }


    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        int id = item.getItemId();
        if (id == R.id.logout)
        {
            startActivityForResult(intent3, 0);
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v == newtaskbtn)
        {
            startActivityForResult(intent, 0);
        }
        if (v == ongoingbtn)
        {

            intent2.putExtra("TASKLIST", tasklist);
            startActivityForResult(intent2, 0);
        }

    }
}


