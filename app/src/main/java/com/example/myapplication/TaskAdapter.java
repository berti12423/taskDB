package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {


    private List<Task> mTasks;
    public List<Task> getmTasks() {
        return mTasks;
    }

    public void setmTasks(List<Task> mTasks) {
        this.mTasks = mTasks;
    }

    // Store a member variable for the tasks



    // Pass in the task array into the constructor
    public TaskAdapter(List<Task> tasks) {
        mTasks = tasks;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View taskView = inflater.inflate(R.layout.items, parent, false);

        // Return a new holder instance
        return new ViewHolder(taskView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the data model based on position
        Task task = mTasks.get(position);

        // Set item views based on your views and data model
        TextView textView = holder.nameTextView;
        textView.setText(task.getTaskName());

        Button button = holder.messageButton;
        button.setText(task.getStatus());

        holder.updateSelectedDateTime(task.getCalendar());


        Button button2 = holder.messageButton;
        button.setText(task.getStatus());

        // Set a click listener for the button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedPosition = holder.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    if (buttonClickListener != null) {
                        buttonClickListener.onButtonClick(holder.getAdapterPosition());
                    }
                }
            }
        });
    }
    public interface OnButtonClickListener {
        void onButtonClick(int position);
    }
    private OnButtonClickListener buttonClickListener;

    // Method to set the button click listener
    public void setOnButtonClickListener(OnButtonClickListener listener) {
        this.buttonClickListener = listener;
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    // Provide a direct reference to each of the views within a data item
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView,dateTextView;
        public Button messageButton;
        public Date date;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            dateTextView = (TextView) itemView.findViewById(R.id.task_date);
            nameTextView = (TextView) itemView.findViewById(R.id.task_name);
            messageButton = (Button) itemView.findViewById(R.id.status_button);

        }
        private void updateSelectedDateTime(String selectedDateTime) {

            dateTextView.setText(selectedDateTime);
        }
    }
}