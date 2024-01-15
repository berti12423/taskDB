package com.example.myapplication;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import  java.io.Serializable;
public class Task implements Serializable {

    String taskName;
    String recurrence;
    String priority;
    String status;
    String calendar;

    public String getTaskName() {
        return this.taskName;
    }
    public String getRecurrence() {return  this.recurrence;}
    public String  getCalendar() { return this.calendar; }
    public void setCalendar(String a){ this.calendar = a; }
    public void setRecurrence(String a){ this.recurrence = a;}
    public String getPriority() {return  this.priority;}
    public void setPriority(String a){ this.priority = a;}
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskName='" + taskName + '\'' +
                ", recurrence='" + recurrence + '\'' +
                ", priority='" + priority + '\'' +
                ", status='" + status + '\'' +
                ", calendar=" + calendar +
                '}';
    }
    Task(){}

    public Task(String taskName, String priority,String recurrence, String calendar) {
        this.taskName = taskName;
        this.recurrence = recurrence;
        this.priority = priority;
        this.status = "In progress";
        this.calendar = calendar;
    }



}
