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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import main.Main;

/**
 * FXML Controller class
 *
 * @author Theus
 */
public class FXMLTelaLoginGerenteController implements Initializable {
    @FXML
    private TextField TextFieldLogin;
    @FXML
    private PasswordField PasswordFieldSenha;
    
    @FXML
    private Label labalAlerta;
    
    private static String login = "GerenteRTR";
    private static String senha = "1234RTR";
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void Login() {
        System.out.println(PasswordFieldSenha.getText() + " - " + TextFieldLogin.getText());
        if(tratamentoLogin()) {
            if(TextFieldLogin.getText().equals(login) && PasswordFieldSenha.getText().equals(senha)) {
                Main m = new Main();
                m.mudaTela("/view/FXMLTelaGerenciamento.fxml");
            } else labalAlerta.setText("Login ou Senha invalida");
        }
        
        
    }
    
    public void Voltar() {
        Main m = new Main();
        m.mudaTela("/view/FXMLTelaEntrada.fxml");
    }
    
    public boolean tratamentoLogin() {
        boolean ret = false; 
        
        if(TextFieldLogin != null && PasswordFieldSenha != null || !TextFieldLogin.getText().equals("") && !PasswordFieldSenha.getText().equals("")) ret = true;
        else JOptionPane.showMessageDialog(null, "Algumas informações não foram digitadas", "Alerta", JOptionPane.WARNING_MESSAGE);
        
        return ret;
    }
    
}
