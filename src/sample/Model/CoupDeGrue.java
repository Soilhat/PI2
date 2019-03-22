package Model;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import service.LittleMoveService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CoupDeGrue  {

    public XSSFSheet sheet;
    public LoadData data;

    static int erreur = 1;

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

            if(moves.get(i).getHeight() - moves.get(i+1).getHeight()<0 && moves.get(i).getHeight() - moves.get(i+2).getHeight()<0 && moves.get(i).getHeight() - moves.get(i+3).getHeight()<0){
                head++;
                continue;
            }

            else if(moves.get(i).getHeight() - moves.get(i+1).getHeight()>0 && moves.get(i).getHeight() - moves.get(i+2).getHeight()>0&& moves.get(i).getHeight() - moves.get(i+3).getHeight()>0 ){
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

    public static ArrayList<Integer> searchCDG(ArrayList<Move> moves,int start, int end){
        Boolean find = false;
        ArrayList<Integer> pair = new ArrayList<Integer>();
        pair.add(0);
        pair.add(0);
        int head = 0;
        int tale = 0;
        int i = 0;
        while (find == false && i <=10){
            int startDown1 = searchDown(moves,start,end).get(0);
            int endDown = (searchDown(moves,start,end).get(1))-1;
            int startUp = searchUp(moves,endDown,end).get(0);
            int endUp = (searchUp(moves,endDown,end).get(1))-1;
            int startTurn = searchTurnAndStandStill(moves,endUp-1,end).get(0);
            int endTurn = (searchTurnAndStandStill(moves,endUp,end).get(1))-1;
            int startDown2 = searchDown(moves,endTurn-1,end).get(0);

            System.out.println("start " + start + " endDown " + endDown + " startUp " + startUp + " endUp " + endUp + " startTurn " + startTurn + " endTurn " + endTurn + " startDown2 " + startDown2);

            if (endDown == startUp && endUp == startTurn && endTurn == startDown2){
                find = true;
                head = startDown1;
                tale = startDown2;

                System.out.println("h " + head + " t " + tale);
            }
            else {
                start = endDown;
                System.out.println("start " + start);
            }

            i++;

        }

        pair.set(0,head);
        pair.set(1,tale);

        return pair;
    }

    public static ArrayList<ArrayList<Integer>> listCDG(ArrayList<Move> moves,int start, int end){
        ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
        while (start!=(end-1)){
            list.add(searchCDG(moves,start,end));
            start = searchCDG(moves,start,end).get(1);
            System.out.println(start);
        }

        return list;
    }
}
