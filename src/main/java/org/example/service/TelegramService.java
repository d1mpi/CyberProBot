package org.example.service;

import org.example.config.BotConfig;
import org.example.service.callback.CallbackService;
import org.example.service.command.CommandService;
import org.example.utils.ReturnResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramService extends TelegramLongPollingBot {
    @Autowired
    private UserService userService;
    final BotConfig config;

    public TelegramService(BotConfig config) {
        super(config.getBotToken());
        this.config = config;
    }

    @Override
    public void onUpdateReceived(Update update) {
        // FIXME: Переделать бы это по другому..
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (message) {
                case "/start" -> {
                    ReturnResult value = CommandService.commandStart(update, userService);
                    sendMessage(chatId, value);
                }
                case "/help" -> {
                    ReturnResult value = CommandService.commandHelp();
                    sendMessage(chatId, value);
                }
                default -> sendMessage(chatId, new ReturnResult("Неизвестная команда", null));
            }
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            switch (callbackData) {
                case "userProfile" -> {
                    ReturnResult value = CallbackService.userProfileCallback(update, userService);
                    sendAnswerCallbackQuery("", update.getCallbackQuery());

                    sendMessage(chatId, value);
                }
                case "userInformation" -> {
                    ReturnResult value = CallbackService.userInformationCallback(update, userService);
                    sendAnswerCallbackQuery("", update.getCallbackQuery());

                    sendMessage(chatId, value);
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotUsername();
    }

    private void sendAnswerCallbackQuery(String text, CallbackQuery callbackQuery) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(callbackQuery.getId());
        answerCallbackQuery.setShowAlert(false);
        answerCallbackQuery.setText(text);

        try {
            execute(answerCallbackQuery);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMessage(Long chatId, ReturnResult value) {
        String chatIdStr = String.valueOf(chatId);
        SendMessage sendMessage = new SendMessage(chatIdStr, value.message());

        sendMessage.enableHtml(true);
        sendMessage.setReplyMarkup(value.markup());

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
