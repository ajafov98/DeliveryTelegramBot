package com.aydinnajafov.TelegramBot.BotMenu;

import com.aydinnajafov.TelegramBot.Model.Cart;
import com.aydinnajafov.TelegramBot.Model.EmojiList;
import com.aydinnajafov.TelegramBot.Model.Food;
import com.aydinnajafov.TelegramBot.Model.Restaurant;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

class MenuButtons {
    private ReplyKeyboardMarkup markup;
    private KeyboardRow row;
    private KeyboardRow row0;
    private KeyboardRow row1;
    private KeyboardRow row2;
    private List<KeyboardRow> rowList;

    ReplyKeyboardMarkup mainMenuKeyboardMarkup() {
        clearVariables();
        row0.add("Make an Order " + EmojiList.MAKE_AN_ORDER_EMOJI);
        row1.add("Settings " + EmojiList.SETTINGS_EMOJI);
        rowList.add(row0);
        rowList.add(row1);
        markup.setKeyboard(rowList);
        markup.setResizeKeyboard(true);
        return markup;
    }

    ReplyKeyboardMarkup makeAnOrderKeyboardMarkup() {
        clearVariables();
        String[] menuItems = {"Select Restaurant \uD83C\uDFE2", "Select Food \uD83E\uDD58",
                "Show Cart \uD83D\uDED2", "Confirm Order \uD83D\uDCDD", "Back \uD83D\uDD19"};

        for (String item : menuItems) {
            row.add(item);
            rowList.add(row);
            row = new KeyboardRow();
        }

        markup.setKeyboard(rowList);
        markup.setResizeKeyboard(true);

        return markup;
    }

    ReplyKeyboardMarkup selectRestaurantKeyboardMarkup(List<Restaurant> restaurants) {
        clearVariables();

        for (Restaurant restaurant : restaurants) {
            if (restaurant.isActive()) {
                row.add(restaurant.getRestaurantName());
            }
            if (row.size() % 3 == 0) {
                rowList.add(row);
                row = new KeyboardRow();
            }

        }
        row.add("Back " + EmojiList.BACK_BUTTON_EMOJI);
        rowList.add(row);

        markup.setKeyboard(rowList).setResizeKeyboard(true);

        return markup;
    }


    ReplyKeyboardMarkup settingsMenuKeyboardMarkup() {
        clearVariables();

        row0.add("About " + EmojiList.ABOUT_EMOJI);
        row1.add("Register Restaurant " + EmojiList.REGISTER_RESTAURANT);
        row2.add("Back " + EmojiList.BACK_BUTTON_EMOJI);

        rowList.add(row0);
        rowList.add(row1);
        rowList.add(row2);

        markup.setKeyboard(rowList);
        markup.setResizeKeyboard(true);

        return markup;

    }

    ReplyKeyboardMarkup aboutMenuKeyboardMarkup() {
        clearVariables();
        row0.add("Back " + EmojiList.BACK_BUTTON_EMOJI);
        rowList.add(row0);

        markup.setKeyboard(rowList);
        markup.setResizeKeyboard(true);

        return markup;
    }

    ReplyKeyboardMarkup itemsOfRestaurantMarkup(Restaurant restaurant) {
        clearVariables();

        row = addFoodToRow(restaurant.getFoodList(), row);
        row.add("Back \uD83D\uDD19");
        rowList.add(row);

        markup.setKeyboard(rowList);
        markup.setResizeKeyboard(true);

        return markup;
    }

    ReplyKeyboardMarkup itemsOfCartMarkup(Cart cart) {
        clearVariables();

        row = addFoodToRow(cart.getFoodList(), row);
        row.add("Back " + EmojiList.BACK_BUTTON_EMOJI);
        rowList.add(row);

        return markup.setKeyboard(rowList).setResizeKeyboard(true);
    }

    ReplyKeyboardMarkup showCartMarkup() {
        clearVariables();

        row.add("Delete " + EmojiList.DELETE_EMOJI);
        row.add("Back " + EmojiList.BACK_BUTTON_EMOJI);
        rowList.add(row);

        return markup.setKeyboard(rowList).setResizeKeyboard(true);
    }

    ReplyKeyboardMarkup registerRestaurantReply() {
        clearVariables();

        row.add("Back " + EmojiList.BACK_BUTTON_EMOJI);
        rowList.add(row);

        return markup.setKeyboard(rowList).setResizeKeyboard(true);
    }


    private void clearVariables() {
        markup = new ReplyKeyboardMarkup();
        row = new KeyboardRow();
        row0 = new KeyboardRow();
        row1 = new KeyboardRow();
        row2 = new KeyboardRow();
        rowList = new ArrayList<>();
    }

    private KeyboardRow addFoodToRow(List<Food> foodList, KeyboardRow row) {
        int i = 0;
        for (Food food : foodList) {
            row.add(food.getName());
            i++;
            if (i % 3 == 0) {
                rowList.add(row);
                row = new KeyboardRow();
            }
        }
        return row;
    }

}
