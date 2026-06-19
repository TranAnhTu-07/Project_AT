package vn.edu.nlu.fit.projectweb.dao;

import vn.edu.nlu.fit.projectweb.model.Category;

import java.util.List;
public class CategoryDao extends BaseDao {
    public List<Category> getCategoryChild(int parent_id) {
        return get().withHandle(h -> h.createQuery("SELECT * FROM categories where ParentID=:id")
                .bind("id", parent_id)
                .mapToBean(Category.class)
                .list());
    }
    public List<Category> getCategoryParent() {
        return get().withHandle(h -> h.createQuery("SELECT * FROM categories where ParentID IS NULL")
                .mapToBean(Category.class)
                .list());
    }
//    public List<String> getCategoryImages() {
//        return get().withHandle(h -> h.createQuery("select img from categories").mapTo(String.class).list()).stream()
//                .filter(img -> img != null && !img.isBlank())
//                .toList();
//
//    }
    public List<Category> getAllCategoryChild() {
        return get().withHandle(h ->
                h.createQuery("select * from categories where ParentID is not null")
                        .mapToBean(Category.class)
                        .list()
        );
    }

}