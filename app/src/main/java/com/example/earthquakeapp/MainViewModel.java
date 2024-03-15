package com.example.earthquakeapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.earthquakeapp.pojos.Feature;
import com.example.earthquakeapp.responses.EarthquakeJsonResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    private MutableLiveData<List<Earthquake>> earthquakeList = new MutableLiveData<>();
    public LiveData<List<Earthquake>> getEartuquakeList() {
        return this.earthquakeList;
    }

    private MainRepository repository = new MainRepository();
    public void getEarthquakes(){
        repository.getEarthquakes(eql -> {
            earthquakeList.setValue(eql);
        });
    }
    /*
    public void getEarthquakes() {
        ApiClient.Service service = ApiClient.getInstance().getService();
        service.getEarthquakes().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                List<Earthquake> eql = parseEartquake(response.body());
                earthquakeList.setValue(eql);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private List<Earthquake> parseEartquake(String body) {
        ArrayList<Earthquake> eql = new ArrayList<>();

        try {
            JSONObject jsonResponse = new JSONObject(body);
            JSONArray features = jsonResponse.getJSONArray("features");

            for(int i = 0; i < features.length(); i++) {
                JSONObject jsonFeature = features.getJSONObject(i);
                String id = jsonFeature.getString("id");

                JSONObject jsonProperties = jsonFeature.getJSONObject("properties");
                double magnitude = jsonProperties.getDouble("mag");
                String place = jsonProperties.getString("place");
                Long time = jsonProperties.getLong("time");

                JSONObject jsonGeometry = jsonFeature.getJSONObject("geometry");
                JSONArray coord = jsonGeometry.getJSONArray("coordinates");
                double longitude = coord.getDouble(0);
                double latitude = coord.getDouble(1);

                eql.add(new Earthquake(id, place, magnitude, time, longitude, latitude));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return eql;
    }
     */
}
