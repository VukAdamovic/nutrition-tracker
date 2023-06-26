package com.example.myapplication.presentation.view.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.data.models.api.domain.MealFiltered;
import com.example.myapplication.data.models.api.domain.MealSingle;
import com.example.myapplication.databinding.FragmentFilterBinding;
import com.example.myapplication.presentation.view.activities.MainActivity;
import com.example.myapplication.presentation.view.fragments.adapters.FilterAdapter;
import com.example.myapplication.presentation.view.fragments.adapters.MealAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;


public class FilterFragment extends Fragment implements FilterAdapter.OnTagClickListener {

    private FragmentFilterBinding binding;

    private RecyclerView filterRecycleView;

    private RecyclerView tagsRecycleView;

    private RecyclerView mealRecycleView;

    private MealAdapter mealAdapter;

    private Handler handler = new Handler();

    private Handler sliderHandler = new Handler();

    private Runnable searchRunnable;

    private Runnable sliderRunnable;

    private String currentTag;

    private CopyOnWriteArrayList<MealFiltered> filterByTag;

    private CopyOnWriteArrayList<MealFiltered> filteredListByCalories;

    private float left;

    private float right;



    public FilterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFilterBinding.inflate(inflater, container, false);
        initListeners();
        return binding.getRoot();
    }

    private void initListeners() {
        filterRecycleView = binding.filterRecycleView;
        tagsRecycleView = binding.recyclerViewTags;
        mealRecycleView = binding.recyclerViewMeals;
        SwitchCompat toggleSearch = binding.button8;

        AtomicBoolean isToggleChange = new AtomicBoolean(false);

        toggleSearch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isToggleChange.set(true);
            binding.editTextText4.setText("");
        });

        initObservers();

        // ubacio sam da bi bilo popunjeno na pocetku neki mealovi deluje mi prazno
        MainActivity.mainViewModel.getMealsByName("");

        binding.editTextText4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (isToggleChange.get()) {
                    isToggleChange.set(false);
                    return;
                }

                handler.removeCallbacks(searchRunnable);

                searchRunnable = () -> {
                    String searchQuery = charSequence.toString();
                    if ((toggleSearch.isChecked())) {
                        MainActivity.mainViewModel.getMealsByIngredient(searchQuery);
                    } else {
                        MainActivity.mainViewModel.getMealsByName(searchQuery);
                    }
                };

                handler.postDelayed(searchRunnable, 500);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        binding.editTextText5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                handler.removeCallbacks(searchRunnable); // Assuming 'handler' and 'searchRunnable' are available here

                searchRunnable = () -> mealAdapter.filter(charSequence.toString());

                handler.postDelayed(searchRunnable, 500);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        binding.rangeSlider.addOnChangeListener((slider, value, fromUser) -> {
            List<Float> values = slider.getValues();
            left = values.get(0);
            right = values.get(1);
            MealAdapter currAdapter = (MealAdapter) mealRecycleView.getAdapter();
            if (currAdapter != null) {
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderRunnable = () -> {
                    filteredListByCalories.clear(); // Clear the previous list
                    List<MealFiltered> currentList = currAdapter.getMealFilteredList();
                    for (MealFiltered mealFiltered : currentList) {
                        MainActivity.mainViewModel.getMealById(mealFiltered.getId());
                    }
                };
                sliderHandler.postDelayed(sliderRunnable, 500);
            }
        });



        //inicijalizacija da ne bude prazna
        binding.radioButton.setChecked(true);
        MainActivity.mainViewModel.getCategories();

        binding.radioButton.setOnClickListener(v -> { //category
            // Opcija 1 je selektovana
            binding.radioButton.setChecked(true);
            binding.radioButton2.setChecked(false);
            binding.radioButton3.setChecked(false);
            MainActivity.mainViewModel.getCategories();

        });

        binding.radioButton2.setOnClickListener(v -> { //area
            // Opcija 2 je selektovana
            binding.radioButton.setChecked(false);
            binding.radioButton2.setChecked(true);
            binding.radioButton3.setChecked(false);
            MainActivity.mainViewModel.fetchAreas();
        });

        binding.radioButton3.setOnClickListener(v -> { //ingredient
            // Opcija 3 je selektovana
            binding.radioButton.setChecked(false);
            binding.radioButton2.setChecked(false);
            binding.radioButton3.setChecked(true);
            MainActivity.mainViewModel.getIngredients("list");
        });

        binding.radioButton4.setOnClickListener(v -> {
            binding.radioButton5.setChecked(false);
            binding.radioButton4.setChecked(true);
            if (mealAdapter != null) {
                mealAdapter.sortAscending();
            }
        });

        binding.radioButton5.setOnClickListener(v -> {
            binding.radioButton4.setChecked(false);
            binding.radioButton5.setChecked(true);
            if (mealAdapter != null) {
                mealAdapter.sortDescending();
            }
        });
    }

    private void initObservers() {

        //Observer za gornji recycler view
        MainActivity.allMealsByNameLiveData.observe(this, meals -> {
            List<MealFiltered> mealFilteredList = new ArrayList<>();
            for (MealSingle mealSingle : meals) {
                mealFilteredList.add(new MealFiltered(String.valueOf(mealSingle.getId()), mealSingle.getMealImageUrl(), mealSingle.getMealName()));
            }
            mealAdapter = new MealAdapter(mealFilteredList, requireActivity().getSupportFragmentManager());
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            mealRecycleView.setLayoutManager(layoutManager);
            mealRecycleView.setAdapter(mealAdapter);

            List<String> uniqueTags = new ArrayList<>();

            for(MealSingle  mealSingle : meals) {
                for(String tag : mealSingle.getTags()){
                    if(!uniqueTags.contains(tag) && !tag.equals("") && !tag.equals(" ")){
                        uniqueTags.add(tag);
                    }
                }
                FilterAdapter filterAdapter = new FilterAdapter(null, null, null, uniqueTags, this);
                LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                tagsRecycleView.setLayoutManager(layoutManager2);
                tagsRecycleView.setAdapter(filterAdapter);
            }

        });

        MainActivity.allCategoriesLiveData.observe(this, categories -> {
            FilterAdapter filterAdapter = new FilterAdapter(categories, null, null, null, this);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            filterRecycleView.setLayoutManager(layoutManager);
            filterRecycleView.setAdapter(filterAdapter);
        });

        MainActivity.allIngredientsLiveData.observe(this, ingredients -> {
            FilterAdapter filterAdapter = new FilterAdapter(null, null, ingredients, null, this);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            filterRecycleView.setLayoutManager(layoutManager);
            filterRecycleView.setAdapter(filterAdapter);
        });

        MainActivity.areas.observe(this, areas -> {
            FilterAdapter filterAdapter = new FilterAdapter(null, areas, null, null, this);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            filterRecycleView.setLayoutManager(layoutManager);
            filterRecycleView.setAdapter(filterAdapter);
        });

        //- dodaj ovde za areo ovaj gornji recycler view

        //-------------------------------------------------------------------------//

        //Recycler view za prikaz filtriranih jela
        MainActivity.allFilteredMealsByCategoryLiveData.observe(this, meals -> {
            mealAdapter = new MealAdapter(meals, requireActivity().getSupportFragmentManager());
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            mealRecycleView.setLayoutManager(layoutManager);
            mealRecycleView.setAdapter(mealAdapter);

            List<String> uniqueTags = new ArrayList<>();

            for(MealFiltered mealFiltered : meals) {
                MainActivity.mainViewModel.getMealById(mealFiltered.getId());

                MainActivity.singleMealByIdLiveData.observe(this, mealSingles -> {
                    MealSingle mealSingle = mealSingles.get(0);
                    for(String tag : mealSingle.getTags()){
                        if(!uniqueTags.contains(tag) && !tag.equals("") && !tag.equals(" ")){
                            uniqueTags.add(tag);
                        }
                    }
                    FilterAdapter filterAdapter = new FilterAdapter(null, null, null, uniqueTags, this );
                    LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    tagsRecycleView.setLayoutManager(layoutManager2);
                    tagsRecycleView.setAdapter(filterAdapter);
                });
            }
        });

        MainActivity.allFilteredMealsByIngredientLiveData.observe(this, meals -> {
            mealAdapter = new MealAdapter(meals, requireActivity().getSupportFragmentManager());
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            mealRecycleView.setLayoutManager(layoutManager);
            mealRecycleView.setAdapter(mealAdapter);

            List<String> uniqueTags = new ArrayList<>();

            for(MealFiltered mealFiltered : meals) {
                MainActivity.mainViewModel.getMealById(mealFiltered.getId());

                MainActivity.singleMealByIdLiveData.observe(this, mealSingles -> {
                    MealSingle mealSingle = mealSingles.get(0);
                    for(String tag : mealSingle.getTags()){
                        if(!uniqueTags.contains(tag) && !tag.equals("") && !tag.equals(" ")){
                            uniqueTags.add(tag);
                        }
                    }
                    FilterAdapter filterAdapter = new FilterAdapter(null, null, null, uniqueTags, this);
                    LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    tagsRecycleView.setLayoutManager(layoutManager2);
                    tagsRecycleView.setAdapter(filterAdapter);
                });
            }
        });

        MainActivity.mealsByAreaLiveData.observe(this, meals -> {
            mealAdapter = new MealAdapter(meals, requireActivity().getSupportFragmentManager());
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            mealRecycleView.setLayoutManager(layoutManager);
            mealRecycleView.setAdapter(mealAdapter);

            List<String> uniqueTags = new ArrayList<>();

            for(MealFiltered mealFiltered : meals) {
                MainActivity.mainViewModel.getMealById(mealFiltered.getId());

                MainActivity.singleMealByIdLiveData.observe(this, mealSingles -> {
                    MealSingle mealSingle = mealSingles.get(0);
                    for(String tag : mealSingle.getTags()){
                        if(!uniqueTags.contains(tag) && !tag.equals("") && !tag.equals(" ")){
                            uniqueTags.add(tag);
                        }
                    }
                    FilterAdapter filterAdapter = new FilterAdapter(null, null, null, uniqueTags, this);
                    LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    tagsRecycleView.setLayoutManager(layoutManager2);
                    tagsRecycleView.setAdapter(filterAdapter);
                });
            }
        });

        MainActivity.singleMealByIdLiveData.observe(getViewLifecycleOwner(), meals -> {
            MealSingle meal = meals.get(0);
            if (currentTag != null) {
                for (String curr : meal.getTags()) {
                    if (curr.equals(currentTag)) {
                        filterByTag.add(new MealFiltered(String.valueOf(meal.getId()), meal.getMealImageUrl(), meal.getMealName()));
                        MealAdapter filteredAdapter = new MealAdapter(filterByTag, requireActivity().getSupportFragmentManager());
                        mealRecycleView.setAdapter(filteredAdapter);
                        break;
                    }
                }
            }
//            MainActivity.mainViewModel.getCaloriesForMeal(meal);
        });

        MainActivity.currentMealWithCaloriesLiveData.observe(this, meal -> {
            if (meal.getCalories() >= left && meal.getCalories() <= right) {
                filteredListByCalories.add(new MealFiltered(String.valueOf(meal.getId()), meal.getMealImageUrl(), meal.getMealName() + " " + meal.getCalories()));
                MealAdapter filteredAdapter = new MealAdapter(filteredListByCalories, requireActivity().getSupportFragmentManager());
                mealRecycleView.setAdapter(filteredAdapter);
            }
        });
    }

    @Override
    public void onTagClick(String tag) {
        if (tag.equals(currentTag)) {
            currentTag = null;
            mealRecycleView.setAdapter(mealAdapter);
        } else {
            currentTag = tag;
            filterByTag = new CopyOnWriteArrayList<>();
            filteredListByCalories = new CopyOnWriteArrayList<>();
            List<MealFiltered> currentList = mealAdapter.getMealFilteredList();

            for(MealFiltered mealFiltered : currentList){
                MainActivity.mainViewModel.getMealById(mealFiltered.getId());
            }
        }
    }

}