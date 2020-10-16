package com.example.ibds.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ibds.Do.ProductDo;
import com.example.ibds.R;

import java.util.ArrayList;
import java.util.List;

public class ProductAdaptor extends RecyclerView.Adapter<ProductAdaptor.ViewHolder> {
    List<ProductDo> productList = new ArrayList<>();
    Context mcontext;

    public ProductAdaptor(List<ProductDo> productList, Context mcontext) {
        this.productList = productList;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.home_adaptor, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final ProductDo product = productList.get(position);
            if(product !=null){
                holder.imgProductImage.setImageResource(product.getImgProduct());
                holder.ratingBar.setRating(product.getRating());
                holder.txtAmount.setText("$ "+product.getAmount());
                holder.txtDescription.setText(product.getDescription());
                holder.txtName.setText(product.getName());

            }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtName,txtDescription,txtAmount;
        RatingBar ratingBar;
        ImageView imgProductImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtAmount = itemView.findViewById(R.id.txtAmount);
            txtName = itemView.findViewById(R.id.txtName);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            imgProductImage = itemView.findViewById(R.id.imgProductImage);
        }
    }
}
