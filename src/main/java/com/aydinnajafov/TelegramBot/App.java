package com.aydinnajafov.TelegramBot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Timer;
import java.util.TimerTask;

public class App {
    public static void main(String[] args) {

        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        TelegramBot telegramBot = new TelegramBot();
        try {
            telegramBotsApi.registerBot(telegramBot);
            timerTask(telegramBot);
        } catch (TelegramApiException e) {
            e.getMessage();
        }
    }

    // Updates restaurants from database every 24 hours
    private static void timerTask(TelegramBot telegramBot) {
        Timer timer = new Timer();
        TimerTask t = new TimerTask() {
            @Override
            public void run() {
                telegramBot.updateDatabase();
            }
        };

        timer.schedule(t, 0L, 1000 * 86400);
    }
}
