package com.palvair.tuto.java.ee;

import lombok.extern.log4j.Log4j;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by widdy on 02/04/2016.
 */
@Log4j
@WebServlet(name = "JMSSender", urlPatterns = {"/send"})
public class JMSSenderServlet extends HttpServlet {

    @Resource(mappedName = "myQueueFactory")
    private ConnectionFactory connectionFactory;

    @Inject
    @JMSConnectionFactory("myQueueFactory")
    private JMSContext jmsContext;

    @Resource(mappedName = "myQueue")
    private Queue queue;

    @Resource(mappedName = "otherQueue")
    private Queue otherQueue;

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

        } catch (JMSException e) {
            log.error(e);
            request.setAttribute("error", e);
        }
        request.setAttribute("messages", messagesSend);
        request.getRequestDispatcher("receive.jsp").forward(request, response);
    }

    private String sendMessage(String body, Queue queue) throws JMSException {
        final Connection connection = connectionFactory.createConnection();
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
        final JMSProducer jmsProducer = jmsContext.createProducer();
        final TextMessage textMessage = jmsContext.createTextMessage(body);
        jmsProducer.send(queue, textMessage);
        return body;
    }

}
