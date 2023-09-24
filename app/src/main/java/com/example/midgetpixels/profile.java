package com.example.midgetpixels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        TextView logoutButton = findViewById(R.id.logoutBtn);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sign the user out using Firebase Auth
                FirebaseAuth.getInstance().signOut();

                // Redirect the user to the login or splash screen
                Intent intent = new Intent(profile.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        TextView profileUserName = findViewById(R.id.profileUserName);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String userName = currentUser.getDisplayName(); // Get the user's display name
            if (userName != null && !userName.isEmpty()) {
                profileUserName.setText(userName);
            }
        }
    }
}
