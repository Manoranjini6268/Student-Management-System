import java.sql.*;
import java.util.Scanner;

public class Student {


    private static final String URL = "jdbc:mysql://localhost:3306/jdbc";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "mano6268";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\n--- Student Database Management ---");
                System.out.println("1. Add Student");
                System.out.println("2. Update Student");
                System.out.println("3. Delete Student");
                System.out.println("4. View All Students");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline left-over

                switch (choice) {
                    case 1 -> addStudent(connection, scanner);
                    case 2 -> updateStudent(connection, scanner);
                    case 3 -> deleteStudent(connection, scanner);
                    case 4 -> fetchAllStudents(connection);
                    case 5 -> {
                        System.out.println("Exiting...");
                        connection.close();
                        scanner.close();
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Method to fetch all students
    private static void fetchAllStudents(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Student");

        System.out.println("Student Records:");
        while (resultSet.next()) {
            System.out.println("ID: " + resultSet.getInt("student_id") +
                    ", First Name: " + resultSet.getString("first_name") +
                    ", Last Name: " + resultSet.getString("last_name") +
                    ", Year of Study: " + resultSet.getInt("year_of_study") +
                    ", Department: " + resultSet.getString("department") +
                    ", Email: " + resultSet.getString("email"));
        }
        resultSet.close();
        statement.close();
    }


    private static void addStudent(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter Year of Study (1-4): ");
        int yearOfStudy = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.print("Enter Department: ");
        String department = scanner.nextLine();
        System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
        String dateOfBirth = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Phone Number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();

        String sql = "INSERT INTO Student (first_name, last_name, year_of_study, department, date_of_birth, email, phone_number, address) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, firstName);
        statement.setString(2, lastName);
        statement.setInt(3, yearOfStudy);
        statement.setString(4, department);
        statement.setDate(5, Date.valueOf(dateOfBirth));
        statement.setString(6, email);
        statement.setString(7, phoneNumber);
        statement.setString(8, address);

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("A new student was inserted successfully!");
        }
        statement.close();
    }

    private static void updateStudent(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter Student ID to update: ");
        int studentId = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        System.out.print("Enter New First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter New Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter New Year of Study (1-4): ");
        int yearOfStudy = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.print("Enter New Department: ");
        String department = scanner.nextLine();
        System.out.print("Enter New Date of Birth (YYYY-MM-DD): ");
        String dateOfBirth = scanner.nextLine();
        System.out.print("Enter New Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter New Phone Number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter New Address: ");
        String address = scanner.nextLine();

        String sql = "UPDATE Student SET first_name = ?, last_name = ?, year_of_study = ?, department = ?, " +
                "date_of_birth = ?, email = ?, phone_number = ?, address = ? WHERE student_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, firstName);
        statement.setString(2, lastName);
        statement.setInt(3, yearOfStudy);
        statement.setString(4, department);
        statement.setDate(5, Date.valueOf(dateOfBirth));
        statement.setString(6, email);
        statement.setString(7, phoneNumber);
        statement.setString(8, address);
        statement.setInt(9, studentId);

        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Student details updated successfully!");
        }
        statement.close();
    }
    private static void deleteStudent(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter Student ID to delete: ");
        int studentId = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        String sql = "DELETE FROM Student WHERE student_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, studentId);

        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("A student was deleted successfully!");
        }
        statement.close();
    }
}
