/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;




import dao.ConnectDB_Pedido;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javax.swing.JOptionPane;
import main.Main;
import model.Pedido;
/**
 *
 * @author Theus
 */
public class FXMLTelaCozinheiroController implements Initializable{
    
    private static String ListaDB[][];  
    private List<Pedido> ListaPedidos = new ArrayList();
    private ObservableList<Pedido> ObsListaPedidos; 
    Pedido novoPedido;
    ConnectDB_Pedido conn = new ConnectDB_Pedido();
    //Tabela
    @FXML
    private TableView<Pedido> TableViewCozinheiro;
    @FXML
    private TableColumn<Pedido, Integer> TabelaColumnID; 
    @FXML
    private TableColumn<Pedido, Integer> TabelaColumnMesa;
    @FXML
    private TableColumn<Pedido, String> TabelaColumnPedido;
    @FXML
    private TableColumn<Pedido, String> TabelaColumnEstado; 
    
    @FXML 
    private Label labelAlerta; 
    
    // RadiosButton dos Estados
    @FXML 
    private RadioButton RadioButtonEmPreparo;
    @FXML         
    private RadioButton RadioButtonPronto;
    @FXML 
    private RadioButton RadioButtonRecusado;
    
    //Button que Altera o Estado
    @FXML
    private Button ButtonConcluir;
    
    
    //Menu dos IDs 
    @FXML
    private SplitMenuButton SplitMenuButtonIDPedidos; 
    
    @FXML
    private Button ButtonVerPedidos;
    
    @FXML
    private TabPane TabPanePedidos;
    
    public static ToggleGroup tgIDs = new ToggleGroup();
    public static ToggleGroup tgEstados = new ToggleGroup();
   
   @Override
    public void initialize(URL location, ResourceBundle resources) {
          RadioButtonEmPreparo.setToggleGroup(tgEstados);
          RadioButtonPronto.setToggleGroup(tgEstados);
          RadioButtonRecusado.setToggleGroup(tgEstados);
        
        try {
            recarregar();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            System.out.println( "Erro: " + ex.getMessage());
        }
       
    }
    
    public void alterarEstado() throws SQLException {  
        tratementoAlterarEstado();
    }
    
    public void recarregar() throws SQLException {
       //Limpando a TabPane, ListPedidos e o SplitMenuButtonIDPedidos
       SplitMenuButtonIDPedidos.getItems().clear();
       TabPanePedidos.getTabs().clear();
       if(ListaPedidos != null) ListaPedidos.clear();
       //Pegando dados 
       conn.Connect();
       if(!conn.select().equals("")) {
            String linhas[] = conn.select().split("\n"); 
            String linha[];
            
            for(int i = 0; i < linhas.length; i++) {
                linha = linhas[i].split(";");
            if(!linha[2].equals(RadioButtonPronto.getText()) && !linha[2].equals(RadioButtonRecusado.getText())) {
                //Criando e configurando a ListView
                ListView lvPedido = new ListView(); 
                lvPedido.getItems().add("");
                lvPedido.setFixedCellSize(20);
                lvPedido.setMinWidth(1200);
                //Instanciando as Labels e a VBox
                Label IDped = new Label("ID do Pedido: " + linha[0]);
                Label NumMesa = new Label("Número da Mesa: " + linha[1]);
                Label EstPed = new Label("Estado do Pedido: " + linha[2]);
                VBox v = new VBox(IDped, NumMesa, EstPed);
                v.setMinWidth(180);
                //Colocando dados na ListView
                String Vpeds[] = linha[3].split("-");
                for(int j = 0; j < Vpeds.length; j++) {
                    String Iped[] = Vpeds[j].split(",");
                    String Ref = "ID: " + Iped[0] + " - " + Iped[1] + " - Qaunt.: " + Iped[2];
                    lvPedido.getItems().add(Ref);
                }
                //Instanciando e configurando a HBox
                HBox h = new HBox(v, lvPedido);
                h.setSpacing(10);
                h.setAlignment(Pos.CENTER);
                //Instanciando a Tab
                Tab TabPedido = new Tab("Pedido " + linha[0]);
                TabPedido.setContent(h);
                //Coloando a TabPedido no TabPane
                TabPanePedidos.getTabs().addAll(TabPedido);
                //Carregando os dados no Array de Pedidos e adcionando os RadioMenuItem   
                Pedido p1 = new Pedido(Integer.parseInt(linha[0]), Integer.parseInt(linha[1]), linha[2], linha[3]);
                ListaPedidos.add(p1);
                
                RadioMenuItem RadioMenuItemIDPed = new RadioMenuItem();
                RadioMenuItemIDPed.setId("Pedido" + p1.getId());
                RadioMenuItemIDPed.setText("" + p1.getId()); 
                RadioMenuItemIDPed.setToggleGroup(tgIDs);
                //Colocanco os RadioMenuItem no SplitMenuButton
                SplitMenuButtonIDPedidos.getItems().addAll(RadioMenuItemIDPed); 
            }
            }
       } else JOptionPane.showMessageDialog(null, "Ainda não existe nenhum pedido", "Aviso!", JOptionPane.WARNING_MESSAGE);
    }
    
    public void tratementoAlterarEstado() throws SQLException {
        RadioButton RadioMenuItemEstadoSelect = (RadioButton) tgEstados.getSelectedToggle();
        RadioMenuItem RadioMenuItemIDSelect = (RadioMenuItem) tgIDs.getSelectedToggle();
        if(RadioMenuItemIDSelect != null ) { //Checando se o RadioMenuItemIDSelect foi selecionada 
            labelAlerta.setText(""); 
            if(RadioMenuItemEstadoSelect != null) { //Checando se o RadioMenuItemEstadoSelect foi selecionado
                boolean fleg = true;
                int id = Integer.parseInt(RadioMenuItemIDSelect.getText());
                String estado = RadioMenuItemEstadoSelect.getText();
                
                String linhas[] = conn.select().split("\n");
                
                for(int i = 0; i < linhas.length; i++) {
                    String linha[] = linhas[i].split(";");
                    if(Integer.parseInt(linha[0]) == id) { 
                        if(linha[2].equals(estado)) { //Checando se o Estado do id selecionado é igual ao que foi escolhido para alterar
                            fleg = false;
                            System.out.println(ListaPedidos.get(i).getEstado() + " - " + estado);
                        }
                    }
                }
                if(fleg) { //Se tudo ocorrer o metodo eventoAlterar é chamada, mandando o id do pedido e o estado
                    try {
                        eventoAlterar(id, estado);
                    } catch(SQLException ex) { }
                } else {
                 labelAlerta.setText("!Pedido já possui o estado escolhido!");
                }
            } else {
                labelAlerta.setText("!Estado do Pedido não selecionado!");
            }
        } else {
           labelAlerta.setText("!ID do Pedido não selecionado!");
        }
    }
    
    public void eventoAlterar(int id, String estado) throws SQLException {
        conn.Connect();
        conn.update(id, estado); //Altera do estado por meio do ID
        labelAlerta.setText("Estado Alterado!"); 
        recarregar();    
    }
    
    public void Voltar() {
        Main m = new Main();
        m.mudaTela("/view/FXMLTelaEntrada.fxml");
    }
}
