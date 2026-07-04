/*package bloodbank;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class MainDashboard extends Application {

    private BorderPane root;
    private VBox sidebar;
    private StackPane contentArea;

    @Override
    public void start(Stage primaryStage) {
        root = new BorderPane();
        root.setStyle("-fx-background-color: #0f1923;");

        // Sidebar
        sidebar = createSidebar();
        root.setLeft(sidebar);

        // Default content
        contentArea = new StackPane();
        contentArea.setStyle("-fx-background-color: #1a2634;");
        contentArea.setPadding(new Insets(20));
        root.setCenter(contentArea);

        showDashboardHome();

        Scene scene = new Scene(root, 1200, 750);
        scene.getStylesheets().add(getClass().getResource("/bloodbank/style.css").toExternalForm()); 
        primaryStage.setTitle("Blood Bank Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createSidebar() {
        VBox vbox = new VBox(5);
        vbox.setPrefWidth(240);
        vbox.setPadding(new Insets(20, 15, 20, 15));
        vbox.setStyle("-fx-background-color: #152232;");

        // Title
        Label title = new Label("🩸 Blood Bank");
        title.setFont(Font.font("System", FontWeight.BOLD, 20));
        title.setTextFill(Color.web("#e74c3c"));
        title.setPadding(new Insets(0, 0, 25, 0));

        Label subtitle = new Label("Management System");
        subtitle.setFont(Font.font("System", FontWeight.NORMAL, 11));
        subtitle.setTextFill(Color.web("#7f8c9b"));
        subtitle.setPadding(new Insets(-20, 0, 25, 0));

        Button btnDashboard = createSidebarButton("📊  Dashboard");
        Button btnDonors = createSidebarButton("👤  View Donors");
        Button btnAddDonor = createSidebarButton("➕  Add Donor");
        Button btnRecipients = createSidebarButton("🏥  View Recipients");
        Button btnAddRecipient = createSidebarButton("➕  Add Recipient");
        Button btnBloodUnits = createSidebarButton("🩸  Blood Units");
        Button btnRequests = createSidebarButton("📋  Blood Requests");
        Button btnNewRequest = createSidebarButton("📝  New Request");
        Button btnApprove = createSidebarButton("✅  Approve Requests");
        Button btnTransactions = createSidebarButton("💉  Transactions");

        btnDashboard.setOnAction(e -> showDashboardHome());
        btnDonors.setOnAction(e -> showTable("DONOR"));
        btnAddDonor.setOnAction(e -> showContent(new AddDonorUI().getView()));
        btnRecipients.setOnAction(e -> showTable("RECIPIENT"));
        btnAddRecipient.setOnAction(e -> showContent(new AddRecipientUI().getView()));
        btnBloodUnits.setOnAction(e -> showTable("BLOOD_UNIT"));
        btnRequests.setOnAction(e -> showTable("BLOOD_REQUEST"));
        btnNewRequest.setOnAction(e -> showContent(new BloodRequestUI().getView()));
        btnApprove.setOnAction(e -> showContent(new ApproveRequestUI().getView()));
        btnTransactions.setOnAction(e -> showTable("ISSUE_TRANSACTION"));

        vbox.getChildren().addAll(title, subtitle,
                btnDashboard, new Separator(),
                btnDonors, btnAddDonor, new Separator(),
                btnRecipients, btnAddRecipient, new Separator(),
                btnBloodUnits, btnRequests, btnNewRequest, btnApprove, new Separator(),
                btnTransactions);

        return vbox;
    }

    private Button createSidebarButton(String text) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setPrefHeight(38);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setFont(Font.font("System", 13));
        btn.setStyle(
            "-fx-background-color: transparent; -fx-text-fill: #c0cad8; " +
            "-fx-cursor: hand; -fx-background-radius: 8; -fx-padding: 8 14;"
        );
        btn.setOnMouseEntered(e -> btn.setStyle(
            "-fx-background-color: #1e3248; -fx-text-fill: #ffffff; " +
            "-fx-cursor: hand; -fx-background-radius: 8; -fx-padding: 8 14;"
        ));
        btn.setOnMouseExited(e -> btn.setStyle(
            "-fx-background-color: transparent; -fx-text-fill: #c0cad8; " +
            "-fx-cursor: hand; -fx-background-radius: 8; -fx-padding: 8 14;"
        ));
        return btn;
    }

    private void showContent(VBox view) {
        contentArea.getChildren().clear();
        ScrollPane scroll = new ScrollPane(view);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: #1a2634; -fx-background-color: #1a2634;");
        contentArea.getChildren().add(scroll);
    }

    private void showTable(String tableName) {
        contentArea.getChildren().clear();
        VBox tableView = ViewTablesUI.getTableView(tableName);
        ScrollPane scroll = new ScrollPane(tableView);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: #1a2634; -fx-background-color: #1a2634;");
        contentArea.getChildren().add(scroll);
    }

    private void showDashboardHome() {
        contentArea.getChildren().clear();
        VBox home = DashboardHomeUI.getView();
        ScrollPane scroll = new ScrollPane(home);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: #1a2634; -fx-background-color: #1a2634;");
        contentArea.getChildren().add(scroll);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
*/

