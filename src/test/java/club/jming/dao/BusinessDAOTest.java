package club.jming.dao;

import club.jming.entity.Business;
import org.junit.Test;

import static org.junit.Assert.*;

public class BusinessDAOTest {

    @Test
    public void addBusiness() {
        BusinessDAO businessDAO = new BusinessDAO();
        Business business = new Business();
        business.setComponentId(5);
        business.setBusiness(99);
        businessDAO.addBusiness(business);
    }

    @Test
    public void deleteBusinessById() {
        BusinessDAO businessDAO = new BusinessDAO();
        businessDAO.deleteBusinessById(4);
    }

    @Test
    public void updateBusiness() {
        BusinessDAO businessDAO = new BusinessDAO();
        Business business = new Business();
        business.setId(3);
        business.setComponentId(2);
        business.setBusiness(55);
        businessDAO.updateBusiness(business);
    }

    @Test
    public void getAllBusinesses() {
        BusinessDAO businessDAO = new BusinessDAO();
        for (Business business : businessDAO.queryAllBusinesses()){
            System.out.println(business);
        }
    }

    @Test
    public void getBusinessById() {
        BusinessDAO businessDAO = new BusinessDAO();
        System.out.println(businessDAO.queryBusinessById(3));
    }
}