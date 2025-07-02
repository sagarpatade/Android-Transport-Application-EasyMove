package com.example.easymove;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;

import java.util.ArrayList;
import java.util.List;

public class LocationInputActivity extends Fragment {

    private EditText searchLocation;
    private ListView locationSuggestions;
    private PlacesClient placesClient;
    private ArrayAdapter<String> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_input_activity, container, false);

        // Initialize Places SDK
        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), "AIzaSyAH_jqhXs-jB13JfOEWv-pPPNGwaOrEgPo");
        }
        placesClient = Places.createClient(requireContext());

        // Initialize Views
        searchLocation = view.findViewById(R.id.search_location);
        locationSuggestions = view.findViewById(R.id.location_suggestions);

        // Set up the adapter for the ListView
        List<String> suggestionList = new ArrayList<>();
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, suggestionList);
        locationSuggestions.setAdapter(adapter);

        // Handle EditText input for fetching location suggestions
        searchLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 3) { // Start searching when input is 3 or more characters
                    fetchPlaceSuggestions(s.toString());
                } else {
                    suggestionList.clear();
                    adapter.notifyDataSetChanged();
                    locationSuggestions.setVisibility(View.GONE); // Hide suggestions
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do nothing
            }
        });

        // Handle suggestion click
        locationSuggestions.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedPlace = suggestionList.get(position);
            searchLocation.setText(selectedPlace); // Set the selected place in EditText
            locationSuggestions.setVisibility(View.GONE); // Hide suggestions after selection
        });

        return view;
    }

    // Fetch place suggestions using Places API
    private void fetchPlaceSuggestions(String query) {
        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                .setQuery(query)
                .build();

        placesClient.findAutocompletePredictions(request).addOnSuccessListener((response) -> {
            List<String> newSuggestions = new ArrayList<>();
            for (AutocompletePrediction prediction : response.getAutocompletePredictions()) {
                newSuggestions.add(prediction.getFullText(null).toString());
            }
            // Update the adapter with the new suggestions
            adapter.clear();
            adapter.addAll(newSuggestions);
            adapter.notifyDataSetChanged();
            locationSuggestions.setVisibility(View.VISIBLE); // Show suggestions
        }).addOnFailureListener((exception) -> {
            Toast.makeText(getContext(), "Error fetching suggestions: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }
}
