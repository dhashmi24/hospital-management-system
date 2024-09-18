package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Patient {
    private Connection con;
    private Scanner scanner;

    public Patient(Connection con, Scanner scanner){
        this.con=con;
        this.scanner=scanner;
    }

    public void addPatient(){
        System.out.printf("Enter Patient Name: ");
        String name=scanner.next();
        System.out.printf("Enter Patient Age: ");
        int age=scanner.nextInt();
        System.out.printf("Enter Patient Gender: ");
        String gender=scanner.next();

        try{
            String query="INSERT INTO patients(name,age,gender) VALUES(?,?,?)";
            PreparedStatement ps=con.prepareStatement(query);
            ps.setString(1,name);
            ps.setInt(2,age);
            ps.setString(3,gender);
            int r=ps.executeUpdate();
            if(r>0){
                System.out.println("Patient added successfully");
            } else{
                System.out.println("Failed to add patient");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void viewPatient(){
        String query="SELECT * FROM patients";
        try{
            PreparedStatement ps=con.prepareStatement(query);
            ResultSet res=ps.executeQuery();
            System.out.println("Patients: ");
            System.out.println("-----------------------------------------------------------");
            System.out.println("|Patient Id  |Name              |Age  |Gender             |");
            System.out.println("-----------------------------------------------------------");
            while(res.next()){
                int id=res.getInt("id");
                String name=res.getString("name");
                int age=res.getInt("age");
                String gender=res.getString("gender");
                System.out.printf("|%-12s|%-18s|%-5s|%-19s|\n",id,name,age,gender);
                System.out.println("-----------------------------------------------------------");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean getPatientById(int id){
        String query="SELECT * FROM patients WHERE id=?";
        try{
            PreparedStatement ps=con.prepareStatement(query);
            ps.setInt(1,id);
            ResultSet res=ps.executeQuery();
            if(res.next()){
                return true;
            } else{
                return false;

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

}
