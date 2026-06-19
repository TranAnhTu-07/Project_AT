package vn.edu.nlu.fit.projectweb.dao;

import java.io.IOException;
import java.util.Properties;

public class DBPProperties {
    private static Properties prop = new Properties();
    static{
        try{
            prop.load(DBPProperties.class.getClassLoader().getResourceAsStream("db.properties"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    public static String host() {
        return prop.get("db.host").toString();
    }
    public static int port() {
        try{
            return Integer.parseInt(prop.get("db.port").toString());
        } catch(NumberFormatException e){
            return 3306;
        }
    }
    public static String username() {
        return prop.get("db.username").toString();
    }
    public static String password() {
        return prop.get("db.password").toString();
    }
    public static String option() {
        return prop.get("db.option").toString();
    }
    public static String name() {
        return prop.get("db.name").toString();
    }

    public static void main(String[] args) {
        System.out.println(host());
    }
}
