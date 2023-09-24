package com.example.midgetpixels;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button shopbutton= findViewById(R.id.shopBtn);

        shopbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Shopping.class);
                startActivity(intent);
            }
        });
   Button chatbutton= findViewById(R.id.chatBtn);

        chatbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Chat.class);
                startActivity(intent);
            }
        });

        Button notificationBtn= findViewById(R.id.notificationBtn);
        notificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, AppNotification.class);
                startActivity(intent);
            }
        });
        Button profile= findViewById(R.id.userBtn);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, profile.class);
                startActivity(intent);
            }
        });

    }
}