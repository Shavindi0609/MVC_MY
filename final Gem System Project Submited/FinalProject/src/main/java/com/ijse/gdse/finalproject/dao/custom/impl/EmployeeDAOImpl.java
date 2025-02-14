package com.ijse.gdse.finalproject.dao.custom.impl;

import com.ijse.gdse.finalproject.dao.custom.EmployeeDAO;
import com.ijse.gdse.finalproject.db.DBConnection;
import com.ijse.gdse.finalproject.dto.EmployeeDTO;
import com.ijse.gdse.finalproject.dao.SQLUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {

    public  String getNext() throws SQLException {
        ResultSet rst = SQLUtil.execute("select employee_id from employee order by employee_id desc limit 1");

        if(rst.next()){
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIndex = i+1;
            return  String.format("E%03d", newIndex);
        }
        return "E001";
    }

    public boolean save(EmployeeDTO employeeDTO) throws SQLException {
        return SQLUtil.execute(
                "insert into employee(employee_id , name , nic , email , phone , address,user_id) values (?,?,?,?,?,?,?)",
                employeeDTO.getEmployeeId(),
                employeeDTO.getName(),
                employeeDTO.getNic(),
                employeeDTO.getEmail(),
                employeeDTO.getPhone(),
                employeeDTO.getAddress(),
                employeeDTO.getUserId()
        );
    }

    public ArrayList<EmployeeDTO> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from employee");

        ArrayList<EmployeeDTO> employeeDTOS = new ArrayList<>();

        while (rst.next()){
            EmployeeDTO employeeDTO = new EmployeeDTO(
                    rst.getString(1),  // Customer ID
                    rst.getString(2),  // Name
                    rst.getString(3),  // NIC
                    rst.getString(4),  // Email
                    rst.getString(5),   //phone
                    rst.getString(6),
                    rst.getString(7)
                    //address
            );
            employeeDTOS.add(employeeDTO);
        }
        return employeeDTOS;
    }

    public boolean delete(String employeeId) throws SQLException {
        return SQLUtil.execute("delete from employee where employee_id=?" , employeeId);
    }

    public boolean update(EmployeeDTO employeeDTO) throws SQLException {
        return SQLUtil.execute(
                "update employee set name=?, nic=?, email=?, phone=?, address=? , user_id=? where employee_id=?",
                employeeDTO.getName(),
                employeeDTO.getNic(),
                employeeDTO.getEmail(),
                employeeDTO.getPhone(),
                employeeDTO.getAddress(),
                employeeDTO.getUserId(),
                employeeDTO.getEmployeeId()
        );
    }

    public int getEmployeeCount() throws SQLException {
        String query = "SELECT COUNT(*) FROM employee";
        ResultSet rst = SQLUtil.execute(query);
        if (rst.next()) {
            return rst.getInt(1);
        }
        return 0;
    }

    public int getTotCount() throws SQLException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String d1 = LocalDate.now().format(dateTimeFormatter);

        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "select SUM(s.total_amount) from payment s join orders o on s.payment_id = o.payment_id where order_date = ? ";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, d1);
        ResultSet rst = statement.executeQuery();
        if (rst.next()) {
            return rst.getInt(1);
        }
        return 0;

    }
}
