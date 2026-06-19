package vn.edu.nlu.fit.projectweb.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.projectweb.model.Product;
import vn.edu.nlu.fit.projectweb.services.ProductService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "CanonCompactController", value = "/CanonCompact")
public class CanonCompactController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, java.io.IOException {
        ProductService ps = new ProductService();
        List<Product> list = ps.getListProduct();
        request.setAttribute("list",list);
        request.getRequestDispatcher("html/canon-compact.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}