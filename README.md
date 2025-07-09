
## Description

Testini is a comprehensive educational assessment platform designed to streamline the testing and evaluation process for professors and students. It provides an intuitive interface for creating, managing, and conducting educational assessments with advanced features for classroom management, quiz creation, and performance tracking. The platform supports multiple subjects and enables educators to create personalized challenges while maintaining detailed analytics on student performance.

## Features

### Core Functionality
- **Login and Home Page**: Secure access with authentication system and home page containing instructional videos on how to use the platform effectively


  ![0 login](https://github.com/user-attachments/assets/7a162d21-85fe-45dc-9c2f-6ba4c5c61ce7)
  ![1 home](https://github.com/user-attachments/assets/74f8c4a0-888f-4850-81b7-5004cc8039c4)

- **Template Gallery**: Access a library of pre-built quiz templates and customize them according to specific educational needs

  
  ![2 template](https://github.com/user-attachments/assets/e0ef8be7-0ed2-4180-80ed-3ff787e8966f)

- **Quiz Creation**: Create interactive quizzes with multiple question types, including multiple-choice questions with detailed explanations

  
  ![3 challenge-creator](https://github.com/user-attachments/assets/851b4602-0eab-43ec-9921-49cea5ca6c85)
  
  ![4 View mode](https://github.com/user-attachments/assets/cf8f4c73-02f3-4bbd-8cd7-a5b6fd7f643b)

- **Challenge System**: Complete CRUD operations for educational challenges with publishing capabilities and status management

  
   ![5 Challenges Gallery](https://github.com/user-attachments/assets/0cd590f3-1c34-4199-9d85-3ba54bcf5381)

   ![6 Challenges Gallery mode 2 png ](https://github.com/user-attachments/assets/67785d6c-d647-4cb3-ab99-2982b90be805)

- **Classroom Management**: Organize multiple subjects (Mathematics, Algebra, Geometry, Physics, Chemistry, Astrophysics) with dedicated dashboards for each course

  
  ![7 Clasroom](https://github.com/user-attachments/assets/2c995641-f464-46a5-b7e9-dfac82e79db1)

- **Performance Analytics**: Track student performance across multiple assessments with detailed score reporting and progress visualization

  
  ![8 report-card](https://github.com/user-attachments/assets/b255c0ce-ef8c-435a-90e2-da915e70a3fc)

- **Flexible Assessment Scheduling**: Schedule tests with specific start and end times, and manage session codes for secure access

  
  ![9 new Session](https://github.com/user-attachments/assets/9d17dbc5-f2b6-4b80-8438-95f2c2ea3575)

- **Session Management**: Organize assessment sessions with customizable scoring coefficients and merge multiple test results for comprehensive evaluation

  
 ![10 Merge Sessions](https://github.com/user-attachments/assets/ac2f910c-3b56-446a-95ff-dbb0e3f683e5)

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


## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.



---

**Built with ❤️ for educators and students worldwide**
