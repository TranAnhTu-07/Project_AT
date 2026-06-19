package vn.edu.nlu.fit.projectweb.dao;

import vn.edu.nlu.fit.projectweb.model.User;

public class AuthDao extends BaseDao {
    public User getUserByUsername(String username) {
        return get().withHandle(h ->
                h.createQuery("select * from users where username = :username")
                        .bind("username", username)
                        .mapToBean(User.class).findFirst().orElse(null));
    }
}
