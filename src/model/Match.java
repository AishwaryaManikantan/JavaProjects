package model;

import patterns.AbstractPattern;

public class Match {
    private AbstractPattern pattern;
    private int weight;
    public Match(AbstractPattern pattern, int weight){
        this.pattern=pattern;
        this.weight=weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public AbstractPattern getPattern() {
        return pattern;
    }

    public void setPattern(AbstractPattern pattern) {
        this.pattern = pattern;
    }
}
