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
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;


public class ChartFragment extends Fragment {

    private FragmentChartBinding binding;

    private PieChart chartNumMeals;

    private PieChart chartNumCalories;

    private List<PieEntry> numMeals = new ArrayList<>();
    private List<PieEntry> caloriesMeals = new ArrayList<>();



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

            Comparator<String> dayOfWeekComparator = new Comparator<String>() {
                @Override
                public int compare(String day1, String day2) {
                    String[] weekDays = {"pon", "uto", "sre", "čet", "pet", "sub", "ned"};
                    int index1 = -1;
                    int index2 = -1;
                    for (int i = 0; i < weekDays.length; i++) {
                        if (weekDays[i].equals(day1)) {
                            index1 = i;
                        }
                        if (weekDays[i].equals(day2)) {
                            index2 = i;
                        }
                    }
                    return Integer.compare(index1, index2);
                }
            };

            Map<String, List<MealEntity>> sortedMealsByDate = new TreeMap<>(dayOfWeekComparator);

            for (LocalDate date = danPreTrenutnog; date.isAfter(sedamDanaUnazad); date = date.minusDays(1)) {
                String formattedDate = date.format(format);

                if (!mealsByDate.containsKey(formattedDate)) {
                    mealsByDate.put(formattedDate, new ArrayList<>());
                }
            }

            sortedMealsByDate.putAll(mealsByDate);


            //Gore je sve za logiku ovde punis pieChart-ove za borj jela

            for (Map.Entry<String, List<MealEntity>> entry : sortedMealsByDate.entrySet()) {
                String date = entry.getKey();
                List<MealEntity> meals = entry.getValue();
                int mealCount = meals.size();

                double totalCalories = 0;
                for (MealEntity meal : meals) {
                    totalCalories += meal.getCalories();
                }
                totalCalories = Math.round(totalCalories); // Zaokruživanje na najbliži ceo broj
                caloriesMeals.add(new PieEntry(1, date.toUpperCase() + " - " + totalCalories));
                numMeals.add(new PieEntry(1, date.toUpperCase() + " - " + mealCount));
            }


            List<Integer> colors = new ArrayList<>();
            colors.add(Color.parseColor("#FF6200EE"));
            colors.add(Color.parseColor("#FF8B14"));
            colors.add(Color.parseColor("#FF00695C"));

            //Inicijalizacija za gornji chart
            PieDataSet pieDataSet = new PieDataSet(numMeals, "Number of meals");
            pieDataSet.setColors(colors);
            pieDataSet.setSliceSpace(2f);  // Postavljanje razmaka između sektora
            pieDataSet.setSelectionShift(10f);  // Postavljanje pomeraja za odabir sektora
            pieDataSet.setValueTextColor(Color.WHITE);
            pieDataSet.setValueTextSize(4f);
            pieDataSet.setDrawValues(false);  // Isključivanje prikaza vrednosti

            PieData pieData = new PieData(pieDataSet);
            pieData.setValueTextSize(12f);
            pieData.setValueTextColor(Color.WHITE);

            chartNumMeals.setData(pieData);
            chartNumMeals.setCenterText("Meals per day");
            chartNumMeals.setCenterTextColor(Color.GRAY);
            chartNumMeals.setCenterTextSize(10f);
            chartNumMeals.getDescription().setEnabled(false);
            chartNumMeals.getLegend().setEnabled(false);

            //inicijalizacija za donji chart
            PieDataSet pieDataSet2 = new PieDataSet(caloriesMeals, "Number of meals per day");
            pieDataSet2.setColors(colors);
            pieDataSet2.setSliceSpace(2f);
            pieDataSet2.setSelectionShift(10f);
            pieDataSet2.setValueTextColor(Color.WHITE);
            pieDataSet2.setValueTextSize(4f);
            pieDataSet2.setDrawValues(false);

            PieData pieData2 = new PieData(pieDataSet2);
            pieData2.setValueTextSize(12f);
            pieData2.setValueTextColor(Color.WHITE);

            chartNumCalories.setData(pieData2);
            chartNumCalories.setCenterText("Calories per day");
            chartNumCalories.setCenterTextColor(Color.GRAY);
            chartNumCalories.setCenterTextSize(10f);
            chartNumCalories.getDescription().setEnabled(false);
            chartNumCalories.getLegend().setEnabled(false);


        });
    }



}