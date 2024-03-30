package com.bsm.bsm.author; // Đảm bảo package phù hợp với lớp Author

import com.bsm.bsm.commonInterface.*;

import java.util.List;

public class AuthorService implements Activable, Searchable<Author>, Sortable<Author>, Updatable<Author>, Addable<Author> {
    private final AuthorDAO authorDAO;

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
        return authorDAO.addAuthor(item.getName(), item.getIntroduction());
    }

    @Override
    public boolean setEnable(boolean state) {
        return state;
    }

    // use this to check case update author
    public boolean checkAuthorExists (String name, String id) {
        return authorDAO.checkAuthorExists(name, id);
    }

    //use this to check case add new author
    public boolean checkAuthorExists (String name) {
        return checkAuthorExists(name, "");
    }

    public boolean updateAuthor(Author author) {
        return authorDAO.updateAuthor(author.getId(), author.getName(), author.getIntroduction());
    }

    public Author getAuthor(String id) {
        return authorDAO.getAuthorById(id);
    }

    public boolean isEnabled(String id) {
        return getAuthor(id).isEnabled();
    }
}
