package club.jming.servlet;

import club.jming.dao.BusinessDAO;
import club.jming.dao.ComponentsInfDAO;
import club.jming.dao.InventoryListDAO;
import club.jming.dao.SupplierInfDAO;
import club.jming.entity.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ValueFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 事务servlet，对事务信息做处理
 */
public class BusinessServlet extends HttpServlet {

    ValueFilter filter = new ValueFilter() {
        @Override
        public Object process(Object object, String name, Object value) {
            if(value instanceof BigDecimal || value instanceof Double || value instanceof Float){
                DecimalFormat df   =new   java.text.DecimalFormat("#.00");
                return df.format(value);
            }
            return value;
        }
    };

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        System.out.println(type + "----type--");

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
        if (type.equals("batchDelete")) {
            batchDelete(req, resp);
        }

        //模糊搜索
        if (type.equals("search")){
            search(req,resp);
        }
    }

    //模糊搜索
    private void search(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pattern = req.getParameter("componentName");

        //转换字符集
        byte[] bytes=  pattern.getBytes("ISO-8859-1");
        pattern = new String(bytes,"UTF-8");

//        pattern = "%"+pattern+"%";

        System.out.println(pattern);
        List<BusinessToShow> businessToShows = this.queryBusinessToShowByComponentName(pattern);

        resp.setContentType("application/json;charset=utf-8");

        System.out.println(JSON.toJSONString(new ResMessage(200, "succeed", businessToShows)));
        resp.getWriter().write(JSON.toJSONString(new ResMessage(200, "succeed", businessToShows), filter));
    }

    private void deleteSupplier(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        SupplierInfDAO supplierInfDAO = new SupplierInfDAO();
        supplierInfDAO.deleteSupplierById(id);

        resp.setContentType("application/json;charset=utf-8");
        System.out.println(JSON.toJSONString(new ResMessage(0,"succeed","删除成功")));
        resp.getWriter().write(JSON.toJSONString(new ResMessage(0,"succeed","删除成功"),filter));
//        showSupplier(req,resp);
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
        int business = businessDAO.queryBusinessById(businessId).getBusiness();
        int componentId = businessDAO.queryBusinessById(businessId).getComponentId();

        System.out.println(businessId);
        System.out.println(business);

        resp.setContentType("application/json;charset=utf-8");

        if (checkToDelete(businessId)==true){
            businessDAO.deleteBusinessById(businessId);
            System.out.println(JSON.toJSONString(new ResMessage(0, "succeed", "删除成功")));
            resp.getWriter().write(JSON.toJSONString(new ResMessage(0, "succeed", "删除成功"), filter));
        }else {
            System.out.println(JSON.toJSONString(new ResMessage(0, "fail", "删除失败")));
            resp.getWriter().write(JSON.toJSONString(new ResMessage(0, "fail", "删除失败"), filter));
        }
    }

    public void updateBusiness(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int componentId = Integer.parseInt(req.getParameter("componentId"));

        System.out.println("update---"+componentId);

        int business = Integer.parseInt(req.getParameter("business"));

        System.out.println("business---"+business);

        //businessId
        int id = Integer.parseInt(req.getParameter("id"));
        int oldBusiness = new BusinessDAO().queryBusinessById(id).getBusiness();

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
//                    resp.getWriter().write("1");

                    System.out.println(JSON.toJSONString(new ResMessage(0, "succeed", "更新成功")));
                    resp.getWriter().write(JSON.toJSONString(new ResMessage(0, "succeed", "更新成功"), filter));

                }else if (newInventory == 0 ){
                    //允许修改并删除
                    new InventoryListDAO().deleteInventoryListById(list.getId());
                    new BusinessDAO().updateBusiness(business1);
//                    resp.getWriter().write("1");

                    System.out.println(JSON.toJSONString(new ResMessage(0, "succeed", "更新成功")));
                    resp.getWriter().write(JSON.toJSONString(new ResMessage(0, "succeed", "更新成功"), filter));


                }else {
                    //不允许修改
//                    resp.getWriter().write("2");
                    System.out.println(JSON.toJSONString(new ResMessage(0, "fail", "更新失败")));
                    resp.getWriter().write(JSON.toJSONString(new ResMessage(0, "fail", "更新失败"), filter));

                }
                break;
            }
        }

        //没有找到
        if (!isFound){
            if ((oldBusiness-business)>=0){
                //不可以删除
//                resp.getWriter().write("2");

                System.out.println(JSON.toJSONString(new ResMessage(0, "fail", "修改失败")));
                resp.getWriter().write(JSON.toJSONString(new ResMessage(0, "fail", "修改失败"), filter));
            }else {
                InventoryList inventoryList = new InventoryList();
                inventoryList.setComponentId(componentId);
                inventoryList.setCriticalValue(100);
                business = -oldBusiness+business;
                inventoryList.setInventory(business);
                new InventoryListDAO().addInventoryList(inventoryList);
                new BusinessDAO().updateBusiness(business1);
//                resp.getWriter().write("1");

                System.out.println(JSON.toJSONString(new ResMessage(0, "succeed", "修改成功")));
                resp.getWriter().write(JSON.toJSONString(new ResMessage(0, "succeed", "修改成功"), filter));

            }
        }
    }

    private void showBusiness(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BusinessDAO businessDAO = new BusinessDAO();
        List<Business> businesses = businessDAO.queryAllBusinesses();
        List<BusinessToShow> businessToShows = new ArrayList<>();

        BusinessToShow businessToShow = null;
        //key----business,value----component'name
//        Map<Business, String> map = new HashMap();
        //显示零件名称
        ComponentsInfDAO componentsInfDAO = new ComponentsInfDAO();
        for (Business business : businesses) {
//            map.put(business, componentsInfDAO.queryComponentsById(business.getComponentId()).getName());

            String componentName = componentsInfDAO.queryComponentsById(business.getComponentId()).getName();
            businessToShow = new BusinessToShow();
            businessToShow.setBusiness(business.getBusiness());
            businessToShow.setComponentId(business.getComponentId());
            businessToShow.setComponentName(componentName);
            businessToShow.setId(business.getId());
            businessToShows.add(businessToShow);
//            System.out.println(componentsInfDAO.queryComponentsById(business.getComponentId()).getName());
        }

//        List<ComponentsInf> componentsInfs = componentsInfDAO.queryAllComponentsInfs();
//        //把作用域提升到session，免得考虑如何传参给iframe
//        req.getSession().setAttribute("businesses", businesses);
//        req.getSession().setAttribute("components", componentsInfs);
//        req.getSession().setAttribute("map", map);
//        req.getRequestDispatcher("business.jsp").forward(req, resp);

        resp.setContentType("application/json;charset=utf-8");

        System.out.println(JSON.toJSONString(new ResMessage(200, "succeed", businessToShows)));
        resp.getWriter().write(JSON.toJSONString(new ResMessage(200, "succeed", businessToShows), filter));

    }


    /**
     * 通过名称找到零件
     * @param pattern
     * @return
     */
    private List<BusinessToShow> queryBusinessToShowByComponentName(String pattern){
        BusinessDAO businessDAO = new BusinessDAO();
        List<Business> businesses = businessDAO.queryAllBusinesses();
        List<BusinessToShow> businessToShows = new ArrayList<>();

        BusinessToShow businessToShow = null;
        //key----business,value----component'name
        //显示零件名称
        ComponentsInfDAO componentsInfDAO = new ComponentsInfDAO();
        for (Business business : businesses) {
//            map.put(business, componentsInfDAO.queryComponentsById(business.getComponentId()).getName());
            //找到零件名字符合的business
            String componentName = componentsInfDAO.queryComponentsById(business.getComponentId()).getName();
            System.out.println(componentName+"----match1----"+pattern);
            //用不了正则，这里代替
            if (componentName.contains(pattern)||componentName.toLowerCase().contains(pattern.toLowerCase())){
                System.out.println(componentName+"----match");
                businessToShow = new BusinessToShow();
                businessToShow.setBusiness(business.getBusiness());
                businessToShow.setComponentId(business.getComponentId());
                businessToShow.setComponentName(componentName);
                businessToShow.setId(business.getId());
                businessToShows.add(businessToShow);
            }

//            System.out.println(componentsInfDAO.queryComponentsById(business.getComponentId()).getName());
        }

//        List<ComponentsInf> componentsInfs = componentsInfDAO.queryAllComponentsInfs();
//        //把作用域提升到session，免得考虑如何传参给iframe
//        req.getSession().setAttribute("businesses", businesses);
//        req.getSession().setAttribute("components", componentsInfs);
//        req.getSession().setAttribute("map", map);
//        req.getRequestDispatcher("business.jsp").forward(req, resp);

        return businessToShows;
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

        int componentId = Integer.parseInt(req.getParameter("componentId"));
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
            System.out.println(JSON.toJSONString(new ResMessage(0, "succeed", "增加成功")));
            resp.getWriter().write(JSON.toJSONString(new ResMessage(0, "succeed", "增加成功"), filter));
            return;
        }

        if (isOK==true){

            System.out.println(JSON.toJSONString(new ResMessage(0, "succeed", "增加成功")));
            resp.getWriter().write(JSON.toJSONString(new ResMessage(0, "succeed", "增加成功"), filter));
//            resp.getWriter().write("1");


        }else {
//            resp.getWriter().write("2");

            System.out.println(JSON.toJSONString(new ResMessage(0, "fail", "更新失败")));
            resp.getWriter().write(JSON.toJSONString(new ResMessage(0, "fail", "更新失败"), filter));
        }

