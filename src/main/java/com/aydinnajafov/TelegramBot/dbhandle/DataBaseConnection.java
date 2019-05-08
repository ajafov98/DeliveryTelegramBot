package com.aydinnajafov.TelegramBot.dbhandle;

import com.aydinnajafov.TelegramBot.Model.DatabaseQueries;
import com.aydinnajafov.TelegramBot.Model.Food;
import com.aydinnajafov.TelegramBot.Model.Restaurant;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.aydinnajafov.TelegramBot.Model.ProjectConstants.*;


public class DataBaseConnection {
    private Connection connection;
    private Statement statement;


    public void startConnection() {
        try {

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connected to database");
            statement = connection.createStatement();
            System.out.println("Statement created");

        } catch (ClassNotFoundException e) {
            System.out.println("Class not found exception");
            e.printStackTrace();
        } catch (SQLException e1) {
            System.out.println("SQL exception");
            e1.printStackTrace();
        }
    }

    public void stopConnection() throws SQLException {

        if (!statement.isClosed()) {
            statement.close();
            System.out.println("Statement closed");
        }

        if (!connection.isClosed()) {
            connection.close();
            System.out.println("Connection closed");
        }

    }

    public List<Restaurant> getRestaurants() {
        List<Restaurant> restaurantList = new ArrayList<>();

        try (ResultSet resultSet = statement.executeQuery(DatabaseQueries.GET_RESTAURANTS_QUERY())) {
            while (resultSet.next()) {
                restaurantList.add(getRestaurantFromDB(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return restaurantList;
    }

    public Boolean setChatIdOfRestaurant(String restaurantName, long id) {
        try {
            statement.executeQuery(DatabaseQueries.SET_RESTAURANT_CHAT_ID(id, restaurantName));
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public Map<String, BigDecimal> getRestaurantLocation(int restaurantId) {
        try (ResultSet resultSet = statement.executeQuery(DatabaseQueries.GET_RESTAURANT_LOCATION_QUERY(restaurantId))) {
            resultSet.first();
            Map<String, BigDecimal> locationMap = new HashMap<>();
            locationMap.put("latitude", resultSet.getBigDecimal("location_latitude"));
            locationMap.put("longitude", resultSet.getBigDecimal("location_longitude"));
            return locationMap;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public long getRestaurantChatId(int restaurantId) {
        try (ResultSet resultSet = statement.executeQuery(DatabaseQueries.GET_RESTAURANT_CHAT_QUERY(restaurantId))) {
            resultSet.first();
            return resultSet.getLong("restaurant_chat_id");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public Restaurant getRestaurantByName(String restaurantName) {
        System.out.println(restaurantName);
        try (ResultSet resultSet = statement.executeQuery(DatabaseQueries.GET_RESTAURANT_BY_NAME(restaurantName))) {
            resultSet.first();
            return getRestaurantFromDB(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Food getFoodByName(String foodName) {
        System.out.println(foodName);
        try (ResultSet resultSet = statement.executeQuery(DatabaseQueries.GET_FOOD_BY_NAME(foodName))) {
            resultSet.first();
            return getFoodFromDB(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Food getFoodByItemId(int itemId) {
        try (ResultSet resultSet = statement.executeQuery(DatabaseQueries.GET_FOOD_BY_ITEM_ID(itemId))) {
            return getFoodFromDB(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<Food> getAllFoodOfRestaurant(int restaurantId) {
        ArrayList<Food> foods = new ArrayList<>();

        try (ResultSet resultSet = statement.executeQuery(DatabaseQueries.GET_ALL_FOOD_BY_RESTAURANT_ID(restaurantId))) {
            while (resultSet.next()) {
                foods.add(getFoodFromDB(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return foods;
    }


    private Food getFoodFromDB(ResultSet resultSet) {
        try {
            int id = resultSet.getInt("item_id");
            String name = resultSet.getString("item_name");
            String itemQuantity = resultSet.getString("item_quantity");
            String itemIngredients = resultSet.getString("item_ingredients");
            float itemCost = resultSet.getFloat("item_cost");
            String foodType = resultSet.getString("item_food_type");
            int restaurantId = resultSet.getInt("item_restaurant_id");
            return new Food(id, name, itemQuantity, itemIngredients, itemCost,
                    foodType, restaurantId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Restaurant getRestaurantFromDB(ResultSet resultSet) {
        try {
            int restaurantId = resultSet.getInt("restaurant_id");
            String restaurantName = resultSet.getString("restaurant_name");
            String address = resultSet.getString("restaurant_address");
            String restaurantPhoneNumber = resultSet.getString("restaurant_phone_number");
            String restaurantEmail = resultSet.getString("restaurant_email");
            String restaurantTaxId = resultSet.getString("restaurant_tax_id");
            float restaurantMinimumOrderAmount = resultSet.getFloat("restaurant_minimum_order_amount");
            String restaurantWorkingHours = resultSet.getString("restaurant_working_hours");
            String restaurantAverageDeliveryTime = resultSet.getString("restaurant_average_delivery_time");
            int restaurantLocationId = resultSet.getInt("restaurant_location_id");
            long restaurantChatId = resultSet.getLong("restaurant_chat_id");
            String restaurantSecretPassword = resultSet.getString("restaurant_secret_password");
            boolean isActive = Boolean.valueOf(resultSet.getString("restaurant_is_active"));
            return new Restaurant(restaurantId, restaurantName, address, restaurantPhoneNumber, restaurantEmail,
                    restaurantTaxId, restaurantMinimumOrderAmount, restaurantWorkingHours, restaurantAverageDeliveryTime, restaurantLocationId,
                    restaurantChatId, restaurantSecretPassword, isActive);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
