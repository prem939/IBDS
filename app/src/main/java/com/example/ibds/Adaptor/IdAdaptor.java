package com.example.ibds.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ibds.Do.IdDo;
import com.example.ibds.Do.TaskDo;
import com.example.ibds.R;

import java.util.ArrayList;
import java.util.List;

public class IdAdaptor extends RecyclerView.Adapter<IdAdaptor.ViewHolder> {
    Context mcontext;
    List<IdDo> idList = new ArrayList<>();

    public IdAdaptor(Context mcontext, List<IdDo> idList) {
        this.mcontext = mcontext;
        this.idList = idList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.id_adaptor, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final IdDo idDo = idList.get(position);
        if(idDo!=null){
            holder.txtName.setText(idDo.getName());
            holder.txtPassword.setText(idDo.getPassword());
            holder.txtStatus.setText(idDo.getStatus());
        }
    }

    @Override
    public int getItemCount() {
        return idList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtName,txtPassword,txtStatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtPassword = itemView.findViewById(R.id.txtPassword);
            txtStatus = itemView.findViewById(R.id.txtStatus);
        }
    }
    public void refreshData( List<IdDo> idList){
        this.idList = idList;
        notifyDataSetChanged();
    }
}
