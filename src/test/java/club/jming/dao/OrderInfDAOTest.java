package club.jming.dao;

import club.jming.entity.OrderInf;
import org.junit.Test;

import static org.junit.Assert.*;

public class OrderInfDAOTest {

    @Test
    public void queryAllOrderInfs() {
        OrderInfDAO orderInfDAO = new OrderInfDAO();
        for (OrderInf orderInf : orderInfDAO.queryAllOrderInfs()){
            System.out.println(orderInf);
        }

    }

    @Test
    public void addOrderInf() {
        OrderInf orderInf = new OrderInf();
        orderInf.setComponentId(2);
        orderInf.setAmount(99);
        OrderInfDAO orderInfDAO = new OrderInfDAO();
        orderInfDAO.addOrderInf(orderInf);
    }

    @Test
    public void updateOrderInf() {
        OrderInfDAO orderInfDAO = new OrderInfDAO();
        OrderInf orderInf = new OrderInf();
        orderInf.setId(4);
        orderInf.setComponentId(2);
        orderInf.setAmount(3);
        orderInfDAO.updateOrderInf(orderInf);
    }

    @Test
    public void deleteOrderById() {
        OrderInfDAO orderInfDAO = new OrderInfDAO();
        orderInfDAO.deleteOrderById(10);
    }

    @Test
    public void queryOrderInfById() {
        OrderInfDAO orderInfDAO = new OrderInfDAO();
        System.out.println(orderInfDAO.queryOrderInfById(4));
    }
}