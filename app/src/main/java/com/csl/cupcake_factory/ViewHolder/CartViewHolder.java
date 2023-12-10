package com.csl.cupcake_factory.ViewHolder;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.csl.cupcake_factory.Interface.ItemClickListner;
import com.csl.cupcake_factory.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtProductName, txtProductPrice, txtProductQuantity;
    public ImageView imageView;
    public View remove, edit;
    public ItemClickListner listner;

    public CartViewHolder(View itemView) {
        super(itemView);

        remove = itemView.findViewById(R.id.removeimg);
        edit = itemView.findViewById(R.id.editimg);

        imageView = (ImageView) itemView.findViewById(R.id.cart_image);
        txtProductName = itemView.findViewById(R.id.cart_product_name);
        txtProductPrice = itemView.findViewById(R.id.cart_product_price);
        txtProductQuantity = itemView.findViewById(R.id.cart_product_quantity);
    }

    public void setItemClickListner(ItemClickListner listner) {
        this.listner = listner;
    }

    @Override
    public void onClick(View view) {
        listner.onClick(view, getAdapterPosition(), false);
    }
}


