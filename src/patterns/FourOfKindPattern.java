package patterns;


import java.util.List;

public class FourOfKindPattern extends AbstractPattern {

    public FourOfKindPattern(){
        super(8);
    }

    public Boolean isMatching(int cardNumberCount) {
        if (cardNumberCount == 4) {
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
        return "Four of Kind";
    }
}
