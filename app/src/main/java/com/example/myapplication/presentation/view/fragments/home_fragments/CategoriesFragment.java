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
import com.example.myapplication.databinding.FragmentCategoriesBinding;
import com.example.myapplication.presentation.view.fragments.adapters.CategoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment {

    private FragmentCategoriesBinding binding;


    public CategoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment using ViewBinding
        binding = FragmentCategoriesBinding.inflate(inflater, container, false);
        initListeners();
        return binding.getRoot();
    }

    private void initListeners(){
        RecyclerView recyclerView = binding.categoriesRecycleView;
        CategoryAdapter categoryAdapter = new CategoryAdapter(CategoriesFragment.generateCategoryList(), getParentFragment());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(categoryAdapter);
    }

    public static List<Category> generateCategoryList() {
        List<Category> categoryList = new ArrayList<>();

        // Creating 20 Category instances and adding them to the list
        categoryList.add(new Category("1", "Category 1", "thumbnail1", "Description 1"));
        categoryList.add(new Category("2", "Category 2", "thumbnail2", "Description 2"));
        categoryList.add(new Category("3", "Category 3", "thumbnail3", "Description 3"));
        categoryList.add(new Category("4", "Category 4", "thumbnail4", "Description 4"));
        categoryList.add(new Category("5", "Category 5", "thumbnail5", "Description 5"));
        categoryList.add(new Category("6", "Category 6", "thumbnail6", "Description 6"));
        categoryList.add(new Category("7", "Category 7", "thumbnail7", "Description 7"));
        categoryList.add(new Category("8", "Category 8", "thumbnail8", "Description 8"));
        categoryList.add(new Category("9", "Category 9", "thumbnail9", "Description 9"));
        categoryList.add(new Category("10", "Category 10", "thumbnail10", "Description 10"));
        categoryList.add(new Category("11", "Category 11", "thumbnail11", "Description 11"));
        categoryList.add(new Category("12", "Category 12", "thumbnail12", "Description 12"));
        categoryList.add(new Category("13", "Category 13", "thumbnail13", "Description 13"));
        categoryList.add(new Category("14", "Category 14", "thumbnail14", "Description 14"));
        categoryList.add(new Category("15", "Category 15", "thumbnail15", "Description 15"));
        categoryList.add(new Category("16", "Category 16", "thumbnail16", "Description 16"));
        categoryList.add(new Category("17", "Category 17", "thumbnail17", "Description 17"));
        categoryList.add(new Category("18", "Category 18", "thumbnail18", "Description 18"));
        categoryList.add(new Category("19", "Category 19", "thumbnail19", "Description 19"));
        categoryList.add(new Category("20", "Category 20", "thumbnail20", "Description 20"));

        return categoryList;
    }

}