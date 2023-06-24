package com.example.myapplication.presentation.view.fragments.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data.models.api.domain.MealFiltered;
import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder>{

    private List<MealFiltered> mealFilteredList;

    private Activity mainActivity;

    public MealAdapter(List<MealFiltered> mealFilteredList, Activity mainActivity) {
        this.mealFilteredList = mealFilteredList;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_element, parent, false);
        return new MealViewHolder(view, mainActivity);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        MealFiltered mealFiltered = mealFilteredList.get(position);
        holder.bind(mealFiltered);
    }

    @Override
    public int getItemCount() {
        return mealFilteredList.size();
    }

    public class MealViewHolder extends RecyclerView.ViewHolder {


        public MealViewHolder(@NonNull View itemView, Activity mainActivity) {
            super(itemView);
        }

        public void bind(MealFiltered mealFiltered){
            ImageView imageView = itemView.findViewById(R.id.imageView3);
            TextView mealNameTextView = itemView.findViewById(R.id.textView7);

            imageView.setImageResource(R.drawable.background);
            mealNameTextView.setText(mealFiltered.getName());
        }
    }
}
