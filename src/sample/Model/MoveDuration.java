package Model;

import java.util.Date;

public class MoveDuration
{
    private Double height;
    private Long start;
    private Long end;
    private Double minHeight;
    private Double maxHeight;

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

    public Double getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(Double minHeight) {
        this.minHeight = minHeight;
    }

    public Double getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(Double maxHeight) {
        this.maxHeight = maxHeight;
    }

    @Override
    public String toString() {
        return "MoveDuration{" +
                ", start=" + start +
                ", end=" + end +
                ", minHeight=" + minHeight +
                ", maxHeight=" + maxHeight +
                '}';
    }
}
