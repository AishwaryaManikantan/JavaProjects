package patterns;

import datahander.CommonConstants;

import java.util.List;
import java.util.Set;

public abstract class AbstractPattern {

    private int rank;

    protected AbstractPattern(int rank){
        this.rank=rank;
    }

    public int getRank() {
        return rank;
    }

   public int calculateWeight(Set<String> values){
       return values.stream().mapToInt(key-> CommonConstants.cardNumbers.indexOf(key)).sum();
   }

    public int calculateWeight(int cardNumber,int cardnumberCount){
        return cardNumber*cardnumberCount;
    }

    abstract public Boolean isMatching(int cardNumberCount);
    abstract public Boolean isMatching(List<String> values);
    abstract public String label();
}
