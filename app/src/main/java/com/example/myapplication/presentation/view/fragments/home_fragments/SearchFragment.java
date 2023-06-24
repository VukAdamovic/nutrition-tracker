package com.example.myapplication.presentation.view.fragments.home_fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.data.models.api.domain.Category;
import com.example.myapplication.data.models.api.domain.MealFiltered;
import com.example.myapplication.databinding.FragmentSearchBinding;
import com.example.myapplication.presentation.view.fragments.adapters.MealAdapter;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;

    private Category category;



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
        RecyclerView recyclerView = binding.searchRecycleView;
        MealAdapter mealAdapter = new MealAdapter(getSampleList(), requireActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mealAdapter);
    }

    public static List<MealFiltered> getSampleList() {
        List<MealFiltered> mealList = new ArrayList<>();

        MealFiltered meal1 = new MealFiltered("1", "thumbnail1.jpg", "Meal 1");
        mealList.add(meal1);

        MealFiltered meal2 = new MealFiltered("2", "thumbnail2.jpg", "Meal 2");
        mealList.add(meal2);

        MealFiltered meal3 = new MealFiltered("3", "thumbnail3.jpg", "Meal 3");
        mealList.add(meal3);

        MealFiltered meal4 = new MealFiltered("4", "thumbnail4.jpg", "Meal 4");
        mealList.add(meal4);

        MealFiltered meal5 = new MealFiltered("5", "thumbnail5.jpg", "Meal 5");
        mealList.add(meal5);

        MealFiltered meal6 = new MealFiltered("6", "thumbnail6.jpg", "Meal 6");
        mealList.add(meal6);

        MealFiltered meal7 = new MealFiltered("7", "thumbnail7.jpg", "Meal 7");
        mealList.add(meal7);

        MealFiltered meal8 = new MealFiltered("8", "thumbnail8.jpg", "Meal 8");
        mealList.add(meal8);

        MealFiltered meal9 = new MealFiltered("9", "thumbnail9.jpg", "Meal 9");
        mealList.add(meal9);

        MealFiltered meal10 = new MealFiltered("10", "thumbnail10.jpg", "Meal 10");
        mealList.add(meal10);

        MealFiltered meal11 = new MealFiltered("11", "thumbnail11.jpg", "Meal 11");
        mealList.add(meal11);

        MealFiltered meal12 = new MealFiltered("12", "thumbnail12.jpg", "Meal 12");
        mealList.add(meal12);

        MealFiltered meal13 = new MealFiltered("13", "thumbnail13.jpg", "Meal 13");
        mealList.add(meal13);

        MealFiltered meal14 = new MealFiltered("14", "thumbnail14.jpg", "Meal 14");
        mealList.add(meal14);

        MealFiltered meal15 = new MealFiltered("15", "thumbnail15.jpg", "Meal 15");
        mealList.add(meal15);

        MealFiltered meal16 = new MealFiltered("16", "thumbnail16.jpg", "Meal 16");
        mealList.add(meal16);

        MealFiltered meal17 = new MealFiltered("17", "thumbnail17.jpg", "Meal 17");
        mealList.add(meal17);

        MealFiltered meal18 = new MealFiltered("18", "thumbnail18.jpg", "Meal 18");
        mealList.add(meal18);

        MealFiltered meal19 = new MealFiltered("19", "thumbnail19.jpg", "Meal 19");
        mealList.add(meal19);

        MealFiltered meal20 = new MealFiltered("20", "thumbnail20.jpg", "Meal 20");
        mealList.add(meal20);

        return mealList;
    }
}