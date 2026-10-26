package com.example.transceb;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private ImageButton btnLogout;
    private EditText txtSearchJeepney;

    // Quick Chips
    private TextView chip04L;
    private TextView chip13C;
    private TextView chip17B;
    private TextView chip62B;

    // Service Cards
    private View cardFareCalc;
    private View cardMapGuide;
    private View cardTerminals;

    // Route Cards
    private View cardRoute04L;
    private View cardRoute13C;
    private View cardRoute17B;

    // Fare Calculator Views
    private Button btnDist4km;
    private Button btnDist8km;
    private Button btnDist12km;
    private TextView lblFareAmount;
    private TextView lblFareDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Bind Views
        TextView lblWelcomeUser = findViewById(R.id.lblWelcomeUser);
        TextView lblUsernameTag = findViewById(R.id.lblUsernameTag);
        btnLogout = findViewById(R.id.btnLogout);
        Button btnHomeLogout = findViewById(R.id.btnHomeLogout);
        txtSearchJeepney = findViewById(R.id.txtSearchJeepney);

        chip04L = findViewById(R.id.chip04L);
        chip13C = findViewById(R.id.chip13C);
        chip17B = findViewById(R.id.chip17B);
        chip62B = findViewById(R.id.chip62B);

        cardFareCalc = findViewById(R.id.cardFareCalc);
        cardMapGuide = findViewById(R.id.cardMapGuide);
        cardTerminals = findViewById(R.id.cardTerminals);

        cardRoute04L = findViewById(R.id.cardRoute04L);
        cardRoute13C = findViewById(R.id.cardRoute13C);
        cardRoute17B = findViewById(R.id.cardRoute17B);

        btnDist4km = findViewById(R.id.btnDist4km);
        btnDist8km = findViewById(R.id.btnDist8km);
        btnDist12km = findViewById(R.id.btnDist12km);
        lblFareAmount = findViewById(R.id.lblFareAmount);
        lblFareDetail = findViewById(R.id.lblFareDetail);

        // Retrieve logged-in Username
        Intent intent = getIntent();
        String passedUser = intent != null ? intent.getStringExtra("username") : null;
        String username = (passedUser != null && !passedUser.isEmpty()) ? passedUser : "admin";

        if (lblWelcomeUser != null) {
            lblWelcomeUser.setText(getString(R.string.welcome_commuter, username));
        }
        if (lblUsernameTag != null) {
            lblUsernameTag.setText(String.format("@%s", username));
        }

        // Setup Logout Handlers
        if (btnLogout != null) btnLogout.setOnClickListener(v -> performLogout());
        if (btnHomeLogout != null) btnHomeLogout.setOnClickListener(v -> performLogout());

        // Search Input Watcher
        if (txtSearchJeepney != null) {
            txtSearchJeepney.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    filterRoutes(s.toString().trim());
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });

            txtSearchJeepney.setOnEditorActionListener((v, actionId, event) -> {
                String route = txtSearchJeepney.getText().toString().trim();
                if (!route.isEmpty()) {
                    Toast.makeText(HomeActivity.this, "Searching Jeepney Route: " + route + " in Metro Cebu...", Toast.LENGTH_SHORT).show();
                }
                return false;
            });
        }

        // Chip Clicks
        setupChipListeners();

        // Feature Service Clicks
        setupServiceListeners();

        // Route Card Clicks
        setupRouteCardListeners();

        // Fare Calculator Buttons
        setupFareCalculator();
    }

    private void setupChipListeners() {
        if (chip04L != null) {
            chip04L.setOnClickListener(v -> setChipSearchText("04L"));
        }
        if (chip13C != null) {
            chip13C.setOnClickListener(v -> setChipSearchText("13C"));
        }
        if (chip17B != null) {
            chip17B.setOnClickListener(v -> setChipSearchText("17B"));
        }
        if (chip62B != null) {
            chip62B.setOnClickListener(v -> setChipSearchText("62B"));
        }
    }

    private void setChipSearchText(String text) {
        if (txtSearchJeepney != null) {
            txtSearchJeepney.setText(text);
            txtSearchJeepney.setSelection(text.length());
        }
    }

    private void setupServiceListeners() {
        if (cardFareCalc != null) {
            cardFareCalc.setOnClickListener(v -> {
                View fareBox = findViewById(R.id.boxFareCalculator);
                if (fareBox != null) {
                    fareBox.requestFocus();
                    Toast.makeText(this, "Quick Fare Calculator ready below!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (cardMapGuide != null) {
            cardMapGuide.setOnClickListener(v -> new AlertDialog.Builder(this)
                    .setTitle("📍 Cebu Major Stops & Landmarks")
                    .setMessage("Key Transit Hubs:\n" +
                            "• IT Park Terminal (17B, 17D, CPTD)\n" +
                            "• Fuente Osmeña Circle (04L, 14D, 06B)\n" +
                            "• Ayala Center Cebu (12L, 13C, 04L)\n" +
                            "• Colon Street & Carbon Market (04L, 10M, 62B)\n" +
                            "• SM City Cebu Terminal (01K, 10H, 12D)")
                    .setPositiveButton("Got it", null)
                    .show());
        }

        if (cardTerminals != null) {
            cardTerminals.setOnClickListener(v -> new AlertDialog.Builder(this)
                    .setTitle("🚌 Cebu City Terminals")
                    .setMessage("Major Loading & Unloading Terminals:\n\n" +
                            "1. Cebu South Bus Terminal (CSBT) - Sambag I\n" +
                            "2. SM City Cebu PUJ Terminal - North Reclamation\n" +
                            "3. Cebu IT Park Central Terminal - Lahug\n" +
                            "4. Carbon Market Jeepney Terminal - Ermita")
                    .setPositiveButton("Close", null)
                    .show());
        }
    }

    private void setupRouteCardListeners() {
        if (cardRoute04L != null) {
            cardRoute04L.setOnClickListener(v -> showRouteDetail("04L", "Lahug to Carbon Market",
                    "Gorordo Ave -> Fuente Osmeña -> Jones Ave -> Colon St -> Carbon Market",
                    "₱13.00 Base Fare • Approx 3-5 min frequency"));
        }
        if (cardRoute13C != null) {
            cardRoute13C.setOnClickListener(v -> showRouteDetail("13C", "Talamban to Colon Street",
                    "USC Talamban -> Banilad -> Ayala Center -> Ramos -> Colon Street",
                    "₱13.00 Base Fare • Approx 2-4 min frequency"));
        }
        if (cardRoute17B != null) {
            cardRoute17B.setOnClickListener(v -> showRouteDetail("17B", "Cebu IT Park to Carbon",
                    "Salinas Drive -> Gorordo Ave -> General Maxilom -> Colon St",
                    "₱13.00 Base Fare • Approx 5-8 min frequency"));
        }
    }

    private void showRouteDetail(String code, String name, String path, String fareInfo) {
        new AlertDialog.Builder(this)
                .setTitle("🚌 Route " + code + " - " + name)
                .setMessage("Path & Landmarks:\n" + path + "\n\nFare & Schedule:\n" + fareInfo)
                .setPositiveButton("OK", null)
                .show();
    }

    private void filterRoutes(String query) {
        if (query.isEmpty()) {
            if (cardRoute04L != null) cardRoute04L.setVisibility(View.VISIBLE);
            if (cardRoute13C != null) cardRoute13C.setVisibility(View.VISIBLE);
            if (cardRoute17B != null) cardRoute17B.setVisibility(View.VISIBLE);
            return;
        }

        String lower = query.toLowerCase();

        if (cardRoute04L != null) {
            cardRoute04L.setVisibility(lower.contains("04") || lower.contains("lahug") || lower.contains("carbon") ? View.VISIBLE : View.GONE);
        }

        if (cardRoute13C != null) {
            cardRoute13C.setVisibility(lower.contains("13") || lower.contains("talamban") || lower.contains("colon") ? View.VISIBLE : View.GONE);
        }

        if (cardRoute17B != null) {
            cardRoute17B.setVisibility(lower.contains("17") || lower.contains("park") || lower.contains("it") ? View.VISIBLE : View.GONE);
        }
    }

    private void setupFareCalculator() {
        if (btnDist4km != null) {
            btnDist4km.setOnClickListener(v -> updateFareDisplay("₱13.00", "4 km Base Fare • Student/PWD: ₱10.50"));
        }
        if (btnDist8km != null) {
            btnDist8km.setOnClickListener(v -> updateFareDisplay("₱19.00", "8 km Distance • Student/PWD: ₱15.20"));
        }
        if (btnDist12km != null) {
            btnDist12km.setOnClickListener(v -> updateFareDisplay("₱25.00", "12 km Distance • Student/PWD: ₱20.00"));
        }
    }

    private void updateFareDisplay(String amount, String detail) {
        if (lblFareAmount != null) lblFareAmount.setText(amount);
        if (lblFareDetail != null) lblFareDetail.setText(detail);
        Toast.makeText(this, "Updated fare estimate: " + amount, Toast.LENGTH_SHORT).show();
    }

    private void performLogout() {
        Toast.makeText(HomeActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
