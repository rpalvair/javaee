package com.palvair.tuto.java.ee;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnectionFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j;

/**
 * Created by widdy on 02/04/2016.
 */
@Log4j
@WebServlet(name = "JMSSender", urlPatterns = { "/send" })
public class JMSSenderServlet extends HttpServlet {

	@Resource(mappedName = "myQueueFactory")
	private ConnectionFactory queueFactory;

	@Inject
	@JMSConnectionFactory("myQueueFactory")
	private JMSContext queueContext;

	@Resource(mappedName = "myQueue")
	private Queue queue;

	@Resource(mappedName = "otherQueue")
	private Queue otherQueue;

	@Resource(mappedName = "log")
	private Topic topicLog;

	@Resource(mappedName = "topicFactory")
	private TopicConnectionFactory topicFactory;

	private final List<String> messagesSend = new ArrayList<>();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("send.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			final String content = request.getParameter("content");
			if (content != null) {
				messagesSend.add(sendMessage(content, queue));
			} else {
				messagesSend.add(sendMessage("I'm the first message", queue));
				messagesSend.add(sendMessageWithJmsContext("I'm the second message", otherQueue));
			}
			// always log
			sendTopicMessage("message sent", topicLog);

		} catch (JMSException e) {
			log.error(e);
			request.setAttribute("error", e);
		}
		request.setAttribute("messages", messagesSend);
		request.getRequestDispatcher("receive.jsp").forward(request, response);
	}

	private void sendTopicMessage(final String content, final Topic topic) throws JMSException {
		final Connection connection = topicFactory.createConnection();
		final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		final MessageProducer messageProducer = session.createProducer(topic);
		final ObjectMessage message = session.createObjectMessage();
		message.setObject(content);
		messageProducer.send(message);
		messageProducer.close();
		connection.close();
	}

	private String sendMessage(String body, Queue queue) throws JMSException {
		final Connection connection = queueFactory.createConnection();
		final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		final MessageProducer messageProducer = session.createProducer(queue);
		final ObjectMessage message = session.createObjectMessage();
		message.setObject(body);
		messageProducer.send(message);
		messageProducer.close();
		connection.close();
		return body;
	}

	private String sendMessageWithJmsContext(String body, Queue queue) {
		final JMSProducer jmsProducer = queueContext.createProducer();
		final TextMessage textMessage = queueContext.createTextMessage(body);
		jmsProducer.send(queue, textMessage);
		return body;
	}

}
