package com.example.midgetpixels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView welcomeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
        welcomeTextView = findViewById(R.id.welcomeTextView);

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            // User is logged in, display their name
            String userName = currentUser.getDisplayName();
            welcomeTextView.setText( userName); // Display the user's name
        } else {
            // User is not logged in, redirect to MainActivity
            Intent intent = new Intent(Home.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close this activity to prevent going back to the home screen
        }

        Button shopButton = findViewById(R.id.shopBtn);

        shopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Shopping.class);
                startActivity(intent);
            }
        });

        Button chatButton = findViewById(R.id.chatBtn);

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Chat.class);
                startActivity(intent);
            }
        });

        Button notificationBtn = findViewById(R.id.notificationBtn);
        notificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, AppNotification.class);
                startActivity(intent);
            }
        });

        Button profileBtn = findViewById(R.id.userBtn);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, profile.class);
                startActivity(intent);
            }
        });
    }
}
