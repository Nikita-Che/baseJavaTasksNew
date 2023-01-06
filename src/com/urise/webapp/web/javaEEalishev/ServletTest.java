package com.urise.webapp.web.javaEEalishev;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ServletTest", value = "/servlet")
public class ServletTest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestParameter = request.getParameter("request");
        String requestParameter1 = request.getParameter("request1");

        PrintWriter out = response.getWriter();
        out.println("<h1>Hello " + requestParameter + " " + requestParameter1 + " </h1>");
//        out.println("<h1>Hello " + requestParameter1 + " </h1>");

//        http://localhost:8080/resumes/servlet?request=vasya
//        http://localhost:8080/resumes/servlet?request=vasya&request1=petya

//        response.sendRedirect("https://github.com/Nikita-Che");

        RequestDispatcher dispatcher = request.getRequestDispatcher("testJsp.jsp");
        dispatcher.forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
