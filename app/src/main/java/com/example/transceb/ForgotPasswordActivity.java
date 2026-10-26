package com.example.transceb;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText txtForgotEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        txtForgotEmail = findViewById(R.id.txtForgotEmail);
        Button btnSendOtp = findViewById(R.id.btnSendOtp);
        TextView btnBackToSignIn = findViewById(R.id.btnBackToSignIn);

        if (btnSendOtp != null) {
            btnSendOtp.setOnClickListener(v -> sendOtpCode());
        }

        if (btnBackToSignIn != null) {
            btnBackToSignIn.setOnClickListener(v -> finish());
        }
    }

    private void sendOtpCode() {
        String email = txtForgotEmail.getText().toString().trim();
        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter your email address", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "OTP code sent to " + email, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ForgotPasswordActivity.this, OtpVerificationActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);
    }
}
