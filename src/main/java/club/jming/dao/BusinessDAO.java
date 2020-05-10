package club.jming.dao;

import club.jming.entity.Business;
import club.jming.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 事务的DAO层
 * 需要实现
 * 1.增加事务
 * 2.删除事务
 * 3.修改事务
 * 4.查询所有事务
 * 5.查询某一个事务
 */
public class BusinessDAO {
    public BusinessDAO() {
    }

    /**
     * 增加事务
     * @param business
     */
    public void addBusiness(Business business){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "insert into business values (null,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,business.getComponentId());
            preparedStatement.setInt(2,business.getBusiness());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
//            匹配不到外键不要报错啊
        }finally {
            JDBCUtil.release(preparedStatement,connection);
        }
    }

    /**
     * 删除事务
     * @param i
     */
    public void deleteBusinessById(int i){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtil.getConnection();
            String  sql = "delete from business where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,i);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtil.release(preparedStatement,connection);
        }

    }

    /**
     * 修改事务
     * @param business
     */
    public void updateBusiness(Business business){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "update business set componentId = ?,business = ? where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,business.getComponentId());
            preparedStatement.setInt(2,business.getBusiness());
            preparedStatement.setInt(3,business.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
//            找不到外键不要报错
        }finally {
            JDBCUtil.release(preparedStatement,connection);
        }
    }

    /**
     * 查询所有事务
     * @return
     */
    public List<Business> queryAllBusinesses(){
        List<Business> businesses = new ArrayList<Business>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "select * from business";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Business business = new Business();
                business.setBusiness(resultSet.getInt("business"));
                business.setComponentId(resultSet.getInt("componentId"));
                business.setId(resultSet.getInt("id"));
                businesses.add(business);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.release(resultSet,preparedStatement,connection);
        }
        return businesses;
    }

    /**
     * 查询某一个事务
     * @param i
     * @return
     */
    public Business queryBusinessById(int i){
        Business business = new Business();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "select * from business where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,i);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                business.setBusiness(resultSet.getInt("business"));
                business.setComponentId(resultSet.getInt("componentId"));
                business.setId(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.release(resultSet,preparedStatement,connection);
        }
        return business;
    }
}
