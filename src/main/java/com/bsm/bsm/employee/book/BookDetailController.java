package com.bsm.bsm.employee.book;

import com.bsm.bsm.admin.userAccount.UserDetailController;
import com.bsm.bsm.author.Author;
import com.bsm.bsm.book.Book;
import com.bsm.bsm.book.BookService;
import com.bsm.bsm.category.Category;
import com.bsm.bsm.utils.NumericValidationUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BookDetailController {
    public ScrollPane scrollPanelAuthor;
    public ScrollPane scrollPanelCategory;
    public Button button;
    @FXML
    private TextField fullNameField,bookPriceField,bookQuantityField,publisherField,languageField;
    @FXML
    private DatePicker releaseDatePicker;
    @FXML
    private Button isEnabledLabel;
    @FXML
    private VBox pnAuthor,pnCate;
    @FXML
    private TextField idField;
    @FXML
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final BookService bookService = new BookService();
    private static String id;
    private static Book bookDetail = null;

    @FXML
    public void initialize() {
        new UserDetailController();
        setupDatePicker();
        setBookInfo();
        releaseDatePicker.getEditor().setOpacity(1);
        isEnabledLabel.setOpacity(1);

    }
    public static void handleTableItemSelection(String myId) {
        id = myId;
        bookDetail = bookService.getBookByISBN(id);
        System.out.println("book" + bookDetail);
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
        idField.setText(bookDetail.getIsbn());
        bookPriceField.setText(String.valueOf(bookDetail.getSalePrice()));
        bookQuantityField.setText(String.valueOf(bookDetail.getQuantity()));
        isEnabledLabel.setText(bookDetail.isEnabled() ? "Enable" : "Disable");
        if (bookDetail.isEnabled()) {
            isEnabledLabel.getStyleClass().add("enable-button");
        } else {
            isEnabledLabel.getStyleClass().add("disable-button");
        }
    }
    private void setupDatePicker() {
        releaseDatePicker.setPromptText("dd/mm/yyyy");

        releaseDatePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return date != null ? dateFormatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                return string != null && !string.isEmpty() ? LocalDate.parse(string, dateFormatter) : null;
            }
        });

        releaseDatePicker.getEditor().addEventFilter(KeyEvent.KEY_TYPED, NumericValidationUtils.numericValidation(10));
    }
}
