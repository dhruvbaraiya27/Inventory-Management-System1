# Inventory Management System

A comprehensive web-based inventory management solution that automates and integrates all aspects of inventory control and related business operations.

## 🚀 Overview

The Inventory Management System addresses the challenges of traditional manual inventory management methods by providing a modern, efficient, and user-friendly platform. The system eliminates common issues such as stockouts, overstocking, delayed order processing, and inaccurate record-keeping while improving operational efficiency and customer satisfaction.

## ✨ Key Features

- **Real-time Inventory Tracking**: Monitor stock levels and product information in real-time
- **User Management**: Role-based authentication and access control for different user types
- **Order Processing**: Seamless purchase order creation and management
- **Invoice Generation**: Automated PDF invoice generation with professional formatting
- **Low Stock Alerts**: Proactive notifications when inventory levels are low
- **Customer Management**: Comprehensive buyer and customer record management
- **Employee Management**: Staff member tracking and role assignment
- **Dashboard Analytics**: Role-specific dashboards with summary information
- **Email Notifications**: Automated email alerts for important events
- **Secure Authentication**: JWT-based authentication with Spring Security

## 🛠️ Tech Stack

### Backend
- **Java** - Core programming language
- **Spring Boot** - Application framework
- **Spring Security** - Authentication and authorization
- **JWT** - Token-based authentication
- **MySQL** - Relational database
- **iTextPDF** - PDF generation
- **JavaMailSender** - Email notifications

### Frontend
- **React.js** - User interface library
- **JavaScript/ES6+** - Frontend logic

### Tools & DevOps
- **Docker** - Containerization
- **Swagger** - API documentation
- **Postman** - API testing
- **GitHub** - Version control

## 🏗️ Architecture

The system implements several design patterns to ensure maintainability, scalability, and code reusability:

### Design Patterns Implemented

1. **Command Pattern** - Encapsulates requests as objects for network communication
2. **Decorator Pattern** - Dynamically adds behavior to inventory cart calculations
3. **Factory Pattern** - Creates objects without exposing instantiation logic
4. **Facade Pattern** - Provides simplified interfaces for complex operations (PDF generation, messaging)
5. **Observer Pattern** - Notifies customers and updates database when products are added
6. **State Pattern** - Manages different stock states (alerts, updates)
7. **Strategy Pattern** - Implements different operation strategies for various entities

## 📁 Project Structure

```
inventory-management-system/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── controllers/          # REST API controllers
│   │   │   ├── models/              # Entity models
│   │   │   ├── repositories/        # Data access layer
│   │   │   ├── services/           # Business logic
│   │   │   ├── patterns/           # Design pattern implementations
│   │   │   └── config/             # Configuration classes
│   │   └── resources/
│   │       └── application.properties
│   └── test/                       # Unit and integration tests
├── frontend/
│   ├── src/
│   │   ├── components/             # React components
│   │   ├── pages/                  # Application pages
│   │   ├── services/               # API service calls
│   │   └── utils/                  # Utility functions
│   └── public/
├── docker-compose.yml
└── README.md
```

## 🚀 Getting Started

### Prerequisites

- Java 11 or higher
- Node.js 14 or higher
- MySQL 8.0 or higher
- Docker (optional)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/inventory-management-system.git
   cd inventory-management-system
   ```

2. **Database Setup**
   ```sql
   CREATE DATABASE inventory_management;
   ```

3. **Backend Setup**
   ```bash
   cd backend
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```

4. **Frontend Setup**
   ```bash
   cd frontend
   npm install
   npm start
   ```

### Using Docker

1. **Build and run with Docker Compose**
   ```bash
   docker-compose up --build
   ```

## 🔧 Configuration

Update `application.properties` with your database configuration:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/inventory_management
spring.datasource.username=your_username
spring.datasource.password=your_password

# JWT Configuration
jwt.secret=your_jwt_secret
jwt.expiration=86400

# Email Configuration
spring.mail.host=your_smtp_host
spring.mail.username=your_email
spring.mail.password=your_password
```

## 📊 API Documentation

The API is documented using Swagger. After starting the application, visit:
```
http://localhost:8080/swagger-ui.html
```

### Key Endpoints

- `POST /api/auth/login` - User authentication
- `POST /api/auth/register` - User registration
- `GET /api/products` - Retrieve all products
- `POST /api/products` - Add new product
- `GET /api/orders` - Retrieve orders
- `POST /api/invoices` - Generate invoice

## 👥 User Roles

The system supports multiple user roles with different permissions:

- **Admin**: Full system access, user management
- **Manager**: Inventory management, order processing
- **Employee**: Basic inventory operations
- **Customer**: View products, place orders

## 🧪 Testing

Run the test suite:

```bash
# Backend tests
./mvnw test

# Frontend tests
npm test
```

## 📈 Features in Detail

### Inventory Management
- Add, update, and delete products
- Track stock quantities and locations
- Set reorder points and maximum stock levels
- Generate low-stock alerts

### Order Management
- Create and process purchase orders
- Track order status and fulfillment
- Generate delivery schedules
- Manage supplier relationships

### Reporting & Analytics
- Generate comprehensive reports
- Export data in various formats
- Real-time dashboard analytics
- Historical trend analysis

## 🤝 Contributing

We welcome contributions! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 👨‍💻 Team

- **Rudra Patel** 
- **Janaki Rama Raju Vadapalli** 
- **Smit Patel**
- **Omkar Nate**
- **Dhruv Baraiya**

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.


## 🙏 Acknowledgments

- Spring Boot community for excellent documentation
- React.js team for the robust frontend framework
- All contributors who helped make this project possible

---

**Note**: This is an academic project developed as part of a software engineering course. The system demonstrates various design patterns and modern web development practices.
