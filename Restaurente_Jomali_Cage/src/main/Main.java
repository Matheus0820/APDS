/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Theus
 */
public class Main extends Application {
    private static Stage stag;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        stag = primaryStage;
       
        
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/FXMLTelaEntrada.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Jomali Cage");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMinWidth(600);
        primaryStage.setMaxWidth(600);
        primaryStage.setMinHeight(550);
        primaryStage.setMaxHeight(550);
        
        primaryStage.show();
    }
    
    public void mudaTela(String endereco) {
        try {
            Parent pane = FXMLLoader.load(getClass().getResource(endereco));
            stag.getScene().setRoot(pane);
        } catch(IOException ex) {
            
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
