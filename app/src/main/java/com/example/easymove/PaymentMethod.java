package com.example.easymove;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PaymentMethod extends AppCompatActivity {

    private TextView headerTitle, amountText;
    private RadioButton upiAnyApp, upiPhonePe, upiOneCard, cardOption, walletAmazonPay, walletFreecharge, walletJiomoney;
    private Button proceedButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_payment_method); // Ensure this matches your layout XML file

        // Initialize views
        headerTitle = findViewById(R.id.headerTitle);
        amountText = findViewById(R.id.amountText); // This is where the amount will be displayed
        proceedButton = findViewById(R.id.proceedButton);

        // Initialize RadioButtons
        upiAnyApp = findViewById(R.id.upiAnyApp);
        upiPhonePe = findViewById(R.id.upiPhonePe);
        upiOneCard = findViewById(R.id.upiOneCard);
        walletAmazonPay = findViewById(R.id.walletAmazonPay);
        walletFreecharge = findViewById(R.id.walletFreecharge);
        walletJiomoney = findViewById(R.id.walletJiomoney);
        cardOption = findViewById(R.id.cardOption);

        // Get the amount passed from the previous activity (e.g., AddMoneyBottomSheet)
        Intent intent = getIntent();
        int amount = intent.getIntExtra("AMOUNT", 0); // Default to 0 if no amount is passed
        amountText.setText("Amount: " + amount); // Display the amount in the TextView

        // Set the button text dynamically to show the amount
        proceedButton.setText("Proceed to Pay: ₹" + amount);

        // Set button click listener
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePayment();
            }
        });
    }

    private void handlePayment() {
        // Get selected payment method
        String paymentMethod = "";

        // Check for selected UPI method
        if (upiAnyApp.isChecked()) {
            paymentMethod = "UPI - Any UPI App";
        } else if (upiPhonePe.isChecked()) {
            paymentMethod = "UPI - PhonePe";
        } else if (upiOneCard.isChecked()) {
            paymentMethod = "UPI - OneCard";
        }
        // Check for selected card option
        else if (cardOption.isChecked()) {
            paymentMethod = "Credit / Debit Card";
        }
        // Check for selected wallet option
        else if (walletAmazonPay.isChecked()) {
            paymentMethod = "Wallet - Amazon Pay";
        } else if (walletFreecharge.isChecked()) {
            paymentMethod = "Wallet - Freecharge";
        } else if (walletJiomoney.isChecked()) {
            paymentMethod = "Wallet - JioMoney";
        }

        // If no option is selected, show a toast message
        if (!paymentMethod.isEmpty()) {
            // Show Toast with selected payment method
            Toast.makeText(PaymentMethod.this, "Proceeding with " + paymentMethod, Toast.LENGTH_SHORT).show();

            // Here, initiate the payment process (API calls or other methods can be triggered)
            // Example: initiatePayment(paymentMethod);
        } else {
            // If no option selected, prompt user to select one
            Toast.makeText(PaymentMethod.this, "Please select a payment method", Toast.LENGTH_SHORT).show();
        }
    }
}
