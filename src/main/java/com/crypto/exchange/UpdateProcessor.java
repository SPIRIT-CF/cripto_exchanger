package com.crypto.exchange;

import com.crypto.exchange.events.CreationEvent;
import com.crypto.exchange.events.UpdateCreationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;


@Component
@Slf4j
@RequiredArgsConstructor
public class UpdateProcessor {

    @EventListener(classes = {UpdateCreationEvent.class})
    public void handleUpdate(CreationEvent<Update> updateCreationEvent) {
        final Update update = updateCreationEvent.get();
        Long userId = null;
        String text = null;

        if (isMessageWithText(update)) {
            final Message message = update.getMessage();
            userId = message.getFrom().getId();
            text = message.getText();
            log.debug("Update is text message {} from {}", text, userId);
        } else if (update.hasCallbackQuery()) {
            final CallbackQuery callbackQuery = update.getCallbackQuery();
            userId = callbackQuery.getFrom().getId();
            text = callbackQuery.getData();
            log.debug("Update is callbackQuery {} from {}", text, userId);
        }

       /* if (text != null && userId != null) {
            //orchestrator.operate(userId, text);
        }*/
    }

    private boolean isMessageWithText(Update update) {
        return !update.hasCallbackQuery() && update.hasMessage() && update.getMessage().hasText();
    }
}
