package com.ijse.gdse.finalproject.dao.custom.impl;

import com.ijse.gdse.finalproject.dao.custom.UserDAO;
import com.ijse.gdse.finalproject.dao.SQLUtil;
import com.ijse.gdse.finalproject.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAOImpl implements UserDAO {
    public String getNext() throws SQLException {
        ResultSet rst = SQLUtil.execute("select user_id from user order by user_id desc limit 1");

        if(rst.next()){
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIndex = i+1;
            return  String.format("U%03d", newIndex);
        }
        return "U001";
    }

    public ArrayList<User> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from user");

        ArrayList<User> userDTOS = new ArrayList<>();

        while (rst.next()){
            User userDTO = new User(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)

            );
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }

    public boolean save(User user) throws SQLException {
        return SQLUtil.execute(
                "insert into user(user_id,user_name,role,password) values (?,?,?,?)",
                user.getUserId(),
                user.getUsername(),
                user.getRole(),
                user.getPassword()
        );
    }

    @Override
    public boolean update(User DTO) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String Id) throws SQLException {
        return false;
    }

    public User getUserDetails(String username) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM user WHERE user_name = ?", username);
        User user = new User();
        try {

            if (rst.next()) {
                user.setUserId(rst.getString(1));
                user.setUsername(rst.getString(2));
                user.setRole(rst.getString(3));
                user.setPassword(rst.getString(4));


            } else {
                System.out.println("No user found with the specified Name.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving user details: " + e.getMessage());
        }
        return user;
    }

    public ArrayList<String> getAllUserIds() throws SQLException {
        ResultSet rst = SQLUtil.execute("select user_id from user");
        ArrayList<String> userIds = new ArrayList<>();
        while (rst.next()) {
            userIds.add(rst.getString(1));
        }
        return userIds;
    }

    }

