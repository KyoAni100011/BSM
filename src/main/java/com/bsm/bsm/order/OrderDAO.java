package com.bsm.bsm.order;

import com.bsm.bsm.book.Book;
import com.bsm.bsm.book.BookBatch;
import com.bsm.bsm.customer.Customer;
import com.bsm.bsm.customer.CustomerDAO;
import com.bsm.bsm.database.DatabaseConnection;
import com.bsm.bsm.employee.EmployeeModel;
import com.bsm.bsm.utils.DateUtils;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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

    public boolean createOrder(EmployeeModel employee, List<String> selectedBooks, List<Integer> quantities, List<BigDecimal> salePrices, Customer customer) throws SQLException {

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

    public List<Order> getOrderInfo(String condition) {
        String QUERY_GET_ORDER_INFO = "SELECT " +
                "os.id AS order_id, " +
                "os.orderDate, " +
                "os.totalPrice AS order_total_price, " +
                "e.id AS employee_id, " +
                "u.id AS employee_user_id, " +
                "u.name AS employee_name, " +
                "u.email AS employee_email, " +
                "u.dob AS employee_dob, " +
                "u.telephone AS employee_telephone, " +
                "u.address AS employee_address, " +
                "u.password AS employee_password, " +
                "u.isEnabled AS employee_is_enabled, " +
                "u.lastLogin AS employee_last_login, " +
                "c.id AS customer_id, " +
                "c.name AS customer_name, " +
                "c.phone AS customer_phone " +
                "FROM " +
                "orderSheet os " +
                "JOIN " +
                "employee e ON os.employeeID = e.id " +
                "JOIN " +
                "user u ON e.userID = u.id " +
                "JOIN " +
                "customer c ON os.customerID = c.id " +
                condition; // Thêm điều kiện tùy chỉnh

        List<Order> orderInfo = new ArrayList<>();
        DatabaseConnection.executeQuery(QUERY_GET_ORDER_INFO, resultSet -> {
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getInt("order_id"));
                order.setOrderDate(resultSet.getString("orderDate"));
                order.setTotalPrice(resultSet.getBigDecimal("order_total_price"));

                EmployeeModel employee = new EmployeeModel();
                employee.setName(resultSet.getString("employee_name"));
                employee.setEmail(resultSet.getString("employee_email"));
                employee.setDob(resultSet.getString("employee_dob"));
                employee.setPhone(resultSet.getString("employee_telephone"));
                employee.setAddress(resultSet.getString("employee_address"));
                employee.setPassword(resultSet.getString("employee_password"));
                employee.setEnabled(resultSet.getBoolean("employee_is_enabled"));
                employee.setLastLogin(resultSet.getString("employee_last_login"));

                Customer customer = new Customer();
                customer.setId(resultSet.getString("customer_id"));
                customer.setName(resultSet.getString("customer_name"));
                customer.setPhone(resultSet.getString("customer_phone"));

                order.setEmployee(employee);
                order.setCustomer(customer);

                orderInfo.add(order);
            }
        });

        return orderInfo;
    }

    public Order getOrderByCustomer(EmployeeModel employee, Customer customer) throws SQLException {
        String employeeID = getEmployeeID(employee);
        String customerID = CustomerDAO.getCustomerID(customer);

        String QUERY_GET_ORDER = "select id, orderDate, totalPrice from orderSheet where employeeID = ? and customerID = ? order by id desc limit 1";
        Order order = new Order(employee, customer, new BigDecimal(0));

        DatabaseConnection.executeQuery(QUERY_GET_ORDER, resultSet -> {
            if (resultSet != null && resultSet.next()) {
                int orderID = resultSet.getInt("id");
                String orderDate = resultSet.getString("orderDate");
                BigDecimal totalPrice = resultSet.getBigDecimal("totalPrice");

                order.setId(orderID);
                order.setOrderDate(DateUtils.convertDOBFormat(orderDate));
                order.setTotalPrice(totalPrice);
            }
        }, employeeID, customerID);

        return order;
    }

    public List<OrderBooksDetails> getOrderBookDetails(int id) {
        String QUERY_GET_ORDER_DETAILS = """
                select b.title, obd.quantity, obd.salePrice 
                from orderBooksDetails obd 
                join bookBatch bb on obd.bookBatchID = bb.id 
                join book b on bb.bookID = b.isbn
                where obd.orderID = ?
                """;

        List<OrderBooksDetails> orderBooksDetails = new ArrayList<>();

        DatabaseConnection.executeQuery(QUERY_GET_ORDER_DETAILS, resultSet -> {
            if (resultSet != null) {
                while (resultSet.next()) {
                    String bookName = resultSet.getString("title");
                    int quantity = resultSet.getInt("quantity");
                    BigDecimal salePrice = resultSet.getBigDecimal("salePrice");

                    OrderBooksDetails booksDetails = new OrderBooksDetails();
                    booksDetails.setOrderID(id);
                    booksDetails.setBookBatch(new BookBatch(new Book(bookName)));
                    booksDetails.setQuantity(quantity);
                    booksDetails.setSalePrice(salePrice);

                    orderBooksDetails.add(booksDetails);
                }
            }
        }, id);

        return orderBooksDetails;
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

    private String getEmployeeID(EmployeeModel employee) throws SQLException {
        String QUERY_GET_EMPLOYEEID = "select e.id from user u join employee e on u.id = e.userID where u.id = ?";
        AtomicReference<String> employeeID = new AtomicReference<>();

        DatabaseConnection.executeQuery(QUERY_GET_EMPLOYEEID, resultSet -> {
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
