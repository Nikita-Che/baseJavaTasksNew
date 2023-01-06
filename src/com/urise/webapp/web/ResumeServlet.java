package com.urise.webapp.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "ResumeServlet", value = "/resume")
public class ResumeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter printWriter = response.getWriter();

        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/resumes", "postgres", "qw");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM resume");

            while (resultSet.next()) {
                printWriter.println(resultSet.getString("uuid" ));
                printWriter.println(resultSet.getString("full_name" ));
            }
            statement.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
//        String name = request.getParameter("name");
//        String fullName = request.getParameter("fullname");
//        response.getWriter().write(name == null ? "Hello Resumes " : "Hello " + name + " !" + "\n");
//        if (name == null) {
//            response.getWriter().write("Hello Resumes " + "\n");
//        } else {
//            response.getWriter().write("Hello " + name + " !" + "\n");
//        }
//        if(fullName == null) {
//            response.getWriter().write("напиши запрос! ");
//        }
//        response.getWriter().write("Ну у тебя и фаммилия я скажу " + fullName);

//        PrintWriter out = response.getWriter();
//        out.print("<h1>Hello Servlet</h1>");
//        Resume resume = new Resume("Konchen-    ii  ");
//        response.getWriter().write(String.valueOf(resume));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
