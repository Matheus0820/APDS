/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import main.Main;

/**
 *
 * @author Theus
 */
public class FXMLTelaEntradaController implements Initializable {
    @FXML
    private Button ButtonGerenciamento;
    @FXML
    private Button ButtonCheckin;
    
    Main m = new Main();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    public void TelaLogin() {
        String tla = "/view/FXMLTelaLoginGerente.fxml";
        m.mudaTela(tla);
    }
    
    public void TelaRecepcionista() {
        String tla = "/view/FXMLTelaMesa.fxml";
        m.mudaTela(tla);
    }
    
    public void TelaGarcom() {
        String tla = "/view/FXMLTelaPedidos.fxml";
        m.mudaTela(tla);
    }
    public void TelaCozinheiro(){
        String tla = "/view/FXMLTelaCozinheiro.fxml";
        m.mudaTela(tla);
    }
    public void TalaConfiguracao() {
        String tla = "/view/FXMLTelaConfiguracao.fxml";
        m.mudaTela(tla);
    }

    
}
