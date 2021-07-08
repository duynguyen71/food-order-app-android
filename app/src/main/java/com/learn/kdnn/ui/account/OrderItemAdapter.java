package com.learn.kdnn.ui.account;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learn.kdnn.R;
import com.learn.kdnn.model.CartItem;
import com.learn.kdnn.model.Order;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.MyViewHolder> {

    private List<Order> orderList;
    private Context context;

    public OrderItemAdapter(List<Order> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public OrderItemAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.order_item_entry, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull OrderItemAdapter.MyViewHolder holder, int position) {

        Order order = orderList.get(position);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String stringDate = dateFormat.format(order.getOrderDate());
        holder.tvOrderDate.setText(stringDate);

        if(order.getCartItems()!=null &&!order.getCartItems().isEmpty())
        {
            for (int i = 0; i < order.getCartItems().size(); i++) {
                CartItem cartItem = order.getCartItems().get(i);
                StringBuffer buffer = new StringBuffer("");
                String name = cartItem.getProduct().getName();
                int qty = cartItem.getQuality();
                buffer.append(name);
                buffer.append(" (" + qty + ") ");
                buffer.append(" - ");
                TextView tvDetails = holder.tvDetails;
                tvDetails.setText(buffer);
            }

        }

        holder.tvTotalPrice.setText("$" + order.getTotalPrice());
    }

    @Override
    public int getItemCount() {
        if (orderList == null || orderList.isEmpty()) {
            return 0;
        }
        return orderList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvDetails, tvOrderDate;
        TextView tvTotalPrice;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvDetails = itemView.findViewById(R.id.tvOrderDetail);
            tvTotalPrice = itemView.findViewById(R.id.tvOrderTotal);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
        }
    }
}
