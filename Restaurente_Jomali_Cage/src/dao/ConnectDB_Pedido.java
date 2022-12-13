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
public class ConnectDB_Pedido {
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
            rset = sqlQuery.executeQuery("SELECT * from pedidos");
            
        } catch(SQLException e) {
            e.printStackTrace();
        }  
    }
    
    public String select() throws SQLException {
        String lista = "";
        rset = sqlQuery.executeQuery("SELECT * from pedidos");
        
        while(rset.next()) {
            lista += rset.getInt("id") + ";" + rset.getInt("mesa") + ";" + rset.getString("estado") + ";" + rset.getString("listaderefeicao") + "\n";
        }
        
        return lista;
    }
    public void insert(int id, int mesa, String refeicoes) throws SQLException {
        String insert = "insert into pedidos values(?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(insert);
        
        ps.setInt(1, id);
        ps.setInt(2, mesa);
        ps.setString(3, "Em espera");
        ps.setString(4, refeicoes);
        
        ps.execute();
        
    }
    
    public void update(int id, String estado) throws SQLException {

            String update = "UPDATE pedidos SET estado =? WHERE id =?";
            PreparedStatement ps = connection.prepareStatement(update);	
            
            ps.setString(1, estado);
            ps.setInt(2, id);
            ps.executeUpdate();
            
        
    }
    
}
