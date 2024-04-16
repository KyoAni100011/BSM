package com.bsm.bsm.sheet;

import com.bsm.bsm.book.BookBatch;

import java.sql.SQLException;
import java.util.List;

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

}
