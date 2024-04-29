package com.bsm.bsm.order;

import com.bsm.bsm.customer.Customer;
import com.bsm.bsm.customer.CustomerDAO;
import com.bsm.bsm.database.DatabaseConnection;
import com.bsm.bsm.employee.EmployeeModel;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class OrderDAO {

//    public static void main(String[] args) throws SQLException {
//        OrderDAO orderDAO = new OrderDAO();
//        Customer customer = new Customer("1", "Phuong", "0917527783", true);
//        EmployeeModel employee = new EmployeeModel("22220002", "", "", "", "", "", true, "");
//
//        Order order = new Order(
//                employee,
//                customer,
//                null
//        );
//        List<String> selectedBooks = new ArrayList<>(List.of("Toi thay hoa vang tren co xanh"));
//        List<Integer> quantities = new ArrayList<>(List.of(100));
//        List<BigDecimal> salePrices = new ArrayList<>(List.of(BigDecimal.valueOf(84000)));
//
//        orderDAO.createOrder(employee, selectedBooks, quantities, salePrices, customer);
//    }

    public boolean createOrder(EmployeeModel employee,List<String> selectedBooks, List<Integer> quantities, List<BigDecimal> salePrices, Customer customer) throws SQLException {

        Connection connection = DatabaseConnection.getConnection();
        connection.setAutoCommit(false);

        String employeeID = getEmployeeID(connection, employee);
        String customerID = CustomerDAO.getCustomerID(connection, customer);
        String orderID = getOrderID(connection, employeeID, customerID);

        if (orderID == null) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }

        for (int i = 0; i < selectedBooks.size(); i++) {
            System.out.println(selectedBooks.get(i));
            System.out.println(salePrices.get(i));

            int quantityInput = quantities.get(i);
            while (quantityInput > 0) {
                quantityInput = insertOrderBooksDetails(connection, orderID, selectedBooks.get(i), quantityInput, salePrices.get(i));
            }
        }

        //if customer is Member, discount 5%
        discountForCustomer(connection, orderID, customer);
        connection.setAutoCommit(true);
        return true;
    }

    private String getEmployeeID(Connection connection, EmployeeModel employee) throws SQLException {
        String QUERY_GET_EMPLOYEEID = "select e.id from user u join employee e on u.id = e.userID where u.id = ?";
        AtomicReference<String> employeeID = new AtomicReference<>();

        DatabaseConnection.executeQuery(connection, QUERY_GET_EMPLOYEEID, resultSet -> {
            if (resultSet != null && resultSet.next()) {
                employeeID.set(resultSet.getString("id"));
            }
        }, employee.getId());

        return employeeID.get();
    }

    private String getOrderID(Connection connection, String employeeID, String customerID) throws SQLException {
        if (createOrderID(connection, employeeID, customerID)) {
            String QUERY_GET_ORDER_ID = """
                    select max(id) as id
                    from orderSheet
                    where employeeID = ? and customerID = ?
                    """;
            AtomicReference<String> orderID = new AtomicReference<>();

            DatabaseConnection.executeQuery(connection, QUERY_GET_ORDER_ID, resultSet -> {
                if (resultSet != null && resultSet.next()) {
                    orderID.set(resultSet.getString("id"));
                }
            }, employeeID, customerID);

            return orderID.get();
        } else {
            return null;
        }
    }

    private boolean createOrderID(Connection connection, String employeeID, String customerID) throws SQLException {
        String QUERY_CREATE_ORDER = "insert into orderSheet (employeeID, customerID) values (?, ?) ";

        int rowAffected = DatabaseConnection.executeUpdate(connection, QUERY_CREATE_ORDER, employeeID, customerID);

        return rowAffected > 0;
    }

    private int insertOrderBooksDetails(Connection connection, String orderID, String bookName, int quantityInput, BigDecimal salePrice) throws SQLException {

        String bookISBN = getBookISBN(connection, bookName);

        Map<Integer, Integer> bookBatchIDAndQuantity = getIDAndQuanitityInBookBatch(connection, bookISBN, bookName, quantityInput);
        int bookBatchID = bookBatchIDAndQuantity.keySet().iterator().next();
        var quantity = bookBatchIDAndQuantity.values().iterator().next();

        String QUERY_INSERT_ORDER_BOOK_DETAILS = "insert into orderBooksDetails (orderID, bookBatchID, quantity, salePrice) values (?, ?, ?, ?)";
        int rowAffected = DatabaseConnection.executeUpdate(connection, QUERY_INSERT_ORDER_BOOK_DETAILS, orderID, bookBatchID, quantityInput, salePrice);

        if (checkRowAffected(connection, rowAffected)) {
            System.out.println("Insert orderBooksDetails success");
        } else {
            System.out.println("Insert orderBooksDetails failed");
        }

        String QUERY_UPDATE_QUANTITY_IN_BOOK_BATCH = "update bookBatch set quantity = ? where id = ?";

        String QUERY_QUANTITY_IN_BOOK = "select sum(quantity) as quantity from bookBatch where bookID = ?";
        AtomicReference<Integer> quantityInBook = new AtomicReference<>();
        DatabaseConnection.executeQuery(connection, QUERY_QUANTITY_IN_BOOK, resultSet -> {
            if (resultSet != null && resultSet.next()) {
                quantityInBook.set(resultSet.getInt("quantity"));
            }
        }, bookISBN);

        String QUERY_UPDATE_QUANTITY_IN_BOOK = "update book set quantity = ? where isbn = ?";
        DatabaseConnection.executeUpdate(connection, QUERY_UPDATE_QUANTITY_IN_BOOK, quantityInBook.get() - quantityInput, bookISBN);

        String QUERY_UPDATE_TOTAL_PRICE_IN_ORDER = "update orderSheet set totalPrice = totalPrice + ? where id = ?";
        DatabaseConnection.executeUpdate(connection, QUERY_UPDATE_TOTAL_PRICE_IN_ORDER, salePrice.multiply(BigDecimal.valueOf(quantityInput)), orderID);


        if (quantity >= quantityInput) {
            DatabaseConnection.executeUpdate(connection, QUERY_UPDATE_QUANTITY_IN_BOOK_BATCH, quantity - quantityInput, bookBatchID);
            quantityInput = 0;
        } else {
            DatabaseConnection.executeUpdate(connection, QUERY_UPDATE_QUANTITY_IN_BOOK_BATCH, 0, bookBatchID);
            quantityInput -= quantity;
        }

        return quantityInput;
    }

    private String getBookISBN(Connection connection, String bookName) {
        String QUERY_GET_BOOK_ID = "select isbn from book where title = ?";
        AtomicReference<String> bookID = new AtomicReference<>();
        DatabaseConnection.executeQuery(connection, QUERY_GET_BOOK_ID, resultSet -> {
            if (resultSet != null && resultSet.next()) {
                bookID.set(resultSet.getString("isbn"));
            }
        }, bookName);

        return bookID.get();
    }

    private Map<Integer, Integer> getIDAndQuanitityInBookBatch(Connection connection, String isbn, String bookName, int quantityInput) {
        String QUERY_GET_ID_AND_QUANTITY_IN_BOOK_BATCH = """
            select bb.id, bb.quantity
            from bookBatch bb join importsheet i on bb.importSheetID = i.id
            where bb.quantity > 0 and bookID = ?
            order by importDate limit 1
            """;

        AtomicReference<Integer> bookBatchID = new AtomicReference<>();
        AtomicReference<Integer> quantity = new AtomicReference<>();
        DatabaseConnection.executeQuery(connection, QUERY_GET_ID_AND_QUANTITY_IN_BOOK_BATCH, resultSet -> {
            if (resultSet != null && resultSet.next()) {
                bookBatchID.set(resultSet.getInt("id"));
                quantity.set(resultSet.getInt("quantity"));
            }
        }, isbn);

        return Map.of(bookBatchID.get(), quantity.get());
    }

    private void discountForCustomer(Connection connection, String orderID, Customer customer) throws SQLException {
        if (!customer.getName().equalsIgnoreCase("Anonymous")) {
            String QUERY_UPDATE_TOTAL_PRICE = "update orderSheet set totalPrice = totalPrice * 0.95 where id = ?";
            DatabaseConnection.executeUpdate(connection, QUERY_UPDATE_TOTAL_PRICE, orderID);
            System.out.println("Discount 5% for customer");
        }
    }

    private boolean checkRowAffected(Connection connection, int rowAffected) throws SQLException {
        if (rowAffected == 0) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }

        return true;
    }
}
