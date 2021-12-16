package com.example.finalproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Dialog.LongClickProjectDialog;
import com.example.finalproject.Dialog.Project;
import com.example.finalproject.ProjectView;
import com.example.finalproject.R;

import java.util.ArrayList;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder>{
    private ArrayList<Project> localDataSet;
    private ArrayList<String> keys;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = (TextView) view.findViewById(R.id.txt_projectName);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     Intent intent = new Intent(view.getContext()  , ProjectView.class);
                     intent.putExtra("Project title",textView.getText().toString());
                     intent.putExtra("Project key",textView.getTag().toString());
                     view.getContext().startActivity(intent);
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    String ProjectKey = textView.getTag().toString();
                    LongClickProjectDialog epd = new LongClickProjectDialog(view.getContext(), ProjectKey);
                    epd.show();
                    return true;
                }
            });
        }

        public TextView getTextView() {
            return textView;
        }
    }


    public ProjectAdapter(Context context, ArrayList<Project> dataSet, ArrayList<String> keys) {
        localDataSet = dataSet;
        this.keys = keys;
    }




    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.project_item_layout,
                        viewGroup,
                        false);

        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView().setText(localDataSet.get(position).getProjectName());
        viewHolder.textView.setBackgroundColor(localDataSet.get(position).getColorID());
        viewHolder.getTextView().setTag(keys.get(position));
//        viewHolder.
    }


    @Override
    public int getItemCount() {
        return localDataSet.size();
    }


}
