/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package triosignin;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author Theo
 */
public class DBConnect {
    
    public void DBConnect() {
        
    }
    
    public boolean isInSystem(String identifier) {
        boolean isIn = false;
        String value = identifier;
        
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Holder.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            String connectionUrl = "jdbc:sqlserver://10.123.96.51:1433;database=TRiO;user=TRiO;password=P@55w0rd!";
            String query = "SELECT * FROM Students WHERE id='" + value + "' OR cardnumber='" + value + "';";
            
            try (java.sql.Connection con = DriverManager.getConnection(connectionUrl)) {
                PreparedStatement stm = con.prepareStatement(query);
                ResultSet rs = stm.executeQuery();
                
                if(rs.next()) {
                    isIn = true;
                }
                else {
                   isIn = false; 
                }
                
                rs.close();
                stm.close();
                con.close();
                
                return isIn;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Holder.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return isIn;
    }
    
    public boolean hasOpenSession(String student) {
        boolean isIn = false;
        String value = student;
        
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Holder.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            String connectionUrl = "jdbc:sqlserver://10.123.96.51:1433;database=TRiO;user=TRiO;password=P@55w0rd!";
            String query = "SELECT * FROM Sessions WHERE studentid='" + value + "' AND isactive='1';";
            
            try (java.sql.Connection con = DriverManager.getConnection(connectionUrl)) {
                PreparedStatement stm = con.prepareStatement(query);
                ResultSet rs = stm.executeQuery();
                
                if(rs.next()) {
                    isIn = true;
                }
                else {
                   isIn = false; 
                }
                
                rs.close();
                stm.close();
                con.close();
                
                return isIn;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Holder.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return isIn;
    }
    
    public void addStudent(String id, String fname, String lname, String mi, String email, String classification, String cardnumber) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Holder.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            String connectionUrl = "jdbc:sqlserver://10.123.96.51:1433;database=TRiO;user=TRiO;password=P@55w0rd!";
            String insertQuery = "INSERT into Students (id, fname, lname, mi, email, classification, cardnumber) " +
                "VALUES ('" + id + "', '" + fname + "', '" + lname + "', '" + mi + "', '" + email + "', '" + classification + "', '" + cardnumber + "');";
            
            try (java.sql.Connection con = DriverManager.getConnection(connectionUrl)) {
                PreparedStatement stm = con.prepareStatement(insertQuery);
                stm.executeUpdate();
                
                stm.close();
                con.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Holder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void startSession(String student) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Holder.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            String connectionUrl = "jdbc:sqlserver://10.123.96.51:1433;database=TRiO;user=TRiO;password=P@55w0rd!";
            String insertQuery = "INSERT into Sessions VALUES ('" + student + "', '1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '', '1', 'null');";
            
            try (java.sql.Connection con = DriverManager.getConnection(connectionUrl)) {
                PreparedStatement stm = con.prepareStatement(insertQuery);
                stm.executeUpdate();
                
                stm.close();
                con.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Holder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void endSession(String student) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Holder.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            String connectionUrl = "jdbc:sqlserver://10.123.96.51:1433;database=TRiO;user=TRiO;password=P@55w0rd!";
            String insertQuery = "UPDATE Sessions SET endtime=CURRENT_TIMESTAMP WHERE studentid='" + student + "' AND isactive='1';";
            String insertTotalTime = "UPDATE Sessions SET totaltime=DATEDIFF(minute, starttime, endtime), isactive='0' WHERE studentid='" + student + "' AND isactive='1';";
            try (java.sql.Connection con = DriverManager.getConnection(connectionUrl)) {
                PreparedStatement stm = con.prepareStatement(insertQuery);
                stm.executeUpdate();
                stm = con.prepareStatement(insertTotalTime);
                stm.executeUpdate();
                
                stm.close();
                con.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Holder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getStudentID(String info) {
         
        if(info.length() > 10 ) { 
            String id = info;
               
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Holder.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                String connectionUrl = "jdbc:sqlserver://10.123.96.51:1433;database=TRiO;user=TRiO;password=P@55w0rd!";
                String selectQuery = "SELECT id FROM Students WHERE cardnumber='" + info + "';";
                Statement stm = null;

            try (java.sql.Connection con = DriverManager.getConnection(connectionUrl)) {
                stm = con.createStatement();
                ResultSet rs = stm.executeQuery(selectQuery);                    
                while(rs.next()){
                    id = rs.getString("id");
                }

                stm.close();
                con.close();
            }
            } catch (SQLException ex) {
                    Logger.getLogger(Holder.class.getName()).log(Level.SEVERE, null, ex);
              }
            
            System.out.println(id);
            return id;
        }
        else {
            return info;
        }   
    }
    
    public void closeAllSessions() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Holder.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            String connectionUrl = "jdbc:sqlserver://10.123.96.51:1433;database=TRiO;user=TRiO;password=P@55w0rd!";
            String insertQuery = "UPDATE Sessions SET isactive='0' WHERE isactive='1';";
            try (java.sql.Connection con = DriverManager.getConnection(connectionUrl)) {
                PreparedStatement stm = con.prepareStatement(insertQuery);
                stm.executeUpdate();
                
                stm.close();
                con.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Holder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
