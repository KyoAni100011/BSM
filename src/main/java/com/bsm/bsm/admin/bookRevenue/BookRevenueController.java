package com.bsm.bsm.admin.bookRevenue;

import com.bsm.bsm.revenue.ResultStatistic;
import com.bsm.bsm.revenue.RevenueStatisticService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javafx.util.StringConverter;
import javafx.geometry.Side;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class BookBarChartController {
    private final RevenueStatisticService revenueStatisticService = new RevenueStatisticService();
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    @FXML private Button btnByMonth, btnByWeek, btnByDate, btnFromDateToDate;
    @FXML private BarChart<String, Number> bookBarChart;
    @FXML private DatePicker datePicker, datePicker1;
    @FXML private AnchorPane datePickerContainer;

    private LocalDate currentDate;
    private boolean isDailyActive = false;
    private boolean isMonthActive = false;
    private boolean isWeekActive = false;

    public void initialize() {
        currentDate = LocalDate.now();
        datePicker.setValue(currentDate);
        datePicker1.setValue(currentDate);
        datePicker1.setVisible(false);
        handleByMonth();

        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                if(isMonthActive) {
                    handleByMonth();
                } else if (isWeekActive) {
                    handleByWeek();
                } else if (isDailyActive) {
                    handleByDate();
                } else if (datePicker1.isVisible()) {
                    handleFromDateToDate();
                }
            });
        });

        datePicker1.valueProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                if (!isDailyActive && datePicker1.isVisible()) {
                    handleFromDateToDate();
                }
            });
        });
    }

    @FXML
    private void handleByMonth() {
        datePicker.setShowWeekNumbers(false);
        isMonthActive = true;
        isDailyActive = false;
        isWeekActive = false;
        updateDatePickerCellStyle();  // Update the cell style
        setVisibility(false);
        LocalDate selectedDate = datePicker.getValue();
        executorService.submit(() -> {
            try {
                List<ResultStatistic> bookMonthly = revenueStatisticService.getBookMonthlyRevenue(selectedDate.getYear(), selectedDate.getMonthValue());
                Platform.runLater(() -> updateChartWithData(bookMonthly, "Top 10 Best Selling Books By Month"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        updateButtonStyle(btnByMonth);
    }

    @FXML
    private void handleByWeek() {
        datePicker.setShowWeekNumbers(true);
        isMonthActive = false;
        isDailyActive = false;
        isWeekActive = true;
        updateDatePickerCellStyle();  // Update the cell style for week view
        setVisibility(false);
        LocalDate selectedDate = datePicker.getValue();
        executorService.submit(() -> {
            try {
                List<ResultStatistic> bookWeekly = revenueStatisticService.getBookWeeklyRevenue(selectedDate.getYear(), selectedDate.get(WeekFields.ISO.weekOfYear()));
                System.out.println(bookWeekly);
                Platform.runLater(() -> updateChartWithData(bookWeekly, "Top 10 Best Selling Books By Week"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        updateButtonStyle(btnByWeek);
    }

    private void updateDatePickerCellStyle() {
        if (isWeekActive) {
            datePicker.setDayCellFactory(picker -> new DateCell() {
                @Override
                public void updateItem(LocalDate itemDate, boolean empty) {
                    super.updateItem(itemDate, empty);
                    if (itemDate != null && !empty) {
                        LocalDate selectedDate = datePicker.getValue();
                        LocalDate startOfWeek = selectedDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                        LocalDate endOfWeek = selectedDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
                        if (!itemDate.isBefore(startOfWeek) && !itemDate.isAfter(endOfWeek)) {
                            this.setStyle("-fx-background-color: #0096C9; -fx-text-fill: white;");
                        } else {
                            this.setStyle("");
                        }
                    }
                }
            });
        } else {
            datePicker.setDayCellFactory(picker -> new DateCell() {
                @Override
                public void updateItem(LocalDate itemDate, boolean empty) {
                    super.updateItem(itemDate, empty);
                    this.setStyle("");  // Reset style to default for all days
                }
            });
        }
    }

    @FXML
    private void handleByDate() {
        isDailyActive = true;
        isMonthActive = false;
        isWeekActive = false;
        updateDatePickerCellStyle();
        datePicker.setShowWeekNumbers(false);
        setVisibility(false);
        LocalDate selectedDate = datePicker.getValue();
        executorService.submit(() -> {
            try {
                List<ResultStatistic> bookDaily = revenueStatisticService.getBookDailyRevenue(selectedDate.toString());
                Platform.runLater(() -> updateChartWithData(bookDaily, "Top 10 Best Selling Books By Date"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        updateButtonStyle(btnByDate);
    }

    @FXML
    private void handleFromDateToDate() {
        datePicker.setShowWeekNumbers(false);
        isMonthActive = false;
        isWeekActive = false;
        isDailyActive = false;
        setVisibility(true);
        updateDatePickerCellStyle();
        executorService.submit(() -> {
            LocalDate startDate = datePicker.getValue(), endDate = datePicker1.getValue();
            if (startDate != null && endDate != null && !startDate.isAfter(endDate)) {
                try {
                    List<ResultStatistic> booksFromTo = revenueStatisticService.getBookDateToDateRevenue(startDate.toString(), endDate.toString());
                    Platform.runLater(() -> updateChartWithData(booksFromTo, "Top 10 Best Selling Books From " + startDate + " To " + endDate));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                Platform.runLater(() -> {
                    bookBarChart.getData().clear();
                    bookBarChart.setTitle("Invalid Date Range");
                });
            }
        });
        updateButtonStyle(btnFromDateToDate);
    }

    private void setVisibility(boolean fromDateToDateActive) {
        datePicker1.setVisible(fromDateToDateActive);
        if (fromDateToDateActive) {
            datePicker.prefWidthProperty().unbind();
            datePicker.setPrefWidth(datePickerContainer.getWidth() / 2);
            datePicker1.setPrefWidth(datePickerContainer.getWidth() / 2);
        } else {
            datePicker.prefWidthProperty().bind(datePickerContainer.widthProperty());
        }
    }

    private void updateChartWithData(List<ResultStatistic> data, String chartTitle) {
        // Tạo một biểu đồ mới
        BarChart<String, Number> newChart = new BarChart<>(bookBarChart.getXAxis(), bookBarChart.getYAxis());
        newChart.setTitle(chartTitle);

        ObservableList<XYChart.Data<String, Number>> chartData = FXCollections.observableArrayList();
        if (data != null) {
            data.forEach(rs -> chartData.add(new XYChart.Data<>(rs.getTitle(), rs.getStatisticValue())));
        }
        XYChart.Series<String, Number> series = new XYChart.Series<>(chartData);

        // Thêm dữ liệu vào biểu đồ mới
        newChart.getData().add(series);
        applyTooltip(series);

        // Xác định kích thước của cha và thiết lập kích thước của biểu đồ mới bằng với cha
        AnchorPane parentPane = (AnchorPane) bookBarChart.getParent();
        double parentWidth = parentPane.getWidth();
        double parentHeight = parentPane.getHeight();
        newChart.setPrefWidth(parentWidth);
        newChart.setPrefHeight(parentHeight);

        // Xóa biểu đồ hiện tại và thêm biểu đồ mới vào cùng cha của nó
        parentPane.getChildren().remove(bookBarChart);
        parentPane.getChildren().add(newChart);
        bookBarChart = newChart;
    }



    private void applyTooltip(XYChart.Series<String, Number> series) {
        series.getData().forEach(data -> {
            Tooltip tooltip = new Tooltip("Category: " + data.getXValue() + "\nValue: " + data.getYValue());
            tooltip.setShowDelay(Duration.ZERO);
            Tooltip.install(data.getNode(), tooltip);
        });
    }

    private void updateButtonStyle(Button selectedButton) {
        Arrays.asList(btnByMonth, btnByWeek, btnByDate, btnFromDateToDate).forEach(button -> {
            if (button == selectedButton) {
                button.getStyleClass().removeAll("chartActionButton-admin");
                button.getStyleClass().add("chartActionButton-admin-selected");
            } else {
                button.getStyleClass().remove("chartActionButton-admin-selected");
                button.getStyleClass().add("chartActionButton-admin");
            }
        });
    }
}
