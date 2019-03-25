package Controller;

import Model.Move;
import Model.MoveDuration;
import Model.MoveListWrapper;
import Model.MoveStat;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import service.csv.CsvLittleMoveService;
import service.csv.impl.CsvLittleMoveServiceImpl;
import service.move.LittleMoveService;
import service.move.impl.LittleMoveServiceImpl;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/littlemove.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("/littlemove.fxml"));
        primaryStage.setTitle("Hello World");
        FileChooser fileChooser = new FileChooser();
        /*fileChooser.setTitle("Open Resource File");
        fileChooser.showOpenDialog(primaryStage);*/
        primaryStage.setScene(new Scene(root, 700, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            /** ce sont les deux valeurs parametrables pour les petits mouvements
             - threshold : seuil à partir duquel on considère qu'il s'agit d'un petit mouvement
             - dateString : la date pour laquelle seront génerées les statistiques de repartition des petits mouvements
             **/
            Integer threshold = 10;
            String dateString = "11/05/2017";
            Date date;
            try
            {
                date = sdf.parse(dateString);
            } catch (ParseException e) {
                date = new Date();
                e.printStackTrace();
            }
        System.out.println("Options");
            System.out.println("threshold : " + threshold);
            System.out.println("Loading...");

            LittleMoveService littleMoveService = new LittleMoveServiceImpl();
            //Recupère et calcule la durée des petits mouvements
            List<MoveDuration> stats = littleMoveService.littleMoveDuration(threshold);

            System.out.println("Petits mouvemements trouvés");
            stats.forEach(System.out::println);
            //Calcule la durée totale des petits mouvements (via la fonction sum() de l'api Stream
            String totalDurationString = littleMoveService.littleMoveTotalDurationFormatted(stats);
            System.out.println("Durée total des petits mouvements : " + totalDurationString);


        //Calcule la durée moyenne des petits mouvements (via la fonction average() de l'api Stream
        String averageDurationString = littleMoveService.littleMoveAverageFormatted(stats);
        System.out.println("Durée moyenne des petits mouvements : " + averageDurationString);

        //Recupère la plus longure periode pendant laquelle il n'y a eu que des petits mouvements
            // via la fonction max() de l'api stream
            MoveDuration longestLittleMove = littleMoveService.longestLittleMove(stats);
            System.out.println("Le plus long petit mouvement : " + longestLittleMove);

            MoveListWrapper wrapper = littleMoveService.splitData(threshold);
            List<MoveStat> moveStats = littleMoveService.statSplitedByHour(wrapper, date);
            moveStats.forEach(System.out::println);

            System.out.println("Done");

        launch(args);
    }
}
