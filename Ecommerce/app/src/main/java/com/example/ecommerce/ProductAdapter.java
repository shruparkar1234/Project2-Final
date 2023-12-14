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

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    public List<Product> productList;
    private Context context;

    private Dao dao;

    private View.OnClickListener listener;

    private int isAdmin;

    private TextView button;

    private String name;


    public ProductAdapter(Context context, List<Product> productList, Dao dao, View.OnClickListener onClickListener, int isAdmin, TextView button,
                          String name) {
        this.context = context;
        this.productList = productList;
        this.dao = dao;
        listener = onClickListener;
        this.isAdmin = isAdmin;
        this.button = button;
        this.name = name;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_layout, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

            Log.d("products description",product.description.toString());
            if(product.quantity>0){
            holder.productName.setText(product.description);
            holder.productPrice.setText("Price: $" + product.price);
            if(isAdmin==0){
            holder.editButton.setText("Add");
            holder.productQuantity.setVisibility(View.INVISIBLE);
            }else{
            holder.productQuantity.setText("Quantity: " + product.quantity);
            }
            holder.editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isAdmin==1) {
                        Products.pos = product.productId;
                        Products.isEdit = true;
                        BottomSheet customBottomSheet = new BottomSheet(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                listener.onClick(view);
                            }
                        });

                        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

                        customBottomSheet.show(fragmentManager, "BottomSheet");
                    }else{
                        count+=1;
                        button.setText("Place order "+count);
                        button.setVisibility(View.VISIBLE);
                        cart.name = name;
                        Log.d("product id is ", String.valueOf(product.productId));
                        Log.d("product id count is ", String.valueOf(count));
                        cart.productId.add(product.productId);
                        cart.Totalprice+=product.price;
                        // notifyDataSetChanged();
                    }
                }
            });}

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }



    public class ProductViewHolder extends RecyclerView.ViewHolder {
        public TextView productName;
        public TextView productPrice;
        public TextView productQuantity;
        public Button editButton;

        public ProductViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productQuantity = itemView.findViewById(R.id.productQuantity);
            editButton = itemView.findViewById(R.id.editButton);
        }
    }
}

