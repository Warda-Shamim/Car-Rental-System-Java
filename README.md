# Car-Rental-System-Java
Java, Swing + MySQL based Car Rental System with Owner and Customer modules
---

## Project Description
The **Car Rental Management System** is a comprehensive solution designed to bridge the gap between car owners and customers. This project focuses on automating the rental workflow, from inventory management to secure booking and payment tracking. By leveraging a relational database, the system ensures data integrity and provides real-time updates on vehicle availability, ensuring a professional experience for both administrative owners and end-users.

---

## Group Members & Contribution
| Name | CMS / ID | Section |
| :--- | :--- | :--- |
| **Warda Shamim** | 023-25-0518 | B |
| **Binish Fatima** | 023-25-0205 | B |

---

## Purpose & Scope
The system is built to solve common issues in traditional rental services, such as double-booking and manual record-keeping. 
*   **Encapsulation & Abstraction:** Implementing secure user roles.
*   **Data Persistence:** Using MySQL to ensure bookings are saved even after the app closes.
*   **User Experience:** A "demure" and professional GUI using Java Swing components.

---

## Main Modules

### 1. Authentication System
The `AuthService` handles secure access. It distinguishes between **Owner** and **Customer** tables in the database to provide role-specific functionalities.

### 2. Customer Dashboard
Customers have access to a streamlined interface where they can:
*   **Browse Inventory:** View all available cars with brand and model details.
*   **Instant Booking:** Select a Car ID to create an immediate booking entry.
*   **Booking History:** Track current and past rentals and payments.

### 3. Owner Dashboard
A high-level administrative panel allowing the owner to monitor:
*   **All Registered Customers:** Full contact details and history.
*   **Global Bookings:** Real-time view of which cars are rented.
*   **Revenue Tracking:** View all payments made through the system.

---

## How to Run

### Database Setup (MySQL)
1.  Create the database using the provided script:
    ```sql
    SOURCE database/schema.sql;
    ```
2.  Ensure your MySQL server is running on `localhost:3306`.
3.  Update the `DB.java` file if your MySQL password differs from the default `PASSWORD`.

### Compilation & Execution
1.  **Clone the Repo:**
    ```bash
    git clone https://github.com/Warda-Shamim/Car-Rental-System-Java.git
    ```
2.  **Add Connector:** Ensure `mysql-connector-java.jar` is in your project libraries.
3.  **Run:** Execute the `CarRental.java` file.

---

## 📂 Project Structure
```text
├── database/
│   └── schema.sql        # Database schema and sample records
├── src/
│   └── CarRental.java    # Main application source code
├── assets/               # Screenshots or demo images (Optional)
└── README.md             # Project documentation
