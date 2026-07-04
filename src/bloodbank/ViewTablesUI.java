package bloodbank;

import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;


import java.sql.*;

public class ViewTablesUI {

    public static VBox getTableView(String tableName) {
        VBox vbox = new VBox(16);
        vbox.setPadding(new Insets(25));

        Label heading = new Label("📋 " + tableName.replace("_", " "));
        heading.setFont(Font.font("System", FontWeight.BOLD, 24));
        heading.setTextFill(Color.WHITE);

        TableView<ObservableList<String>> table = new TableView<>();

        table.setFixedCellSize(35);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setRowFactory(tv -> new TableRow<>());
        table.setPrefHeight(500);

        // ✅ FULL TABLE STYLE FIX
        table.setStyle(
            "-fx-background-color: #223344; " +
            "-fx-control-inner-background: #223344; " +
            "-fx-table-cell-border-color: transparent; " +
            "-fx-text-fill: white;"
        );

        table.setPrefHeight(500);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Search
        TextField searchField = new TextField();
        searchField.setPromptText("🔍 Search...");
        searchField.setPrefHeight(36);
        searchField.setPrefWidth(300);
        searchField.setStyle(
            "-fx-background-color: #1e3248; -fx-text-fill: white; " +
            "-fx-prompt-text-fill: #667788; -fx-background-radius: 8; " +
            "-fx-border-color: #2a4560; -fx-border-radius: 8; -fx-padding: 6 12;"
        );

        Label countLabel = new Label();
        countLabel.setFont(Font.font("System", 12));
        countLabel.setTextFill(Color.web("#8899aa"));

        ObservableList<ObservableList<String>> allData = FXCollections.observableArrayList();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName)) {

            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();

            for (int i = 1; i <= colCount; i++) {
                final int idx = i - 1;

                TableColumn<ObservableList<String>, String> col =
                        new TableColumn<>(meta.getColumnName(i));

                col.setCellValueFactory(data ->
                    new javafx.beans.property.SimpleStringProperty(
                        idx < data.getValue().size() ? data.getValue().get(idx) : ""
                    )
                );

                col.setMinWidth(100);

                // ✅ Center align header + cells
                col.setStyle("-fx-alignment: CENTER;");

                // Styled cell factory
                col.setCellFactory(tc -> new TableCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(item);
                            setTextFill(Color.web("#d0d8e0"));
                            setFont(Font.font("System", 13));
                            setPadding(new Insets(8, 12, 8, 12));

                            // Color-coded values
                            if (item.equals("Available")) setTextFill(Color.web("#2ecc71"));
                            else if (item.equals("Used")) setTextFill(Color.web("#3498db"));
                            else if (item.equals("Expired")) setTextFill(Color.web("#e74c3c"));
                            else if (item.equals("Pending")) setTextFill(Color.web("#f39c12"));
                            else if (item.equals("Approved")) setTextFill(Color.web("#27ae60"));
                            else if (item.equals("Rejected")) setTextFill(Color.web("#e74c3c"));
                        }
                    }
                });

                table.getColumns().add(col);
            }

            // ✅ HEADER COLOR FIX (MAIN FIX)
            table.skinProperty().addListener((obs, oldSkin, newSkin) -> {
                table.lookupAll(".column-header").forEach(node ->
                    node.setStyle(
                        "-fx-background-color: #1e3248; " +
                        "-fx-text-fill: white;"
                    )
                );
            });

            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= colCount; i++) {
                    row.add(rs.getString(i));
                }
                allData.add(row);
            }

            table.setItems(allData);
            countLabel.setText(allData.size() + " records found");

        } catch (SQLException e) {
            Label err = new Label("❌ Error: " + e.getMessage());
            err.setTextFill(Color.web("#e74c3c"));
            vbox.getChildren().add(err);
        }

        // Search filter
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null || newVal.isEmpty()) {
                table.setItems(allData);
                countLabel.setText(allData.size() + " records found");
            } else {
                ObservableList<ObservableList<String>> filtered = FXCollections.observableArrayList();

                for (ObservableList<String> row : allData) {
                    for (String cell : row) {
                        if (cell != null && cell.toLowerCase().contains(newVal.toLowerCase())) {
                            filtered.add(row);
                            break;
                        }
                    }
                }

                table.setItems(filtered);
                countLabel.setText(filtered.size() + " records found");
            }
        });

        HBox topBar = new HBox(12, searchField, countLabel);
        topBar.setAlignment(Pos.CENTER_LEFT);

        // Alternating row colors
        table.setRowFactory(tv -> {
            TableRow<ObservableList<String>> row = new TableRow<>();

            row.itemProperty().addListener((obs, oldItem, newItem) -> {
                if (newItem == null) {
                    row.setStyle("-fx-background-color: transparent;");
                } else if (row.getIndex() % 2 == 0) {
                    row.setStyle("-fx-background-color: #1e3248;");
                } else {
                    row.setStyle("-fx-background-color: #223a50;");
                }
            });

            return row;
        });

        vbox.getChildren().addAll(heading, topBar, table);
        return vbox;
    }
}