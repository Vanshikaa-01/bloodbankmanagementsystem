package bloodbank;

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import java.sql.*;

public class BloodRequestUI {

    public VBox getView() {
        VBox vbox = new VBox(16);
        vbox.setPadding(new Insets(25));
        vbox.setMaxWidth(550);

        Label heading = new Label("📝 New Blood Request");
        heading.setFont(Font.font("System", FontWeight.BOLD, 24));
        heading.setTextFill(Color.WHITE);

        ComboBox<String> cbBlood = new ComboBox<>();
        cbBlood.getItems().addAll("A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-");
        cbBlood.setPromptText("Blood Group");
        cbBlood.setMaxWidth(Double.MAX_VALUE);
        styleControl(cbBlood);

        // ✅ FIX: Selected value visible
        cbBlood.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item);
                setTextFill(Color.WHITE);
            }
        });

        // ✅ FIX: Dropdown list visible
        cbBlood.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item);
                setTextFill(Color.WHITE);
            }
        });

        TextField tfQuantity = styledField("Quantity (units)");

        DatePicker dpDate = new DatePicker();
        dpDate.setPromptText("Request Date");
        dpDate.setMaxWidth(Double.MAX_VALUE);
        styleControl(dpDate);
        dpDate.getEditor().setStyle("-fx-text-fill: white;"); // ✅ FIX

        TextField tfHospitalId = styledField("Hospital ID");

        Label lblStatus = new Label();
        lblStatus.setFont(Font.font("System", 13));

        Button btnSubmit = new Button("📤  Submit Request");
        btnSubmit.setMaxWidth(Double.MAX_VALUE);
        btnSubmit.setPrefHeight(42);
        btnSubmit.setFont(Font.font("System", FontWeight.BOLD, 14));
        btnSubmit.setStyle(
            "-fx-background-color: #f39c12; -fx-text-fill: white; " +
            "-fx-background-radius: 10; -fx-cursor: hand;"
        );

        btnSubmit.setOnAction(e -> {
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO BLOOD_REQUEST (Blood_Group, Quantity, Request_Date, request_Status, Hospital_ID) VALUES (?, ?, ?, 'Pending', ?)")) {

                ps.setString(1, cbBlood.getValue());
                ps.setInt(2, Integer.parseInt(tfQuantity.getText()));
                ps.setDate(3, Date.valueOf(dpDate.getValue()));
                ps.setInt(4, Integer.parseInt(tfHospitalId.getText()));

                ps.executeUpdate();

                lblStatus.setTextFill(Color.web("#2ecc71"));
                lblStatus.setText("✅ Blood request submitted!");

                tfQuantity.clear();
                tfHospitalId.clear();
                cbBlood.setValue(null);
                dpDate.setValue(null);

            } catch (Exception ex) {
                lblStatus.setTextFill(Color.web("#e74c3c"));
                lblStatus.setText("❌ Error: " + ex.getMessage());
            }
        });

        vbox.getChildren().addAll(
            heading,
            labeledField("Blood Group", cbBlood),
            labeledField("Quantity", tfQuantity),
            labeledField("Request Date", dpDate),
            labeledField("Hospital ID", tfHospitalId),
            btnSubmit,
            lblStatus
        );

        return vbox;
    }

    private TextField styledField(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setPrefHeight(38);
        tf.setStyle(
            "-fx-background-color: #1e3248; -fx-text-fill: white; " +
            "-fx-prompt-text-fill: #667788; -fx-background-radius: 8; " +
            "-fx-border-color: #2a4560; -fx-border-radius: 8; -fx-padding: 8 12;"
        );
        return tf;
    }

    private void styleControl(Control c) {
        c.setStyle(
            "-fx-background-color: #1e3248; -fx-text-fill: white; " +
            "-fx-background-radius: 8; -fx-border-color: #2a4560; -fx-border-radius: 8;"
        );
    }

    private VBox labeledField(String label, Control field) {
        VBox vb = new VBox(4);
        Label lbl = new Label(label);
        lbl.setFont(Font.font("System", FontWeight.SEMI_BOLD, 12));
        lbl.setTextFill(Color.web("#8899aa"));
        vb.getChildren().addAll(lbl, field);
        return vb;
    }
}