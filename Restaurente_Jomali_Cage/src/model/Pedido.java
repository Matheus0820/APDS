/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;

/**
 *
 * @author Theus
 */
public class Pedido {
    private int id;
    private int Mesa;
    private String estado;
    private String lpedido;  
    
    public Pedido(int id, int Mesa, String Pedido, String Estado) {
        this.id = id;
        this.Mesa = Mesa;
        this.lpedido = Pedido;
    }

    public String getLpedido() {
        return lpedido;
    }
    public void setLpedido(String lpedido) {
        this.lpedido = lpedido;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getMesa() {
        return Mesa;
    }

    public void setMesa(int NumMesa) {
        this.Mesa = NumMesa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    
    
}
