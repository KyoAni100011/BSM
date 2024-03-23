package com.bsm.bsm.category;

public class UpdateCategoryService {

    private final UpdateCategoryDAO updateCategoryDAO;

    public UpdateCategoryService() {
        updateCategoryDAO = new UpdateCategoryDAO();
    }

    public boolean updateCategory(String id, String newName, String description) {
        return updateCategoryDAO.updateCategory(id, newName, description);
    }

    public Category getCategory(String id) {
        return updateCategoryDAO.getCategory(id);
    }
}
