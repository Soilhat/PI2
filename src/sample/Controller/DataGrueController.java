package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;


public class DataGrueController implements Initializable {
    @FXML
    DatePicker datePicker;
    @FXML
    TextField printDate;
    @FXML
    Button btnDate;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        //datePicker.setValue(LocalDate.now());
        //DatePicker d = new DatePicker();
        //printDate.setText("fhgh");
        /*LocalDate localDate = datePicker.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        System.out.println(localDate + "\n" + instant + "\n" + date);
        printDate.setText(localDate + "\n" + instant + "\n" + date);*/

        /*btnDate.setOnAction(action -> {
            LocalDate localDate = datePicker.getValue();
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            Date date = Date.from(instant);
            System.out.println(localDate + "\n" + instant + "\n" + date);
            printDate.setText(localDate + "\n" + instant + "\n" + date);

        });*/
            System.out.println("gjgj");
    }
    public void actionDate(){
        System.out.println("aaaaaa");
        //printDate.setText("fhgh");
        LocalDate localDate = datePicker.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        System.out.println(localDate);// + "\n" + instant + "\n" + date);
        //printDate.setText(localDate +"");// + "\n" + instant + "\n" + date);
        /*FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");*/
        FileChooser fileChooser = new FileChooser();

        /*Stage stage = new Stage();
        Button button = new Button("Select File");
        button.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(stage);
        });
        VBox vBox = new VBox(button);
        Scene scene = new Scene(vBox, 960, 600);

        stage.setScene(scene);
        stage.show();*/

        /*stage.setTitle("Choose a file");
        fileChooser.showOpenDialog(stage);
        stage.setScene(new Scene(root, 300, 275));
        stage.show();*/

        String[] dateFormat = (localDate+"").split("-");
        printDate.setText(dateFormat[2]+"/"+dateFormat[1]+"/"+dateFormat[0]);

    }

    public void chooseFile(){
        FileChooser fileChooser = new FileChooser();

        Stage stage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(stage);
        /*VBox vBox = new VBox(button);
        Scene scene = new Scene(vBox, 960, 600);


        stage.setScene(scene);
        stage.show();*/

        printDate.setText(selectedFile.getAbsolutePath());

    }
}