/*package bloodbank;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class DashboardUI {

    public Pane getView() {

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(20);
        grid.setVgap(20);

        // Add cards (row, col)
        grid.add(createCard("Total Donors", "16"), 0, 0);
        grid.add(createCard("Blood Units", "15"), 1, 0);
        grid.add(createCard("Pending Requests", "3"), 2, 0);
        grid.add(createCard("Hospitals", "15"), 3, 0);

        grid.add(createCard("Transactions", "16"), 0, 1);
        grid.add(createCard("Available Units", "10"), 1, 1);
        grid.add(createCard("Expired Units", "2"), 2, 1);
        grid.add(createCard("Recipients", "16"), 3, 1);

        return grid;
    }

    private VBox createCard(String titleText, String valueText) {

        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setPrefWidth(200);
        card.setPrefHeight(120);

        Label title = new Label(titleText);
        Label value = new Label(valueText);

        // Apply CSS classes
        card.getStyleClass().add("card");
        title.getStyleClass().add("card-title");
        value.getStyleClass().add("card-value");

        card.getChildren().addAll(title, value);

        return card;
    }
}*/


package bloodbank;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class MainDashboard extends Application {

    private BorderPane root;
    private VBox sidebar;
    private StackPane contentArea;

    @Override
    public void start(Stage primaryStage) {
        root = new BorderPane();
        root.setStyle("-fx-background-color: #0f1923;");

        // Sidebar
        sidebar = createSidebar();
        root.setLeft(sidebar);

        // Default content
        contentArea = new StackPane();
        contentArea.setStyle("-fx-background-color: #1a2634;");
        contentArea.setPadding(new Insets(20));
        root.setCenter(contentArea);

        showDashboardHome();

        Scene scene = new Scene(root, 1200, 750);

        // CSS (safe load)
        var css = getClass().getResource("/bloodbank/style.css");
        if (css != null) {
            scene.getStylesheets().add(css.toExternalForm());
        } else {
            System.out.println("CSS not found");
        }

        primaryStage.setTitle("Blood Bank Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createSidebar() {
        VBox vbox = new VBox(5);
        vbox.setPrefWidth(240);
        vbox.setPadding(new Insets(20, 15, 20, 15));
        vbox.setStyle("-fx-background-color: #152232;");

        Label title = new Label("🩸 Blood Bank");
        title.setFont(Font.font("System", FontWeight.BOLD, 20));
        title.setTextFill(Color.web("#e74c3c"));
        title.setPadding(new Insets(0, 0, 25, 0));

        Label subtitle = new Label("Management System");
        subtitle.setFont(Font.font("System", FontWeight.NORMAL, 11));
        subtitle.setTextFill(Color.web("#7f8c9b"));
        subtitle.setPadding(new Insets(-20, 0, 25, 0));

        Button btnDashboard = createSidebarButton("📊  Dashboard");
        Button btnDonors = createSidebarButton("👤  View Donors");
        Button btnAddDonor = createSidebarButton("➕  Add Donor");
        Button btnRecipients = createSidebarButton("🏥  View Recipients");
        Button btnAddRecipient = createSidebarButton("➕  Add Recipient");
        Button btnBloodUnits = createSidebarButton("🩸  Blood Units");
        Button btnRequests = createSidebarButton("📋  Blood Requests");
        Button btnNewRequest = createSidebarButton("📝  New Request");
        Button btnApprove = createSidebarButton("✅  Approve Requests");
        //Button btnTransactions = createSidebarButton("💉  Transactions");

        btnDashboard.setOnAction(e -> showDashboardHome());
        btnDonors.setOnAction(e -> showTable("DONOR"));
        btnAddDonor.setOnAction(e -> showContent(new AddDonorUI().getView()));
        btnRecipients.setOnAction(e -> showTable("RECIPIENT"));
        btnAddRecipient.setOnAction(e -> showContent(new AddRecipientUI().getView()));
        btnBloodUnits.setOnAction(e -> showTable("BLOOD_UNIT"));
        btnRequests.setOnAction(e -> showTable("BLOOD_REQUEST"));
        btnNewRequest.setOnAction(e -> showContent(new BloodRequestUI().getView()));
        btnApprove.setOnAction(e -> showContent(new ApproveRequestUI().getView()));
        //btnTransactions.setOnAction(e -> showTable("ISSUE_TRANSACTION"));

        vbox.getChildren().addAll(title, subtitle,
                btnDashboard, new Separator(),
                btnDonors, btnAddDonor, new Separator(),
                btnRecipients, btnAddRecipient, new Separator(),
                btnBloodUnits, btnRequests, btnNewRequest, btnApprove, new Separator());
                /*btnTransactions*/

        return vbox;
    }

    private Button createSidebarButton(String text) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setPrefHeight(38);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setFont(Font.font("System", 13));

        btn.setStyle(
            "-fx-background-color: transparent; -fx-text-fill: #c0cad8; " +
            "-fx-cursor: hand; -fx-background-radius: 8; -fx-padding: 8 14;"
        );

        btn.setOnMouseEntered(e -> btn.setStyle(
            "-fx-background-color: #1e3248; -fx-text-fill: #ffffff; " +
            "-fx-cursor: hand; -fx-background-radius: 8; -fx-padding: 8 14;"
        ));

        btn.setOnMouseExited(e -> btn.setStyle(
            "-fx-background-color: transparent; -fx-text-fill: #c0cad8; " +
            "-fx-cursor: hand; -fx-background-radius: 8; -fx-padding: 8 14;"
        ));

        return btn;
    }

    // ✅ FIXED SCROLLPANE
    private void showContent(VBox view) {
        contentArea.getChildren().clear();

        ScrollPane scroll = new ScrollPane(view);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);  // ✅ FIX
        scroll.setPannable(true);     // ✅ FIX
        scroll.setStyle("-fx-background: #1a2634; -fx-background-color: #1a2634;");

        contentArea.getChildren().add(scroll);
    }

    // ✅ FIXED TABLE SCROLL
    private void showTable(String tableName) {
        contentArea.getChildren().clear();

        VBox tableView = ViewTablesUI.getTableView(tableName);

        ScrollPane scroll = new ScrollPane(tableView);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);  // ✅ FIX
        scroll.setPannable(true);     // ✅ FIX
        scroll.setStyle("-fx-background: #1a2634; -fx-background-color: #1a2634;");

        contentArea.getChildren().add(scroll);
    }

    // ✅ FIXED DASHBOARD SCROLL
    private void showDashboardHome() {
        contentArea.getChildren().clear();

        VBox home = DashboardHomeUI.getView();

        ScrollPane scroll = new ScrollPane(home);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);  // ✅ FIX
        scroll.setPannable(true);     // ✅ FIX
        scroll.setStyle("-fx-background: #1a2634; -fx-background-color: #1a2634;");

        contentArea.getChildren().add(scroll);
    }

    public static void main(String[] args) {
        launch(args);
    }
}