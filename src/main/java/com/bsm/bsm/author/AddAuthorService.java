package com.bsm.bsm.author;

public class AddAuthorService {

    private final AddAuthorDAO addAuthorDAO;

    public AddAuthorService() {
        addAuthorDAO = new AddAuthorDAO();
    }

    public boolean checkAuthorExists (String name) {
        return addAuthorDAO.checkAuthorExists(name);
    }

    public boolean addAuthor (String name, String introduction) {
        return addAuthorDAO.addAuthor(name, introduction);
    }
}
