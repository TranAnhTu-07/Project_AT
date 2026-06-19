package vn.edu.nlu.fit.projectweb.dao;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.Batch;
import org.jdbi.v3.core.statement.PreparedBatch;
import vn.edu.nlu.fit.projectweb.model.InventoryStats;
import vn.edu.nlu.fit.projectweb.model.Product;
import vn.edu.nlu.fit.projectweb.model.Reviews;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDao extends BaseDao {
    static Map<Integer, Product> data = new HashMap<>();

    //    static {
//        data.put(1,new Product(1,"Máy Ảnh Sony RX1R III | Chính Hãng","https://bizweb.dktcdn.net/100/107/650/products/8783339-sony-rx1riii-16.jpg?v=1752639489140",125500000));
//        data.put(2,new Product(1,"Máy Ảnh Sony RX1R III | Chính Hãng","https://bizweb.dktcdn.net/100/107/650/products/8783339-sony-rx1riii-16.jpg?v=1752639489140",125500000));
//        data.put(3,new Product(1,"Máy Ảnh Sony RX1R III | Chính Hãng","https://bizweb.dktcdn.net/100/107/650/products/8783339-sony-rx1riii-16.jpg?v=1752639489140",125500000));
//        data.put(4,new Product(1,"Máy Ảnh Sony RX1R III | Chính Hãng","https://bizweb.dktcdn.net/100/107/650/products/8783339-sony-rx1riii-16.jpg?v=1752639489140",125500000));
//        data.put(5,new Product(1,"Máy Ảnh Sony RX1R III | Chính Hãng","https://bizweb.dktcdn.net/100/107/650/products/8783339-sony-rx1riii-16.jpg?v=1752639489140",125500000));
//        data.put(6,new Product(1,"Máy Ảnh Sony RX1R III | Chính Hãng","https://bizweb.dktcdn.net/100/107/650/products/8783339-sony-rx1riii-16.jpg?v=1752639489140",125500000));
//        data.put(7,new Product(1,"Máy Ảnh Sony RX1R III | Chính Hãng","https://bizweb.dktcdn.net/100/107/650/products/8783339-sony-rx1riii-16.jpg?v=1752639489140",125500000));
//        data.put(8,new Product(1,"Máy Ảnh Sony RX1R III | Chính Hãng","https://bizweb.dktcdn.net/100/107/650/products/8783339-sony-rx1riii-16.jpg?v=1752639489140",125500000));
//        data.put(9,new Product(1,"Máy Ảnh Sony RX1R III | Chính Hãng","https://bizweb.dktcdn.net/100/107/650/products/8783339-sony-rx1riii-16.jpg?v=1752639489140",125500000));
//        data.put(10,new Product(1,"Máy Ảnh Sony RX1R III | Chính Hãng","https://bizweb.dktcdn.net/100/107/650/products/8783339-sony-rx1riii-16.jpg?v=1752639489140",125500000));
//
//    }
    public List<Product> getAll() {
        String sql = "SELECT ProductID, ProductName FROM products";

        return get().withHandle(handle ->
                handle.createQuery(sql)
                        .mapToBean(Product.class)
                        .list()
        );
    }

    public List<Product> getListProduct() {
        return get().withHandle(h -> h.createQuery("select * from products").mapToBean(Product.class).list());
    }

    public Product getProduct(int id) {
        return get().withHandle(h -> h.createQuery("select * from products where ProductID = :id").bind("id", id).mapToBean(Product.class).stream().findFirst().orElse(null));
    }

    //    public void insert(List<Product> products) {
//        Jdbi jdbi = get();
//        jdbi.useHandle(h->{
//            PreparedBatch batch = h.prepareBatch("insert into products values(:id, :name,:img,:price)");
//            products.forEach(product -> {
//                batch.bindBean(product).add();
//            });
//            batch.execute();
//        });
//
//    }
    public List<Product> getByCategory(int categoryID) {
        return get().withHandle(h ->
                h.createQuery("SELECT * FROM products WHERE categoryID = :cid")
                        .bind("cid", categoryID)
                        .mapToBean(Product.class)
                        .list()
        );
    }

    public List<Product> getRandomProducts() {
        return get().withHandle(h ->
                h.createQuery("SELECT * FROM products ORDER BY RAND() LIMIT 30")
                        .mapToBean(Product.class)
                        .list()
        );
    }
    // Hàm lấy sản phẩm theo thương hiệu
    public List<Product> getProductsByBrand(String brandName) {
        return get().withHandle(handle -> {
            // Cột Brand của m viết hoa chữ B, nhớ chú ý
            String sql = "SELECT * FROM products WHERE Brand = :brandName";

            return handle.createQuery(sql)
                    .bind("brandName", brandName)
                    .mapToBean(Product.class)
                    .list();
        });
    }

    public static void main(String[] args) {
        ProductDao dao = new ProductDao();
        // Test thử lấy danh mục 28 (Bao đựng)
        List<Product> list = dao.getByCategory(28);
        for (Product p : list) {
            System.out.println(p.getProductName());
        }
    }

    // Phương thức lấy chi tiết sản phẩm theo ID - THÊM VÀO
    public Product getProductById(int productId) {
        return get().withHandle(h ->
                h.createQuery("SELECT * FROM products WHERE ProductID = :id")
                        .bind("id", productId)
                        .mapToBean(Product.class)
                        .findFirst()
                        .orElse(null)
        );
    }

    public List<Product> getRelatedProducts(int currentProductId, int categoryId) {
        return get().withHandle(h ->
                h.createQuery("SELECT * FROM products WHERE CategoryID = :catId AND ProductID != :currentId LIMIT 4")
                        .bind("catId", categoryId)
                        .bind("currentId", currentProductId)
                        .mapToBean(Product.class)
                        .list()
        );
    }

    //    them sua xoa kho
    public void add(Product p) {
        get().withHandle(h -> h.createUpdate("""
                            insert into products
                            (ProductName, img, categoryId, size, price_sale, price_origin, status)
                            values (:ProductName, :img, :categoryId,, :Brand, :price, :NewPrice, 0)
                        """)
                .bind("name", p.getProductName())
                .bind("img", p.getImg())
                .bind("category_id", p.getCategoryID())
                .bind("Brand", p.getBrand())
                .bind("price", p.getPrice())
                .bind("New price", p.getNewPrice())
                .execute());
    }

    public void delete(int id) {
        get().withHandle(h -> h.createUpdate("delete from products where id = :id").bind("id", id).execute()
        );
    }

    public void publish(int id) {
        get().withHandle(h -> h.createUpdate("update products set status = 1 where id = :id").bind("id", id).execute());
    }

    public void unpublish(int id) {
        get().withHandle(h -> h.createUpdate("update products set status = 0 where id = :id").bind("id", id).execute());
    }

    public void update(int id, String name, double price) {
        get().withHandle(h -> h.createUpdate("update products set name = :name, price_sale = :price where id = :id")
                .bind("id", id).bind("price", price).bind("name", name).execute());
    }

    //tim kiem tren header


    public List<Product> searchProducts(String keyword) {
        return get().withHandle(h ->
                h.createQuery("SELECT * FROM products WHERE productName LIKE :keyword ORDER BY productName LIMIT 50")
                        .bind("keyword", "%" + keyword + "%")
                        .mapToBean(Product.class)
                        .list()
        );
    }

    public List<Product> searchProducts(String keyword, int categoryId) {
        return get().withHandle(h ->
                h.createQuery("SELECT * FROM products WHERE productName LIKE :keyword AND categoryID = :categoryId ORDER BY productName")
                        .bind("keyword", "%" + keyword + "%")
                        .bind("categoryId", categoryId)
                        .mapToBean(Product.class)
                        .list()
        );
    }

    public List<Product> searchProducts(String keyword, String brand) {
        return get().withHandle(h ->
                h.createQuery("SELECT * FROM products WHERE productName LIKE :keyword AND brand = :brand ORDER BY productName")
                        .bind("keyword", "%" + keyword + "%")
                        .bind("brand", brand)
                        .mapToBean(Product.class)
                        .list()
        );
    }

    public List<Product> getSearchSuggestions(String keyword, int limit) {
        return get().withHandle(h ->
                h.createQuery("SELECT * FROM products WHERE productName LIKE :keyword ORDER BY productName LIMIT :limit")
                        .bind("keyword", "%" + keyword + "%")
                        .bind("limit", limit)
                        .mapToBean(Product.class)
                        .list()
        );
    }

    public List<Reviews> getReviewByID(int product_id) {
        return get().withHandle(h -> h.createQuery("SELECT r.*, u.name, u.img FROM reviews r join users u on u.id = r.user_id where product_id=:product_id")
                .bind("product_id", product_id)
                .mapToBean(Reviews.class)
                .list());
    }

    public int totalReview(int product_id) {
        return get().withHandle(h -> h.createQuery("SELECT r.*, u.name, u.img FROM reviews r join users u on u.id = r.user_id where product_id=:product_id")
                .bind("product_id", product_id)
                .mapToBean(Reviews.class)
                .list()
                .size());
    }

    public int totalReviewByStar(int stars, int product_id) {
        return get().withHandle(h -> h.createQuery("SELECT r.*, u.name, u.img FROM reviews r join users u on u.id = r.user_id where stars=:stars and product_id=:product_id")
                .bind("stars", stars)
                .bind("product_id", product_id)
                .mapToBean(Reviews.class)
                .list()
                .size());
    }
    public int totalProductSold(int product_id) {
        return get().withHandle(h -> h.createQuery("SELECT SUM(quantity) AS da_ban FROM order_details WHERE product_id =:product_id")
                .bind("product_id", product_id)
                .mapTo(Integer.class)
                .findOne()
                .orElse(0));
    }
    public static InventoryStats getInventoryStats() {
        String sql = """
        SELECT
            COUNT(*) AS totalProducts,
            SUM(NewPrice * quantity) AS totalValue,
            SUM(CASE WHEN quantity < 10 AND quantity > 0 THEN 1 ELSE 0 END) AS lowStock,
            SUM(CASE WHEN quantity = 0 THEN 1 ELSE 0 END) AS outOfStock
        FROM products
        WHERE status = 'selling'
    """;

        return get().withHandle(h ->
                h.createQuery(sql)
                        .mapToBean(InventoryStats.class)
                        .one()
        );
    }

//
    public static List<Product> getProductsOnKho() {
        String sql = """
        SELECT *
        FROM products
        WHERE status = 'selling'
    """;
        return get().withHandle(h ->
                h.createQuery(sql)
                        .mapToBean(Product.class)
                        .list()
        );
    }
}