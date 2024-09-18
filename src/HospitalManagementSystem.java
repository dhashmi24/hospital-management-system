package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url="jdbc:mysql://localhost:3306/hospital";
    private static final String username="root";
    private static final String password="dhashu2407";
    public static void main(String[] args){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        Scanner scanner=new Scanner(System.in);
        try{
            Connection con= DriverManager.getConnection(url,username,password);
            Patient patient=new Patient(con, scanner);
            Doctor doctor=new Doctor(con);
            while(true){
                System.out.println("Hospital Management System");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patient");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.println("Enter your choice: ");
                int choice=scanner.nextInt();
                switch (choice){
                    case 1:
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        patient.viewPatient();
                        System.out.println();
                        break;
                    case 3:
                        doctor.viewDoctors();
                        System.out.println();
                        break;
                    case 4:
                        bookAppointment(patient,doctor,con,scanner);
                        System.out.println();
                        break;
                    case 5:
                        System.out.println("Thank You!!!");
                        return;
                    default:
                        System.out.println("Enter valid choice!");
                        break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void bookAppointment(Patient patient,Doctor doctor, Connection con, Scanner scanner){
        System.out.printf("Enter Patient Id: ");
        int pid=scanner.nextInt();
        System.out.printf("Enter Doctor Id: ");
        int did=scanner.nextInt();
        System.out.printf("Enter appointment date (YYYY-MM-DD): ");
        String apDate=scanner.next();
        if(patient.getPatientById(pid) && doctor.getDoctorById(did)){
            if(checkDoctorAvailability(did,apDate,con)){
                String query="INSERT INTO appointments(patient_id,doctor_id,appointment_date) VALUES (?,?,?)";
                try{
                    PreparedStatement ps=con.prepareStatement(query);
                    ps.setInt(1,pid);
                    ps.setInt(2,did);
                    ps.setString(3,apDate);
                    int res=ps.executeUpdate();
                    if(res>0){
                        System.out.println("Appointment booked");
                    }else {
                        System.out.println("Failed to book");
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }else{
                System.out.println("Doctor not available.");
            }
        }else{
            System.out.println("Not Exist!");
        }
    }
    public static boolean checkDoctorAvailability(int did,String apDate,Connection con){
        String query="SELECT COUNT(*) FROM appointments WHERE doctor_id=? AND appointment_date=?";
        try{
            PreparedStatement ps= con.prepareStatement(query);
            ps.setInt(1,did);
            ps.setString(2,apDate);
            ResultSet r=ps.executeQuery();
            if(r.next()){
                int c=r.getInt(1);
                if(c==0){
                    return true;
                }else{
                    return false;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
