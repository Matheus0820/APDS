/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Theus
 */
public class ConnectDB_Cardapio {
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
            rset = sqlQuery.executeQuery("SELECT * from cardapio");
            
        } catch(SQLException e) {
            e.printStackTrace();
        }  
    }
    public String select() throws SQLException {
        String lista = "";
        rset = sqlQuery.executeQuery("SELECT * from cardapio");
        
        while(rset.next()) {
            
                lista += rset.getInt("id") + ";" + rset.getInt("tipo") + ";" + rset.getString("refeicao") + ";" + rset.getString("ingredientes") + ";" + rset.getFloat("valor") + "\n";
        }
        
        return lista;
    }
    
    public void alter(int id, String refeicao, String ingredientes, float valor) throws SQLException {
        String update = "update cardapio set refeicao =?, ingredientes = ?, valor =? where id =?";
        
        PreparedStatement ps = connection.prepareStatement(update);
        
        ps.setString(1, refeicao);
        ps.setString(2, ingredientes);
        ps.setFloat(3, valor);
        ps.setInt(4, id);
        
        ps.execute();
    }
    
    public void insert(int id, int tipo, String refeicao, String ingredientes, float valor) throws SQLException {
        String insert = "insert into cardapio values(?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(insert);
        
        ps.setInt(1, id);
        ps.setInt(2, tipo);
        ps.setString(3, refeicao);
        ps.setString(4, ingredientes);
        ps.setFloat(5, valor);
        
        ps.execute();
        
    }
    
    public void delete(int id) throws SQLException {
         String delete = "delete from cardapio where id = ?";
        
        PreparedStatement ps = connection.prepareStatement(delete);
        
        ps.setInt(1, id);
        
        ps.execute();
    }
    
    public void deleteTudo() throws SQLException {
        String delete = "delete from cardapio where id != 0";
        PreparedStatement ps = connection.prepareStatement(delete);
        ps.execute();
    }
    
}
