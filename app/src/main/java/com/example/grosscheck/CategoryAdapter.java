package com.example.grosscheck;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grosscheck.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ContentViewHolder> {
    private OnItemClickedListener mOnItemClickedListener;
    private ArrayList<Category> categoryList;
    private int selectedCategoryPosition;

    public int getSelectedCategoryPosition() {
        return selectedCategoryPosition;
    }

//    public void setSelectedCategoryPosition(int selectedCategoryPosition) {
//        this.selectedCategoryPosition = selectedCategoryPosition;
//    }

    public void setSelectedCategoryPosition(int newIndex) {
        for (int i = 0; i < categoryList.size(); i++) {
            categoryList.get(i).setSelected(newIndex == i);
        }
        notifyDataSetChanged();
    }

    public CategoryAdapter(ArrayList<Category> dataList) {
        this.categoryList = dataList;
    }

    @NonNull
    @Override
    public ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new ContentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentViewHolder holder, int position) {
        holder.tv_categoryName.setText(categoryList.get(position).getCategoryName());
        if (mOnItemClickedListener != null) {
            holder.itemView.setClickable(true);
            holder.itemView.setOnClickListener(v -> mOnItemClickedListener.onItemClicked(v, position));
        }
        if (categoryList.get(position).isSelected()) {
            holder.tv_categoryName.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.colorPrimary));
        } else {
            holder.tv_categoryName.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.black_overlay));
        }
    }

    @Override
    public int getItemCount() {
        return (categoryList != null ? categoryList.size() : 0);
    }

    public List<Category> getItems() {
        return categoryList;
    }

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        mOnItemClickedListener = onItemClickedListener;
    }

    public void setItems(List<Category> items) {
        this.categoryList.clear();
        this.categoryList.addAll(items);
        notifyDataSetChanged();
    }

    public class ContentViewHolder extends ItemRowHolder {
        TextView tv_categoryName;

        private ContentViewHolder(View itemView) {
            super(itemView);
            tv_categoryName = itemView.findViewById(R.id.tv_categoryName);
        }
    }

    public static class ItemRowHolder extends RecyclerView.ViewHolder {
        private ItemRowHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnItemClickedListener {
        void onItemClicked(View view, int position);
    }
}
