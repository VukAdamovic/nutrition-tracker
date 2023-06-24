package com.example.myapplication.presentation.view.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.R;

public class SavedMealDialog extends DialogFragment {

    private int idMeal;

    public SavedMealDialog(int idMeal) {
        this.idMeal = idMeal;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.saved_meal_dialog, null);

        // Pronalaženje View elemenata
        TextView textViewClose = view.findViewById(R.id.textView30);
        ImageView imageView = view.findViewById(R.id.imageView5);
        imageView.setImageResource(R.drawable.background);


        // Postavljanje teksta i klika na X TextView
        textViewClose.setText("X");
        textViewClose.setOnClickListener(v -> dismiss());

        builder.setView(view);

        return builder.create();
    }

}
