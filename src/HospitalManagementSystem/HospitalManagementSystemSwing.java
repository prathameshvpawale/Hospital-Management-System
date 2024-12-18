package HospitalManagementSystem;

import HospitalManagementSystem.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;

public class HospitalManagementSystemSwing {
    private JFrame frame;
    private Connection connection;

    public HospitalManagementSystemSwing(Connection connection) {
        this.connection = connection;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Hospital Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);

        JPanel mainPanel = new JPanel(new GridLayout(5, 1));
        JButton btnAddPatient = new JButton("Add Patient");
        JButton btnViewPatients = new JButton("View Patients");
        JButton btnViewDoctors = new JButton("View Doctors");
        JButton btnAddAppointment = new JButton("Add Appointment");
        JButton btnViewAppointments = new JButton("View Appointments");

        mainPanel.add(btnAddPatient);
        mainPanel.add(btnViewPatients);
        mainPanel.add(btnViewDoctors);
        mainPanel.add(btnAddAppointment);
        mainPanel.add(btnViewAppointments);

        frame.add(mainPanel);
        frame.setVisible(true);

        btnAddPatient.addActionListener(e -> showAddPatientPanel());
        btnViewPatients.addActionListener(e -> showViewPatientsPanel());
        btnViewDoctors.addActionListener(e -> showViewDoctorsPanel());
        btnAddAppointment.addActionListener(e -> showAddAppointmentPanel());
        btnViewAppointments.addActionListener(e -> showViewAppointmentsPanel());
    }

    private void showAddPatientPanel() {
        JFrame addPatientFrame = new JFrame("Add Patient");
        addPatientFrame.setSize(400, 300);
        addPatientFrame.setLayout(new GridLayout(4, 2));

        JLabel lblName = new JLabel("Name:");
        JTextField txtName = new JTextField();
        JLabel lblAge = new JLabel("Age:");
        JTextField txtAge = new JTextField();
        JLabel lblGender = new JLabel("Gender:");
        JTextField txtGender = new JTextField();
        JButton btnSubmit = new JButton("Submit");

        addPatientFrame.add(lblName);
        addPatientFrame.add(txtName);
        addPatientFrame.add(lblAge);
        addPatientFrame.add(txtAge);
        addPatientFrame.add(lblGender);
        addPatientFrame.add(txtGender);
        addPatientFrame.add(new JLabel()); // Placeholder
        addPatientFrame.add(btnSubmit);

        addPatientFrame.setVisible(true);

        btnSubmit.addActionListener(e -> {
            String name = txtName.getText();
            int age = Integer.parseInt(txtAge.getText());
            String gender = txtGender.getText();

            Patient patient = new Patient(connection);
            patient.addPatient(name, age, gender);
            JOptionPane.showMessageDialog(addPatientFrame, "Patient added successfully!");
            addPatientFrame.dispose();
        });
    }

    private void showViewPatientsPanel() {
        JFrame viewPatientFrame = new JFrame("View Patients");
        viewPatientFrame.setSize(500, 400);

        String[] columnNames = {"Patient ID", "Name", "Age", "Gender"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);

        Patient patient = new Patient(connection);
        try {
            ResultSet rs = patient.viewPatients();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String gender = rs.getString("gender");
                model.addRow(new Object[]{id, name, age, gender});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(table);
        viewPatientFrame.add(scrollPane);
        viewPatientFrame.setVisible(true);
    }

    private void showViewDoctorsPanel() {
        JFrame viewDoctorFrame = new JFrame("View Doctors");
        viewDoctorFrame.setSize(600, 400);

        String[] columnNames = {"Doctor ID", "Name", "Specialization"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);

        Doctor doctor = new Doctor(connection);
        try {
            ResultSet rs = doctor.viewDoctors();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String specialization = rs.getString("specialization");
                model.addRow(new Object[]{id, name, specialization});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(table);
        viewDoctorFrame.add(scrollPane);
        viewDoctorFrame.setVisible(true);
    }


    private void showAddAppointmentPanel() {
        JFrame addAppointmentFrame = new JFrame("Add Appointment");
        addAppointmentFrame.setSize(400, 300);
        addAppointmentFrame.setLayout(new GridLayout(5, 2));

        JLabel lblPatientId = new JLabel("Patient ID:");
        JTextField txtPatientId = new JTextField();
        JLabel lblDoctorId = new JLabel("Doctor ID:");
        JTextField txtDoctorId = new JTextField();
        JLabel lblDate = new JLabel("Date (YYYY-MM-DD):");
        JTextField txtDate = new JTextField();
        JLabel lblTime = new JLabel("Time (HH:MM:SS):");
        JTextField txtTime = new JTextField();
        JButton btnSubmit = new JButton("Submit");

        addAppointmentFrame.add(lblPatientId);
        addAppointmentFrame.add(txtPatientId);
        addAppointmentFrame.add(lblDoctorId);
        addAppointmentFrame.add(txtDoctorId);
        addAppointmentFrame.add(lblDate);
        addAppointmentFrame.add(txtDate);

        addAppointmentFrame.add(new JLabel()); // Placeholder
        addAppointmentFrame.add(btnSubmit);

        addAppointmentFrame.setVisible(true);

        btnSubmit.addActionListener(e -> {
            int patientId = Integer.parseInt(txtPatientId.getText());
            int doctorId = Integer.parseInt(txtDoctorId.getText());
            String date = txtDate.getText();
            String time = txtTime.getText();

            Appointment appointment = new Appointment(connection);
            appointment.addAppointment(patientId, doctorId, date);
            JOptionPane.showMessageDialog(addAppointmentFrame, "Appointment added successfully!");
            addAppointmentFrame.dispose();
        });
    }


    private void showViewAppointmentsPanel() {
        JFrame viewAppointmentFrame = new JFrame("View Appointments");
        viewAppointmentFrame.setSize(600, 400);

        String[] columnNames = {"Appointment ID", "Patient Name", "Doctor Name", "Date", "Time"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);

        Appointment appointment = new Appointment(connection);
        try {
            ResultSet rs = appointment.viewAppointments();

            while (rs.next()) {
                int id = rs.getInt("id");
                String patientName = rs.getString("patient_name");
                String doctorName = rs.getString("doctor_name");
                String date = rs.getString("appointment_date");

                model.addRow(new Object[]{id, patientName, doctorName, date});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(table);
        viewAppointmentFrame.add(scrollPane);
        viewAppointmentFrame.setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Connection connection = DatabaseConnection.getConnection();
                new HospitalManagementSystemSwing(connection);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
