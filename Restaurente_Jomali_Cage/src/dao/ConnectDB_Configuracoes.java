/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 *
 * @author Theus
 */
public class ConnectDB_Configuracoes {
    private static Connection connection;
    private static Statement sqlQuery;
    private static ResultSet rset;
    
    private String url; 
    private String user; 
    private String password; 
    
    public ConnectDB_Configuracoes() {
        
    }

    public ConnectDB_Configuracoes(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }
    
    
    public boolean Connect() {
        boolean ret = false;
        String arq = "C:\\RESTAURANTE\\DB.txt";
        File fp = new File(arq);
        Scanner sc = null;
        try {       
            if (!fp.exists()) fp.mkdirs();
            sc = new Scanner(fp);
            if(sc.hasNextLine()) {
                this.url = sc.nextLine();               
                this.user = sc.nextLine();               
                this.password = sc.nextLine();
            }
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
        finally {
            if (sc != null) {
                sc.close(); //fecha recurso ao fim e evita excessão de ponteiro nulo
            }
        }
        try {
            connection = DriverManager.getConnection(url, user, password);
            if(connection != null) {
                System.out.println("Connected...");
                ret = true;
            } else System.out.println("Failed to connect...");
            sqlQuery = connection.createStatement();
            rset = sqlQuery.executeQuery("SELECT * from configuracoes");
            
        } catch(SQLException e) {
            e.printStackTrace();
        }  
        return ret;
    }
    
    public String select() throws SQLException {
        String lista = "";
        rset = sqlQuery.executeQuery("SELECT * from configuracoes");
        while(rset.next()) {
            lista = rset.getString("url") + ";" + rset.getString("usuario") + ";" + rset.getString("senha");
        }
        
        return lista;
        
    }
    public void insert(String url, String usuario, String senha) throws SQLException {
        String insert = "insert into configuracoes values(?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(insert);
        
        ps.setString(1, url);
        ps.setString(2, usuario);
        ps.setString(3, senha);
        
        ps.execute();
        
    }
}
