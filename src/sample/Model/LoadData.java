package Model;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LoadData {
    private Map<String, Integer> headerCache = new HashMap<>();

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

    public Map<String, Integer> getHeader(XSSFSheet sheet)
    {
        Row header = sheet.getRow(0);
        headerCache = createHeader(header);
        return headerCache;
    }

    public XSSFSheet loadSheet(InputStream is) throws IOException
    {
        XSSFWorkbook workbook = new XSSFWorkbook(is);

        XSSFSheet sheet = workbook.getSheetAt(0);
        Row header = sheet.getRow(0);
        headerCache = createHeader(header);

        return sheet;
    }

    public Row getRow(XSSFSheet sheet, int index)
    {
        return sheet.getRow(index);
    }

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
}
