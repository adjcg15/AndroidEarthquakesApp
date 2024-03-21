package com.example.earthquakeapp.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.example.earthquakeapp.Earthquake;
import com.example.earthquakeapp.database.EqDatabase;
import com.example.earthquakeapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EqAdapter adapter = new EqAdapter();
        MainViewModel viewModel = new ViewModelProvider(this,
                new MainViewModelFactory(getApplication())).get(MainViewModel.class);

        binding.eqRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.eqRecycler.setAdapter(adapter);

        viewModel.getEarthquakeList().observe(this, eql->{
            adapter.submitList(eql);
        });

        viewModel.downloadEarthquakes();
    }
}