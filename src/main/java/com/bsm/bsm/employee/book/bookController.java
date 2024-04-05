package com.bsm.bsm.employee.book;

import com.bsm.bsm.book.BookService;
import com.bsm.bsm.book.Book;


import com.bsm.bsm.utils.AlertUtils;
import com.bsm.bsm.utils.FXMLLoaderHelper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.*;

public class bookController implements Initializable {
    private static String isbn;
    private final ToggleGroup toggleGroup = new ToggleGroup();
    private final BookService bookService = new BookService();

    @FXML
    public SVGPath quantitySortLabel,actionSortLabel ,bookNameSortLabel, idSortLabel,priceSortLabel;
    @FXML
    public Button bookNameLabel,actionLabel,priceLabel,quantityLabel,idLabel,outOfStockBookButton,updateBookButton,filterBookButton,addBookButton,bookButton,newBookButton;

    @FXML
    private VBox pnItems;

    @FXML
    public Button previousPaginationButton, nextPaginationButton, firstPaginationButton, secondPaginationButton, thirdPaginationButton, fourthPaginationButton, fifthPaginationButton;

    @FXML
    private TextField inputSearch;
    private Book bookInfo ;

    private List<Book> book = null;
    private String sortOrder = "ASC";
    private String column = "id";
    private String type;
    private int currentPage = 1;
    private String inputSearchText = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("hi");
        book = new ArrayList<>();
        book.add(new Book("1034067320125","Giao thua","44441111","",new Date(),"",true,100, BigDecimal.valueOf(61600.00)));
        initializeButtonsAndLabels();
        loadAllBooks();
        initializePaginationButtons();

        inputSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                inputSearchText = newValue;
//                book = bookService.search(inputSearchText, bookInfo.getIsbn());
                try {
                    updateBooksList();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    static void handleTableItemSelection(String bookIsbn) {
        isbn = bookIsbn; // Store the selected book
    }
    private void loadAllBooks() {
//        book = bookService.getAllBooksBySortInfo(bookInfo.getId(), sortOrder, column);
//        bookInfo.setBooks(book);

        try {
            type = "book";
            updateBooksList();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    private void initializeButtonsAndLabels() {
        bookButton.getStyleClass().add("profile-setting-button");
        updateButtonStyle(bookButton);
        newBookButton.getStyleClass().add("profile-setting-button");
        outOfStockBookButton.getStyleClass().add("profile-setting-button");

        idLabel.setOnMouseClicked(this::handleLabelClick);
        bookNameLabel.setOnMouseClicked(this::handleLabelClick);
        priceLabel.setOnMouseClicked(this::handleLabelClick);
        quantityLabel.setOnMouseClicked(this::handleLabelClick);
        actionLabel.setOnMouseClicked(this::handleLabelClick);

        idSortLabel.setContent("");
        quantitySortLabel.setContent("");
        bookNameSortLabel.setContent("");
        actionSortLabel.setContent("");
        priceSortLabel.setContent("");

        addBookButton.setOnAction(this::handleAddUserButton);
        filterBookButton.setOnAction(this::handlePasswordResetButton);
        updateBookButton.setOnAction(this::handleUpdateUserButton);
        bookButton.setOnAction(this::handleBookButton);
        newBookButton.setOnAction(this::handleNewBookButton);
        outOfStockBookButton.setOnAction(this::handleOutOfStockBookButton);
    }

    private void initializePaginationButtons() {
        firstPaginationButton.setOnAction(this::handlePaginationButton);
        secondPaginationButton.setOnAction(this::handlePaginationButton);
        thirdPaginationButton.setOnAction(this::handlePaginationButton);
        fourthPaginationButton.setOnAction(this::handlePaginationButton);
        fifthPaginationButton.setOnAction(this::handlePaginationButton);
        previousPaginationButton.setOnAction(this::handlePaginationButton);
        nextPaginationButton.setOnAction(this::handlePaginationButton);
    }

    @FXML
    private void handleBookButton(ActionEvent event) {
        type = "book";
        currentPage = 1;
//            updateBooksList();
        updateButtonStyle(bookButton);
    }

    @FXML
    private void handleNewBookButton(ActionEvent event) {
        try {
            type = "newBook";
            currentPage = 1;
            updateBooksList();
            updateButtonStyle(newBookButton);
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtils.showAlert("Error", "Error loading admin book", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleOutOfStockBookButton(ActionEvent event) {
        try {
            type = "outOfStockBook";
            currentPage = 1;
            updateBooksList();
            updateButtonStyle(outOfStockBookButton);
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtils.showAlert("Error", "Error loading admin book", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handlePasswordResetButton(ActionEvent event) {
//        try {
//            if (isbn != null) {
//                PasswordResetController.handleTableItemSelection(isbn);
//                FXMLLoaderHelper.loadFXML(new Stage(), "admin/bookBook/passwordReset");
//            } else {
//                AlertUtils.showAlert("Error", "Can't find book", Alert.AlertType.ERROR);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            AlertUtils.showAlert("Error", "Error loading passwordReset FXML", Alert.AlertType.ERROR);
//        }
    }

    @FXML
    private void handleUpdateUserButton(ActionEvent event) {
//        try {
//            if (isbn != null) {
//                UpdateUserController.handleTableItemSelection(isbn);
//                FXMLLoaderHelper.loadFXML(new Stage(), "admin/bookBook/updateUser");
//            } else {
//                AlertUtils.showAlert("Error", "Can't find book", Alert.AlertType.ERROR);
//            }
//        } catch (IOException e) {
//            AlertUtils.showAlert("Error", "Error loading updateUser FXML", Alert.AlertType.ERROR);
//        }
    }

    @FXML
    private void handleAddUserButton(ActionEvent event) {
//        try {
//            FXMLLoaderHelper.loadFXML(new Stage(), "admin/bookBook/addUser");
//            book = bookService.getAllBooksBySortInfo(UserSingleton.getInstance().getUser().getId(), sortOrder, column);
//            updateBooksList();
//        } catch (IOException e) {
//            e.printStackTrace();
//            AlertUtils.showAlert("Error", "Error loading addUser FXML", Alert.AlertType.ERROR);
//        }
    }

    @FXML
    private void handlePaginationButton(ActionEvent event) {
        Button buttonClicked = (Button) event.getSource();
        if (buttonClicked == previousPaginationButton) {
            currentPage--;
        } else if (buttonClicked == nextPaginationButton) {
            currentPage++;
        } else {
            currentPage = Integer.parseInt(buttonClicked.getText());
        }
        if (bookButton.getStyleClass().contains("profile-setting-button-admin")) {
            try {
                type = "book";
                updateBooksList();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (newBookButton.getStyleClass().contains("profile-setting-button-admin")) {
            try {
                type = "newBook";
                updateBooksList();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                type = "outOfStockBook";
                updateBooksList();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updatePaginationButtons(int totalPages) {
        // Clear all pagination buttons visibility
        List<Button> paginationButtons = Arrays.asList(firstPaginationButton, secondPaginationButton, thirdPaginationButton, fourthPaginationButton, fifthPaginationButton);
        paginationButtons.forEach(button -> {
            button.setVisible(false);
            button.setManaged(false);
            button.getStyleClass().add("pagination-button-admin");
        });

        // Show pagination buttons based on the current page and total pages
        if (totalPages > 1) {
            int startPage = Math.max(1, Math.min(currentPage - 2, totalPages - 4));
            int endPage = Math.min(startPage + 4, totalPages);

            previousPaginationButton.setDisable(!(currentPage > 1));
            nextPaginationButton.setDisable(!(currentPage < totalPages));

            for (int i = startPage; i <= endPage; i++) {
                Button button;
                int buttonIndex = i - startPage;
                if (totalPages > 5 && startPage < 6) {
                    button = paginationButtons.get(buttonIndex);
                } else if (totalPages > 5) {
                    button = paginationButtons.get(buttonIndex + 1);
                } else {
                    button = paginationButtons.get(buttonIndex);
                }
                button.setText(String.valueOf(i));
                button.setManaged(true);
                button.setVisible(true);

                if (i == currentPage) {
                    button.setStyle("-fx-background-color: #914d2a; -fx-text-fill: white;");
                } else {
                    button.setStyle(null);
                }
            }
        } else {
            previousPaginationButton.setDisable(true);
            firstPaginationButton.setText("1");
            firstPaginationButton.setVisible(true);
            firstPaginationButton.setManaged(true);
            firstPaginationButton.setStyle("-fx-background-color: #914d2a; -fx-text-fill: white;");
            nextPaginationButton.setDisable(true);
        }
    }

    private void updateButtonStyle(Button activeButton) {

        if (activeButton == bookButton) {
            outOfStockBookButton.getStyleClass().remove("profile-setting-button-admin");
            bookButton.getStyleClass().add("profile-setting-button-admin");
            newBookButton.getStyleClass().remove("profile-setting-button-admin");
        }else if (activeButton == newBookButton) {
            outOfStockBookButton.getStyleClass().remove("profile-setting-button-admin");
            newBookButton.getStyleClass().add("profile-setting-button-admin");
            bookButton.getStyleClass().remove("profile-setting-button-admin");
        } else {
            newBookButton.getStyleClass().remove("profile-setting-button-admin");
            outOfStockBookButton.getStyleClass().add("profile-setting-button-admin");
            bookButton.getStyleClass().remove("profile-setting-button-admin");
        }
    }

    private void updateBooksList() throws IOException {
        pnItems.getChildren().clear();
        int itemsPerPage = 9;
        int startIndex = (currentPage - 1) * itemsPerPage;
        int totalUserCountForRole = getTotalBookCountForRole(type);
        int endIndex = Math.min(startIndex + itemsPerPage, totalUserCountForRole);
        int totalCount = 0;

        for (int i = startIndex; i < book.size() && totalCount < endIndex; i++) {
            Book b = book.get(i);
            if (Objects.equals(type, "book") || Objects.equals(type, "newBook") || Objects.equals(type, "outOfStockBook") ){
                totalCount++;
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/bsm/bsm/view/employee/book/tableItem.fxml"));
                    Node item = fxmlLoader.load();
                    TableItemController tableItemController = fxmlLoader.getController();
                    tableItemController.setToggleGroup(toggleGroup);
                    tableItemController.setBook(b);
                    pnItems.getChildren().add(item);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("\n\n");

        int totalPages = (int) Math.ceil((double) totalUserCountForRole / itemsPerPage);
        updatePaginationButtons(totalPages);
    }

    private int getTotalBookCountForRole(String type) {
        int count = 0;
        for (Book books : book) {
            if (Objects.equals(type, "book") || Objects.equals(type, "newBook") || Objects.equals(type, "outOfStockBook"))
            {
                count++;
            }
        }
        return count;
    }


    @FXML
    private void handleLabelClick(MouseEvent event) {
        Button clickedLabel = (Button) event.getSource();
        String columnName = clickedLabel.getText();
        if (columnName.equals(column)) {
            sortOrder = sortOrder.equals("ASC") ? "DESC" : "ASC";
        } else {
            sortOrder = "ASC";
        }

        column = columnName;

        idSortLabel.setContent(column.equals("ISBN") ? (sortOrder.equals("ASC") ? "M233.4 105.4c12.5-12.5 32.8-12.5 45.3 0l192 192c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L256 173.3 86.6 342.6c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3l192-192z" : "M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z") : "");
        quantitySortLabel.setContent(column.equals("Quantity") ? (sortOrder.equals("ASC") ? "M233.4 105.4c12.5-12.5 32.8-12.5 45.3 0l192 192c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L256 173.3 86.6 342.6c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3l192-192z" : "M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z") : "");
        bookNameSortLabel.setContent(column.equals("Book Name") ? (sortOrder.equals("ASC") ? "M233.4 105.4c12.5-12.5 32.8-12.5 45.3 0l192 192c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L256 173.3 86.6 342.6c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3l192-192z" : "M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z") : "");
        actionSortLabel.setContent(column.equals("Action") ? (sortOrder.equals("ASC") ? "M233.4 105.4c12.5-12.5 32.8-12.5 45.3 0l192 192c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L256 173.3 86.6 342.6c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3l192-192z" : "M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z") : "");
        priceSortLabel.setContent(column.equals("Price") ? (sortOrder.equals("ASC") ? "M233.4 105.4c12.5-12.5 32.8-12.5 45.3 0l192 192c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L256 173.3 86.6 342.6c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3l192-192z" : "M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z") : "");
        try {
            updateBooksList();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        book = bookService.getAllBooksBySortInfo(bookInfo.getId(), sortOrder, column);
        try {
            updateBooksList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
