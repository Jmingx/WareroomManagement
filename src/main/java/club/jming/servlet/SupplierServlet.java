package club.jming.servlet;

import club.jming.dao.SupplierInfDAO;
import club.jming.entity.SupplierInf;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 事务servlet，对事务信息做处理
 */
public class SupplierServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
//        System.out.println(type);
        if (type.equals("show")){
            showSupplier(req,resp);
        }
        if (type.equals("delete")){
            deleteSupplier(req,resp);
        }
        if (type.equals("update")){
            System.out.println("update");
        }
        if (type.equals("search")){
            System.out.println("search");
        }
        if (type.equals("insert")){
            insertSupplier(req,resp);
        }
    }

    private void deleteSupplier(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        SupplierInfDAO supplierInfDAO = new SupplierInfDAO();
        supplierInfDAO.deleteSupplierById(id);
        showSupplier(req,resp);
    }

    public void updateSupplier(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    private void showSupplier(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SupplierInfDAO supplierInfDAO = new SupplierInfDAO();
        List<SupplierInf> supplierInfs = supplierInfDAO.queryAllSuppliers();

        req.getSession().setAttribute("suppliers",supplierInfs);
        req.getRequestDispatcher("supplier.jsp").forward(req,resp);
    }

    private void insertSupplier(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SupplierInfDAO supplierInfDAO = new SupplierInfDAO();
        SupplierInf supplierInf = new SupplierInf();

        String name = (String) req.getAttribute("name");
        String contact = (String) req.getAttribute("contact");

        supplierInf.setName(name);
        supplierInf.setContact(contact);
        supplierInfDAO.addSupplier(supplierInf);
        showSupplier(req,resp);
    }
}
