/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textpad;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author hossam
 */
public class TextPad extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        TextPadUIBase root = new TextPadUIBase(primaryStage);
//        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 400, 350);
        
        primaryStage.setTitle("TextPad");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
