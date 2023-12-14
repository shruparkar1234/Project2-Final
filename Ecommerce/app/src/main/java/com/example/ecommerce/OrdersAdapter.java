package com.example.ecommerce;
import static com.example.ecommerce.Products.cart;
import static com.example.ecommerce.Products.count;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderViewHolder> {
    public List<Cart> productList;
    private Context context;


    private int isAdmin;


    public OrdersAdapter(Context c,List<Cart> productList,int isAdmin) {
        this.context = c;
        this.productList = productList;
        this.isAdmin = isAdmin;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.orders_layout, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Cart product = productList.get(position);

            holder.cartNameTextView.setText(product.name);
            holder.cartPriceTextView.setText("Price: $" + product.Totalprice);
            holder.date.setText("Date:" + product.date);
            holder.quantity.setText("Quantity:" + product.quantity);
            holder.productId.setText("ProductIds:" +product.productId);


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }



    public class OrderViewHolder extends RecyclerView.ViewHolder {
        public TextView cartNameTextView;
        public TextView cartPriceTextView;

        public TextView date;
        public TextView productId;
        public TextView quantity;
        public OrderViewHolder(View itemView) {
            super(itemView);
            cartNameTextView = itemView.findViewById(R.id.cartNameTextView);
            cartPriceTextView = itemView.findViewById(R.id.cartPriceTextView);
            date = itemView.findViewById(R.id.Date);
            quantity = itemView.findViewById(R.id.quantityLabelTextView);
            productId = itemView.findViewById(R.id.productLabelTextView);

        }
    }
}

