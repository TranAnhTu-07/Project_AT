package vn.edu.nlu.fit.projectweb.dao;

import vn.edu.nlu.fit.projectweb.model.UserKey;

import java.util.Optional;

public class UserKeyDao extends BaseDao {

    public static Optional<UserKey> getLatestActiveKey(int userId) {
        String sql = "SELECT id, user_id, public_key, status, created_at, updated_at " +
                "FROM user_keys " +
                "WHERE user_id = :userId AND status = 1 " +
                "ORDER BY created_at DESC LIMIT 1";

        return get().withHandle(handle ->
                handle.createQuery(sql)
                        .bind("userId", userId)
                        .mapToBean(UserKey.class)
                        .findFirst()
        );
    }
}