//TODO: Add(DONE), Edit, Delete

package com.csl.cupcake_factory.Admin;

import android.app.ProgressDialog;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.csl.cupcake_factory.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddNewCategoryActivity extends AppCompatActivity {
    private String CategoryName, saveCurrentDate, saveCurrentTime;
    private Button AddNewCategoryButton;
    private EditText InputCategoryName;
    private String categoryRandomKey;
    private DatabaseReference CategoriesRef;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_add_new_category);

        CategoriesRef = FirebaseDatabase.getInstance().getReference();

        AddNewCategoryButton = (Button) findViewById(R.id.add_new_category);
        InputCategoryName = (EditText) findViewById(R.id.category_name);

        loadingBar = new ProgressDialog(this);

        AddNewCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateProductData();
            }
        });
    }

    private void ValidateProductData() {
        CategoryName = InputCategoryName.getText().toString();
        if (TextUtils.isEmpty(CategoryName)) {
            Toast.makeText(this, "Please write the category name !", Toast.LENGTH_SHORT).show();
        } else {
            StoreCategoryInformation();
            SaveCategoryInfoToDatabase();
        }

    }

    private void StoreCategoryInformation() {
        loadingBar.setTitle("Adding a New Category");
        loadingBar.setMessage("Please wait while we are adding the new category.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        categoryRandomKey = saveCurrentDate + " - " + saveCurrentTime;

    }

    private void SaveCategoryInfoToDatabase() {
        HashMap<String, Object> categoryMap = new HashMap<>();
        categoryMap.put("cid", categoryRandomKey);
        categoryMap.put("cname", CategoryName);

        CategoriesRef.child("Categories").child(CategoryName).updateChildren(categoryMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    loadingBar.dismiss();
                    Toast.makeText(AddNewCategoryActivity.this, "Category Added Successfully. ✔", Toast.LENGTH_SHORT).show();
                    InputCategoryName.setText("");
                } else {
                    loadingBar.dismiss();
                    String message = task.getException().toString();
                    Toast.makeText(AddNewCategoryActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddNewCategoryActivity.this, MainScreenActivity.class);
        startActivity(intent);
    }
}
