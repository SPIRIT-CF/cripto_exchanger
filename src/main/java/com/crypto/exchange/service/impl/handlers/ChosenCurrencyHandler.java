package com.crypto.exchange.service.impl.handlers;

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

import java.util.List;

import static com.crypto.exchange.Command.*;

@Slf4j
@Service
@RequiredArgsConstructor
@BotCommand(command = {SET_CHOSEN})
public class ChosenCurrencyHandler extends AbstractBaseHandler {

    private final CurrencyModeService currencyModeService;
    private final KeyboardButtonGenerator buttonGenerator;

    public void handle(Long userId, Long chatId, Integer messageId, String text) {
            String[] param = text.split(" ");
            String action = param[0];
            Currency chosenCurrency = null;
            if (param.length > 1) {
                chosenCurrency = Currency.valueOf(param[1]);
            }
            switch (action) {
                case "/SET_CHOSEN" -> currencyModeService.setChosenCurrency(chatId, chosenCurrency);
            }
            List<List<InlineKeyboardButton>> buttons = buttonGenerator.generateChosenCurrenciesButtons(chatId);
            publish(EditMessageReplyMarkup.builder()
                    .chatId(chatId.toString())
                    .messageId(messageId)
                    .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                    .build());
    }
}
