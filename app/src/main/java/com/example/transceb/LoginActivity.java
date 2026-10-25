package com.example.transceb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private EditText txtUsername;
    private EditText txtPassword;
    private ImageButton btnTogglePassword;
    private Button btnLogin;
    private ProgressBar progressBar;

    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnTogglePassword = findViewById(R.id.btnTogglePassword);
        btnLogin = findViewById(R.id.btnLogin);
        TextView btnGoToSignUp = findViewById(R.id.btnGoToSignUp);
        progressBar = findViewById(R.id.progressBar);

        btnTogglePassword.setOnClickListener(v -> togglePasswordVisibility());
        btnLogin.setOnClickListener(v -> performLogin());

        btnGoToSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
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

    private void performLogin() {
        String username = txtUsername.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
        btnLogin.setEnabled(false);

        boolean isValid = validateLogin(username, password);

        if (progressBar != null) progressBar.setVisibility(View.GONE);
        btnLogin.setEnabled(true);

        if (isValid) {
            Toast.makeText(LoginActivity.this, "Welcome back, " + username + "!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(LoginActivity.this, "Incorrect username or password", Toast.LENGTH_LONG).show();
        }
    }

    private boolean validateLogin(String username, String password) {
        if ("admin".equalsIgnoreCase(username) && "123456".equals(password)) {
            return true;
        }

        if ("user".equalsIgnoreCase(username) && "123456".equals(password)) {
            return true;
        }

        SharedPreferences prefs = getSharedPreferences("transceb_prefs", MODE_PRIVATE);
        String savedUser = prefs.getString("registered_username", "");
        String savedPass = prefs.getString("registered_password", "");

        return !savedUser.isEmpty() && savedUser.equalsIgnoreCase(username) && Objects.equals(savedPass, password);
    }
}
