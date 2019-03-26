package Model;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import service.LittleMoveService;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CoupDeGrue  {

    static long inactivityThreshold = 600000; //1h40mn
    //public LoadData data;

    static int erreur = 2;

    public static ArrayList<Integer> up (ArrayList<Move> moves,int start, int end){
        int row = start;
        ArrayList<Integer> pair = new ArrayList<Integer>();

        //On ajoute deux élément dans la liste pour qu'elle soit de taille 2
        pair.add(0);
        pair.add(0);
        for (int i =start; i<=end; i++){
            row ++;
            if(moves.get(i).getHeight()>= moves.get(i+1).getHeight() && moves.get(i+1).getHeight()>= moves.get(i+2).getHeight() && moves.get(i+2).getHeight()>= moves.get(i+3).getHeight()) {
                if (row == 0)
                    continue;
                break;
            }
            else if(moves.get(i).getHeight()< moves.get(i+1).getHeight() && moves.get(i+1).getHeight()< moves.get(i+2).getHeight() && moves.get(i+2).getHeight()< moves.get(i+3).getHeight()){
                continue;
            }

        }
        pair.set(0,start);
        pair.set(1,row);
        return pair;

    }


    public static ArrayList<Integer> searchUp(ArrayList<Move> moves ,int start, int end){
        int head = start;
        //ArrayList<Integer> pair = new ArrayList<Integer>(2);
        for (int i =start; i<=end; i++){

            if((Math.abs(moves.get(i).getHeight() - moves.get(i+1).getHeight()))<=erreur && (Math.abs(moves.get(i).getHeight() - moves.get(i+2).getHeight()))<=erreur && (Math.abs(moves.get(i).getHeight() - moves.get(i+3).getHeight()))<=erreur ){
                //System.out.println("continue " + i);
                continue;
            }
            else if((Math.abs(moves.get(i).getHeight() - moves.get(i+1).getHeight()))>erreur && (Math.abs(moves.get(i).getHeight() - moves.get(i+2).getHeight()))>erreur && (Math.abs(moves.get(i).getHeight() - moves.get(i+3).getHeight()))>erreur ){
                head = i;
                //System.out.println("break " + i);
                break;
            }
            //else System.out.println("aaa "+i);
        }
        return up(moves,head,end);
    }

    public static ArrayList<Integer> down (ArrayList<Move> moves,int start, int end){
        int row = start;
        ArrayList<Integer> pair = new ArrayList<Integer>();

        //On ajoute deux élément dans la liste pour qu'elle soit de taille 2
        pair.add(0);
        pair.add(0);
        for (int i =start; i<=end; i++){
            row ++;
            if(moves.get(i).getHeight()<= moves.get(i+1).getHeight() && moves.get(i+1).getHeight()<= moves.get(i+2).getHeight() && moves.get(i+2).getHeight()<= moves.get(i+3).getHeight()){
                if (row == 0)
                    continue;
                break;
            }
            else if(moves.get(i).getHeight()> moves.get(i+1).getHeight() && moves.get(i+1).getHeight()> moves.get(i+2).getHeight() && moves.get(i+2).getHeight()> moves.get(i+3).getHeight()){
                continue;
            }

        }
        pair.set(0,start);
        pair.set(1,row);
        return pair;

    }


    public static ArrayList<Integer> searchDown(ArrayList<Move> moves ,int start, int end){
        int head = start;
        int a = 3;
        //ArrayList<Integer> pair = new ArrayList<Integer>(2);
        for (int i =start; i<=end; i++){

            if(moves.get(i).getHeight() - moves.get(i+1).getHeight()<=0 && moves.get(i+1).getHeight() - moves.get(i+2).getHeight()<=0 && moves.get(i+2).getHeight() - moves.get(i+3).getHeight()<=0){
                head++;
                continue;
            }

            else if(moves.get(i).getHeight() - moves.get(i+1).getHeight()>0 && moves.get(i+1).getHeight() - moves.get(i+2).getHeight()>0&& moves.get(i+2).getHeight() - moves.get(i+3).getHeight()>0 ){
                head = i;
                a=i;
                break;
            }
        }
        //System.out.println("a = " + a);
        //System.out.println("head = " + head);
        return down(moves,head,end);
    }

    public static ArrayList<Integer> standStill(ArrayList<Move> moves ,int start, int end){
        Boolean standStill = false;
        ArrayList<Integer> pair = new ArrayList<Integer>();

        //On ajoute deux élément dans la liste pour qu'elle soit de taille 2
        pair.add(0);
        pair.add(0);
        int row = start;

        for (int i = start; i <= end ; i++){
            row ++;

            //System.out.println(i);
            if(moves.get(i).getStringDate() != moves.get(i+1).getStringDate() && (Math.abs(moves.get(i).getHeight() - moves.get(i+1).getHeight()))<=erreur && (Math.abs(moves.get(i).getHeight() - moves.get(i+2).getHeight()))<=erreur && (Math.abs(moves.get(i).getHeight() - moves.get(i+3).getHeight()))<=erreur ){
                standStill = true;
                //System.out.println("continue");
                continue;

            }
            else if(moves.get(i).getStringDate() != moves.get(i+1).getStringDate() && (Math.abs(moves.get(i).getHeight() - moves.get(i+1).getHeight()))>erreur && (Math.abs(moves.get(i).getHeight() - moves.get(i+2).getHeight()))>erreur && (Math.abs(moves.get(i).getHeight() - moves.get(i+3).getHeight()))>erreur ){
                standStill = false;
                //System.out.println("break");
                break;
            }
        }
        pair.set(0,start);
        pair.set(1,row);
        return pair;
    }

    public static ArrayList<Integer> searchTurnAndStandStill(ArrayList<Move> moves ,int start, int end){
        int head = start;
        for(int i = start; i <= end; i++){
            if(moves.get(i).getStringDate() != moves.get(i+1).getStringDate() && (Math.abs(moves.get(i).getHeight() - moves.get(i+1).getHeight()))<=erreur && (Math.abs(moves.get(i).getHeight() - moves.get(i+2).getHeight()))<=erreur && (Math.abs(moves.get(i).getHeight() - moves.get(i+3).getHeight()))<=erreur ){
              head = i;
                break;
            }
            else if(moves.get(i).getStringDate() != moves.get(i+1).getStringDate() && (Math.abs(moves.get(i).getHeight() - moves.get(i+1).getHeight()))>erreur && (Math.abs(moves.get(i).getHeight() - moves.get(i+2).getHeight()))>erreur && (Math.abs(moves.get(i).getHeight() - moves.get(i+3).getHeight()))>erreur ){
                continue;
            }
        }
        return standStill(moves,head,end);
    }


    public static String findNextMove(ArrayList<Move> moves, int start, int end){
        String nextMove ="";
        int up = searchUp(moves,start,end).get(0);
        int down = searchDown(moves,start,end).get(0);
        /*int turn = searchTurnAndStandStill(moves,start,end).get(0);

        if(Math.min(up,Math.min(down,turn)) == up) nextMove = "up";
        if(Math.min(up,Math.min(down,turn)) == down) nextMove = "down";
        if(Math.min(up,Math.min(down,turn)) == turn) nextMove = "turn";*/

        if(Math.min(up,down) == up) nextMove = "up";
        if(Math.min(up,down) == down) nextMove = "down";

        return nextMove;
    }

    public static ArrayList<Integer> searchCDG(ArrayList<Move> moves,int start, int end){
        ArrayList<Integer> pair = new ArrayList<Integer>();
        Boolean find = false;
        pair.add(0);
        pair.add(0);
        int head = 0;
        int tale = 0;
        int startDown1 = searchDown(moves,start,end).get(0);
        //int endDown = (searchDown(moves,start,end).get(1));
        while (find == false) {
            int endDown = (searchDown(moves,start,end).get(1));
            if (findNextMove(moves, endDown, end) == "up") {
                //System.out.println(start);
                int startUp = searchUp(moves, endDown, end).get(0);
                int endUp = searchUp(moves, endDown, end).get(1);
                /*if (findNextMove(moves,endUp,end) == "turn"){
                    int startTurn = searchTurnAndStandStill(moves,endUp,end).get(0);
                    int endTurn = (searchTurnAndStandStill(moves,endUp,end).get(1));
                    if (findNextMove(moves,endTurn,end) == "down"){
                        find = true;
                        head = startDown1;
                        tale = searchDown(moves,endTurn-1,end).get(0);
                    }
                }*/
                if (findNextMove(moves, endUp, end) == "down") {
                    //System.out.println("endUp1 " + endUp);
                    find = true;
                    head = startDown1;
                    tale = searchDown(moves, endUp, end).get(1);
                }
                else if (findNextMove(moves, endUp, end) == "up") {
                    endUp = searchUp(moves, endUp, end).get(1);
                    if (findNextMove(moves, endUp, end) == "down") {
                        //System.out.println("endUp2 " + endUp);
                        find = true;
                        head = startDown1;
                        tale = searchDown(moves, endUp, end).get(1);
                    }
                    else start = endDown;
                }
                else start = endDown;
            }
            else if (findNextMove(moves, endDown, end) == "down") {
                endDown = (searchDown(moves, endDown, end).get(1));
                int startUp = searchUp(moves, endDown, end).get(0);
                int endUp = searchUp(moves, endDown, end).get(1);
                /*if (findNextMove(moves,endUp,end) == "turn"){
                    int startTurn = searchTurnAndStandStill(moves,endUp,end).get(0);
                    int endTurn = (searchTurnAndStandStill(moves,endUp,end).get(1));
                    if (findNextMove(moves,endTurn,end) == "down"){
                        find = true;
                        head = startDown1;
                        tale = searchDown(moves,endTurn-1,end).get(0);
                    }
                }*/
                if (findNextMove(moves, endUp, end) == "down") {
                    //System.out.println("endUp3 " + endUp);
                    endUp = searchUp(moves,endUp,end).get(1);
                    //System.out.println("endUp3 " + endUp);
                    find = true;
                    head = startDown1;
                    tale = searchDown(moves, endUp, end).get(1);

                }
                else if (findNextMove(moves, endUp, end) == "up") {
                    endUp = searchUp(moves, endUp, end).get(1);
                    if (findNextMove(moves, endUp, end) == "down") {
                        find = true;
                        head = startDown1;
                        //System.out.println("endUp4 " + endUp);
                        tale = searchDown(moves, endUp, end).get(1);
                    }
                    else start = endDown;
                }
            } else start = endDown;
        }
        pair.set(0,head);
        pair.set(1,tale);


        /*Boolean find = false;
        ArrayList<Integer> pair = new ArrayList<Integer>();
        pair.add(0);
        pair.add(0);
        int head = 0;
        int tale = 0;
        int i = 0;
        while (find == false && start != end+1 ){
            int startDown1 = searchDown(moves,start,end).get(0);
            int endDown = (searchDown(moves,start,end).get(1));
            int startUp = searchUp(moves,endDown,end).get(0);
            int endUp = (searchUp(moves,endDown,end).get(1));
            int startTurn = searchTurnAndStandStill(moves,endUp,end).get(0);
            int endTurn = (searchTurnAndStandStill(moves,endUp,end).get(1));
            int startDown2 = searchDown(moves,endTurn-1,end).get(0);

            //System.out.println("start "+ start +" startDown " + startDown1 + " endDown " + endDown + " startUp " + (startUp-2) + " endUp " + endUp + " startTurn " + startTurn + " endTurn " + endTurn + " startDown2 " + (startDown2+1) + " end " + end);
            if (endDown == startUp-2 && endUp == startTurn && endTurn == startDown2+1){
                find = true;
                head = startDown1;
                tale = startDown2;
                System.out.println(find);
            }
            else {
                start = endDown;
            }

            i++;

        }

        pair.set(0,head);
        pair.set(1,tale);*/

        return pair;
    }

    public static ArrayList<ArrayList<Integer>> listCDG(ArrayList<Move> moves,int start, int end){
        ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
        int i =0;
        while (!searchCDG(moves,start,end).get(1).equals(searchCDG(moves,start,end).get(0))){
            //searchCDG(moves,start,end);
            if(searchCDG(moves,start,end).get(1) != 0 && searchCDG(moves,start,end).get(0) != 0 ){
                list.add(searchCDG(moves,start,end));
                //System.out.println(list);
                start = searchCDG(moves,start,end).get(1);

            }


            /*else if (start!= end && searchCDG(moves,start,end).get(1) == 0 && searchCDG(moves,start,end).get(0) == 0){
                start++;
                System.out.println("hgfhgf " + start + " " + end);
            }*/

        }

        return list;
    }

    public static long duration(ArrayList<Move> moves, int start, int end){
        long duree = moves.get(end).getDate().getTime()-moves.get(start).getDate().getTime();
        //Timestamp t = new Timestamp(duree);
        //String[] date = t.toString().split(" ");
        return duree;
    }

    public static double maximumDistanceX (ArrayList<Move> moves, int start, int end){
        double distance = 0;
        for(int i= start; i<end ; i++){
            distance+=Math.abs(moves.get(i).getX()-moves.get(i+1).getX());
        }
        BigDecimal bd = new BigDecimal(distance/10);
        bd= bd.setScale(2,BigDecimal.ROUND_DOWN);
        distance = bd.doubleValue();
        return distance;
    }
    public static double maximumDistanceY (ArrayList<Move> moves, int start, int end){
        double distance = 0;
        for(int i= start; i<end ; i++){
            distance+=Math.abs(moves.get(i).getY()-moves.get(i+1).getY());
        }
        BigDecimal bd = new BigDecimal(distance/10);
        bd= bd.setScale(2,BigDecimal.ROUND_DOWN);
        distance = bd.doubleValue();
        return distance;
    }
    public static double maximumDistanceZ (ArrayList<Move> moves, int start, int end){
        double distance = 0;
        for(int i= start; i<end ; i++){
            distance+=Math.abs(moves.get(i).getZ()-moves.get(i+1).getZ());
        }
        BigDecimal bd = new BigDecimal(distance/10);
        bd= bd.setScale(2,BigDecimal.ROUND_DOWN);
        distance = bd.doubleValue();
        return distance;
    }
    public static int maximumAngle (ArrayList<Move> moves, int start, int end){
        int distance = 0;
        for(int i= start; i<end ; i++){
            distance+=Math.abs(moves.get(i).getAngle()-moves.get(i+1).getAngle());
        }
        return distance;
    }

    public static ArrayList<String> totalDurationCDG(ArrayList<Move> moves, int start, int end){
        ArrayList<ArrayList<Integer>> listCDG = listCDG(moves,start,end);
        ArrayList<String> timeStampAndDuration = new ArrayList<String>();
        timeStampAndDuration.add("");
        timeStampAndDuration.add("");
        timeStampAndDuration.add("");
        String total ="";
        long duree =0;

        for (ArrayList<Integer> cdg : listCDG){
            duree += duration(moves,cdg.get(0),cdg.get(1));
            //System.out.println(duree);
        }
        Timestamp t = new Timestamp(duree);
        String[] date = t.toString().split(" ");
        double prct = duree/864000;  // on divise par 24h
        timeStampAndDuration.set(0,duree+"");
        timeStampAndDuration.set(1,date[1]);
        timeStampAndDuration.set(2,prct+"");

        //System.out.println(prct+ " %");
        return timeStampAndDuration;
    }

    public static String getHour(long timeStamp){
        Timestamp t = new Timestamp(timeStamp);
        String[] date = t.toString().split(" ");
        return date[1];
    }

    public static ArrayList<String> endOfDay(ArrayList<Move> moves){
        //String end ="";
        ArrayList<String> endDayElement = new ArrayList<String>();
        endDayElement.add("");
        endDayElement.add("");
        int end = -1;
        for(int i =0; i<moves.size()-3; i++){
            if(moves.get(i+1).getDate().getTime()-moves.get(i).getDate().getTime()>inactivityThreshold){
                end = i;
                break;
            }
        }
        long duree = moves.get(end).getDate().getTime();
        Timestamp t = new Timestamp(duree);
        String[] date = t.toString().split(" ");
        endDayElement.set(0,date[1]);
        endDayElement.set(1,end+"");
        return endDayElement;

    }



}
