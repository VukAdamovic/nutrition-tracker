package com.example.myapplication.presentation.view.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.myapplication.R;
import com.example.myapplication.data.models.api.domain.Category;

import java.util.Objects;

public class CategoryDialog extends DialogFragment {

    private Category category;

    public CategoryDialog(Category category) {
        this.category = category;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_dialog, container, false);

        TextView textViewCategory = view.findViewById(R.id.textView9);
        TextView textViewDescription = view.findViewById(R.id.textView10);

        textViewCategory.setText(category.getName());
        textViewDescription.setText(category.getDescription());

        TextView textViewClose = view.findViewById(R.id.textView11);
        textViewClose.setOnClickListener(v -> dismiss());

        return view;
    }
}


