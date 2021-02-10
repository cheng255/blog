package org.example.dao;

import org.example.exception.AppException;
import org.example.model.Article;
import org.example.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文章的数据库相关操作
 *
 * @author nuonuo
 * @create 2021-01-15 16:16
 */
public class ArticleDao {

    /**
     * 根据用户id查询文章列表
     *
     * @param userId
     * @return
     */
    public static List<Article> queryByUserId(Integer userId) {
        List<Article> articles = null;//文章列表
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            articles = new ArrayList<>();
            conn = JDBCUtil.getConnection();
            ps = conn.prepareStatement("select id, title from article where user_id=?");
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Article a = new Article();
                a.setId(rs.getInt("id"));
                a.setTitle(rs.getString("title"));
                articles.add(a);
            }
            return articles;
        } catch (SQLException e) {
            throw new AppException("ART001", "查询文章列表出错", e);
        } finally {
            JDBCUtil.closeResource(conn, ps, rs);
        }
    }

    /**
     * 根据用户id查询文章详情
     *
     * @param userId
     * @return
     */
    public static Article query(int userId) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Article a = null;
        try {
            conn = JDBCUtil.getConnection();
            ps = conn.prepareStatement("select title, content from article where id=?");
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            while (rs.next()) {
                a = new Article();
                a.setId(userId);
                a.setTitle(rs.getString("title"));
                a.setContent(rs.getString("content"));
            }
            return a;
        } catch (SQLException e) {
            throw new AppException("ART006", "查询文章详情出错", e);
        } finally {
            JDBCUtil.closeResource(conn, ps, rs);
        }
    }


    /**
     * 修改文章内容
     * @param a
     * @return
     */
    public static int update(Article a) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtil.getConnection();
            String sql = "update article set title=?, content=? where id=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, a.getTitle());
            ps.setString(2, a.getContent());
            ps.setInt(3, a.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new AppException("ART007", "修改文章信息出错", e);
        } finally {
            JDBCUtil.closeResource(conn, ps);
        }
    }

    public static int delete(String[] split) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtil.getConnection();
            StringBuilder sql = new StringBuilder("delete from article where id in (");
            for (int i = 0; i < split.length; i++) {
                if (i != 0) {
                    sql.append(",");
                }
                sql.append("?");
            }
            sql.append(")");
            ps = conn.prepareStatement(sql.toString());
            for (int i = 0; i < split.length; i++) {
                ps.setInt(i+1, Integer.parseInt(split[i]));
            }
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new AppException("ART004", "文章删除操作出错", e);
        } finally {
            JDBCUtil.closeResource(conn, ps);
        }
    }

    /**
     * 将Article对象的信息（文章信息）加入到数据库文章表
     * @param a
     * @return
     */
    public static int insert(Article a) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtil.getConnection();
            String sql = "insert into article(title, content, user_id)" +
                    " values (?, ?, ?)";
            ps = conn.prepareStatement(sql);
            //设置占位符
            ps.setString(1,a.getTitle());
            ps.setString(2,a.getContent());
            ps.setInt(3,a.getUserId());
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new AppException("ART005", "新增文章操作出错", e);
        } finally {
            JDBCUtil.closeResource(conn,ps);
        }
    }
}
