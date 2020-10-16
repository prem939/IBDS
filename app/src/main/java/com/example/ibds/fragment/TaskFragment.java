package com.example.ibds.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.ibds.Activity.BaseActivity;
import com.example.ibds.CalendarUtils;
import com.example.ibds.CustomDialog;
import com.example.ibds.Adaptor.TaskAdaptor;
import com.example.ibds.DatabaseHelper;
import com.example.ibds.Preference;
import com.example.ibds.R;
import com.example.ibds.Do.TaskDo;

import java.util.ArrayList;
import java.util.List;


public class TaskFragment extends Fragment {

    Context mcontext;
    TaskAdaptor adaptor;
    List<TaskDo> taskList = new ArrayList<>();
    ListView lvHome;
    TextView txtNotask;
    public CustomDialog customDialog;
    public LayoutInflater inflater;
    Preference preference;
    DatabaseHelper databaseHelper;
    ImageView imgReload;
    Button btnNewTask;
    public TaskFragment(Context mcontext, List<TaskDo> taskList, LayoutInflater inflater, Preference preference, DatabaseHelper databaseHelper) {
        this.mcontext = mcontext;
        this.taskList = taskList;
        this.inflater = inflater;
        this.preference = preference;
        this.databaseHelper = databaseHelper;
    }

    public TaskFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_fragment, container, false);
        lvHome = view.findViewById(R.id.lvhome);
        txtNotask = view.findViewById(R.id.txtNoTask);
        imgReload = view.findViewById(R.id.imgReload);
        btnNewTask = view.findViewById(R.id.btnNewTask);

        if (taskList.size() > 0) {
            lvHome.setVisibility(View.VISIBLE);
            txtNotask.setVisibility(View.GONE);
            adaptor = new TaskAdaptor(taskList, mcontext);
            lvHome.setAdapter(adaptor);
        } else {
            lvHome.setVisibility(View.GONE);
            txtNotask.setVisibility(View.VISIBLE);
        }
        btnNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BaseActivity)getActivity()).runOnUiThread(new RunCustomEditTestDialog());
            }
        });
//        FloatingActionButton actionA = view.findViewById(R.id.action_a);
//        actionA.setTitle("first");
//        actionA.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        imgReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reLoad();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
    public void reLoad(){
        lvHome.setVisibility(View.VISIBLE);
        txtNotask.setVisibility(View.GONE);
        taskList = databaseHelper.getAllTaskList();
        adaptor = new TaskAdaptor(taskList, mcontext);
        adaptor.refreshTaskList(taskList);
        lvHome.setAdapter(adaptor);
        if(adaptor !=null)
            adaptor.notifyDataSetChanged();
    }
    @Override
    public void onPause() {
        super.onPause();
    }


    class RunCustomEditTestDialog implements Runnable {
        private boolean isCancelable = false;

        public RunCustomEditTestDialog() {

        }

        @Override
        public void run() {
            if (customDialog != null && customDialog.isShowing())
                customDialog.dismiss();
            View view = inflater.inflate(R.layout.custom_edittext_popup, null);
            customDialog = new CustomDialog(mcontext, view, preference
                    .getIntFromPreference(Preference.DEVICE_DISPLAY_WIDTH, 320) - 60,
                    ViewGroup.LayoutParams.WRAP_CONTENT, true);
            customDialog.setCancelable(isCancelable);
            customDialog.setCanceledOnTouchOutside(true);
            final EditText edtTask = view.findViewById(R.id.edtTask);
            final EditText edtAccuracy = view.findViewById(R.id.edtAccuracy);
            final EditText edtAllocatedTime = view.findViewById(R.id.edtAllocatedTime);
            Button btnAdd = view.findViewById(R.id.btnAdd);

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String enteredTask = edtTask.getText().toString();
                    String enteredAllocatedTime = edtAllocatedTime.getText().toString();
                    String enteredAccuracy = edtAccuracy.getText().toString();
                    if (enteredTask.equalsIgnoreCase("") || enteredAllocatedTime.equalsIgnoreCase("") || enteredAccuracy.equalsIgnoreCase("")) {
                        ((BaseActivity) getActivity()).showCustomDialog(mcontext, getString(R.string.warning), "Enter the task", getString(R.string.OK), "", "");
                    } else {
                        TaskDo task = new TaskDo();
                        task.setCreatedtiming(CalendarUtils.getCurrentDateTime());
                        task.setCheck(false);
                        task.setTask(enteredTask);
                        task.setAllocatedTime(enteredAllocatedTime);
                        task.setAccuracy(enteredAccuracy);
                        if (databaseHelper.InsertTask(task)) {
                            ((BaseActivity)getActivity()).showCustomeToast("Task "+ "' "+task.getTask()+" '" +" was saved.");
                            reLoad();
                        } else {
                            ((BaseActivity)getActivity()).showCustomeToast("Something went wrong");
                        }
                        customDialog.dismiss();
                    }
                }
            });
            try {
                if (!customDialog.isShowing())
                    customDialog.showCustomDialog();
            } catch (Exception e) {
            }
        }
    }
}
