package com.example.myapplication.data.repositories.remote.ingredient;

import com.example.myapplication.data.datasources.remote.IngredientService;
import com.example.myapplication.data.models.api.domain.Ingredient;
import com.example.myapplication.data.models.api.ingredient.AllIngredientsResponse;
import com.example.myapplication.data.models.api.ingredient.IngredientResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class IngredientRepositoryImpl implements IngredientRepository{

    private IngredientService ingredientService;

    @Inject
    public IngredientRepositoryImpl(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @Override
    public Observable<List<Ingredient>> getAllIngredients(String s) {
        return ingredientService
                .getAllIngredients(s)
                .map(new Function<AllIngredientsResponse, List<Ingredient>>() {
                    @Override
                    public List<Ingredient> apply(AllIngredientsResponse allIngredientsResponse) throws Exception {
                        List<Ingredient> ingredients = new ArrayList<>();
                        if(allIngredientsResponse != null && allIngredientsResponse.getAllIngredients() != null){
                            for (IngredientResponse ingredientResponse : allIngredientsResponse.getAllIngredients()) {
                                ingredients.add(new Ingredient(
                                        ingredientResponse.getIdIngredient(),
                                        ingredientResponse.getStrIngredient(),
                                        (ingredientResponse.getStrDescription() == null) ? "": ingredientResponse.getStrDescription()
                                ));
                            }
                        }
                        return ingredients;
                    }
                });
    }
}
