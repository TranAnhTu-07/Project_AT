package vn.edu.nlu.fit.projectweb.utils;

import org.jdbi.v3.core.Jdbi;

public class JDBIConnector {

    private static final Jdbi jdbi;

    static {
        jdbi = Jdbi.create(
                "jdbc:mysql://localhost:3306/shopdb?useSSL=false",
                "root",
                "123456"
        );
    }

    public static Jdbi me() {
        return jdbi;
    }
}