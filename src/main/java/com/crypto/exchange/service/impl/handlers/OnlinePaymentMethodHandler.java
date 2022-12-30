package com.crypto.exchange.service.impl.handlers;

import com.crypto.exchange.annotations.BotCommand;
import com.crypto.exchange.service.AbstractBaseHandler;
import com.crypto.exchange.service.KeyboardButtonGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

import static com.crypto.exchange.Command.SET_BANK;

@Slf4j
@Service
@RequiredArgsConstructor
@BotCommand(command = {SET_BANK})
public class OnlinePaymentMethodHandler extends AbstractBaseHandler {

    private final KeyboardButtonGenerator buttonGenerator;

    public void handle(Long userId, Long chatId, Integer messageId, String text) {
        List<List<InlineKeyboardButton>> buttons = buttonGenerator.generateBanksListButtons(chatId);
        publish(SendMessage.builder()
                .text("Please choose desired transaction method")
                .chatId(chatId.toString())
                .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                .build());
    }
}
