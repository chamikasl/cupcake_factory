//code completed

package com.csl.cupcake_factory.Admin;

import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.csl.cupcake_factory.Model.AdminOrders;
import com.csl.cupcake_factory.Prevalent.Prevalent;
import com.csl.cupcake_factory.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class NewOrdersActivity extends AppCompatActivity {
    private RecyclerView ordersList;
    private DatabaseReference ordersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_view_orders);
        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders");
        ordersList = findViewById(R.id.orders_list);
        ordersList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<AdminOrders> options =
                new FirebaseRecyclerOptions.Builder<AdminOrders>()
                        .setQuery(ordersRef, AdminOrders.class)
                        .build();
        FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder> adapter =
                new FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull AdminOrdersViewHolder holder, final int position, @NonNull final AdminOrders model) {

                        holder.userName.setText(model.getName());
                        holder.userPhoneNumber.setText(model.getPhone());
                        holder.userTotalPrice.setText("Rs " + model.getTotalAmount() + ".00");
                        holder.userDateTime.setText(model.getDate() + " | " + model.getTime());
                        holder.userShippingAddress.setText(model.getAddress());
                        holder.userNote.setText("Note: " + model.getNote());
                        holder.userPayment.setText(model.getPayment());
                        holder.showOrdersBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String uID = getRef(position).getKey();
                                Intent intent = new Intent(NewOrdersActivity.this, OrderedProductsActivity.class);
                                intent.putExtra("uid", uID);
                                startActivity(intent);
                            }
                        });

                        if (model.getState().equals("Processing")) {

                            holder.processOrderBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    CharSequence options[] = new CharSequence[]{
                                            "Confirm",
                                            "Cancel"
                                    };

                                    AlertDialog.Builder builder = new AlertDialog.Builder(NewOrdersActivity.this);
                                    builder.setItems(options, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if (i == 0) {
                                                // Update order status to "Confirmed"
                                                String uID = getRef(position).getKey();
                                                DatabaseReference orderRef = ordersRef.child(uID);
                                                orderRef.child("state").setValue("Confirmed");
                                            } else if (i == 1) {
                                                String uID = getRef(position).getKey();
                                                DatabaseReference orderRef = ordersRef.child(uID);
                                                orderRef.child("state").setValue("Cancelled");
                                            } else {
                                                return;
                                            }
                                        }
                                    });
                                    builder.show();
                                }
                            });

                        } else if (model.getState().equals("Confirmed")) {
                            holder.showOrdersBtn.setText("Confirmed");
                            holder.showOrdersBtn.setBackgroundColor(getResources().getColor(R.color.green));
                            holder.processOrderBtn.setText("Ship");

                            holder.processOrderBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    CharSequence options[] = new CharSequence[]{
                                            "Yes",
                                            "No"
                                    };

                                    AlertDialog.Builder builder = new AlertDialog.Builder(NewOrdersActivity.this);
                                    builder.setTitle("Want to ship this order ?");
                                    builder.setItems(options, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if (i == 0) {
                                                String uID = getRef(position).getKey();
                                                DatabaseReference orderRef = ordersRef.child(uID);
                                                orderRef.child("state").setValue("Shipped");
                                            } else {
                                                return;
                                            }
                                        }
                                    });
                                    builder.show();
                                }
                            });

                        } else if (model.getState().equals("Shipped")) {
                            holder.showOrdersBtn.setText("Shipped");
                            holder.showOrdersBtn.setBackgroundColor(getResources().getColor(R.color.blue));
                            holder.processOrderBtn.setText("Complete");

                            holder.processOrderBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    CharSequence options[] = new CharSequence[]{
                                            "Yes",
                                            "No"
                                    };

                                    AlertDialog.Builder builder = new AlertDialog.Builder(NewOrdersActivity.this);
                                    builder.setTitle("Want to complete this order ?");
                                    builder.setItems(options, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if (i == 0) {
                                                String uID = getRef(position).getKey();
                                                DatabaseReference orderRef = ordersRef.child(uID);
                                                orderRef.child("state").setValue("Completed");
                                            } else {
                                                return;
                                            }
                                        }
                                    });
                                    builder.show();
                                }
                            });

                        } else if (model.getState().equals("Completed")) {
                            holder.showOrdersBtn.setText("Completed");
                            holder.showOrdersBtn.setBackgroundColor(getResources().getColor(R.color.yellow));
                            holder.processOrderBtn.setText("Remove");
                            holder.processOrderBtn.setBackgroundColor(getResources().getColor(R.color.colorFavoriteTrue));


                            holder.processOrderBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    CharSequence options[] = new CharSequence[]{
                                            "Yes",
                                            "No"
                                    };

                                    AlertDialog.Builder builder = new AlertDialog.Builder(NewOrdersActivity.this);
                                    builder.setTitle("Want to remove this order ?");
                                    builder.setItems(options, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if (i == 0) {
                                                String uID = getRef(position).getKey();
                                                RemoverOrder(uID);
                                            } else {
                                                return;
                                            }
                                        }
                                    });
                                    builder.show();
                                }
                            });

                        } else if (model.getState().equals("Cancelled")) {
                            holder.showOrdersBtn.setText("Cancelled");
                            holder.showOrdersBtn.setBackgroundColor(getResources().getColor(R.color.colorRed));
                            holder.processOrderBtn.setText("Remove");
                            holder.processOrderBtn.setBackgroundColor(getResources().getColor(R.color.colorFavoriteTrue));

                            holder.processOrderBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    CharSequence options[] = new CharSequence[]{
                                            "Yes",
                                            "No"
                                    };

                                    AlertDialog.Builder builder = new AlertDialog.Builder(NewOrdersActivity.this);
                                    builder.setTitle("Want to remove this order ?");
                                    builder.setItems(options, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if (i == 0) {
                                                String uID = getRef(position).getKey();
                                                RemoverOrder(uID);
                                            } else {
                                                return;
                                            }
                                        }
                                    });
                                    builder.show();
                                }
                            });

                        } else {
                            return;
                        }
                    }

                    @NonNull
                    @Override
                    public AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_orders_layout, parent, false);
                        return new AdminOrdersViewHolder(view);
                    }
                };
        ordersList.setAdapter(adapter);
        adapter.startListening();

    }


    public static class AdminOrdersViewHolder extends RecyclerView.ViewHolder {

        public TextView userName, userPhoneNumber, userTotalPrice, userDateTime, userShippingAddress, userNote, userPayment;
        public Button showOrdersBtn, processOrderBtn;

        public AdminOrdersViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.order_user_name);
            userPhoneNumber = itemView.findViewById(R.id.order_phone_number);
            userTotalPrice = itemView.findViewById(R.id.order_total_price);
            userDateTime = itemView.findViewById(R.id.order_date_time);
            userShippingAddress = itemView.findViewById(R.id.order_address_city);
            showOrdersBtn = itemView.findViewById(R.id.show_all_product_btn);
            processOrderBtn = itemView.findViewById(R.id.process_order_btn);
            userNote = itemView.findViewById(R.id.order_note);
            userPayment = itemView.findViewById(R.id.type_of_payment);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NewOrdersActivity.this, MainScreenActivity.class);
        startActivity(intent);
    }

    private void RemoverOrder(String uID) {
        ordersRef.child(uID).removeValue();
    }
}