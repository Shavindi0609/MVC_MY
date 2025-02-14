package com.ijse.gdse.finalproject.dao.custom.impl;

import com.ijse.gdse.finalproject.dao.custom.AppointmentDAO;
import com.ijse.gdse.finalproject.dao.SQLUtil;
import com.ijse.gdse.finalproject.entity.Appointment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AppointmentDAOImpl implements AppointmentDAO {

    public ArrayList<Appointment> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM appointment");

        ArrayList<Appointment> appointmentDTOS = new ArrayList<>();

        while (rst.next()){
            Appointment appointment = new Appointment(
                    rst.getString(1),  // Customer ID
                    rst.getString(2),  // Name
                    rst.getDate(3).toLocalDate(),  // NIC
                    rst.getTime(4).toLocalTime(),  // Email
                    rst.getBoolean(5)//address
            );
            appointmentDTOS.add(appointment);
        }
        return appointmentDTOS;
    }

    public String getNext() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT appointment_id FROM appointment ORDER BY appointment_id DESC LIMIT 1");

        if(rst.next()){
            String lastId = rst.getString(1);
            String substring = lastId.substring(3);
            int i = Integer.parseInt(substring);
            int newIndex = i+1;
            return  String.format("APP%03d", newIndex);
        }
        return "APP001";
    }

    public boolean save(Appointment appointment) throws SQLException {
        return SQLUtil.execute(
                "insert into appointment(appointment_id , customer_id , date , time , is_attendance ) values (?,?,?,?,?)",
                appointment.getAppointmentId(),
                appointment.getCustomerId(),
                appointment.getDate(),
                appointment.getTime(),
                appointment.getIsAttendance()

        );
    }

    public boolean delete(String appointmentId) throws SQLException {
        return SQLUtil.execute("delete from appointment where appointment_id=?" , appointmentId);
    }

    public boolean update(Appointment appointment) throws SQLException {
        return SQLUtil.execute(
                "UPDATE appointment SET customer_id=?, date=?, time=?, is_attendance=? WHERE appointment_id=?",
                appointment.getCustomerId(),   // 1st ?
                appointment.getDate(),         // 2nd ?
                appointment.getTime(),         // 3rd ?
                appointment.getIsAttendance(), // 4th ?
                appointment.getAppointmentId() // 5th ? (for WHERE clause)
        );

    }
}
