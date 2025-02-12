package com.ijse.gdse.finalproject.model;

import com.ijse.gdse.finalproject.dto.AppointmentDTO;
import com.ijse.gdse.finalproject.dto.EmployeeDTO;
import com.ijse.gdse.finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AppointmentModel {
    public ArrayList<AppointmentDTO> getAllAppointments() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM appointment");

        ArrayList<AppointmentDTO> appointmentDTOS = new ArrayList<>();

        while (rst.next()){
            AppointmentDTO appointmentDTO = new AppointmentDTO(
                    rst.getString(1),  // Customer ID
                    rst.getString(2),  // Name
                    rst.getDate(3).toLocalDate(),  // NIC
                    rst.getTime(4).toLocalTime(),  // Email
                    rst.getBoolean(5)//address
            );
            appointmentDTOS.add(appointmentDTO);
        }
        return appointmentDTOS;
    }

    public String getNextAppointmentId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT appointment_id FROM appointment ORDER BY appointment_id DESC LIMIT 1");

        if(rst.next()){
            String lastId = rst.getString(1);
            String substring = lastId.substring(3);
            int i = Integer.parseInt(substring);
            int newIndex = i+1;
            return  String.format("APP%03d", newIndex);
        }
        return "APP001";
    }

    public boolean addAppointment(AppointmentDTO appointmentDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into appointment(appointment_id , customer_id , date , time , is_attendance ) values (?,?,?,?,?)",
                appointmentDTO.getAppointmentId(),
                appointmentDTO.getCustomerId(),
                appointmentDTO.getDate(),
                appointmentDTO.getTime(),
                appointmentDTO.getIsAttendance()

        );
    }

    public boolean deleteAppointment(String appointmentId) throws SQLException {
        return CrudUtil.execute("delete from appointment where appointment_id=?" , appointmentId);
    }

    public boolean updateAppointment(AppointmentDTO appointmentDTO) throws SQLException {
        return CrudUtil.execute(
                "UPDATE appointment SET customer_id=?, date=?, time=?, is_attendance=? WHERE appointment_id=?",
                appointmentDTO.getCustomerId(),   // 1st ?
                appointmentDTO.getDate(),         // 2nd ?
                appointmentDTO.getTime(),         // 3rd ?
                appointmentDTO.getIsAttendance(), // 4th ?
                appointmentDTO.getAppointmentId() // 5th ? (for WHERE clause)
        );

    }
}
