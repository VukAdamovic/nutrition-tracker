package com.example.myapplication.presentation.view.fragments.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.data.models.api.domain.Category;
import com.example.myapplication.presentation.view.dialogs.CategoryDialog;
import com.example.myapplication.presentation.view.fragments.HomeFragment;
import com.example.myapplication.presentation.view.fragments.home_fragments.SearchFragment;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private final List<Category> categories;

    private final Fragment parentFragment;

    public CategoryAdapter(List<Category> categories, Fragment parentFragment) {
        this.categories = categories;
        this.parentFragment = parentFragment;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_element, parent, false);
        return new CategoryViewHolder(view, parentFragment);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.bind(category);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        private Category category;

        public CategoryViewHolder(@NonNull View itemView, Fragment parentFragment) {
            super(itemView);

            itemView.setOnClickListener(v -> {
                if (parentFragment instanceof HomeFragment) {
                    ((HomeFragment) parentFragment).replaceFragment(new SearchFragment(category));
                }
            });
        }

        public void bind(Category category) {
            this.category = category;
            ImageView slika = itemView.findViewById(R.id.imageView2);
            TextView textViewName = itemView.findViewById(R.id.textView4);
            Button moreBtn = itemView.findViewById(R.id.button2);

            Glide.with(itemView)
                    .load(category.getThumbnail())
                    .into(slika);
            textViewName.setText(category.getName());

            moreBtn.setOnClickListener(v -> {
                CategoryDialog dialog = new CategoryDialog(category);

                FragmentManager fragmentManager = parentFragment.getChildFragmentManager();
                dialog.show(fragmentManager, "category_dialog");
            });
        }
    }
}
