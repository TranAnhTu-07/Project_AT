package vn.edu.nlu.fit.projectweb.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import vn.edu.nlu.fit.projectweb.model.Product;
import vn.edu.nlu.fit.projectweb.services.ProductService;
import java.util.List;

import java.io.IOException;

@WebServlet(name = "ProductDetailController", value = "/detail")
public class ProductDetailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // 1. Lấy ID sản phẩm từ URL
            String idParam = request.getParameter("id");

            if (idParam == null || idParam.isEmpty()) {
                // Chuyển về trang chủ nếu không có ID
                response.sendRedirect("ListProduct");
                return;
            }

            int productId = Integer.parseInt(idParam);

            // 2. Lấy thông tin sản phẩm từ database
            ProductService productService = new ProductService();
            Product product = productService.getProductDetail(productId);

            if (product == null) {
                // Nếu không tìm thấy sản phẩm
                response.sendRedirect("ListProduct");
                return;
            }

            // 3. Đặt sản phẩm vào request để hiển thị
            request.setAttribute("product", product);

            // 4. Chuyển đến trang chi tiết sản phẩm
            // File ctsp.jsp nằm trong thư mục html
            request.getRequestDispatcher("/html/ctsp.jsp").forward(request, response);

        } catch (Exception e) {
            // Nếu có lỗi, chuyển về trang chủ
            response.sendRedirect("ListProduct");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}