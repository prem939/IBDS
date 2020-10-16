package com.example.ibds.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ibds.Adaptor.LeadAdaptor;
import com.example.ibds.DatabaseHelper;
import com.example.ibds.Do.LeadDo;
import com.example.ibds.R;

import java.util.List;

public class LeadFragment extends Fragment {
    Context mcontext;
    RecyclerView rvLead;
    LeadAdaptor adaptor;
    List<LeadDo> leadList;
    TextView txtNoData;
    DatabaseHelper databaseHelper;

    public LeadFragment(Context mcontext, List<LeadDo> leadList, DatabaseHelper databaseHelper) {
        this.mcontext = mcontext;
        this.leadList = leadList;
        this.databaseHelper = databaseHelper;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lead_fragment, container, false);
        rvLead = view.findViewById(R.id.rvLead);
        txtNoData = view.findViewById(R.id.txtNoData);
        if (leadList.size() > 0) {
            rvLead.setVisibility(View.VISIBLE);
            txtNoData.setVisibility(View.GONE);
            adaptor = new LeadAdaptor(mcontext, leadList,databaseHelper);
            rvLead.setLayoutManager(new LinearLayoutManager(mcontext));
            rvLead.setAdapter(adaptor);
        } else {
            rvLead.setVisibility(View.GONE);
            txtNoData.setVisibility(View.VISIBLE);
        }
        return view;
    }
}
