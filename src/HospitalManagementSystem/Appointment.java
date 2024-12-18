package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Appointment {
    private Connection connection;

    public Appointment(Connection connection) {
        this.connection = connection;
    }

    public void addAppointment(int doctorId, int patientId, String date) {
        String query = "INSERT INTO appointments (doctor_id, patient_id, appointment_date) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, doctorId);
            preparedStatement.setInt(2, patientId);
            preparedStatement.setString(3, date); // Expecting the date as a string in the format 'YYYY-MM-DD'.
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Appointment added successfully!");
            } else {
                System.out.println("Failed to add appointment.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public ResultSet viewAppointments() {
        String query = "SELECT a.id, p.name as patient_name, d.name as doctor_name, a.appointment_date " +
                "FROM appointments a " +
                "JOIN patients p ON a.patient_id = p.id " +
                "JOIN doctors d ON a.doctor_id = d.id";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();  // Print stack trace for debugging
        }
        return null;
    }

}


