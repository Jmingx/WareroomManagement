package club.jming.dao;

import club.jming.entity.ComponentsInf;
import org.junit.Test;

import static org.junit.Assert.*;

public class ComponentsInfDAOTest {

    @Test
    public void addComponentsInf() {
        ComponentsInf componentsInf = new ComponentsInf();
        componentsInf.setSupplierId(14);
        componentsInf.setPrice(99.5);
        componentsInf.setName("maven");
        ComponentsInfDAO componentsInfDAO = new ComponentsInfDAO();
        componentsInfDAO.addComponentsInf(componentsInf);
    }

    @Test
    public void deleteComponentsInfById() {
        ComponentsInfDAO componentsInfDAO = new ComponentsInfDAO();
        componentsInfDAO.deleteComponentsInfById(4);
    }

    @Test
    public void updateComponentsInf() {
        ComponentsInfDAO componentsInfDAO = new ComponentsInfDAO();
        ComponentsInf componentsInf = new ComponentsInf();
        componentsInf.setId(2);
        componentsInf.setSupplierId(12);
        componentsInf.setPrice(42.3);
        componentsInf.setName("javaBean");
        componentsInfDAO.updateComponentsInf(componentsInf);
    }

    @Test
    public void getAllComponentsInfs() {
        ComponentsInfDAO componentsInfDAO = new ComponentsInfDAO();
        for (ComponentsInf componentsInf : componentsInfDAO.queryAllComponentsInfs()){
            System.out.println(componentsInf);
        }
    }

    @Test
    public void queryComponentsById() {
        ComponentsInfDAO componentsInfDAO = new ComponentsInfDAO();
        System.out.println(componentsInfDAO.queryComponentsById(2));
    }
}