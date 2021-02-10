package org.example.filter;

import org.example.model.JSONResponse;
import org.example.util.JSONUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Filter对验证Session统一处理
 * <p>
 * 配置用户统一会话管理的过滤器：匹配所有请求路径
 * 1.服务器资源：/login不用校验Session,其他都要校验
 * 如果不通过 返回401响应码
 * 2.前端资源：/jsp/*需要校验Session，不通过重定向到登录页面
 * 3./js/* /static/* /view/*全部不校验
 * @author nuonuo
 * @create 2021-01-14 21:16
 */
@WebFilter("/*") //****
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String servletPath = req.getServletPath();//获取当前请求的服务路径
        //1.不需要验证session的
        if (servletPath.startsWith("/login") ||
        servletPath.startsWith("/js/") ||
        servletPath.startsWith("/static/") ||
        servletPath.startsWith("/view/")) {
            chain.doFilter(request,response);
        } else {
            //需要验证session
            HttpSession session = req.getSession(false);
            if (session == null || session.getAttribute("user") == null) {
                //2.session验证失败
                if (servletPath.startsWith("/jsp/")) {
                    //重定向到登录页面
                    resp.sendRedirect(basePath(req) + "/view/login.html");
                } else {
                    //返回401响应码
                    resp.setStatus(401);
                    resp.setCharacterEncoding("UTF-8");
                    resp.setContentType("application/json");
                    JSONResponse json = new JSONResponse();
                    json.setCode("LOG000");
//                    json.setSuccess(false);
                    json.setMessage("用户没有登录，不允许访问");
                    PrintWriter writer = resp.getWriter();
                    writer.println(JSONUtil.serialize(json));
                    writer.flush();
                    writer.close();
                }
            } else {
                //3.session验证成功
                chain.doFilter(request,response);
            }

        }
    }
    /**
     * 根据http请求 动态获取访问路径（服务路径之前的部分）
     * @param req
     * @return
     */
    public static String basePath(HttpServletRequest req) {
        String schema = req.getScheme();//获取http
        String host = req.getServerName();//主机ip或域名
        int port = req.getServerPort();//服务器端口号
        String contextPath = req.getContextPath();//应用上下文路径
        return schema + "://" + host + ":" + port + contextPath;
    }

    @Override
    public void destroy() {

    }
}
