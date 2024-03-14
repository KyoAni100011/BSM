package com.bsm.bsm.author; // Đảm bảo package phù hợp với lớp Author

import com.bsm.bsm.commonInterface.*;

import java.util.List;

public class AuthorService implements Activable, Searchable<Author>, Sortable<Author>, Updatable<Author>, Addable<Author> {
    private AuthorDAO authorDAO = null;

    public AuthorService() {
        this.authorDAO = new AuthorDAO();
    }

    @Override
    public void update(Author item) {
        authorDAO.update(item);
    }

    @Override
    public List<Author> sort(List<Author> items) {
        // Implement sorting logic
        return null;
    }

    @Override
    public List<Author> search(String keyword) {
        // Implement search logic
        return null;
    }

    @Override
    public void add(Author item) {
        // Implement add logic
    }

    @Override
    public void setEnable(boolean state) {

    }
}
