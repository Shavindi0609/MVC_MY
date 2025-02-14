package com.ijse.gdse.finalproject.dao.custom.impl;

import com.ijse.gdse.finalproject.dao.custom.CustomerDAO;
import com.ijse.gdse.finalproject.dao.SQLUtil;
import com.ijse.gdse.finalproject.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {

    public String getNext() throws SQLException {
        ResultSet rst = SQLUtil.execute("select customer_id from customer order by customer_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("C%03d", newIdIndex);
        }
        return "C001";
    }

    public boolean save(Customer customer) throws SQLException {
        return SQLUtil.execute(
                "insert into customer(customer_id , name , nic , email , phone , address,user_id) values (?,?,?,?,?,?,?)",
                customer.getCustomerId(),
                customer.getName(),
                customer.getNic(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getAddress(),
                customer.getUserId()
        );
    }

    public ArrayList<Customer> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from customer");

        ArrayList<Customer> customerDTOS = new ArrayList<>();

        while (rst.next()) {
            Customer customer = new Customer(
                    rst.getString(1),  // Customer ID
                    rst.getString(2),  // Name
                    rst.getString(3),  // NIC
                    rst.getString(4),  // Email
                    rst.getString(5),   //phone
                    rst.getString(6),
                    rst.getString(7)
            );
            customerDTOS.add(customer);
        }
        return customerDTOS;
    }

    public boolean update(Customer customer) throws SQLException {
        return SQLUtil.execute(
                "update customer set name=?, nic=?, email=?, phone=?, address=? , user_id=? where customer_id=?",
                customer.getName(),
                customer.getNic(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getAddress(),
                customer.getUserId(),
                customer.getCustomerId()
        );
    }

    public boolean delete(String customerId) throws SQLException {
        return SQLUtil.execute("delete from customer where customer_id=?", customerId);
    }

    public ArrayList<String> getAllCustomerIds() throws SQLException {
        ResultSet rst = SQLUtil.execute("select customer_id from customer");

        ArrayList<String> customerIds = new ArrayList<>();

        while (rst.next()) {
            customerIds.add(rst.getString(1));
        }

        return customerIds;
    }

    public Customer findById(String selectedCusId) throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from customer where customer_id=?", selectedCusId);

        if (rst.next()) {
            return new Customer(
                    rst.getString(1),  // Customer ID
                    rst.getString(2),  // Name
                    rst.getString(3),  // NIC
                    rst.getString(4),  // Email
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)
            );
        }
        return null;
    }

    public int getCustomerCount() throws SQLException {
        String query = "SELECT COUNT(*) FROM customer";
        ResultSet rst = SQLUtil.execute(query);
        if (rst.next()) {
            return rst.getInt(1);
        }
        return 0;
    }
}
