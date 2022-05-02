package com.crypto.exchange.service.impl;

import com.crypto.Currency;
import com.crypto.exchange.CurrencyModeService;
import com.crypto.exchange.annotations.BotCommand;
import com.crypto.exchange.service.AbstractBaseHandler;
import com.crypto.exchange.service.KeyboardButtonGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

import static com.crypto.exchange.Command.*;

@Slf4j
@Service
@RequiredArgsConstructor
@BotCommand(command = {SET_ORIGINAL, SET_TARGET})
public class ChosenCurrencyHandler extends AbstractBaseHandler {

    private final CurrencyModeService currencyModeService;
    private final KeyboardButtonGenerator buttonGenerator;

    public void handle(Long userId, Long chatId, Integer messageId, String text) {
            String[] param = text.split(" ");
            String action = param[0];
            Currency newCurrency = null;
            if (param.length > 1) {
                newCurrency = Currency.valueOf(param[1]);
            }
            switch (action) {
                case "/SET_ORIGINAL" -> currencyModeService.setOriginalCurrency(chatId, newCurrency);
                case "/SET_TARGET" -> currencyModeService.setTargetCurrency(chatId, newCurrency);
                case "DONE" -> publish(SendMessage.builder()
                        .text("Please choose currency to be given/received and enter its quantity")
                        .chatId(chatId.toString())
                        .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttonGenerator.generateChosenCurrenciesButtons(chatId)).build())
                        .build());
            }
            List<List<InlineKeyboardButton>> buttons = buttonGenerator.generateDefaultCurrenciesButtons(chatId);
            publish(EditMessageReplyMarkup.builder()
                    .chatId(chatId.toString())
                    .messageId(messageId)
                    .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                    .build());
    }
}
