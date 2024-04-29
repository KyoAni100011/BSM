package com.bsm.bsm.admin.employeeRevenue;

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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmployeeRevenueController {
    private final RevenueStatisticService revenueStatisticService = new RevenueStatisticService();
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    public AnchorPane AncBookBarChart;
    @FXML private Button btnByMonth, btnByWeek, btnByDate, btnFromDateToDate;
    @FXML private BarChart<String, Number> employeeBarChart;
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
                List<ResultStatistic> employeeMonthly = revenueStatisticService.getEmployeeMonthlyRevenue(selectedDate.getYear(), selectedDate.getMonthValue());
                Platform.runLater(() -> updateChartWithData(employeeMonthly, "Top 10 Best Selling By Month By Employee"));
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
                List<ResultStatistic> employeeWeekly = revenueStatisticService.getEmployeeWeeklyRevenue(selectedDate.getYear(), selectedDate.get(WeekFields.ISO.weekOfYear()));
                Platform.runLater(() -> updateChartWithData(employeeWeekly, "Top 10 Best Selling By Week By Employee"));
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
                List<ResultStatistic> employeeDaily = revenueStatisticService.getEmployeeDailyRevenue(selectedDate.toString());
                Platform.runLater(() -> updateChartWithData(employeeDaily, "Top 10 Best Selling By Date By Employee"));
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
                    List<ResultStatistic> employeeFromTo = revenueStatisticService.getEmployeeDateToDateRevenue(startDate.toString(), endDate.toString());
                    Platform.runLater(() -> updateChartWithData(employeeFromTo, "Top 10 Best Selling Employee From " + startDate + " To " + endDate));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                Platform.runLater(() -> {
                    employeeBarChart.getData().clear();
                    employeeBarChart.setTitle("Invalid Date Range");
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
        if (data != null) {
            int count = 1;
            for (ResultStatistic rs : data) {
                chartData.add(new XYChart.Data<>(String.valueOf(count), rs.getStatisticValue()));
                count++;
            }
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        // Tạo một chuỗi chuyển đổi để định dạng giá trị trục y
        NumberAxis yAxis = (NumberAxis) employeeBarChart.getYAxis();
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

        // Tạo một biểu đồ mới
        AncBookBarChart.getChildren().remove(employeeBarChart);
        employeeBarChart = new BarChart<>(employeeBarChart.getXAxis(), employeeBarChart.getYAxis());
        employeeBarChart.setPrefWidth(AncBookBarChart.getWidth());
        employeeBarChart.setPrefHeight(AncBookBarChart.getHeight());
        AncBookBarChart.getChildren().add(employeeBarChart);

        series.setData(chartData);
        employeeBarChart.getData().clear();
        employeeBarChart.setLegendVisible(false);
        employeeBarChart.setTitle(chartTitle);
        employeeBarChart.getData().add(series);

        applyTooltip(series);

        employeeBarChart.setAnimated(false);
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