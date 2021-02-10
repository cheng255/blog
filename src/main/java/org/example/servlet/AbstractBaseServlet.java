package org.example.servlet;

import org.example.exception.AppException;
import org.example.model.JSONResponse;
import org.example.util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 抽象服务端，作为所有服务端后端资源类的父类，进行统一的 基础设置 异常处理
 *
 * @author nuonuo
 * @create 2021-01-14 15:42
 */
public abstract class AbstractBaseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置请求体的编码格式
        req.setCharacterEncoding("UTF-8");
        //设置响应体的编码
        resp.setCharacterEncoding("UTF-8");
        //设置响应体的数据类型（浏览器要采取什么方式执行）
        resp.setContentType("application/json");
        JSONResponse json = new JSONResponse();
        try {
            Object data = process(req, resp);
            //子类的process方法执行完没有抛异常，表示业务执行成功
            json.setSuccess(true);
            json.setData(data);
        } catch (Exception e) {
            e.printStackTrace();
            String code = "UNKNOWN";
            String message = "未知的错误";
            if (e instanceof AppException) {
                code = ((AppException) e).getCode();
                message = e.getMessage();
            }
            json.setCode(code);
            json.setMessage(message);
        }
        PrintWriter pw = resp.getWriter();
        pw.println(JSONUtil.serialize(json));
        pw.flush();
        pw.close();
    }

    protected abstract Object process(HttpServletRequest req, HttpServletResponse resp) throws IOException;

}