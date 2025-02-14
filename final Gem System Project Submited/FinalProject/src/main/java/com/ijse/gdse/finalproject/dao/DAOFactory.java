package com.ijse.gdse.finalproject.dao;

import com.ijse.gdse.finalproject.dao.custom.impl.*;

public class DAOFactory {

    private static DAOFactory daoFactory;

    private DAOFactory(){}

    public static DAOFactory getDaoFactory(){
        return (daoFactory==null) ? daoFactory = new DAOFactory():daoFactory;

    }

    public enum DAOType {
        CUSTOMER,GEM,ORDER,ORDERDETAILS, APPOINTMENT, SUPPLIER,CATEGORY,EMPLOYEE,USER,SUPPLIERORDER,SUPPLIERORDERDETAILS,LOGIN
    }

    public SuperDAO getDAO(DAOType type){
        switch (type){
            case CUSTOMER:
                return new CustomerDAOImpl();
            case LOGIN:
                return new LoginDAOImpl();
            case GEM:
                return new GemDAOImpl();
            case ORDER:
                return new OrdersDAOImpl();
            case ORDERDETAILS:
                return new OrderDetailsDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            case APPOINTMENT:
                return new AppointmentDAOImpl();
            case CATEGORY:
                return new CategoryDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case USER:
                return new UserDAOImpl();
            case SUPPLIERORDER:
                return new SupplierOrderDAOImpl();
            case SUPPLIERORDERDETAILS:
                return new SupplierOrderDetailsDAOImpl();
            default:
                return null;
        }
    }
}
