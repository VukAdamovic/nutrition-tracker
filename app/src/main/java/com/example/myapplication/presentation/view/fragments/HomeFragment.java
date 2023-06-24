package com.example.myapplication.presentation.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.presentation.view.fragments.home_fragments.CategoriesFragment;
import com.example.myapplication.presentation.view.fragments.home_fragments.MenuFragment;
import com.example.myapplication.presentation.view.fragments.home_fragments.SearchFragment;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        initListeners();
        return binding.getRoot();
    }

    private void initListeners(){
        replaceFragment(new CategoriesFragment()); // da se onda prikaze neki fragment

        TextView categories = binding.categories;
        categories.setOnClickListener(v->{
            replaceFragment(new CategoriesFragment());
        });

        TextView search = binding.search;
        search.setOnClickListener(v->{
            replaceFragment(new SearchFragment());
        });

        TextView menu = binding.menu;
        menu.setOnClickListener(v->{
            replaceFragment(new MenuFragment());
        });



    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout2, fragment);
        fragmentTransaction.commit();
    }


}