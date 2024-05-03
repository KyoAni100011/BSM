package com.bsm.bsm.sheet;

import com.bsm.bsm.book.Book;
import com.bsm.bsm.book.BookBatch;
import com.bsm.bsm.commonInterface.*;
import com.bsm.bsm.employee.EmployeeModel;
import com.bsm.bsm.user.UserSingleton;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ImportSheetService implements Searchable<ImportSheet>, Sortable<ImportSheet>{

    private final ImportSheetDAO importSheetDAO;

    public ImportSheetService() {
        importSheetDAO = new ImportSheetDAO();
    }

    public boolean updateSalePrice(List<BookBatch> bookBatches) {
        try {
            return importSheetDAO.updateSalePrice(bookBatches);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean createImportSheet(ImportSheet importSheet, List<BookBatch> bookBatches) {
        try {
            return importSheetDAO.createImportSheet(importSheet, bookBatches);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<ImportSheet> getAllSheets() {
        return importSheetDAO.getAllImportSheets();
    }

    @Override
    public List<ImportSheet> search(String keyword) {
        List<ImportSheet> sheets = getAllSheets();
        String finalKeyword = keyword.toLowerCase();
        return sheets.stream()
                .filter(sheet ->
                        sheet.getEmployee().getName().toLowerCase().contains(finalKeyword) ||
                                sheet.getId().contains(finalKeyword)||
                                sheet.getImportDate().contains(finalKeyword) ||
                                String.valueOf(sheet.getQuantity()).contains(finalKeyword) ||
                                sheet.getTotalPrice().toString().contains(finalKeyword)
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<ImportSheet> sort(List<ImportSheet> sheets, boolean isAscending, String column) {
        List<ImportSheet> sortedImportSheet = new ArrayList<>(sheets);
        Comparator<ImportSheet> comparator = (importSheet1,importSheet2) -> {
            switch (column) {
                case "id" -> {
                    int importSheetID1 = Integer.parseInt(importSheet1.getId());
                    int importSheetID2 = Integer.parseInt(importSheet2.getId());
                    return Integer.compare(importSheetID1, importSheetID2);
                }
                case "date import" -> {
                    return Comparator.comparing(ImportSheet::getImportDate).compare(importSheet1, importSheet2);
                }
                case "employee" -> {
                    String employeeName1 = importSheet1.getEmployee().getName();
                    String employeeName2 = importSheet2.getEmployee().getName();
                    return employeeName1.compareTo(employeeName2);
                }
                case "quantity" -> {
                    return Comparator.comparing(ImportSheet::getQuantity).compare(importSheet1, importSheet2);
                }
                case "total price" -> {
                    return Comparator.comparing(ImportSheet::getTotalPrice).compare(importSheet1, importSheet2);
                }
                default -> {
                    return 0;
                }
            }
        };

        if (!isAscending) {
            comparator = comparator.reversed();
        }

        return sortedImportSheet.stream().sorted(comparator).collect(Collectors.toList());
    }

    public List<Book> getISheetBookDetails(String id)
    {
        return importSheetDAO.getISheetBookDetails(id);
    }

    public String getImportSheetID(ImportSheet importSheet) throws SQLException {
        EmployeeModel employee = (EmployeeModel) UserSingleton.getInstance().getUser();
        return importSheetDAO.getImportSheetID(employee,importSheet);
    }
}
