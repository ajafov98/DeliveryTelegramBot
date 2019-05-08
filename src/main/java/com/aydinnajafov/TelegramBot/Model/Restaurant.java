package com.aydinnajafov.TelegramBot.Model;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private int restaurantId;
    private String restaurantName;
    private String restaurantAddress;
    private String restaurantPhoneNumber;
    private String restaurantEmail;
    private String restaurantTaxId;
    private float restaurantMinimumOrderAmount;
    private String restaurantWorkingHours;
    private String restaurantAverageDeliveryTime;
    private int restaurantLocationId;
    private long restaurantChatId;
    private String restaurantSecretPassword;
    private List<Food> foodList = new ArrayList<>();
    private boolean isActive;


    public Restaurant(int restaurantId, String restaurantName, String restaurantAddress,
                      String restaurantPhoneNumber, String restaurantEmail, String restaurantTaxId,
                      float restaurantMinimumOrderAmount, String restaurantWorkingHours, String restaurantAverageDeliveryTime,
                      int restaurantLocationId, long restaurantChatId, String restaurantSecretPassword, boolean isActive) {

        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
        this.restaurantPhoneNumber = restaurantPhoneNumber;
        this.restaurantEmail = restaurantEmail;
        this.restaurantTaxId = restaurantTaxId;
        this.restaurantMinimumOrderAmount = restaurantMinimumOrderAmount;
        this.restaurantWorkingHours = restaurantWorkingHours;
        this.restaurantAverageDeliveryTime = restaurantAverageDeliveryTime;
        this.restaurantLocationId = restaurantLocationId;
        this.restaurantChatId = restaurantChatId;
        this.restaurantSecretPassword = restaurantSecretPassword;
        this.isActive = isActive;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public String getRestaurantPhoneNumber() {
        return restaurantPhoneNumber;
    }

    public String getRestaurantEmail() {
        return restaurantEmail;
    }

    public String getRestaurantTaxId() {
        return restaurantTaxId;
    }

    public float getRestaurantMinimumOrderAmount() {
        return restaurantMinimumOrderAmount;
    }

    public String getRestaurantWorkingHours() {
        return restaurantWorkingHours;
    }

    public String getRestaurantAverageDeliveryTime() {
        return restaurantAverageDeliveryTime;
    }

    public int getRestaurantLocationId() {
        return restaurantLocationId;
    }

    public long getRestaurantChatId() {
        return restaurantChatId;
    }

    public List<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }

    public String getRestaurantSecretPassword() {
        return restaurantSecretPassword;
    }

    public boolean isActive() {
        return isActive;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "restaurantId=" + restaurantId +
                ", restaurantName='" + restaurantName + '\'' +
                ", restaurantAddress='" + restaurantAddress + '\'' +
                ", restaurantPhoneNumber='" + restaurantPhoneNumber + '\'' +
                ", restaurantEmail='" + restaurantEmail + '\'' +
                ", restaurantTaxId='" + restaurantTaxId + '\'' +
                ", restaurantMinimumOrderAmount=" + restaurantMinimumOrderAmount +
                ", restaurantWorkingHours='" + restaurantWorkingHours + '\'' +
                ", restaurantAverageDeliveryTime='" + restaurantAverageDeliveryTime + '\'' +
                ", restaurantLocationId=" + restaurantLocationId +
                ", restaurantChatId=" + restaurantChatId +
                ", restaurantSecretPassword='" + restaurantSecretPassword + '\'' +
                ", foodList=" + foodList +
                '}';
    }
}
