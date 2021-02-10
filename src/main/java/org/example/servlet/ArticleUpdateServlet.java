package org.example.servlet;

import org.example.dao.ArticleDao;
import org.example.model.Article;
import org.example.util.JSONUtil;

import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author nuonuo
 * @create 2021-01-15 19:46
 */
@WebServlet("/articleUpdate")
public class ArticleUpdateServlet extends AbstractBaseServlet{
    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        InputStream is = req.getInputStream();
        Article a = JSONUtil.deserialize(is, Article.class);
        int num = ArticleDao.update(a);
        return num;
    }
}
