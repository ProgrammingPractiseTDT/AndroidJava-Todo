package com.example.finalproject.Adapter;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.DataClass.Task;
import com.example.finalproject.Dialog.LongClickTaskDialog;
import com.example.finalproject.FirebaseOperator;
import com.example.finalproject.R;

import java.util.ArrayList;

public class MultiProjectTaskAdapter extends RecyclerView.Adapter<MultiProjectTaskAdapter.ViewHolder>{
    static ArrayList<Task> tasks;
    private final Context context;
    private ArrayList<String> projectKeys;
    private ArrayList<String> taskKeys;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView taskName,dateTime, description, fullTitle;
        private final CheckBox checkingStatus;
        private final ConstraintLayout layoutExpand;
        private final ConstraintLayout Fulllayout;
        private final TextView clockText;
        public ViewHolder(View view) {
            super(view);
            this.setIsRecyclable(false);
            // Define click listener for the ViewHolder's View
            taskName = (TextView) view.findViewById(R.id.txt_taskTitle);
            dateTime = (TextView) view.findViewById((R.id.txt_deadline));
            checkingStatus = (CheckBox) view.findViewById(R.id.chk_jobCheckingStatus);
            layoutExpand = (ConstraintLayout) view.findViewById(R.id.ly_expandLayout);
            description = (TextView) view.findViewById(R.id.txt_description);
            fullTitle = (TextView) view.findViewById(R.id.txt_fullTitle);
            Fulllayout = (ConstraintLayout) view.findViewById(R.id.ly_fullLayout);
            clockText = (TextView) view.findViewById(R.id.clockText);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//
                    if (layoutExpand.getVisibility() == View.GONE){
                        layoutExpand.setVisibility(View.VISIBLE);
                    }
                    else{
                        layoutExpand.setVisibility(View.GONE);
                    }
                }
            });
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    LongClickTaskDialog epd = new LongClickTaskDialog(view.getContext(), description.getTag().toString(), taskName.getTag().toString());
                    epd.show();
                    return true;
                }
            });
        }

        public ConstraintLayout getFulllayout() {
            return Fulllayout;
        }

        public TextView getFullTitle() {
            return fullTitle;
        }

        public CheckBox getCheckingStatus() {
            return checkingStatus;
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
        public TextView getClockText(){
            return clockText;
        }
    }


    public MultiProjectTaskAdapter(Context context, ArrayList<Task> dataSet, ArrayList<String> taskKeys, ArrayList<String> projectKeys) {
        tasks = dataSet;
        this.taskKeys = taskKeys;
        this.projectKeys = projectKeys;
        this.context = context;
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
//        for (int i = 0; i < tasks.size(); i++){
//            for (int j = 0; j < tasks.size() - i - 1; j++){
//                int a = tasks.get(j).getPriority();
//                int b = tasks.get(j+1).getPriority();
//                if(a < b){
//                    Task temp = tasks.get(j);
//                    tasks.set(j, tasks.get(j+1));
//                    tasks.set(j+1, temp);
//                }
//            }
//        }
        viewHolder.getTaskName().setText(tasks.get(position).getTitle());
        viewHolder.getDateTime().setText(tasks.get(position).getEndTime());
        viewHolder.getDescription().setText(tasks.get(position).getDescription());
        viewHolder.getFullTitle().setText(tasks.get(position).getTitle());
        viewHolder.getClockText().setText(tasks.get(position).getOnTime());
        viewHolder.getCheckingStatus().setChecked(tasks.get(position).getCheckingStatus());
        String taskKey = taskKeys.get(position);
        viewHolder.getTaskName().setTag(taskKey);
        String projectKey = projectKeys.get(position);
        viewHolder.getDescription().setTag(projectKey);
        if(tasks.get(position).getCheckingStatus()){
                viewHolder.getFulllayout().setAlpha((float)0.2);
        }
        else{
            viewHolder.getFulllayout().setAlpha((float)1);
        }
        if (tasks.get(position).getPriority() == 1){
            viewHolder.getCheckingStatus().setButtonDrawable(R.drawable.low_priority_cb);
        }
        else if (tasks.get(position).getPriority() == 2){
//            viewHolder.getPriorityText().setText( "!!");
//            viewHolder.getPriorityText().setTextColor(context.getResources().getColor(R.color.light_red));
            viewHolder.getCheckingStatus().setButtonDrawable(R.drawable.medium_priority_cb);
        }
        else{
//            viewHolder.getChec
//             viewHolder.getPriorityText().setText( "!!!");
//            viewHolder.getPriorityText().setTextColor(context.getResources().getColor(R.color.light_red));
            //viewHolder.getFulllayout().setBackgroundColor(Color.parseColor("#f04646"));
        }
        viewHolder.getCheckingStatus().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                boolean checking = viewHolder.getCheckingStatus().isChecked();
                FirebaseOperator taskUpdate = new FirebaseOperator();
                taskUpdate.updateTasks(projectKey, taskKey, checking);
            }
        });
    }


    @Override
    public int getItemCount() {
        return tasks.size();
    }

}