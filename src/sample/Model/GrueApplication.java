package Model;

import Model.Move;
import Model.MoveListWrapper;
import Model.MoveStat;
import service.LittleMoveService;
import Model.LittleMoveServiceImpl;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.io.Console;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GrueApplication
{
    public static void main(String[] args) throws ParseException, IOException {

        SimpleDateFormat isoDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Integer threshold = 2 ; //Integer.parseInt(args[0]);
        Date start = isoDate.parse("2017-05-11T08:00:00Z");
        Date end = isoDate.parse("2017-05-11T11:00:00Z");

        LittleMoveService littleMoveService = new LittleMoveServiceImpl();

        //System.out.println(littleMoveService);
        //littleMoveService.loadSheet(littleMoveService.getClass().getClassLoader().getResourceAsStream("data3.xlsx"));

        //System.out.println(littleMoveService.loadSheet(littleMoveService.getClass().getClassLoader().getResourceAsStream("data2.xlsx")).getClass());

        XSSFSheet sheet = littleMoveService.loadSheet(littleMoveService.getClass().getClassLoader().getResourceAsStream("data3.xlsx"));
        MoveListWrapper moveList = littleMoveService.getLittleMove(sheet, threshold);

        MoveStat stat = littleMoveService.statByDate(moveList,start, end);
        List<MoveStat> stats = littleMoveService.statSplitedByHour(moveList,start);

        /*for (MoveStat m : stats){
            System.out.println(m);
        }*/
        stats.forEach(System.out::println);

        /*CoupDeGrue cdg = new CoupDeGrue();
        cdg.data =
        cdg.sheet = cdg.data.loadSheet(littleMoveService.getClass().getClassLoader().getResourceAsStream("data.xlsx"));*/

    }
}