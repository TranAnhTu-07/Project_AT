package vn.edu.nlu.fit.projectweb.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import vn.edu.nlu.fit.projectweb.model.Product;
import vn.edu.nlu.fit.projectweb.services.ProductService;

import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "SonyCompactController", value = "/Sony")
public class SonyController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid = request.getParameter("cid");
        ProductService ps = new ProductService();
        List<Product> listProduct = new ArrayList<>();

        String categoryName = "MÁY ẢNH SONY";

        if (cid != null) {
            try {
                int id = Integer.parseInt(cid);

                // 1. Lấy danh sách sản phẩm theo ID
                listProduct = ps.getByCategory(id);

                // 2. Đặt tên tiêu đề dựa theo ID (Hardcode tên cho đẹp)
                switch (id) {
                    case 11: categoryName = "MÁY ẢNH SONY COMPACT"; break;
                    case 12: categoryName = "MÁY ẢNH SONY MIRRORLESS"; break;
                }

            } catch (NumberFormatException e) {

                listProduct = ps.getListProduct();
            }
        } else {

            listProduct = ps.getListProduct();
        }

        // 3. Gửi dữ liệu sang JSP
        request.setAttribute("listSony", listProduct);
        request.setAttribute("catName", categoryName);

        // 4. Chuyển hướng sang file hiển thị (Dùng chung 1 file)
        request.getRequestDispatcher("html/sony.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}