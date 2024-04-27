package com.bsm.bsm.sheet;

import com.bsm.bsm.author.Author;
import com.bsm.bsm.book.Book;
import com.bsm.bsm.book.BookBatch;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ImportSheetService {

    private final ImportSheetDAO importSheetDAO;

    public ImportSheetService() {
        importSheetDAO = new ImportSheetDAO();
    }

    public boolean createImportSheet(ImportSheet importSheet, List<BookBatch> bookBatches) {
        try {
            return importSheetDAO.createImportSheet(importSheet, bookBatches);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<ImportSheet> getAllSheets() throws SQLException {
        return importSheetDAO.getAllImportSheets();
    }

    public List<ImportSheet> search(String keyword) throws SQLException {
        List<ImportSheet> sheets = getAllSheets();
        String finalKeyword = keyword.toLowerCase();
        return sheets.stream()
                .filter(sheet ->
                        sheet.getEmployee().getName().toLowerCase().contains(finalKeyword) ||
                                sheet.getId().contains(finalKeyword))
                .collect(Collectors.toList());
    }
}
