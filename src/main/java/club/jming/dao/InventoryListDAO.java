package club.jming.dao;

import club.jming.entity.InventoryList;
import club.jming.entity.PageVo;
import club.jming.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 库存清单DAO
 * 1.增加一个库存清单
 * 2.删除一个库存清单
 * 3.更新一个库存清单
 * 4.返回所有库存清单
 * 5.查询某个库存清单
 */
public class InventoryListDAO {

    /**
     * 增加一个库存清单
     * @param inventoryList
     */
    public void addInventoryList(InventoryList inventoryList){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "insert into inventory values(null,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,inventoryList.getComponentId());
            preparedStatement.setInt(2,inventoryList.getInventory());
            preparedStatement.setInt(3,inventoryList.getCriticalValue());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
//            查询不到外键别报错啊
            e.printStackTrace();
        }finally {
            JDBCUtil.release(preparedStatement,connection);
        }
    }

    /**
     * 删除一个库存清单
     * @param i
     */
    public void deleteInventoryListById(int i){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "delete from inventory where id = ?";
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
     * 更新一个库存清单
     * @param inventoryList
     */
    public void updateInventoryList(InventoryList inventoryList){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "update inventory set componentId=? , inventory=? ,criticalValue=? where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,inventoryList.getComponentId());
            preparedStatement.setInt(2,inventoryList.getInventory());
            preparedStatement.setInt(3,inventoryList.getCriticalValue());
            preparedStatement.setInt(4,inventoryList.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
//            没有匹配到外键别报错啊
            e.printStackTrace();
        }finally {
            JDBCUtil.release(preparedStatement,connection);
        }
    }

    /**
     * 返回所有库存清单
     * @return
     */
    public List<InventoryList> queryAllInventoryList(){
        List<InventoryList> inventoryLists = new ArrayList<InventoryList>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "select * from inventory ";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                InventoryList inventoryList = new InventoryList();
                inventoryList.setId(resultSet.getInt("id"));
                inventoryList.setComponentId(resultSet.getInt("componentId"));
                inventoryList.setInventory(resultSet.getInt("inventory"));
                inventoryList.setCriticalValue(resultSet.getInt("criticalValue"));
                inventoryLists.add(inventoryList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.release(resultSet,preparedStatement,connection);
        }
        return inventoryLists;
    }

    /**
     * 查询某个库存清单
     * @param i
     * @return
     */
    public InventoryList queryInventoryById(int i){
        InventoryList inventoryList = new InventoryList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "select * from inventory where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,i);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                inventoryList.setId(resultSet.getInt("id"));
                inventoryList.setComponentId(resultSet.getInt("componentId"));
                inventoryList.setInventory(resultSet.getInt("inventory"));
                inventoryList.setCriticalValue(resultSet.getInt("criticalValue"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.release(resultSet,preparedStatement,connection);
        }
        return inventoryList;

    }

}
