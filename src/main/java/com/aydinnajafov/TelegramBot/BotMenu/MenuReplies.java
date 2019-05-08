package com.aydinnajafov.TelegramBot.BotMenu;

import com.aydinnajafov.TelegramBot.Model.Cart;
import com.aydinnajafov.TelegramBot.Model.Food;
import com.aydinnajafov.TelegramBot.Model.Restaurant;

import java.util.List;


class MenuReplies {
    ;

    String selectRestaurantReply(List<Restaurant> restaurants) {
        StringBuilder reply = new StringBuilder();
        reply.append("Please select Restaurant\n\n");
        int id = 1;
        for (Restaurant restaurant : restaurants) {
            if (restaurant.isActive()) {
                reply.append(id).append(". \n\n").append("\uD83C\uDFF7 Name: ").append(restaurant.getRestaurantName())
                        .append("\n\uD83D\uDCCC Address : ").append(restaurant.getRestaurantAddress())
                        .append("\n\uD83D\uDCE6 Minimum order amount : ").append(restaurant.getRestaurantMinimumOrderAmount()).append(" AZN")
                        .append("\n\uD83D\uDD70 Working hours: ").append(restaurant.getRestaurantWorkingHours()).append("\n\n");
                id++;
            }
        }
        return reply.toString();
    }

    String itemsOfRestaurantReply(Restaurant restaurant) {
        StringBuilder reply = new StringBuilder();
        reply.append("Please select Food\n\n");
        return getFoodFromList(reply, restaurant.getFoodList());
    }

    String itemsOfCartReply(Cart cart) {
        StringBuilder reply = new StringBuilder();
        reply.append("Food in Cart:\n\n");
        return getFoodFromList(reply, cart.getFoodList()).concat("\n").concat("\uD83D\uDCB0 Total: " + cart.calculateCartCost());
    }

    private String getFoodFromList(StringBuilder stringBuilder, List<Food> foodList) {
        int id = 1;
        for (Food food : foodList) {
            stringBuilder.append(id).append(". \n\n").append("\uD83C\uDFF7 Name: ").append(food.getName()).append("\n\uD83C\uDF62 Food type: ")
                    .append(foodTypeIdentifier(food)).append("\n\uD83E\uDDFE Ingredients: ")
                    .append(food.getItemIngredients()).append("\n\uD83D\uDCB5 Cost: ")
                    .append(food.getItemCost()).append("\n\n");
            id++;
        }
        return stringBuilder.toString();
    }

    private String foodTypeIdentifier(Food food) {
        switch (food.getFoodType()) {
            case ("Drinks"):
                return food.getFoodType() + " \uD83E\uDD64";
            case ("Hot Dishes"):
                return food.getFoodType() + " \uD83C\uDF72";
            case ("Cold Dishes"):
                return food.getFoodType() + " \uD83E\uDD59";
            case ("Snacks"):
                return food.getFoodType() + " \uD83C\uDF6B";
            case ("Fast Food"):
                return food.getFoodType() + " \uD83C\uDF55";
        }
        return food.getFoodType();
    }


}
