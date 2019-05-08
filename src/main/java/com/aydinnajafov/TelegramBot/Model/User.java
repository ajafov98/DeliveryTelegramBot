package com.aydinnajafov.TelegramBot.Model;

import com.aydinnajafov.TelegramBot.BotMenu.MessageBuilder;
import org.telegram.telegrambots.meta.api.objects.Message;

public class User {
    private Cart cart;
    private String userName;
    private String userFirstName;
    private String userLastName;
    private String userPhoneNumber;
    private MessageBuilder messageBuilder;
    private String currentMenu;

    public User(Cart cart) {
        this.cart = cart;
        messageBuilder = new MessageBuilder();
    }

    public void setUser(Message message) {
        this.userFirstName = message.getContact().getFirstName();
        this.userLastName = message.getContact().getLastName();
        this.userName = message.getChat().getUserName();
        this.userPhoneNumber = message.getContact().getPhoneNumber();
    }

    public String getOrder() {
        String newLine = System.getProperty("line.separator"); //Universal line separator
        StringBuilder orderBuilder = new StringBuilder();
        orderBuilder.append("New Order!").append(newLine).append("User name: @").append(userName).append(newLine)
                .append("User first name: ").append(userFirstName).append(newLine)
                .append("User last name: ").append(userLastName).append(newLine)
                .append("User Phone number: ").append(userPhoneNumber).append(newLine);
        for (Food food : cart.getFoodList()) {
            orderBuilder.append("Food Name: ").append(food.getName()).append(newLine).append("Food cost: ")
                    .append(food.getItemCost()).append(newLine);
        }
        orderBuilder.append("Total Cost of order: ").append(cart.calculateCartCost());
        return orderBuilder.toString();
    }


    public Cart getCart() {
        return cart;
    }


    public MessageBuilder getMessageBuilder() {
        return messageBuilder;
    }

    public String getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(String currentMenu) {
        this.currentMenu = currentMenu;
    }
}
