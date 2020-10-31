public enum Direction {
    NORTH,
    SOUTH,
    EAST,
    WEST;

    public Direction nextClockwiseDirection() {
        return switch (this) {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
        };
    }

    public Direction nextCounterClockwiseDirection() {
        return switch (this) {
            case NORTH -> WEST;
            case EAST -> NORTH;
            case SOUTH -> EAST;
            case WEST -> SOUTH;
        };
    }
}
