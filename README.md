# Car-Rental-System-Java
Java, Swing + MySQL based Car Rental System with Owner and Customer modules
---

## ➜ Project Description
The **Car Rental Management** System is a professional, Java-based application designed to streamline the rental process for both car owners and customers. By integrating Swing for a user-friendly interface and MySQL for robust database management, the system automates core workflows such as vehicle inventory tracking, secure booking management, and data-driven updates on car availability. This comprehensive solution focuses on maintaining data integrity through relational structures, ensuring a seamless experience that bridges the gap between administrative oversight and end-user convenience.

---

## ➜ Group Members & Contribution
| Name | CMS / ID | Section |
| :--- | :--- | :--- |
| **Warda Shamim** | 023-25-0518 | B |
| **Binish Fatima** | 023-25-0205 | B |

---

## ➜ Purpose & Scope
The system is built to solve common issues in traditional rental services, such as double-booking and manual record-keeping. 
* **Classes & Objects:** Uses custom classes to represent core entities, allowing the system to create and manage specific car and user instances.
* **Encapsulation:** Protects sensitive data by using private access modifiers and controlled access through getter and setter methods.
* **Inheritance:** Reduces redundancy by allowing specific user roles (like Admin or Customer) to inherit properties from a base parent class.
* **Database Persistence:** Utilizes MySQL via JDBC to ensure that all booking and inventory data remains saved even after the application is closed.
* **Exception Handling:** Implements try-catch blocks and throws keywords to manage database connection errors and prevent system crashes during invalid user        input.
* **Collections:** Employs Java Collections (such as ArrayList) to handle dynamic lists of records, such as active bookings or available vehicle arrays.
* **User Experience:** Provides a professional, "demure" GUI built with Java Swing components to streamline the workflow for both owners and customers.
  
---

## ➜ Main Modules

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

## ➜ How to Run

### Database Setup (MySQL)
1.  Create the database using the provided script:
    ```sql
    SOURCE database/carrentaldb.sql;
    ```
2.  Ensure your MySQL server is running on `localhost:3306`.
3.  Update the `DB.java` file if your MySQL password differs from the default `YOUR_SQL_PASSWORD`.

### Compilation & Execution
1.  **Clone the Repo:**
    ```bash
    git clone https://github.com/Warda-Shamim/Car-Rental-System-Java.git
    ```
2.  **Add Connector:** Ensure `mysql-connector-java.jar` is in your project libraries.
3.  **Run:** Execute the `CarRental.java` file.

---

## ➜ Project Links
*   **GitHub Repository:** https://github.com/Warda-Shamim/Car-Rental-System-Java
*   **Demo Video:** https://drive.google.com/file/d/1HhUoHKKAlTGakxsvnO76j37_ZEMzolWr/view?usp=drivesdk

---

## ➜ Project Structure
```text
├── database/
│   └── carrentaldb.sql        # Database schema and sample records
├── src/
│   └── CarRental.java    # Main application source code
├── assets/               # Screenshots of outputs 
└── README.md             # Project documentation
