package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{
    static ArrayList<Task> tasks;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView taskName,dateTime, description;
        private final CheckBox checkingStatus;
        private final ConstraintLayout layoutExpand;
        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            taskName = (TextView) view.findViewById(R.id.txt_taskTitle);
            dateTime = (TextView) view.findViewById((R.id.txt_deadline));
            checkingStatus = (CheckBox) view.findViewById(R.id.chk_jobCheckingStatus);
            layoutExpand = (ConstraintLayout) view.findViewById(R.id.ly_expandLayout);
            description = (TextView) view.findViewById(R.id.txt_description);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(view.getContext()  , ProjectView.class);
//                    intent.putExtra("Project Title",textView.getText().toString());
//                    view.getContext().startActivity(intent);
                    if (layoutExpand.getVisibility() == View.GONE){
                        layoutExpand.setVisibility(View.VISIBLE);
                    }
                    else{
                        layoutExpand.setVisibility(View.GONE);
                    }
                }
            });
        }

        public TextView getTaskName() {
            return taskName;
        }

        public TextView getDateTime() {
            return dateTime;
        }

        public ConstraintLayout getLayoutExpand() {
            return layoutExpand;
        }

        public TextView getDescription() {
            return description;
        }
    }


    public TaskAdapter(Context context, ArrayList<Task> dataSet) {
        tasks = dataSet;
    }




    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.task_item_layout,
                        viewGroup,
                        false);

        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTaskName().setText(tasks.get(position).getTitle());
        viewHolder.getDateTime().setText(tasks.get(position).getEndTime());
        viewHolder.getDescription().setText(tasks.get(position).getDescription());
    }


    @Override
    public int getItemCount() {
        return tasks.size();
    }


}