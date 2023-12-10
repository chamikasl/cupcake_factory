package com.csl.cupcake_factory.ViewHolder;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.csl.cupcake_factory.Interface.ItemClickListner;
import com.csl.cupcake_factory.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtProductName, txtProductCategory, txtProductPrice;
    public ImageView imageView;
    public View imgToCart;
    public ItemClickListner listner;
    public ProductViewHolder(View itemView) {
        super(itemView);

        imgToCart = (ImageView) itemView.findViewById(R.id.imgToCart);

        imageView = (ImageView) itemView.findViewById(R.id.product_image);

        txtProductName = (TextView) itemView.findViewById(R.id.product_name);
        txtProductCategory = (TextView) itemView.findViewById(R.id.product_description);
        txtProductPrice = (TextView) itemView.findViewById(R.id.product_price);
    }

    public void setItemClickListner(ItemClickListner listner) {
        this.listner = listner;
    }

    @Override
    public void onClick(View view) {
        listner.onClick(view, getAdapterPosition(), false);
    }
}

