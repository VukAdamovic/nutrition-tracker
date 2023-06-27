package com.example.myapplication.presentation.view.fragments.home_fragments;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.FragmentMenuBinding;
import com.example.myapplication.presentation.view.activities.MainActivity;
import com.example.myapplication.presentation.view.fragments.adapters.SavedMealAdapter;

public class MenuFragment extends Fragment {

    private FragmentMenuBinding binding;

    private RecyclerView recyclerView;

    private EditText search;

    private SavedMealAdapter savedMealAdapter;

    private Handler handler = new Handler();

    private Runnable searchRunnable;

    private ProgressBar progressBar;

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
        search = binding.editTextText3;
        progressBar = binding.progressBar3;
        MainActivity mainActivity = (MainActivity) requireActivity();
        showProgressDialog();
        MainActivity.mainViewModel.getMealsByUserId(mainActivity.sharedPreferences.getInt("USER_ID", 9999));
        initObservers();

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action required here
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeCallbacks(searchRunnable); // Assuming 'handler' and 'searchRunnable' are available here

                searchRunnable = () -> savedMealAdapter.filter(s.toString());

                handler.postDelayed(searchRunnable, 500);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void initObservers() {
        MainActivity.mealsByUserId.observe(this, meals -> {
            hideProgressDialog();
            savedMealAdapter = new SavedMealAdapter(meals, getParentFragment());
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(savedMealAdapter);
        });
    }

    private void showProgressDialog() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressDialog() {
        progressBar.setVisibility(View.GONE);
    }

}