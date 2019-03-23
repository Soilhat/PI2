package Model;

import java.util.Date;

public class MoveDuration
{
    private Double height;
    private Long start;
    private Long end;

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    public Long getDuration()
    {
        return end - start;
    }

    @Override
    public String toString() {
        return "MoveDuration{" +
                "height=" + height +
                ", start=" + new Date(start) +
                ", end=" + new Date(end) +
                '}';
    }
}
