package club.jming.servlet;

import club.jming.dao.ComponentsInfDAO;
import club.jming.dao.SupplierInfDAO;
import club.jming.entity.ComponentsInf;
import club.jming.entity.ResMessage;
import club.jming.entity.SupplierInf;
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
public class SupplierServlet extends HttpServlet {

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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        if (type.equals("insert")){
            insertSupplier(req,resp);
        }
        if (type.equals("update")){
            updateSupplier(req,resp);
        }


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        System.out.println(type+"---type----"+req.getParameter("supplierName"));
        if (type.equals("show")){
            showSupplier(req,resp);
        }
        if (type.equals("delete")){
            deleteSupplier(req,resp);
        }
        if (type.equals("update")){
            updateSupplier(req,resp);
        }
//        if (type.equals("insert")){
//            insertSupplier(req,resp);
//        }

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
        String pattern = req.getParameter("supplierName");

        //转换字符集
        byte[] bytes=  pattern.getBytes("ISO-8859-1");
        pattern = new String(bytes,"UTF-8");

        System.out.println(pattern);
        List<SupplierInf> supplierInfs = new SupplierInfDAO().fuzzySearch(pattern);

        resp.setContentType("application/json;charset=utf-8");

        System.out.println(JSON.toJSONString(new ResMessage(200, "succeed", supplierInfs)));
        resp.getWriter().write(JSON.toJSONString(new ResMessage(200, "succeed", supplierInfs), filter));
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

    //批量删除
    public void batchDelete(HttpServletRequest request, HttpServletResponse response) {

        SupplierInfDAO supplierInfDAO = new SupplierInfDAO();
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
                    supplierInfDAO.deleteSupplierById(integer);
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


    public void updateSupplier(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String contact = req.getParameter("contact");
        int id = Integer.parseInt(req.getParameter("id"));

        SupplierInf supplierInf = new SupplierInf();
        supplierInf.setId(id);
        supplierInf.setContact(contact);
        supplierInf.setName(name);

        new SupplierInfDAO().updateSupplier(supplierInf);

        resp.setContentType("application/json;charset=utf-8");
        System.out.println(JSON.toJSONString(new ResMessage(0,"succeed","更新成功")));
        resp.getWriter().write(JSON.toJSONString(new ResMessage(0,"succeed","更新成功"),filter));
//
//        showSupplier(req,resp);
    }

    private void showSupplier(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SupplierInfDAO supplierInfDAO = new SupplierInfDAO();
        List<SupplierInf> supplierInfs = supplierInfDAO.queryAllSuppliers();

//        req.getSession().setAttribute("suppliers",supplierInfs);
//        req.getRequestDispatcher("supplier.jsp").forward(req,resp);

        resp.setContentType("application/json;charset=utf-8");
        System.out.println(JSON.toJSONString(new ResMessage(200,"succeed",supplierInfs),filter));
        resp.getWriter().write(JSON.toJSONString(new ResMessage(200,"succeed",supplierInfs),filter));
    }

    private void insertSupplier(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SupplierInfDAO supplierInfDAO = new SupplierInfDAO();
        SupplierInf supplierInf = new SupplierInf();

        String name = req.getParameter("name");
        String contact = req.getParameter("contact");

        supplierInf.setName(name);
        supplierInf.setContact(contact);
        supplierInfDAO.addSupplier(supplierInf);

        resp.setContentType("application/json;charset=utf-8");
        System.out.println(JSON.toJSONString(new ResMessage(0,"succeed","增加成功")));
        resp.getWriter().write(JSON.toJSONString(new ResMessage(0,"succeed","增加成功"),filter));

//        showSupplier(req,resp);
    }


    private boolean checkField(String field) {
        return field != null && !"".equals(field);
    }

}
