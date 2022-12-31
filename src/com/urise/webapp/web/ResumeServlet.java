package com.urise.webapp.web;

import com.urise.webapp.sql.SqlHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;

public class ResumeServlet extends HttpServlet {
    SqlHelper sqlHelper;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        sqlHelper.execute("SELECT * FROM resume", preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String uuid = resultSet.getString("uuid");
                String fullName = resultSet.getString("full_name");
                System.out.println(uuid);
                System.out.println(fullName);
            }
            return null;
        });

        // TODO: 31.12.2022 нужнос делать  request к базе данных и response отобразить на экране
//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
//        String name = request.getParameter("name");
//        response.getWriter().write(name == null ? "Hello Resumes" : "Hello " + name + " !");

//        if (name == null) {
//            response.getWriter().write("Hello Resumes");
//        } else {
//            response.getWriter().write("Hello " + name + " !");
//        }
//        Resume resume = new Resume("Konchen-    ii  ");
//        response.getWriter().write(String.valueOf(resume));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
