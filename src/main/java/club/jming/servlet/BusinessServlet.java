package club.jming.servlet;

import club.jming.dao.BusinessDAO;
import club.jming.dao.ComponentsInfDAO;
import club.jming.dao.InventoryListDAO;
import club.jming.entity.Business;
import club.jming.entity.ComponentsInf;
import club.jming.entity.InventoryList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 事务servlet，对事务信息做处理
 */
public class BusinessServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
//        System.out.println(type + "------");

        if (type.equals("show")) {
            showBusiness(req, resp);
        }
        if (type.equals("delete")) {
//            System.out.println("delete");
            deleteBusiness(req, resp);
        }
        if (type.equals("update")) {
            updateBusiness(req,resp);
        }
        if (type.equals("search")) {
        }
        if (type.equals("insert")) {
            insertBusiness(req, resp);
        }
    }

    /**
     * 通过事务的id，找到事务，
     * 遍历所有的库存，找到与事务的零件id相同的库存零件id，
     * 更新库存的余量,
     * 删除该事务，并且返回/business界面
     */
    private void deleteBusiness(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //事务的id
        int businessId = Integer.parseInt(req.getParameter("id"));
        BusinessDAO businessDAO = new BusinessDAO();
        Business target = businessDAO.queryBusinessById(businessId);

        InventoryListDAO inventoryListDAO = new InventoryListDAO();
        List<InventoryList> inventoryLists = inventoryListDAO.queryAllInventoryList();
        for (InventoryList inventoryList : inventoryLists){

            if (inventoryList.getComponentId().equals(target.getComponentId())){
                int old = inventoryList.getInventory();
                inventoryList.setInventory(old-target.getBusiness());
                inventoryListDAO.updateInventoryList(inventoryList);
            }
        }
        businessDAO.deleteBusinessById(businessId);
        showBusiness(req, resp);
    }

    public void updateBusiness(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int componentId = Integer.parseInt(req.getParameter("componentId"));
        System.out.println(componentId);
        int business = Integer.parseInt(req.getParameter("business"));
        int id = Integer.parseInt(req.getParameter("id"));

        Business business1 = new Business();
        business1.setId(id);
        business1.setBusiness(business);
        business1.setComponentId(componentId);

        new BusinessDAO().updateBusiness(business1);

        showBusiness(req,resp);

    }

    private void showBusiness(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BusinessDAO businessDAO = new BusinessDAO();
        List<Business> businesses = businessDAO.queryAllBusinesses();

        //key----business,value----component'name
        Map<Business, String> map = new HashMap();
        //显示零件名称
        ComponentsInfDAO componentsInfDAO = new ComponentsInfDAO();
        for (Business business : businesses) {
            map.put(business, componentsInfDAO.queryComponentsById(business.getComponentId()).getName());
//            System.out.println(componentsInfDAO.queryComponentsById(business.getComponentId()).getName());
        }

        List<ComponentsInf> componentsInfs = componentsInfDAO.queryAllComponentsInfs();
        //把作用域提升到session，免得考虑如何传参给iframe
        req.getSession().setAttribute("businesses", businesses);
        req.getSession().setAttribute("components",componentsInfs);
        req.getSession().setAttribute("map", map);
        req.getRequestDispatcher("business.jsp").forward(req, resp);
    }

    /**
     *插入一个事务
     * 并且通过事务的零件id，找到库存清单中的事务，当零件id<=0不执行（不存在）
     * 遍历库存清单，找到与事务的零件id相同的库存，更新该库存
     */
    private void insertBusiness(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BusinessDAO businessDAO = new BusinessDAO();
        Business business = new Business();
        String componentName = (String) req.getAttribute("componentId");
        ComponentsInfDAO componentsInfDAO = new ComponentsInfDAO();
        List<ComponentsInf> componentsInfs = componentsInfDAO.queryAllComponentsInfs();
        ComponentsInf target = null;
        //通过名称找到零件ID
        for (ComponentsInf componentsInf : componentsInfs){
            if (componentsInf.getName().equals(componentName)){
                target = componentsInf;
            }
        }


        int componentId = target.getId();
        int businessNum = Integer.parseInt(req.getParameter("business"));

        business.setComponentId(componentId);
        business.setBusiness(businessNum);


        //处理后的剩余值
        int last = businessNum;

        //修改库存清单
        if (componentId > 0) {
            InventoryListDAO inventoryListDAO = new InventoryListDAO();
            List<InventoryList> inventoryLists = inventoryListDAO.queryAllInventoryList();
            boolean isFound = false;
            InventoryList inventoryListNew;
            for (InventoryList inventoryList : inventoryLists) {
//            如果找到库存的话
                if (inventoryList.getComponentId() == componentId) {
                    isFound = true;
                    //修改库存清单,库存量与事务量相加
                    last = inventoryList.getInventory() + businessNum;
                    inventoryList.setInventory(last);
                    if (last>0){
                        inventoryListDAO.updateInventoryList(inventoryList);
                    }
                    break;
                }
            }
//        没有找到,并且事务不小于0
            if (!isFound && businessNum >= 0) {
//            临界值没有设置
                inventoryListNew = new InventoryList();
                inventoryListNew.setComponentId(componentId);
                inventoryListNew.setInventory(businessNum);
                inventoryListNew.setCriticalValue(100);
                inventoryListDAO.addInventoryList(inventoryListNew);
            }
        }

        if (last>0){
            businessDAO.addBusiness(business);
        }
        showBusiness(req, resp);
    }
}
