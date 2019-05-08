package com.aydinnajafov.TelegramBot.Model;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Order {
    private static final Order instance = new Order();

    private Order() {
        super();
    }

    public void saveOrder(User user) {
        synchronized (this) {
            File log = new File("orders.txt");
            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(log, true), StandardCharsets.UTF_8))) {
                bw.write("Restaurant Name : " + user.getCart().getRestaurant().getRestaurantName());
                bw.newLine();
                bw.write(user.getOrder());
                bw.newLine();
                bw.write("=========================================");
                bw.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Order getInstance() {
        return instance;
    }
}
