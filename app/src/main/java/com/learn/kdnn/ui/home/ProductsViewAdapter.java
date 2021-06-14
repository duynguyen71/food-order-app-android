package com.learn.kdnn.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.learn.kdnn.R;
import com.learn.kdnn.model.Product;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import lombok.Setter;


public class ProductsViewAdapter extends RecyclerView.Adapter<ProductsViewAdapter.MyViewHolder> {

    private List<Product> products;
    private Context context;

    @Setter
    private OnProductViewClickListener onItemClick;

    public ProductsViewAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ProductsViewAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_entry, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProductsViewAdapter.MyViewHolder holder, int position) {
        Product product = products.get(position);
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText(String.valueOf(product.getPrice()));

        Glide.with(context)
                .load(product.getImgUri())
                .centerCrop()
                .error(R.drawable.ic_menu_camera)
                .into(holder.productImage);

        holder.itemView.setOnClickListener(v -> {
            if (onItemClick != null) {
                onItemClick.onProductViewClick(position, product,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (products != null && !products.isEmpty())
            return products.size();
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice;
        AppCompatImageView productImage;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.productName);
            tvPrice = itemView.findViewById(R.id.productPrice);
            productImage = itemView.findViewById(R.id.productPrimaryImage);
        }
    }

    public interface OnProductViewClickListener {
        void onProductViewClick(int position, Product product,int index);
    }
}
