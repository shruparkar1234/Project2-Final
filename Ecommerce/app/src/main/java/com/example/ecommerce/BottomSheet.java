package com.example.ecommerce;
import static com.example.ecommerce.Products.isEdit;
import static com.example.ecommerce.Products.pos;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.room.Room;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

public class BottomSheet extends BottomSheetDialogFragment {

    private View.OnClickListener updateListener;

    public BottomSheet(View.OnClickListener updateView) {
        this.updateListener =  updateView;
    }
    TextInputEditText description;
    TextInputEditText price;
    TextInputEditText quantity;

    TextView addProduct;

    Dao dao;

    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_bottom_sheet, container, false);
        description = view.findViewById(R.id.ProductName);
        price = view.findViewById(R.id.price);
        quantity = view.findViewById(R.id.ProductQuantity);
        addProduct = view.findViewById(R.id.adP);
        user = Room.databaseBuilder(requireContext(), User.class, "app-database").build();
        dao = user.userDao();
        if(isEdit)
        addProduct.setText("Edit Product");
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(isEdit){
            editProduct();
            }else{
            addProduct();
            }

            }
        });
        return view;
    }



    public void editProduct(){
    try {
        final String name = description.getText().toString();
        final int pPrice = Integer.parseInt(price.getText().toString());
        final int pQuantity = Integer.parseInt(quantity.getText().toString());

        if (name != null && pQuantity != 0 && pPrice != 0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Product p = new Product();
                    p.productId = pos;
                    p.description = (name);
                    p.price = (pPrice);
                    p.quantity = (pQuantity);
                    dao.updateProduct(p);
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(requireContext(), "Product edited", Toast.LENGTH_SHORT).show();
                            updateListener.onClick(addProduct);
                            isEdit = false;
                            dismiss();
                        }
                    });
                }
            }).start();
        }

    }catch (Exception e){

    }


    }

    @Override
    public void onStart() {
        super.onStart();
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetStyle);
    }

    public void addProduct() {
        try{
        final String name = description.getText().toString();
        final int pPrice = Integer.parseInt(price.getText().toString());
        final int pQuantity = Integer.parseInt(quantity.getText().toString());

        if (name != null && pQuantity != 0 && pPrice != 0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Product p = new Product();
                    p.description = (name);
                    p.price =(pPrice);
                    p.quantity =(pQuantity);
                    dao.insertProduct(p);
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                        Toast.makeText(requireContext(), "Product added", Toast.LENGTH_SHORT).show();
                         updateListener.onClick(addProduct);
                        dismiss();
                        }
                    });
                }
            }).start();
        }}catch (Exception e){

        }
    }

    interface UpdateView{
    public void update();

    }
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);

        isEdit = false;
    }



}