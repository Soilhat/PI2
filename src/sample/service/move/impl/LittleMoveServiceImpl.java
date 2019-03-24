package service.move.impl;

import Model.Move;
import Model.MoveDuration;
import Model.MoveListWrapper;
import Model.MoveStat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.joda.time.DateTime;
import service.csv.CsvLittleMoveService;
import service.csv.impl.CsvLittleMoveServiceImpl;
import service.move.LittleMoveService;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class LittleMoveServiceImpl implements LittleMoveService
{
    private static final Long LITTLE_MOVE_LIMIT_DURATION = 60000L;

    private CsvLittleMoveService csvLittleMoveService;

    public LittleMoveServiceImpl()
    {
        this.csvLittleMoveService = new CsvLittleMoveServiceImpl();
    }

    @Override
    public List<MoveDuration> littleMoveDuration(Integer threshold)
    {
        List<Move> moves ;
        try
        {
            moves = csvLittleMoveService.extractContent(this.getClass().getClassLoader().getResourceAsStream("valid_data.csv"));
        }
        catch (IOException e)
        {
            moves = new ArrayList<Move>();
        }
        List<MoveDuration> moveDurations = new ArrayList<>();

        double height = moves.get(0).getHeight();
        MoveDuration duration = new MoveDuration();
        duration.setStart(moves.get(0).getTime().getTime());
        duration.setHeight(height);
        duration.setEnd(moves.get(0).getTime().getTime());
        for (Move move : moves)
        {
            double actualHeight= move.getHeight();

            if (Math.abs(actualHeight-height) < threshold)
            {
                duration.setEnd(move.getTime().getTime());
            }
            else
            {
                moveDurations.add(duration);
                duration = new MoveDuration();
                duration.setHeight(actualHeight);
                duration.setStart(move.getTime().getTime());
                duration.setEnd(move.getTime().getTime());
                height = actualHeight;
            }
        }
        moveDurations.add(duration);

        return onlyLongLittleMove(moveDurations);
    }

    @Override
    public Long littleMoveTotalDuration(List<MoveDuration> durations)
    {
        return durations.stream().mapToLong(MoveDuration::getDuration).sum();
    }

    @Override
    public String littleMoveTotalDurationFormatted(List<MoveDuration> durations) {
        long totalDuration = littleMoveTotalDuration(durations);
        return formatDuration(totalDuration);
    }
    private String formatDuration(long duration)
    {
        int seconds = (int) (duration / 1000) % 60;
        int minutes = (int) ((duration / (1000 * 60)) % 60);
        int hours = (int) ((duration / (1000 * 60 * 60)) % 24);

        return String.format("%d h %d min, %d sec", hours, minutes, seconds);
    }

    @Override
    public MoveDuration longestLittleMove(List<MoveDuration> durations) {
        return durations
                .stream()
                .max(Comparator.comparing(MoveDuration::getDuration))
                .get();
    }

    @Override
    public Long littleMoveAverage(List<MoveDuration> durations) {
        return (long) durations.stream().filter(m -> m.getDuration() > 0).mapToLong(MoveDuration::getDuration).average().getAsDouble();
    }

    @Override
    public String littleMoveAverageFormatted(List<MoveDuration> durations) {
        long average = littleMoveAverage(durations);
        return formatDuration(average);
    }

    @Override
    public MoveListWrapper splitData(Integer threshold) {
        MoveListWrapper wrapper = new MoveListWrapper();
        List<Move> littleMoves = new ArrayList<>();
        List<Move> normalMoves = new ArrayList<>();

        List<Move> moves ;
        try
        {
            moves = csvLittleMoveService.extractContent(this.getClass().getClassLoader().getResourceAsStream("valid_data.csv"));
        }
        catch (IOException e)
        {
            moves = new ArrayList<Move>();
        }

        double height = threshold*(-1);

        for (Move move : moves)
        {

            double actualHeight= move.getHeight();

            if (Math.abs(actualHeight-height) < threshold) {
                littleMoves.add(move);
            }
            else
            {
                normalMoves.add(move);
            }
            height = actualHeight;

        }
        wrapper.setLittleMoves(littleMoves);
        wrapper.setNormalMoves(normalMoves);
        return wrapper;
    }

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

    private List<MoveDuration> onlyLongLittleMove(List<MoveDuration> moveDurations)
    {
        return moveDurations.stream().filter(m -> m.getDuration() > LITTLE_MOVE_LIMIT_DURATION).collect(Collectors.toList());
    }
}
