package vn.edu.nlu.fit.projectweb.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.projectweb.dao.CategoryDao;
import vn.edu.nlu.fit.projectweb.model.Category;
import vn.edu.nlu.fit.projectweb.model.Product;
import vn.edu.nlu.fit.projectweb.services.ProductService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "AddProductController", value = "/add-product")
public class AddProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CategoryDao cd = new CategoryDao();
        List<Category> parents = cd.getCategoryParent();
        Map<Integer, List<Category>> childrenMap = new HashMap<>();
        for (Category pr : parents) {
            childrenMap.put(pr.getId(),
                    cd.getCategoryChild(pr.getId()));
        }
        request.setAttribute("parents", parents);
        request.setAttribute("children", childrenMap);
//        request.setAttribute("categoryImages", cd.getCategoryImages());
        request.setAttribute("categories", cd.getAllCategoryChild());
        request.getRequestDispatcher("/html/addProduct.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductService ps = new ProductService();
        CategoryDao cd = new CategoryDao();
        String name = request.getParameter("name");
        String img = request.getParameter("img");
        int categoryId = Integer.parseInt(request.getParameter("category_id"));
        String material = request.getParameter("material");
        String size = request.getParameter("size");
        int priceSale = Integer.parseInt(request.getParameter("price_sale"));
        int priceOrigin = Integer.parseInt(request.getParameter("price_origin"));

        Product p = new Product();
        p.setProductName(name);
        p.setImg(img);
        p.setCategoryID(categoryId);
        p.setBrand(p.getBrand());
        p.setNewPrice(priceSale);
        p.setPrice(priceOrigin);
        ps.add(p);

        List<Category> parents = cd.getCategoryParent();
        Map<Integer, List<Category>> childrenMap = new HashMap<>();
        for (Category pr : parents) {
            childrenMap.put(pr.getId(),
                    cd.getCategoryChild(pr.getId()));
        }
        request.setAttribute("parents", parents);
        request.setAttribute("children", childrenMap);
        request.setAttribute("products", ps.getByCategory(categoryId));
        request.setAttribute("category_id", categoryId);
//        request.setAttribute("categoryImages", cd.getCategoryImages());
        request.setAttribute("success", "Đã thêm sản phẩm thành công!");
        request.getRequestDispatcher("/html/addProduct.jsp").forward(request, response);
    }
}
