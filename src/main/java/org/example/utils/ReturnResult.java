package org.example.utils;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public record ReturnResult(String message, InlineKeyboardMarkup markup) { }
