package com.example.myapplication.presentation.view.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.data.models.api.domain.MealFiltered;
import com.example.myapplication.data.models.entities.MealEntity;
import com.example.myapplication.databinding.FragmentPlanBinding;
import com.example.myapplication.presentation.view.activities.MainActivity;
import com.example.myapplication.presentation.view.fragments.adapters.FilterAdapter;
import com.example.myapplication.presentation.view.fragments.adapters.MealAdapter;
import com.example.myapplication.presentation.view.fragments.adapters.PlanAdapter;
import com.example.myapplication.presentation.view.fragments.adapters.PlanMakerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


public class PlanFragment extends Fragment {

    private FragmentPlanBinding binding;

    private RecyclerView recyclerViewWeeklyPlan;

    private PlanMakerAdapter planMakerAdapter;

    private RecyclerView recyclerViewCategories;

    private FilterAdapter filterAdapter;

    private RecyclerView recyclerViewMealsByCategory;

    private PlanAdapter planAdapter;



    public PlanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPlanBinding.inflate(inflater, container, false);
        initListeners();
        return binding.getRoot();
    }

    private void initListeners(){
        recyclerViewWeeklyPlan = binding.recyclerView;
        recyclerViewCategories = binding.recyclerView2;
        recyclerViewMealsByCategory = binding.recyclerViewApiOrMenu;

        initObservers();

        SwitchCompat toggleSearch = binding.toggleButton2;

        //Popunjvam da ne bude prazno na pocetku
        MainActivity.mainViewModel.getCategories();
        MainActivity.mainViewModel.getMealsByCategory("Chicken");

        toggleSearch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) { // saved
                MainActivity mainActivity = (MainActivity) requireActivity();
                MainActivity.mainViewModel.getMealsByUserId(mainActivity.sharedPreferences.getInt("USER_ID", -1));
            } else { // za api
                MainActivity.mainViewModel.getCategories();
                MainActivity.mainViewModel.getMealsByCategory("Chicken");
            }
        });
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
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerViewMealsByCategory.setLayoutManager(layoutManager);
            recyclerViewMealsByCategory.setAdapter(planAdapter);
        });

        //
    }
}