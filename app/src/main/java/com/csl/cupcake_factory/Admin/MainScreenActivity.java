//code completed

package com.csl.cupcake_factory.Admin;

import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.csl.cupcake_factory.LoginActivity;
import com.csl.cupcake_factory.R;

public class MainScreenActivity extends AppCompatActivity {
    private Button LogoutBtn, ViewOrdersBtn, CategoriesBtn, ItemsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_main_screen);

        CategoriesBtn = (Button) findViewById(R.id.add_view_categories_btn);
        CategoriesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainScreenActivity.this, AddNewCategoryActivity.class);
                startActivity(intent);
            }
        });

        ItemsBtn = (Button) findViewById(R.id.add_view_products_btn);
        ItemsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainScreenActivity.this, AddNewProductActivity.class);
                startActivity(intent);
            }
        });

        ViewOrdersBtn = (Button) findViewById(R.id.view_orders_btn);
        ViewOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainScreenActivity.this, NewOrdersActivity.class);
                startActivity(intent);
            }
        });

        LogoutBtn = (Button) findViewById(R.id.admin_logout_btn);
        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainScreenActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainScreenActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
