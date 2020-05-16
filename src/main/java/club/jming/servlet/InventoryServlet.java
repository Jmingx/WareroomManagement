package club.jming.servlet;

import club.jming.dao.InventoryListDAO;
import club.jming.entity.InventoryList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class InventoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        if (type.equals("show")){
            showInventory(req,resp);
        }else {
            System.out.println("error!");
        }
    }

    private void showInventory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<InventoryList> inventoryLists = new InventoryListDAO().queryAllInventoryList();
        req.getSession().setAttribute("inventories",inventoryLists);
        req.getRequestDispatcher("inventory.jsp").forward(req,resp);
    }
}
