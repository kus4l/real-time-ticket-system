# Real-Time-Ticket-System

## Introduction
The Real Time Ticket System is a simulation front-end project that provides an interface to manage and visualize a ticketing system in real time. Built with React and Vite, the system aims to deliver a seamless and efficient user experience for tracking ticket statuses and interactions.

## Setup Instructions

### Prerequisites
Before setting up the project, ensure you have the following installed:
- **Node.js**: Version 16 or higher
- **npm**: Version 7 or higher (comes with Node.js)
- **Git**: To clone the repository

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/kus4l/real-time-ticket-system.git
   cd real-time-ticket-system
   ```
2. Install dependencies:
   ```bash
   npm install
   ```

### Build and Run the Application
1. To start the development server:
   ```bash
   npm run dev
   ```
   The application will be accessible at `http://localhost:5173/` by default.

2. To create a production build:
   ```bash
   npm run build
   ```
   The build files will be generated in the `dist/` folder.

3. To preview the production build:
   ```bash
   npm run preview
   ```

### Frontend Setup
1. Clone the frontend repository:
   ```bash
   git clone https://github.com/kus4l/real-time-ticket-system.git
   cd real-time-ticket-system
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the development server:
   ```bash
   npm start
   ```
   The application will be accessible at `http://localhost:5173/`.

### Backend Setup
1. Clone the backend repository:
   ```bash
   git clone https://github.com/kus4l/real-time-ticket-system.git
   cd real-time-ticket-system
   ```
2. Configure the database:
   - Create a MySQL database named `ticket_system`.
   - Update database credentials in `src/main/resources/application.properties`:
     ```
     spring.datasource.url=jdbc:mysql://localhost:3306/ticket_system
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     ```
    - Additional JPA properties
        ```
        # JPA properties
        spring.jpa.hibernate.ddl-auto=create //if you need to drop and create
        spring.jpa.hibernate.ddl-auto=update //if you need to update
        spring.jpa.show-sql=true
        spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
        ```
3. Build the project:
   ```bash
   mvn clean install
   ```
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```
   The backend will be accessible at `http://localhost:3306/`.

### Database Setup
1. Install MySQL and ensure it is running on your system.
2. Create a database named `ticket_system`:
   ```sql
   CREATE DATABASE ticket_system;
   ```
3. Use the credentials provided in the backend setup to connect the application to the database.

## Usage Instructions

### Configuring the System
- Ensure you have the required backend configured and running.
- Update any necessary API endpoints or configurations in the `src/config` folder (if present).

### Starting the System
- Start the application by running the development server as explained above.
- Open the application in your browser at `http://localhost:5173/`.

### Explanation of UI Controls
- **Ticket List**: Displays a list of all tickets with their status.
- **Add/View Configuration**: User can add a new configuration or use the existion configuration from the database.
- **Real-Time Updates**: Automatically reflects any changes made to tickets in the UI without requiring a page refresh.

## API References

### 1. Create Configuration
**POST** `http://localhost:8080/config/add`

- Sample PostMan Configuration Body

``` 
{
    "totalTickets": 1000,
    "maxTicketCapacity": 4000,
    "ticketReleaseRate": 2000,
    "customerRetrievalRate": 5000
}
 ```

### 2. Get Configuration
**GET** `http://localhost:8080/config/get`

### 3. Start the Simulation
**POST** `http://localhost:8080/execution/start`

### 4. Stop the Simulation
**POST** `http://localhost:8080/execution/stop`

### 5. Get the Logs
**GET** `http://localhost:8080/send-log`

## Project Structure

```
Real-Time Ticketing System
├── Backend
│   ├── org.ticket.backend
│   │   ├── configuration         # Contains configuration files for the backend.
│   │   │   ├── WebConfig         # Configures web-related settings (e.g., CORS, interceptors).
│   │   │   └── WebSocketConfig   # Configures WebSocket communication for real-time updates.
│   │   ├── controllers           # Handles incoming HTTP requests.
│   │   │   ├── ConfigController  # Controller for configuration-related APIs.
│   │   │   └── ExecutionController # Controller for ticket execution-related APIs.
│   │   ├── logger                # Custom logging utilities.
│   │   │   └── TicketLogger      # Handles ticket-specific logging functionality.
│   │   ├── models                # Contains data models for the application.
│   │   │   └── Configuration     # Represents configuration data for the ticketing system.
│   │   ├── repositories          # Data access layer for interacting with databases.
│   │   │   └── ConfigRepository  # Handles database operations for configurations.
│   │   ├── services              # Business logic layer of the application.
│   │   │   ├── ConfigService     # Service to manage configurations.
│   │   │   └── ExecutionService  # Service to manage ticket execution logic.
│   │   └── util                  # Utility classes for various functions.
│   │       ├── Customer          # Represents a customer in the ticketing system.
│   │       ├── TicketPool        # Manages a pool of available tickets.
│   │       └── Vendor            # Represents a vendor in the ticketing system.
│   └── resources
│       └── application.properties # Main configuration file for the backend application.
├── CLI
│   ├── org.ticket
│   │   ├── Customer              # Represents a customer for CLI interactions.
│   │   ├── Main                  # Entry point for the CLI application.
│   │   ├── Ticket                # Represents an individual ticket entity.
│   │   ├── TicketLogger          # CLI-specific logging utility.
│   │   ├── TicketPool            # Manages a pool of tickets for the CLI.
│   │   ├── TicketSystemConfig    # Stores configuration data for the CLI.
│   │   └── Vendor                # Represents a vendor for CLI interactions.
│   └── resources
│       └── logback.xml           # Logging configuration for the CLI.
└── Frontend
    ├── src
    │   ├── components            # Contains reusable UI components.
    │   │   └── Navbar.jsx        # Navbar component for site navigation.
    │   ├── pages                 # Pages for the frontend application.
    │   │   ├── FormPage.jsx      # Page to submit form data for ticketing.
    │   │   └── SimulationPage.jsx # Page to simulate ticketing operations.
    │   ├── assets                # Stores static assets like images and icons.
    │   ├── App.css               # Main CSS file for styling the application.
    │   ├── App.jsx               # Root component of the React application.
    │   └── main.jsx              # Entry point for rendering the React app.
    ├── public
    │   └── index.html            # HTML template for rendering the React application.
    ├── node_modules              # External dependencies installed by npm.
    ├── package.json              # Defines project dependencies and scripts.
    ├── package-lock.json         # Lockfile for consistent dependency versions.
    ├── vite.config.js            # Vite configuration file for the React app.
    └── README.md                 # Documentation for the frontend project.

```

For more detailed information about the project, please visit the [GitHub repository](https://github.com/kus4l/real-time-ticket-system).
