package com.learn.kdnn.ui.bag;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.learn.kdnn.R;
import com.learn.kdnn.model.Product;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BagItemViewAdapter extends RecyclerView.Adapter<BagItemViewAdapter.ItemViewHolder> {

    private List<Product> products;
    private Context context;

    public BagItemViewAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public BagItemViewAdapter.ItemViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bag_item_entry, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BagItemViewAdapter.ItemViewHolder holder, int position) {
        Product product = products.get(position);

        holder.tvName.setText(product.getName());
        holder.tvPrice.setText("$" + product.getPrice());
        Glide.with(context).load(product.getImgUri()).centerCrop().into(holder.primaryImg);

    }

    @Override
    public int getItemCount() {
        if (products != null && !products.isEmpty()) {
            return products.size();
        }
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvPrice, tvName;
        ImageView primaryImg;

        public ItemViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.productname);
            tvPrice = itemView.findViewById(R.id.price);
            primaryImg = itemView.findViewById(R.id.productImg);
        }

    }
}
