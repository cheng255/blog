package org.example.servlet;

import org.example.dao.ArticleDao;
import org.example.model.Article;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author nuonuo
 * @create 2021-01-15 20:04
 */
@WebServlet("/articleDelete")
public class ArticleDeleteServlet extends AbstractBaseServlet{
    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String ids = req.getParameter("ids");//根据前端抓包得到
        return ArticleDao.delete(ids.split(","));
    }
}
