package com.aydinnajafov.TelegramBot;

import com.aydinnajafov.TelegramBot.Model.*;
import com.aydinnajafov.TelegramBot.dbhandle.DataBaseConnection;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TelegramBot extends TelegramLongPollingBot {

    private DataBaseConnection dataBaseConnection = new DataBaseConnection();
    private List<Restaurant> restaurantArrayList = new ArrayList<>();
    private Map<Long, User> userList = new HashMap<>();

    @Override
    public void onUpdateReceived(Update update) {


        synchronized (update.getMessage().getChatId()) {
            if (update.hasMessage() && update.getMessage().hasText()) {

                switch (update.getMessage().getText()) {

                    case "/start":
                        try {
                            execute(getUser(update).getMessageBuilder().mainMenuMessage(update));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        break;

                    case "Settings \uD83D\uDEE0":
                        getUser(update).setCurrentMenu("Settings \uD83D\uDEE0");
                        try {
                            execute(getUser(update).getMessageBuilder().settingsMenuMessage(update));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        break;

                    case "About \uD83D\uDCAD":
                        getUser(update).setCurrentMenu("About \uD83D\uDCAD");
                        try {
                            execute(getUser(update).getMessageBuilder().aboutMenuMessage(update));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        break;

                    case "Back \uD83D\uDD19":

                        try {
                            execute(getUser(update).getMessageBuilder().backButtonMessage(update, getUser(update).getCart()));
                        } catch (TelegramApiException e) {
                            System.out.println("Telegram API Exception");
                            e.printStackTrace();
                        }
                        break;

                    case "Make an Order \uD83C\uDF74":

                        getUser(update).setCurrentMenu("Make an Order \uD83C\uDF74");
                        try {
                            getUser(update).getCart().clearCart();
                            execute(getUser(update).getMessageBuilder().makeAnOrderMessage(update));

                        } catch (TelegramApiException e) {
                            System.out.println("Telegram API Exception");
                            e.printStackTrace();
                        }

                        break;

                    case "Select Restaurant \uD83C\uDFE2":
                        try {
                            getUser(update).setCurrentMenu("Select Restaurant");
                            getUser(update).getCart().getFoodList().clear();
                            execute(getUser(update).getMessageBuilder().selectRestaurant(update, restaurantArrayList));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }

                        break;

                    case "Select Food \uD83E\uDD58":
                        if (checkRestaurant(update)) {
                            getUser(update).setCurrentMenu("Select Food");
                            try {
                                execute(getUser(update).getMessageBuilder().getItemsOfRestaurant(update, getUser(update).getCart()));
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        }
                        break;

                    case "Show Cart \uD83D\uDED2":
                        if (checkRestaurant(update)) {
                            getUser(update).setCurrentMenu("Show Cart");
                            try {
                                execute(getUser(update).getMessageBuilder().showCart(update, getUser(update).getCart()));
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        }
                        break;


                    case "Confirm Order \uD83D\uDCDD":
                        if (checkRestaurant(update) && checkCart(update)) {
                            getUser(update).setCurrentMenu("Confirm Order \uD83E\uDD61");
                            SendMessage sendMessage = new SendMessage();
                            sendMessage.setChatId(update.getMessage().getChatId())
                                    .setText("Please send your number and location")
                                    .setReplyMarkup(getNumberOrLocation("number"));
                            try {
                                execute(sendMessage);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        }
                        break;

                    case "Delete \u274C":
                        getUser(update).setCurrentMenu("Delete");

                        try {
                            execute(getUser(update).getMessageBuilder().getItemsOfCart(update, getUser(update).getCart()));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        break;

                    case "Register Restaurant \uD83D\uDCDD":
                        getUser(update).setCurrentMenu("Register Restaurant");
                        try {
                            execute(getUser(update).getMessageBuilder().registerRestaurant(update, restaurantArrayList));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }

                        break;


                    default:
                        switch (getUser(update).getCurrentMenu()) {

                            case "Select Restaurant":
                                getUser(update).getCart().setRestaurant(getRestaurantFromList(update.getMessage()));
                                try {
                                    execute(getUser(update).getMessageBuilder().makeAnOrderMessage(update));
                                } catch (TelegramApiException e) {
                                    e.printStackTrace();
                                }
                                break;

                            case "Select Food":
                                getUser(update).getCart().addFoodToCart(getFoodFromRestaurant(update.getMessage(), update));
                                SendMessage sendMessage = new SendMessage();
                                sendMessage.setChatId(update.getMessage().getChatId())
                                        .setReplyMarkup(getUser(update).getMessageBuilder().getItemsOfRestaurant(update, getUser(update).getCart())
                                                .getReplyMarkup())
                                        .setText("Item: " + update.getMessage().getText() + " added cart \u2611");
                                try {
                                    execute(sendMessage);
                                } catch (TelegramApiException e) {
                                    e.printStackTrace();
                                }
                                break;

                            case "Delete":
                                getUser(update).getCart().deleteFoodFromCart(getFoodFromRestaurant(update.getMessage(), update));
                                SendMessage sendDeleteMessage = new SendMessage();
                                sendDeleteMessage.setChatId(update.getMessage().getChatId()).setText("Item: " + update.getMessage().getText() + " deleted from Cart");
                                try {
                                    execute(sendDeleteMessage);
                                    execute(getUser(update).getMessageBuilder().getItemsOfCart(update, getUser(update).getCart()));
                                } catch (TelegramApiException e) {
                                    e.printStackTrace();
                                }
                                break;

                            case "Register Restaurant":
                                if (setRestaurantChatIdByPassword(update.getMessage())) {
                                    SendMessage sendRegisterMessage = new SendMessage();
                                    sendRegisterMessage.setChatId(update.getMessage().getChatId())
                                            .setText("Your restaurant Registered, " +
                                                    "please contact your coordinator to activate your account \uD83D\uDC97")
                                            .setReplyMarkup(getUser(update).getMessageBuilder().settingsMenuMessage(update).getReplyMarkup());
                                    try {
                                        execute(sendRegisterMessage);
                                    } catch (TelegramApiException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    SendMessage sendRegisterMessage = new SendMessage();
                                    sendRegisterMessage.setChatId(update.getMessage().getChatId())
                                            .setText("Your password not recognized, please contact your coordinator \u2757\uFE0F ")
                                            .setReplyMarkup(getUser(update).getMessageBuilder().settingsMenuMessage(update).getReplyMarkup());
                                    try {
                                        execute(sendRegisterMessage);
                                    } catch (TelegramApiException e) {
                                        e.printStackTrace();
                                    }
                                }
                        }
                        break;
                }

            } else if (update.hasMessage() && update.getMessage().hasContact()) {
                getUser(update).setUser(update.getMessage());
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(update.getMessage().getChatId())
                        .setText("Please provide your location to complete confirmation")
                        .setReplyMarkup(getNumberOrLocation("location"));
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            } else if (update.hasMessage() && update.getMessage().hasLocation()) {
                getUser(update).getCart().setUserLocation(update.getMessage());
                SendMessage confirmationToUser = new SendMessage();
                SendMessage confirmationToRestaurant = new SendMessage();
                SendLocation sendUserLocation = new SendLocation();
                confirmationToUser.setChatId(update.getMessage().getChatId())
                        .setText("Your order is confirmed, you'll be contacted shortly \uD83D\uDE80");
                confirmationToRestaurant.setChatId(getUser(update).getCart().getRestaurant().getRestaurantChatId())
                        .setText(getUser(update).getOrder());
                sendUserLocation.setChatId(getUser(update).getCart().getRestaurant().getRestaurantChatId())
                        .setLatitude(getUser(update).getCart().getLatitude())
                        .setLongitude(getUser(update).getCart().getLongitude());

                try {
                    execute(confirmationToUser);
                    execute(confirmationToRestaurant);
                    execute(sendUserLocation);
                    Order.getInstance().saveOrder(getUser(update));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void updateDatabase() {
        restaurantArrayList = new ArrayList<>();
        dataBaseConnection.startConnection();
        restaurantArrayList.addAll(dataBaseConnection.getRestaurants());
        addFoodByRestaurant();
        try {
            dataBaseConnection.stopConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ReplyKeyboardMarkup getNumberOrLocation(String selection) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        KeyboardButton keyboardButton = new KeyboardButton();

        if (selection.equals("number")) {
            keyboardButton.setRequestContact(true).setText("Send your number ");

        } else if (selection.equals("location")) {
            keyboardButton.setRequestLocation(true).setText("Send your Location");
        }

        row.add(keyboardButton);
        row.add("Back " + EmojiList.BACK_BUTTON_EMOJI);
        rows.add(row);
        return replyKeyboardMarkup.setKeyboard(rows).setResizeKeyboard(true);
    }

    private boolean setRestaurantChatIdByPassword(Message message) {
        for (Restaurant restaurant : restaurantArrayList) {
            if (message.getText().equals(restaurant.getRestaurantSecretPassword())) {
                dataBaseConnection.startConnection();
                return dataBaseConnection.setChatIdOfRestaurant(restaurant.getRestaurantName(), message.getChatId());
            }
        }
        return false;
    }

    private Restaurant getRestaurantFromList(Message message) {
        for (Restaurant restaurant : restaurantArrayList) {
            if (restaurant.getRestaurantName().equals(message.getText())) {
                return restaurant;
            }
        }
        return null;
    }

    private Food getFoodFromRestaurant(Message message, Update update) {
        for (Food food : getUser(update).getCart().getRestaurant().getFoodList()) {
            if (food.getName().equals(message.getText())) {
                return food;
            }
        }
        return null;
    }

    private void addFoodByRestaurant() {
        dataBaseConnection.startConnection();
        for (Restaurant restaurant : restaurantArrayList) {
            for (Food food : dataBaseConnection.getAllFoodOfRestaurant(restaurant.getRestaurantId())) {
                restaurant.getFoodList().add(food);
            }
        }
    }

    private User getUser(Update update) {
        if (userList.containsKey(update.getMessage().getChatId())) {
            return userList.get(update.getMessage().getChatId());
        } else {
            userList.put(update.getMessage().getChatId(),
                    new User(new Cart()));
            return userList.get(update.getMessage().getChatId());
        }
    }

    private boolean checkRestaurant(Update update) {
        if (getUser(update).getCart().getRestaurant() == null) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText("Please select Restaurant!")
                    .setReplyMarkup(getUser(update).getMessageBuilder().makeAnOrderMessage(update).getReplyMarkup())
                    .setChatId(update.getMessage().getChatId());
            try {
                execute(sendMessage);
                return false;
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private boolean checkCart(Update update) {
        if (getUser(update).getCart().getFoodList().size() == 0 || getUser(update).getCart().getFoodList() == null) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText("Please add food to cart!")
                    .setReplyMarkup(getUser(update).getMessageBuilder().makeAnOrderMessage(update).getReplyMarkup())
                    .setChatId(update.getMessage().getChatId());
            try {
                execute(sendMessage);
                return false;
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        return true;
    }


    @Override
    public String getBotUsername() {
        return ProjectConstants.BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return ProjectConstants.BOT_TOKEN;
    }
}
