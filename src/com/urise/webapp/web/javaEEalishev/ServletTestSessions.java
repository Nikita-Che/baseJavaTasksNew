package com.urise.webapp.web.javaEEalishev;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "ServletTestSessions", value = "/servlet1")
public class ServletTestSessions extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String name = request.getParameter("name");
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            cart.setName(name);
            cart.setQuantity(quantity);
        }
        session.setAttribute("cart", cart);

        getServletContext().getRequestDispatcher("/showCart.jsp").forward(request,response);

//        http://localhost:8080/resumes/servlet1?name=Car&quantity=3


//        Integer count = (Integer) session.getAttribute("count");
//        if (count == null) {
//            session.setAttribute("count", 1);
//            count = 1;
//        } else {
//            session.setAttribute("count", count + 1);
//        }
//        PrintWriter printWriter = response.getWriter();
//        printWriter.println("<html>");
//        printWriter.println("<h1> Your count is " + count + "  <h/1>");
//        printWriter.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
