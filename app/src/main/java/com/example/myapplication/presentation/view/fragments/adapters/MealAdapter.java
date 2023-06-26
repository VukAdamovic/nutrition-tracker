package com.example.myapplication.presentation.view.fragments.adapters;

import android.graphics.Outline;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.data.models.api.domain.MealFiltered;
import com.example.myapplication.presentation.view.fragments.SingleMealFragment;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder>{

    private List<MealFiltered> mealFilteredList;

    private List<MealFiltered> originalList;

    private FragmentManager fragmentManager;


    public MealAdapter(List<MealFiltered> mealFilteredList, FragmentManager fragmentManager) {
        this.mealFilteredList = mealFilteredList;
        this.fragmentManager = fragmentManager;
        this.originalList = new ArrayList<>(mealFilteredList);
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_element, parent, false);
        return new MealViewHolder(view, fragmentManager);
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

    public void sortAscending() {
        mealFilteredList.sort((MealFiltered o1, MealFiltered o2) -> o1.getName().compareTo(o2.getName()));
        notifyDataSetChanged();
    }

    public void sortDescending() {
        mealFilteredList.sort((MealFiltered o1, MealFiltered o2) -> o2.getName().compareTo(o1.getName()));
        notifyDataSetChanged();
    }

    public void filter(String text) {
        List<MealFiltered> filteredList = new ArrayList<>();
        for (MealFiltered item : originalList) {
            // Adjust the if condition based on what property of MealFiltered you want to compare the text with.
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        mealFilteredList = filteredList; // Assuming 'mealFilteredList' is the list that gets displayed
        notifyDataSetChanged();
    }



    public class MealViewHolder extends RecyclerView.ViewHolder {

        private MealFiltered mealFiltered;



        public MealViewHolder(@NonNull View itemView, FragmentManager fragmentManager) {
            super(itemView);
            itemView.setOnClickListener(v -> {
                SingleMealFragment singleMealFragment = new SingleMealFragment(mealFiltered);
                fragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, singleMealFragment)
                        .addToBackStack(null) // Dodajte fragment na stek povratka
                        .commit();
            });
        }

        public void bind(MealFiltered mealFiltered){
            this.mealFiltered = mealFiltered;
            ImageView imageView = itemView.findViewById(R.id.imageView3);
            TextView mealNameTextView = itemView.findViewById(R.id.textView7);

            imageView.setClipToOutline(true);
            imageView.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    // Use view.getContext().getResources()
                    outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 10 * view.getContext().getResources().getDisplayMetrics().density);
                }
            });

            Glide.with(itemView)
                    .load(mealFiltered.getThumbnail())
                    .into(imageView);
            mealNameTextView.setText(mealFiltered.getName());

        }
    }
}
