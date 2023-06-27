package com.example.myapplication.presentation.view.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.R;
import com.example.myapplication.data.models.api.domain.MealFiltered;

public class PlanDialog extends DialogFragment {

    private MealFiltered mealFiltered;

    private OnPlanSelectedListener listener;

    public PlanDialog(MealFiltered mealFiltered, OnPlanSelectedListener listener) {
        this.mealFiltered = mealFiltered;
        this.listener = listener;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.plan_dialog, container, false);

        RadioGroup radioGroupDay = view.findViewById(R.id.radioGroup4);
        RadioGroup radioGroupType = view.findViewById(R.id.radioGroup3);
        Button addButton = view.findViewById(R.id.button10);

        view.findViewById(R.id.textView52).setOnClickListener(v -> dismiss());

        RadioButton radioButtonMon = view.findViewById(R.id.radioButton16);
        radioButtonMon.setChecked(true);
        RadioButton radioButtonBreakfast = view.findViewById(R.id.radioButton9);
        radioButtonBreakfast.setChecked(true);

        addButton.setOnClickListener(v -> {
            int selectedDayId = radioGroupDay.getCheckedRadioButtonId();
            RadioButton radioButtonDay = view.findViewById(selectedDayId);
            String selectedDay = radioButtonDay.getText().toString();

            int selectedTypeId = radioGroupType.getCheckedRadioButtonId();
            RadioButton radioButtonType = view.findViewById(selectedTypeId);
            String selectedType = radioButtonType.getText().toString();

            if (listener != null) {
                listener.onPlanSelected(selectedDay, selectedType);
            }

            dismiss();
        });

        return view;
    }

    public void setOnPlanSelectedListener(OnPlanSelectedListener listener) {
        this.listener = listener;
    }

    public interface OnPlanSelectedListener {
        void onPlanSelected(String day, String type);
    }
}
