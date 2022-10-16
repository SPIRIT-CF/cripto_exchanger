package com.crypto.exchange.service.impl;

import com.crypto.exchange.annotations.BotCommand;
import com.crypto.exchange.domain.Currency;
import com.crypto.exchange.service.AbstractBaseHandler;
import com.crypto.exchange.service.CurrencyModeService;
import com.crypto.exchange.service.KeyboardButtonGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

import static com.crypto.exchange.Command.SET_ORIGINAL;
import static com.crypto.exchange.Command.SET_TARGET;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyQuantityHandler extends AbstractBaseHandler {

    private final CurrencyModeService currencyModeService;
    private final KeyboardButtonGenerator buttonGenerator;

    public void handle(Long userId, Long chatId, Integer messageId, String text) {
        try {
            Double value = Double.valueOf(text.trim());
            Currency originalCurrency = currencyModeService.getOriginalCurrency(chatId);
            Currency targetCurrency = currencyModeService.getTargetCurrency(chatId);
            Currency chosenCurrency = currencyModeService.getChosenCurrency(chatId);

            if (originalCurrency != null && targetCurrency != null && chosenCurrency != null) {

            }
        } catch (NumberFormatException e) {
            log.error("NumberFormatException. Error at parsing currency value");
        }

    }
}
