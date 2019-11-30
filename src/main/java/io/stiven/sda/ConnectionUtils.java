package io.stiven.sda;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionUtils {

    public static void close(Connection conn){
        try{
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void rollback(Connection conn){
        try{
            conn.rollback();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setAutoCommit(Connection conn, boolean flag){
        try {
            conn.setAutoCommit(flag);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
