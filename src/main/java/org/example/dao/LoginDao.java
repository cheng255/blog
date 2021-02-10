package org.example.dao;

import org.example.exception.AppException;
import org.example.model.User;
import org.example.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * @author nuonuo
 * @create 2021-01-14 16:15
 */
public class LoginDao {
    public static User query(String username) {
        Connection connection = null;//获取数据库连接
        PreparedStatement ps = null;
        ResultSet rs = null;//结果集
        User user = null;
        try {
            connection = JDBCUtil.getConnection();
            ps = connection.prepareStatement//预处理
                    ("select id, username, password, nickname, sex," +
                            " birthday, head from user where username=?");
            ps.setString(1, username);//设置占位符
            rs = ps.executeQuery();
            //处理结果集
            user = null;
            while (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(username);
                user.setPassword(rs.getString("password"));
                user.setNickname(rs.getString("nickname"));
                user.setSex(rs.getBoolean("sex"));
                //java中一般使用java.util.Date(年月日时分秒)
                //rs.getDate() 返回值时java.sql.Date（时分秒）
                //rs.getTimestamp() 返回值设计java.sql.Timestamp(年月日时分秒)
                java.sql.Date birthday = rs.getDate("birthday");
                if (birthday != null) {
                    user.setBirthday(new Date(birthday.getTime()));
                }
                user.setHead(rs.getString("head"));
            }
        } catch (SQLException e) {
            throw new AppException("JDBC002", "查询操作出错", e);
        } finally {
            JDBCUtil.closeResource(connection, ps, rs);
        }
        return user;
    }
}
