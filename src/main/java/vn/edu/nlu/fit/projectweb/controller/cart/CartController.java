package vn.edu.nlu.fit.projectweb.controller.cart;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.projectweb.cart.Cart;
import vn.edu.nlu.fit.projectweb.cart.CartItem;
import vn.edu.nlu.fit.projectweb.model.Product;
import vn.edu.nlu.fit.projectweb.services.ProductService;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@WebServlet(name = "CartController", value = "/cart")
public class CartController extends HttpServlet {
    private ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            // Hiển thị trang giỏ hàng
            showCartPage(request, response);
        } else {
            switch (action) {
                case "view":
                    showCartPage(request, response);
                    break;
                case "count":
                    getCartCount(request, response);
                    break;
                case "total":
                    getCartTotal(request, response);
                    break;
                case "clear":
                    clearCart(request, response);
                    break;
                case "increase":
                    handleIncrease(request, response);
                    break;
                case "decrease":
                    handleDecrease(request, response);
                    break;
                case "remove":
                    handleRemoveFromUrl(request, response);
                    break;
                default:
                    showCartPage(request, response);
            }
        }
    }

    private void handleRemoveFromUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            Cart cart = (Cart) session.getAttribute("cart");

            if (cart != null) {
                cart.delItem(productId);
                session.setAttribute("message", "Đã xóa sản phẩm khỏi giỏ hàng!");
            }

            response.sendRedirect(request.getContextPath() + "/cart");
        } catch (Exception e) {
            session.setAttribute("message", "Có lỗi xảy ra khi xóa sản phẩm!");
            response.sendRedirect(request.getContextPath() + "/cart");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "add":
                    addToCart(request, response);
                    break;
                case "update":
                    updateCartItem(request, response);
                    break;
                case "remove":
                    removeFromCart(request, response);
                    break;
                case "increase":
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/cart");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/cart");
        }
    }

    private void showCartPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        List<CartItem> items = cart.getItems();

        double total = cart.getTotal();
        int totalQuantity = cart.getTotalQuantity();

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        request.setAttribute("cartItems", items);
        request.setAttribute("cartTotal", total);
        request.setAttribute("cartTotalFormatted", currencyFormat.format(total));
        request.setAttribute("totalQuantity", totalQuantity);
        request.setAttribute("shippingFee", 30000); // Phí vận chuyển

        RequestDispatcher dispatcher = request.getRequestDispatcher("/html/ShoppingCart.jsp");
        dispatcher.forward(request, response);
    }

    private void addToCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            if (quantity <= 0) {
                quantity = 1;
            }

            Product product = productService.getProduct(productId);

            if (product != null) {
                HttpSession session = request.getSession();
                Cart cart = (Cart) session.getAttribute("cart");

                if (cart == null) {
                    cart = new Cart();
                    session.setAttribute("cart", cart);
                }

                if (cart.isValidQuantity(product, cart.getTotalQuantity() + quantity)) {
                    cart.addItem(product, quantity);

                    response.setContentType("application/json");
                    PrintWriter out = response.getWriter();
                    out.print("{\"success\": true, \"message\": \"Đã thêm vào giỏ hàng\", \"totalQuantity\": " + cart.getTotalQuantity() + "}");
                } else {
                    response.setContentType("application/json");
                    PrintWriter out = response.getWriter();
                    out.print("{\"success\": false, \"message\": \"Số lượng vượt quá tồn kho\"}");
                }
            } else {
                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                out.print("{\"success\": false, \"message\": \"Sản phẩm không tồn tại\"}");
            }
        } catch (NumberFormatException e) {
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print("{\"success\": false, \"message\": \"Dữ liệu không hợp lệ\"}");
        }
    }

    private void updateCartItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            Product product = productService.getProduct(productId);

            if (product != null) {
                HttpSession session = request.getSession();
                Cart cart = (Cart) session.getAttribute("cart");

                if (cart == null) {
                    cart = new Cart();
                    session.setAttribute("cart", cart);
                }

                if (quantity > 0) {
                    if (cart.isValidQuantity(product, quantity)) {
                        cart.updateItem(product, quantity);
                    } else {
                        int maxQuantity = product.getStock();
                        cart.updateItem(product, maxQuantity);
                        quantity = maxQuantity;
                    }
                } else {
                    cart.delItem(productId);
                }

                double newTotal = cart.getTotal();
                int newTotalQuantity = cart.getTotalQuantity();

                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                out.print("{\"success\": true, \"newQuantity\": " + quantity +
                        ", \"newTotal\": " + newTotal +
                        ", \"newTotalQuantity\": " + newTotalQuantity + "}");
            }
        } catch (NumberFormatException e) {
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print("{\"success\": false, \"message\": \"Dữ liệu không hợp lệ\"}");
        }
    }

    private void removeFromCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int productId = Integer.parseInt(request.getParameter("productId"));

            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute("cart");

            if (cart != null) {
                CartItem removedItem = cart.delItem(productId);

                if (removedItem != null) {
                    // Tính lại tổng
                    double newTotal = cart.getTotal();
                    int newTotalQuantity = cart.getTotalQuantity();

                    response.setContentType("application/json");
                    PrintWriter out = response.getWriter();
                    out.print("{\"success\": true, \"message\": \"Đã xóa sản phẩm\", " +
                            "\"newTotal\": " + newTotal + ", \"newTotalQuantity\": " + newTotalQuantity + "}");
                } else {
                    response.setContentType("application/json");
                    PrintWriter out = response.getWriter();
                    out.print("{\"success\": false, \"message\": \"Sản phẩm không tồn tại trong giỏ hàng\"}");
                }
            }
        } catch (NumberFormatException e) {
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print("{\"success\": false, \"message\": \"Dữ liệu không hợp lệ\"}");
        }
    }

    private void handleIncrease(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute("cart");

            if (cart == null) {
                cart = new Cart();
                session.setAttribute("cart", cart);
            }

            // Tìm sản phẩm
            boolean found = false;
            for (CartItem item : cart.getItems()) {
                if (item.getProduct().getProductID() == productId) {
                    found = true;
                    int oldQty = item.getQuantity();
                    int newQty = oldQty + 1; // TĂNG

                    // Xóa cũ, thêm mới với số lượng mới
                    cart.delItem(productId);
                    cart.addItem(item.getProduct(), newQty);
                    break;
                }
            }

            if (!found) {
                Product product = productService.getProduct(productId);
                if (product != null) {
                    cart.addItem(product, 1);
                }
            }

        } catch (Exception e) {
            // Không làm gì
        }

        response.sendRedirect("cart");
    }

    private void handleDecrease(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute("cart");

            if (cart == null) {
                response.sendRedirect("cart");
                return;
            }

            // Tìm sản phẩm
            for (CartItem item : cart.getItems()) {
                if (item.getProduct().getProductID() == productId) {
                    int oldQty = item.getQuantity();

                    if (oldQty > 1) {
                        int newQty = oldQty - 1; // GIẢM
                        cart.delItem(productId);
                        cart.addItem(item.getProduct(), newQty);
                    } else {
                        cart.delItem(productId);
                    }
                    break;
                }
            }

        } catch (Exception e) {
            // Không làm gì
        }

        response.sendRedirect("cart");
    }

    private void getCartCount(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        int count = 0;
        if (cart != null) {
            count = cart.getTotalQuantity();
        }

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        out.print(count);
    }

    private void getCartTotal(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        double total = 0;
        if (cart != null) {
            total = cart.getTotal();
        }

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        out.print(currencyFormat.format(total));
    }

    private void clearCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart != null) {
            cart.delAll();
        }

        response.sendRedirect(request.getContextPath() + "/cart");
    }
}