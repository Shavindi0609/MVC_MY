package com.ijse.gdse.finalproject.bo;

import com.ijse.gdse.finalproject.bo.custom.impl.*;

public class BOFactory {

    public static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getInstance() {
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOType {
        CUSTOMER,APPOINTMENT,CATEGORY,EMPLOYEE,GEM,LOGIN,USER,SUPPLIER,HOME,ORDERS,SUPPLIERORDERS
    }

    public SuperBO getBO(BOType type) {
        switch (type) {
            case CUSTOMER:
                return new CustomerBOImpl();
            case APPOINTMENT:
                return new AppointmentBOImpl();
            case CATEGORY:
                return new CategoryBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case GEM:
                return new GemBOImpl();
            case LOGIN:
                return new LoginBOImpl();
            case USER:
                return new UserBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            case HOME:
                return new HomePageBOImpl();
            case ORDERS:
                return new OrdersBOImpl();
            case SUPPLIERORDERS:
                return new SupplierOrderBOImpl();
            default:
                return null;
        }
    }

}
