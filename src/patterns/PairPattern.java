package patterns;


import java.util.List;

public class PairPattern extends AbstractPattern {

    public PairPattern(){
        super(2);
    }


    public Boolean isMatching(int cardNumber) {
        if (cardNumber == 2) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean isMatching(List<String> values) {
        return null;
    }

    @Override
    public String label() {
        return "Pair";
    }
}
