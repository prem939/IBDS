package com.example.ibds.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ibds.Do.CommentDo;
import com.example.ibds.R;

import java.util.List;

public class CommentAdaptor extends RecyclerView.Adapter<CommentAdaptor.ViewHolder> {

    Context mcontext;
    List<CommentDo> commentList;
    public CommentAdaptor(Context mcontext, List<CommentDo> commentList) {
        this.mcontext = mcontext;
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.comment_adaptor, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CommentDo comment = commentList.get(position);
        if(comment != null){
            holder.txtName.setText(comment.getName());
            holder.txtComment.setText(comment.getComment());
        }
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtComment, txtName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtComment = itemView.findViewById(R.id.txtComment);
            txtName = itemView.findViewById(R.id.txtName);
        }
    }
    public void refreshComments(List<CommentDo> commentList){
        this.commentList = commentList;
        notifyDataSetChanged();
    }
}
