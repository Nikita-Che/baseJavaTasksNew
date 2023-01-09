<%@ page import="com.urise.webapp.web.javaEEalishev.Cart" %><%--
  Created by IntelliJ IDEA.
  User: nikita
  Date: 06.01.2023
  Time: 15:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Show Cart</title>
</head>
<body>
<%@page import="com.urise.webapp.web.javaEEalishev.Cart" %>

<% Cart cart = (Cart) session.getAttribute("cart"); %>

<p>   Наименование:  <%= cart.getName()%> </p>
<p>   Количество:   <%= cart.getQuantity()%> </p>
</body>
</html>
