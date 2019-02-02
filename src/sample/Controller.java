package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private void Change2(MouseEvent event) throws IOException {
        for (int i=0; i<10; i++){
            ((VBox)Main.scene.lookup("#content")).getChildren().add(new Button("Lol" + i));
        }
    }


    @FXML
    private void Change(KeyEvent event) throws IOException {
        for (int i=0; i<10; i++){
            ((VBox)Main.scene.lookup("#content")).getChildren().add(new Button("Lol" + i));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
