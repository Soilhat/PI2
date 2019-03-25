package Model;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;

import static Model.CoupDeGrue.*;
import static Model.ReadExcel.oneByOneExample;

public class MainTest {
    public static void main(String[] args) throws ParseException {
        ArrayList<Move> moves = oneByOneExample("data3.csv");
        //System.out.println(listCDG(moves,0,moves.size()-3).size());
        //System.out.println(totalDurationCDG(moves,0,moves.size()-3));
        System.out.println(endDay(moves));
        System.out.println(moves.get(0));

        //System.out.println(moves.get(5).getDate());

        //System.out.println(moves.size());
        //System.out.println(searchDown(moves,360,3000));
        //System.out.println(findNextMove(moves,417,3000));
        /*System.out.println(searchUp(moves,51,3000));

        System.out.println(searchTurnAndStandStill(moves,100,3000));
        System.out.println(searchDown(moves,128,3000));*/
        //System.out.println(moves.get(3).getDate());

        //System.out.println(standStill(moves,0,498));

        /*t endDown = (searchDown(moves,1000,50000).get(1));
        System.out.println(endDown);
        System.out.println(searchDown(moves,2380,3000));
        System.out.println(searchUp(moves,endDown,3000));
        System.out.println(findNextMove(moves,endDown,3000));*/
        //System.out.println(searchCDG(moves,49378,50000));
        //System.out.println(listCDG(moves,0,50000).size());

        //System.out.println(moves.get(3).getDate());
        //System.out.println(moves.get(4).getDate());

    }
}
