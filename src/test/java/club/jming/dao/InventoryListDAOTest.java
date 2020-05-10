package club.jming.dao;

import club.jming.entity.InventoryList;
import org.junit.Test;

import static org.junit.Assert.*;

public class InventoryListDAOTest {

    @Test
    public void addInventoryList() {
        InventoryList inventoryList = new InventoryList();
        InventoryListDAO inventoryListDAO = new InventoryListDAO();
        inventoryList.setComponentId(5);
        inventoryList.setInventory(6);
        inventoryList.setCriticalValue(750);
        inventoryListDAO.addInventoryList(inventoryList);
    }

    @Test
    public void deleteInventoryListById() {
        InventoryListDAO inventoryListDAO = new InventoryListDAO();
        inventoryListDAO.deleteInventoryListById(4);
    }

    @Test
    public void updateInventoryList() {
        InventoryList inventoryList = new InventoryList();
        InventoryListDAO inventoryListDAO = new InventoryListDAO();
        inventoryList.setId(5);
        inventoryList.setComponentId(5);
        inventoryList.setInventory(8);
        inventoryList.setCriticalValue(950);
        inventoryListDAO.updateInventoryList(inventoryList);
    }

    @Test
    public void queryAllInventoryList() {
        InventoryListDAO inventoryListDAO = new InventoryListDAO();
        for (InventoryList inventoryList : inventoryListDAO.queryAllInventoryList()) {
            System.out.println(inventoryList);
        }
    }

    @Test
    public void queryInventoryById() {
        InventoryListDAO inventoryListDAO = new InventoryListDAO();
        System.out.println(inventoryListDAO.queryInventoryById(5));
    }
}