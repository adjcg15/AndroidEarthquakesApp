package com.example.earthquakeapp.main;

import androidx.lifecycle.LiveData;

import com.example.earthquakeapp.Earthquake;
import com.example.earthquakeapp.api.ApiClient;
import com.example.earthquakeapp.api.Feature;
import com.example.earthquakeapp.api.EarthquakeJsonResponse;
import com.example.earthquakeapp.database.EqDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {
    private final EqDatabase database;

    public MainRepository(EqDatabase database) {
        this.database = database;
    }

    public LiveData<List<Earthquake>> getEqList() {
        return database.eqDAO().getEarthquakes();
    }

    public void downloadAndSaveEarthquakes() {
        ApiClient.Service service = ApiClient.getInstance().getService();

        service.getEarthquakes().enqueue(new Callback<EarthquakeJsonResponse>() {
            @Override
            public void onResponse(Call<EarthquakeJsonResponse> call,
                                   Response<EarthquakeJsonResponse> response) {
                List<Earthquake> earthquakeList = getEarthquakesWithMoshi(response.body());

                EqDatabase.databaseWriteExecutor.execute(() -> {
                    database.eqDAO().insertAll(earthquakeList);
                });
            }

            @Override
            public void onFailure(Call<EarthquakeJsonResponse> call, Throwable t) {
            }
        });
    }

    private List<Earthquake> getEarthquakesWithMoshi(EarthquakeJsonResponse body) {
        ArrayList<Earthquake> eqList = new ArrayList<>();
        List<Feature> features = body.getFeatures();

        for (Feature feature : features) {
            String id = feature.getId();
            double magnitude = feature.getProperties().getMagnitude();
            String place = feature.getProperties().getPlace();
            long time = feature.getProperties().getTime();
            double longitude = feature.getGeometry().getLongitude();
            double latitude = feature.getGeometry().getLatitude();
            Earthquake earthquake = new Earthquake(id, place, magnitude, time,
                    latitude, longitude);
            eqList.add(earthquake);
        }
        return eqList;
    }
}
