package com.bsm.bsm.admin.bookRevenue;

import com.bsm.bsm.utils.FXMLLoaderHelper;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class BookRevenueController {
    public AnchorPane AncBookBarChart;


    public void initialize() throws IOException {
        updateBarChart();
    }

    private void updateBarChart() throws IOException {
        AncBookBarChart.getChildren().clear();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/bsm/bsm/view/admin/bookRevenue/bookBarChart.fxml"));
        AncBookBarChart.getChildren().add(fxmlLoader.load());
    }

    }
