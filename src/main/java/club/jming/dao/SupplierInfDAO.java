package club.jming.dao;

import club.jming.entity.SupplierInf;
import club.jming.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 供应商信息DAO
 * 1.增加供应商信息
 * 2.更新供应商信息
 * 3.删除供应商信息
 * 4.查询所有供应商信息
 * 5.查询某个供应商信息
 */
public class SupplierInfDAO {

    /**
     * 增加供应商信息
     * @param supplierInf
     */
    public void addSupplier(SupplierInf supplierInf) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "insert into supplierInf values (null,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, supplierInf.getName());
            preparedStatement.setString(2, supplierInf.getContact());
            if (preparedStatement.executeUpdate()==0){
                return ;
            }

        } catch (SQLException e) {
        } finally {
            JDBCUtil.release(preparedStatement, connection);
        }
    }

    /**
     * 更新供应商信息
     * @param supplierInf
     */
    public void updateSupplier(SupplierInf supplierInf) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "update supplierInf set name = ?,contact = ? where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, supplierInf.getName());
            preparedStatement.setString(2, supplierInf.getContact());
            preparedStatement.setInt(3, supplierInf.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.release(preparedStatement, connection);
        }
    }

    /**
     * 删除供应商信息
     * @param i
     */
    public void deleteSupplierById(int i) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "delete from supplierInf where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, i);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.release(preparedStatement, connection);
        }
    }

    /**
     * 查询所有供应商信息
     * @return
     */
    public List<SupplierInf> queryAllSuppliers() {
        List<SupplierInf> supplierInfs = new ArrayList<SupplierInf>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "select * from supplierInf";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                SupplierInf supplierInf = new SupplierInf();
                supplierInf.setId(resultSet.getInt("id"));
                supplierInf.setName(resultSet.getString("name"));
                supplierInf.setContact(resultSet.getString("contact"));
                supplierInfs.add(supplierInf);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.release(resultSet, preparedStatement, connection);
        }
        return supplierInfs;
    }

    /**
     * 查询某个供应商信息
     * @param i
     * @return
     */
    public SupplierInf querySupplierById(int i){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        SupplierInf supplierInf = new SupplierInf();
        try {
            connection = JDBCUtil.getConnection();
            String sql = "select * from supplierInf where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,i);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                supplierInf.setId(resultSet.getInt("id"));
                supplierInf.setName(resultSet.getString("name"));
                supplierInf.setContact(resultSet.getString("contact"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.release(resultSet,preparedStatement,connection);
        }
        return supplierInf;
    }


}
