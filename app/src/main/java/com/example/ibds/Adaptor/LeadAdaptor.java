package com.example.ibds.Adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ibds.DatabaseHelper;
import com.example.ibds.Do.CommentDo;
import com.example.ibds.Do.LeadDo;
import com.example.ibds.R;

import java.util.List;

public class LeadAdaptor extends RecyclerView.Adapter<LeadAdaptor.ViewHolder> {
    Context mcontext;
    List<LeadDo> leadList;
    boolean show = true;
    CommentAdaptor adaptor;
    DatabaseHelper databaseHelper;

    public LeadAdaptor(Context mcontext, List<LeadDo> leadList, DatabaseHelper databaseHelper) {
        this.mcontext = mcontext;
        this.leadList = leadList;
        this.databaseHelper = databaseHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.lead_adaptor, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final LeadDo lead = leadList.get(position);
        if (lead != null) {
            List<CommentDo> commentList = databaseHelper.getCommentAsPerTheName(lead.getName());
            holder.txtName2.setText(lead.getName());
            holder.txtName.setText(lead.getName());
            holder.txtCompany.setText(lead.getCompany());
            holder.txtTitle.setText(lead.getTitle());
            holder.txtPhone.setText(lead.getPhone());
            holder.txtEmail.setText(lead.getEamil());
            holder.txtTiming.setText(lead.getCreatedTime() + " " + "Created");
            adaptor = new CommentAdaptor(mcontext,databaseHelper.getCommentAsPerTheName(lead.getName()));
            holder.rvComment.setLayoutManager(new LinearLayoutManager(mcontext));
            holder.rvComment.setAdapter(adaptor);
            holder.txtCommentCount.setText(commentList.size() > 1 ? commentList.size()+" "+"Comments ":commentList.size()+" "+"Comment ");
            holder.edtComment.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @SuppressLint("ResourceAsColor")
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    holder.btnPost.setBackgroundColor(R.color.gray_dark);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            holder.btnPost.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View view) {
                    String strComment = holder.edtComment.getText().toString();
                    if(strComment.equalsIgnoreCase("")){
                        Toast.makeText(mcontext,"Comment is empty",Toast.LENGTH_SHORT).show();
                    }else{
                        CommentDo comment = new CommentDo(lead.getName(),strComment);
                        if(databaseHelper.insertComment(comment)){
                            List<CommentDo> commentList= databaseHelper.getCommentAsPerTheName(lead.getName());
                            adaptor = new CommentAdaptor(mcontext,commentList);
                            adaptor.refreshComments(commentList);
                            holder.rvComment.setLayoutManager(new LinearLayoutManager(mcontext));
                            holder.rvComment.setAdapter(adaptor);
                            holder.txtCommentCount.setText(commentList.size() > 1 ? commentList.size()+" "+"Comments":commentList.size()+" "+"Comment");
                            holder.edtComment.getText().clear();
                            holder.btnPost.setBackgroundColor(R.color.gray_light2);
                            if(adaptor !=null)
                                adaptor.notifyDataSetChanged();
                        }
                    }
                }
            });
            holder.llComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (show) {
                        show = false;
                        holder.llCommentView.setVisibility(View.VISIBLE);
                    } else {
                        show = true;
                        holder.llCommentView.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return leadList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtName2, txtCompany, txtTitle, txtPhone, txtTiming, txtEmail,txtCommentCount;
        LinearLayout llComment, llCommentView;
        RecyclerView rvComment;
        Button btnPost;
        EditText edtComment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName2 = itemView.findViewById(R.id.txtName2);
            txtName = itemView.findViewById(R.id.txtName);
            txtCompany = itemView.findViewById(R.id.txtCompany);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtPhone = itemView.findViewById(R.id.txtPhone);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtTiming = itemView.findViewById(R.id.txtTiming);
            llComment = itemView.findViewById(R.id.llComment);
            llCommentView = itemView.findViewById(R.id.llCommentView);
            rvComment = itemView.findViewById(R.id.rvComment);
            btnPost = itemView.findViewById(R.id.btnPost);
            edtComment = itemView.findViewById(R.id.edtComment);
            txtCommentCount = itemView.findViewById(R.id.txtCommentCount);
        }
    }
//    public String getDuration(String time){
//        String createdTiming[] = time.split(":");
//        String presentTiming[] = CalendarUtils.getTime().split(":");
//        if(createdTiming[0])
//    }
}
