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
            updateBusiness(req, resp);
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
     * 判断是否在库存清单中存在--T--判断是否负溢出并操作
     *                         F--判断是否为负数，负数增加，正数消减
     */
    private void deleteBusiness(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //事务的id
        int businessId = Integer.parseInt(req.getParameter("id"));
        BusinessDAO businessDAO = new BusinessDAO();
        Business target = businessDAO.queryBusinessById(businessId);
        int business = Integer.parseInt(req.getParameter("business"));
        int componentId = businessDAO.queryBusinessById(businessId).getComponentId();

        //能否在库存清单找到相应的库存信息
        boolean isFound = false;

        InventoryListDAO inventoryListDAO = new InventoryListDAO();
        List<InventoryList> inventoryLists = inventoryListDAO.queryAllInventoryList();
        for (InventoryList inventoryList : inventoryLists) {
            if (inventoryList.getComponentId().equals(target.getComponentId())) {
                isFound = true;
                int old = inventoryList.getInventory();

                int newInventory = old - target.getBusiness();
                if (newInventory>0){
                    inventoryList.setInventory(newInventory);
                    inventoryListDAO.updateInventoryList(inventoryList);
                    //可以删除
                    businessDAO.deleteBusinessById(businessId);
                    resp.getWriter().write("1");
                }else if (newInventory==0){
//                    inventoryList.setInventory(newInventory);
                    inventoryListDAO.deleteInventoryListById(inventoryList.getId());
                    //可以删除
                    businessDAO.deleteBusinessById(businessId);
                    resp.getWriter().write("1");
                }else {
                    resp.getWriter().write("2");
                }


            }
        }
        //没有找到
        if (!isFound){
            if (business>0){
                //不可以删除
                resp.getWriter().write("2");
            }else {
                businessDAO.deleteBusinessById(businessId);
                InventoryList inventoryList = new InventoryList();
                business = -business;
                inventoryList.setInventory(business);
                inventoryList.setCriticalValue(100);
                inventoryList.setComponentId(componentId);
//                System.out.println(componentId);
                inventoryListDAO.addInventoryList(inventoryList);
                //可以删除
                businessDAO.deleteBusinessById(businessId);
                resp.getWriter().write("1");
            }
        }
    }

    public void updateBusiness(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int componentId = Integer.parseInt(req.getParameter("componentId"));
        int business = Integer.parseInt(req.getParameter("business"));
        //businessId
        int id = Integer.parseInt(req.getParameter("id"));
        int oldBusiness = Integer.parseInt(req.getParameter("oldBusiness"));
        //是否找到相应的库存清单信息
        boolean isFound = false;

        Business business1 = new Business();
        business1.setId(id);
        business1.setBusiness(business);
        business1.setComponentId(componentId);

        InventoryListDAO inventoryListDAO = new InventoryListDAO();
        for (InventoryList list : inventoryListDAO.queryAllInventoryList()){
            if (componentId == list.getComponentId()){
                //找到
                isFound = true;
                int oldInventory = list.getInventory();
                //新的库存剩余
                int newInventory = oldInventory-oldBusiness+business;
                if (newInventory>0){
                    list.setInventory(newInventory);
//                    System.out.println(newInventory);
                    //允许修改
                    new BusinessDAO().updateBusiness(business1);
                    new InventoryListDAO().updateInventoryList(list);
                    resp.getWriter().write("1");
                }else if (newInventory == 0 ){
                    //允许修改并删除
                    new InventoryListDAO().deleteInventoryListById(list.getId());
                    new BusinessDAO().updateBusiness(business1);
                    resp.getWriter().write("1");
                }else {
                    //不允许修改
                    resp.getWriter().write("2");
                }
                break;
            }
        }

        //没有找到
        if (!isFound){
            if ((oldBusiness-business)>=0){
                //不可以删除
                resp.getWriter().write("2");
            }else {
                InventoryList inventoryList = new InventoryList();
                inventoryList.setComponentId(componentId);
                inventoryList.setCriticalValue(100);
                business = -oldBusiness+business;
                inventoryList.setInventory(business);
                new InventoryListDAO().addInventoryList(inventoryList);
                new BusinessDAO().updateBusiness(business1);
                resp.getWriter().write("1");
            }
        }
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
        req.getSession().setAttribute("components", componentsInfs);
        req.getSession().setAttribute("map", map);
        req.getRequestDispatcher("business.jsp").forward(req, resp);
    }

    /**
     * 插入一个事务
     * 先查找库存清单，是否有该零件信息。
     *  T--如果有，则判断是否负溢出，如果溢出，则不执行并且返回"2"，如果不溢出，则执行，并返回"1"且增加事务
     *  F--如果没有，则判断是否为不大于0，如果是，则创建相应的仓库，并且增加事务，否则，不执行
     */
    private void insertBusiness(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean isOK = false;
        BusinessDAO businessDAO = new BusinessDAO();
        Business business = new Business();
        String componentName = (String) req.getAttribute("componentId");
        ComponentsInfDAO componentsInfDAO = new ComponentsInfDAO();
        List<ComponentsInf> componentsInfs = componentsInfDAO.queryAllComponentsInfs();
        ComponentsInf target = null;
        //通过名称找到零件ID
        for (ComponentsInf componentsInf : componentsInfs) {
            if (componentsInf.getName().equals(componentName)) {
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
                if (last > 0) {
                    inventoryListDAO.updateInventoryList(inventoryList);
                }
                //清零
                if (last == 0) {
                    inventoryListDAO.deleteInventoryListById(inventoryList.getId());
                }
                isOK=true;
                break;
            }
        }
//        没有找到,并且事务不小于0
        if (!isFound && businessNum > 0) {
//            临界值没有设置
            inventoryListNew = new InventoryList();
            inventoryListNew.setComponentId(componentId);
            inventoryListNew.setInventory(businessNum);
            inventoryListNew.setCriticalValue(100);
            inventoryListDAO.addInventoryList(inventoryListNew);
            isOK=true;
        }

        if (last >= 0) {
            businessDAO.addBusiness(business);
        }

        if (isOK==true){
            resp.getWriter().write("1");
        }else {
            resp.getWriter().write("2");
        }

//        showBusiness(req, resp);
    }
}
