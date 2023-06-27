package com.example.myapplication.presentation.view.dialogs;

import android.app.Dialog;
import android.graphics.Outline;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.data.models.entities.MealEntity;

public class SavedMealDialog extends DialogFragment {

    private MealEntity meal;

    public SavedMealDialog(MealEntity meal) {
        this.meal = meal;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_single_meal, null);

        // Pronalaženje View elemenata
        TextView textViewClose = view.findViewById(R.id.textView31);
        TextView mealName = view.findViewById(R.id.textView32);
        ImageView imageView = view.findViewById(R.id.imageView6);
        TextView category = view.findViewById(R.id.textView34);
        TextView areaHeader = view.findViewById(R.id.textView35);
        TextView area = view.findViewById(R.id.textView36);
        TextView instructions = view.findViewById(R.id.textView38);
        TextView tagsHeader = view.findViewById(R.id.textView39);
        TextView tags = view.findViewById(R.id.textView40);
        TextView youtube = view.findViewById(R.id.textView42);
        TextView calories = view.findViewById(R.id.textView44);
        TextView ingredients = view.findViewById(R.id.textView46);
        Button hideButton = view.findViewById(R.id.button7);

        textViewClose.setOnClickListener(v -> dismiss());
        mealName.setText(meal.getMealName());
        imageView.setClipToOutline(true);
        imageView.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                // Use view.getContext().getResources()
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 10 * view.getContext().getResources().getDisplayMetrics().density);
            }
        });

        Glide.with(view)
                .load(meal.getMealImageUrl())
                .placeholder(R.drawable.background)
                .into(imageView);

        category.setText(meal.getCategory());
//        area.setText();
        instructions.setText(meal.getInstructions());
//        tags.setText();
        youtube.setText(meal.getYouTubeLink());
        calories.setText(String.valueOf(meal.getCalories()));
        ingredients.setText(TextUtils.join("\n", meal.getIngredientsMeasurements()));
        hideButton.setVisibility(View.INVISIBLE);
        areaHeader.setVisibility(View.GONE);
        area.setVisibility(View.GONE);
        tagsHeader.setVisibility(View.GONE);
        tags.setVisibility(View.GONE);

        builder.setView(view);
        return builder.create();
    }

}
