package org.example.servlet;
import org.example.dao.ArticleDao;
import org.example.exception.AppException;
import org.example.model.Article;
import org.example.model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author nuonuo
 * @create 2021-01-14 21:04
 */
@WebServlet("/articleList")
public class ArticleListServlet extends AbstractBaseServlet {
    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        if (session == null) {
            throw new AppException("ART002", "用户未登录，进制访问");
        }
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new AppException("ART003", "当前会话过期，请重新登录");
        }
        List<Article> articles = ArticleDao.queryByUserId(user.getId());
        return articles;
    }
}
