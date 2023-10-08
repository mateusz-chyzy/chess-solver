package chess.moves.base;

public enum MoveDelta {
    North(0, 1),
    South(0, -1),
    East(1, 0),
    West(-1, 0),
    NorthEast(1, 1),
    NorthWest(-1, 1),
    SouthEast(1, -1),
    SouthWest(-1, -1),
    KnightNorthEast1(1, 2),
    KnightNorthEast2(2, 1),
    KnightNorthWest1(1, -2),
    KnightNorthWest2(2, -1),
    KnightSouthEast1(-1, 2),
    KnightSouthEast2(-2, 1),
    KnightSouthWest1(-1, -2),
    KnightSouthWest2(-2, -1),
    FirstPawnNorth(0, 2),
    FirstPawnSouth(0, -2);

    private final int deltaFile;
    private final int deltaRank;

    MoveDelta(int deltaFile, int deltaRank) {
        this.deltaFile = deltaFile;
        this.deltaRank = deltaRank;
    }

    public int getDeltaFile() {
        return deltaFile;
    }

    public int getDeltaRank() {
        return deltaRank;
    }
}
