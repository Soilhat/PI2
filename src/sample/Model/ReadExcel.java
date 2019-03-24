package Model;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReadExcel {

    public static void main(String[] args) throws ParseException {

        oneByOneExample("data.csv");
    }


    public static ArrayList<Move> oneByOneExample(String fileName) throws ParseException {
        ArrayList<String[]> tab = new ArrayList<String[]>();
        ArrayList<Move> moves = new ArrayList<Move>();
        moves.add(new Move());
        ArrayList<Move> goodMoves = new ArrayList<Move>();

        try{
            ClassLoader c = Thread.currentThread().getContextClassLoader();
            InputStream is = c.getResourceAsStream(fileName);
            InputStreamReader s = new InputStreamReader(is);
            BufferedReader br = //new BufferedReader(new FileReader("sqlify-result-8fd341696c7d4.csv"));
                    new  BufferedReader(s);

            BufferedWriter file = new BufferedWriter(new FileWriter("test.csv"));
            //BufferedWriter file2 = new BufferedWriter(new FileWriter("books.csv"));

            String line="";
            String id="";
            int i =0;
            String firstLine = br.readLine();
            file.write(firstLine);
            while((line = br.readLine()) != null){

                Move m = new Move();
                String[] val = line.split(";");
                //String[] nextval = br.readLine().split(";");
                tab.add(val);
                //m.setTime(Timestamp.valueOf(val[5]));
                m.setRadius(Integer.parseInt(val[1]));
                m.setHeight(Integer.parseInt(val[2]));
                m.setLoad(Integer.parseInt(val[3]));
                m.setAngle(Integer.parseInt(val[4]));
                SimpleDateFormat isoDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss,SSS");
                //System.out.println(isoDate);

                Date date = isoDate.parse(val[5]);
                String[] stringDate = val[5].split(":");
                m.setStringDate(stringDate[2]);
                //System.out.println(m.getStringDate());

                m.setDate(date);
                m.setX(Double.parseDouble(val[7].replace(',','.')));
                m.setY(Double.parseDouble(val[8].replace(',','.')));
                m.setZ(Double.parseDouble(val[9].replace(',','.')));
                m.setDistance(Double.parseDouble(val[10].replace(',','.')));
                m.setVitesse(Double.parseDouble(val[11].replace(',','.')));
                //m.setDate(Date.parseDate(val[5]));
                //List<String> val2 = new ArrayList<String>(Arrays.asList(val));


                moves.add(m);
                i++;
                if(moves.get(i-1).getHeight() != moves.get(i).getHeight() && moves.get(i-1).getAngle() != moves.get(i).getAngle()) {
                    goodMoves.add(moves.get(i));
                    String truc = StringUtils.join(val, ';');
                    file.write(truc +"\n");
                }
                //System.out.println(m);


            }
            //System.out.println(file);


        }
        catch (IOException e){
            e.printStackTrace();
        }
        moves.remove(0); //on retire le mouvement null
        System.out.println(moves.size());
        System.out.println(goodMoves.size());
        /*for (int i = 0; i<moves.size()-1; i++){
            if(moves.get(i).getHeight() != moves.get(i+1).getHeight() && moves.get(i).getAngle() != moves.get(i+1).getAngle()) {
                goodMoves.add(moves.get(i));

                //System.out.println("h: " + val[2] +" next_h: " + nextval[2] + " a " + val[4] + " next_a " + nextval[4]);

            }
        }*/


        return goodMoves;

    }
}
