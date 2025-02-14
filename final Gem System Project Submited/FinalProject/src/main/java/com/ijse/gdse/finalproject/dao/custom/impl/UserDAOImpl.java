package com.ijse.gdse.finalproject.dao.custom.impl;

import com.ijse.gdse.finalproject.dao.custom.UserDAO;
import com.ijse.gdse.finalproject.dto.UserDTO;
import com.ijse.gdse.finalproject.dao.SQLUtil;

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

    public ArrayList<UserDTO> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from user");

        ArrayList<UserDTO> userDTOS = new ArrayList<>();

        while (rst.next()){
            UserDTO userDTO = new UserDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)

            );
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }

    public boolean save(UserDTO userDTO) throws SQLException {
        return SQLUtil.execute(
                "insert into user(user_id,user_name,role,password) values (?,?,?,?)",
                userDTO.getUserId(),
                userDTO.getUsername(),
                userDTO.getRole(),
                userDTO.getPassword()
        );
    }

    @Override
    public boolean update(UserDTO DTO) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String Id) throws SQLException {
        return false;
    }

    public UserDTO getUserDetails(String username) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM user WHERE user_name = ?", username);
        UserDTO userDTO = new UserDTO();
        try {

            if (rst.next()) {
                userDTO.setUserId(rst.getString(1));
                userDTO.setUsername(rst.getString(2));
                userDTO.setRole(rst.getString(3));
                userDTO.setPassword(rst.getString(4));


            } else {
                System.out.println("No user found with the specified Name.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving user details: " + e.getMessage());
        }
        return userDTO;
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

