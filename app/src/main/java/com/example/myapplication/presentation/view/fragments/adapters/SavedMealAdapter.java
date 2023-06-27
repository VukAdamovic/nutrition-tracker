package com.example.myapplication.presentation.view.fragments.adapters;

import android.graphics.Outline;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.data.models.entities.MealEntity;
import com.example.myapplication.presentation.view.activities.MainActivity;
import com.example.myapplication.presentation.view.dialogs.SavedMealDialog;
import com.example.myapplication.presentation.view.dialogs.UpdateSavedMealDialog;

import java.util.ArrayList;
import java.util.List;

public class SavedMealAdapter extends RecyclerView.Adapter<SavedMealAdapter.SavedMealHolder> implements UpdateSavedMealDialog.UpdateSavedMealDialogListener {

    private List<MealEntity> mealEntityList;

    private Fragment parentFragment;

    private List<MealEntity> originalList;

    public SavedMealAdapter(List<MealEntity> mealEntityList, Fragment parentFragment) {
        this.mealEntityList = mealEntityList;
        this.parentFragment = parentFragment;
        this.originalList = new ArrayList<>(mealEntityList);
    }

    public void filter(String text) {
        List<MealEntity> filteredList = new ArrayList<>();
        for (MealEntity item : originalList) {
            if (item.getMealName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        mealEntityList = filteredList;
        notifyDataSetChanged();
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

    @Override
    public void onMealUpdated() {
        notifyDataSetChanged();
    }

    public class SavedMealHolder extends RecyclerView.ViewHolder{

        private MealEntity mealEntity;

        public SavedMealHolder(@NonNull View itemView, Fragment parentFragment) {
            super(itemView);

            // Postavljanje klika na cijeli element koristeći lambda izraz
            itemView.setOnClickListener(v -> {
                // Kreiranje instance dijaloga koristeći lambda izraz
                SavedMealDialog dialog = new SavedMealDialog(mealEntity);

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
                UpdateSavedMealDialog dialog = new UpdateSavedMealDialog(mealEntity, SavedMealAdapter.this);
                dialog.show(parentFragment.getChildFragmentManager(), "UpdateSavedMealDialog");
            });

            deleteBtn.setOnClickListener(v -> {
                MainActivity.mainViewModel.deleteMeal(mealEntity.getId());
                removeAt(getBindingAdapterPosition());
            });


            imageView.setClipToOutline(true);
            imageView.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    // Use view.getContext().getResources()
                    outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 10 * view.getContext().getResources().getDisplayMetrics().density);
                }
            });

            Glide.with(itemView)
                    .load(mealEntity.getMealImageUrl())
                    .placeholder(R.drawable.background)
                    .into(imageView);

            mealNameTextView.setText(mealEntity.getMealName());
        }

        public void removeAt(int position) {
            if (position != RecyclerView.NO_POSITION) { // Check if item still exists
                mealEntityList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mealEntityList.size());
            }
        }
    }
}
