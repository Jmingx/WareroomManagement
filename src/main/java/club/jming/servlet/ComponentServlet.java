package club.jming.servlet;

import club.jming.dao.ComponentsInfDAO;
import club.jming.dao.SupplierInfDAO;
import club.jming.entity.ComponentsInf;
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
public class ComponentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
//        System.out.println(type+"--component");

        if (type.equals("show")) {
            showComponent(req, resp);
        }
        if (type.equals("delete")) {
//            System.out.println("delete");
            deleteComponent(req, resp);
        }
        if (type.equals("update")) {
//            System.out.println("update");
            updateComponent(req, resp);
        }
        if (type.equals("search")) {
        }
        if (type.equals("insert")) {
            insertComponent(req, resp);
        }
    }

    private void deleteComponent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        ComponentsInfDAO componentsInfDAO = new ComponentsInfDAO();
        componentsInfDAO.deleteComponentsInfById(id);
        resp.getWriter().write("1");
    }

    private void updateComponent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int componentId = Integer.parseInt(req.getParameter("componentId"));
        String componentName = (String) req.getAttribute("componentName");
        double price = Double.parseDouble(req.getParameter("price"));
        int supplierId = Integer.parseInt(req.getParameter("supplierId"));

        ComponentsInfDAO componentsInfDAO = new ComponentsInfDAO();
        ComponentsInf componentsInf = componentsInfDAO.queryComponentsById(componentId);
        componentsInf.setSupplierId(supplierId);
        componentsInf.setPrice(price);
        componentsInf.setName(componentName);
        componentsInf.setId(componentId);
        componentsInfDAO.updateComponentsInf(componentsInf);
        resp.getWriter().write("1");
    }

    private void showComponent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ComponentsInfDAO componentsInfDAO = new ComponentsInfDAO();
        List<ComponentsInf> componentsInfs = componentsInfDAO.queryAllComponentsInfs();

        SupplierInfDAO supplierInfDAO = new SupplierInfDAO();
        List<SupplierInf> supplierInfs = supplierInfDAO.queryAllSuppliers();

        req.getSession().setAttribute("components", componentsInfs);
        req.getSession().setAttribute("suppliers", supplierInfs);
        req.getRequestDispatcher("component.jsp").forward(req, resp);
    }

    private void insertComponent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String componentName = (String) req.getAttribute("name");
        String supplierName = (String) req.getAttribute("supplierId");
        double price = Double.parseDouble(req.getParameter("price"));

        SupplierInfDAO supplierInfDAO = new SupplierInfDAO();
        List<SupplierInf> supplierInfs = supplierInfDAO.queryAllSuppliers();
        //通过名称找到供应商ID
        SupplierInf target = null;
//        System.out.println(supplierName);
//        System.out.println(supplierInfs);
        for (SupplierInf supplierInf : supplierInfs) {
//            System.out.println(supplierInf);
            if (supplierInf.getName().equals(supplierName)) {
                target = supplierInf;
            }
        }
        int supplierId = target.getId();

//        数据合理
        //Component存储
        ComponentsInfDAO componentsInfDAO = new ComponentsInfDAO();
        ComponentsInf componentsInf = new ComponentsInf();
        componentsInf.setName(componentName);
        componentsInf.setPrice(price);
        componentsInf.setSupplierId(supplierId);
        componentsInfDAO.addComponentsInf(componentsInf);
        resp.getWriter().write("1");
    }
}
