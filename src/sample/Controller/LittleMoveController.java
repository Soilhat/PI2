package Controller;

import Model.MoveDuration;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import service.move.impl.LittleMoveServiceImpl;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class LittleMoveController implements Initializable {
    private LittleMoveServiceImpl data =new LittleMoveServiceImpl();
    private int threshold;

    private List<MoveDuration> datas;
    public int getThreshold() {
        return threshold;
    }

    @FXML
    TextArea outputAverage;
    @FXML
    TextArea outputDureeMoyenne;
    @FXML
    TextField thresholdText;

    @FXML
    DatePicker datePicker;
    @FXML
    TextField printDate;
    @FXML
    Button btnDate;

    @FXML
    Button BtnList;

    @FXML
    ListView listLittleMove;

    @FXML
    Button duration;

    @FXML
    TextArea outputDureeTotal;

    @FXML
    Button moyenne;

    @Override
    public void initialize(URL location, ResourceBundle resources){
    }

    public void afficherListDeplacement(){
        String  thresholdString = thresholdText.getText();
        threshold = Integer.parseInt(thresholdString);
        datas  = data.littleMoveDuration(threshold);
        listLittleMove.setItems(FXCollections.observableList(datas));
    }

    public void afficherDureeTotal(){
        outputDureeTotal.appendText(data.littleMoveTotalDurationFormatted(datas));
    }

    public void afficherDureeMoyenne(){

        outputAverage.appendText(data.littleMoveAverageFormatted(datas));
    }

}
