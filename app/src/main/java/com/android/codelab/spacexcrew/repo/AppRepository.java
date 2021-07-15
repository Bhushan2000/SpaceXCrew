package com.android.codelab.spacexcrew.repo;

import androidx.lifecycle.MutableLiveData;

import com.android.codelab.spacexcrew.model.SpaceX;
import com.android.codelab.spacexcrew.network.ApiClient;
import com.android.codelab.spacexcrew.network.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppRepository {

    private static final AppRepository repository = new AppRepository();

    private ApiInterface api;

    private MutableLiveData<List<SpaceX>> listMutableLiveData = new MutableLiveData<>();

    public static AppRepository getInstance() {
        return repository;
    }

    private AppRepository() {
        api = ApiClient.getClient().create(ApiInterface.class);

    }

    public MutableLiveData<List<SpaceX>> getCrews() {
        api.getCrew().enqueue(new Callback<List<SpaceX>>() {
            @Override
            public void onResponse(Call<List<SpaceX>> call, Response<List<SpaceX>> response) {

                listMutableLiveData.setValue(response.body());

            }

            @Override
            public void onFailure(Call<List<SpaceX>> call, Throwable t) {
                t.printStackTrace();
              }
        });
        return listMutableLiveData;

    }

}
