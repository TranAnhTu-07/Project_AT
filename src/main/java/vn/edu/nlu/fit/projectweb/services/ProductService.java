package vn.edu.nlu.fit.projectweb.services;

import vn.edu.nlu.fit.projectweb.dao.ProductDao;
import vn.edu.nlu.fit.projectweb.model.Product;
import vn.edu.nlu.fit.projectweb.model.Reviews;

import java.util.List;

public class ProductService {
    ProductDao pdao = new ProductDao();

    public Product getProduct(int id) {
        return pdao.getProduct(id);
    }
    public List<Product> getListProduct() {
        return pdao.getListProduct();
    }
    public List<Product> getByCategory(int id) {
        return pdao.getByCategory(id);
    }
    public List<Product> getRandomProducts() {
        return pdao.getRandomProducts();
    }

    public Product getProductDetail(int productId) {
        return pdao.getProductById(productId);
    }
    // Phương thức lấy sản phẩm liên quan
    public List<Product> getRelatedProducts(int productId, int categoryId) {
        return pdao.getRelatedProducts(productId, categoryId);
    }
    public void add(Product p) {
        pdao.add(p);
    }

    public void delete(int id) {
        pdao.delete(id);
    }

    public List<Product> searchProducts(String keyword) {
        return pdao.searchProducts(keyword);
    }

    public List<Product> searchProducts(String keyword, int categoryId) {
        return pdao.searchProducts(keyword, categoryId);
    }

    public List<Product> searchProducts(String keyword, String brand) {
        return pdao.searchProducts(keyword, brand);
    }

    public List<Product> getSearchSuggestions(String keyword) {
        return pdao.getSearchSuggestions(keyword, 10);
    }

    public List<Reviews> getReviewByID(int product_id) {
        return pdao.getReviewByID(product_id);
    }

    public int totalProductSold(int id) {
        return pdao.totalProductSold(id);
    }


    public int totalReview(int id) {
        return pdao.totalReview(id);
    }
    public int totalReviewByStar(int stars, int product_id) {
        return pdao.totalReviewByStar(stars, product_id);
    }

    // Hàm gọi DAO để lấy danh sách theo hãng
    public List<Product> getProductsByBrand(String brandName) {
        return pdao.getProductsByBrand(brandName);
    }
    public void publish(int id) {
        pdao.publish(id);
    }
    public void unpublish(int id) {
        pdao.unpublish(id);
    }
    public void update(int id, String name, double price_sale) {
        pdao.update(id, name, price_sale);
    }
}
