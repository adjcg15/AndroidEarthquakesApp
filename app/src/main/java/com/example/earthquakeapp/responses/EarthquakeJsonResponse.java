package com.example.earthquakeapp.responses;

import com.example.earthquakeapp.pojos.Feature;

import java.util.List;

public class EarthquakeJsonResponse {
    private List<Feature> features;
    public List<Feature> getFeatures() {
        return features;
    }
}
