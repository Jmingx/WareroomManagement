package club.jming.servlet;

import club.jming.dao.InventoryListDAO;
import club.jming.entity.InventoryList;
import club.jming.entity.PageVo;
import club.jming.entity.ResMessage;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ValueFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

public class InventoryServlet extends HttpServlet {

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
        if (type.equals("show")){
            showInventory(req,resp);
        }
        if (type.equals("update")){
            update(req,resp);
        } else {
            System.out.println("error!");
        }
    }

    private void showInventory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<InventoryList> inventoryLists = new InventoryListDAO().queryAllInventoryList();

        resp.setContentType("application/json;charset=utf-8");
        System.out.println(JSON.toJSONString(new ResMessage(200,"succeed",inventoryLists)));
        resp.getWriter().write(JSON.toJSONString(new ResMessage(200,"succeed",inventoryLists),filter));
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer criticalValue = Integer.parseInt(req.getParameter("criticalValue"));
        Integer id = Integer.parseInt(req.getParameter("id"));
        Integer componentId = Integer.parseInt(req.getParameter("componentId"));
        Integer inventory = Integer.parseInt(req.getParameter("inventory"));

        if (criticalValue<=0){
            resp.setContentType("application/json;charset=utf-8");
            System.out.println(JSON.toJSONString(new ResMessage(0,"fail","更新失败")));
            resp.getWriter().write(JSON.toJSONString(new ResMessage(0,"fail","更新失败"),filter));
            return;
        }

        InventoryList inventoryList = new InventoryList();
        inventoryList.setId(id);
        inventoryList.setInventory(inventory);
        inventoryList.setCriticalValue(criticalValue);
        inventoryList.setComponentId(componentId);

        new InventoryListDAO().updateInventoryList(inventoryList);

        resp.setContentType("application/json;charset=utf-8");
        System.out.println(JSON.toJSONString(new ResMessage(0,"succeed","更新成功")));
        resp.getWriter().write(JSON.toJSONString(new ResMessage(0,"succeed","更新成功"),filter));
    }
}
