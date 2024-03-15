package com.example.earthquakeapp.pojos;

public class Geometry {
    private double[] coordinates;
    public double getLongitude() {
        return coordinates[0];
    }
    public double getLatitude() {
        return coordinates[1];
    }
}
