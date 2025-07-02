package com.example.easymove;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ChooseServiceActivity extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_choose_service, container, false);

        // Handle button clicks
        LinearLayout localServiceCard = view.findViewById(R.id.local_service_card);
        LinearLayout outstationServiceCard = view.findViewById(R.id.outstation_service_card);

        localServiceCard.setOnClickListener(v -> openPickupDropoffScreen("Local"));
        outstationServiceCard.setOnClickListener(v -> openPickupDropoffScreen("Outstation"));

        return view;
    }

    private void openPickupDropoffScreen(String serviceType) {
        if (getActivity() != null) {
            Intent intent = new Intent(getActivity(), PickupDropoffActivity.class);
            intent.putExtra("SERVICE_TYPE", serviceType);
            startActivity(intent);
            dismiss(); // Dismiss the bottom sheet after navigation
        }
    }
}
