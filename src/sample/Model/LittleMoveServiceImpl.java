package Model;

import org.joda.time.DateTime;
import Model.Move;
import Model.MoveListWrapper;
import Model.MoveStat;
import service.LittleMoveService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class LittleMoveServiceImpl implements LittleMoveService
{
    private Map<String, Integer> headerCache = new HashMap<>();

    @Override
    public Map<String, Integer> getHeader(XSSFSheet sheet)
    {
        Row header = sheet.getRow(0);
        headerCache = createHeader(header);
        return headerCache;
    }

    //load file
    @Override
    public XSSFSheet loadSheet(InputStream is) throws IOException
    {
        XSSFWorkbook workbook = new XSSFWorkbook(is);

        XSSFSheet sheet = workbook.getSheetAt(0);
        Row header = sheet.getRow(0);
        headerCache = createHeader(header);

        return sheet;
    }

    @Override
    public Row getRow(XSSFSheet sheet, int index)
    {
        return sheet.getRow(index);
    }

    @Override
    public boolean isLittleMove(Row actualRow, Row nextRow, int threshold)
    {
        double actualHeight = actualRow.getCell(headerCache.get("Height")).getNumericCellValue();
        double nextHeight = nextRow.getCell(headerCache.get("Height")).getNumericCellValue();
        return Math.abs(actualHeight-nextHeight) < threshold;
    }

    // fonction qui permet de détecter les petits déplacements
// on prend comme paramètre, le fichier et l'ecart quon définit pour un petit déplacements
    @Override
    public MoveListWrapper getLittleMove(XSSFSheet sheet, int threshold)
    {
        MoveListWrapper wrapper = new MoveListWrapper();
        List<Move> littleMoves = new ArrayList<>();
        List<Move> normalMoves = new ArrayList<>();

        double height = threshold*(-1);

        for (Row row : sheet)
        {
            if(row.getRowNum() == 0)
                continue;

            if(row == null || row.getCell(headerCache.get("Height")) == null)
                break;

            double actualHeight= row.getCell(headerCache.get("Height")).getNumericCellValue();

            if (Math.abs(actualHeight-height) < threshold) {
                Move move = convertToMove(row);
                littleMoves.add(move);
            }
            else
            {
                Move move = convertToMove(row);
                normalMoves.add(move);
            }
            height = actualHeight;

        }
        wrapper.setLittleMoves(littleMoves);
        wrapper.setNormalMoves(normalMoves);
        return wrapper;
    }

// je convertis les données dans le fichier .xlsx en objet et je l'appelle dans la fonctio ci dessous pour sauvegarder les données

    @Override
    public Move convertToMove(Row row)
    {
        Move move = new Move();

        Double angle = row.getCell(headerCache.get("Angle")).getNumericCellValue();
        move.setAngle(angle.intValue());

        Double time = row.getCell(headerCache.get("Time")).getNumericCellValue();
        //move.setTime(time.longValue());

        Double radius = row.getCell(headerCache.get("Radius")).getNumericCellValue();
        move.setRadius(radius.intValue());

        Double height = row.getCell(headerCache.get("Height")).getNumericCellValue();
        move.setHeight(height.intValue());

        Double load = row.getCell(headerCache.get("Load")).getNumericCellValue();
        move.setLoad(load.intValue());

        Date date = row.getCell(headerCache.get("Date")).getDateCellValue();
        move.setDate(date);

        //move.setX(row.getCell(headerCache.get("x")).getNumericCellValue());
        move.setY(row.getCell(headerCache.get("y")).getNumericCellValue());
        move.setZ(row.getCell(headerCache.get("z")).getNumericCellValue());
        move.setDistance(row.getCell(headerCache.get("distance (m)")).getNumericCellValue());
        move.setVitesse(row.getCell(headerCache.get("vitesse (m/sec)")).getNumericCellValue());

        return move;
    }

    // la fonction MoveStat statByDate permet de prendre toute les données par date c'est à dire là par exemple dans notre fichier c'est une seule date donc on saisit la même date et l'heure qu'on veut ( je l'a testé dans le fichier grue application)
    @Override
    public MoveStat statByDate(MoveListWrapper moves, Date start, Date end) {
        long littleCount = moves.getLittleMoves().stream().filter(move -> move.getDate().after(start) && move.getDate().before(end)).count();
        long normalCount = moves.getNormalMoves().stream().filter(move -> move.getDate().after(start) && move.getDate().before(end)).count();
        MoveStat stat = new MoveStat();
        stat.setLittleMove(littleCount);
        stat.setNormalMove(normalCount);
        stat.setEnd(end);
        stat.setStart(start);
        return stat;
    }

    // et ici ça affiche les données par heure
    @Override
    public List<MoveStat> statSplitedByHour(MoveListWrapper moves, Date date)
    {
        List<MoveStat> stats = new ArrayList<>();
        DateTime start = new DateTime(date).withTimeAtStartOfDay();
        DateTime end = start.plusHours(1);
        for(int i = 0 ; i < 24 ; i++)
        {
            MoveStat stat = statByDate(moves, start.toDate(), end.toDate());
            start = start.plusHours(1);
            end = end.plusHours(1);
            stats.add(stat);
        }
        return stats;
    }

    //ça c'est juste ce qui permet de ne pas dépendre de l'ordre des colonnes dans le fichier c'est à dire  même si tu inverse l'ordre des colonne dans ton fichier ça va tjr marcher
    private Map<String, Integer> createHeader(Row header)
    {
        Map<String, Integer> result = new HashMap<>();

        Iterator<Cell> iterator = header.cellIterator();

        while (iterator.hasNext())
        {
            Cell cell = iterator.next();
            result.put(cell.getStringCellValue(), cell.getColumnIndex());
        }
        return result;
    }
}
