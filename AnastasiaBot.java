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
       String messageText = update.getMessage().getText();
        String chatId = update.getMessage().getChatId().toString();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        if (messageText.equals("/start")) {
            String startMessage = "Ласкаво просимо до Beauty Salon Bot!\n" +
                    "Будь ласка, оберіть послугу:";
            sendMessage.setText(startMessage);
            sendMessage.setReplyMarkup(getServiceKeyboard());
        } else if (messageText.equalsIgnoreCase("website")) {
            String websiteLink = "https://https://www.instagram.com/aa.ilchenko?igsh=ODd6enp4aXZ3eHg1&utm_source=qr";
            sendMessage.setText("Ось наш веб-сайт: " + websiteLink);
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

    private ReplyKeyboardMarkup getServiceKeyboard() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add("Зачіска");
        row1.add("Стрижка");
        KeyboardRow row2 = new KeyboardRow();
        row2.add("Макіяж");
        row2.add("Манікюр");
        KeyboardRow row3 = new KeyboardRow();
        row3.add("Веб-сайт");

        keyboardRows.add(row1);
        keyboardRows.add(row2);
        keyboardRows.add(row3);

        keyboardMarkup.setKeyboard(keyboardRows);
        return keyboardMarkup;
    }

    private String getServicePrice(String service) {
        switch (service.toLowerCase()) {
            case "зачіска":
                return "Ціна за Зачіску: $50";
            case "стрижка":
                return "Ціна за Стрижку: $30";
            case "макіяж":
                return "Ціна за Макіяж: $40";
            case "манікюр":
                return "Ціна за Манікюр: $20";
            case "веб-сайт":
                return "Ось наш веб-сайт: https://https://www.instagram.com/aa.ilchenko?igsh=ODd6enp4aXZ3eHg1&utm_source=qr";
            default:
                return "Вибачте, ми не надаємо цю послугу.";
        }
    }
}


