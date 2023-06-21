package com.example.myapplication.data.repositories.remote.meal;

import com.example.myapplication.data.datasources.remote.MealService;
import com.example.myapplication.data.models.api.domain.MealByCategory;
import com.example.myapplication.data.models.api.domain.MealSingle;
import com.example.myapplication.data.models.api.meal.SingleMealResponse;
import com.example.myapplication.data.models.api.meal_by_category.AllMealsByCategoryResponse;
import com.example.myapplication.data.models.api.meal_by_category.MealByCategoryResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class MealRepositoryRemoteImpl implements MealRepositoryRemote {

    private MealService mealService;

    @Inject
    public MealRepositoryRemoteImpl(MealService mealService) {
        this.mealService = mealService;
    }

    @Override
    public Observable<List<MealByCategory>> getAllMealsByCategory(String category) {
        return mealService
                .getAllMealsByCategory(category)
                .map(new Function<AllMealsByCategoryResponse, List<MealByCategory>>() {
                    @Override
                    public List<MealByCategory> apply(AllMealsByCategoryResponse allMealsByCategoryResponse) throws Exception {
                        List<MealByCategory> meals = new ArrayList<>();
                        if(allMealsByCategoryResponse != null && allMealsByCategoryResponse.getAllMeals() != null){
                            for (MealByCategoryResponse mealByCategoryResponse : allMealsByCategoryResponse.getAllMeals()) {
                                meals.add(new MealByCategory(
                                        mealByCategoryResponse.getId(),
                                        mealByCategoryResponse.getThumbnail(),
                                        mealByCategoryResponse.getName()
                                ));
                            }
                        }
                        return meals;
                    }
                });
    }

    @Override
    public Observable<List<MealSingle>> getMealsByName(String mealName) {
        return mealService
                .getMealByName(mealName)
                .map(allMealsResponse -> {
                    List<MealSingle> meals = new ArrayList<>();
                    if (allMealsResponse != null && allMealsResponse.getAllMeals() != null) {
                        for (SingleMealResponse singleMealResponse : allMealsResponse.getAllMeals()) {
                            meals.add(new MealSingle(
                                    singleMealResponse.getIdMeal(),
                                    singleMealResponse.getStrMeal(),
                                    singleMealResponse.getStrMealThumb(),
                                    singleMealResponse.getStrInstructions(),
                                    singleMealResponse.getStrYoutube(),
                                    createIngredientsMeasurementsList(singleMealResponse),
                                    singleMealResponse.getStrCategory(),
                                    singleMealResponse.getStrArea(),
                                    Arrays.asList(singleMealResponse.getStrTags().split(",")),
                                    0
                            ));
                        }
                    }
                    return meals;
                });
    }




    //Privatne metode

    private List<String> createIngredientsMeasurementsList(SingleMealResponse singleMealResponse) {
        List<String> ingredientsMeasurements = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            String ingredient = getIngredientValue(singleMealResponse, i);
            String measurement = getMeasurementValue(singleMealResponse, i);

            if (ingredient != null && measurement != null && !ingredient.equals("") && !measurement.equals("")) {
                String ingredientMeasurement = measurement + " " + ingredient;
                ingredientsMeasurements.add(ingredientMeasurement);
            }
        }
        return ingredientsMeasurements;
    }

    private String getIngredientValue(SingleMealResponse singleMealResponse, int index) {
        switch (index) {
            case 1:
                return singleMealResponse.getStrIngredient1();
            case 2:
                return singleMealResponse.getStrIngredient2();
            case 3:
                return singleMealResponse.getStrIngredient3();
            case 4:
                return singleMealResponse.getStrIngredient4();
            case 5:
                return singleMealResponse.getStrIngredient5();
            case 6:
                return singleMealResponse.getStrIngredient6();
            case 7:
                return singleMealResponse.getStrIngredient7();
            case 8:
                return singleMealResponse.getStrIngredient8();
            case 9:
                return singleMealResponse.getStrIngredient9();
            case 10:
                return singleMealResponse.getStrIngredient10();
            case 11:
                return singleMealResponse.getStrIngredient11();
            case 12:
                return singleMealResponse.getStrIngredient12();
            case 13:
                return singleMealResponse.getStrIngredient13();
            case 14:
                return singleMealResponse.getStrIngredient14();
            case 15:
                return singleMealResponse.getStrIngredient15();
            case 16:
                return singleMealResponse.getStrIngredient16();
            case 17:
                return singleMealResponse.getStrIngredient17();
            case 18:
                return singleMealResponse.getStrIngredient18();
            case 19:
                return singleMealResponse.getStrIngredient19();
            case 20:
                return singleMealResponse.getStrIngredient20();
            default:
                return null;
        }
    }

    private String getMeasurementValue(SingleMealResponse singleMealResponse, int index) {
        switch (index) {
            case 1:
                return singleMealResponse.getStrMeasure1();
            case 2:
                return singleMealResponse.getStrMeasure2();
            case 3:
                return singleMealResponse.getStrMeasure3();
            case 4:
                return singleMealResponse.getStrMeasure4();
            case 5:
                return singleMealResponse.getStrMeasure5();
            case 6:
                return singleMealResponse.getStrMeasure6();
            case 7:
                return singleMealResponse.getStrMeasure7();
            case 8:
                return singleMealResponse.getStrMeasure8();
            case 9:
                return singleMealResponse.getStrMeasure9();
            case 10:
                return singleMealResponse.getStrMeasure10();
            case 11:
                return singleMealResponse.getStrMeasure11();
            case 12:
                return singleMealResponse.getStrMeasure12();
            case 13:
                return singleMealResponse.getStrMeasure13();
            case 14:
                return singleMealResponse.getStrMeasure14();
            case 15:
                return singleMealResponse.getStrMeasure15();
            case 16:
                return singleMealResponse.getStrMeasure16();
            case 17:
                return singleMealResponse.getStrMeasure17();
            case 18:
                return singleMealResponse.getStrMeasure18();
            case 19:
                return singleMealResponse.getStrMeasure19();
            case 20:
                return singleMealResponse.getStrMeasure20();
            default:
                return null;
        }
    }




}
