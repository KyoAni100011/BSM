package com.bsm.bsm.admin.customerRevenue;

import com.bsm.bsm.revenue.ResultStatistic;
import com.bsm.bsm.revenue.RevenueStatisticService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CustomerRevenueController {
    private final RevenueStatisticService revenueStatisticService = new RevenueStatisticService();
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    public AnchorPane AncBookBarChart;
    @FXML private Button btnByMonth, btnByWeek, btnByDate, btnFromDateToDate;
    @FXML private BarChart<String, Number> customerBarChart;
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
        String chartTitle = getChartTitle("Month", selectedDate);
        executorService.submit(() -> {
            try {
                List<ResultStatistic> customerMonthly = revenueStatisticService.getCustomerMonthlyRevenue(selectedDate.getYear(), selectedDate.getMonthValue());
                Platform.runLater(() -> updateChartWithData(customerMonthly, chartTitle));
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
        String chartTitle = getChartTitle("Week", selectedDate);
        executorService.submit(() -> {
            try {
                List<ResultStatistic> customerWeekly = revenueStatisticService.getCustomerWeeklyRevenue(selectedDate.getYear(), selectedDate.get(WeekFields.ISO.weekOfYear()));
                Platform.runLater(() -> updateChartWithData(customerWeekly, chartTitle));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        updateButtonStyle(btnByWeek);
    }

    private String getChartTitle(String tagType, LocalDate selectedDate) {
        String month = selectedDate.getMonth().toString();
        String year = String.valueOf(selectedDate.getYear());
        if (tagType.equals("Month")) {
            return "Top 10 Best Selling Customers In " + month + " " + year;
        } else if (tagType.equals("Week")) {
            return "Top 10 Best Selling Customers In Week " + selectedDate.get(WeekFields.ISO.weekOfYear()) + ", " + year;
        } else if (tagType.equals("Date")) {
            return "Top 10 Best Selling Customers On " + selectedDate;
        } else if (tagType.equals("DateRange")) {
            return "Top 10 Best Selling Customers From " + datePicker.getValue() + " To " + datePicker1.getValue();
        } else {
            return "";
        }
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
        String chartTitle = getChartTitle("Date", selectedDate);
        executorService.submit(() -> {
            try {
                List<ResultStatistic> customerDaily = revenueStatisticService.getCustomerDailyRevenue(selectedDate.toString());
                Platform.runLater(() -> updateChartWithData(customerDaily, chartTitle));
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
                String chartTitle = "Top 10 Best Selling Customers From " + startDate + " To " + endDate;
                try {
                    List<ResultStatistic> customerFromTo = revenueStatisticService.getCustomerDateToDateRevenue(startDate.toString(), endDate.toString());
                    Platform.runLater(() -> updateChartWithData(customerFromTo, chartTitle));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                Platform.runLater(() -> {
                    customerBarChart.getData().clear();
                    customerBarChart.setTitle("Invalid Date Range");
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
        ObservableList<XYChart.Data<String, Number>> chartData = FXCollections.observableArrayList();
        List<String> dataNames = new ArrayList<>();

        if (data != null) {
            int count = 1;
            for (ResultStatistic rs : data) {
                chartData.add(new XYChart.Data<>(String.valueOf(count), rs.getStatisticValue()));
                dataNames.add(rs.getTitle());
                count++;
            }
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        // Tạo một biểu đồ mới
        AncBookBarChart.getChildren().remove(customerBarChart);
        customerBarChart = new BarChart<>(customerBarChart.getXAxis(), customerBarChart.getYAxis());
        customerBarChart.setPrefWidth(AncBookBarChart.getWidth());
        customerBarChart.setPrefHeight(AncBookBarChart.getHeight());
        AncBookBarChart.getChildren().add(customerBarChart);

        series.setData(chartData);
        customerBarChart.getData().clear();
        customerBarChart.setLegendVisible(false);
        customerBarChart.setTitle(chartTitle);
        customerBarChart.getData().add(series);

        applyTooltip(series, dataNames);

        customerBarChart.setAnimated(false);
    }

    private void applyTooltip(XYChart.Series<String, Number> series, List<String> dataNames) {
        DecimalFormat formatter = new DecimalFormat("#,###");

        series.getData().forEach(data -> {
            String formattedValue = formatter.format(data.getYValue());
            String dataName = dataNames.get(series.getData().indexOf(data));
            Tooltip tooltip = new Tooltip("Customer: " + dataName + "\nRevenue: " + formattedValue);
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
