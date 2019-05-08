package com.aydinnajafov.TelegramBot.BotMenu;

import com.aydinnajafov.TelegramBot.Model.Cart;
import com.aydinnajafov.TelegramBot.Model.EmojiList;
import com.aydinnajafov.TelegramBot.Model.Restaurant;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public class MessageBuilder {
    private SendMessage sendMessage;
    private MenuButtons menuButtons = new MenuButtons();
    private MenuReplies menuReplies = new MenuReplies();
    private String lastChosenMenu = "";

    public SendMessage mainMenuMessage(Update update) {
        sendMessage = new SendMessage();

        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText("Welcome " + EmojiList.HUG_EMOJI);
        sendMessage.setReplyMarkup(menuButtons.mainMenuKeyboardMarkup());

        return sendMessage;
    }

    public SendMessage settingsMenuMessage(Update update) {
        sendMessage = new SendMessage();

        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText("Make a choice");
        sendMessage.setReplyMarkup(menuButtons.settingsMenuKeyboardMarkup());
        lastChosenMenu = "Settings \uD83D\uDEE0";

        return sendMessage;
    }

    public SendMessage aboutMenuMessage(Update update) {
        sendMessage = new SendMessage();

        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText("Developed by Aydin Najafov \n Baku 2019");
        sendMessage.setReplyMarkup(menuButtons.aboutMenuKeyboardMarkup());
        lastChosenMenu = "About \uD83D\uDCAD";

        return sendMessage;
    }

    public SendMessage makeAnOrderMessage(Update update) {
        sendMessage = new SendMessage();

        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setReplyMarkup(menuButtons.makeAnOrderKeyboardMarkup());
        sendMessage.setText("Please make a choice");
        lastChosenMenu = "Make an Order \uD83C\uDF74";

        return sendMessage;
    }

    public SendMessage selectRestaurant(Update update, List<Restaurant> restaurants) {
        sendMessage = new SendMessage();

        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText(menuReplies.selectRestaurantReply(restaurants))
                .setReplyMarkup(menuButtons.selectRestaurantKeyboardMarkup(restaurants));
        lastChosenMenu = "selectRestaurant";

        return sendMessage;
    }

    public SendMessage showCart(Update update, Cart cart) {
        sendMessage = new SendMessage();

        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText(menuReplies.itemsOfCartReply(cart))
                .setReplyMarkup(menuButtons.showCartMarkup());
        lastChosenMenu = "showCart";

        return sendMessage;

    }

    public SendMessage getItemsOfRestaurant(Update update, Cart cart) {
        sendMessage = new SendMessage();

        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setReplyMarkup(menuButtons
                .itemsOfRestaurantMarkup(cart.getRestaurant()));
        sendMessage.setText(menuReplies
                .itemsOfRestaurantReply(cart.getRestaurant()));
        lastChosenMenu = "restaurant";

        return sendMessage;
    }

    public SendMessage getItemsOfCart(Update update, Cart cart) {
        sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setReplyMarkup(menuButtons.itemsOfCartMarkup(cart));
        sendMessage.setText(menuReplies.itemsOfCartReply(cart));
        lastChosenMenu = "ItemsOfCart";
        return sendMessage;
    }

    public SendMessage registerRestaurant(Update update, List<Restaurant> restaurants) {
        sendMessage = new SendMessage();

        sendMessage.setChatId(update.getMessage().getChatId())
                .setText("Please provide your secret password")
                .setReplyMarkup(menuButtons.registerRestaurantReply());
        lastChosenMenu = "registerRestaurant";

        return sendMessage;
    }

    public SendMessage backButtonMessage(Update update, Cart cart) {
        sendMessage = new SendMessage();

        sendMessage.setChatId(update.getMessage().getChatId());
        switch (lastChosenMenu) {

            case "About \uD83D\uDCAD":
                sendMessage.setText("Make a choice").setReplyMarkup(menuButtons.settingsMenuKeyboardMarkup());
                lastChosenMenu = "Settings \uD83D\uDEE0";
                break;

            case "Settings \uD83D\uDEE0":
                sendMessage.setText("Make a choice").setReplyMarkup(menuButtons.mainMenuKeyboardMarkup());
                break;

            case "Make an Order \uD83C\uDF74":
                sendMessage.setText("Make a choice").setReplyMarkup(menuButtons.mainMenuKeyboardMarkup());
                break;

            case "restaurant":
                sendMessage = makeAnOrderMessage(update);
                break;

            case "selectRestaurant":
                sendMessage = makeAnOrderMessage(update);
                break;

            case "showCart":
                sendMessage = makeAnOrderMessage(update);
                break;

            case "ItemsOfCart":
                sendMessage = showCart(update, cart);
                break;

            case "registerRestaurant":
                sendMessage = settingsMenuMessage(update);
                break;

            case "Confirm Order \uD83E\uDD61":
                sendMessage = makeAnOrderMessage(update);
                break;

            default:
                sendMessage = new SendMessage();
                sendMessage.setChatId(update.getMessage().getChatId()).setText("Make a choice").setReplyMarkup(menuButtons.mainMenuKeyboardMarkup());
                break;
        }

        return sendMessage;
    }


}
