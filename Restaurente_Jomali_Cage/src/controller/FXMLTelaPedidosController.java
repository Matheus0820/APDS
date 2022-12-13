package controller;

import dao.ConnectDB_Cardapio;
import dao.ConnectDB_Mesas;
import dao.ConnectDB_Pedido;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import main.Main;
import model.Cardapio;


public class FXMLTelaPedidosController implements Initializable {
    private ObservableList<Cardapio> ObsListaCardapio;
    List<Cardapio> listCardapio = new ArrayList<Cardapio>();
    List<Cardapio> listPedidos = new ArrayList<Cardapio>();
    ConnectDB_Cardapio connCdp = new ConnectDB_Cardapio();
    ConnectDB_Mesas connMs = new ConnectDB_Mesas();
    ConnectDB_Pedido connPed = new ConnectDB_Pedido();
    private static String lpedidos = "";
    private static float valort = 0;
    
    @FXML
    private TableView<Cardapio> TableViewCardapio;
    @FXML
    private TableColumn<Cardapio, Integer> TableColumnID;
    @FXML
    private TableColumn<Cardapio, String> TableColumnRefeicoes;
    @FXML
    private TableColumn<Cardapio, Float> TableColumnValor;
    
    @FXML
    private ListView ListViewPedido;
    
    @FXML
    private SplitMenuButton SplitMenuButtonIDRef;
    @FXML
    private SplitMenuButton SplitMenuButtonIDRefRmv;
    @FXML
    private SplitMenuButton SplitMenuButtonNumMesa;
    
    @FXML
    private Spinner<Integer> SpinnerQuantidade;
    
    @FXML
    private Label labelValor;
    @FXML
    private Label labelAlerta;
    
    ToggleGroup tgIDRefAd = new ToggleGroup();
    ToggleGroup tgIDRefRmv = new ToggleGroup();
    ToggleGroup tgNumMesa = new ToggleGroup();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       TableColumnID.setCellValueFactory(new PropertyValueFactory<>("id"));
       TableColumnRefeicoes.setCellValueFactory(new PropertyValueFactory<>("refeicao"));
       TableColumnValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
       
