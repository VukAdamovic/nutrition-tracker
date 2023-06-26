package com.example.myapplication.presentation.view.dialogs;

import android.app.DatePickerDialog;
import android.graphics.Outline;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.data.models.entities.MealEntity;
import com.example.myapplication.presentation.view.activities.MainActivity;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UpdateSavedMealDialog extends DialogFragment {

    private MealEntity meal;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private Button updateButton;
    private String selectedDate;
    private UpdateSavedMealDialogListener listener;

    public UpdateSavedMealDialog(MealEntity meal, UpdateSavedMealDialogListener listener) {
        this.meal = meal;
        this.listener = listener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.saved_meal_update_dialog, container, false);

        TextView textViewImage = view.findViewById(R.id.textViewAboveImage);
        ImageView image = view.findViewById(R.id.imageView);
        updateButton = view.findViewById(R.id.centerButton);

        image.setClipToOutline(true);
        image.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                // Use view.getContext().getResources()
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 10 * view.getContext().getResources().getDisplayMetrics().density);
            }
        });

        Glide
                .with(this)
                .load(meal.getMealImageUrl())
                .into(image);

        textViewImage.setText(meal.getMealName());
        initDatePicker();
        dateButton = view.findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());
        selectedDate = getTodaysDate();

        dateButton.setOnClickListener(v -> datePickerDialog.show());

        updateButton.setOnClickListener(v -> {
            Date date = stringToDate(selectedDate);
            if(date != null) {
                MainActivity.mainViewModel.updateMeal(meal.getId(), meal.getMealImageUrl(), date);
                listener.onMealUpdated();
                dismiss();
            }
        });

        return view;
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            String date = makeDateString(day, month, year);
            dateButton.setText(date);
            selectedDate = date;
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = android.R.style.Theme_Material_Dialog;

        datePickerDialog = new DatePickerDialog(getContext(), style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    private Date stringToDate(String aDate) {
        if(aDate == null) return null;
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat("MMM dd yyyy");
        return simpledateformat.parse(aDate, pos);
    }

    public interface UpdateSavedMealDialogListener {
        void onMealUpdated();
    }
}
