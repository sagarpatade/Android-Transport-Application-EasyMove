package com.example.easymove;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PickupDropoffActivity extends AppCompatActivity {

    TextInputEditText inputName, inputMobile, inputAddress, inputMainlandPickup, inputDropLocation;
    Button submitButton;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickup_dropoff);

        // Initialize Firebase if not done already
        FirebaseApp.initializeApp(this);

        // Initialize UI components
        inputName = findViewById(R.id.input_full_name);
        inputMobile = findViewById(R.id.input_mobile);
        inputAddress = findViewById(R.id.input_address);
        inputMainlandPickup = findViewById(R.id.input_pickup_location);
        inputDropLocation = findViewById(R.id.input_drop_location);
        submitButton = findViewById(R.id.submit_button);

        // Initialize Firebase reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Bookings");

        // Handle the Submit Button logic
        submitButton.setOnClickListener(v -> {
            String name = inputName.getText().toString().trim();
            String mobile = inputMobile.getText().toString().trim();
            String address = inputAddress.getText().toString().trim();
            String pickupLocation = inputMainlandPickup.getText().toString().trim();
            String dropLocation = inputDropLocation.getText().toString().trim();

            if (name.isEmpty() || mobile.isEmpty() || address.isEmpty() ||
                    pickupLocation.isEmpty() || dropLocation.isEmpty()) {
                Toast.makeText(PickupDropoffActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            } else {
                String bookingId = databaseReference.push().getKey();
                Booking booking = new Booking(name, mobile, address, pickupLocation, dropLocation);
                if (bookingId != null) {
                    databaseReference.child(bookingId).setValue(booking)
                            .addOnSuccessListener(aVoid -> Toast.makeText(PickupDropoffActivity.this,
                                    "Booking saved successfully", Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e -> {
                                Toast.makeText(PickupDropoffActivity.this,
                                        "Failed to save booking", Toast.LENGTH_SHORT).show();
                                Log.e("FirebaseError", "Error: " + e.getMessage());
                            });
                }
            }
        });
    }

    // Booking class for Firebase
    public static class Booking {
        public String name, mobile, address, pickupLocation, dropLocation;

        public Booking() {
        }

        public Booking(String name, String mobile, String address, String pickupLocation, String dropLocation) {
            this.name = name;
            this.mobile = mobile;
            this.address = address;
            this.pickupLocation = pickupLocation;
            this.dropLocation = dropLocation;
        }
    }
}
