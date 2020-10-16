package com.example.ibds.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ibds.R;

public class AcountAdaptor extends RecyclerView.Adapter<AcountAdaptor.ViewHolder> {
    Context mcontext;

    public AcountAdaptor(Context mcontext) {
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.acount_adaptor, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtPayHours.setText("2 Hours/Rs : 2000");
        holder.txtWorkinghours.setText("2 Hours");
        holder.txtPaidHours.setText("1 Hours");
        holder.txtBalancePay.setText("Rs : 10000");
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtWorkinghours, txtPaidHours,txtBalancePay,txtPayHours;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtWorkinghours = itemView.findViewById(R.id.txtWorkinghours);
            txtPaidHours = itemView.findViewById(R.id.txtPaidHours);
            txtBalancePay = itemView.findViewById(R.id.txtBalancePay);
            txtPayHours = itemView.findViewById(R.id.txtPayHours);
        }
    }
}
