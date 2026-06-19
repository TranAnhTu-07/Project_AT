package vn.edu.nlu.fit.projectweb.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.projectweb.model.Product;
import vn.edu.nlu.fit.projectweb.services.ProductService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "PhuKienController", value = "/PhuKien")
public class PhuKienController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid = request.getParameter("cid");
        ProductService ps = new ProductService();
        List<Product> listProduct = new ArrayList<>();

        String categoryName = "PHỤ KIỆN MÁY ẢNH";

        if (cid != null) {
            try {
                int id = Integer.parseInt(cid);

                // 1. Lấy danh sách sản phẩm theo ID
                listProduct = ps.getByCategory(id);

                // 2. Đặt tên tiêu đề dựa theo ID (Hardcode tên cho đẹp)
                switch (id) {
                    case 28: categoryName = "BAO ĐỰNG MÁY ẢNH"; break;
                    case 29: categoryName = "CHÂN MÁY ẢNH"; break;
                    case 30: categoryName = "THẺ NHỚ MÁY ẢNH"; break;
                    case 31: categoryName = "SẠC MÁY ẢNH"; break;
                    case 32: categoryName = "TỦ CHỐNG ẨM"; break;
                    case 33: categoryName = "ĐÈN FLASH"; break;
                    default: categoryName = "SẢN PHẨM KHÁC"; break;
                }

            } catch (NumberFormatException e) {

                listProduct = ps.getListProduct();
            }
        } else {

            listProduct = ps.getListProduct();
        }

        // 3. Gửi dữ liệu sang JSP
        request.setAttribute("listBao", listProduct);
        request.setAttribute("catName", categoryName);

        // 4. Chuyển hướng sang file hiển thị (Dùng chung 1 file)
        request.getRequestDispatcher("html/baodungmayanh.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
