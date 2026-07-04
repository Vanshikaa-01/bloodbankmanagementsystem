package bloodbank;

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import java.sql.*;

public class AddDonorUI {

    public VBox getView() {
        VBox vbox = new VBox(16);
        vbox.setPadding(new Insets(25));
        vbox.setMaxWidth(550);

        Label heading = new Label("➕ Add New Donor");
        heading.setFont(Font.font("System", FontWeight.BOLD, 24));
        heading.setTextFill(Color.WHITE);

        TextField tfId = styledField("Donor ID");
        TextField tfName = styledField("Full Name");

        DatePicker dpDob = new DatePicker();
        dpDob.setPromptText("Date of Birth");
        dpDob.setMaxWidth(Double.MAX_VALUE);
        styleControl(dpDob);
        dpDob.getEditor().setStyle("-fx-text-fill: white;"); // ✅ FIX

        ComboBox<String> cbGender = new ComboBox<>();
        cbGender.getItems().addAll("Male", "Female", "Other");
        cbGender.setPromptText("Gender");
        cbGender.setMaxWidth(Double.MAX_VALUE);
        styleControl(cbGender);

        ComboBox<String> cbBlood = new ComboBox<>();
        cbBlood.getItems().addAll("A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-");
        cbBlood.setPromptText("Blood Group");
        cbBlood.setMaxWidth(Double.MAX_VALUE);
        styleControl(cbBlood);

        // ✅ FIX: Selected value visible
        cbGender.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item);
                setTextFill(Color.WHITE);
            }
        });

        cbBlood.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item);
                setTextFill(Color.WHITE);
            }
        });

        // ✅ FIX: Dropdown list text visible
        cbGender.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item);
                setTextFill(Color.WHITE);
            }
        });

        cbBlood.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item);
                setTextFill(Color.WHITE);
            }
        });

        TextField tfContact = styledField("Contact Number");

        DatePicker dpLastDonation = new DatePicker();
        dpLastDonation.setPromptText("Last Donation Date");
        dpLastDonation.setMaxWidth(Double.MAX_VALUE);
        styleControl(dpLastDonation);
        dpLastDonation.getEditor().setStyle("-fx-text-fill: white;"); // ✅ FIX

        Label lblStatus = new Label();
        lblStatus.setFont(Font.font("System", 13));

        Button btnSave = new Button("💾  Save Donor");
        btnSave.setMaxWidth(Double.MAX_VALUE);
        btnSave.setPrefHeight(42);
        btnSave.setFont(Font.font("System", FontWeight.BOLD, 14));
        btnSave.setStyle(
            "-fx-background-color: #e74c3c; -fx-text-fill: white; " +
            "-fx-background-radius: 10; -fx-cursor: hand;"
        );

        btnSave.setOnAction(e -> {
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO DONOR VALUES (?, ?, ?, ?, ?, ?, ?)")) {

                ps.setInt(1, Integer.parseInt(tfId.getText()));
                ps.setString(2, tfName.getText());
                ps.setDate(3, Date.valueOf(dpDob.getValue()));
                ps.setString(4, cbGender.getValue());
                ps.setString(5, cbBlood.getValue());
                ps.setString(6, tfContact.getText());
                ps.setDate(7, Date.valueOf(dpLastDonation.getValue()));

                ps.executeUpdate();

                lblStatus.setTextFill(Color.web("#2ecc71"));
                lblStatus.setText("✅ Donor added successfully!");

                tfId.clear();
                tfName.clear();
                tfContact.clear();
                dpDob.setValue(null);
                dpLastDonation.setValue(null);
                cbGender.setValue(null);
                cbBlood.setValue(null);

            } catch (Exception ex) {
                lblStatus.setTextFill(Color.web("#e74c3c"));
                lblStatus.setText("❌ Error: " + ex.getMessage());
            }
        });

        vbox.getChildren().addAll(
            heading,
            labeledField("Donor ID", tfId),
            labeledField("Full Name", tfName),
            labeledField("Date of Birth", dpDob),
            labeledField("Gender", cbGender),
            labeledField("Blood Group", cbBlood),
            labeledField("Contact", tfContact),
            labeledField("Last Donation Date", dpLastDonation),
            btnSave,
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