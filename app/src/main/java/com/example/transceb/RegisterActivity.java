package com.example.transceb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText txtFirstName;
    private EditText txtUsername;
    private EditText txtPassword;
    private ImageButton btnTogglePassword;

    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtFirstName = findViewById(R.id.txtFirstName);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnTogglePassword = findViewById(R.id.btnTogglePassword);
        Button btnRegister = findViewById(R.id.btnRegister);
        ImageButton btnGoogleSignUp = findViewById(R.id.btnGoogleSignUp);
        TextView btnBackToLogin = findViewById(R.id.btnBackToLogin);

        if (btnTogglePassword != null) {
            btnTogglePassword.setOnClickListener(v -> togglePasswordVisibility());
        }

        if (btnRegister != null) {
            btnRegister.setOnClickListener(v -> performRegistration());
        }

        if (btnGoogleSignUp != null) {
            btnGoogleSignUp.setOnClickListener(v -> performGoogleSignUp());
        }

        if (btnBackToLogin != null) {
            btnBackToLogin.setOnClickListener(v -> finish());
        }
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            txtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            btnTogglePassword.setImageResource(R.drawable.ic_eye_visible);
            isPasswordVisible = false;
        } else {
            txtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            btnTogglePassword.setImageResource(R.drawable.ic_eye_off);
            isPasswordVisible = true;
        }
        txtPassword.setSelection(txtPassword.getText().length());
    }

    private void performRegistration() {
        String name = txtFirstName.getText().toString().trim();
        String email = txtUsername.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(RegisterActivity.this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save account details into SharedPreferences
        SharedPreferences prefs = getSharedPreferences("transceb_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("registered_firstname", name);
        editor.putString("registered_username", email);
        editor.putString("registered_password", password);
        editor.apply();

        Toast.makeText(RegisterActivity.this, "Account created successfully! Please sign in.", Toast.LENGTH_LONG).show();
        finish();
    }

    private void performGoogleSignUp() {
        Toast.makeText(RegisterActivity.this, "Signing up with Google...", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
        intent.putExtra("username", "Google User");
        startActivity(intent);
        finish();
    }
}
