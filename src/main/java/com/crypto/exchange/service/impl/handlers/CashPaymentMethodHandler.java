package com.crypto.exchange.service.impl.handlers;

import com.crypto.exchange.annotations.BotCommand;
import com.crypto.exchange.domain.City;
import com.crypto.exchange.domain.Currency;
import com.crypto.exchange.service.AbstractBaseHandler;
import com.crypto.exchange.service.ExchangeDetailsService;
import com.crypto.exchange.service.KeyboardButtonGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

import static com.crypto.exchange.Command.*;

@Slf4j
@Service
@RequiredArgsConstructor
@BotCommand(command = {SET_COUNTRY, SET_CITY})
public class CashPaymentMethodHandler extends AbstractBaseHandler {

    private final KeyboardButtonGenerator buttonGenerator;

    public void handle(Long userId, Long chatId, Integer messageId, String text) {
        String[] param = text.split(" ");
        String action = param[0];


        switch (action) {
            case ("/SET_COUNTRY") -> publish(SendMessage.builder()
                    .text("Please choose location")
                    .chatId(chatId.toString())
                    .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttonGenerator.generateCountriesButtons()).build())
                    .build());
            case ("/SET_CITY") -> {
                publish(EditMessageReplyMarkup.builder()
                        .chatId(chatId.toString())
                        .messageId(messageId)
                        .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttonGenerator.generateCitiesButtons(param[1])).build())
                        .build());
            }
        }
    }
}
