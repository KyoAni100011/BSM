package com.bsm.bsm.admin.bookRevenue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import javafx.util.StringConverter;
import javafx.geometry.Side;

import java.util.Arrays;
import java.util.List;

public class BookRevenueController {

    public Button btnByMonth, btnByWeek, btnByDate, btnFromDateToDate;
    @FXML
    private BarChart<String, Number> bookBarChart;

    public void initialize() {
        // Tạo dữ liệu giả định
        ObservableList<XYChart.Data<String, Number>> data = FXCollections.observableArrayList();
        data.add(new XYChart.Data<>("1", 80000000));
        data.add(new XYChart.Data<>("2", 120000000));
        data.add(new XYChart.Data<>("3", 100000000));
        data.add(new XYChart.Data<>("4", 150000000));
        data.add(new XYChart.Data<>("5", 200000000));
        data.add(new XYChart.Data<>("6", 250000000));
        data.add(new XYChart.Data<>("7", 300000000));
        data.add(new XYChart.Data<>("8", 350000000));
        data.add(new XYChart.Data<>("9", 400000000));
        data.add(new XYChart.Data<>("10", 450000000));


        // Thêm dữ liệu vào biểu đồ
        XYChart.Series<String, Number> series = new XYChart.Series<>();


        // Tạo một chuỗi chuyển đổi để định dạng giá trị trục y
        NumberAxis yAxis = (NumberAxis) bookBarChart.getYAxis();
        CategoryAxis xAxis = (CategoryAxis) bookBarChart.getXAxis();
        xAxis.setTickLabelRotation(-90);
        xAxis.setTickLength(10);


        yAxis.setTickLabelFormatter(new StringConverter<Number>() {
            @Override
            public String toString(Number object) {
                if (object.intValue() < 1000000) {
                    return String.format("%.0f", object);
                } else if (object.intValue() < 1000000000) {
                    return String.format("%.0fM", object.doubleValue() / 1000000);
                } else {
                    return String.format("%.0fB", object.doubleValue() / 1000000000);
                }
            }

            @Override
            public Number fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });


        series.setData(data);
//        bookBarChart.getData().clear();
//        bookBarChart.getData().add(series);
//        bookBarChart.setLegendVisible(false);
//        bookBarChart.setTitle("Doanh thu sách theo tháng");
        series.setName("Doanh thu");
        for (XYChart.Series<String, Number> currentSeries : bookBarChart.getData()) {
            for (XYChart.Data<String, Number> currentData : currentSeries.getData()) {

                Tooltip tooltip = new Tooltip("Category: " + currentData.getXValue() + "\nValue: " + currentData.getYValue());
                tooltip.setShowDelay(Duration.ZERO);
                Tooltip.install(currentData.getNode(), tooltip);
                tooltip.setAutoHide(true);
            }
        }
//        bookBarChart.setLegendSide(Side.TOP);
//
//         bookBarChart.setTitleSide(Side.RIGHT);

        btnByMonth.getStyleClass().addAll("chartActionButton-admin-selected", "chartActionButton");
        btnByWeek.getStyleClass().add("chartActionButton");
        btnByDate.getStyleClass().add("chartActionButton");
        btnFromDateToDate.getStyleClass().add("chartActionButton");
        updateChart("Top 10 Best Selling Books By Month");


    }

    @FXML
    private void handleByMonth() {
        updateChart("Top 10 Best Selling Books By Month");
        updateButtonStyle(btnByMonth);
    }

    @FXML
    private void handleByWeek() {
        updateChart("Top 10 Best Selling Books By Week");
        updateButtonStyle(btnByWeek);
    }

    @FXML
    private void handleByDate() {
        updateChart("Top 10 Best Selling Books By Date");
        updateButtonStyle(btnByDate);
    }

    @FXML
    private void handleFromDateToDate() {
        updateChart("Top 10 Best Selling Books From Date To Date");
        updateButtonStyle(btnFromDateToDate);
    }

    private void updateChart(String title) {
        bookBarChart.setTitle(title);
        bookBarChart.lookup(".chart-title").getStyleClass().add("chart-title");
    }

    private void updateButtonStyle(Button selectedButton) {
        List<Button> buttons = Arrays.asList(btnByMonth, btnByWeek, btnByDate, btnFromDateToDate);
        for (Button button : buttons) {
            if (button == selectedButton) {
                button.getStyleClass().remove("chartActionButton-admin");
                button.getStyleClass().add("chartActionButton-admin-selected");
            } else {
                button.getStyleClass().remove("chartActionButton-admin-selected");
            }
        }
    }
}