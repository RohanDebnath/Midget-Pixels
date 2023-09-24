package com.example.midgetpixels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailEditText, passwordEditText;
    private CheckBox keepLoggedInCheckbox;

    // SharedPreferences keys
    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String PREF_EMAIL = "email";
    private static final String PREF_KEEP_LOGGED_IN = "keepLoggedIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        emailEditText = findViewById(R.id.emaillogin);
        passwordEditText = findViewById(R.id.passwordLogin);
        keepLoggedInCheckbox = findViewById(R.id.keepLoggedInCheckbox);

        TextView signUpLink = findViewById(R.id.hyperlink2);
        String signUpText = "Don't have an account? SignUp Here";

        // Make the text blue
        SpannableString spannableString = new SpannableString(signUpText);
        ForegroundColorSpan blueColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.purple_700)); // Define a blue color
        spannableString.setSpan(blueColorSpan, signUpText.indexOf("SignUp"), signUpText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        signUpLink.setText(spannableString);
        signUpLink.setMovementMethod(LinkMovementMethod.getInstance());

        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the SignUp activity when the link is clicked
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
            }
        });

        Button loginButton = findViewById(R.id.loginBtn);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(email, password);
                }
            }
        });

        // Check if "Keep me logged in" is checked and navigate to Home Activity
        if (isKeepLoggedIn()) {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null) {
                // User is already logged in, navigate to Home Activity
                Intent homeIntent = new Intent(MainActivity.this, Home.class);
                startActivity(homeIntent);
                finish(); // Close this activity to prevent going back to the login screen
            }
        }
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Login successful
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // Login failed
                            Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        // Save the login status and email if "Keep me logged in" is checked
        if (keepLoggedInCheckbox.isChecked()) {
            SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
            editor.putBoolean(PREF_KEEP_LOGGED_IN, true);
            editor.putString(PREF_EMAIL, email);
            editor.apply();
        } else {
            // Clear the saved preferences if "Keep me logged in" is not checked
            SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
            editor.clear().apply();
        }
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            // User is logged in, navigate to Home Activity
            Intent intent = new Intent(MainActivity.this, Home.class);
            startActivity(intent);
            finish(); // Close this activity to prevent going back to the login screen
        }
    }

    // Check if "Keep me logged in" is checked
    private boolean isKeepLoggedIn() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return prefs.getBoolean(PREF_KEEP_LOGGED_IN, false);
    }
}
