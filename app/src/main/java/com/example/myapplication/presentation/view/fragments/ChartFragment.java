package com.example.myapplication.presentation.view.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.data.models.entities.MealEntity;
import com.example.myapplication.databinding.FragmentChartBinding;
import com.example.myapplication.presentation.view.activities.MainActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;


public class ChartFragment extends Fragment {

    private FragmentChartBinding binding;

    private PieChart chartNumMeals;

    private BarChart chartNumCalories;


    private List<PieEntry> numMeals = new ArrayList<>();
    private List<BarEntry> caloriesMeals = new ArrayList<>();



    public ChartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChartBinding.inflate(inflater, container, false);
        initListeners();
        return binding.getRoot();
    }

    private void initListeners(){
        initObservers();

        chartNumMeals = binding.barChart1;
        chartNumCalories = binding.barChart2;

        MainActivity mainActivity = (MainActivity) requireActivity();
        MainActivity.mainViewModel.getMealsLastSevenDays(mainActivity.sharedPreferences.getInt("USER_ID",-1), new Date());
    }



    private void initObservers(){
        MainActivity.mealsInLastSevenDays.observe(this, mealEntities -> {
            HashMap<String, List<MealEntity>> mealsByDate = new HashMap<>();
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE", Locale.getDefault());

            // Grupisanje obroka po datumu
            for (MealEntity meal : mealEntities) {
                Date mealDate = meal.getPreparationDate();
                String formattedDate = dateFormat.format(mealDate);

                if (mealsByDate.containsKey(formattedDate)) {
                    List<MealEntity> mealList = mealsByDate.get(formattedDate);
                    mealList.add(meal);
                } else {
                    List<MealEntity> newMealList = new ArrayList<>();
                    newMealList.add(meal);
                    mealsByDate.put(formattedDate, newMealList);
                }
            }

            LocalDate trenutniDatum = LocalDate.now();
            LocalDate danPreTrenutnog = trenutniDatum.minusDays(1);
            LocalDate sedamDanaUnazad = trenutniDatum.minusDays(7);

            DateTimeFormatter format = DateTimeFormatter.ofPattern("EEE");

            for (LocalDate date = danPreTrenutnog; date.isAfter(sedamDanaUnazad); date = date.minusDays(1)) {
                String formattedDate = date.format(format);

                if (!mealsByDate.containsKey(formattedDate)) {
                    mealsByDate.put(formattedDate, new ArrayList<>());
                }
            }

            Log.d("Hash Mapa za sve dane", mealsByDate.keySet() + " " + mealsByDate);

            // Iteriranje kroz sortiranu hash mapu i dodavanje PieEntry-ja u listu numMeals
            for (Map.Entry<String, List<MealEntity>> entry : mealsByDate.entrySet()) {
                String date = entry.getKey();
                List<MealEntity> meals = entry.getValue();
                int mealCount = meals.size();

                numMeals.add(new PieEntry(1, date + "\n" + mealCount));
            }



            PieDataSet pieDataSet = new PieDataSet(numMeals, "Number of meals");
            pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
            pieDataSet.setSliceSpace(2f);  // Postavljanje razmaka između sektora
            pieDataSet.setSelectionShift(8f);  // Postavljanje pomeraja za odabir sektora
            pieDataSet.setValueTextColor(Color.WHITE);
            pieDataSet.setValueTextSize(16f);
            pieDataSet.setDrawValues(false);  // Isključivanje prikaza vrednosti



            PieData pieData = new PieData(pieDataSet);
            pieData.setValueTextSize(12f);
            pieData.setValueTextColor(Color.WHITE);


            chartNumMeals.setData(pieData);
            chartNumMeals.setCenterText("Number of meals");
            chartNumMeals.setCenterTextColor(Color.GRAY);
            chartNumMeals.setCenterTextSize(10f);
            chartNumMeals.getDescription().setEnabled(false); // Isključuje prikaz opisa
            chartNumMeals.getLegend().setEnabled(false); // Isključuje prikaz legende


        });
    }



}