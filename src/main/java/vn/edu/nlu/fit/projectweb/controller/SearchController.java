package vn.edu.nlu.fit.projectweb.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.projectweb.model.Product;
import vn.edu.nlu.fit.projectweb.services.ProductService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "SearchController", value = "/search")
public class SearchController extends HttpServlet {
    private ProductService ps = new ProductService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        String category = request.getParameter("category");
        String brand = request.getParameter("brand");

        // Kiểm tra nếu là AJAX request cho autocomplete
        String isAjax = request.getParameter("ajax");
        if ("true".equals(isAjax) && keyword != null) {
            handleAjaxRequest(keyword, response);
            return;
        }

        List<Product> searchResults;

        if (keyword == null || keyword.trim().isEmpty()) {
            // Nếu không có từ khóa, quay về trang chủ
            response.sendRedirect("ListProduct");
            return;
        }

        keyword = keyword.trim();

        // Xử lý tìm kiếm theo điều kiện
        if (category != null && !category.isEmpty()) {
            try {
                int categoryId = Integer.parseInt(category);
                searchResults = ps.searchProducts(keyword, categoryId);
            } catch (NumberFormatException e) {
                searchResults = ps.searchProducts(keyword);
            }
        } else if (brand != null && !brand.isEmpty()) {
            searchResults = ps.searchProducts(keyword, brand);
        } else {
            searchResults = ps.searchProducts(keyword);
        }

        // Gửi dữ liệu sang JSP
        request.setAttribute("list", searchResults);
        request.setAttribute("searchKeyword", keyword);
        request.setAttribute("resultCount", searchResults.size());

        // Sử dụng file JSP hiện có để hiển thị kết quả
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    private void handleAjaxRequest(String keyword, HttpServletResponse response) throws IOException {
        List<Product> suggestions = ps.getSearchSuggestions(keyword);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < suggestions.size(); i++) {
            Product p = suggestions.get(i);
            json.append("{")
                    .append("\"id\":").append(p.getProductID()).append(",")
                    .append("\"name\":\"").append(escapeJson(p.getProductName())).append("\",")
                    .append("\"img\":\"").append(escapeJson(p.getImg())).append("\",")
                    .append("\"price\":").append(p.getPrice())
                    .append("}");
            if (i < suggestions.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");

        PrintWriter out = response.getWriter();
        out.print(json.toString());
        out.flush();
    }

    private String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}