package service;

import Model.Move;
import Model.MoveListWrapper;
import Model.MoveStat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface LittleMoveService
{
    Map<String, Integer> getHeader(XSSFSheet sheet);
    XSSFSheet loadSheet(InputStream is) throws IOException;
    Row getRow(XSSFSheet sheet, int index);
    boolean isLittleMove(Row actualRow, Row nextRow, int threshold);
    MoveListWrapper getLittleMove(XSSFSheet sheet, int threshold) throws IOException;

    Move convertToMove(Row row);

    MoveStat statByDate(MoveListWrapper moves, Date start, Date end);
    List<MoveStat> statSplitedByHour(MoveListWrapper moves, Date date);
}