# Blood Bank Management System

A desktop application built with **Java, JavaFX, and MySQL** to digitize core blood bank operations — donor and recipient records, blood requests, approvals, and live inventory tracking — through a dashboard-style GUI.

## Features

- **Live Dashboard**: Real-time stat cards showing total donors, blood units, hospitals, recipients, and pending/approved requests, computed via live SQL queries.
- **Donor Management**: Register donors with ID, name, DOB, gender, blood group, contact, and last donation date.
- **Recipient Management**: Register recipients and link them to a hospital.
- **Blood Request Workflow**: Hospitals submit blood requests (group, quantity, date), which enter a "Pending" queue.
- **Approve/Reject Requests**: A dedicated screen lists all pending requests in a table; selecting a row auto-fills its ID for one-click approval or rejection, which updates the database and refreshes the view.
- **Dynamic Table Viewer**: A generic, reusable table component that renders any database table by reading its column metadata at runtime — includes live search/filter and color-coded status cells (Available, Expired, Pending, Approved, Rejected).

## Tech Stack

- **Language**: Java
- **GUI**: JavaFX
- **Database**: MySQL (via JDBC)
- **Architecture**: Modular UI classes per feature (Add Donor, Add Recipient, Blood Request, Approve Request, View Tables), each interacting directly with the database layer

## Project Structure

```
src/bloodbank/
├── MainDashboard.java       # App entry point, sidebar navigation, main layout
├── DashboardHomeUI.java     # Live stat cards (donors, units, requests, etc.)
├── AddDonorUI.java          # Donor registration form
├── AddRecipientUI.java      # Recipient registration form
├── BloodRequestUI.java      # New blood request form
├── ApproveRequestUI.java    # Pending request approval/rejection workflow
├── ViewTablesUI.java        # Generic dynamic table viewer with search
├── DatabaseConnection.java  # JDBC connection handler
└── style.css                # UI styling

lib/
└── mysql-connector-j-9.6.0.jar   # MySQL JDBC driver
```

## Database

Requires a MySQL database named `blood_bank` with tables: `DONOR`, `RECIPIENT`, `HOSPITAL`, `BLOOD_UNIT`, `BLOOD_REQUEST`, `BLOOD_BANK`, and `ISSUE_TRANSACTION`.

> **Note**: Database credentials should be supplied via environment variables or a local config file (excluded from version control) rather than hardcoded.

## Running the Project

1. Set up a MySQL instance with the `blood_bank` schema and required tables.
2. Configure your database credentials.
3. Ensure the MySQL Connector/J JAR (in `lib/`) is on the classpath.
4. Run `MainDashboard.java` as a JavaFX application.

## Future Improvements

- Move database credentials to environment variables / config file
- Add authentication for staff/admin roles
- Add blood unit expiry tracking and automated alerts
- Add data export (CSV/PDF) for reports
