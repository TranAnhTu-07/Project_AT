package vn.edu.nlu.fit.projectweb.controller;
import vn.edu.nlu.fit.projectweb.cart.Cart;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/PrepareCheckout")
public class PrepareCheckoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // 1. LẤY GIỎ HÀNG TỪ SESSION
        Object cartObj = session.getAttribute("cart");

        if (cartObj == null) {
            response.sendRedirect(request.getContextPath() + "/ListProduct");
            return;
        }
        Cart cart = (Cart) cartObj;

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/ListProduct");
            return;
        }
        request.getRequestDispatcher("/html/ttdh.jsp").forward(request, response);
    }
}