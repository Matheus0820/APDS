/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Theus
 */
public class ConnectDB_Mesas {
    ConnectDB_Configuracoes conn = new ConnectDB_Configuracoes();
    
    private static Connection connection;
    private static Statement sqlQuery;
    private static ResultSet rset;
    
    private String url; 
    private String user; 
    private String password; 
    
    public void Connect() {
        try {
            conn.Connect();
            String dadosDB[] = conn.select().split(";");
            this.url = dadosDB[0];
            this.user = dadosDB[1];
            this.password = dadosDB[2];
            connection = DriverManager.getConnection(url, user, password);
            if(connection != null) {
                System.out.println("Connected...");
            } else System.out.println("Failed to connect...");
            sqlQuery = connection.createStatement();
            rset = sqlQuery.executeQuery("SELECT * from mesas");
            
        } catch(SQLException e) {
            e.printStackTrace();
        }  
    }
    public String select() throws SQLException {
        String lista = "";
        rset = sqlQuery.executeQuery("SELECT * from mesas");
        
        while(rset.next()) {
            
                lista += rset.getInt("numero") + ";" + rset.getInt("lugares") + ";" + rset.getString("estado") + "\n";
        }
        
        return lista;
    }
    
    public void alter(int numero, String estado) throws SQLException {
        String update = "update mesas set estado =? where numero =?";
        
        PreparedStatement ps = connection.prepareStatement(update);
        
        ps.setString(1, estado);
        ps.setInt(2, numero);
        
        ps.execute();
    }
    
    public void insert(int numero, int lugares, String estado) throws SQLException {
        String insert = "insert into mesas values(?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(insert);
        
        ps.setInt(1, numero);
        ps.setInt(2, lugares);
        ps.setString(3, estado);
        
        ps.execute();
        
    }
    public void delete(int numero) throws SQLException {
        String delete = "delete from mesas where numero = ?";
        
        PreparedStatement ps = connection.prepareStatement(delete);
        
        ps.setInt(1, numero);
        
        ps.execute();
    }
}
