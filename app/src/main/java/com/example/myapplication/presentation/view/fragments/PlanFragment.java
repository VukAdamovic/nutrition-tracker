package com.example.myapplication.presentation.view.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.data.models.api.domain.MealFiltered;
import com.example.myapplication.data.models.api.domain.PlanMeal;
import com.example.myapplication.data.models.entities.MealEntity;
import com.example.myapplication.databinding.FragmentPlanBinding;
import com.example.myapplication.presentation.view.activities.MainActivity;
import com.example.myapplication.presentation.view.fragments.adapters.FilterAdapter;
import com.example.myapplication.presentation.view.fragments.adapters.PlanAdapter;
import com.example.myapplication.presentation.view.fragments.adapters.PlanMakerAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class PlanFragment extends Fragment {

    private FragmentPlanBinding binding;

    private RecyclerView recyclerViewWeeklyPlan;

    private PlanMakerAdapter planMakerAdapter;

    private RecyclerView recyclerViewCategories;

    private FilterAdapter filterAdapter;

    private RecyclerView recyclerViewMealsByCategory;

    private EditText email;

    private PlanAdapter planAdapter;

    private String day;

    private String type;

    private boolean openedFragment;



    public PlanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPlanBinding.inflate(inflater, container, false);
        openedFragment = true;
        initListeners();
        return binding.getRoot();
    }

    private void initListeners(){
        recyclerViewWeeklyPlan = binding.recyclerView;
        recyclerViewCategories = binding.recyclerView2;
        recyclerViewMealsByCategory = binding.recyclerViewApiOrMenu;
        email = binding.editTextText6;

        initObservers();

        SwitchCompat toggleSearch = binding.toggleButton2;

        MainActivity.mainViewModel.getCategories();
        MainActivity.mainViewModel.getMealsByCategory("Chicken");

        toggleSearch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                MainActivity.mainViewModel.getCategories();
                MainActivity.mainViewModel.getMealsByCategory("Chicken");
            } else {
                MainActivity mainActivity = (MainActivity) requireActivity();
                MainActivity.mainViewModel.getMealsByUserId(mainActivity.sharedPreferences.getInt("USER_ID", -1));
            }
        });

        binding.button11.setOnClickListener(v -> {
            if(planMakerAdapter == null) {
                showToastMessage("There must be at least one meal per day.");
                return;
            }

            String emailStr = email.getText().toString();

            // Check if the email is valid
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()) {
                showToastMessage("Invalid email address.");
                return;
            }

            List<PlanMeal> planMeals = planMakerAdapter.getPlanMeals();
            HashSet<String> daysInPlan = new HashSet<>();

            for (PlanMeal planMeal : planMeals) {
                daysInPlan.add(planMeal.getMealDay());
            }

            if (daysInPlan.size() < 7) {
                showToastMessage("There must be at least one meal per day.");
            } else {
                StringBuilder emailBody = new StringBuilder();
                for (PlanMeal planMeal : planMeals) {
                    emailBody.append("Day: ").append(planMeal.getMealDay())
                            .append("\nType: ").append(planMeal.getMealType())
                            .append("\nMeal: ").append(planMeal.getName())
                            .append("\nCalories: ").append(planMeal.getCalories())
                            .append("\nMeal Image: ").append(planMeal.getImageUrl())
                            .append("\n\n");
                }

                StringBuilder url = new StringBuilder("https://www.nutritracker.com/tracker");

                for (int i = 0; i < planMeals.size(); i++) {
                    if (i == 0) {
                        url.append("?id").append(i).append("=").append(Uri.encode(String.valueOf(planMeals.get(i).getId())));
                    } else {
                        url.append("&id").append(i).append("=").append(Uri.encode(String.valueOf(planMeals.get(i).getId())));
                    }
                }


                emailBody.append("\nOpen the app: ")
                        .append(url);

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Meal Plan");
                intent.putExtra(Intent.EXTRA_TEXT, emailBody.toString());
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailStr});
                try {
                    startActivity(Intent.createChooser(intent, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        toggleSearch.setChecked(true);
    }

    private void initObservers(){
        MainActivity.allCategoriesLiveData.observe(this, categories -> {
            filterAdapter = new FilterAdapter(categories, null, null, null, null);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            recyclerViewCategories.setLayoutManager(layoutManager);
            recyclerViewCategories.setAdapter(filterAdapter);
        });

        MainActivity.allFilteredMealsByCategoryLiveData.observe(this, meals -> {
            planAdapter = new PlanAdapter(meals, requireActivity().getSupportFragmentManager());
            planAdapter.setOnPlanValuesPassListener((day, type, mealFiltered) -> {
                Log.d("PlanFragment", "Day and Type: " + day + " " + type + " " + mealFiltered.getId());
                this.type = type;
                this.day = day;
                MainActivity.mainViewModel.getMealById(mealFiltered.getId());
            });
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerViewMealsByCategory.setLayoutManager(layoutManager);
            recyclerViewMealsByCategory.setAdapter(planAdapter);
        });

        MainActivity.allMealsByUserId.observe(this, mealEntities -> {
            List<MealFiltered> mealFilteredList = new ArrayList<>();
            for(MealEntity meal : mealEntities){
                mealFilteredList.add(new MealFiltered(String.valueOf(meal.getId()), meal.getMealImageUrl(), meal.getMealName()));
            }
            planAdapter = new PlanAdapter(mealFilteredList, requireActivity().getSupportFragmentManager());
            planAdapter.setOnPlanValuesPassListener((day, type, mealFiltered) -> {
                Log.d("PlanFragment", "Day and Type: " + day + " " + type + " " + mealFiltered.getId());
                this.type = type;
                this.day = day;
                MainActivity.mainViewModel.getMealById(mealFiltered.getId());
            });
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerViewMealsByCategory.setLayoutManager(layoutManager);
            recyclerViewMealsByCategory.setAdapter(planAdapter);
        });

        MainActivity.currentMealWithCaloriesLiveData.observe(this, mealSingle -> {
            if (day == null || type == null) return;
            if (recyclerViewWeeklyPlan.getAdapter() != null) {
                planMakerAdapter = (PlanMakerAdapter) recyclerViewWeeklyPlan.getAdapter();
                List<PlanMeal> newPlanMeals = new ArrayList<>(planMakerAdapter.getPlanMeals());
                newPlanMeals.add(new PlanMeal(mealSingle.getId(), mealSingle.getMealName(), mealSingle.getMealImageUrl(), type, day, mealSingle.getCalories()));
                planMakerAdapter.setPlanMeals(newPlanMeals);
            } else {
                List<PlanMeal> planMeals = new ArrayList<>();
                planMeals.add(new PlanMeal(mealSingle.getId(), mealSingle.getMealName(), mealSingle.getMealImageUrl(), type, day, mealSingle.getCalories()));
                planMakerAdapter = new PlanMakerAdapter(planMeals, this);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                recyclerViewWeeklyPlan.setLayoutManager(layoutManager);
                recyclerViewWeeklyPlan.setAdapter(planMakerAdapter);
            }
            type = null;
            day = null;
        });
        MainActivity.singleMealByIdLiveData.observe(this, mealSingles -> {
            MainActivity.mainViewModel.getCaloriesForMeal(mealSingles.get(0));
        });
    }

    private void showToastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}