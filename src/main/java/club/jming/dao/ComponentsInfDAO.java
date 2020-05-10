package club.jming.dao;

import club.jming.entity.ComponentsInf;
import club.jming.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 零件信息的DAO层
 * 1.增加零件信息
 * 2.删除零件信息
 * 3.更新某个零件信息
 * 4.返回所有零件信息
 * 5.返回一个零件信息
 */
public class ComponentsInfDAO {
    /**
     * 增加零件信息
     * @param componentsInf
     */
    public void addComponentsInf(ComponentsInf componentsInf){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "insert into componentInf values (null,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, componentsInf.getSupplierId());
            preparedStatement.setDouble(2, componentsInf.getPrice());
            preparedStatement.setString(3, componentsInf.getName());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
//            没有匹配到外键别报错啊
        }finally {
            JDBCUtil.release(preparedStatement,connection);
        }
    }

    /**
     * 删除零件信息
     * @param i
     */
    public void deleteComponentsInfById(int i){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "delete from componentInf where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, i);
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.release(preparedStatement,connection);
        }
    }

    /**
     * 更新某个零件信息
     * @param componentsInf
     */
    public void updateComponentsInf(ComponentsInf componentsInf){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "update componentInf set supplierId=? , price=? ,name=? where id = ? ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, componentsInf.getSupplierId());
            preparedStatement.setDouble(2, componentsInf.getPrice());
            preparedStatement.setString(3, componentsInf.getName());
            preparedStatement.setInt(4, componentsInf.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.release(preparedStatement,connection);
        }
    }

    /**
     * 返回所有零件信息
     * @return
     */
    public List<ComponentsInf> queryAllComponentsInfs(){
        List<ComponentsInf> componentsInfs = new ArrayList<ComponentsInf>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "select * from componentInf";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                ComponentsInf componentsInf = new ComponentsInf();
                componentsInf.setId(resultSet.getInt("id"));
                componentsInf.setSupplierId(resultSet.getInt("supplierId"));
                componentsInf.setPrice(resultSet.getDouble("price"));
                componentsInf.setName(resultSet.getString("name"));
                componentsInfs.add(componentsInf);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.release(resultSet,preparedStatement,connection);
        }
        return componentsInfs;
    }

    /**
     * 返回一个零件信息
     * @param id
     * @return
     */
    public ComponentsInf queryComponentsById(int id){
        ComponentsInf componentsInf = new ComponentsInf();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "select * from componentInf where id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                componentsInf.setName(resultSet.getString("name"));
                componentsInf.setPrice(resultSet.getDouble("price"));
                componentsInf.setSupplierId(resultSet.getInt("supplierId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.release(resultSet,preparedStatement,connection);
        }
        return componentsInf;
    }
}
