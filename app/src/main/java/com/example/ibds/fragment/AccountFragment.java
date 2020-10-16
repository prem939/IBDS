package com.example.ibds.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ibds.Adaptor.AcountAdaptor;
import com.example.ibds.Adaptor.CompletedTaskAdaptor;
import com.example.ibds.R;


public class AccountFragment extends Fragment {
    private RecyclerView rvAccount;
    private AcountAdaptor acountAdaptor;
    Context mcontext;
    public AccountFragment( Context mcontext) {
        this.mcontext =mcontext;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        rvAccount = view.findViewById(R.id.rvAccount);
        acountAdaptor = new AcountAdaptor(mcontext);
        rvAccount.setLayoutManager(new LinearLayoutManager(mcontext));
        rvAccount.setAdapter(acountAdaptor);
        return view;
    }

}
