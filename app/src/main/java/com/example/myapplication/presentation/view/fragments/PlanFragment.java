package com.example.myapplication.presentation.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.data.models.api.domain.MealFiltered;
import com.example.myapplication.data.models.api.domain.PlanMeal;
import com.example.myapplication.data.models.entities.MealEntity;
import com.example.myapplication.databinding.FragmentPlanBinding;
import com.example.myapplication.presentation.view.activities.MainActivity;
import com.example.myapplication.presentation.view.fragments.adapters.FilterAdapter;
import com.example.myapplication.presentation.view.fragments.adapters.PlanAdapter;
import com.example.myapplication.presentation.view.fragments.adapters.PlanMakerAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class PlanFragment extends Fragment {

    private FragmentPlanBinding binding;

    private RecyclerView recyclerViewWeeklyPlan;

    private PlanMakerAdapter planMakerAdapter;

    private RecyclerView recyclerViewCategories;

    private FilterAdapter filterAdapter;

    private RecyclerView recyclerViewMealsByCategory;

    private PlanAdapter planAdapter;

    private String day;

    private String type;

    private boolean openedFragment;



    public PlanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPlanBinding.inflate(inflater, container, false);
        openedFragment = true;
        initListeners();
        return binding.getRoot();
    }

    private void initListeners(){
        recyclerViewWeeklyPlan = binding.recyclerView;
        recyclerViewCategories = binding.recyclerView2;
        recyclerViewMealsByCategory = binding.recyclerViewApiOrMenu;

        initObservers();

        SwitchCompat toggleSearch = binding.toggleButton2;

        MainActivity.mainViewModel.getCategories();
        MainActivity.mainViewModel.getMealsByCategory("Chicken");

        toggleSearch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                MainActivity.mainViewModel.getCategories();
                MainActivity.mainViewModel.getMealsByCategory("Chicken");
            } else {
                MainActivity mainActivity = (MainActivity) requireActivity();
                MainActivity.mainViewModel.getMealsByUserId(mainActivity.sharedPreferences.getInt("USER_ID", -1));
            }
        });

        binding.button11.setOnClickListener(v -> {
            if(planMakerAdapter == null) {
                Log.d("PlanFragment", "No PlanMeal data available");
                return;
            }

            List<PlanMeal> planMeals = planMakerAdapter.getPlanMeals();
            HashSet<String> daysInPlan = new HashSet<>();

            for (PlanMeal planMeal : planMeals) {
                daysInPlan.add(planMeal.getMealDay());
            }

            if(daysInPlan.size() < 7) {
                // Not all days of the week have entries
                Log.d("PlanFragment", "Not all days of the week have entries");
            } else {
                // All days of the week have entries
                Log.d("PlanFragment", "All days of the week have entries");
            }
        });

        toggleSearch.setChecked(true);
    }

    private void initObservers(){
        MainActivity.allCategoriesLiveData.observe(this, categories -> {
            filterAdapter = new FilterAdapter(categories, null, null, null, null);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            recyclerViewCategories.setLayoutManager(layoutManager);
            recyclerViewCategories.setAdapter(filterAdapter);
        });

        MainActivity.allFilteredMealsByCategoryLiveData.observe(this, meals -> {
            planAdapter = new PlanAdapter(meals, requireActivity().getSupportFragmentManager());
            planAdapter.setOnPlanValuesPassListener((day, type, mealFiltered) -> {
                Log.d("PlanFragment", "Day and Type: " + day + " " + type + " " + mealFiltered.getId());
                this.type = type;
                this.day = day;
                MainActivity.mainViewModel.getMealById(mealFiltered.getId());
            });
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerViewMealsByCategory.setLayoutManager(layoutManager);
            recyclerViewMealsByCategory.setAdapter(planAdapter);
        });

        MainActivity.allMealsByUserId.observe(this, mealEntities -> {
            List<MealFiltered> mealFilteredList = new ArrayList<>();
            for(MealEntity meal : mealEntities){
                mealFilteredList.add(new MealFiltered(String.valueOf(meal.getId()), meal.getMealImageUrl(), meal.getMealName()));
            }
            planAdapter = new PlanAdapter(mealFilteredList, requireActivity().getSupportFragmentManager());
            planAdapter.setOnPlanValuesPassListener((day, type, mealFiltered) -> {
                Log.d("PlanFragment", "Day and Type: " + day + " " + type + " " + mealFiltered.getId());
                this.type = type;
                this.day = day;
                MainActivity.mainViewModel.getMealById(mealFiltered.getId());
            });
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerViewMealsByCategory.setLayoutManager(layoutManager);
            recyclerViewMealsByCategory.setAdapter(planAdapter);
        });

        MainActivity.currentMealWithCaloriesLiveData.observe(this, mealSingle -> {
            if (day == null || type == null) return;
            if (recyclerViewWeeklyPlan.getAdapter() != null) {
                planMakerAdapter = (PlanMakerAdapter) recyclerViewWeeklyPlan.getAdapter();
                List<PlanMeal> newPlanMeals = new ArrayList<>(planMakerAdapter.getPlanMeals());
                newPlanMeals.add(new PlanMeal(mealSingle.getId(), mealSingle.getMealName(), mealSingle.getMealImageUrl(), type, day, mealSingle.getCalories()));
                planMakerAdapter.setPlanMeals(newPlanMeals);
            } else {
                List<PlanMeal> planMeals = new ArrayList<>();
                planMeals.add(new PlanMeal(mealSingle.getId(), mealSingle.getMealName(), mealSingle.getMealImageUrl(), type, day, mealSingle.getCalories()));
                planMakerAdapter = new PlanMakerAdapter(planMeals, this);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                recyclerViewWeeklyPlan.setLayoutManager(layoutManager);
                recyclerViewWeeklyPlan.setAdapter(planMakerAdapter);
            }
            type = null;
            day = null;
        });
        MainActivity.singleMealByIdLiveData.observe(this, mealSingles -> {
            MainActivity.mainViewModel.getCaloriesForMeal(mealSingles.get(0));
        });
    }
}