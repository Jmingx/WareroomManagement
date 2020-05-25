package club.jming.servlet;

import club.jming.dao.ComponentsInfDAO;
import club.jming.dao.InventoryListDAO;
import club.jming.dao.OrderInfDAO;
import club.jming.dao.SupplierInfDAO;
import club.jming.entity.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ValueFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ReportServlet extends HttpServlet {

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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        if (type.equals("show")) {
            reportShow(req, resp);
        }

        //模糊搜索
        if (type.equals("search")){
            search(req,resp);
        }
    }

    /**
     * 先把原来的order数据更新一次（删除原来数据，再整理数据）
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void reportShow(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderInfDAO orderInfDAO = new OrderInfDAO();
        //清理上一次的order
        List<OrderInf> orderInfs1 = orderInfDAO.queryAllOrderInfs();
        if (orderInfs1!=null){
            for (OrderInf orderInf : orderInfs1){
                orderInfDAO.deleteOrderById(orderInf.getId());
            }
        }

        //更新这一次的
        InventoryListDAO inventoryListDAO = new InventoryListDAO();
        List<InventoryList> inventoryLists = inventoryListDAO.queryAllInventoryList();
//        整理好order
        for (InventoryList inventoryList : inventoryLists) {
            int amount = (inventoryList.getCriticalValue()-inventoryList.getInventory());
            if (amount > 0) {
                OrderInf orderInf = new OrderInf();
                orderInf.setComponentId(inventoryList.getComponentId());
                orderInf.setAmount(amount);
                orderInfDAO.addOrderInf(orderInf);
            }
        }

        //生成报表
        ComponentsInfDAO componentsInfDAO = new ComponentsInfDAO();
        SupplierInfDAO supplierInfDAO = new SupplierInfDAO();

        List<OrderInf> orderInfs = orderInfDAO.queryAllOrderInfs();
        List<Report> reportList = new ArrayList<Report>();
        Report report ;
        //所有的总价
        double summary = 0;
        for (OrderInf orderInf : orderInfs){
            report = new Report();
            //零件编号
            int componentId = orderInf.getComponentId();
            //零件
            ComponentsInf componentsInf = componentsInfDAO.queryComponentsById(componentId);
            //供应商编号
            int supplierId = componentsInf.getSupplierId();
            //供应商
            SupplierInf supplierInf = supplierInfDAO.querySupplierById(supplierId);
            //供应商名称
            String supplierName = supplierInf.getName();
            //供应商联系地址
            String supplierContact = supplierInf.getContact();
            //零件名称
            String componentName =componentsInf.getName();
            //价格
            double price = componentsInf.getPrice();
            //数量
            int amount = orderInf.getAmount();
            //总价
            double total = (double) price*orderInf.getAmount();

            report.setAmount(amount);
            report.setComponentId(componentId);
            report.setComponentName(componentName);
            report.setSupplierContact(supplierContact);
            report.setSupplierName(supplierName);
            report.setPrice(price);
            report.setTotal(total);

            reportList.add(report);

            summary+=total;
        }

//        System.out.println(reportList+"---------->");
//        req.getSession().setAttribute("summary",summary);
//        req.getSession().setAttribute("reportList",reportList);
//        req.getRequestDispatcher("/report.jsp").forward(req, resp);
        resp.setContentType("application/json;charset=utf-8");
        System.out.println(JSON.toJSONString(new ResMessage(200,"succeed",reportList)));
        resp.getWriter().write(JSON.toJSONString(new ResMessage(200,"succeed",reportList),filter));
    }

    //模糊搜索
    private void search(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pattern = req.getParameter("componentName");

        //转换字符集
        byte[] bytes=  pattern.getBytes("ISO-8859-1");
        pattern = new String(bytes,"UTF-8");

        System.out.println(pattern);
        List<Report> reports = fuzzySearch(req,resp);

        resp.setContentType("application/json;charset=utf-8");

        System.out.println(JSON.toJSONString(new ResMessage(200, "succeed", reports)));
        resp.getWriter().write(JSON.toJSONString(new ResMessage(200, "succeed", reports), filter));
    }

    private List<Report> fuzzySearch(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        OrderInfDAO orderInfDAO = new OrderInfDAO();
        //清理上一次的order
        List<OrderInf> orderInfs1 = orderInfDAO.queryAllOrderInfs();
        if (orderInfs1!=null){
            for (OrderInf orderInf : orderInfs1){
                orderInfDAO.deleteOrderById(orderInf.getId());
            }
        }

        //更新这一次的
        InventoryListDAO inventoryListDAO = new InventoryListDAO();
        List<InventoryList> inventoryLists = inventoryListDAO.queryAllInventoryList();
//        整理好order
        for (InventoryList inventoryList : inventoryLists) {
            int amount = (inventoryList.getCriticalValue()-inventoryList.getInventory());
            if (amount > 0) {
                OrderInf orderInf = new OrderInf();
                orderInf.setComponentId(inventoryList.getComponentId());
                orderInf.setAmount(amount);
                orderInfDAO.addOrderInf(orderInf);
            }
        }

        //生成报表
        ComponentsInfDAO componentsInfDAO = new ComponentsInfDAO();
        SupplierInfDAO supplierInfDAO = new SupplierInfDAO();

        List<OrderInf> orderInfs = orderInfDAO.queryAllOrderInfs();
        List<Report> reportList = new ArrayList<Report>();
        Report report ;
        //所有的总价
        double summary = 0;
        for (OrderInf orderInf : orderInfs){
            report = new Report();
            //零件编号
            int componentId = orderInf.getComponentId();
            //零件
            ComponentsInf componentsInf = componentsInfDAO.queryComponentsById(componentId);
            //供应商编号
            int supplierId = componentsInf.getSupplierId();
            //供应商
            SupplierInf supplierInf = supplierInfDAO.querySupplierById(supplierId);
            //供应商名称
            String supplierName = supplierInf.getName();
            //供应商联系地址
            String supplierContact = supplierInf.getContact();
            //零件名称
            String componentName =componentsInf.getName();
            //价格
            double price = componentsInf.getPrice();
            //数量
            int amount = orderInf.getAmount();
            //总价
            double total = (double) price*orderInf.getAmount();

            report.setAmount(amount);
            report.setComponentId(componentId);
            report.setComponentName(componentName);
            report.setSupplierContact(supplierContact);
            report.setSupplierName(supplierName);
            report.setPrice(price);
            report.setTotal(total);

            reportList.add(report);

            summary+=total;
        }

        String pattern = req.getParameter("componentName");

        //转换字符集
        byte[] bytes=  pattern.getBytes("ISO-8859-1");
        pattern = new String(bytes,"UTF-8");

        pattern = pattern;

        List<Report> reports = new ArrayList<>();

        //不符合的就移除
        for (Report report1 : reportList){
            String componentName = report1.getComponentName();
            if (componentName.contains(pattern)||componentName.toLowerCase().contains(pattern.toLowerCase())){
                reports.add(report1);
            }
        }

        return reports;

    }

    private boolean checkField(String field) {
        return field != null && !"".equals(field);
    }
}

