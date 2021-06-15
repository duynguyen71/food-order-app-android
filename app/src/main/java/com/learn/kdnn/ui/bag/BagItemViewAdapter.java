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
import com.learn.kdnn.model.CartItem;
import com.learn.kdnn.model.Product;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import lombok.Setter;

public class BagItemViewAdapter extends RecyclerView.Adapter<BagItemViewAdapter.ItemViewHolder> {

    private List<CartItem> cartItems;
    private Context context;



    @Setter
    private OnOptionsClickListener onOptionsClickListener;

    public BagItemViewAdapter(List<CartItem> cartItems, Context context) {
        this.cartItems = cartItems;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public BagItemViewAdapter.ItemViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shopping_bag_item_entry, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BagItemViewAdapter.ItemViewHolder holder, int position) {
        CartItem cartitem = cartItems.get(position);

        Product product = cartitem.getProduct();

        holder.tvName.setText(product.getName());
        holder.tvPrice.setText("$" + product.getPrice());
        holder.tvQuality.setText(String.valueOf(cartitem.getQuality()));
        holder.tvOptions.setOnClickListener(v->{
            if(onOptionsClickListener!=null){
                onOptionsClickListener.onOptionClick(position,product);
            }
        });
        Glide.with(context).load(product.getPrimaryImgUrl()).centerCrop().into(holder.primaryImg);

    }

    @Override
    public int getItemCount() {
        if (cartItems != null && !cartItems.isEmpty()) {
            return cartItems.size();
        }
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvPrice, tvName,tvOptions,tvQuality;
        ImageView primaryImg;

        public ItemViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.productname);
            tvPrice = itemView.findViewById(R.id.price);
            primaryImg = itemView.findViewById(R.id.productImg);
            tvOptions = itemView.findViewById(R.id.bagItemOptions);
            tvQuality = itemView.findViewById(R.id.quality);
        }

    }
    public interface OnOptionsClickListener{
        void onOptionClick(int index,Product product);
    }


}
