package com.crypto.exchange.service.impl;

import com.crypto.exchange.annotations.BotCommand;
import com.crypto.exchange.service.AbstractBaseHandler;
import com.crypto.exchange.service.KeyboardButtonGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

import static com.crypto.exchange.Command.EXCHANGE;

@Slf4j
@Service
@RequiredArgsConstructor
@BotCommand(command = EXCHANGE)
public class MessageHandler extends AbstractBaseHandler {

    private final KeyboardButtonGenerator buttonGenerator;

    public void handle(Long userId, Long chatId, Integer messageId, String text) {
        List<List<InlineKeyboardButton>> buttons = buttonGenerator.generateDefaultCurrenciesButtons(chatId);
        publish(SendMessage.builder()
                .text("Please choose Original and Target currencies")
                .chatId(chatId.toString())
                .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                .build());
/*        if (message.hasText() && message.hasEntities()) {
            Optional<MessageEntity> commandEntity = message.getEntities().stream().filter(e -> "bot_command".equals(e.getType())).findFirst();

            if (commandEntity.isPresent()) {
                String command = message.getText().substring(commandEntity.get().getOffset(), commandEntity.get().getLength());

                switch (command) {
                    case "/set_currency" -> {
                        try {


                            SendMessage sendMessage = SendMessage.builder()
                                    .text("Please choose Original and Target currencies")
                                    .chatId(message.getChatId().toString())
                                    .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                                    .build();

                            publisher.publishEvent(new SendMessageCreationEvent(sendMessage));
                        } catch (TelegramApiException e) {
                            log.error("telegram api error {e}");
                        }
                    }

                }
            }
        }*/
/*        if (message.hasText()) {
            String messageText = message.getText();
            Optional<Double> value = parseDouble(messageText);
            Currency originalCurrency = currencyModeService.getOriginalCurrency(message.getChatId());
            Currency targetCurrency = currencyModeService.getTargetCurrency(message.getChatId());
            double ratio = currencyConversionService.getConversionRatio(originalCurrency, targetCurrency);
            if (value.isPresent()) {
                execute(
                        SendMessage.builder()
                                .chatId(message.getChatId().toString())
                                .text(
                                        String.format(
                                                "%4.2f %s is %4.2f %s",
                                                value.get(), originalCurrency, (value.get() * ratio), targetCurrency))
                                .build());
                return;
            }
        }*/
    }
}
