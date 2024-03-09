package com.bsm.bsm.employee;

import com.bsm.bsm.user.UserModel;

import java.util.ArrayList;
import java.util.List;

public class EmployeeModel extends UserModel {
    public EmployeeModel(String id, String name, String email, String dob, String phone, String address, boolean isEnabled) {
        super(id, name, email, dob, phone, address, isEnabled);
    }

}