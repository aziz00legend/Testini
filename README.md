# Testini — Educational Assessment Platform

## Description

Testini is a comprehensive educational assessment platform designed to streamline the testing and evaluation process for professors and students. It provides an intuitive interface for creating, managing, and conducting educational assessments with advanced features for classroom management, quiz creation, and performance tracking. The platform supports multiple subjects and enables educators to create personalized challenges while maintaining detailed analytics on student performance.

## Features

### Core Functionality
- **Login and Home Page**: Secure access with authentication system and home page containing instructional videos on how to use the platform effectively
- **Template Gallery**: Access a library of pre-built quiz templates and customize them according to specific educational needs
- **Quiz Creation and Management**: Create interactive quizzes with multiple question types, including multiple-choice questions with detailed explanations and scoring systems
- **Challenge System**: Complete CRUD operations for educational challenges with publishing capabilities and status management
- **Classroom Management**: Organize multiple subjects (Mathematics, Algebra, Geometry, Physics, Chemistry, Astrophysics) with dedicated dashboards for each course
- **Performance Analytics**: Track student performance across multiple assessments with detailed score reporting and progress visualization
- **Session Management**: Organize assessment sessions with customizable scoring coefficients and merge multiple test results for comprehensive evaluation
- **Flexible Assessment Scheduling**: Schedule tests with specific start and end times, and manage session codes for secure access

## Technology Stack

| Component | Technology |
|-----------|------------|
| Frontend | Angular with PrimeNG UI components |
| Backend | Spring Boot |
| Database | MySQL |
| UI Framework | PrimeNG for responsive and modern interface design |

## Installation

### Prerequisites

- **Node.js** (recommended version: 16.x or later)
- **Angular CLI** (install globally with `npm install -g @angular/cli`)
- **Java JDK** (version 17 or higher)
- **MySQL** (version 8.0 or higher)
- **Maven** for Spring Boot dependency management

### Backend Setup

1. Clone the repository
   ```bash
   git clone <repository-url>
   cd testini
   ```

2. Navigate to the backend directory
   ```bash
   cd backend
   ```

3. Configure database connection in `application.properties`
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/testini_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

4. Run the Spring Boot application
   ```bash
   mvn spring-boot:run
   ```

### Frontend Setup

1. Navigate to the frontend directory
   ```bash
   cd frontend
   ```

2. Install dependencies
   ```bash
   npm install
   ```

3. Start the Angular development server
   ```bash
   ng serve
   ```

The application will be available at `http://localhost:4200`

## Usage

Once the application is running:

### For Professors
- Create quizzes and manage educational content
- Organize and manage classrooms across multiple subjects
- Schedule assessments with flexible timing options
- Track student performance and generate detailed reports

### For Administrators
- Oversee multiple classrooms and educational programs
- Generate comprehensive reports and analytics
- Monitor system-wide performance and usage

### Dashboard Features
- Monitor assessment status in real-time
- Track student participation and engagement
- Access academic performance analytics and insights

## Key Features Highlights

- ✅ **Intuitive Quiz Builder** with drag-and-drop question creation
- ✅ **Multi-subject Support** for comprehensive educational coverage
- ✅ **Flexible Scoring System** with customizable coefficients
- ✅ **Secure Session Management** with unique access codes
- ✅ **Responsive Design** optimized for both desktop and mobile devices


## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.



---

**Built with ❤️ for educators and students worldwide**
