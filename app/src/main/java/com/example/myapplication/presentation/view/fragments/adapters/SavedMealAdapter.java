package com.example.myapplication.presentation.view.fragments.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data.models.entities.MealEntity;
import com.example.myapplication.presentation.view.dialogs.SavedMealDialog;
import com.example.myapplication.presentation.view.dialogs.UpdateSavedMealDialog;

import java.util.List;

public class SavedMealAdapter extends RecyclerView.Adapter<SavedMealAdapter.SavedMealHolder>{

    private List<MealEntity> mealEntityList;

    private Fragment parentFragment;

    public SavedMealAdapter(List<MealEntity> mealEntityList, Fragment parentFragment) {
        this.mealEntityList = mealEntityList;
        this.parentFragment = parentFragment;
    }

    @NonNull
    @Override
    public SavedMealHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_meal_element, parent, false);
        return new SavedMealHolder(view, parentFragment);
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

        private MealEntity mealEntity;

        public SavedMealHolder(@NonNull View itemView, Fragment parentFragment) {
            super(itemView);

            // Postavljanje klika na cijeli element koristeći lambda izraz
            itemView.setOnClickListener(v -> {
                // Kreiranje instance dijaloga koristeći lambda izraz
                SavedMealDialog dialog = new SavedMealDialog(mealEntity.getId());

                // Prikazivanje dijaloga
                dialog.show(parentFragment.getChildFragmentManager(), "SavedMealDialog");
            });
        }

        public void bind(MealEntity mealEntity){
            this.mealEntity = mealEntity;
            ImageView imageView = itemView.findViewById(R.id.imageView4);
            TextView mealNameTextView = itemView.findViewById(R.id.textView8);
            Button updateBtn = itemView.findViewById(R.id.button5);
            Button deleteBtn = itemView.findViewById(R.id.button6);

            updateBtn.setOnClickListener(v -> {
                UpdateSavedMealDialog dialog = new UpdateSavedMealDialog(mealEntity);

                dialog.show(parentFragment.getChildFragmentManager(), "UpdateSavedMealDialog");
            });

            imageView.setImageResource(R.drawable.background);
            mealNameTextView.setText(mealEntity.getMealName());
        }
    }
}
