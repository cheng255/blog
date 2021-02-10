package org.example.servlet;

import org.example.dao.ArticleDao;
import org.example.model.Article;
import org.example.model.User;
import org.example.util.JSONUtil;

import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author nuonuo
 * @create 2021-01-15 20:21
 */
@WebServlet("/articleAdd")
public class ArticleAddServlet extends AbstractBaseServlet{
    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //将json文章反序列为java对象
        ServletInputStream is = req.getInputStream();
        Article a = JSONUtil.deserialize(is, Article.class);
        //文章表的user_id外键需要用session动态获取
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
        a.setUserId(user.getId());//设置用户id
        return ArticleDao.insert(a);
    }
}
