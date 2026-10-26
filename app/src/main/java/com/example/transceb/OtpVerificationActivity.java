package com.example.transceb;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OtpVerificationActivity extends AppCompatActivity {

    private EditText txtOtpCode;
    private String userEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        if (getIntent() != null && getIntent().hasExtra("email")) {
            userEmail = getIntent().getStringExtra("email");
        }

        txtOtpCode = findViewById(R.id.txtOtpCode);
        Button btnVerifyOtp = findViewById(R.id.btnVerifyOtp);
        TextView btnBackToForgotPassword = findViewById(R.id.btnBackToForgotPassword);

        if (btnVerifyOtp != null) {
            btnVerifyOtp.setOnClickListener(v -> verifyOtp());
        }

        if (btnBackToForgotPassword != null) {
            btnBackToForgotPassword.setOnClickListener(v -> finish());
        }
    }

    private void verifyOtp() {
        String code = txtOtpCode.getText().toString().trim();
        if (code.length() < 4) {
            Toast.makeText(this, "Please enter a valid OTP code", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "OTP Verification Successful!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(OtpVerificationActivity.this, HomeActivity.class);
        intent.putExtra("username", !userEmail.isEmpty() ? userEmail : "User");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
