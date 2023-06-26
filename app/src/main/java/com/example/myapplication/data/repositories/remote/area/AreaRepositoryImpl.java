package com.example.myapplication.data.repositories.remote.area;

import com.example.myapplication.data.datasources.remote.AreaService;
import com.example.myapplication.data.models.api.area.AllAreasResponse;
import com.example.myapplication.data.models.api.area.AreaResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class AreaRepositoryImpl implements AreaRepository{

    private AreaService areaService;

    @Inject
    public AreaRepositoryImpl(AreaService areaService) {
        this.areaService = areaService;
    }

    @Override
    public Observable<List<String>> fetchAreas() {
        return areaService
                .getAllAreas()
                .map(new Function<AllAreasResponse, List<String>>() {
                    @Override
                    public List<String> apply(AllAreasResponse allAreasResponse) throws Exception {
                        List<String> areas = new ArrayList<>();
                        for (AreaResponse areaResponse : allAreasResponse.getAllAreas()) {
                            areas.add(areaResponse.getArea());
                        }
                        return areas;
                    }
                });
    }
}
