package com.palvair.tuto.java.ee;

import lombok.extern.log4j.Log4j;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Created by widdy on 09/09/14.
 */
@Log4j
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/otherQueue")
})
public class OtherListener implements MessageListener {

    @Inject
    private JMSContext jmsContext;

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
