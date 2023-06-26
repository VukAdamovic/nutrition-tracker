package com.example.myapplication.presentation.view.fragments.home_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.FragmentMenuBinding;
import com.example.myapplication.presentation.view.activities.MainActivity;
import com.example.myapplication.presentation.view.fragments.adapters.SavedMealAdapter;

public class MenuFragment extends Fragment {

    private FragmentMenuBinding binding;

    RecyclerView recyclerView;

    public MenuFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMenuBinding.inflate(inflater,container,false);
        initListeners();
        return binding.getRoot();
    }

    private void initListeners(){
        recyclerView = binding.menuRecycleView;
        MainActivity mainActivity = (MainActivity) requireActivity();
        MainActivity.mainViewModel.getMealsByUserId(mainActivity.sharedPreferences.getInt("USER_ID", 9999));
        initObservers();
    }

    private void initObservers() {
        MainActivity.mealsByUserId.observe(this, meals -> {
            SavedMealAdapter savedMealAdapter = new SavedMealAdapter(meals, getParentFragment());
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(savedMealAdapter);
        });
    }
}