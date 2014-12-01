package com.palvair.tuto.java.ee;

import com.palvair.tuto.java.ee.dao.J2eeEntityLocal;
import com.palvair.tuto.java.ee.entity.J2eeEntity;
import lombok.extern.log4j.Log4j;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by widdy on 09/09/14.
 */
@Stateless
@Log4j
@WebServlet(name = "J2ee", urlPatterns = {"/J2EE"})
public class J2EEServlet extends HttpServlet {

    @Resource(mappedName = "jms/myQueueFactory")
    private ConnectionFactory connectionFactory;

    @Resource(mappedName = "jms/myQueue")
    private Queue queue;

    @Resource(mappedName = "jms/otherQueue")
    private Queue otherQueue;

    //@Inject
    //@JMSConnectionFactory("jms/myQueueFactory")
    //private JMSContext jmsContext;

    @EJB
    private J2eeEntityLocal j2eeEntityLocal;

    @PostConstruct
    public void setUp() {

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            sendMessage("Hello QueueListener", queue);
            sendMessage("Hello OtherListener", otherQueue);
            //sendMessageWithJmsContext("Hello QueueListener(sended by JMSContext)", queue);
        } catch (JMSException ex) {
            ex.printStackTrace();
            throw new ServletException(ex.getMessage());
        }

        log.info("call create");
        J2eeEntity j2eeEntity = new J2eeEntity();
        j2eeEntity.setName("test");
        j2eeEntityLocal.create(j2eeEntity);
        log.info("creation done");

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>J2EE Servlet</title>");
            out.println("<body>");
            out.println("<h1>Hello</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private void sendMessage(String body, Queue queue) throws JMSException {
        final Connection connection = connectionFactory.createConnection();
        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        final MessageProducer messageProducer = session.createProducer(queue);
        final ObjectMessage message = session.createObjectMessage();
        message.setObject(body);
        messageProducer.send(message);
        messageProducer.close();
        connection.close();
        log.info("message sent");
    }

    /*private void sendMessageWithJmsContext(String body, Queue queue) {
        final JMSProducer jmsProducer = jmsContext.createProducer();
        final TextMessage textMessage = jmsContext.createTextMessage(body);
        jmsProducer.send(queue, textMessage);
    }*/

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
