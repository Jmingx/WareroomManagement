package club.jming.dao;

import club.jming.entity.OrderInf;
import club.jming.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 订货信息的DAO层
 * 1.增加订单
 * 2.删除订单
 * 3.修改订单
 * 4.查询某个订单
 * 5.查询所有订单
 */
public class OrderInfDAO {

    /**
     * 查询所有订单
     * @return
     */
    public List<OrderInf> queryAllOrderInfs(){
        List<OrderInf> orderInfs = new ArrayList<OrderInf>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "select * from orderInf";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                OrderInf orderInf = new OrderInf();
                orderInf.setId(resultSet.getInt("id"));
                orderInf.setComponentId(resultSet.getInt("componentId"));
                orderInf.setAmount(resultSet.getInt("amount"));
                orderInfs.add(orderInf);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.release(resultSet,preparedStatement,connection);
        }

        return orderInfs;
    }

    /**
     * 增加订单
     * @param orderInf
     */
    public void addOrderInf(OrderInf orderInf){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "insert into orderInf values (null,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,orderInf.getComponentId());
            preparedStatement.setInt(2,orderInf.getAmount());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
//            不存在外键别报错啊
        }finally {
            JDBCUtil.release(preparedStatement,connection);
        }
    }

    /**
     * 修改订单
     * @param orderInf
     */
    public void updateOrderInf(OrderInf orderInf){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "update orderInf set componentId = ?,amount = ? where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,orderInf.getComponentId());
            preparedStatement.setInt(2,orderInf.getAmount());
            preparedStatement.setInt(3,orderInf.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
//            没有匹配到外键别报错啊
        }finally {
            JDBCUtil.release(preparedStatement,connection);
        }
    }

    /**
     * 删除订单
     * @param i
     */
    public void deleteOrderById(int i){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "delete from orderInf where id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,i);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.release(preparedStatement,connection);
        }
    }

    /**
     * 查询某个订单
     * @param i
     * @return
     */
    public OrderInf queryOrderInfById(int i){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        OrderInf orderInf = new OrderInf();
        try {
            connection = JDBCUtil.getConnection();
            String sql = "select * from orderInf where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,i);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                orderInf = new OrderInf();
                orderInf.setId(resultSet.getInt("id"));
                orderInf.setComponentId(resultSet.getInt("componentId"));
                orderInf.setAmount(resultSet.getInt("amount"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.release(preparedStatement,connection);
        }
        return orderInf;
    }
}
