package com.example.myapplication.data.repositories.remote.ingredient;

import com.example.myapplication.data.models.api.domain.Category;
import com.example.myapplication.data.models.api.domain.Ingredient;

import java.util.List;

import io.reactivex.Observable;

public interface IngredientRepository {

    Observable<List<Ingredient>> getAllIngredients(String s);

}
