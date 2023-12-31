package com.example.myapplication.presentation.view.fragments;

import android.graphics.Outline;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.myapplication.data.models.api.domain.MealFiltered;
import com.example.myapplication.data.models.api.domain.MealSingle;
import com.example.myapplication.data.models.entities.MealEntity;
import com.example.myapplication.databinding.FragmentSingleMealBinding;
import com.example.myapplication.presentation.view.activities.MainActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SingleMealFragment extends Fragment {

    private MealFiltered mealFiltered;
    private FragmentSingleMealBinding binding;
    private ProgressBar progressBar;


    public SingleMealFragment(MealFiltered mealFiltered) {
        this.mealFiltered = mealFiltered;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSingleMealBinding.inflate(inflater, container, false);
        initListeners();
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        List<MealSingle> lista = new ArrayList<>();
        lista.add(new MealSingle());
        MainActivity.singleMealByIdLiveData.setValue(lista);
        MainActivity.currentMealWithCaloriesLiveData.setValue(new MealSingle());
        MainActivity.singleMealByIdLiveData.removeObservers(getViewLifecycleOwner());
        MainActivity.currentMealWithCaloriesLiveData.removeObservers(getViewLifecycleOwner());
        binding = null;
    }


    private void initListeners(){
        TextView exit = binding.textView31;
        progressBar = binding.progressBar2;

        exit.setOnClickListener(v -> {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.popBackStack(); // Uklonite fragment sa steka povratka
        });

        initObservers();
        showProgressDialog();
        MainActivity.mainViewModel.getMealById(mealFiltered.getId());
    }

    private void initObservers() {
        MainActivity.singleMealByIdLiveData.observe(getViewLifecycleOwner(), meals -> {
            MainActivity.mainViewModel.getCaloriesForMeal(meals.get(0));
        });

        MainActivity.currentMealWithCaloriesLiveData.observe(getViewLifecycleOwner(), mealSingle -> {
            hideProgressDialog();


            binding.textView32.setText(mealSingle.getMealName());

            ImageView imageView = binding.imageView6;
            imageView.setClipToOutline(true);
            imageView.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    // Use view.getContext().getResources()
                    outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 10 * view.getContext().getResources().getDisplayMetrics().density);
                }
            });

            Glide.with(this)
                    .load(mealSingle.getMealImageUrl())
                    .into(imageView);

            binding.textView34.setText(mealSingle.getCategory());
            binding.textView36.setText(mealSingle.getArea());
            binding.textView38.setText(mealSingle.getInstructions());
            binding.textView40.setText(TextUtils.join(", ", mealSingle.getTags()));
            binding.textView42.setText(mealSingle.getYouTubeLink());
            binding.textView44.setText(String.valueOf(mealSingle.getCalories()));
            binding.textView46.setText(TextUtils.join("\n", mealSingle.getIngredientsMeasurements()));

            binding.button7.setOnClickListener(v->{
                MainActivity mainActivity = (MainActivity) requireActivity();

                MainActivity.mainViewModel.insertMeal(new MealEntity(
                        mealSingle.getMealName(),
                        mealSingle.getMealImageUrl(),
                        mealSingle.getInstructions(),
                        mealSingle.getYouTubeLink(),
                        mealSingle.getIngredientsMeasurements(),
                        mealSingle.getCategory(),
                        new Date(),
                        mealSingle.getCalories(),
                        mainActivity.sharedPreferences.getInt("USER_ID", 9999)
                ));

                Toast.makeText(requireActivity(), "Meal Saved Successfully!", Toast.LENGTH_SHORT).show();

                requireActivity().getSupportFragmentManager().popBackStack();
            });
        });
    }

    private void showProgressDialog() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressDialog() {
        progressBar.setVisibility(View.GONE);
    }
}