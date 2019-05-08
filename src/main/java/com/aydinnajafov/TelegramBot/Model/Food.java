package com.aydinnajafov.TelegramBot.Model;

public class Food {
    private int id;
    private String name;
    private String itemQuantity;
    private String itemIngredients;
    private float itemCost;
    private String foodType;
    private int restaurantId;

    public Food(int id, String name, String itemQuantity, String itemIngredients, float itemCost, String foodType, int restaurantId) {
        this.id = id;
        this.name = name;
        this.itemQuantity = itemQuantity;
        this.itemIngredients = itemIngredients;
        this.itemCost = itemCost;
        this.foodType = foodType;
        this.restaurantId = restaurantId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public String getItemIngredients() {
        return itemIngredients;
    }

    public float getItemCost() {
        return itemCost;
    }

    public String getFoodType() {
        return foodType;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", itemQuantity='" + itemQuantity + '\'' +
                ", itemIngredients='" + itemIngredients + '\'' +
                ", itemCost=" + itemCost +
                ", foodType='" + foodType + '\'' +
                ", restaurantId=" + restaurantId +
                '}';
    }
}
