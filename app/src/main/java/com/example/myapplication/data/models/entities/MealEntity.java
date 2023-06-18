package com.example.myapplication.data.models.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(tableName = "meals",  foreignKeys = @ForeignKey(entity = UserEntity.class, parentColumns = "id", childColumns = "user_id"))
public class MealEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String mealName;

    private String mealImageUrl;

    private String instructions;

    private String youTubeLink;

    private List<String> ingredientsMeasurements;

    private String category;

    private Date preparationDate;

    private double calories;

    @ColumnInfo(name = "user_id")
    private int userId;

    public MealEntity(String mealName, String mealImageUrl, String instructions, String youTubeLink, List<String> ingredientsMeasurements, String category, Date preparationDate, double calories, int userId) {
        this.mealName = mealName;
        this.mealImageUrl = mealImageUrl;
        this.instructions = instructions;
        this.youTubeLink = youTubeLink;
        this.ingredientsMeasurements = ingredientsMeasurements;
        this.category = category;
        this.preparationDate = preparationDate;
        this.calories = calories;
        this.userId = userId;
    }
}
