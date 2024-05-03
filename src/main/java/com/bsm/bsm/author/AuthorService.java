package com.bsm.bsm.author; // Đảm bảo package phù hợp với lớp Author

import com.bsm.bsm.commonInterface.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorService implements Activable, Searchable<Author>, Sortable<Author>, Updatable<Author>, Addable<Author>, Displayable<Author> {
    private final AuthorDAO authorDAO;

    public AuthorService() {
        this.authorDAO = new AuthorDAO();
    }

    @Override
    public List<Author> display() {
        return authorDAO.getAllAuthors();
    }

    @Override
    public boolean update(Author author) {
        return authorDAO.updateAuthor(author.getId(), author.getName(), author.getIntroduction());
    }

    @Override
    public List<Author> sort(List<Author> authors, boolean isAscending, String column) {
        List<Author> sortedAuthors = new ArrayList<>(authors);
        Comparator<Author> comparator = (author1, author2) -> {
            switch(column) {
                case "id" -> {
                    int authorID1 = Integer.parseInt(author1.getId());
                    int authorID2 = Integer.parseInt(author2.getId());
                    return Integer.compare(authorID1, authorID2);
                }
                case "name" -> {
                    return Comparator.comparing(Author::getName).compare(author1, author2);
                }
                case "introduction" -> {
                    return Comparator.comparing(Author::getIntroduction).compare(author1, author2);
                }
                case "enable/disable" -> {
                    return Comparator.comparing(Author::isEnabled).compare(author1, author2);
                }
                default -> {
                    return 0;
                }
            }
        };

        if (!isAscending) {
            comparator = comparator.reversed();
        }

        return sortedAuthors.stream().sorted(comparator).collect(Collectors.toList());
    }

    @Override
    public List<Author> search(String keyword) {
        List<Author> authors = display();
        String finalKeyword = keyword.toLowerCase();
        return authors.stream()
                .filter(author ->
                        author.getName().toLowerCase().contains(finalKeyword) ||
                                author.getIntroduction().toLowerCase().contains(finalKeyword) ||
                                author.getId().contains(finalKeyword))
                .collect(Collectors.toList());
    }

    @Override
    public boolean add(Author item) {
        return authorDAO.addAuthor(item.getName(), item.getIntroduction());
    }

    @Override
    public boolean setEnable(String id, boolean state) {
        return state;
    }

    public boolean enableAuthor(String authorId) {
        try {
            return authorDAO.enableBookAuthor(authorId);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean disableAuthor(String authorId) {
        try {
            return authorDAO.disableBookAuthor(authorId);
        } catch (Exception e) {
            return false;
        }
    }

    // use this to check case update author
    public boolean checkAuthorExists (String name, String id) {
        return authorDAO.checkAuthorExists(name, id);
    }

    //use this to check case add new author
    public boolean checkAuthorExists (String name) {
        return checkAuthorExists(name, "");
    }


    public Author getAuthor(String id) {
        return authorDAO.getAuthorById(id);
    }

    public Author getAuthorByName(String name) {
        return authorDAO.getAuthorByName(name);
    }

    public boolean isEnabled(String id) {
        return getAuthor(id).isEnabled();
    }

}