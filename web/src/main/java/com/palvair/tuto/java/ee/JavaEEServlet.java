package com.palvair.tuto.java.ee;

import com.palvair.tuto.java.ee.dao.J2eeEntityLocal;
import com.palvair.tuto.java.ee.entity.J2eeEntity;
import lombok.extern.log4j.Log4j;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
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
@WebServlet(name = "javaee", urlPatterns = {"/javaee"})
public class JavaEEServlet extends HttpServlet {

    @EJB
    private J2eeEntityLocal j2eeEntityLocal;

    @PostConstruct
    public void setUp() {

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
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
