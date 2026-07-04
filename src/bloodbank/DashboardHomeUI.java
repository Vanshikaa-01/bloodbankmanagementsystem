package bloodbank;

import javafx.geometry.*;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import java.sql.*;

public class DashboardHomeUI {

    public static VBox getView() {
        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(20));

        Label heading = new Label("Dashboard Overview");
        heading.setFont(Font.font("System", FontWeight.BOLD, 26));
        heading.setTextFill(Color.WHITE);

        HBox cards = new HBox(20);
        cards.setAlignment(Pos.CENTER_LEFT);

        cards.getChildren().addAll(
            createStatCard("Total Donors", getCount("SELECT COUNT(*) FROM DONOR"), "#e74c3c", "👤"),
            createStatCard("Blood Units", getCount("SELECT COUNT(*) FROM BLOOD_UNIT"), "#3498db", "🩸"),
            createStatCard("Pending Requests", getCount("SELECT COUNT(*) FROM BLOOD_REQUEST WHERE request_Status='Pending'"), "#f39c12", "⏳"),
            createStatCard("Hospitals", getCount("SELECT COUNT(*) FROM HOSPITAL"), "#2ecc71", "🏥")
            //createStatCard("Transactions", getCount("SELECT COUNT(*) FROM ISSUE_TRANSACTION"), "#9b59b6", "💉")
        );

        HBox row2 = new HBox(20);
        row2.getChildren().addAll(
            createStatCard("Available Units", getCount("SELECT COUNT(*) FROM BLOOD_UNIT WHERE unit_Status='Available'"), "#1abc9c", "✅"),
            createStatCard("Expired Units", getCount("SELECT COUNT(*) FROM BLOOD_UNIT WHERE unit_Status='Expired'"), "#e67e22", "⚠"),
            createStatCard("Recipients", getCount("SELECT COUNT(*) FROM RECIPIENT"), "#8e44ad", "🙋"),
            createStatCard("Approved Requests", getCount("SELECT COUNT(*) FROM BLOOD_REQUEST WHERE request_Status='Approved'"), "#27ae60", "✔"),
            createStatCard("Blood Banks", getCount("SELECT COUNT(*) FROM BLOOD_BANK"), "#c0392b", "🏦")
        );

        vbox.getChildren().addAll(heading, cards, row2);
        return vbox;
    }

    private static VBox createStatCard(String title, String value, String color, String icon) {
        VBox card = new VBox(8);
        card.setPrefWidth(200);
        card.setPrefHeight(120);
        card.setPadding(new Insets(18));
        card.setAlignment(Pos.CENTER_LEFT);
        card.setStyle(
            "-fx-background-color: #223344; -fx-background-radius: 14; " +
            "-fx-border-color: " + color + "33; -fx-border-radius: 14; -fx-border-width: 1;"
        );

        Label iconLabel = new Label(icon);
        iconLabel.setFont(Font.font(24));

        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("System", FontWeight.BOLD, 28));
        valueLabel.setTextFill(Color.web(color));

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("System", 12));
        titleLabel.setTextFill(Color.web("#8899aa"));

        card.getChildren().addAll(iconLabel, valueLabel, titleLabel);
        return card;
    }

    private static String getCount(String query) {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) return String.valueOf(rs.getInt(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "0";
    }
}
