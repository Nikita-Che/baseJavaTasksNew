<%--
  Created by IntelliJ IDEA.
  User: nikita
  Date: 06.01.2023
  Time: 13:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>First Jsp</title>
</head>
<body>
<h1>Testing JSP</h1>
<p>

<%--        <%--%>
<%--            String requestParameter = request.getParameter("requestFromJsp");--%>
<%--        %>--%>

<%--        <%= "Hello, " + requestParameter %>--%>
<%--        http://localhost:8080/resumes/jspTest?requestFromJsp=vasya--%>

<%--        <%@page import="java.util.Date, com.urise.webapp.jspTesting.Test" %>--%>
<%--        <%Test test = new Test();%>--%>

<%--        <%=--%>
<%--        test.getInfo()--%>
<%--        %>--%>

<%--        <% java.util.Date now = new java.util.Date();--%>
<%--            String someString = "Текущая дата " + now;--%>
<%--        %>--%>

<%--        <%= "<p>" + someString %>--%>

        <%
            for (int i = 0; i < 10; i++) {
                out.println("<p>" + "HER: " + i + "<p>");
            }
        %>
</p>
</body>
</html>
