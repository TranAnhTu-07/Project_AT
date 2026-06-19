package vn.edu.nlu.fit.projectweb.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.projectweb.model.Product;
import vn.edu.nlu.fit.projectweb.services.ProductService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ListProductController", value = "/ListProduct")
public class ListProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String brand = request.getParameter("brand");
        ProductService ps = new ProductService();
        List<Product> list;
        if (brand != null && !brand.isEmpty()) {
            list = ps.getProductsByBrand(brand);
            request.setAttribute("currentBrand", brand);
        } else {
            list = ps.getRandomProducts();
        }
        request.setAttribute("list", list);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}