package club.jming.servlet;

import club.jming.dao.ComponentsInfDAO;
import club.jming.dao.InventoryListDAO;
import club.jming.dao.SupplierInfDAO;
import club.jming.entity.ComponentsInf;
import club.jming.entity.InventoryList;
import club.jming.entity.ResMessage;
import club.jming.entity.SupplierInf;
import club.jming.utils.ResMessageBuilder;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ValueFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 事务servlet，对事务信息做处理
 */
public class ComponentServlet extends HttpServlet {

    ValueFilter filter = new ValueFilter() {
        @Override
        public Object process(Object object, String name, Object value) {
            if (value instanceof BigDecimal || value instanceof Double || value instanceof Float) {
                DecimalFormat df = new java.text.DecimalFormat("#.00");
                return df.format(value);
            }
            return value;
        }
    };

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("--component---post");
        insertComponent(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        System.out.println(type + "--component");

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
//        byte[] bytes=  pattern.getBytes("ISO-8859-1");
//        pattern = new String(bytes,"UTF-8");

        System.out.println(pattern);
        List<ComponentsInf> componentsInfs = new ComponentsInfDAO().fuzzySearch(pattern);

        resp.setContentType("application/json;charset=utf-8");

        System.out.println(JSON.toJSONString(new ResMessage(200, "succeed", componentsInfs)));
        resp.getWriter().write(JSON.toJSONString(new ResMessage(200, "succeed", componentsInfs), filter));
    }


    private void deleteComponent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        System.out.println(id);

        ComponentsInfDAO componentsInfDAO = new ComponentsInfDAO();
        resp.setContentType("application/json;charset=utf-8");
        if (componentsInfDAO.queryComponentsById(id) != null) {
            componentsInfDAO.deleteComponentsInfById(id);

            System.out.println(JSON.toJSONString(new ResMessage(0, "succeed", "删除成功")));
            resp.getWriter().write(JSON.toJSONString(new ResMessage(0, "succeed", "删除成功"), filter));
        } else {
            System.out.println(JSON.toJSONString(new ResMessage(200, "fail", "删除失败")));
            resp.getWriter().write(JSON.toJSONString(new ResMessage(200, "fail", "删除失败"), filter));
        }


    }

    //批量删除
    public void batchDelete(HttpServletRequest request, HttpServletResponse response) {

        ComponentsInfDAO componentsInfDAO = new ComponentsInfDAO();
        String ids = request.getParameter("ids");
        Set<Integer> idSet = new HashSet<>();
        ResMessage message = null;

        System.out.println(ids + "   isaidai");

        response.setContentType("application/json;charset=utf-8");

        try {
            if (checkField(ids)) {
                String[] split = ids.split(",");
                for (String s : split) {
                    idSet.add(Integer.parseInt(s));
                }

                for (Integer integer : idSet) {
                    componentsInfDAO.deleteComponentsInfById(integer);
                }

                System.out.println(JSON.toJSONString(new ResMessage(0, "succeed", "删除成功")));
                response.getWriter().write(JSON.toJSONString(new ResMessage(0, "succeed", "删除成功"), filter));
            }
        } catch (IOException e) {
            System.out.println(JSON.toJSONString(new ResMessage(200, "fail", "删除失败")));
            try {
                response.getWriter().write(JSON.toJSONString(new ResMessage(200, "fail", "删除失败"), filter));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }


    }


    private void updateComponent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int componentId = Integer.parseInt(req.getParameter("id"));
        String componentName = req.getParameter("name");
        double price = Double.parseDouble(req.getParameter("price"));
        int supplierId = Integer.parseInt(req.getParameter("supplierId"));

        if (price<=0){
            System.out.println(JSON.toJSONString(new ResMessage(0, "fail", "删除成功")));
            resp.getWriter().write(JSON.toJSONString(new ResMessage(0, "fail", "删除成功"), filter));
            return;
        }

        //处理GET乱码问题
        byte[] bytes =  componentName.getBytes("ISO-8859-1");
        componentName = new String(bytes,"UTF-8");


        ComponentsInfDAO componentsInfDAO = new ComponentsInfDAO();
        ComponentsInf componentsInf = componentsInfDAO.queryComponentsById(componentId);

        resp.setContentType("application/json;charset=utf-8");

        componentsInf.setSupplierId(supplierId);
        componentsInf.setPrice(price);
        componentsInf.setName(componentName);
        componentsInf.setId(componentId);
        componentsInfDAO.updateComponentsInf(componentsInf);

        System.out.println(JSON.toJSONString(new ResMessage(0, "succeed", "删除成功")));
        resp.getWriter().write(JSON.toJSONString(new ResMessage(0, "succeed", "删除成功"), filter));

    }

    private void showComponent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ComponentsInfDAO componentsInfDAO = new ComponentsInfDAO();
        List<ComponentsInf> componentsInfs = componentsInfDAO.queryAllComponentsInfs();
//
//        SupplierInfDAO supplierInfDAO = new SupplierInfDAO();
//        List<SupplierInf> supplierInfs = supplierInfDAO.queryAllSuppliers();
//
//        req.getSession().setAttribute("components", componentsInfs);
//        req.getSession().setAttribute("suppliers", supplierInfs);
//        req.getRequestDispatcher("component.jsp").forward(req, resp);


        resp.setContentType("application/json;charset=utf-8");
        System.out.println(JSON.toJSONString(new ResMessage(200, "succeed", componentsInfs)));
        resp.getWriter().write(JSON.toJSONString(new ResMessage(200, "succeed", componentsInfs), filter));
    }

    private void insertComponent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String componentName = req.getParameter("name");
        Integer supplierId = Integer.parseInt(req.getParameter("supplierId"));
        double price = Double.parseDouble(req.getParameter("price"));

        if (price<=0){
            System.out.println(JSON.toJSONString(new ResMessage(0, "fail", "删除成功")));
            resp.getWriter().write(JSON.toJSONString(new ResMessage(0, "fail", "删除成功"), filter));
            return;
        }

//        SupplierInfDAO supplierInfDAO = new SupplierInfDAO();
//        List<SupplierInf> supplierInfs = supplierInfDAO.queryAllSuppliers();
        //通过名称找到供应商ID
//        SupplierInf target = null;
////        System.out.println(supplierName);
////        System.out.println(supplierInfs);
//        for (SupplierInf supplierInf : supplierInfs) {
////            System.out.println(supplierInf);
//            if (supplierInf.getName().equals(supplierId)) {
//                target = supplierInf;
//            }
//        }
//        int supplierId = target.getId();

//        数据合理
        //Component存储
        ComponentsInfDAO componentsInfDAO = new ComponentsInfDAO();
        ComponentsInf componentsInf = new ComponentsInf();
        componentsInf.setName(componentName);
        componentsInf.setPrice(price);
        componentsInf.setSupplierId(supplierId);
        componentsInfDAO.addComponentsInf(componentsInf);

        resp.setContentType("application/json;charset=utf-8");
        System.out.println(JSON.toJSONString(new ResMessage(200, "succeed", "增加成功")));
        resp.getWriter().write(JSON.toJSONString(new ResMessage(0, "succeed", "增加成功"), filter));
    }


    private boolean checkField(String field) {
        return field != null && !"".equals(field);
    }
}
