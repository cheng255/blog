package org.example.servlet;


import org.example.dao.LoginDao;
import org.example.exception.AppException;
import org.example.model.JSONResponse;
import org.example.model.User;
import org.example.util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author nuonuo
 * @create 2020-12-01 15:25
 */
@WebServlet("/login")
public class LoginServlet extends AbstractBaseServlet {

    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp) {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        //登录有三种情况  1.用户不存在 2.用户名或密码错误 3.登录成功
        User user = LoginDao.query(username);
        if (user == null) {
            throw new AppException("LOG002", "用户不存在");
        }
        if (!user.getPassword().equals(password)) {
            throw new AppException("LOG003", "用户名或密码错误");
        }
        //登录成功，创建Session对话
        HttpSession session = req.getSession();
        session.setAttribute("user", user);
        return null;
    }
}
