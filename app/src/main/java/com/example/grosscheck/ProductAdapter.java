package com.example.grosscheck;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ContentViewHolder> {
    private ArrayList<Product> productList;

    public ProductAdapter(ArrayList<Product> dataList) {
        this.productList = dataList;
    }

    @NonNull
    @Override
    public ProductAdapter.ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductAdapter.ContentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ContentViewHolder holder, int position) {
        holder.tv_productName.setText(productList.get(position).getProductName());
        holder.tv_productPrice.setText(productList.get(position).getProductPrice());
        Picasso.get()
                .load(productList.get(position).getProductImage())
                .into(holder.iv_productImage);
    }

    @Override
    public int getItemCount() {
        return (productList != null ? productList.size() : 0);
    }

    public void setItems(List<Product> items) {
        this.productList.clear();
        this.productList.addAll(items);
        notifyDataSetChanged();
    }

    public class ContentViewHolder extends ProductAdapter.ItemRowHolder {
        TextView tv_productName;
        TextView tv_productPrice;
        ImageView iv_productImage;

        private ContentViewHolder(View itemView) {
            super(itemView);
            tv_productName = itemView.findViewById(R.id.tv_productName);
            tv_productPrice = itemView.findViewById(R.id.tv_productPrice);
            iv_productImage = itemView.findViewById(R.id.iv_productImage);
        }
    }

    public static class ItemRowHolder extends RecyclerView.ViewHolder {
        private ItemRowHolder(View itemView) {
            super(itemView);
        }
    }
}
