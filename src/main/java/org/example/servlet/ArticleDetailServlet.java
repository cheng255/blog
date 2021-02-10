package org.example.servlet;

import org.example.dao.ArticleDao;
import org.example.model.Article;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 查看文章细节的服务器
 * @author nuonuo
 * @create 2021-01-15 17:24
 */
@WebServlet("/articleDetail")
public class ArticleDetailServlet extends AbstractBaseServlet{

    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");
        Article a = ArticleDao.query(Integer.parseInt(id));
        return a;
    }
}
