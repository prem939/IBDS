package com.example.ibds.Adaptor;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import android.view.LayoutInflater;

import com.example.ibds.Activity.BaseActivity;
import com.example.ibds.CalendarUtils;
import com.example.ibds.R;
import com.example.ibds.Do.TaskDo;

public class TaskAdaptor extends BaseAdapter {
    List<TaskDo> taskList = new ArrayList<>();
    Context mcontext;
    public TaskAdaptor(List<TaskDo> taskList, Context mcontext) {
        this.taskList = taskList;
        this.mcontext = mcontext;
    }

    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder {
        TextView txtTask,txtDate,txtAllocatedTime,txtAccuracy;
        CheckBox checkb;
    }
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mcontext);
            convertView = inflater.inflate(R.layout.task_adaptor, null);

            holder = new ViewHolder();
            holder.checkb = convertView.findViewById(R.id.checkb);
            holder.txtTask = convertView.findViewById(R.id.txtTask);
            holder.txtDate = convertView.findViewById(R.id.txtDate);
            holder.txtAllocatedTime = convertView.findViewById(R.id.txtAllocatedTime);
            holder.txtAccuracy = convertView.findViewById(R.id.txtAccuracy);
            convertView.setTag(holder);

            final ViewHolder finalHolder = holder;
            holder.checkb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(finalHolder.checkb.isChecked()) {
                        CheckBox cb = (CheckBox) view;
                        TaskDo task = (TaskDo) cb.getTag();
                        task.setEndTiming(CalendarUtils.getCurrentDateTime());
                        ((BaseActivity) mcontext).showCustomDialog(mcontext, "Warning", "Are you completed the Task", "Yes", "No", "Completion", task);
                    }
                }
            });

        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        final TaskDo task = taskList.get(i);
        holder.checkb.setChecked(task.isCheck());
        holder.txtDate.setText(task.getCreatedtiming());
        holder.txtTask.setText(task.getTask());
        holder.txtAllocatedTime.setText(task.getAllocatedTime());
        holder.txtAccuracy.setText(task.getAccuracy());
        holder.checkb.setTag(task);
        return convertView;
    }

    public void refreshTaskList(List<TaskDo> taskList){
        this.taskList = taskList;
        notifyDataSetChanged();
    }
}
