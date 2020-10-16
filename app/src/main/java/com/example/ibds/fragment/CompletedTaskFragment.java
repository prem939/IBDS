package com.example.ibds.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ibds.Adaptor.CompletedTaskAdaptor;
import com.example.ibds.DatabaseHelper;
import com.example.ibds.R;
import com.example.ibds.Do.TaskDo;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class CompletedTaskFragment extends Fragment {
    Context mcontext;
    List<TaskDo> completedTaskList = new ArrayList<>();
    DatabaseHelper databaseHelper;
    CompletedTaskAdaptor adaptor;
    TextView txtNoCompletedTask;
    RecyclerView rvInbox;
    ImageView imgReload;
    public CompletedTaskFragment(Context mcontext, List<TaskDo> completedTaskList) {
        this.mcontext = mcontext;
        this.completedTaskList = completedTaskList;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.completed_fragment, container, false);
        txtNoCompletedTask = view.findViewById(R.id.txtNoCompletedTask);
        rvInbox = view.findViewById(R.id.rvInbox);
        imgReload = view.findViewById(R.id.imgReload);

        if (completedTaskList.size() > 0) {
            rvInbox.setVisibility(View.VISIBLE);
            txtNoCompletedTask.setVisibility(View.GONE);
            adaptor = new CompletedTaskAdaptor(mcontext, completedTaskList);
            rvInbox.setLayoutManager(new LinearLayoutManager(mcontext));
            rvInbox.setAdapter(adaptor);
        } else {
            rvInbox.setVisibility(View.GONE);
            txtNoCompletedTask.setVisibility(View.VISIBLE);
        }
        imgReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reLoad();
            }
        });
        return view;
    }

    public void reLoad() {
        rvInbox.setVisibility(View.VISIBLE);
        txtNoCompletedTask.setVisibility(View.GONE);
        databaseHelper = new DatabaseHelper(mcontext);
        List<TaskDo> completedTaskList = databaseHelper.getAllIndexList();
        adaptor = new CompletedTaskAdaptor(mcontext, completedTaskList);
        adaptor.refresh(completedTaskList);
        rvInbox.setLayoutManager(new LinearLayoutManager(mcontext));
        rvInbox.setAdapter(adaptor);
        if(adaptor!=null)
            adaptor.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
