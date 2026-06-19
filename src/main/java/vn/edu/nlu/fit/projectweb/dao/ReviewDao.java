package vn.edu.nlu.fit.projectweb.dao;

import vn.edu.nlu.fit.projectweb.model.Reviews;
import java.util.List;

public class ReviewDao extends BaseDao {

    public List<Reviews> getRecentReviews() {
        String sql = """
            SELECT id,product_id AS productId, name,email, rating,content, created_at AS createdAt
            FROM review
            ORDER BY created_at DESC
        """;

        return get().withHandle(h ->
                h.createQuery(sql)
                        .mapToBean(Reviews.class)
                        .list()
        );
    }

    public void insert(Reviews r) {
        String sql = """
            INSERT INTO review(ProductID, name, email, rating, content)
            VALUES (:productId, :name, :email, :rating, :content)
        """;

        get().useHandle(h ->
                h.createUpdate(sql)
                        .bindBean(r)
                        .execute()
        );
    }
}
