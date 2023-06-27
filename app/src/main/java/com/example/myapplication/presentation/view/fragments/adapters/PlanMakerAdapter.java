package com.example.myapplication.presentation.view.fragments.adapters;

import android.graphics.Outline;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.data.models.api.domain.PlanMeal;
import com.example.myapplication.presentation.view.dialogs.SavedMealDialog;

import java.util.ArrayList;
import java.util.List;

public class PlanMakerAdapter extends  RecyclerView.Adapter<PlanMakerAdapter.PlanMakerViewHolder>{

    private List<PlanMeal> planMeals;

    private Fragment parentFragment;

    public PlanMakerAdapter(List<PlanMeal> planMeals, Fragment parentFragment) {
        this.planMeals = planMeals;
        this.parentFragment = parentFragment;
    }

    @NonNull
    @Override
    public PlanMakerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_meal_element, parent, false);
        return new PlanMakerViewHolder(view, parentFragment);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanMakerViewHolder holder, int position) {
        holder.bind(planMeals.get(position));
    }

    public List<PlanMeal> getPlanMeals() {
        return planMeals;
    }

    public void setPlanMeals(List<PlanMeal> planMeals) {
        this.planMeals = planMeals;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return  planMeals.size();
    }

    public class PlanMakerViewHolder extends RecyclerView.ViewHolder{

        public PlanMakerViewHolder(@NonNull View itemView, Fragment parentFragment) {
            super(itemView);
        }

        public void bind(PlanMeal planMeal){
            ImageView imageView = itemView.findViewById(R.id.imageView7);
            TextView mealNameTextView = itemView.findViewById(R.id.textView53);
            TextView obrokTextView = itemView.findViewById(R.id.textView57);
            TextView kalorijeTextView = itemView.findViewById(R.id.textView58);
            TextView deleteElement = itemView.findViewById(R.id.textView52);

            imageView.setClipToOutline(true);
            imageView.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    // Use view.getContext().getResources()
                    outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 10 * view.getContext().getResources().getDisplayMetrics().density);
                }
            });

            Glide.with(itemView)
                    .load(planMeal.getImageUrl())
                    .into(imageView);

            mealNameTextView.setText(planMeal.getName());
            obrokTextView.setText(planMeal.getMealType());
            kalorijeTextView.setText(String.valueOf(planMeal.getCalories()));

            deleteElement.setOnClickListener(view -> {
                planMeals.remove(planMeal);
                notifyDataSetChanged();
            });
        }
    }

}
