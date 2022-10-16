package com.crypto.exchange.service.impl;

import com.crypto.exchange.domain.Currency;
import com.crypto.exchange.service.CurrencyModeService;
import com.crypto.exchange.annotations.BotCommand;
import com.crypto.exchange.service.AbstractBaseHandler;
import com.crypto.exchange.service.KeyboardButtonGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.crypto.exchange.Command.*;

@Slf4j
@Service
@RequiredArgsConstructor
@BotCommand(command = {SET_ORIGINAL, SET_TARGET})
public class OriginalAndTargetCurrencyHandler extends AbstractBaseHandler {

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
                case "/SET_ORIGINAL" -> validateIsFiat(chatId, newCurrency);
                case "/SET_TARGET" -> currencyModeService.setTargetCurrency(chatId, newCurrency);
            }

            List<Currency> targetCurrencies = getTargetCurrencies(chatId);
            List<List<InlineKeyboardButton>> buttons = buttonGenerator.generateDefaultCurrenciesButtons(chatId, List.of(Currency.values()), targetCurrencies);
            publish(EditMessageReplyMarkup.builder()
                    .chatId(chatId.toString())
                    .messageId(messageId)
                    .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                    .build());
    }

    private List<Currency> getTargetCurrencies(Long chatId) {
        if (currencyModeService.getOriginalCurrency(chatId).isFiat()) return new ArrayList<>(Currency.getCryptoCurrencies());
        else return List.of(Currency.values());
    }

    private void validateIsFiat(Long chatId, Currency newCurrency) {
        currencyModeService.setOriginalCurrency(chatId, newCurrency);
        //if (newCurrency.isFiat()) currencyModeService.deleteFromTarget(chatId, Currency.getFiatCurrencies());
    }

}
