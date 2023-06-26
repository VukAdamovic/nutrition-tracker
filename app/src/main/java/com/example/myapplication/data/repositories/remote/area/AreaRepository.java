package com.example.myapplication.data.repositories.remote.area;

import java.util.List;

import io.reactivex.Observable;

public interface AreaRepository {

    Observable<List<String>> fetchAreas();
}
