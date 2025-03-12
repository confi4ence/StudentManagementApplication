/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.studentmanagementapplication;

/**
 *
 * @author Confidence
 */
import javax.swing.*;
import java.util.ArrayList;

class Student {
    String id;
    String name;
    String email;
    int age;
    String course;

    public Student(String id, String name, String email, int age, String course) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.course = course;
    }
}

class StudentManagement {
    private ArrayList<Student> students = new ArrayList<>();

    public void saveStudent(Student student) {
        students.add(student);
    }

    public Student searchStudent(String id) {
        for (Student student : students) {
            if (student.id.equals(id)) {
                return student;
            }
        }
        return null;
    }

    public boolean deleteStudent(String id) {
        Student studentToDelete = searchStudent(id);
        if (studentToDelete != null) {
            students.remove(studentToDelete);
            return true;
        }
        return false;
    }

    public String generateReport() {
        if (students.isEmpty()) {
            return "No students to report.";
        }
        StringBuilder report = new StringBuilder();
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            report.append("STUDENT ").append(i + 1).append("\n")
                  .append("------------------------------\n")
                  .append("STUDENT ID: ").append(student.id).append("\n")
                  .append("STUDENT NAME: ").append(student.name).append("\n")
                  .append("STUDENT AGE: ").append(student.age).append("\n")
                  .append("STUDENT EMAIL: ").append(student.email).append("\n")
                  .append("STUDENT COURSE: ").append(student.course).append("\n")
                  .append("------------------------------\n\n");
        }
        return report.toString();
    }

    public void exitApplication() {
        JOptionPane.showMessageDialog(null, "Exiting the application. Goodbye!");
        System.exit(0);
    }
}

public class StudentManagementApplication {
    private static StudentManagement studentManagement = new StudentManagement();

    public static void main(String[] args) {
        while (true) {
            String input = JOptionPane.showInputDialog(null, 
                "STUDENT MANAGEMENT APPLICATION\n" +
                "Enter (1) to launch menu or any other key to exit", 
                "Menu", JOptionPane.INFORMATION_MESSAGE);
            
            if (!"1".equals(input)) {
                studentManagement.exitApplication();
            }
            showMenu();
        }
    }

    private static void showMenu() {
        String[] options = {"Capture a new student", "Search for a student", 
                            "Delete a student", "Print student report", "Exit"};
        int choice = JOptionPane.showOptionDialog(null, 
                "Please select one of the following menu items:", 
                "Main Menu", 
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.INFORMATION_MESSAGE, 
                null, options, options[0]);

        switch (choice) {
            case 0 -> captureNewStudent();
            case 1 -> searchStudent();
            case 2 -> deleteStudent();
            case 3 -> printStudentReport();
            case 4 -> studentManagement.exitApplication();
            default -> JOptionPane.showMessageDialog(null, "Invalid choice.");
        }
    }

    private static void captureNewStudent() {
        String id = JOptionPane.showInputDialog("Enter the student id:");
        String name = JOptionPane.showInputDialog("Enter the student name:");
        String email = JOptionPane.showInputDialog("Enter the student email:");
        int age = getValidAge();
        String course = JOptionPane.showInputDialog("Enter the student course:");

        Student student = new Student(id, name, email, age, course);
        studentManagement.saveStudent(student);
        JOptionPane.showMessageDialog(null, "Student details have been successfully saved!");
    }

    private static int getValidAge() {
        int age = -1;
        while (age < 16) {
            String ageInput = JOptionPane.showInputDialog("Enter the student age (must be >= 16):");
            try {
                age = Integer.parseInt(ageInput);
                if (age < 16) {
                    JOptionPane.showMessageDialog(null, "You have entered an incorrect student age!\nPlease re-enter the student age >>");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "You have entered an incorrect student age character!\nPlease re-enter the student age >>");
            }
        }
        return age;
    }

    private static void searchStudent() {
        String id = JOptionPane.showInputDialog("Enter the student id to search:");
        Student student = studentManagement.searchStudent(id);
        if (student != null) {
            String details = "STUDENT ID: " + student.id + "\n" +
                             "STUDENT NAME: " + student.name + "\n" +
                             "STUDENT AGE: " + student.age + "\n" +
                             "STUDENT EMAIL: " + student.email + "\n" +
                             "STUDENT COURSE: " + student.course;
            JOptionPane.showMessageDialog(null, details);
        } else {
            JOptionPane.showMessageDialog(null, "Student with ID: " + id + " was not found!");
        }
    }

    private static void deleteStudent() {
        String id = JOptionPane.showInputDialog("Enter the student id to delete:");
        int confirm = JOptionPane.showConfirmDialog(null, 
            "Are you sure you want to delete student ID: " + id + " from the system? Yes (y) to delete.", 
            "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (studentManagement.deleteStudent(id)) {
                JOptionPane.showMessageDialog(null, "Student with ID: " + id + " WAS deleted!");
            } else {
                JOptionPane.showMessageDialog(null, "Student not found.");
            }
        }
    }

    private static void printStudentReport() {
        String report = studentManagement.generateReport();
        JOptionPane.showMessageDialog(null, report);
    }
}
