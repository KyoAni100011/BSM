package com.bsm.bsm.employee.order;

import com.bsm.bsm.author.Author;
import com.bsm.bsm.book.Book;
import com.bsm.bsm.category.Category;
import com.bsm.bsm.employee.importSheet.ItemImportController;
import com.bsm.bsm.publisher.Publisher;
import com.bsm.bsm.utils.FXMLLoaderHelper;
import com.bsm.bsm.utils.ValidationUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.*;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class CreateOrderController implements Initializable {
    @FXML
    public TextField handleNameField,handlePhoneField,MoneyTextField;
    @FXML
    public Button handlePayButton , addBookButton ,refreshButton;
    @FXML
    public Label subtotalLabel, discountLabel ,totalLabel, totalQuantityItems,MoneyReturnLabel;
    @FXML
    public VBox pnItems = new VBox();

    private ObservableList<String> bookNames = FXCollections.observableArrayList(); // Store filtered category items
    private ObservableList<String> currentBookNamesData = FXCollections.observableArrayList(bookNames);
    private static List<OrderItemController> orderItemController ;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            MoneyTextField.setTextFormatter(new TextFormatter<>(integerFilter));
            handlePhoneField.setTextFormatter(new TextFormatter<>(integerFilter));

            bookNames.add("The Midnight Library by Matt Haig");
            bookNames.add("Where the Crawdads Sing by Delia Owens");
            bookNames.add("Educated: A Memoir by Tara Westover");
            bookNames.add("Becoming by Michelle Obama");
            bookNames.add("The Silent Patient by Alex Michaelides");
            orderItemController = new ArrayList<>();
            currentBookNamesData = bookNames;

            MoneyTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue != null  && !newValue.isEmpty() ){
                    MoneyReturnLabel.setText(String.valueOf(Integer.parseInt(newValue) - Integer.parseInt(totalLabel.getText().isEmpty() ? "0" : totalLabel.getText() )));
                } else {
                    MoneyReturnLabel.setText("0");

                }
            });
            handleNameField.textProperty().addListener((observable, oldValue, newValue) -> {
                if((newValue != null && !newValue.isEmpty()) && (handlePhoneField.getText().length() == 11 || handlePhoneField.getText().length() == 10)){
                    discountLabel.setText("(-30%)" +(int) (Integer.parseInt(subtotalLabel.getText()) * 0.3 ));
                    totalLabel.setText(String.valueOf( (int)( Integer.parseInt(subtotalLabel.getText()) * 0.7)));
                } else{
                    discountLabel.setText("0%");
                }
            });
            handlePhoneField.textProperty().addListener((observable, oldValue, newValue) -> {
                if((newValue.length() == 11 || newValue.length() == 10 ) && !handleNameField.getText().isEmpty()){
                    System.out.println("get in phone" +handleNameField.getText() );
                    discountLabel.setText("(-30%)" + (int)(Integer.parseInt(subtotalLabel.getText()) * 0.3) );
                    totalLabel.setText(String.valueOf((int) (Integer.parseInt(subtotalLabel.getText()) * 0.7)) );

                } else{
                    discountLabel.setText("0%");
                }
            });
        } catch (Exception e ){
            System.out.println("ini" + e);
        }
    }

    void handleTableItemSelection( int index) {
        try{
            if (index - 1 < 0 || index - 1 >= pnItems.getChildren().size()) {
                return;
            }
            orderItemController.remove(index - 1);
            pnItems.getChildren().remove(index - 1);
            for(int i = 0; i < orderItemController.size(); i++){
                orderItemController.get(i).setIndex(this,i + 1);

            }
            setAlreadyClick();
            handleCountSubtotalAndQuantity();
        } catch (Exception e){
            System.out.println("handle" + e);

        }


    }

    public void handleAddBookButton() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/bsm/bsm/view/employee/order/orderItem.fxml"));
            Node item = fxmlLoader.load();
            int size = orderItemController.size();
            pnItems.getChildren().add(item);
            orderItemController.add(fxmlLoader.getController()) ;
            orderItemController.get(size).setIndex(this,size + 1);
            orderItemController.get(size).setListBook(bookNames);
            orderItemController.get(size).setListBook(currentBookNamesData);

        } catch (Exception e) {
            System.out.println("this" +e.getMessage());
        }
    }

    public void setAlreadyClick() {
        try {
            ObservableList<String> tempBookNames = FXCollections.observableArrayList(bookNames);
            currentBookNamesData = tempBookNames;
            for (OrderItemController controller : orderItemController) {
                String selectedBookName = controller.getBookName();
                if (!selectedBookName.isEmpty()) {
                    tempBookNames.remove(selectedBookName);
                }
            }
            for (OrderItemController controller : orderItemController) {
                ObservableList<String> tempEachBookNames = FXCollections.observableArrayList(tempBookNames);
                String selectedBookName = controller.getBookName();
                if (!selectedBookName.isEmpty()) {
                    tempEachBookNames.add(selectedBookName);
                }
                controller.setListBook(tempEachBookNames);
            }
        } catch (Exception e){
            System.out.println("handle click" + e);
        }
    }
    public void handleCountSubtotalAndQuantity(){
        int Sub = 0, Quan = 0;
        for (OrderItemController controller : orderItemController) {
            Sub += controller.getSubtotal();
            Quan += controller.getItemQuantity();
        }
        subtotalLabel.setText(String.valueOf(Sub));
        if(!Objects.equals(discountLabel.getText(), "0%")){
            discountLabel.setText("(-30%)" + (int)(Integer.parseInt(subtotalLabel.getText()) * 0.3 ));
            totalLabel.setText(String.valueOf((int) (Integer.parseInt(subtotalLabel.getText()) * 0.7)));
        } else {
            totalLabel.setText(String.valueOf(Sub));
        }

        totalQuantityItems.setText("Total: "+ Quan+" items" );

    }
    private final UnaryOperator<TextFormatter.Change> integerFilter = change -> {
        if (Pattern.matches("\\d*", change.getControlNewText())) {
            return change;
        } else {
            return null;
        }
    };
    public void handleRefreshButton(ActionEvent actionEvent) {
    }

}
