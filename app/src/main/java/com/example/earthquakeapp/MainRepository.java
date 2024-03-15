package com.example.earthquakeapp;

import com.example.earthquakeapp.pojos.Feature;
import com.example.earthquakeapp.responses.EarthquakeJsonResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {
    public interface DownloadEqsListener{
        void onEqsDownloaded(List<Earthquake> eqList);
    }

    private List<Earthquake> getEarthquakesWithMoshi(EarthquakeJsonResponse body) {
        ArrayList<Earthquake> eqList = new ArrayList<>();
        List<Feature> features = body.getFeatures();

        for (Feature feature: features) {
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

    public void getEarthquakes(DownloadEqsListener downloadEqsListener){
        ApiClient.Service service = ApiClient.getInstance().getService();
        service.getEarthquakes().enqueue(new Callback<EarthquakeJsonResponse>() {
            @Override
            public void onResponse(Call<EarthquakeJsonResponse> call,
                                   Response<EarthquakeJsonResponse> response) {
                List<Earthquake> earthquakeList = getEarthquakesWithMoshi(response.body());
                downloadEqsListener.onEqsDownloaded(earthquakeList);
            }
            @Override
            public void onFailure(Call<EarthquakeJsonResponse> call, Throwable t) { }
        });
    }
}
