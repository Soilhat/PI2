package Model;

import java.util.Date;

public class MoveStat {
    private Date start;
    private Date end;
    private long littleMove;
    private long normalMove;

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public long getLittleMove() {
        return littleMove;
    }

    public void setLittleMove(long littleMove) {
        this.littleMove = littleMove;
    }

    public long getNormalMove() {
        return normalMove;
    }

    public void setNormalMove(long normalMove) {
        this.normalMove = normalMove;
    }

    @Override
    public String toString() {
        return "MoveStat{" +
                "start=" + start +
                ", end=" + end +
                ", littleMove=" + littleMove +
                ", normalMove=" + normalMove +
                '}';
    }
}
