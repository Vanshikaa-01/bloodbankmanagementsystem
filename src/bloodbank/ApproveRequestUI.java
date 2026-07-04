/*package bloodbank;

import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

import java.lang.classfile.Label;
import java.sql.*;

import javax.swing.table.TableColumn;
import javax.swing.text.TableView;
import javax.swing.text.TableView.TableCell;

public class ApproveRequestUI {

    public VBox getView() {
        VBox vbox = new VBox(16);
        vbox.setPadding(new Insets(25));

        Label heading = new Label("✅ Approve / Reject Requests");
        heading.setFont(Font.font("System", FontWeight.BOLD, 24));
        heading.setTextFill(Color.WHITE);

        // ✅ TABLE
        TableView<ObservableList<String>> table = new TableView<>();
        table.setPrefHeight(350);

        // 🔥 FULL TABLE STYLE FIX
        table.setStyle(
            "-fx-background-color: #223344; " +
            "-fx-control-inner-background: #223344; " +
            "-fx-table-cell-border-color: transparent; " +
            "-fx-text-fill: white;"
        );

        String[] cols = {"Request_ID", "Blood_Group", "Quantity", "Request_Date", "Hospital_ID"};

        for (int i = 0; i < cols.length; i++) {
            final int idx = i;
            TableColumn<ObservableList<String>, String> col = new TableColumn<>(cols[i]);

            col.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().get(idx)));

            col.setPrefWidth(140);

            // ✅ CELL TEXT COLOR FIX
            col.setCellFactory(tc -> new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item);
                        setTextFill(Color.WHITE);
                    }
                }
            });

            table.getColumns().add(col);
        }

        // 🔥 HEADER COLOR FIX (VERY IMPORTANT)
        table.skinProperty().addListener((obs, oldSkin, newSkin) -> {
            table.lookupAll(".column-header").forEach(node ->
                node.setStyle("-fx-background-color: #1e3248; -fx-text-fill: white;")
            );
        });

        loadPendingRequests(table);

        // ✅ REQUEST ID FIELD
        TextField tfRequestId = new TextField();
        tfRequestId.setPromptText("Enter Request ID");
        tfRequestId.setPrefHeight(38);
        tfRequestId.setStyle(
            "-fx-background-color: #1e3248; -fx-text-fill: white; " +
            "-fx-prompt-text-fill: #667788; -fx-background-radius: 8; " +
            "-fx-border-color: #2a4560; -fx-border-radius: 8; -fx-padding: 8 12;"
        );

        // 🔥 AUTO-FILL ON CLICK
        table.setOnMouseClicked(event -> {
            ObservableList<String> selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                tfRequestId.setText(selected.get(0));
            }
        });

        Label lblStatus = new Label();
        lblStatus.setFont(Font.font("System", 13));

        // ✅ BUTTONS
        Button btnApprove = new Button("✅ Approve");
        btnApprove.setPrefHeight(38);
        btnApprove.setPrefWidth(150);
        btnApprove.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-background-radius: 10; -fx-font-weight: bold;");

        Button btnReject = new Button("❌ Reject");
        btnReject.setPrefHeight(38);
        btnReject.setPrefWidth(150);
        btnReject.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-background-radius: 10; -fx-font-weight: bold;");

        Button btnRefresh = new Button("🔄 Refresh");
        btnRefresh.setPrefHeight(38);
        btnRefresh.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-background-radius: 10;");
        btnRefresh.setOnAction(e -> loadPendingRequests(table));

        btnApprove.setOnAction(e -> updateStatus(tfRequestId.getText(), "Approved", lblStatus, table));
        btnReject.setOnAction(e -> updateStatus(tfRequestId.getText(), "Rejected", lblStatus, table));

        HBox actions = new HBox(12, tfRequestId, btnApprove, btnReject, btnRefresh);
        actions.setAlignment(Pos.CENTER_LEFT);

        vbox.getChildren().addAll(heading, table, actions, lblStatus);
        return vbox;
    }

    private void loadPendingRequests(TableView<ObservableList<String>> table) {
        table.getItems().clear();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                "SELECT Request_ID, Blood_Group, Quantity, Request_Date, Hospital_ID " +
                "FROM BLOOD_REQUEST WHERE TRIM(request_Status) = 'Pending'")) {

            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= 5; i++) row.add(rs.getString(i));
                table.getItems().add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateStatus(String requestId, String status, Label lblStatus, TableView<ObservableList<String>> table) {

        if (requestId == null || requestId.trim().isEmpty()) {
            lblStatus.setTextFill(Color.web("#f39c12"));
            lblStatus.setText("⚠ Please enter a Request ID");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                "UPDATE BLOOD_REQUEST SET request_Status = ? " +
                "WHERE Request_ID = ? AND TRIM(request_Status) = 'Pending'")) {

            ps.setString(1, status);
            ps.setInt(2, Integer.parseInt(requestId.trim()));

            int rows = ps.executeUpdate();

            if (rows > 0) {
                lblStatus.setTextFill(Color.web("#2ecc71"));
                lblStatus.setText("✅ Request " + requestId + " " + status.toLowerCase() + "!");
                loadPendingRequests(table);
            } else {
                lblStatus.setTextFill(Color.web("#e74c3c"));
                lblStatus.setText("❌ Not Pending / Invalid ID");
            }

        } catch (Exception ex) {
            lblStatus.setTextFill(Color.web("#e74c3c"));
            lblStatus.setText("❌ Error: " + ex.getMessage());
        }
    }

}*/


