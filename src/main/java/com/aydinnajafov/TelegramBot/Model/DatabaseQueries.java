package com.aydinnajafov.TelegramBot.Model;

public class DatabaseQueries {

    private DatabaseQueries() {
    }

    public static String GET_RESTAURANTS_QUERY() {
        return "select * from restaurants_table;";

    }

    public static String GET_RESTAURANT_QUERY(int restaurantId) {
        return "select * from restaurants_table where restaurant_id = "
                + restaurantId + ";";
    }

    public static String GET_RESTAURANT_LOCATION_QUERY(int restaurantId) {
        return "select location_latitude, location_longitude from locations_table where location_restaurant_id = "
                + restaurantId + ";";
    }

    public static String GET_RESTAURANT_BY_NAME(String restaurantName) {
        return "select * from restaurants_table where restaurant_name = \'"
                + restaurantName + "\';";
    }

    public static String GET_FOOD_BY_NAME(String foodName) {
        return "select * from items_table where item_name = \'" + foodName + "\';";
    }

    public static String SET_RESTAURANT_CHAT_ID(long chatId, String restaurantName) {
        return "update restaurants_table set restaurant_chat_id = " + chatId + " where restaurant_name = \'"
                + restaurantName + "\';";
    }


    public static String GET_RESTAURANT_CHAT_QUERY(int restaurantId) {
        return "select restaurant_chat_id from restaurants_table where restaurant_id = "
                + restaurantId + ";";
    }

    public static String GET_FOOD_BY_ITEM_ID(int itemId) {
        return "select * from items_table where item_id = " + itemId + ";";
    }

    public static String GET_ALL_FOOD_BY_RESTAURANT_ID(int restaurantId) {
        return "select * from items_table where item_restaurant_id = "
                + restaurantId + ";";
    }


}
