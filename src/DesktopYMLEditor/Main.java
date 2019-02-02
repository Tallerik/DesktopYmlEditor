package DesktopYMLEditor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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

        if(field.getText() != "") {
            loadData();
        }

        Button load = (Button) scene.lookup("#loadbtn");

        load.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            loadData();
        });

        Button save = (Button) scene.lookup("#savebtn");
        save.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
          saveData();
        });

    }

    private void saveData() {
        String path = ((TextField) scene.lookup("#filepath")).getText();
        List<String> out = new ArrayList<>();
        ListView view = ((ListView)scene.lookup("#content2"));
        for(Object obj : view.getItems()) {
            if (obj instanceof Text) {
                out.add("#" + ((Text) obj).getText());
            } else if (obj instanceof Label) {
                out.add(((Label) obj).getText());
            } else if (obj instanceof FlowPane) {
                FlowPane pane = (FlowPane) obj;
                for (int i = 0; i < pane.getChildren().size(); i++) {
                    //TODO: Save Panes
                }

            }
        }
        int res = JOptionPane.showConfirmDialog(null, "Save?", "Sure", JOptionPane.YES_NO_OPTION);
        if(res == 1) {
            try {
                FileWriter writer = new FileWriter(path);
                for(String str: out) {
                    writer.write(str);
                }
                writer.close();
            } catch (IOException e) {}
        }
    }

    private void loadData() {
        String path = ((TextField) scene.lookup("#filepath")).getText();
        //VBox vBox = ((VBox)Main.scene.lookup("#content"));
        ListView view = ((ListView)scene.lookup("#content2"));
        for(Object obj:view.getItems()) {
            view.getItems().remove(obj);
        }
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));


            String st;
            String[] split;
            FlowPane pane;
            while ((st = br.readLine()) != null) {
                if(!st.startsWith("#")) {
                    pane = new FlowPane();
                    split = st.split(": ");
                    if (split.length == 1) {
                        view.getItems().add(new Label(split[0]));
                    } else {
                        pane.getChildren().add(new Label(split[0] + ":"));

                        if (split[1].equals("true")) {
                            CheckBox box = new CheckBox();
                            box.setSelected(true);
                            box.setId(split[0]);
                            pane.getChildren().add(box);
                        } else if (split[1].equals("false")) {
                            CheckBox box = new CheckBox();
                            box.setId(split[0]);
                            pane.getChildren().add(box);
                        } else {
                            TextField field = new TextField(split[1]);
                            field.setId(split[0]);
                            pane.getChildren().add(field);
                        }

                        view.getItems().add(pane);
                    }
                } else {
                    Text labe = new Text();
                    labe.setText(st.substring(1));
                    labe.setFill(Color.GREEN);
                    labe.setFont(Font.font(Font.getDefault().getFamily(), FontPosture.ITALIC, labe.getFont().getSize()));
                    view.getItems().add(labe);
                }
            }


        }catch(Exception e){}
    }







    public static void main(String[] args) {
        startArgs = args;
        launch(args);
    }
}
