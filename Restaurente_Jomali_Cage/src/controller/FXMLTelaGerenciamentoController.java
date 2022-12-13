/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ConnectDB_Pedido;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javax.swing.JOptionPane;
import main.Main;

/**
 *
 * @author Theus
 */
public class FXMLTelaGerenciamentoController {
    FXMLTelaCardapioController cd = new FXMLTelaCardapioController();
    ConnectDB_Pedido conn = new ConnectDB_Pedido();
    
    @FXML 
    private Label labelAlerta;
    
    public void GerarRelatorio() throws SQLException {
        String diretorio = JOptionPane.showInputDialog(null, "Digite o Diretório da Pasta onde deseja salvar o arquivo");
        String arq = diretorio + "\\Relatorio.csv";
        //File arqC = new File(arq);
        
        if(diretorio != null) {
            System.out.println("Diretório -> " + arq);
            try(FileWriter arqW = new FileWriter(arq, false);
                //BufferedWriter bf = new BufferedWriter(arqW);
                PrintWriter EsqArq = new PrintWriter(arqW);
                    ) {
                    conn.Connect();
                    String linhas[] = conn.select().split("\n");
                    for(int i = 0; i < linhas.length; i++) {
                        String linha[] = linhas[i].split(";");
                        String id = linha[0];
                        String nummesa = linha[1];
                        
                        EsqArq.append("ID do Pedido: " + id + "\nNúmero da Mesa: " + nummesa + "\n");
                        
                        String Vpeds[] = linha[3].split("-");
                        for(int j = 0; j < Vpeds.length; j++) {
                            String Iped[] = Vpeds[j].split(",");
                            EsqArq.append("ID: " + Iped[0] + ";Refeição: " + Iped[1] + ";Quantidade: " + Iped[2] + "\n"); 
                        }
                        EsqArq.append("\n");
                    }
                    EsqArq.append("\n\n");
                    EsqArq.append("Total de Pedidos: " + linhas.length + " Pedidos");
                    EsqArq.append("\n\n");
                    
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Date date = new Date();
                    EsqArq.append("Momento de Geração do Relatório:;" + dateFormat.format(date));
                    
                    arqW.close();
                    labelAlerta.setText("Relatório Gerado com sucesso");
            } catch(IOException ex) {}
        }
    }
    
    public void TelaCardapio() {
        Main m = new Main();
        String tla = "/view/FXMLTelaCardapio.fxml";
        m.mudaTela(tla);
       // cd.iniciar();
        
    }
    
    public void Voltar() {
        Main m = new Main();
        m.mudaTela("/view/FXMLTelaEntrada.fxml");
    }
    
        
}
