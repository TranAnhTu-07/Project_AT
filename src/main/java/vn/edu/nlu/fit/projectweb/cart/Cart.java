package vn.edu.nlu.fit.projectweb.cart;

import vn.edu.nlu.fit.projectweb.model.Product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Cart implements Serializable {
    Map<Integer, CartItem> data;

    public Cart() {
        data = new HashMap<Integer, CartItem>();
    }

    public void updateItem(Product product, int quantity) {
        if (product == null) return;

        int productId = product.getProductID();

        if (quantity <= 0) {
            data.remove(productId);
            return;
        }

        // LUÔN tạo mới CartItem
        data.put(productId, new CartItem(product, quantity, product.getPrice()));
    }


    public void addItem(Product product, int quantity) {
        if (quantity <= 0) quantity = 1;
        int productId = product.getProductID();
        if (data.containsKey(productId)) {
            CartItem oldItem = data.get(productId);
            int totalQty = oldItem.getQuantity() + quantity;
            data.put(productId, new CartItem(product, totalQty, product.getPrice()));
        } else {
            data.put(productId, new CartItem(product, quantity, product.getPrice()));
        }
    }

    public CartItem delItem(int id) {
        return data.remove(id);
    }
    public List<CartItem> delAll() {
        List<CartItem> items = new ArrayList<CartItem>(data.values());
        data.clear();
        return items;
    }
    public List<CartItem> getItems() {
        return new ArrayList<>(data.values());
    }
    public int getTotalQuantity()   {
        AtomicInteger total = new AtomicInteger();
        data.values().forEach(c -> total.addAndGet(c.getQuantity()));
        return total.get();
    }
    public double getTotal()   {
        AtomicReference<Double> sum = new AtomicReference<>((double) 0);
        data.values().forEach(c -> sum.updateAndGet(v -> v + c.getPrice() * c.getQuantity()));
        return sum.get();
    }
    public boolean isValidQuantity(Product product, int quantity) {
        return quantity <= product.getStock(); // ví dụ
    }


}

