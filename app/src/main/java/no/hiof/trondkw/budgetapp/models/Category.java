package no.hiof.trondkw.budgetapp.models;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Category {

    public static final String HOUSING = "Housing";
    public static final String TRANSPORT = "Transport";
    public static final String FOOD = "Food";
    public static final String SHOPPING = "Shopping";
    public static final String OTHER = "Other";


    private String title;
    private int color;


    public Category() {
    }

    public Category(String title) {
        this.title = title;
    }

    public Category(String title, int color) {
        this.title = title;
        this.color = color;
    }


    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getColor() {
        return color;
    }
    public void setColor(int color) {
        this.color = color;
    }



    // TODO: Move categories to user preferences
    public static ArrayList<Category> getDefaultCategoriesArray() {

        Category housing = new Category(HOUSING, Color.parseColor("#ff857c"));
        Category transport = new Category(TRANSPORT, Color.parseColor("#ffdc78"));
        Category food = new Category(FOOD, Color.parseColor("#b15dff"));
        Category travel = new Category(SHOPPING, Color.parseColor("#72deff"));
        Category other = new Category(OTHER);

        ArrayList< Category> categories;
        categories = new ArrayList<>(Arrays.asList(housing, transport, food, travel, other));

        return categories;
    }

    public static Category getCategory(String category) {

        HashMap<String, Category> defaultCategories = getDefaultCategoriesMap();

        return defaultCategories.get(category);
    }

    public static HashMap<String, Category> getDefaultCategoriesMap() {

        HashMap<String, Category> defaultCategories = new HashMap<>();

        Category housing = new Category(HOUSING, Color.parseColor("#ff857c"));
        Category transport = new Category(TRANSPORT, Color.parseColor("#ffdc78"));
        Category food = new Category(FOOD, Color.parseColor("#b15dff"));
        Category travel = new Category(SHOPPING, Color.parseColor("#72deff"));
        Category other = new Category(OTHER);

        defaultCategories.put(HOUSING, housing);
        defaultCategories.put(TRANSPORT, transport);
        defaultCategories.put(FOOD, food);
        defaultCategories.put(SHOPPING, travel);
        defaultCategories.put(OTHER, other);

        return defaultCategories;
    }

    public static ArrayList<String> getDefaultCategoryTitles() {

        ArrayList<String> categoryTitles;
        categoryTitles = new ArrayList<>(Arrays.asList(HOUSING, TRANSPORT, FOOD, SHOPPING, OTHER));

        return categoryTitles;
    }






} // end Category class
