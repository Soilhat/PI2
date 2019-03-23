package Model;

import java.text.ParseException;
import java.util.ArrayList;

import static Model.CoupDeGrue.*;
import static Model.ReadExcel.oneByOneExample;

public class MainTest {
    public static void main(String[] args) throws ParseException {
        ArrayList<Move> moves = oneByOneExample("data.csv");
        System.out.println(moves.size());
        System.out.println(searchDown(moves,0,3000));
        System.out.println(searchUp(moves,51,3000));

        System.out.println(searchTurnAndStandStill(moves,100,3000));
        System.out.println(searchDown(moves,128,3000));

        //System.out.println(standStill(moves,0,498));

        System.out.println(searchCDG(moves,3000,moves.size()-5));
        //System.out.println(listCDG(moves,0,moves.size()-5));

        //System.out.println(moves.get(3).getDate());
        //System.out.println(moves.get(4).getDate());

    }
}
