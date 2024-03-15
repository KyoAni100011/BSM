package com.bsm.bsm.admin.userAccount;
import com.bsm.bsm.user.UserModel;
import com.bsm.bsm.utils.DateUtils;

public class UpdateUserService {
    private static UpdateUserDAO updateUserDAO = null;

    public UpdateUserService() {
        updateUserDAO = new UpdateUserDAO();
    }

    public boolean updateUserProfile(String id, String fullName, String telephone, String dob, String address) {
        String formattedDOB = DateUtils.formatDOB(dob);
        return updateUserDAO.updateProfile(id, fullName, telephone, formattedDOB, address);
    }
    public static UserModel getUserProfile(String email) {
        return updateUserDAO.getUserProfile(email);
    }


}
