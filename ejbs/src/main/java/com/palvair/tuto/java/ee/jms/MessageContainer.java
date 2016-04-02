package com.palvair.tuto.java.ee.jms;

import lombok.Getter;

import javax.ejb.Singleton;
import javax.jms.Message;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by widdy on 02/04/2016.
 */
@Singleton
public class MessageContainer {

    @Getter
    private final List<Message> messages = new ArrayList<>();

    public void addMessage(final Message message) {
        messages.add(message);
    }

    public void clean() {
        messages.clear();
    }
}
