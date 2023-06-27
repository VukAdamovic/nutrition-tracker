package com.example.myapplication.presentation.view.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentFilterBinding;
import com.example.myapplication.databinding.FragmentSettingsBinding;
import com.example.myapplication.presentation.view.activities.LoginActivity;
import com.example.myapplication.presentation.view.activities.MainActivity;
import com.example.myapplication.presentation.view.activities.StartActivity;

import javax.inject.Inject;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    private TextView error;

    private String newPassword;

    @Inject
    public SharedPreferences sharedPreferences;



    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        initListeners();
        return binding.getRoot();
    }

    private void initListeners(){
        initObservers();

        error = binding.textView50;

        binding.button9.setOnClickListener(v -> {
            newPassword = binding.editTextTextPassword2.getText().toString();
            String confirm = binding.editTextTextPassword3.getText().toString();

            if (newPassword.equals("") || confirm.equals("")) {
                error.setText("All fields are required");
                error.setVisibility(View.VISIBLE);
            } else if (!newPassword.equals(confirm)) {
                error.setText("Enter the same password");
                error.setVisibility(View.VISIBLE);
            } else if (newPassword.length() < 8) {
                error.setText("Password must be at least 8 characters");
                error.setVisibility(View.VISIBLE);
            } else {
                error.setVisibility(View.INVISIBLE);
                MainActivity mainActivity = (MainActivity) requireActivity();
                MainActivity.mainViewModel.updateUser(mainActivity.sharedPreferences.getInt("USER_ID", -1), newPassword);
                MainActivity.mainViewModel.getUserById(mainActivity.sharedPreferences.getInt("USER_ID", -1));
            }
        });

        binding.textView47.setOnClickListener(v->{
            MainActivity mainActivity = (MainActivity) requireActivity();
            mainActivity.sharedPreferences.edit().remove("USER_ID").apply();
            mainActivity.sharedPreferences.edit().remove("LoggedIn").apply();
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });

        binding.button4.setOnClickListener(v -> {
            String ageText = binding.editTextNumber.getText().toString().trim();
            String heightText = binding.editTextNumber2.getText().toString().trim();
            String weightText = binding.editTextNumber3.getText().toString().trim();
            RadioButton maleRadioBtn = binding.radioButton17;
            RadioButton femaleRadioBtn = binding.radioButton18;
            RadioButton activeRadioBtn = binding.radioButton19;
            RadioButton noActiveRadioBtn = binding.radioButton20;

            if (ageText.isEmpty() || heightText.isEmpty() || weightText.isEmpty()) {
                Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
            } else {
                int age = Integer.parseInt(ageText);
                int height = Integer.parseInt(heightText);
                int weight = Integer.parseInt(weightText);

                if (!maleRadioBtn.isChecked() && !femaleRadioBtn.isChecked()){
                    Toast.makeText(getContext(), "Select Gender", Toast.LENGTH_SHORT).show();
                } else if (!activeRadioBtn.isChecked() && !noActiveRadioBtn.isChecked()){
                    Toast.makeText(getContext(), "Select your daily activity", Toast.LENGTH_SHORT).show();
                } else {
                    String gender = maleRadioBtn.isChecked() ? "muškarac" : "žena";
                    String activity = activeRadioBtn.isChecked() ? "aktivno" : "neaktivno";

                    double calorieIntake = calculateCalorieIntake(age, height, weight, gender, activity);

                    MainActivity mainActivity = (MainActivity) requireActivity();
                    float roundedCalorieIntake = Math.round(calorieIntake);
                    mainActivity.sharedPreferences.edit().putFloat("CALORIES", roundedCalorieIntake).apply();
                    Toast.makeText(getContext(), "Calorie Intake: " + roundedCalorieIntake, Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void initObservers(){
        MainActivity.activeUser.observe(this, userEntity -> {
            Toast.makeText(requireActivity().getApplicationContext(), "Password updated successfully", Toast.LENGTH_SHORT).show();
            binding.editTextTextPassword2.setText("");
            binding.editTextTextPassword3.setText("");
        });
    }


    private double calculateCalorieIntake(int age, int height, int weight, String gender, String activity) {
        double bmr;
        double calorieIntake;

        if (gender.equals("muškarac")) {
            bmr = 10 * weight + 6.25 * height - 5 * age + 5;
        } else {
            bmr = 10 * weight + 6.25 * height - 5 * age - 161;
        }

        if (activity.equals("aktivno")) {
            calorieIntake = bmr * 1.55;
        } else {
            calorieIntake = bmr * 1.2;
        }

        return calorieIntake;
    }
}