       SpinnerQuantidade.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10));
       
       try { recarregar(); } 
       catch (SQLException ex) { }
    }
    
    public void recarregar() throws SQLException {
        connCdp.Connect();
        connMs.Connect();
        String linhasCdp[] = connCdp.select().split("\n");
        String linhasMs[] = connMs.select().split("\n");
        for(int i = 0; i < linhasCdp.length; i++) {
            Cardapio it1 = new Cardapio();
            String linha[] = linhasCdp[i].split(";"); 
                
            it1.setId(Integer.parseInt(linha[0]));
            it1.setRefeicao(linha[2]);
            it1.setIngredientes(linha[3]);
            it1.setValor(Float.parseFloat(linha[4]));
            listCardapio.add(it1);  
                
            RadioMenuItem RadioMenuItemIDRefAd = new RadioMenuItem();
            RadioMenuItem RadioMenuItemIDRefRmv = new RadioMenuItem();
            
            RadioMenuItemIDRefAd.setId("Refeicao" + it1.getId());
            RadioMenuItemIDRefRmv.setId("Refeicao" + it1.getId());
            RadioMenuItemIDRefAd.setText("" + it1.getId());
            RadioMenuItemIDRefRmv.setText("" + it1.getId());
            RadioMenuItemIDRefAd.setToggleGroup(tgIDRefAd);
            RadioMenuItemIDRefRmv.setToggleGroup(tgIDRefRmv);
            SplitMenuButtonIDRef.getItems().addAll(RadioMenuItemIDRefAd);
            SplitMenuButtonIDRefRmv.getItems().addAll(RadioMenuItemIDRefRmv);
                
            }
        for(int i = 0; i < linhasMs.length; i++) {
            String linha[] = linhasMs[i].split(";"); 
            RadioMenuItem RadioMenuItemNumMesa = new RadioMenuItem();
            if(linha[2].equals("Ocupada")) {
                RadioMenuItemNumMesa.setId("Mesa" + linha[0]);
                RadioMenuItemNumMesa.setText(linha[0] + "");
                RadioMenuItemNumMesa.setToggleGroup(tgNumMesa);
                SplitMenuButtonNumMesa.getItems().addAll(RadioMenuItemNumMesa);
            }
        }
            ObsListaCardapio = FXCollections.observableArrayList(listCardapio);
            TableViewCardapio.setItems(ObsListaCardapio);        
    }
    
    public void adicionarRefeicao() throws SQLException {
        if(tratamentoAdicionar()) {
            RadioMenuItem IDselect = (RadioMenuItem) tgIDRefAd.getSelectedToggle();
            connCdp.Connect();
            String linhas[] = connCdp.select().split("\n");
               
            for(int i = 0; i < linhas.length; i++) {
                String linha[] = linhas[i].split(";");
                if(Integer.parseInt(IDselect.getText()) == Integer.parseInt(linha[0])) {
                    int quant = SpinnerQuantidade.getValue();
                    valort += quant * Float.parseFloat(linha[4]);
                        
                    String Ref = "ID: " + linha[0] + " - " + linha[2] + " - Qaunt.: " + quant;
                    ListViewPedido.getItems().add(Ref);
                         
                    lpedidos += linha[0] + "," + linha[2] + "," + quant + "-"; 
                    labelValor.setText("Valor do Pedido: R$" + valort);
                    labelAlerta.setText("Refeição adicionada no Pedido");
                } 
            }  
        }
    }
    
    public void removerRefeicao() throws SQLException {
        if(tratamentoRemover()) {
            RadioMenuItem IDselect = (RadioMenuItem) tgIDRefRmv.getSelectedToggle();
            connCdp.Connect();
            String linhasDB[] = connCdp.select().split("\n");
            String linhasLP[] = lpedidos.split("-");
            String pedidormv = "";
            float valorp = 0;
            lpedidos = "";
            
            for(int i = 0; i < linhasDB.length; i++){
                String linhaDB[] = linhasDB[i].split(";");
                if(Integer.parseInt(linhaDB[0]) == Integer.parseInt(IDselect.getText())) {
                    pedidormv = linhaDB[2];
                    System.out.println(Float.parseFloat(linhaDB[4]));
                    valorp = Float.parseFloat(linhaDB[4]);
                    //break;
                }
            }
            
            ListViewPedido.getItems().clear();
            for(int i = 0; i < linhasLP.length; i++) {
                String linha1[] = linhasLP[i].split(",");
                if(linha1[1].equals(pedidormv)) {
                    valort = valort - (valorp * Float.parseFloat(linha1[2]));
                }
                else {
                    lpedidos += linha1[0] + "," + linha1[1] + "," + linha1[2] + "-";
                    ListViewPedido.getItems().add("ID: " + linha1[0] + " - " + linha1[1] + " - Qaunt.: " + linha1[2]);
                }
            }
            labelValor.setText("Valor do Pedido: R$" + valort);
            labelAlerta.setText("Refeição removida");
        }
    }

    public void fazerPedido() throws SQLException {
        if(tratamentoFazerPedido()) {
            RadioMenuItem NumMesaSelect = (RadioMenuItem) tgNumMesa.getSelectedToggle();
            int numMesa = Integer.parseInt(NumMesaSelect.getText());
            int id; 
            connPed.Connect();
            if(!connPed.select().equals("")) {
                String linhas[] = connPed.select().split("\n");
                String linhaf[] = linhas[linhas.length - 1].split(";");
                id = Integer.parseInt(linhaf[0]) +1; 
            } else id = 1;
                connPed.insert(id, numMesa, lpedidos);
                
                ListViewPedido.getItems().clear();
                lpedidos = ""; 
                labelValor.setText("Valor do Pedido: R$" + 0);
                labelAlerta.setText("Pedido Feito com Seucesso!");
        }
    }
    
    public void cancelarPedido() {
        if(tratamentoCancelarPedido()) {
            ListViewPedido.getItems().clear();
            lpedidos = ""; 
            labelValor.setText("Valor do Pedido: R$" + 0);
            labelAlerta.setText("Pedido Cancelado!");
        }
    }
     
    public void Voltar() {
        Main m = new Main();
        m.mudaTela("/view/FXMLTelaEntrada.fxml");
    }
    
    public boolean tratamentoAdicionar() {
        boolean ret = false; 
        RadioMenuItem IDselect = (RadioMenuItem) tgIDRefAd.getSelectedToggle();
        if(IDselect != null) ret = true;
            else JOptionPane.showMessageDialog(null, "Nenhum ID foi selecionado!", "Alerta!", JOptionPane.WARNING_MESSAGE); 
        return ret;
    }
    
    public boolean tratamentoRemover() {
        boolean ret = false; 
        RadioMenuItem IDselect = (RadioMenuItem) tgIDRefRmv.getSelectedToggle();
        if(IDselect != null) {
            System.out.println(IDselect.getText());
            String linhas[] = lpedidos.split("-"); 
            for(int i = 0; i < linhas.length; i++) {
                String linha[] = linhas[i].split(",");
                if(Integer.parseInt(linha[0]) == Integer.parseInt(IDselect.getText())) ret = true;
            }
            if(!ret) JOptionPane.showMessageDialog(null, "Refeição selecionada não faz parte da lista do Pedido", "Alerta!", JOptionPane.WARNING_MESSAGE);
        }
            else JOptionPane.showMessageDialog(null, "Nenhum ID foi selecionado!", "Alerta!", JOptionPane.WARNING_MESSAGE); 
        return ret;
    }
    
    public boolean tratamentoFazerPedido() {
        boolean ret = false; 
        RadioMenuItem NumMesaSelect = (RadioMenuItem) tgNumMesa.getSelectedToggle();
        if(NumMesaSelect != null) {
            if(lpedidos != null) ret = true;
            else JOptionPane.showMessageDialog(null, "Nenhuma Refeição adicionada na Lista do Pedido", "Alerta!", JOptionPane.WARNING_MESSAGE);
        } else JOptionPane.showMessageDialog(null, "Número da mesa não selecionado", "Alerta!", JOptionPane.WARNING_MESSAGE);
        return ret;
    }
    
    public boolean tratamentoCancelarPedido() {
        boolean ret = false;
        //Se Sim -> 0, Se Não -> 1
        int conf = JOptionPane.showConfirmDialog(null, "Deseja mesmo Cancelar esse pedido?" , "Confirmação de Cancelamento", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if(conf == 0) ret = true;
        return ret;
    }   
}