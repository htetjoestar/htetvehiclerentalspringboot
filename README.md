# Vehicle Rental Backend

A **Node.js + Express** backend API with **MySQL** database for managing a vehicle rental platform.  
This backend powers the rental service, handling authentication, vehicle inventory, bookings, and user management.  

## 🚀 Tech Stack
- **Node.js** with Express.js  
- **MySQL** with raw queries
- **JWT Authentication**  
- **RESTful API architecture**  

## ✨ Features
- User registration & authentication  
- Vehicle listing (create, read, update, delete)  
- Booking management (reserve, cancel, track rentals)  
- Payment & pricing logic (if included)  
- Admin dashboard support for managing fleet  

## 📂 Project Structure

backend/
src/main/java
├── controller/ # Handles incoming HTTP requests
├── dto/ # Data Transfer Objects (request/response validation)
├── entity/ # Database entities/models
├── exception/ # Custom exceptions
├── mapper/ # Object mappers (DTO ↔ Entity)
├── repository/ # Database access layer
├── service/ # Business logic layer
│ └── impl/ # Service implementations
└── app.js # Main Express application entry


📌 API Endpoints
| METHOD | Endpoint | Description |
|---|---|---|
| POST | /api/customer/{id} | Register a new customer |
| PUT | /api/customer/{id} | Edit customer information |
| PUT | /api/customer/filter | Get all customers based on filters |
🛠️ Scripts

    npm run dev → Start with nodemon

    npm start → Start in production

🤝 Contributing

Contributions are welcome! Please open an issue or PR.
