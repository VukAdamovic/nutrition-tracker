package com.example.myapplication.data.datasources.remote;

import com.example.myapplication.data.models.api.area.AllAreasResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface AreaService {

    @GET("list.php?a=list")
    Observable<AllAreasResponse> getAllAreas();

}
