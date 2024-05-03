package com.bsm.bsm.admin.employeeRevenue;

import com.bsm.bsm.revenue.ResultStatistic;
import com.bsm.bsm.revenue.RevenueStatisticService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
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
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
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
    @FXML
    private Group arrowDate;

    private LocalDate currentDate;
    private boolean isDailyActive = false;
    private boolean isMonthActive = false;
    private boolean isWeekActive = false;

    public void initialize() {
        currentDate = LocalDate.now();
        datePicker.setValue(currentDate);
        datePicker1.setValue(currentDate);
        datePicker1.setVisible(false);
        arrowDate.setVisible(false);
        setupDatePicker();
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
                List<ResultStatistic> employeeMonthly = revenueStatisticService.getEmployeeMonthlyRevenue(selectedDate.getYear(), selectedDate.getMonthValue());
                Platform.runLater(() -> updateChartWithData(employeeMonthly, chartTitle));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        updateButtonStyle(btnByMonth);
    }

    private void setupDatePicker() {
        // Set prompt text
        String promptText = "dd/MM/yyyy";

        // Set prompt text and create a StringConverter for datePicker
        datePicker.setPromptText(promptText);
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(promptText);

            @Override
            public String toString(LocalDate date) {
                return date != null ? dateFormatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                return string != null && !string.isEmpty() ? LocalDate.parse(string, dateFormatter) : null;
            }
        };
        datePicker.setConverter(converter);
        datePicker1.setPromptText(promptText);
        datePicker1.setConverter(converter);
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
                List<ResultStatistic> employeeWeekly = revenueStatisticService.getEmployeeWeeklyRevenue(selectedDate.getYear(), selectedDate.get(WeekFields.ISO.weekOfYear()));
                Platform.runLater(() -> updateChartWithData(employeeWeekly, chartTitle));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        updateButtonStyle(btnByWeek);
    }

    private String getChartTitle(String tagType, LocalDate selectedDate) {
        String month = selectedDate.getMonth().toString();
        String year = String.valueOf(selectedDate.getYear());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if (tagType.equals("Month")) {
            return "Top 10 Best Selling Employees In " + month + " " + year;
        } else if (tagType.equals("Week")) {
            return "Top 10 Best Selling Employees In Week " + selectedDate.get(WeekFields.ISO.weekOfYear()) + ", " + year;
        } else if (tagType.equals("Date")) {
            return "Top 10 Best Selling Employees On " + selectedDate.format(formatter);
        } else if (tagType.equals("DateRange")) {
            // Assuming datePicker and datePicker1 are DatePicker controls
            return "Top 10 Best Selling Employees From " + datePicker.getValue().format(formatter) + " To " + datePicker1.getValue().format(formatter);
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
                List<ResultStatistic> employeeDaily = revenueStatisticService.getEmployeeDailyRevenue(selectedDate.toString());
                Platform.runLater(() -> updateChartWithData(employeeDaily, chartTitle));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        updateButtonStyle(btnByDate);
    }

    private String getFormattedDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
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
                String chartTitle = "Top 10 Best Selling Employees From " + getFormattedDate(startDate) + " To " + getFormattedDate(endDate);
                try {
                    List<ResultStatistic> employeeFromTo = revenueStatisticService.getEmployeeDateToDateRevenue(startDate.toString(), endDate.toString());
                    Platform.runLater(() -> updateChartWithData(employeeFromTo, chartTitle));
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
        arrowDate.setVisible(fromDateToDateActive);
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

        applyTooltip(series, dataNames);

        employeeBarChart.setAnimated(false);
    }

    private void applyTooltip(XYChart.Series<String, Number> series, List<String> dataNames) {
        DecimalFormat formatter = new DecimalFormat("#,###");

        series.getData().forEach(data -> {
            String formattedValue = formatter.format(data.getYValue());
            String dataName = dataNames.get(series.getData().indexOf(data));
            Tooltip tooltip = new Tooltip("Employee: " + dataName + "\nRevenue: " + formattedValue);
            tooltip.setShowDelay(Duration.ZERO);
            Tooltip.install(data.getNode(), tooltip);
        });
    }

    private void updateButtonStyle(Button selectedButton) {
        Platform.runLater(() -> {
            Arrays.asList(btnByMonth, btnByWeek, btnByDate, btnFromDateToDate).forEach(button -> {
                if (button == selectedButton) {
                    button.getStyleClass().removeAll("chartActionButton-admin");
                    button.getStyleClass().add("chartActionButton-admin-selected");
                } else {
                    button.getStyleClass().remove("chartActionButton-admin-selected");
                    button.getStyleClass().add("chartActionButton-admin");
                }
            });
        });
    }
}
