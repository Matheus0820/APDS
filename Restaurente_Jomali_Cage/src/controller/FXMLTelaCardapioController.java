/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ConnectDB_Cardapio;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import main.Main;
import model.Cardapio;

/**
 *
 * @author Theus
 */
public class FXMLTelaCardapioController implements Initializable {
    private ObservableList<Cardapio> ObsListaCardapio; 
    List<Cardapio> listCardapio = new ArrayList<Cardapio>();
    
    ConnectDB_Cardapio conn = new ConnectDB_Cardapio();
    
    @FXML
    private TableView<Cardapio> TableViewCardapio;
    @FXML
    private TableColumn<Cardapio, Integer> TableColumnID;
    @FXML
    private TableColumn<Cardapio, Integer> TableColumnTipo;
    @FXML
    private TableColumn<Cardapio, String> TableColumnRefeicao;
    @FXML
    private TableColumn<Cardapio, String> TableColumnIngredientes;
    @FXML
    private TableColumn<Cardapio, Float> TableColumnValor;
    
    @FXML
    private Button ButtonImportar;
    @FXML
    private Button ButtonConcluir;
    @FXML
    private Button ButtonRemover;
    
    @FXML 
    private RadioButton RadioButtonTipo1;
    @FXML 
    private RadioButton RadioButtonTipo2;
    
    @FXML
    private TextField TextFieldNomeRefeicao;
    @FXML 
    private TextField TextFieldIngredientes;
    @FXML 
    private TextField TextFieldValor;
    
    @FXML
    private SplitMenuButton SplitMenuButtonIDsRefeicoes;        
    
    @FXML
    private TextField TextFieldModRefeicao;
    @FXML
    private TextField TextFieldModIngredientes; 
    @FXML
    private TextField TextFieldModValor; 
    
    ToggleGroup tgTipos = new ToggleGroup();
    ToggleGroup tgRmiID = new ToggleGroup();
    private static int idMd; 
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    //Quando a tela é carregada ele é chamado automaticamente
       RadioButtonTipo1.setToggleGroup(tgTipos);
       RadioButtonTipo2.setToggleGroup(tgTipos);
       
       TableColumnID.setCellValueFactory(new PropertyValueFactory<>("id"));
       TableColumnTipo.setCellValueFactory(new PropertyValueFactory<>("tipoRefeicao"));
       TableColumnRefeicao.setCellValueFactory(new PropertyValueFactory<>("refeicao"));
       TableColumnIngredientes.setCellValueFactory(new PropertyValueFactory<>("ingredientes"));
       TableColumnValor.setCellValueFactory(new PropertyValueFactory<>("valor"));

