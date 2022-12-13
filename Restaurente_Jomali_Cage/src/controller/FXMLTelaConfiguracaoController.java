/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ConnectDB_Configuracoes;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import main.Main;

/**
 * FXML Controller class
 *
 * @author Theus
 */
public class FXMLTelaConfiguracaoController implements Initializable {
    private static boolean fleg = false;
    @FXML
    private TextField TextFieldURL; 
    @FXML
    private TextField TextFieldUsuario; 
    @FXML
    private TextField TextFieldSenha; 
    
    @FXML
    private Label labelAlerta; 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    public void GerarTxt() {
        String diretorio = JOptionPane.showInputDialog(null, "Digite o Diretório da Pasta onde deseja salvar o arquivo");
        String arq = diretorio + "\\ComoCriarOBancoDeDados.txt";
        //File arqC = new File(arq);
        
        if(diretorio != null) {
            System.out.println("Diretório -> " + arq);
            try(FileWriter arqW = new FileWriter(arq, false);
                //BufferedWriter bf = new BufferedWriter(arqW);
                PrintWriter EsqArq = new PrintWriter(arqW);
                    ) {
                    EsqArq.append("#Use um Banco de Dados PostgreSQL para não ocorrer Erros! \n\n");
                    EsqArq.append("Como Criar o Banco de Dados: ");
                    EsqArq.append("\n\n");
                    EsqArq.append("Nome do Banco de dados: Restaurante \n");
                    EsqArq.append("Tabelas: \n\n");
                    EsqArq.append("CREATE TABLE configuracoes(url text, usuario text, senha text);");
                    EsqArq.append("\n\n");
                    EsqArq.append("CREATE TABLE cardapio(id int2, tipo int2, refeicao varchar(50), ingredientes text, valor float4);");
                    EsqArq.append("\n\n");
                    EsqArq.append("CREATE TABLE pedidos(id int2, mesa int2, estado varchar(30), listaderefeicao text);");
                    EsqArq.append("\n\n");
                    EsqArq.append("CREATE TABLE mesas(numero int2, lugares int2, estado varchar(30));");
                    EsqArq.append("\n\n");
                    EsqArq.append("OBS: Crie as tabelas no Banco de dados nomeado de Restaurante com todas a respectivas colunas informada nos códigos. \n");
                    EsqArq.append("Ou seja, Apenas copie e cole, não modifique nada");
                    System.out.print("Escrito");
                    arqW.close();
            
                    fleg = true;
                    labelAlerta.setText("Arquivo Gerado!!");
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void Configurar() throws SQLException {
        if(tratamentoConfigurar()) {
            String URL = TextFieldURL.getText();
            String Usuario = TextFieldUsuario.getText();
            String Senha = TextFieldSenha.getText();
            //
            String diretorio = "C:\\RESTAURANTE";
            String arq = "C:\\RESTAURANTE\\DB.txt";
            File fp = new File(diretorio); 
            if (!fp.exists()) {
                fp.mkdirs();
            }
            try(FileWriter arqW = new FileWriter(arq, false);
                PrintWriter EsqArq = new PrintWriter(arqW);
                ){
                    EsqArq.append(URL + "\n");
                    EsqArq.append(Usuario + "\n");
                    EsqArq.append(Senha + "\n");
                } catch(IOException ex) {}

            ConnectDB_Configuracoes conn = new ConnectDB_Configuracoes(URL, Usuario, Senha);
            //Checando se dá para se conectar a um banco de dados com os dados digitados
            if(conn.Connect()) {
                conn.insert(URL, Usuario, Senha);
                labelAlerta.setText("Banco de Dados configurados com sucesso");
            } else JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados!! Talvez seja os dados informados que estão errado. Digite novamente!", "Erro!", JOptionPane.ERROR_MESSAGE);     
        }
    }
    
    public void Voltar() {
        Main m = new Main();
        m.mudaTela("/view/FXMLTelaEntrada.fxml");
    }
    
    public boolean tratamentoConfigurar() {
        boolean ret = false; 
        if(fleg) {
            if(TextFieldURL != null && TextFieldUsuario != null && TextFieldSenha != null) ret = true; 
            else JOptionPane.showMessageDialog(null, "Algumas informações não foram digitadas", "Alerta", JOptionPane.WARNING_MESSAGE); 
        } else JOptionPane.showMessageDialog(null, "Arquivo de Configuração do Banco de dados não Gerado. Gere e leia o mesmo e crie o banco de dados conforme as informações dadas nele depois volte aqui", "Alerta", JOptionPane.WARNING_MESSAGE);
        return ret;
    }

    
    
}
