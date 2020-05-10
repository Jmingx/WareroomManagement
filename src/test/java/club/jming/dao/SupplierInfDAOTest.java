package club.jming.dao;

import club.jming.entity.SupplierInf;
import org.junit.Test;

import static org.junit.Assert.*;

public class SupplierInfDAOTest {

    @Test
    public void addSupplier() {
        SupplierInf supplierInf = new SupplierInf();
//        supplierInf.setName("Oracle");
//        supplierInf.setContact("UN,H-DC");
//        supplierInf.setName("Mysql");
//        supplierInf.setContact("CN,ALiBaBa");
        supplierInf.setName("Python");
        supplierInf.setContact("UN,BillGate");
        SupplierInfDAO supplierInfDAO = new SupplierInfDAO();
        supplierInfDAO.addSupplier(supplierInf);
    }

    @Test
    public void updateSupplier() {
        SupplierInf supplierInf = new SupplierInf();
        supplierInf.setName("Java");
        supplierInf.setContact("CN,BeiJing");
        supplierInf.setId(13);
        SupplierInfDAO supplierInfDAO = new SupplierInfDAO();
        supplierInfDAO.updateSupplier(supplierInf);
    }

    @Test
    public void deleteSupplierById() {
        SupplierInfDAO supplierInfDAO = new SupplierInfDAO();
        supplierInfDAO.deleteSupplierById(13);
    }

    @Test
    public void queryAllSuppliers() {
        SupplierInfDAO supplierInfDAO = new SupplierInfDAO();
        for (SupplierInf supplierInf : supplierInfDAO.queryAllSuppliers()){
            System.out.println(supplierInf);
        }
    }

    @Test
    public void querySupplierById() {
        SupplierInfDAO supplierInfDAO = new SupplierInfDAO();
        System.out.println(supplierInfDAO.querySupplierById(13));
    }
}