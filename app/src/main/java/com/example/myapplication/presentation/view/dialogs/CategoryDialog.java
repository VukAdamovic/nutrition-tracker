package com.example.myapplication.presentation.view.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.myapplication.R;

public class CategoryDialog extends DialogFragment {

    private int id;

    public CategoryDialog(int id) {
        this.id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_dialog, container, false);

        // Podesite sadržaj dijaloga
        TextView textViewCategory = view.findViewById(R.id.textView9);
        TextView textViewDescription = view.findViewById(R.id.textView10);


        // Dodajte logiku za dugme "X" koje zatvara dijalog
        TextView textViewClose = view.findViewById(R.id.textView11);
        textViewClose.setOnClickListener(v -> dismiss());

        return view;
    }
}

