//code completed

package com.csl.cupcake_factory;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.csl.cupcake_factory.Model.Users;
import com.csl.cupcake_factory.Prevalent.Prevalent;
import com.csl.cupcake_factory.User.HomeActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);        //hide action bar

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);        //set view screen

        ImageView imageView = (ImageView) findViewById(R.id.logo);        //get logo
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);        //apply animation
        imageView.startAnimation(animation);        //set animation
        Paper.init(this);

        //set delay 3 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                String UserPhoneKey = Paper.book().read(Prevalent.UserPhoneKey);
                String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);

                if (TextUtils.isEmpty(UserPhoneKey) && TextUtils.isEmpty(UserPasswordKey)) {
                    Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);        //set next activity
                    startActivity(intent);

                } else {
                    AllowAccess(UserPhoneKey, UserPasswordKey);
                }
            }

        }, 3000);
    }

    protected void AllowAccess(final String phone, final String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Users").child(phone).exists()) {

                    Users usersData = dataSnapshot.child("Users").child(phone).getValue(Users.class);
                    if (usersData.getPhone().equals(phone)) {
                        if (usersData.getPassword().equals(password)) {
                            //Toast.makeText(SplashScreenActivity.this, "Welcome again, You logged Successfully. ✔", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
                            Prevalent.currentOnlineUser = usersData;
                            startActivity(intent);
                            finish();

                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
