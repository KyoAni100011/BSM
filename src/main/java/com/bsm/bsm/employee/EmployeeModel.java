package com.bsm.bsm.employee;

import com.bsm.bsm.author.Author;
import com.bsm.bsm.book.Book;
import com.bsm.bsm.category.Category;
import com.bsm.bsm.order.Order;
import com.bsm.bsm.publisher.Publisher;
import com.bsm.bsm.sheet.Sheet;
import com.bsm.bsm.user.UserModel;

import java.util.ArrayList;
import java.util.List;

public class EmployeeModel extends UserModel {
    private List<Book> books;
    private List<Author> authors;
    private List<Publisher> publishers;
    private List<Category> categories;
    private List<Sheet> importSlips;
    private List<Order> orders;


    public EmployeeModel(String id, String name, String email, String dob,
                         String phone, String address, boolean isEnabled, String lastLogin) {
        super(id, name, email, dob, phone, address, isEnabled, lastLogin);
        this.books = new ArrayList<>();
        this.authors = new ArrayList<>();
        this.publishers = new ArrayList<>();
        this.categories = new ArrayList<>();
        this.importSlips = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

}