//        showBusiness(req, resp);
    }


    //批量删除
    public void batchDelete(HttpServletRequest request, HttpServletResponse response) {

        BusinessDAO businessDAO = new BusinessDAO();
        String ids = request.getParameter("ids");
        Set<Integer> idSet = new HashSet<>();
        ResMessage message = null;

        //判断是否可以一并删除
        boolean flag = true;

        System.out.println(ids + "   isaidai");

        response.setContentType("application/json;charset=utf-8");

        try {
            if (checkField(ids)) {
                String[] split = ids.split(",");
                for (String s : split) {
                    idSet.add(Integer.parseInt(s));
                }

                for (Integer integer : idSet) {
                    flag = checkToDelete(integer)&&flag;
                    if (!flag){
                        break;
                    }
                }

                if (flag){
                    for (Integer integer : idSet){
                        businessDAO.deleteBusinessById(integer);
                    }
                    System.out.println(JSON.toJSONString(new ResMessage(0, "succeed", "删除成功")));
                    response.getWriter().write(JSON.toJSONString(new ResMessage(0, "succeed", "删除成功"), filter));

                }else {
                    System.out.println(JSON.toJSONString(new ResMessage(0, "fail", "批量删除失败")));
                    response.getWriter().write(JSON.toJSONString(new ResMessage(0, "fail", "批量删除失败"), filter));
                }

            }
        } catch (IOException e) {
            System.out.println(JSON.toJSONString(new ResMessage(0, "fail", "删除失败")));
            try {
                response.getWriter().write(JSON.toJSONString(new ResMessage(0, "fail", "删除失败"), filter));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }

    }

    private boolean checkField(String field) {
        return field != null && !"".equals(field);
    }


    private boolean checkToDelete(Integer businessId){
        //事务的id
        BusinessDAO businessDAO = new BusinessDAO();
        Business target = businessDAO.queryBusinessById(businessId);
        int business = businessDAO.queryBusinessById(businessId).getBusiness();
        int componentId = businessDAO.queryBusinessById(businessId).getComponentId();

        System.out.println(businessId);
        System.out.println(business);

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

//                    resp.getWriter().write("1");

                    return true;
                }else if (newInventory==0){
//                    inventoryList.setInventory(newInventory);
                    inventoryListDAO.deleteInventoryListById(inventoryList.getId());
                    //可以删除
//                    businessDAO.deleteBusinessById(businessId);
//                    resp.getWriter().write("1");

                    return true;
                }else {
//                    resp.getWriter().write("2");
                    return false;
                }


            }
        }
        //没有找到
        if (!isFound){
            if (business>0){
                //不可以删除
//                resp.getWriter().write("2");
                return false;
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
//                businessDAO.deleteBusinessById(businessId);
//                resp.getWriter().write("1");

                return true;
            }
        }


        return false;
    }
}
