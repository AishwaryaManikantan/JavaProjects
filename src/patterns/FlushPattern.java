package patterns;

import java.util.List;

public class FlushPattern extends AbstractPattern {

    public FlushPattern() {
        super(6);
    }


    @Override
    public Boolean isMatching(int cardNumberCount) {
        return null;
    }

    @Override
    public Boolean isMatching(List<String> cardNumbers) {//cards in ascending order
      return true;
    }

    @Override
    public String label() {
        return "Flush";
    }
}
