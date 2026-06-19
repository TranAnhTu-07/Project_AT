package vn.edu.nlu.fit.projectweb.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.projectweb.dao.ProductDao;
import vn.edu.nlu.fit.projectweb.model.InventoryStats;
import vn.edu.nlu.fit.projectweb.model.Product;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "WarehouseController", value = "/kho")
public class KhoController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String category = request.getParameter("category");
        String status = request.getParameter("status");
        String search = request.getParameter("search");
        String sort = request.getParameter("sort");

        InventoryStats stats = ProductDao.getInventoryStats();
//        List<Product> onSale = ProductDao.getOnSaleProducts(category, status, search, sort);
        List<Product> onSale = ProductDao.getProductsOnKho();


        request.setAttribute("stats", stats);
        request.setAttribute("onSale", onSale);

        request.getRequestDispatcher("html/Warehouse.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}

