package vn.edu.nlu.fit.projectweb.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.projectweb.model.Product;
import vn.edu.nlu.fit.projectweb.services.ProductService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CanonMirrorlessController", value = "/Canon")
public class CanonController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid = request.getParameter("cid");
        ProductService ps = new ProductService();
        List<Product> listProduct = new ArrayList<>();

        String categoryName = "MÁY ẢNH CANON";

        if (cid != null) {
            try {
                int id = Integer.parseInt(cid);

                // 1. Lấy danh sách sản phẩm theo ID
                listProduct = ps.getByCategory(id);

                // 2. Đặt tên tiêu đề dựa theo ID (Hardcode tên cho đẹp)
                switch (id) {
                    case 8: categoryName = "MÁY ẢNH CANON COMPACT"; break;
                    case 9: categoryName = "MÁY ẢNH CANON DSLR"; break;
                    case 10: categoryName = "MÁY ẢNH CANON MIRRORLESS"; break;
                }

            } catch (NumberFormatException e) {

                listProduct = ps.getListProduct();
            }
        } else {

            listProduct = ps.getListProduct();
        }

        // 3. Gửi dữ liệu sang JSP
        request.setAttribute("listCanon", listProduct);
        request.setAttribute("catName", categoryName);

        // 4. Chuyển hướng sang file hiển thị (Dùng chung 1 file)
        request.getRequestDispatcher("html/canon.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}