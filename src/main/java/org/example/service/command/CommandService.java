package org.example.service.command;

import org.example.entity.UserEntity;
import org.example.service.UserService;
import org.example.utils.KeyboardBuilder;
import org.example.utils.ReturnResult;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class CommandService {
    static public ReturnResult commandStart(Update update, UserService userService) {
        userService.createUser(
                new UserEntity(
                        update.getMessage().getFrom().getUserName(),
                        update.getMessage().getFrom().getId()
                )
        );

        InlineKeyboardMarkup inlineKeyboardMarkup = KeyboardBuilder
                .inline()
                .button("\uD83D\uDC64 Профиль", "userProfile")
                .button("ℹ Информация", "userInformation")
                .row()
                .button("\uD83D\uDDD3 Расписание", "userSchedule")
                .button("\uD83D\uDC76 Мой ребенок", "userMyChild")
                .build();

        return new ReturnResult("<b>Доброго времени суток!</b>", inlineKeyboardMarkup);
    }

    static public ReturnResult commandHelp() {
        return new ReturnResult("Чем я могу Вам помочь ?", null);
    }
}