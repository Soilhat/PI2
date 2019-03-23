package service.move;

import Model.Move;
import Model.MoveDuration;
import Model.MoveListWrapper;
import Model.MoveStat;

import java.util.Date;
import java.util.List;

public interface LittleMoveService
{
    List<MoveDuration> littleMoveDuration(Integer threshold);

    Long littleMoveTotalDuration(List<MoveDuration> durations);

    String littleMoveTotalDurationFormatted(List<MoveDuration> durations);

    MoveDuration longestLittleMove(List<MoveDuration> durations);

    Long littleMoveAverage(List<MoveDuration> durations);
    String littleMoveAverageFormatted(List<MoveDuration> durations);

    MoveListWrapper splitData(Integer threshold);
    MoveStat statByDate(MoveListWrapper moves, Date start, Date end);
    List<MoveStat> statSplitedByHour(MoveListWrapper moves, Date date);
}
