package patterns;

public enum MultiPattern {
    TWO_PAIR(3),
    FULL_HOUSE(7);

    private final int rank;
    private MultiPattern(int rank){
        this.rank=rank;
    }

    public int getRank(){
     return this.rank;
    }
}
