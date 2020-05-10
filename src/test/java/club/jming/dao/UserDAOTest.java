package club.jming.dao;

import club.jming.entity.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserDAOTest {

    @Test
    public void isRegister() {
        UserDAO userDAO = new UserDAO();
        User user = new User();
        user.setUsername("xjm");
        System.out.println(userDAO.isRegister(user));
    }

    @Test
    public void register() {
        UserDAO userDAO = new UserDAO();
        User user = new User();
        user.setUsername("xjm");
        user.setPassword("123456");
        userDAO.register(user);
    }
}