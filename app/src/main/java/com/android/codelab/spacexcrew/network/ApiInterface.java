package com.android.codelab.spacexcrew.network;

import com.android.codelab.spacexcrew.model.SpaceX;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("/v4/crew")
    Call<List<SpaceX>> getCrew();

}
