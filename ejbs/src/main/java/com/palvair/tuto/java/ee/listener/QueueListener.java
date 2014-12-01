package com.palvair.tuto.java.ee.listener;


import lombok.extern.log4j.Log4j;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 *
 * @author widdy
 */
@Log4j
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/myQueue")
})
public class QueueListener implements MessageListener {

    public QueueListener() {
    }

    @Override
    public void onMessage(Message message) {
        try {
            String body = message.getBody(String.class);
            log.info("body = "+body);
        } catch(JMSException e) {
            e.printStackTrace();
        }

    }

}
