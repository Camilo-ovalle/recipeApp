package com.example.recipies_app;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingItemViewHolder> {

    private List<ShoppingItem> items;
    private Context context;
    private OnItemDeleteListener deleteListener;
    private OnItemCheckListener checkListener;

    public interface OnItemDeleteListener {
        void onItemDelete(int position);
    }

    public interface OnItemCheckListener {
        void onItemChecked();
    }

    public ShoppingListAdapter(List<ShoppingItem> items, Context context, OnItemDeleteListener deleteListener) {
        this.items = items;
        this.context = context;
        this.deleteListener = deleteListener;
        this.checkListener = null;
    }

    public void setOnItemCheckListener(OnItemCheckListener listener) {
        this.checkListener = listener;
    }

    @NonNull
    @Override
    public ShoppingItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shopping_list, parent, false);
        return new ShoppingItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingItemViewHolder holder, int position) {
        ShoppingItem item = items.get(position);

        holder.tvItemName.setText(item.getName());
        holder.tvItemQuantity.setText(item.getQuantity());
        holder.tvItemCategory.setText(item.getCategory());
        holder.checkBox.setChecked(item.isChecked());

        // Aplicar estilo tachado si está marcado
        if (item.isChecked()) {
            holder.tvItemName.setPaintFlags(holder.tvItemName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tvItemQuantity.setPaintFlags(holder.tvItemQuantity.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tvItemName.setAlpha(0.5f);
            holder.tvItemQuantity.setAlpha(0.5f);
        } else {
            holder.tvItemName.setPaintFlags(holder.tvItemName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.tvItemQuantity.setPaintFlags(holder.tvItemQuantity.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.tvItemName.setAlpha(1.0f);
            holder.tvItemQuantity.setAlpha(1.0f);
        }

        // Checkbox listener
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            item.setChecked(isChecked);
            notifyItemChanged(position);
            if (checkListener != null) {
                checkListener.onItemChecked();
            }
        });

        // Delete button listener
        holder.ivDelete.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onItemDelete(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ShoppingItemViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView tvItemName;
        TextView tvItemQuantity;
        TextView tvItemCategory;
        ImageView ivDelete;

        public ShoppingItemViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.cb_item);
            tvItemName = itemView.findViewById(R.id.tv_item_name);
            tvItemQuantity = itemView.findViewById(R.id.tv_item_quantity);
            tvItemCategory = itemView.findViewById(R.id.tv_item_category);
            ivDelete = itemView.findViewById(R.id.iv_delete_item);
        }
    }
}
