package vn.edu.nlu.fit.projectweb.controller.cart;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.projectweb.cart.Cart;
import vn.edu.nlu.fit.projectweb.model.Product;
import vn.edu.nlu.fit.projectweb.services.ProductService;

import java.io.IOException;

@WebServlet(name = "AddCart", value = "/add-to-cart")
public class AddCart extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        ProductService ps = new ProductService();
        Product product = ps.getProduct(productId);
        if (product == null) {
            response.sendRedirect("list-products");
            return;
        }
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        cart.addItem(product, quantity);

        response.sendRedirect(request.getHeader("Referer"));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}