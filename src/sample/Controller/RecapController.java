package Controller;

import Model.Move;
import Model.ReadExcel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import static Model.CoupDeGrue.*;
import static Model.ReadExcel.oneByOneExample;

public class RecapController implements Initializable {
    @FXML
    DatePicker datePicker;
    @FXML
    TextField fileName;
    @FXML
    Button btnDate;
    @FXML
    LineChart cdg;
    @FXML
    ComboBox comboCategory;
    @FXML
    ImageView imcad42;
    @FXML
    PieChart pie;
    @FXML
    DatePicker maintenanceDate;
    @FXML
    TextField x;
    @FXML
    TextField y;
    @FXML
    TextField z;
    @FXML
    TextField xM;
    @FXML
    TextField yM;
    @FXML
    TextField zM;
    @FXML
    TextField startDay;
    @FXML
    TextField endDay;
    Date startDate;
    ArrayList<Move> goodMoves = new ArrayList<Move>();



    @Override
    public void initialize(URL location, ResourceBundle resources){

        System.out.println("gjgj");

        comboCategory.getItems().addAll("Coup de Grue");

        try {
            piechartFunction();
        }
        catch (ParseException e){e.printStackTrace();}



    }


    public void chooseFile(){
        FileChooser fileChooser = new FileChooser();

        Stage stage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(stage);
        fileName.setText(selectedFile.getName());

    }
    public void actionDate() throws ParseException{
        ArrayList<Move> moves = oneByOneExample("data3.csv");
        LocalDate localDate = datePicker.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        System.out.println("date " + date);

        String[] dateFormat = (localDate+"").split("-");
        fileName.setText(dateFormat[2]+"/"+dateFormat[1]+"/"+dateFormat[0]);

        startDate = date;
        for (int i = 0; i<=moves.size()-3; i++){
            if(moves.get(i).getDate().getTime()>= date.getTime()) {
                goodMoves.add(moves.get(i));
            }
        }
        System.out.println(goodMoves.size());
        x.setText(maximumDistanceX(moves, 0, moves.size()-3 )+"");
        y.setText(maximumDistanceY(moves, 0, moves.size()-3)+ "");
        z.setText(maximumDistanceZ(moves, 0, moves.size()-3)+ "");

        startDay.setText(getHour(moves.get(0).getDate().getTime()));
        endDay.setText(endOfDay(moves).get(0));
        piechartFunction();

    }




    public void btnComboCategory(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/DataGrue.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Coup de grue");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }


        System.out.println("rrrrr");

    }

    public void piechartFunction() throws ParseException{
        ArrayList<Move> moves = goodMoves;

        double prct = Double.parseDouble(totalDurationCDG(moves,0,moves.size()-3).get(2));
        PieChart.Data s0 = new PieChart.Data("CDG " + prct +" %" , prct);
        PieChart.Data s1 = new PieChart.Data("Autre " + (100-prct) + " %", 100-prct);
        pie.setData(FXCollections.observableArrayList(s0, s1));
        pie.setTitle("repartition des utilisations de la grue");
    }

    public void btnMaintenance() throws ParseException {
        ArrayList<Move> moves = goodMoves;
        System.out.println("aaaaaa");
        //printDate.setText("fhgh");
        LocalDate localDate = maintenanceDate.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        System.out.println(localDate);
        int start = -1;

        for(int i =0; i<moves.size(); i++){
            if(date.compareTo(moves.get(i).getDate())<0){
                start =i;
                break;
            }
        }

        if(start != -1) {
            xM.setText(maximumDistanceX(moves, start, moves.size() - 3) + "");
            yM.setText(maximumDistanceY(moves, start, moves.size() - 3) + "");
            zM.setText(maximumDistanceZ(moves, start, moves.size() - 3) + "");
        }
        else{
            xM.setText("0");
            yM.setText("0");
            zM.setText("0");
        }


    }
}