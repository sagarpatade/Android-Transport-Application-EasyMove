package com.example.easymove;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class PaymentConfirmationActivity extends AppCompatActivity {

    private TextView confirmationText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_payment_confirmation_activity);

        // Initialize the TextView
        confirmationText = findViewById(R.id.confirmationText);

        // Get the payment method from the intent
        String paymentMethod = getIntent().getStringExtra("PAYMENT_METHOD");

        // Display confirmation message
        if (paymentMethod != null) {
            confirmationText.setText("Payment method selected: " + paymentMethod);
        }
    }
}
