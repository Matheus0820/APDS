/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

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
public class ConectDB {
   
    
    private String url = "jdbc:postgresql://localhost/pedidoss";
    private String user = "postgres";
    private String password = "369963";
    int langth = 0;
    
    //ArrayList<Pedido> Ltped = new ArrayList<>();
    public ConectDB(String url, String user, String password) {
		super();
		this.url = url;
		this.user = user;
		this.password = password;
	}
    public ConectDB() {
        
    }
    
    //FXMLTelaCozinheiroController ControllerCZ = new FXMLTelaCozinheiroController();
    //Trafego traf = new Trafego();
    
    public String Conectar() {
        String lista = "";
        try(Connection connection = DriverManager.getConnection(url, user, password) ) {
            if(connection != null) {
                System.out.println("Connected...");
            } else System.out.println("Failed to connect...");
            Statement sqlQuery = connection.createStatement();
            ResultSet rset = sqlQuery.executeQuery("SELECT * from pedidos");
            //String s;
            lista = ""; 
            //ArrayList<Pedido> Ltped = new ArrayList<>();
            while(rset.next()) {
                //Pedido p1 = new Pedido(rset.getInt("id"), rset.getInt("mesa"), rset.getString("pedido"), rset.getString("estado"));
                //Ltped.add(p1);
                langth++;
                //s = rset.getInt("id") + ";" + rset.getInt("mesa") + ";" + rset.getString("pedido") + ";" + rset.getString("estado");
                lista += rset.getInt("id") + ";" + rset.getInt("mesa") + ";" + rset.getString("pedido") + ";" + rset.getString("estado") + "\n";
                //System.out.println(langth);
                //return s; 
            }
            int i = 0;
            while (i < langth) {
                //lista += Ltped.get(i).getId() + ";" + Ltped.get(i).getMesa() + ";" + Ltped.get(i).getPedido() + ";" + Ltped.get(i).getEstado() + "\n";
                //System.out.println(lista + "\n");
                //traf.TrafNovopedido(langth, Ltped.get(i).getId(), Ltped.get(i).getMesa(), Ltped.get(i).getPedido(), Ltped.get(i).getEstado());
                //ControllerCZ.novoPedido(langth, Ltped.get(i).getId(), Ltped.get(i).getMesa(), Ltped.get(i).getPedido(), Ltped.get(i).getEstado());
                //System.out.println(Ltped.get(i).getId() + ";" + Ltped.get(i).getMesa() + ";" + Ltped.get(i).getPedido() + ";" + Ltped.get(i).getEstadoPedido());
                i++;
                
            }
			
            
        } catch(SQLException e) {
            e.printStackTrace();
        } 
        return lista;
    }
    
    public void Alterar(int id, String estado) {
        try(Connection connection = DriverManager.getConnection(url, user, password) ) {
            if(connection != null) {
                System.out.println("Connected...");
            } else System.out.println("Failed to connect...");
            Statement sqlQuery = connection.createStatement();
            //ResultSet rset = sqlQuery.executeQuery("SELECT * from pedidos");
            //String update = "UPDATE pedidoss SET estado = '" + estado + "' WHERE id =" + id + ";";
            String update = "UPDATE pedidos SET estado =? WHERE id =?";
            PreparedStatement ps = connection.prepareStatement(update);
            //sqlQuery.executeUpdate(update); // Tá acontecendo erro aqui -> pesquisar como dar um update por aqui	
            
            ps.setString(1, estado);
            ps.setInt(2, id);
            ps.executeUpdate();
            
        } catch(SQLException e) {
            e.printStackTrace();
        } 
    }
    
}
