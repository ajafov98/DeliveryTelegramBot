package com.aydinnajafov.TelegramBot.Model;

import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Food> foodList = new ArrayList<>();
    private Restaurant restaurant;
    private float latitude;
    private float longitude;

    public float calculateCartCost() {
        float cartCost = 0;
        for (Food food : foodList) {
            cartCost += food.getItemCost();
        }

        return cartCost;
    }

    public void clearCart() {
        foodList.clear();
        restaurant = null;
        latitude = 0;
        longitude = 0;
    }

    public void setUserLocation(Message message) {
        this.latitude = message.getLocation().getLatitude();
        this.longitude = message.getLocation().getLongitude();
    }

    public void addFoodToCart(Food food) {
        foodList.add(food);
    }

    public void deleteFoodFromCart(Food food) {
        foodList.remove(food);
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<Food> getFoodList() {
        return foodList;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

}
