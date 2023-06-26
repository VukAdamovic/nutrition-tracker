package com.example.myapplication.presentation.view.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    }

    private void initObservers(){
        MainActivity.activeUser.observe(this, userEntity -> {
            Toast.makeText(requireActivity().getApplicationContext(), "Password updated successfully", Toast.LENGTH_SHORT).show();
            binding.editTextTextPassword2.setText("");
            binding.editTextTextPassword3.setText("");
        });
    }
}