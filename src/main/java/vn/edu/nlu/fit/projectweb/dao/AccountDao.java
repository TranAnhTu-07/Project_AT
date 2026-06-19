package vn.edu.nlu.fit.projectweb.dao;
import vn.edu.nlu.fit.projectweb.model.Account;
import vn.edu.nlu.fit.projectweb.model.User;

import java.util.List;

public class AccountDao extends BaseDao{
    public List<Account> getAccount() {
        return get().withHandle(h ->  h.createQuery("SELECT * FROM accounts").mapToBean(Account.class).list());
    }
    public int totalAccount() {
        return get().withHandle(h ->  h.createQuery("SELECT * FROM accounts").mapToBean(Account.class).list().size());
    }
    public int getAccountByStatus(String status) {
        return get().withHandle(h ->
                h.createQuery("SELECT * FROM accounts WHERE status like :status")
                        .bind("status", "%" + status + "%")
                        .mapToBean(Account.class)
                        .list().size()
        );
    }
    public Account getAccountById(int id) {
        return get().withHandle(handle ->
                handle.createQuery("SELECT a.*, u.phone, u.date_of_birth FROM accounts a join users u on a.user_id = u.id WHERE a.id = :id")
                        .bind("id", id)
                        .mapToBean(Account.class)
                        .findOne()
                        .orElse(null)
        );
    }
    public List<Account> getAccountByPage(int page, int pageSize) {
        int offset = (page - 1) * pageSize;

        return get().withHandle(h ->
                h.createQuery("""
            SELECT * FROM accounts
            ORDER BY id DESC
            LIMIT :limit OFFSET :offset
        """)
                        .bind("limit", pageSize)
                        .bind("offset", offset)
                        .mapToBean(Account.class)
                        .list()
        );
    }
    public int updateAcc(User user, int id) {
        return get().withHandle(handle ->
                handle.createUpdate(
                                "UPDATE users " +
                                        "SET name = :name, email = :email, phone = :phone, sex = :sex " +
                                        "WHERE id = :id"
                        )
                        .bind("id", id)
                        .bind("name", user.getFullName())
                        .bind("email", user.getEmail())
                        .bind("phone", user.getPhone())
                        .bind("sex",user.getSex())
                        .execute()
        );
    }


    static void main() {
        AccountDao dao = new AccountDao();
        Account i = dao.getAccountById(8);
        System.out.println(i);
    }
}
