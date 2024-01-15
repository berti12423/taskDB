package com.example.myapplication;

import static com.example.myapplication.MainActivity.u;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class User implements Serializable {

    private String  username;
    private int level;
    String email;
    String password;


    public ArrayList<Task> tasklist;

    public ArrayList<Task> Getarray()
    {
        return this.tasklist;
    }
    public void setTasklist(ArrayList<Task> tasklist) {
        this.tasklist = tasklist;
    }


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    public  ArrayList<Task> getTasklist() {
        return tasklist;
    }



    User(){}
    public User( String password, String email) {

        this.tasklist = new ArrayList<>();
        this.level = 0;

        this.email = email;
        this.password = password;
    }
    public void sortList(ArrayList<Task> a) {   }
    public void  addToList(Task t)
    {
        this.tasklist.add(t);
    }
    public boolean isEqual(User u)
    {
        return u.getEmail().matches(this.email)&&u.getPassword().matches(this.password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
