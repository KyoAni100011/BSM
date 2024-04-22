package com.bsm.bsm.employee.book;

import com.bsm.bsm.admin.userAccount.UserDetailController;
import com.bsm.bsm.author.Author;
import com.bsm.bsm.author.AuthorService;
import com.bsm.bsm.book.Book;
import com.bsm.bsm.book.BookService;
import com.bsm.bsm.category.Category;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BookDetailController {
    @FXML
    private TextField fullNameField,bookPriceField,bookQuantityField,publisherField,languageField;
    @FXML
    private DatePicker releaseDatePicker;
    @FXML
    private Button isEnabledLabel;
    @FXML
    private VBox pnAuthor,pnCate;
    @FXML
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final BookService bookService = new BookService();
    private static String id;
    private static Book bookDetail = null;

    @FXML
    public void initialize() {
        new UserDetailController();
        setBookInfo();
        releaseDatePicker.getEditor().setOpacity(1);
        isEnabledLabel.setOpacity(1);
    }
    public static void handleTableItemSelection(String myId) {
        id = myId;
        bookDetail = bookService.getBookByISBN(id);
    }
//    private VBox pnAuthor,pnCate;

    private void setBookInfo() {
        List<Author> au = bookDetail.getAuthors();

        for (Author author : au) {

        }
        for(int i = 0; i < au.size(); i++){
            if(i == au.size() - 1){
                Label l = new Label(au.get(i).getName());
                pnAuthor.getChildren().add(l);
            } else{
                Label l = new Label(au.get(i).getName() +",");
                pnAuthor.getChildren().add(l);
            }
        }
        List<Category> cate = bookDetail.getCategories();

        for(int i = 0; i < cate.size(); i++){
            if(i == cate.size() - 1){
                Label l = new Label(cate.get(i).getName());
                pnCate.getChildren().add(l);
            } else{
                Label l = new Label(cate.get(i).getName() +",");
                pnCate.getChildren().add(l);
            }
        }

        releaseDatePicker.setValue(LocalDate.parse(bookDetail.getPublishingDate(), dateFormatter));
        publisherField.setText(bookDetail.getPublisher().getName());
        languageField.setText(bookDetail.getLanguages());
        fullNameField.setText(bookDetail.getTitle());
        bookPriceField.setText(String.valueOf(bookDetail.getSalePrice()));
        bookQuantityField.setText(String.valueOf(bookDetail.getQuantity()));
        isEnabledLabel.setText(bookDetail.isEnabled() ? "Enable" : "Disable");
        if (bookDetail.isEnabled()) {
            isEnabledLabel.getStyleClass().add("enable-button");
        } else {
            isEnabledLabel.getStyleClass().add("disable-button");
        }
    }
}
