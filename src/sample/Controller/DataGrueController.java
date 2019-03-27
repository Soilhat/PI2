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
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import static Model.CoupDeGrue.*;
import static Model.ReadExcel.oneByOneExample;


public class DataGrueController  implements Initializable {
    @FXML
    DatePicker datePicker;
    @FXML
    TextField fileName;
    @FXML
    Button btnDate;
    @FXML
    LineChart cdg;
    @FXML
    ComboBox<String> comboHour;
    @FXML
    ImageView imcad42;
    @FXML
    TextField nbTotalCdg;
    @FXML
    ComboBox comboCategory;
    @FXML
    TextField x;
    @FXML
    TextField y;
    @FXML
    TextField z;
    @FXML
    LineChart yFx;
    @FXML
    TextField tpsTotalCdg;
    @FXML
    TextField tpsMoyenCdg;

    Date startDate;
    ArrayList<Move> goodMoves = new ArrayList<Move>();

    @Override
    public void initialize(URL location, ResourceBundle resources){
        System.out.println("gjgj");

        comboCategory.getItems().addAll("recap");

        comboHour.getItems().addAll("1. 8h - 9h", "2. 10h - 11h", "3. 12h - 13h","4. 14h - 15h", "5. 16h - 17h", "6. 18h - 19h");

        cdg.setTitle("deplacement vertical de la grue en fonction du temps");
        cdg.getXAxis().setLabel("temps");
        cdg.getYAxis().setLabel("hauteur en dm");

        yFx.setTitle("deplacement de Y en fonction de X");
        yFx.getXAxis().setLabel("X en dm");
        yFx.getYAxis().setLabel("Y en dm");


    }
    public void chooseFile(){
        FileChooser fileChooser = new FileChooser();

        Stage stage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(stage);
        fileName.setText(selectedFile.getName());

    }
    public void actionDate() throws  ParseException{
        LocalDate localDate = datePicker.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        System.out.println(localDate);

        ArrayList<Move> moves = oneByOneExample(fileName.getText());

        String[] dateFormat = (localDate+"").split("-");
        startDate = date;
        for (int i = 0; i<=moves.size()-3; i++){
            System.out.println("ccccc");
            if(moves.get(i).getDate().getTime()>= date.getTime()) {
                System.out.println("hhhhh");
                goodMoves.add(moves.get(i));
            }
        }

    }


    public void plotCDG(int start, int end) throws ParseException {

        XYChart.Series set1 = new XYChart.Series<>();
        ObservableList<XYChart.Data> data = FXCollections.observableArrayList();

        //ArrayList<Move> moves = oneByOneExample("data3.csv");
        ArrayList<Move> moves = goodMoves;
        cdg.setCreateSymbols(false);
        long duree;

        for(int i=start; i<=end; i++){
            duree = moves.get(i).getDate().getTime();
            Timestamp t = new Timestamp(duree);
            String[] date = t.toString().split(" ");
            if(end - start <= 10000 && i % 50 ==0)
                data.add(new XYChart.Data(date[1],moves.get(i).getHeight()));
            else if(end - start >= 10000 && i % 200 ==0) data.add(new XYChart.Data(date[1],moves.get(i).getHeight()));
            else data.add(new XYChart.Data("",moves.get(i).getHeight()));
        }
        set1.setData(data);
        if(set1 != null)cdg.setData(FXCollections.observableArrayList(set1));



    }

    public  void plotXfY(int start, int end) throws ParseException{
        XYChart.Series set1 = new XYChart.Series<>();
        ObservableList<XYChart.Data> data = FXCollections.observableArrayList();

        ArrayList<Move> moves = goodMoves;
        XYChart.Series<Integer,Integer> series = new XYChart.Series<Integer, Integer>();
        ArrayList<LineChart.Data> list = new ArrayList<LineChart.Data>();

        yFx.setCreateSymbols(false);
        long duree;

        for(int i=start; i<=end; i++){

            duree = moves.get(i).getDate().getTime();
            Timestamp t = new Timestamp(duree);
            String[] date = t.toString().split(" ");
            if(end - start <= 10000 && i % 50 ==0)
                data.add(new XYChart.Data(date[1],moves.get(i).getY()));
            else if(end - start >= 10000 && i % 200 ==0) data.add(new XYChart.Data(Double.toString(moves.get(i).getX()),moves.get(i).getY()));
            else data.add(new XYChart.Data("",moves.get(i).getHeight()));
        }
        set1.setData(data);
        if(set1 != null) yFx.setData(FXCollections.observableArrayList(set1));
    }


    public void btnComboHour() throws ParseException{
        ArrayList<Move> moves = goodMoves;
        ArrayList<Move> newListMove = new ArrayList<Move>();
        int start=-1;
        int end;
        System.out.println("hhhhhh");
        String[] comboText = comboHour.getSelectionModel().getSelectedItem().split(" ");
        for (int i =0; i< moves.size(); i++){
            String[] date= moves.get(i).getDate().toString().split(" ");
            String[] heure = date[3].split(":");
            if(comboText[0].equals("1.")) {
                if (heure[0].equals("08") || heure[0].equals("09")) {
                    start = i;
                    break;

                }
            }
            if(comboText[0].equals("2.")) {
                if (heure[0].equals("10") || heure[0].equals("11")) {
                    start = i;
                    break;

                }
            }
            if(comboText[0].equals("3.")) {
                if (heure[0].equals("12") || heure[0].equals("13")) {
                    start = i;
                    break;

                }
            }

        }
        end = start;
        for (int i =start; i< moves.size(); i++){
            String[] date= moves.get(i).getDate().toString().split(" ");
            String[] heure = date[3].split(":");
            if(comboText[0].equals("1.")) {
                if (heure[0].equals("08") || heure[0].equals("09")) {
                    //end = i;
                    end ++;
                    newListMove.add(moves.get(i));

                }
            }
            if(comboText[0].equals("2.")) {
                if (heure[0].equals("10") || heure[0].equals("11")) {
                    end ++;
                    newListMove.add(moves.get(i));

                }
            }
            if(comboText[0].equals("3.")) {
                if (heure[0].equals("12")|| heure[0].equals("13")) {
                    end ++;
                    newListMove.add(moves.get(i));

                }
            }

        }



        //end =i;

        System.out.println(start + "  " + end);
        nbTotalCdg.setText(Integer.toString(listCDG(moves,start,end).size()));
        x.setText(maximumDistanceX(moves, start, end )+"");
        y.setText(maximumDistanceY(moves, start, end)+ "");
        z.setText(maximumDistanceZ(moves, start, end)+ "");
        tpsTotalCdg.setText(totalDurationCDG(moves,start,end).get(1));


        plotCDG(start,end);
        plotXfY(start,end);

    }

    public void btnComboCategory(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Recap.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("RECAP");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }


        System.out.println("rrrrr");

    }



}