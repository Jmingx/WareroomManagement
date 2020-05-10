package club.jming.utils;

import java.sql.*;

/**
 * JDBC工具类，但没有使用c3p0框架，下次补上
 */
public class JDBCUtil {
    /**
     * 加载数据库驱动
     */
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回数据库连接
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        Connection connection;
        String URL = "jdbc:mysql://localhost:3306/warehouseManagement?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8";
        String USER = "root";
        String PASSWORD = "";
        connection = DriverManager.getConnection(URL, USER, PASSWORD);;
        return connection;
    }

    /**
     * 释放资源
     * @param resultSet
     * @param preparedStatement
     * @param connection
     */
    public static void release(ResultSet resultSet, PreparedStatement preparedStatement,Connection connection){
        if (resultSet!=null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (preparedStatement!=null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (connection!=null){
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    public static void release(PreparedStatement preparedStatement,Connection connection){
        if (preparedStatement!=null){
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
