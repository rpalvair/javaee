package com.palvair.tuto.java.ee;

import com.palvair.tuto.java.ee.jms.MessageContainer;
import lombok.extern.log4j.Log4j;

import javax.ejb.EJB;
import javax.jms.JMSException;
import javax.jms.Message;
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
@WebServlet(name = "JMSReceiver", urlPatterns = {"/receive"})
public class JMSReceiverServlet extends HttpServlet {

    @EJB
    private MessageContainer messageContainer;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        final List<String> messages = new ArrayList<>();
        try {
            for (Message message : messageContainer.getMessages()) {
                messages.add(message.getBody(String.class));
            }
        } catch (JMSException e) {
            log.error(e);
        }
        request.setAttribute("messages", messages);
        request.getRequestDispatcher("receive.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
