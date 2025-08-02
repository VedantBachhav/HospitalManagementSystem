
# Hospital Management System 🏥

A comprehensive desktop application built with Java Swing for managing hospital operations efficiently. This system provides role-based access control and streamlines various hospital processes including patient management, appointment scheduling, medical records, and billing.

## 📋 Table of Contents
- [Problem Statement](#problem-statement)
- [Features](#features)
- [System Requirements](#system-requirements)
- [Installation](#installation)
- [Database Setup](#database-setup)
- [Usage](#usage)
- [User Roles](#user-roles)
- [Project Structure](#project-structure)
- [Technologies Used](#technologies-used)

## 🎯 Problem Statement

Healthcare institutions face numerous challenges in managing their daily operations:

### Current Challenges:
- **Paper-based Records**: Many hospitals still rely on paper records, leading to:
  - Lost or damaged patient files
  - Difficulty in accessing patient history quickly
  - Time-consuming manual searches
  - Risk of data duplication

- **Appointment Management**: Traditional appointment systems suffer from:
  - Double-booking of time slots
  - Lack of real-time availability checking
  - Poor communication between departments
  - Manual scheduling conflicts

- **Billing Inefficiencies**: 
  - Manual calculation errors
  - Delayed invoice generation
  - Difficulty tracking payment status
  - Poor financial reporting

- **Staff Coordination**:
  - Limited visibility into doctor schedules
  - Inefficient resource allocation
  - Communication gaps between departments

- **Data Security & Access Control**:
  - Unauthorized access to sensitive patient data
  - Lack of audit trails
  - No role-based permissions

### Our Solution:
This Hospital Management System addresses these challenges by providing:
- **Digital Patient Records**: Secure, searchable, and always accessible
- **Automated Appointment Scheduling**: Real-time availability and conflict prevention
- **Integrated Billing System**: Automated calculations and payment tracking
- **Role-Based Access Control**: Secure access based on user roles
- **Comprehensive Reporting**: Data-driven insights for better decision making

## ✨ Features

### 🔐 Authentication & Security
- Secure login system with role-based access
- Password protection for all user accounts
- Different dashboard views based on user roles

### 👥 Patient Management
- Add, edit, and view patient information
- Complete patient profiles with personal details
- Medical history tracking
- Patient search and filtering capabilities

### 👨‍⚕️ Doctor Management
- Doctor profile management
- Specialization tracking
- Contact information management
- Schedule management

### 📅 Appointment System
- Book new appointments
- View appointment schedules
- Update appointment status (Scheduled, Completed, Cancelled)
- Conflict detection and prevention
- Real-time availability checking

### 📋 Medical Records
- Digital medical record creation and management
- Diagnosis and treatment tracking
- Doctor-patient interaction history
- Secure access to patient medical data

### 💰 Billing & Finance
- Automated bill generation
- Payment status tracking (Paid/Unpaid)
- Patient billing history
- Financial reporting capabilities

### 📊 Reports & Analytics
- Daily appointment reports
- Monthly billing summaries
- Custom report generation
- Data export capabilities

### ⚙️ System Administration
- User management (Admin only)
- System settings configuration
- Database maintenance tools

## 🔧 System Requirements

### Hardware Requirements:
- **Processor**: Intel Core i3 or equivalent
- **RAM**: Minimum 4GB (8GB recommended)
- **Storage**: 500MB free disk space
- **Display**: 1024x768 resolution minimum

### Software Requirements:
- **Operating System**: Windows 10/11, macOS, or Linux
- **Java**: JDK 8 or higher
- **Database**: MySQL 5.7 or higher
- **IDE**: Any Java IDE (Eclipse, IntelliJ IDEA, NetBeans) for development

## 🚀 Installation

### Step 1: Clone the Repository
```bash
git clone https://github.com/yourusername/hospital-management-system.git
cd hospital-management-system
```

### Step 2: Set Up MySQL Database
1. Install MySQL Server on your system
2. Create a new database named `HospitalManagementSystem`
3. Run the provided SQL scripts to create tables

### Step 3: Configure Database Connection
Update the database connection details in `DatabaseHelper.java`:
```java
private static final String URL = "jdbc:mysql://localhost:3306/HospitalManagementSystem?useSSL=false";
private static final String USER = "your_username";
private static final String PASSWORD = "your_password";
```

### Step 4: Add MySQL Connector
Download and add the MySQL Connector/J JAR file to your project's classpath.

### Step 5: Compile and Run
```bash
javac -cp ".:mysql-connector-java-8.0.33.jar" *.java
java -cp ".:mysql-connector-java-8.0.33.jar" LoginFrame
```

## 🗃️ Database Setup

### Required Tables:
Execute the following SQL commands to create the necessary tables:

```sql
-- Create Users table
CREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role ENUM('Admin', 'Doctor', 'Staff') NOT NULL
);

-- Create Patients table
CREATE TABLE Patients (
    patient_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    date_of_birth DATE,
    gender ENUM('Male', 'Female', 'Other'),
    contact_number VARCHAR(15),
    address TEXT
);

-- Create Doctors table
CREATE TABLE Doctors (
    doctor_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    specialization VARCHAR(100),
    contact_number VARCHAR(15),
    address TEXT
);

-- Create Appointments table
CREATE TABLE Appointments (
    appointment_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    doctor_id INT,
    appointment_date DATE,
    appointment_time TIME,
    status ENUM('Scheduled', 'Completed', 'Cancelled') DEFAULT 'Scheduled',
    FOREIGN KEY (patient_id) REFERENCES Patients(patient_id),
    FOREIGN KEY (doctor_id) REFERENCES Doctors(doctor_id)
);

-- Create Medical Records table
CREATE TABLE MedicalRecords (
    record_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    doctor_id INT,
    record_date DATE,
    diagnosis TEXT,
    treatment TEXT,
    FOREIGN KEY (patient_id) REFERENCES Patients(patient_id),
    FOREIGN KEY (doctor_id) REFERENCES Doctors(doctor_id)
);

-- Create Bills table
CREATE TABLE Bills (
    bill_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    appointment_id INT,
    bill_date DATE,
    amount DECIMAL(10, 2),
    status ENUM('Paid', 'Unpaid') DEFAULT 'Unpaid',
    FOREIGN KEY (patient_id) REFERENCES Patients(patient_id),
    FOREIGN KEY (appointment_id) REFERENCES Appointments(appointment_id)
);
```

### Sample Data:
```sql
-- Insert sample users
INSERT INTO Users (username, password, role) VALUES 
('admin', 'admin123', 'Admin'),
('dr.smith', 'doctor123', 'Doctor'),
('staff1', 'staff123', 'Staff');
```

## 📖 Usage

### Login Process:
1. Launch the application
2. Enter your username and password
3. Click "Login" to access your dashboard

### Default Login Credentials:
- **Admin**: username: `admin`, password: `admin123`
- **Doctor**: username: `dr.smith`, password: `doctor123`
- **Staff**: username: `staff1`, password: `staff123`

### Navigation:
- Use the left sidebar menu to navigate between different modules
- Each role has access to specific functionalities
- Click "Logout" to safely exit the system

## 👤 User Roles

### 🔑 Admin Role
**Full System Access**
- Manage all patients, doctors, and staff
- View all appointments and medical records
- Access billing and financial reports
- User management and system settings
- Generate comprehensive reports

### 👨‍⚕️ Doctor Role
**Clinical Focus**
- View assigned appointments
- Access and update medical records
- View patient information for assigned cases
- Update appointment status
- Limited system settings access

### 👩‍💼 Staff Role
**Operational Support**
- Manage patient registration
- Schedule and manage appointments
- Handle billing and payment processing
- Basic reporting capabilities
- Patient information management


## 🛠️ Technologies Used

- **Programming Language**: Java 8+
- **GUI Framework**: Java Swing
- **Database**: MySQL 8.0
- **Database Connectivity**: JDBC
- **Design Pattern**: MVC (Model-View-Controller)
- **Build Tool**: Standard Java compilation
- **Version Control**: Git

## 🔮 Future Enhancements

- **Web Interface**: Convert to web-based application using Spring Boot
- **Mobile App**: Develop companion mobile applications
- **Email Notifications**: Automated appointment reminders
- **SMS Integration**: SMS notifications for appointments
- **Advanced Analytics**: Machine learning-based insights
- **Electronic Prescriptions**: Digital prescription management
- **Inventory Management**: Medicine and equipment tracking
- **Insurance Integration**: Insurance claim processing
- **Backup & Recovery**: Automated database backup solutions
- **Multi-language Support**: Internationalization capabilities

