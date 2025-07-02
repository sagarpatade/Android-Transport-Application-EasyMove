package com.example.easymove;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddMoneyBottomSheet extends BottomSheetDialogFragment {

    private EditText amountInput;

    public AddMoneyBottomSheet() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for the bottom sheet
        View view = inflater.inflate(R.layout.bottom_sheet_add_money, container, false);

        // Initialize amount input and buttons
        amountInput = view.findViewById(R.id.amountInput);
        Button button500 = view.findViewById(R.id.button500);
        Button button1000 = view.findViewById(R.id.button1000);
        Button button2000 = view.findViewById(R.id.button2000);
        Button proceedButton = view.findViewById(R.id.proceedButton);

        // Set onClick listeners for amount buttons
        button500.setOnClickListener(v -> onAmountSelected(500));
        button1000.setOnClickListener(v -> onAmountSelected(1000));
        button2000.setOnClickListener(v -> onAmountSelected(2000));

        // Set onClick listener for proceed button
        proceedButton.setOnClickListener(v -> onProceedClicked());

        return view;
    }

    // This method sets the selected amount to the EditText
    private void onAmountSelected(int amount) {
        amountInput.setText(String.valueOf(amount));
    }

    // This method handles the proceed button click
    private void onProceedClicked() {
        String amountText = amountInput.getText().toString();

        if (!amountText.isEmpty()) {
            try {
                // Parse the entered amount
                int amount = Integer.parseInt(amountText);

                // Create an intent to navigate to PaymentMethodActivity
                Intent intent = new Intent(getActivity(), PaymentMethod.class);
                intent.putExtra("AMOUNT", amount);  // Pass the amount to the next activity
                startActivity(intent);

                // Dismiss the bottom sheet
                dismiss();
            } catch (NumberFormatException e) {
                // Show an error message if the amount is invalid
                Toast.makeText(getContext(), "Please enter a valid amount", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Show a toast message if no amount is entered
            Toast.makeText(getContext(), "Please enter an amount", Toast.LENGTH_SHORT).show();
        }
    }
}
