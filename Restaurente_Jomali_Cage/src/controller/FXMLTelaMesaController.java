/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ConnectDB_Mesas;
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
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import main.Main;
import model.Mesa;

/**
 *
 * @author Theus
 */
public class FXMLTelaMesaController implements Initializable {
    ConnectDB_Mesas conn = new ConnectDB_Mesas();
    List<Mesa> listMesas = new ArrayList<Mesa>();
    private ObservableList<Mesa> ObsListaMesas; 
    ToggleGroup tgNumEstado = new ToggleGroup();
    ToggleGroup tgNumRemover = new ToggleGroup();
    public static int mesasdisponiveis; 
    
    @FXML
    private TableView<Mesa> TableViewMesas; 
    @FXML
    private TableColumn<Mesa, Integer> TableColumnNumero;
    @FXML
    private TableColumn<Mesa, Integer> TableColumnLugares;
    @FXML
    private  TableColumn<Mesa, String> TableColumnEstado;
    
    @FXML
    private Button ButtonAdicionar;
    @FXML
    private Button ButtonAlterarEstado;
    @FXML
    private Button ButtonRemover;
    
    @FXML
    private SplitMenuButton SplitMenuButtonNumEstado;
    @FXML
    private SplitMenuButton SplitMenuButtonNumRemover;
    
    @FXML
    private TextField TextFieldNumMesa;
    @FXML
    private TextField TextFieldLugares;
    
    @FXML
    private Label labelMesasDisponiveis;
    @FXML
    private Label labelMesasOcupadas;
    @FXML
    private Label labelTotalDeMesas;
    @FXML
    private Label labelAlerta;
    
    @FXML
    private ProgressIndicator ProgressIndicatorMesasOcupadas;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       TableColumnNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
       TableColumnLugares.setCellValueFactory(new PropertyValueFactory<>("lugares"));
       TableColumnEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
       
