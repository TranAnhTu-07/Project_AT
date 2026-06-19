package vn.edu.nlu.fit.projectweb.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

import vn.edu.nlu.fit.projectweb.dao.ProductDao;
import vn.edu.nlu.fit.projectweb.dao.ReviewDao;
import vn.edu.nlu.fit.projectweb.model.Reviews;

@WebServlet(name = "ReviewController", value = "/review")
public class ReviewController extends HttpServlet {
    private final ProductDao productDao = new ProductDao();
    private final ReviewDao reviewDao = new ReviewDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("products", productDao.getAll());
        req.setAttribute("reviews", reviewDao.getRecentReviews());

        req.getRequestDispatcher("/html/ProductReview.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        Reviews r = new Reviews();
        r.setProductId(Integer.parseInt(req.getParameter("productId")));
        r.setName(req.getParameter("name"));
        r.setEmail(req.getParameter("Email"));
        r.setStars(Integer.parseInt(req.getParameter("Stars")));
        r.setContent(req.getParameter("review"));

        reviewDao.insert(r);
        resp.sendRedirect("reviews");
    }
}