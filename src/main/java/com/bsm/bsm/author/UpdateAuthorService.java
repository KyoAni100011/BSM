package com.bsm.bsm.author;

public class UpdateAuthorService {

    private final UpdateAuthorDAO updateAuthorDAO;

    public UpdateAuthorService() {
        updateAuthorDAO = new UpdateAuthorDAO();
    }

    public boolean updateAuthor(String oldName, String newName, String introduction) {
        return updateAuthorDAO.updateAuthor(oldName, newName, introduction);
    }

    public Author getAuthor(String name) {
        return updateAuthorDAO.getAuthor(name);
    }
}
