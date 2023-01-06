package com.urise.webapp.web.javaEEalishev;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "ServletTestAvtorization", value = "/ServletTestAvtorization")
public class ServletTestAuthorization extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String user = (String) session.getAttribute("current_user");
        if (user == null) {
//            response для ананоимного пользователя
            //авторизация
            //регистрация
//            session.setAttribute("current_user", ID);
        }else {
//            response для авторизованного пользователя
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
