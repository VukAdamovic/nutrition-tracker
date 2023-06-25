package com.example.myapplication.presentation.view.fragments.home_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.data.models.entities.MealEntity;
import com.example.myapplication.databinding.FragmentMenuBinding;
import com.example.myapplication.presentation.view.fragments.adapters.SavedMealAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MenuFragment extends Fragment {

    private FragmentMenuBinding binding;

    public MenuFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMenuBinding.inflate(inflater,container,false);
        initListeners();
        return binding.getRoot();
    }

    private void initListeners(){
        RecyclerView recyclerView = binding.menuRecycleView;
        SavedMealAdapter savedMealAdapter = new SavedMealAdapter(getSampleList(), getParentFragment());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(savedMealAdapter);
    }

    public static List<MealEntity> getSampleList(){
        List<MealEntity> mealList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            MealEntity meal = new MealEntity("Meal " + (i+1), "image_url_" + (i+1), "Instructions " + (i+1),
                    "YouTube link " + (i+1), Arrays.asList("Ingredient 1", "Ingredient 2", "Ingredient 3"),
                    "Category " + (i+1), new Date(), 500.0, 1);
            mealList.add(meal);
        }
        return  mealList;
    }
}