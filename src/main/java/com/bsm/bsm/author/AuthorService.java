package com.bsm.bsm.author; // Đảm bảo package phù hợp với lớp Author

import com.bsm.bsm.commonInterface.*;

import java.util.List;

public class AuthorService implements Activable, Searchable<Author>, Sortable<Author>, Updatable<Author>, Addable<Author> {
    private AuthorDAO authorDAO = null;

    public AuthorService() {
        this.authorDAO = new AuthorDAO();
    }

    @Override
    public boolean update(Author item) {
        return authorDAO.update(item);
    }

    @Override
    public List<Author> sort(List<Author> authors, boolean isAscending, String column) {
        // Implement sorting logic
        return null;
    }

    @Override
    public List<Author> search(String keyword) {
        // Implement search logic
        return null;
    }

    @Override
    public boolean add(Author item) {
        // Implement add logic
        return true;
    }

    @Override
    public boolean setEnable(boolean state) {

        return state;
    }

    public boolean checkAuthorExists (String name) {
        return authorDAO.checkAuthorExists(name);
    }

    public boolean addAuthor (String name, String introduction) {
        return authorDAO.addAuthor(name, introduction);
    }

    public boolean updateAuthor(String oldName, String newName, String introduction) {
        return authorDAO.updateAuthor(oldName, newName, introduction);
    }

    public Author getAuthor(String name) {
        return authorDAO.getAuthor(name);
    }
}
