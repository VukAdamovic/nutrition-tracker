package com.example.myapplication.presentation.view.fragments;

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
import com.example.myapplication.presentation.view.activities.MainActivity;

import javax.inject.Inject;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    private TextView error;

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

        binding.button9.setOnClickListener(v->{
            String newPassword = binding.editTextTextPassword2.getText().toString();
            String confirm = binding.editTextTextPassword3.getText().toString();

            if(newPassword.equals("") || confirm.equals("")){
                error.setText("All fields are required");
                error.setVisibility(View.VISIBLE);
            } else if(!newPassword.equals(confirm)){
                error.setText("Enter same password");
                error.setVisibility(View.VISIBLE);
            }else{
                error.setVisibility(View.INVISIBLE);
                MainActivity mainActivity = (MainActivity) requireActivity();
                MainActivity.mainViewModel.updateUser(mainActivity.sharedPreferences.getInt("USER_ID", -1),newPassword);
            }
        });
    }

    private void initObservers(){
        MainActivity.activeUser.observe(this, userEntity -> {
            Toast.makeText(getContext(), "Sucessfully changed password", Toast.LENGTH_SHORT);
            Log.d("Updated password", userEntity.getPassword());
        });

    }
}