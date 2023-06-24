package com.example.myapplication.presentation.view.fragments.home_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.data.models.api.domain.Category;
import com.example.myapplication.data.models.api.domain.MealFiltered;
import com.example.myapplication.databinding.FragmentSearchBinding;
import com.example.myapplication.presentation.view.activities.MainActivity;
import com.example.myapplication.presentation.view.fragments.adapters.MealAdapter;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private Category category;
    private RecyclerView recyclerView;


    public SearchFragment() {
        // Required empty public constructor
    }

    public SearchFragment(Category category){
        this.category = category;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater,container,false);
        initListeners();
        return binding.getRoot();
    }

    private void initListeners(){
        recyclerView = binding.searchRecycleView;
        initObservers();
        if (category != null) MainActivity.mainViewModel.getMealsByCategory(category.getName());
    }

    private void initObservers() {
        MainActivity.allFilteredMealsByCategoryLiveData.observe(this, meals -> {
            MealAdapter mealAdapter = new MealAdapter(meals, requireActivity());
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(mealAdapter);
        });
    }
}