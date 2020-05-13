package com.jali.shiro;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author lijiang
 * @create 2020-05-13 14:44
 */
public class DAO {
    public DAO() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/shiro?characterEncoding=UTF-8","root","root");
    }

    /**
     * 获取用户的密码
     * @param userName
     * @return
     */
    public String getPassword(String userName){
        String sql = "select password from user where name = ?";
        try(Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)){
            // 填写第一个问号占位符
            ps.setString(1,userName);
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()){
                return resultSet.getString("password");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取用户的角色
     * @param userName
     * @return
     */
    public Set<String> listRoles(String userName){
        Set<String> roles = new HashSet<>();
        String sql = "select r.name from user u " +
                "left join user_role ur on u.id = ur.uid " +
                "left join role r on r.id = ur.rid " +
                "where u.name = ?";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                roles.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }

    /**
     * 获取用户的权限
     * @param userName
     * @return
     */
    public Set<String> listPermissions(String userName){
        Set<String> permissions = new HashSet<>();
        String sql = "select p.name from user u " +
                "left join user_role ru on u.id = ru.uid " +
                "left join role r on r.id = ru.rid " +
                "left join role_permission rp on r.id = rp.rid " +
                "left join permission p on p.id = rp.pid " +
                "where u.name = ?";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
            ps.setString(1, userName);
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                permissions.add(resultSet.getString(1));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return permissions;
    }

    public static void main(String[] args) {
        System.out.println("\nzhang3的信息");
        System.out.println("密码：" + new DAO().getPassword("zhang3"));
        System.out.println("角色：" + new DAO().listRoles("zhang3"));
        System.out.println("权限：" + new DAO().listPermissions("zhang3"));

        System.out.println("\nli4的信息");
        System.out.println("密码：" + new DAO().getPassword("li4"));
        System.out.println("角色：" + new DAO().listRoles("li4"));
        System.out.println("权限：" + new DAO().listPermissions("li4"));

        System.out.println("\nwang5的信息");
        System.out.println("密码：" + new DAO().getPassword("wang5"));
        System.out.println("角色：" + new DAO().listRoles("wang5"));
        System.out.println("权限：" + new DAO().listPermissions("wang5"));

    }

}













