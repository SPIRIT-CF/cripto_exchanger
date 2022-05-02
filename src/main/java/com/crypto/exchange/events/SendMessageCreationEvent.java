package com.crypto.exchange.events;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class SendMessageCreationEvent extends CreationEvent<SendMessage> {
    public SendMessageCreationEvent(SendMessage object) {
        super(object);
    }
}
