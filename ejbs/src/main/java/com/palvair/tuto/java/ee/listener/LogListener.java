package com.palvair.tuto.java.ee.listener;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import lombok.extern.log4j.Log4j;

@Log4j
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
		@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "log") })
public class LogListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		try {
			final String content = message.getBody(String.class);
			log.info("content = " + content);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
