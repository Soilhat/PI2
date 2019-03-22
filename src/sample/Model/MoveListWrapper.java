package Model;

import java.util.ArrayList;

import java.util.List;

public class MoveListWrapper {
    private List<Move> littleMoves = new ArrayList<>();
    private List<Move> normalMoves = new ArrayList<>();

    public List<Move> getLittleMoves() {
        return littleMoves;
    }

    public void setLittleMoves(List<Move> littleMoves) {
        this.littleMoves = littleMoves;
    }

    public List<Move> getNormalMoves() {
        return normalMoves;
    }

    public void setNormalMoves(List<Move> normalMoves) {
        this.normalMoves = normalMoves;
    }
}
