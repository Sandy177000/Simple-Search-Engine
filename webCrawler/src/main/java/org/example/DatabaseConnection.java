package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    static Connection connection = null;
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if(connection!=null){
            return connection;
        }
        String user = "root";
        String pwd = "Sandesh@2000";
        String db  ="searchengineapp1";
        return getConnection(user, pwd, db);
    }
    private static Connection getConnection (String user, String pwd, String db) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost/"+db+"?user="+ user +"&password="+pwd);
        return connection;
    }
}
