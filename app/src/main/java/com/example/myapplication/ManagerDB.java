package com.example.myapplication;

import static com.example.myapplication.MainActivity.u;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class ManagerDB {

    public static User curr;
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference ref = database.getReference("users");
    static ArrayList<User> userList;
    public ManagerDB() {}
    public static boolean addUser(User u)
    {

        if(userList==null) //idk
        {
            userList = new ArrayList<>();
            userList.add(u);
            ref.child("data").setValue(userList);
            return true;
        }

        for (int i = 0; i<userList.size();i++)if(u.isEqual(userList.get(i))) return false;
        userList.add(u);
        ref.child("data").setValue(userList);
        curr = u;
        return true;
    }

    public static boolean hasUser(User u)
    {
        for (int i = 0; i<userList.size();i++) if(u.isEqual(userList.get(i)))
        {
            curr = userList.get(i);
            return true;
        }
        return false;
    }
    public static void addListToUser(User u)
    {
        for (int i = 0; i<userList.size();i++)
        {
            if(u.isEqual(userList.get(i)))
            {
                if (userList.get(i).getTasklist() != null)
                {
                    u.setTasklist(userList.get(i).getTasklist());
                }

            }

        }

    }
    public static void addTasktoDB(User u2,Task task)
    {



    }

    public static void readDatabase()
    {
        ref.child("data").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList = snapshot.getValue(new GenericTypeIndicator<ArrayList<User>>() {});
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
