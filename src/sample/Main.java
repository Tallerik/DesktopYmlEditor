package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;


public class Main extends Application {

    static String[] startArgs = null;
    static Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("YAML Reader");
        scene = new Scene(root, 300, 275);
        primaryStage.setScene(scene);
        primaryStage.show();

        initSystem(scene);

    }

    private void initSystem(Scene scene) {

        TextField field = (TextField) scene.lookup("#filepath");

        field.setText(startArgs[0]);

        Button load = (Button) scene.lookup("#loadbtn");
        load.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            loadData();
        });



    }

    private void loadData() {
        String path = ((TextField) scene.lookup("#filepath")).getText();
        VBox vBox = ((VBox)Main.scene.lookup("#content"));
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));


            String st;
            String[] split;
            while ((st = br.readLine()) != null) {
                /*split = st.split(": ");
                vBox.getChildren().add(new TextField(split[0]));
                vBox.getChildren().add(new TextField(split[1]));*/
                vBox.getChildren().add(new TextField(st));
            }


        }catch(Exception e){}
    }

    public static void main(String[] args) {
        startArgs = args;
        launch(args);
    }
}
