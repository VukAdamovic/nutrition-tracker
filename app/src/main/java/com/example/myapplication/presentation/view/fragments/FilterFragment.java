package com.example.myapplication.presentation.view.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.data.models.api.domain.MealFiltered;
import com.example.myapplication.data.models.api.domain.MealSingle;
import com.example.myapplication.databinding.FragmentFilterBinding;
import com.example.myapplication.presentation.view.activities.MainActivity;
import com.example.myapplication.presentation.view.fragments.adapters.CategoryAdapter;
import com.example.myapplication.presentation.view.fragments.adapters.FilterAdapter;
import com.example.myapplication.presentation.view.fragments.adapters.MealAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


public class FilterFragment extends Fragment implements FilterAdapter.OnTagClickListener {

    private FragmentFilterBinding binding;

    private RecyclerView filterRecycleView;

    private RecyclerView tagsRecycleView;

    private RecyclerView mealRecycleView;

    private MealAdapter mealAdapter;

    private Handler handler = new Handler();

    private Runnable searchRunnable;



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
        });

        binding.radioButton3.setOnClickListener(v -> { //ingredient
            // Opcija 3 je selektovana
            binding.radioButton.setChecked(false);
            binding.radioButton2.setChecked(false);
            binding.radioButton3.setChecked(true);
            MainActivity.mainViewModel.getIngredients("list");
        });

        initObservers();
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

    }

    @Override
    public void onTagClick(String tag) {
//        List<MealFiltered> currentList = mealAdapter.getMealFilteredList();
//        List<MealFiltered> filterByTag = new ArrayList<>();
//
//        for(MealFiltered mealFiltered : currentList){
//            MainActivity.mainViewModel.getMealById(mealFiltered.getId());
//
//            MainActivity.singleMealByIdLiveData.observe(this, mealSingles -> {
//                MealSingle mealSingle = mealSingles.get(0);
//                for(String curr : mealSingle.getTags()){
//                    if(curr.equals(tag) && !filterByTag.contains(mealFiltered)){
//                        filterByTag.add(mealFiltered);
//                    }
//                }
//                MealAdapter filteredAdapter = new MealAdapter(filterByTag, requireActivity().getSupportFragmentManager());
//                mealRecycleView.setAdapter(filteredAdapter);
//            });
//        }
    }
}