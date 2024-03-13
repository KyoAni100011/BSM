package com.bsm.bsm.author; // Đảm bảo package phù hợp với lớp Author

import com.bsm.bsm.generic.*;

import java.util.List;

public class AuthorService implements Activable<Author>, Searchable<Author>, Sortable<Author>, Updatable<Author>, Addable<Author> {
    private AuthorDAO authorDAO = null;

    public AuthorService() {
        this.authorDAO = new AuthorDAO();
    }

    @Override
    public void activate(Author item) {
        item.setEnabled(true);
        authorDAO.update(item);
    }

    @Override
    public void deactivate(Author item) {
        item.setEnabled(false);
        authorDAO.update(item);
    }

    @Override
    public boolean isActive(Author item) {
        // Implement isActive logic
        return false;
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
}
