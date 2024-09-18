package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Doctor {
    private Connection con;

    public Doctor(Connection con){
        this.con=con;
    }

    public void viewDoctors(){
        String query="SELECT * FROM doctors";
        try{
            PreparedStatement ps=con.prepareStatement(query);
            ResultSet res=ps.executeQuery();
            System.out.println("Doctors: ");
            System.out.println("-----------------------------------------------------------");
            System.out.println("|Doctor Id   |Name                   |Specialization     |");
            System.out.println("-----------------------------------------------------------");
            while(res.next()){
                int id=res.getInt("id");
                String name=res.getString("name");
                String specialization=res.getString("specialization");
                System.out.printf("|%-12s|%-23s|%-19s|\n",id,name,specialization);
                System.out.println("-----------------------------------------------------------");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean getDoctorById(int id){
        String query="SELECT * FROM doctors WHERE id=?";
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
