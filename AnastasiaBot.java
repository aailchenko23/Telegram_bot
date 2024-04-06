package com.example.javabot.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;


@Component
public class AnastasiaBot extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return "a_ilchenko_bot";
    }

    @Override
    public String getBotToken() {
        return "7178099462:AAHBKyp-DW4I3sHBjBxo7T7kh9r_BUNr7d8";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            if (messageText.equals("/start")) {
                String startMessage = "Welcome to Beauty Salon Bot!\n" +
                        "Please select a service:";
                sendMessage.setText(startMessage);
                sendMessage.setReplyMarkup(getServiceKeyboard());
            } else if (messageText.equalsIgnoreCase("website")) {
                String websiteLink = "https://https://www.instagram.com/aa.ilchenko?igsh=ODd6enp4aXZ3eHg1&utm_source=qr";
                sendMessage.setText("Here is our website: " + websiteLink);
            } else {
                String reply = getServicePrice(messageText);
                sendMessage.setText(reply);
            }

            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private ReplyKeyboardMarkup getServiceKeyboard() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add("Hairstyle");
        row1.add("Haircut");
        KeyboardRow row2 = new KeyboardRow();
        row2.add("Makeup");
        row2.add("Manicure");
        KeyboardRow row3 = new KeyboardRow();
        row3.add("Website");

        keyboardRows.add(row1);
        keyboardRows.add(row2);
        keyboardRows.add(row3);

        keyboardMarkup.setKeyboard(keyboardRows);
        return keyboardMarkup;
    }

     private  String getServicePrice(String service) {
        switch (service.toLowerCase()) {
            case "hairstyle":
                return "Price for Hairstyle: $50";
            case "haircut":
                return "Price for Haircut: $30";
            case "makeup":
                return "Price for Makeup: $40";
            case "manicure":
                return "Price for Manicure: $20";
            case "website":
                return "Here is our website: https://https://www.instagram.com/aa.ilchenko?igsh=ODd6enp4aXZ3eHg1&utm_source=qr";
            default:
                return "Sorry, we don't offer this service.";
        }
    }
}


