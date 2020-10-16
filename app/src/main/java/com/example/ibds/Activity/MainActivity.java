package com.example.ibds.Activity;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.ibds.DatabaseHelper;
import com.example.ibds.Preference;
import com.example.ibds.R;
import com.example.ibds.Do.TaskDo;
import com.example.ibds.ViewPagerAdapter;
import com.example.ibds.fragment.AccountFragment;
import com.example.ibds.fragment.LeadFragment;
import com.example.ibds.fragment.TaskFragment;
import com.example.ibds.fragment.IdFragment;
import com.example.ibds.fragment.CompletedTaskFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends BaseActivity {

    LinearLayout llmain;
    BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;
    private IdFragment idFragment;
    private TaskFragment taskFragment;
    private AccountFragment accountFragment;
    private MenuItem prevMenuItem;
    private CompletedTaskFragment completedTaskFragment;
    DatabaseHelper databaseHelper;
    private ViewPagerAdapter adapter;
    private LeadFragment leadFragment;
    private int position=0;
    @Override
    public void initialize() {
        llmain = (LinearLayout) inflater.inflate(R.layout.activity_main, null);
        llBody.addView(llmain, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        databaseHelper = new DatabaseHelper(getApplicationContext());
        viewPager = (ViewPager) llmain.findViewById(R.id.viewpager);
        bottomNavigationView = (BottomNavigationView) llmain.findViewById(R.id.bottom_navigation);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());


        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LeadCreaterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_task:
                                imgAdd.setVisibility(View.GONE);
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.action_inbox:
                                imgAdd.setVisibility(View.GONE);
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.action_lead:
                                imgAdd.setVisibility(View.VISIBLE);
                                viewPager.setCurrentItem(2);
                                break;
                            case R.id.action_id:
                                imgAdd.setVisibility(View.GONE);
                                viewPager.setCurrentItem(3);
                                break;
                            case R.id.action_account:
                                imgAdd.setVisibility(View.GONE);
                                viewPager.setCurrentItem(4);
                                break;
                        }
                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: " + position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setupViewPager(viewPager);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            position = extras.getInt("viewpager_position");
            imgAdd.setVisibility(View.VISIBLE);
            viewPager.setCurrentItem(position);
        }

    }

    public List<TaskDo> getData() {
        return databaseHelper.getAllTaskList();
    }
    public List<TaskDo> getCompletedTasksList() {
        return databaseHelper.getAllIndexList();
    }
    private void setupViewPager(ViewPager viewPager) {

        idFragment = new IdFragment(MainActivity.this,databaseHelper.getIds(),inflater,new Preference(getApplicationContext()));
        taskFragment = new TaskFragment(MainActivity.this, getData(),inflater,new Preference(getApplicationContext()),databaseHelper);
        completedTaskFragment = new CompletedTaskFragment(getApplicationContext(),getCompletedTasksList());
        accountFragment = new AccountFragment(MainActivity.this);
        leadFragment = new LeadFragment(MainActivity.this,databaseHelper.getAllLeadDetails(),databaseHelper);

        adapter.addFragment(taskFragment);
        adapter.addFragment(completedTaskFragment);
        adapter.addFragment(leadFragment);
        adapter.addFragment(idFragment);
        adapter.addFragment(accountFragment);

        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        showCustomDialog(this, getString(R.string.warning), getResources().getString(R.string.do_you_want_to_logout), getString(R.string.OK), getString(R.string.Cancel), "logout");
    }
}