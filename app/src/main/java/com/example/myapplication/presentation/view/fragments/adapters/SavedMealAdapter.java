package com.example.myapplication.presentation.view.fragments.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data.models.entities.MealEntity;

import java.util.List;

public class SavedMealAdapter extends RecyclerView.Adapter<SavedMealAdapter.SavedMealHolder>{

    private List<MealEntity> mealEntityList;

    public SavedMealAdapter(List<MealEntity> mealEntityList) {
        this.mealEntityList = mealEntityList;
    }

    @NonNull
    @Override
    public SavedMealHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_meal_element, parent, false);
        return new SavedMealHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedMealHolder holder, int position) {
        MealEntity mealEntity = mealEntityList.get(position);
        holder.bind(mealEntity);
    }

    @Override
    public int getItemCount() {
        return mealEntityList.size();
    }

    public class SavedMealHolder extends RecyclerView.ViewHolder{

        public SavedMealHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(MealEntity mealEntity){
            ImageView imageView = itemView.findViewById(R.id.imageView4);
            TextView mealNameTextView = itemView.findViewById(R.id.textView8);
            Button updateBtn = itemView.findViewById(R.id.button5);
            Button deleteBtn = itemView.findViewById(R.id.button6);

            imageView.setImageResource(R.drawable.background);
            mealNameTextView.setText(mealEntity.getMealName());
        }
    }
}
