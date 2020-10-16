package com.example.ibds.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ibds.R;
import com.example.ibds.Do.TaskDo;

import java.util.ArrayList;
import java.util.List;

public class CompletedTaskAdaptor extends RecyclerView.Adapter<CompletedTaskAdaptor.ViewHolder> {
    Context mcontext;
    List<TaskDo> completedTaskList = new ArrayList<>();

    public CompletedTaskAdaptor(Context mcontext, List<TaskDo> completedTaskList) {
        this.mcontext = mcontext;
        this.completedTaskList = completedTaskList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.completed_adaptor, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final TaskDo task = completedTaskList.get(position);
            if(task !=null){
                holder.txtCreatedTime.setText(task.getCreatedtiming());
                holder.txtTask.setText(task.getTask());
                holder.txtEndTime.setText(task.getEndTiming());
            }
    }

    @Override
    public int getItemCount() {
        return completedTaskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTask, txtCreatedTime,txtEndTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTask = itemView.findViewById(R.id.txtTask);
            txtCreatedTime = itemView.findViewById(R.id.txtCreatedTime);
            txtEndTime = itemView.findViewById(R.id.txtEndTime);
        }
    }
    public void refresh( List<TaskDo> completedTaskList){
        this.completedTaskList = completedTaskList;
        notifyDataSetChanged();
    }

}
