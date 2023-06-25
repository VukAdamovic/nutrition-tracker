package com.example.myapplication.presentation.view.fragments.home_fragments;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.data.models.api.domain.Category;
import com.example.myapplication.data.models.api.domain.MealFiltered;
import com.example.myapplication.data.models.api.domain.MealSingle;
import com.example.myapplication.databinding.FragmentSearchBinding;
import com.example.myapplication.presentation.view.activities.MainActivity;
import com.example.myapplication.presentation.view.fragments.adapters.MealAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private Category category;
    private RecyclerView recyclerView;
    private EditText editText;
    private SwitchCompat toggleSearch;
    private Handler handler = new Handler();
    private Runnable searchRunnable;


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
        editText = binding.editTextText2;
        toggleSearch = binding.button3;

        AtomicBoolean isToggleChange = new AtomicBoolean(false);

        toggleSearch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isToggleChange.set(true);
            editText.setText("");
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isToggleChange.get()) {
                    isToggleChange.set(false);
                    return;
                }

                handler.removeCallbacks(searchRunnable);

                searchRunnable = () -> {
                    String searchQuery = s.toString();
                    if ((toggleSearch.isChecked())) {
                        MainActivity.mainViewModel.getMealsByIngredient(searchQuery);
                    } else {
                        MainActivity.mainViewModel.getMealsByName(searchQuery);
                    }
                };

                handler.postDelayed(searchRunnable, 500);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        initObservers();
        if (recyclerView.getAdapter() == null && category != null) MainActivity.mainViewModel.getMealsByCategory(category.getName());
    }

    private void initObservers() {
        MainActivity.allFilteredMealsByCategoryLiveData.observe(this, meals -> {
            MealAdapter mealAdapter = new MealAdapter(meals, requireActivity());
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(mealAdapter);
        });

        MainActivity.allFilteredMealsByIngredientLiveData.observe(this, meals -> {
            MealAdapter mealAdapter = new MealAdapter(meals, requireActivity());
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(mealAdapter);
        });

        MainActivity.allMealsByNameLiveData.observe(this, meals -> {
            List<MealFiltered> mealFilteredList = new ArrayList<>();
            for (MealSingle mealSingle : meals) {
                mealFilteredList.add(new MealFiltered(String.valueOf(mealSingle.getId()), mealSingle.getMealImageUrl(), mealSingle.getMealName()));
            }
            MealAdapter mealAdapter = new MealAdapter(mealFilteredList, requireActivity());
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(mealAdapter);
        });
    }
}