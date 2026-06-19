package vn.edu.nlu.fit.projectweb.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.projectweb.model.Product;
import vn.edu.nlu.fit.projectweb.services.ProductService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "OngKinhController", value = "/OngKinh")
public class OngKinhController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid = request.getParameter("cid");
        ProductService ps = new ProductService();
        List<Product> listProduct = new ArrayList<>();

        String categoryName = "PHỤ KIỆN MÁY ẢNH";

        if (cid != null) {
            try {
                int id = Integer.parseInt(cid);
                listProduct = ps.getByCategory(id);

                // --- XỬ LÝ TIÊU ĐỀ THEO BRAND ---
                switch (id) {
                    case 21: categoryName = "ỐNG KÍNH CANON"; break;
                    case 22: categoryName = "ỐNG KÍNH SONY"; break;
                    case 23: categoryName = "ỐNG KÍNH NIKON"; break;
                    case 24: categoryName = "ỐNG KÍNH FUJIFILM"; break;
                    case 25: categoryName = "ỐNG KÍNH LUMIX"; break;
                    case 26: categoryName = "ỐNG KÍNH LEICA"; break;
                    case 27: categoryName = "ỐNG KÍNH SIGMA"; break;
                    default: categoryName = "ỐNG KÍNH KHÁC"; break;
                }
                // --------------------------------

            } catch (NumberFormatException e) {
                listProduct = ps.getListProduct();
            }
        } else {
            listProduct = ps.getListProduct();
        }

        request.setAttribute("listLens", listProduct);
        request.setAttribute("catName", categoryName);


        request.getRequestDispatcher("html/ongkinh.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}