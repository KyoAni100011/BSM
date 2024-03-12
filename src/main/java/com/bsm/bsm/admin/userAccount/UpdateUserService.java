package com.bsm.bsm.admin.userAccount;
import com.bsm.bsm.utils.DateUtils;

public class UpdateUserService {
    private final UpdateUserDAO updateUserDAO;

    public UpdateUserService() {
        this.updateUserDAO = new UpdateUserDAO();
    }

    public boolean updateUserProfile(String id, String fullName, String telephone, String dob, String address) {
        String formattedDOB = DateUtils.formatDOB(dob);
        return updateUserDAO.updateProfile(id, fullName, telephone, formattedDOB, address);
    }


}