       conn.Connect();
       try {
        if(conn.select() != "") {
            recarregar();
        }
       } catch(SQLException ex) {}
    }
    
    public void recarregar() throws SQLException {
        SplitMenuButtonIDsRefeicoes.getItems().clear();
        if(listCardapio != null) listCardapio.clear();
        
        String linhas[] = conn.select().split("\n");
        for(int i = 0; i < linhas.length; i++) {
            Cardapio it1 = new Cardapio();
            String linha[] = linhas[i].split(";"); 
                
            it1.setId(Integer.parseInt(linha[0]));
            it1.setTipoRefeicao(Integer.parseInt(linha[1]));
            it1.setRefeicao(linha[2]);
            it1.setIngredientes(linha[3]);
            it1.setValor(Float.parseFloat(linha[4]));
            listCardapio.add(it1);  
                
            RadioMenuItem RadioMenuItemID = new RadioMenuItem();
            RadioMenuItemID.setId("Refeicao" + it1.getId());
            RadioMenuItemID.setText("" + it1.getId()); 
            RadioMenuItemID.setToggleGroup(tgRmiID);
            SplitMenuButtonIDsRefeicoes.getItems().addAll(RadioMenuItemID);
                
        }
        ObsListaCardapio = FXCollections.observableArrayList(listCardapio);
        TableViewCardapio.setItems(ObsListaCardapio);
           
       }

    public void importar() throws SQLException{
        String diretorio; 
        diretorio = JOptionPane.showInputDialog("Digite o caminho para o Arquivo em formato .csv do Cardápio");
      if(!conn.select().equals("")) {
          int conf = JOptionPane.showConfirmDialog(null, "Você está exportando outro cardapio, deseja manter o anterior e adicionar esse em conjunto? Aperte Sim para manter em conjunto", "Alerta!", JOptionPane.YES_NO_OPTION);
          if(conf != 0){
              conn.deleteTudo();
          }
      }
        try (BufferedReader br = new BufferedReader(new FileReader(diretorio))) {
            String linha = br.readLine();
            int idAnt = 1;
            conn.Connect();
            while (linha != null) {
                Cardapio it1 = new Cardapio();
                String[] Vlinha = linha.split("-");
                it1.setId(idAnt);
                it1.setTipoRefeicao(Integer.parseInt(Vlinha[0]));
                it1.setRefeicao(Vlinha[1]);
                it1.setIngredientes(Vlinha[2]);
                it1.setValor(Float.parseFloat(Vlinha[3]));
                
                linha = br.readLine();
                conn.insert(it1.getId(), it1.getTipoRefeicao(), it1.getRefeicao(), it1.getIngredientes(), it1.getValor());
                idAnt++;
            }
            
            recarregar();
        
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    
          
    }

    public void adicionar() throws SQLException {
        if(tratamendoAdicionar()) {
          //coloca o código de adicionar aqui!!  
          Cardapio it1 = new Cardapio();
          RadioButton TipoSelect = (RadioButton) tgTipos.getSelectedToggle();
          
          conn.Connect();
          String linhas[] = conn.select().split("\n"); 
          
          it1.setId(linhas.length + 1);
          it1.setTipoRefeicao(Integer.parseInt(TipoSelect.getText()));
          it1.setRefeicao(TextFieldNomeRefeicao.getText());
          it1.setIngredientes(TextFieldIngredientes.getText());
          it1.setValor(Float.parseFloat(TextFieldValor.getText()));
          
          listCardapio.clear(); 
          listCardapio.add(it1);
          
          conn.insert(it1.getId(), it1.getTipoRefeicao(), it1.getRefeicao(), it1.getIngredientes(), it1.getValor());  
          listCardapio.clear();
          recarregar();
        }
    }
    public void remover() throws SQLException {
        if(tratamentoRemover()) {
            RadioMenuItem IDSelect = (RadioMenuItem) tgRmiID.getSelectedToggle();
            conn.delete(Integer.parseInt(IDSelect.getText()));
            recarregar();
        }
    }
    public void modificar() throws SQLException {
        if(tratamentoModificar()) {
            int id = idMd;
            String refeicao = TextFieldModRefeicao.getText();
            String ingredientes = TextFieldModIngredientes.getText(); 
            float valor = Float.parseFloat(TextFieldModValor.getText());
            
            conn.alter(id, refeicao, ingredientes, valor); 
            
            SplitMenuButtonIDsRefeicoes.getItems().clear();
            listCardapio.clear();
            
           recarregar(); 
           
           TextFieldModRefeicao.setText("");
           TextFieldModIngredientes.setText("");
           TextFieldModValor.setText("");
        }
        
    } 
    
    public void TelaAnterior() {
        Main m = new Main();
        String tla = "/view/FXMLTelaGerenciamento.fxml";
        m.mudaTela(tla);
    }

   public void PuxarDados() {
       RadioMenuItem IDSelect = (RadioMenuItem) tgRmiID.getSelectedToggle();
       if(IDSelect != null) {
       
        conn.Connect();
        String linhas[];
        try {
            linhas = conn.select().split("\n");
            for(int i = 0; i < linhas.length; i++) {
                String linha[] = linhas[i].split(";");
                //System.out.println("ID: " + linha[0] + " - i = " + i + " - Texto: " + IDSelect.getText());
                if(Integer.parseInt(linha[0]) == Integer.parseInt(IDSelect.getText())) {
                    idMd = Integer.parseInt(linha[0]);
                    System.out.println("Entrou no IF");
                    TextFieldModRefeicao.setText(linha[2]);
                    TextFieldModIngredientes.setText(linha[3]);
                    TextFieldModValor.setText(linha[4]);
                    System.out.println("Deu certo");
                    i = linhas.length;
                }   
            } 
        } catch (SQLException ex) {}
       } else {
           JOptionPane.showMessageDialog(null, "ID da refeição não selecionado", "Erro!", JOptionPane.WARNING_MESSAGE);
       }   
   }
   
   public boolean tratamendoAdicionar() throws SQLException {
       boolean ret = false;
       if(TextFieldNomeRefeicao.getText() != null && TextFieldIngredientes != null && TextFieldValor != null) {
           String linhas[] = conn.select().split("\n");
           boolean fleg = false; 
           for(int i = 0; i < linhas.length; i++) {
               String linha[] = linhas[i].split(";");
               if(TextFieldNomeRefeicao.getText().equals(linha[2])) {
                   fleg = true;
               }
           }
           if(!fleg){
               ret = true;
           } else {
               JOptionPane.showMessageDialog(null, "Refeição com o mesmo nome já cadastrada", "Alerta!", JOptionPane.WARNING_MESSAGE);
           }  
       } else {
          JOptionPane.showMessageDialog(null, "Algum campo não foi preenchido", "Alerta!", JOptionPane.WARNING_MESSAGE);
       }
       return ret; 
   }
   
   public boolean tratamentoModificar() {
       boolean ret = false;
        RadioMenuItem IDSelect = (RadioMenuItem) tgRmiID.getSelectedToggle();
       if(IDSelect != null) {
            if(TextFieldModRefeicao != null || TextFieldModIngredientes != null || TextFieldModValor != null) {
                ret = true;
            } else JOptionPane.showMessageDialog(null, "Algum campo não foi preenchido", "Alerta!", JOptionPane.WARNING_MESSAGE);
       } else JOptionPane.showMessageDialog(null, "ID não selecionado", "Alerta!", JOptionPane.WARNING_MESSAGE);
       
       return ret;
   }
   
   public boolean tratamentoRemover() {
       boolean ret = false;
       RadioMenuItem IDSelect = (RadioMenuItem) tgRmiID.getSelectedToggle();
       if(IDSelect != null) {
            if(TextFieldModRefeicao != null || TextFieldModIngredientes != null || TextFieldModValor != null) {
                ret = true;
            } else JOptionPane.showMessageDialog(null, "Algum campo não foi preenchido", "Alerta!", JOptionPane.WARNING_MESSAGE);
       } else JOptionPane.showMessageDialog(null, "ID não selecionado", "Alerta!", JOptionPane.WARNING_MESSAGE);
       return ret;
   }
}