       conn.Connect();
       try {
        if(conn.select() != "") recarregar();
       } catch(SQLException ex) {}
       
    } 
    
    public void recarregar() throws SQLException {
        //Limpando os elementos e listas
        mesasdisponiveis = 0;
        SplitMenuButtonNumEstado.getItems().clear();
        SplitMenuButtonNumRemover.getItems().clear();
        TableViewMesas.getItems();
        if(ObsListaMesas != null ) ObsListaMesas.clear();
        if(listMesas != null) listMesas.clear();
        
        String linhas[] = conn.select().split("\n");
        for(int i = 0; i < linhas.length; i++) {
            Mesa mesa1 = new Mesa();
            String linha[] = linhas[i].split(";"); 
            
            if(linha[2].equals("Disponível")) mesasdisponiveis++;
         
            
            mesa1.setNumero(Integer.parseInt(linha[0]));
            mesa1.setLugares(Integer.parseInt(linha[1]));
            mesa1.setEstado(linha[2]);
            listMesas.add(mesa1);  

            RadioMenuItem RadioMenuItemNumEtd = new RadioMenuItem();
            RadioMenuItem RadioMenuItemNumRmv = new RadioMenuItem();
            
            RadioMenuItemNumEtd.setId("Mesa" + mesa1.getNumero());
            RadioMenuItemNumRmv.setId("Mesa" + mesa1.getNumero());
            RadioMenuItemNumEtd.setText("" + mesa1.getNumero());
            RadioMenuItemNumRmv.setText("" + mesa1.getNumero());
            RadioMenuItemNumEtd.setToggleGroup(tgNumEstado);
            RadioMenuItemNumRmv.setToggleGroup(tgNumRemover);
            SplitMenuButtonNumEstado.getItems().addAll(RadioMenuItemNumEtd);
            SplitMenuButtonNumRemover.getItems().addAll(RadioMenuItemNumRmv);
                
            }
        ObsListaMesas = FXCollections.observableArrayList(listMesas);
        TableViewMesas.setItems(ObsListaMesas);
        
        labelMesasDisponiveis.setText(mesasdisponiveis + "");
        labelMesasOcupadas.setText((linhas.length - mesasdisponiveis) + "");
        labelTotalDeMesas.setText(linhas.length + "");
        //labelAlerta.setText("");
    }
    
    public void adicionarNovaMesa() throws SQLException {
        if(tratamendoAdicionar()) {
            Mesa mesa1  = new Mesa();
            mesa1.setNumero(Integer.parseInt(TextFieldNumMesa.getText()));
            mesa1.setLugares(Integer.parseInt(TextFieldLugares.getText()));
            mesa1.setEstado("Disponível");
            
            listMesas.clear();
            listMesas.add(mesa1);
            conn.Connect();
            conn.insert(mesa1.getNumero(), mesa1.getLugares(), mesa1.getEstado());
            
            TextFieldNumMesa.setText("");
            TextFieldLugares.setText("");
            labelAlerta.setText("Mesa " + mesa1.getNumero() + " Foi adicionada");
            recarregar();
        }
    }

    public void alterarEstadoMesa() throws SQLException {
        
        if(tratamentoAlterarEstado()) {
            RadioMenuItem RadioMenuItemSelectNum = (RadioMenuItem) tgNumEstado.getSelectedToggle();
            String estadoMesa = "Ocupada";
            int num = Integer.parseInt(RadioMenuItemSelectNum.getText());
            
            conn.Connect();
            conn.alter(num, estadoMesa);
            
            labelAlerta.setText("Mesa " + num + " Agora está Ocupada");
            recarregar();
        }   
    }

    public void removerMesa() throws SQLException {
        if(tratementoRemover()) {
            RadioMenuItem RadioMenuItemSelectNum = (RadioMenuItem) tgNumRemover.getSelectedToggle();
            int num = Integer.parseInt(RadioMenuItemSelectNum.getText());
            
            conn.Connect();
            conn.delete(num);
            
            labelAlerta.setText("Mesa " + num + " Foi removida");
            recarregar();
        }
        
    }
    
    
    public boolean tratamendoAdicionar() throws SQLException {
       boolean ret = false;
       if(TextFieldNumMesa.getText() != null && TextFieldLugares != null && Integer.parseInt(TextFieldNumMesa.getText()) != 0 && Integer.parseInt(TextFieldLugares.getText()) != 0) {
           String linhas[] = conn.select().split("\n");
           boolean fleg = false; 
           for(int i = 0; i < linhas.length; i++) {
               String linha[] = linhas[i].split(";");
               if(Integer.parseInt(TextFieldNumMesa.getText()) == Integer.parseInt(linha[0]) ) {
                  
                   fleg = true;
               }
           }
           if(!fleg){
               ret = true;
           } else {
               JOptionPane.showMessageDialog(null, "Mesa com o mesmo número já cadastrada", "Erro!", JOptionPane.WARNING_MESSAGE);
           }  
       } else {
          JOptionPane.showMessageDialog(null, "Algum campo não foi preenchido", "Erro!", JOptionPane.WARNING_MESSAGE);
       }
       return ret; 
   }
    public boolean tratamentoAlterarEstado() throws SQLException {
        RadioMenuItem RadioMenuItemSelectNum = (RadioMenuItem) tgNumEstado.getSelectedToggle();
        boolean ret = false; 
        if(RadioMenuItemSelectNum != null) {
            conn.Connect();
            String linhas[] = conn.select().split("\n"); 
            for(int i = 0; i < linhas.length; i++) {
                String linha[] = linhas[i].split(";");
                if(Integer.parseInt(linha[0]) == Integer.parseInt(RadioMenuItemSelectNum.getText()) && linha[2].equals("Disponível")) { 
                    ret = true;
                } else if(Integer.parseInt(linha[0]) == Integer.parseInt(RadioMenuItemSelectNum.getText())) {
                    JOptionPane.showMessageDialog(null, "Mesa Ocupada!", "Alerta", JOptionPane.WARNING_MESSAGE);
                }
            }
            
        } else JOptionPane.showMessageDialog(null, "Número da mesa não selecionado!", "Alerta", JOptionPane.WARNING_MESSAGE);
        
        
        
        
        return ret; 
    }
    public boolean tratementoRemover() {
        RadioMenuItem RadioMenuItemSelectNum = (RadioMenuItem) tgNumRemover.getSelectedToggle();
        boolean ret = false; 
        if(RadioMenuItemSelectNum != null) { 
            ret = true;
        } else JOptionPane.showMessageDialog(null, "Número da mesa não selecionado!", "Alerta", JOptionPane.WARNING_MESSAGE);
        return ret; 
    }
    
    public void TelaAnterior() {
        Main m = new Main();
        String tla = "/view/FXMLTelaEntrada.fxml";
        m.mudaTela(tla);
    }
    
    
    

}
