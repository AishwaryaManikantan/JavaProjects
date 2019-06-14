package patterns;


import java.util.List;

public class ThreeOfKindPattern extends AbstractPattern {

    public ThreeOfKindPattern(){
        super(4);
    }

    public Boolean isMatching(int cardNumberCount) {
        if (cardNumberCount == 3) {
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
        return "Three of kind";
    }
}