/*package bloodbank;

import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

import java.sql.*;

public class ApproveRequestUI {

    public VBox getView() {
        VBox vbox = new VBox(16);
        vbox.setPadding(new Insets(25));

        Label heading = new Label("Approve / Reject Requests");
        heading.setFont(Font.font("System", FontWeight.BOLD, 24));
        heading.setTextFill(Color.WHITE);

        // TABLE
        TableView<ObservableList<String>> table = new TableView<>();
        table.setPrefHeight(350);

        table.setStyle(
            "-fx-background-color: #223344; " +
            "-fx-control-inner-background: #223344; " +
            "-fx-table-cell-border-color: transparent; " +
            "-fx-text-fill: white;"
        );

        String[] cols = {"Request_ID", "Blood_Group", "Quantity", "Request_Date", "Hospital_ID"};

        for (int i = 0; i < cols.length; i++) {
            final int idx = i;
            TableColumn<ObservableList<String>, String> col = new TableColumn<>(cols[i]);

            col.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().get(idx)));

            col.setPrefWidth(140);

            col.setCellFactory(tc -> new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item);
                        setTextFill(Color.WHITE);
                    }
                }
            });

            table.getColumns().add(col);
        }

        table.skinProperty().addListener((obs, oldSkin, newSkin) -> {
            table.lookupAll(".column-header").forEach(node ->
                node.setStyle("-fx-background-color: #1e3248; -fx-text-fill: white;")
            );
        });

        loadPendingRequests(table);

        // INPUT FIELD
        TextField tfRequestId = new TextField();
        tfRequestId.setPromptText("Enter Request ID");
        tfRequestId.setPrefHeight(38);

        table.setOnMouseClicked(event -> {
            ObservableList<String> selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                tfRequestId.setText(selected.get(0));
            }
        });

        Label lblStatus = new Label();
        lblStatus.setFont(Font.font("System", 13));

        // BUTTONS
        Button btnApprove = new Button("Approve");
        Button btnReject = new Button("Reject");
        Button btnRefresh = new Button("Refresh");

        btnRefresh.setOnAction(e -> loadPendingRequests(table));

        btnApprove.setOnAction(e -> updateStatus(tfRequestId.getText(), "Approved", lblStatus, table));
        btnReject.setOnAction(e -> updateStatus(tfRequestId.getText(), "Rejected", lblStatus, table));

        HBox actions = new HBox(12, tfRequestId, btnApprove, btnReject, btnRefresh);
        actions.setAlignment(Pos.CENTER_LEFT);

        vbox.getChildren().addAll(heading, table, actions, lblStatus);
        return vbox;
    }

    private void loadPendingRequests(TableView<ObservableList<String>> table) {
        table.getItems().clear();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                "SELECT Request_ID, Blood_Group, Quantity, Request_Date, Hospital_ID " +
                "FROM BLOOD_REQUEST WHERE TRIM(request_Status) = 'Pending'")) {

            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= 5; i++) row.add(rs.getString(i));
                table.getItems().add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateStatus(String requestId, String status, Label lblStatus, TableView<ObservableList<String>> table) {

        if (requestId == null || requestId.trim().isEmpty()) {
            lblStatus.setTextFill(Color.web("#f39c12"));
            lblStatus.setText("Please enter a Request ID");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                "UPDATE BLOOD_REQUEST SET request_Status = ? " +
                "WHERE Request_ID = ? AND TRIM(request_Status) = 'Pending'")) {

            ps.setString(1, status);
            ps.setInt(2, Integer.parseInt(requestId.trim()));

            int rows = ps.executeUpdate();

            if (rows > 0) {
                lblStatus.setTextFill(Color.web("#2ecc71"));
                lblStatus.setText("Request " + requestId + " " + status.toLowerCase() + "!");

                // 🔥 INSERT into ISSUE_TRANSACTION
                int reqId = Integer.parseInt(requestId.trim());

                // TEMP values (replace later with dropdowns if needed)
                int unitId = 204;
                int recipientId = 404;

                createTransaction(reqId, unitId, recipientId);

                loadPendingRequests(table);
            } else {
                lblStatus.setTextFill(Color.web("#e74c3c"));
                lblStatus.setText("Not Pending / Invalid ID");
            }

        } catch (Exception ex) {
            lblStatus.setTextFill(Color.web("#e74c3c"));
            lblStatus.setText("Error: " + ex.getMessage());
        }
    }

    // ✅ FINAL METHOD: INSERT transaction
    public void createTransaction(int requestId, int unitId, int recipientId) {

        String query = """
            INSERT INTO ISSUE_TRANSACTION 
            (Request_ID, Unit_ID, Recipient_ID, Issue_Date)
            VALUES (?, ?, ?, CURDATE())
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, requestId);
            ps.setInt(2, unitId);
            ps.setInt(3, recipientId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}*/


