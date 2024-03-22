package com.bsm.bsm.author;

public class AuthorDetailService {
    private final AuthorDetailDAO updateAuthorDAO;

    public AuthorDetailService() {
        updateAuthorDAO = new AuthorDetailDAO();
    }

    public Author getAuthor(String id) {
        return updateAuthorDAO.getAuthor(id);
    }
}
