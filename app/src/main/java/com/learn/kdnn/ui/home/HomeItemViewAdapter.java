package com.learn.kdnn.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestFutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.learn.kdnn.R;
import com.learn.kdnn.model.Product;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import lombok.Setter;


public class HomeItemViewAdapter extends RecyclerView.Adapter<HomeItemViewAdapter.MyViewHolder> {

    private List<Product> products;
    private Context context;

    @Setter
    private boolean isUsingListView;
    @Setter
    private OnProductViewClickListener onItemClick;

    public HomeItemViewAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public HomeItemViewAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_entry, parent, false);

        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull HomeItemViewAdapter.MyViewHolder holder, int position) {
        Product product = products.get(position);
        holder.tvName.setText(product.getName());
        holder.category.setText(product.getCategory());

        double salesPrice = product.getPrice();
        double temp = salesPrice;
        if (product.getDiscountPer() > 0) {
            //discount per
            TextView tvDiscountPer = holder.tvDiscountPer;
            tvDiscountPer.setVisibility(View.VISIBLE);
            tvDiscountPer.setText("-" + product.getDiscountPer() + "%");
            //standard price
            TextView tvStandardPrice = holder.tvStandardPrice;
            tvStandardPrice.setVisibility(View.VISIBLE);
            tvStandardPrice.setText("$" +  String.format("%.2f",temp));
            tvStandardPrice.setPaintFlags(tvStandardPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            //sales price
            salesPrice = salesPrice - salesPrice * (product.getDiscountPer() / 100);
        }
        holder.tvPrice.setText("$" + String.format("%.2f",salesPrice));


        Glide.with(context)
                .load(product.getPrimaryImgUrl())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable @org.jetbrains.annotations.Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .centerCrop()
                .error(R.drawable.ic_menu_camera)
                .into(holder.productImage)
        ;

        holder.itemView.setOnClickListener(v -> {
            if (onItemClick != null) {
                onItemClick.onProductViewClick(position, product, position);
            }
        });

        CardView containerWrapper = (CardView) holder.itemView.findViewById(R.id.product_entry_container);
        containerWrapper.setRadius(5);
        LinearLayout container = (LinearLayout) holder.itemView.findViewById(R.id.productEntryViewGroup);
        LinearLayout productNameContainer = (LinearLayout) holder.itemView.findViewById(R.id.productnNameContainer);

        if (isUsingListView) {
            FrameLayout.LayoutParams params = new CardView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 10, 10, 10);
            containerWrapper.setLayoutParams(params);
            container.setOrientation(LinearLayout.HORIZONTAL);
            productNameContainer.setOrientation(LinearLayout.VERTICAL);
        } else {
            FrameLayout.LayoutParams params = new CardView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 10, 10, 10);
            containerWrapper.setLayoutParams(params);
            container.setOrientation(LinearLayout.VERTICAL);
            productNameContainer.setOrientation(LinearLayout.HORIZONTAL);

        }
    }

    @Override
    public int getItemCount() {
        if (products != null && !products.isEmpty())
            return products.size();
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvDiscountPer, tvStandardPrice, category;
        AppCompatImageView productImage;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.productName);
            tvPrice = itemView.findViewById(R.id.productPrice);
            productImage = itemView.findViewById(R.id.productPrimaryImage);
            tvDiscountPer = itemView.findViewById(R.id.dicountPer);
            tvStandardPrice = itemView.findViewById(R.id.productStandardPrice);
            category = itemView.findViewById(R.id.productCategory);
        }
    }

    public interface OnProductViewClickListener {
        void onProductViewClick(int position, Product product, int index);
    }
}