package bloodbank;

import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import java.sql.*;

public class ApproveRequestUI {

    public VBox getView() {
        VBox vbox = new VBox(16);
        vbox.setPadding(new Insets(25));

        Label heading = new Label("✅ Approve / Reject Requests");
        heading.setFont(Font.font("System", FontWeight.BOLD, 24));
        heading.setTextFill(Color.WHITE);

        // Table for pending requests
        TableView<ObservableList<String>> table = new TableView<>();
        table.setStyle("-fx-background-color: #223344; -fx-text-fill: white;");
        table.setPrefHeight(350);

        String[] cols = {"Request_ID", "Blood_Group", "Quantity", "Request_Date", "Hospital_ID"};
        for (int i = 0; i < cols.length; i++) {
            final int idx = i;
            TableColumn<ObservableList<String>, String> col = new TableColumn<>(cols[i]);
            col.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().get(idx)));
            col.setPrefWidth(140);
            col.setStyle("-fx-text-fill: white; -fx-alignment: CENTER;");
            table.getColumns().add(col);
        }

        loadPendingRequests(table);

        TextField tfRequestId = new TextField();
        tfRequestId.setPromptText("Enter Request ID");
        tfRequestId.setPrefHeight(38);
        tfRequestId.setStyle(
            "-fx-background-color: #1e3248; -fx-text-fill: white; " +
            "-fx-prompt-text-fill: #667788; -fx-background-radius: 8; " +
            "-fx-border-color: #2a4560; -fx-border-radius: 8; -fx-padding: 8 12;"
        );

        Label lblStatus = new Label();
        lblStatus.setFont(Font.font("System", 13));

        Button btnApprove = new Button("✅ Approve");
        btnApprove.setPrefHeight(38);
        btnApprove.setPrefWidth(150);
        btnApprove.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-background-radius: 10; -fx-font-weight: bold;");

        Button btnReject = new Button("❌ Reject");
        btnReject.setPrefHeight(38);
        btnReject.setPrefWidth(150);
        btnReject.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-background-radius: 10; -fx-font-weight: bold;");

        Button btnRefresh = new Button("🔄 Refresh");
        btnRefresh.setPrefHeight(38);
        btnRefresh.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-background-radius: 10;");
        btnRefresh.setOnAction(e -> loadPendingRequests(table));

        btnApprove.setOnAction(e -> updateStatus(tfRequestId.getText(), "Approved", lblStatus, table));
        btnReject.setOnAction(e -> updateStatus(tfRequestId.getText(), "Rejected", lblStatus, table));

        HBox actions = new HBox(12, tfRequestId, btnApprove, btnReject, btnRefresh);
        actions.setAlignment(Pos.CENTER_LEFT);

        vbox.getChildren().addAll(heading, table, actions, lblStatus);
        return vbox;
    }

    private void loadPendingRequests(TableView<ObservableList<String>> table) {
        table.getItems().clear();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                "SELECT Request_ID, Blood_Group, Quantity, Request_Date, Hospital_ID FROM BLOOD_REQUEST WHERE request_Status = 'Pending'")) {
            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= 5; i++) row.add(rs.getString(i));
                table.getItems().add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateStatus(String requestId, String status, Label lblStatus, TableView<ObservableList<String>> table) {
        if (requestId == null || requestId.isEmpty()) {
            lblStatus.setTextFill(Color.web("#f39c12"));
            lblStatus.setText("⚠ Please enter a Request ID");
            return;
        }
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                "UPDATE BLOOD_REQUEST SET request_Status = ? WHERE Request_ID = ? AND request_Status = 'Pending'")) {
            ps.setString(1, status);
            ps.setInt(2, Integer.parseInt(requestId));
            int rows = ps.executeUpdate();
            if (rows > 0) {
                lblStatus.setTextFill(Color.web("#2ecc71"));
                lblStatus.setText("✅ Request " + requestId + " " + status.toLowerCase() + "!");
                loadPendingRequests(table);
            } else {
                lblStatus.setTextFill(Color.web("#e74c3c"));
                lblStatus.setText("❌ Request not found or already processed");
            }
        } catch (Exception ex) {
            lblStatus.setTextFill(Color.web("#e74c3c"));
            lblStatus.setText("❌ Error: " + ex.getMessage());
        }
    }
}
