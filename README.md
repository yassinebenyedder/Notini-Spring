# Notini

Notini is a modern note-taking application built with Spring Boot and React. It allows users to easily create, manage, and organize notes with features like tagging and pinning.

## Features

- **User Authentication**: Secure login and registration system with JWT.
- **Create, Update, and Delete Notes**: Easily manage your notes.
- **Tagging System**: Organize notes with tags for easy retrieval.
- **Pinning**: Pin important notes that appear at the top of the list.
- **Responsive Design**: Works well on both desktop and mobile devices.
- **Real-time Updates**: Notes refresh automatically after CRUD operations.

## Technologies Used

- **Frontend**: React, React Router, Axios, React Modal, TailwindCSS.
- **Backend**: Spring Boot, Spring Security, Spring Data JPA, MySQL.
- **Authentication**: JSON Web Tokens (JWT) with Spring Security.
- **Database**: MySQL with Hibernate ORM.
- **Build Tools**: Maven (backend), npm (frontend).

## Project Structure

```
Notini spring/
├── client/          # React frontend
└── server/          # Spring Boot backend
```

## Installation

### Prerequisites
- Java 17 or higher
- Node.js 16 or higher
- MySQL 8.0 or higher
- Maven

### Backend Setup
1. Navigate to the server directory:
   ```bash
   cd server
   ```
2. Configure database connection in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/Notini
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```
3. Install dependencies and run:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

### Frontend Setup
1. Navigate to the client directory:
   ```bash
   cd client
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Create a `.env` file with the API URL:
   ```
   REACT_APP_API_BASE_URL=http://localhost:9022/api
   ```
4. Run the development server:
   ```bash
   npm start
   ```

## Usage

1. Start the Spring Boot backend (runs on port 9022)
2. Start the React frontend (runs on port 3000)
3. Open your browser and navigate to `http://localhost:3000`
4. Register a new account or login to start using the app

## API Endpoints

- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login
- `GET /api/notes/notes/{userId}` - Get user's notes
- `POST /api/notes/createnote` - Create a new note
- `PUT /api/notes/{id}` - Update a note
- `DELETE /api/notes/{id}` - Delete a note
- `PATCH /api/notes/pinned/{id}` - Toggle note pin status

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
