package textpad;

import com.sun.media.jfxmediaimpl.platform.Platform;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import javax.swing.text.Caret;

public class TextPadUIBase extends AnchorPane {

    protected final BorderPane borderPane;
    protected final MenuBar menuBar;
    protected final Menu menu;
    protected final MenuItem newMenu;
    protected final MenuItem openMenu;
    protected final MenuItem saveMenu;
    protected final MenuItem exitMenu;
    protected final Menu menu0;
    protected final MenuItem cutMenu;
    protected final MenuItem copyMenu;
    protected final MenuItem pasteMenu;
    protected final MenuItem deleteMenu;
    protected final MenuItem selectallMenu;
    protected final MenuItem menuItem8;
    protected final Menu menu1;
    protected final MenuItem aboutMenu;
    protected final TextArea textArea;
    public Stage Stage;
    public String clipBoard;
    public int flag;
    public int savedFlag;

    public TextPadUIBase(Stage primaryStage) {
        Stage = primaryStage;
        clipBoard = "";
        flag = 0;
        savedFlag=0;
        borderPane = new BorderPane();
        menuBar = new MenuBar();
        menu = new Menu();
        newMenu = new MenuItem();
        openMenu = new MenuItem();
        saveMenu = new MenuItem();
        exitMenu = new MenuItem();
        menu0 = new Menu();
        cutMenu = new MenuItem();
        copyMenu = new MenuItem();
        pasteMenu = new MenuItem();
        deleteMenu = new MenuItem();
        selectallMenu = new MenuItem();
        menuItem8 = new MenuItem();
        menu1 = new Menu();
        aboutMenu = new MenuItem();
        textArea = new TextArea();

        setId("AnchorPane");
        setPrefHeight(400.0);
        setPrefWidth(600.0);

        borderPane.setLayoutY(-1.0);
        borderPane.setPrefHeight(400.0);
        borderPane.setPrefWidth(600.0);

        BorderPane.setAlignment(menuBar, javafx.geometry.Pos.CENTER);

        menu.setMnemonicParsing(false);
        menu.setText("File");

        newMenu.setMnemonicParsing(false);
        newMenu.setText("New");
        newMenu.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));

        openMenu.setMnemonicParsing(false);
        openMenu.setText("Open");
        openMenu.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));

        saveMenu.setMnemonicParsing(false);
        saveMenu.setText("Save");
        saveMenu.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));

        exitMenu.setMnemonicParsing(false);
        exitMenu.setText("Exit");
        exitMenu.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));

        menu0.setMnemonicParsing(false);
        menu0.setText("Edit");

        cutMenu.setMnemonicParsing(false);
        cutMenu.setText("Cut");

        copyMenu.setMnemonicParsing(false);
        copyMenu.setText("Copy");

        pasteMenu.setMnemonicParsing(false);
        pasteMenu.setText("Paste");

        deleteMenu.setMnemonicParsing(false);
        deleteMenu.setText("Delete");

        selectallMenu.setMnemonicParsing(false);
        selectallMenu.setText("Select All");

        //menuItem8.setMnemonicParsing(false);
        //menuItem8.setText("Delete");
        menu1.setMnemonicParsing(false);
        menu1.setText("Help");

        aboutMenu.setMnemonicParsing(false);
        aboutMenu.setText("About");
        borderPane.setTop(menuBar);

        BorderPane.setAlignment(textArea, javafx.geometry.Pos.CENTER);
        textArea.setPrefHeight(200.0);
        textArea.setPrefWidth(200.0);
        textArea.setFont(new Font(20.0));
        borderPane.setCenter(textArea);

        menu.getItems().add(newMenu);
        menu.getItems().add(openMenu);
        menu.getItems().add(saveMenu);
        menu.getItems().add(exitMenu);
        menuBar.getMenus().add(menu);
        menu0.getItems().add(cutMenu);
        menu0.getItems().add(copyMenu);
        menu0.getItems().add(pasteMenu);
        menu0.getItems().add(deleteMenu);
        menu0.getItems().add(selectallMenu);
        // menu0.getItems().add(menuItem8);
        menuBar.getMenus().add(menu0);
        menu1.getItems().add(aboutMenu);
        menuBar.getMenus().add(menu1);
        getChildren().add(borderPane);

        //Action events
        //New
        newMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (textArea.getText().length() > 1) {
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setContentText("Do you want to save or discart ?");

                    ButtonType btnYes = new ButtonType("Yes");
                    ButtonType btnNo = new ButtonType("No");
                    ButtonType btnCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
                    alert.getButtonTypes().setAll(btnYes,btnNo,btnCancel);
                    
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == btnYes) {
                        saveFile();
                    } else if(result.get() == btnNo) {
                        textArea.setText("");
                    }
                } else {
                    textArea.setText("");
                }
            }
        });

        //open
        openMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                openFile();

            }
        });

        //save
        saveMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                saveFile();

            }
        });

        //Exit
        exitMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                if (textArea.getText().length() > 1) {
                    if(savedFlag==0){
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setContentText("Do you want to save or discart ?");
                    
                    ButtonType btnYes = new ButtonType("Yes");
                    ButtonType btnNo = new ButtonType("No");
                    ButtonType btnCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
                    alert.getButtonTypes().setAll(btnYes,btnNo,btnCancel);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == btnYes) {
                        saveFile();
                    } else if(result.get() == btnNo) {
                        System.exit(0);
                    }
                        }  
                } else {
                    System.exit(0);
                }

            }
        });

        //About
        aboutMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("About TextPad");
                alert.setHeaderText("TextPad V1.0");
                alert.setContentText("TextPad is a simple text editor");

                alert.showAndWait();

            }
        });

        //Select All
        selectallMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                textArea.selectAll();

            }
        });

        //Copy
        copyMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                clipBoard = textArea.getSelectedText();

            }
        });

        //Cut
        cutMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                clipBoard = textArea.getSelectedText();
                textArea.deleteText(textArea.getSelection());

            }
        });

        //Delete
        deleteMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                textArea.deleteText(textArea.getSelection());

            }
        });
        //Paste
        pasteMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                //get Caret position
                int cPos = textArea.getCaretPosition();
                textArea.insertText(cPos, clipBoard);

            }
        });

    }

    ////////////////////////Editor Functions///////////////////////////////
    //Open File
    public void openFile() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Any Type File", "*.*"));

        File selectedFile = fileChooser.showOpenDialog(Stage);
        
        if(selectedFile != null){
        FileReader fReader = null;
        try {
            fReader = new FileReader(selectedFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TextPadUIBase.class.getName()).log(Level.SEVERE, null, ex);
        }

        int i;
        try {
            while ((i = fReader.read()) != -1) {
                textArea.appendText("" + (char) i);
            }
        } catch (IOException ex) {
            Logger.getLogger(TextPadUIBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            savedFlag=0;
            fReader.close();
        } catch (IOException ex) {
            Logger.getLogger(TextPadUIBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }

    //Save to file
    public void saveFile() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll( new ExtensionFilter("Any Type File", "*.*"));

        File selectedFile = fileChooser.showSaveDialog(Stage);

        if(selectedFile != null){
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(selectedFile));
            writer.write(textArea.getText());
            writer.close();
            savedFlag=1;
        } catch (IOException ex) {
            Logger.getLogger(TextPadUIBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        }

    }

}
