package club.jming.dao;

import club.jming.entity.User;
import club.jming.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 用户DAO
 * 1.是否注册
 * 2.注册
 * 3.登录成功
 */
public class UserDAO {

    /**
     * 是否注册
     * @param user
     * @return
     */
    public boolean isRegister(User user){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "select * from User where userName = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,user.getUsername());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                //账号已存在
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.release(resultSet,preparedStatement,connection);
        }
        return false;
    }

    /**
     * 注册
     * @param user
     */
    public void register(User user){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "insert into User values (null,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,user.getUsername());
            preparedStatement.setString(2,user.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
//            密码为空别报错啊
        }finally {
            JDBCUtil.release(preparedStatement,connection);
        }
    }

    /**
     * 登录成功
     * @param user
     * @return
     */
    public boolean login(User user){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "select * from User where userName = ? and password = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,user.getUsername());
            preparedStatement.setString(2,user.getPassword());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                //密码账号正确
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.release(resultSet,preparedStatement,connection);
        }
        return false;
    }
}
