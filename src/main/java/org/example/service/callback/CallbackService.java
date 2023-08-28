package org.example.service.callback;

import jakarta.persistence.EntityNotFoundException;
import org.example.entity.UserEntity;
import org.example.service.UserService;
import org.example.utils.KeyboardBuilder;
import org.example.utils.ReturnResult;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class CallbackService {
    static public ReturnResult userProfileCallback(Update update, UserService userService) {
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        UserEntity user;

        try {
            user = userService.getUserByTelegramId(chatId);
        } catch (EntityNotFoundException e) {
            return new ReturnResult("<b>Ваш профиль не найден в базе!</b>", null);
        }

        InlineKeyboardMarkup inlineKeyboardMarkup = KeyboardBuilder
                .inline()
                .button("\uD83D\uDCB3 Пополнить баланс", "userProfile")
                .button("✍️ Уроки", "userInformation")
                .row()
                .button("\uD83D\uDC40 Заполнить профиль", "userInformation")
                .row()
                .button("❌ Удалить профиль", "userInformation")
                .build();

        return new ReturnResult(
                String.format(
                        """
                                <b>Доброго времени суток!</b>
                                
                                <b>Ваш уникальный номер:</b> %d
                                <b>Ваш Telegram ID:</b> %s

                                <b>Ваше имя:</b> %s
                                <b>Ваша фамилия:</b> %s
                                <b>Ваше отчество:</b> %s
                                
                                <b>Баланс:</b> %d рублей.""",
                        user.getId(), chatId,
                        user.getFirstName() != null ? user.getFirstName() : "Неизвестно",
                        user.getLastName() != null ? user.getLastName() : "Неизвестно",
                        user.getSurname() != null ? user.getSurname() : "Неизвестно",
                        user.getBalance()
                ),
                inlineKeyboardMarkup
        );
    }

    static public ReturnResult userInformationCallback(Update update, UserService userService) {
        return new ReturnResult(
                """
                             <b>Добро пожаловать на страницу КиберШколы "Новое поколение"</b> 💡
                                                 ⠀
                             <b>КиберШкола</b> превращаем потенциал ребенка в реальные навыки, развивает компетенции будущего по топовым направлениям:
                             ⠀
                             🔹 <b>Робототехника</b>
                             🔹 <b>Программирование</b>
                             🔹 <b>Дизайн и мультипликация</b>
                             🔹 <b>Компьютерная грамотность</b>
                             🔹 <b>Конструирование и моделирование</b>
                             
                             Учим детей программировать, разрабатывать игры, сайты и приложения.
                             Конструировать роботов и технические устройства. Поможем повысить успеваемость по школьной программе 1 - 11 классы. Подготовка к <b>ВПР, ОГЭ, ЕГЭ.</b>
                             
                             Пробуждаем интерес детей и молодежи к техническому, естественно - научному творчеству, к современным IT - технологиям.
                             
                             <b>Наши филиалы:</b>
                             В новом городе: <b>Энтузиастов 20А рядом с художественной школой) 2 этаж</b>
                             📱89185793779
                             @new_generation_vdonsk
                             
                             🏡В старом городе: <b>30 лет Победы 20, 3 этаж, каб 1 (дом быта "Радуга")</b>
                             📱89889400018""",
                null
        );
    }
}
