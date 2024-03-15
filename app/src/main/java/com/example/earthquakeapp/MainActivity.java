package com.example.earthquakeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.example.earthquakeapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EqAdapter adapter = new EqAdapter();
        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        binding.eqRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.eqRecycler.setAdapter(adapter);

        viewModel.getEartuquakeList().observe(this, eql->{
            for(Earthquake eq:eql) {
                Log.d("eq", "TERREMOTO: " + eq.getMagnitude() + " " + eq.getPlace());
            }
            adapter.submitList(eql);
        });

        viewModel.getEarthquakes();
    }
}