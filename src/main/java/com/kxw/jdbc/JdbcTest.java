package com.kxw.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kangxiongwei on 2017/5/9.
 */
public class JdbcTest {

    public static void main(String[] args) throws Exception{
        String url = "jdbc:mysql://localhost:3306/kxw_test";
        String username = "root";
        String password = "kxw672015";
        Connection connection = DriverManager.getConnection(url, username, password);
        PreparedStatement ps = connection.prepareStatement("select * from kxw_user");
        ResultSet rs = ps.executeQuery();
        List<User> list = new ArrayList<>();
        while (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setNickname(rs.getString("nickname"));
            list.add(user);
        }
        rs.close();
        ps.close();
        connection.close();

        list.forEach(System.out::println);
    }


}
