package patterns;

import datahander.CommonConstants;

import java.util.List;

public class StraightFlushPattern extends AbstractPattern {

    public StraightFlushPattern() {
        super(9);
    }
    private List<String> cardNumbers;

    @Override
    public Boolean isMatching(int cardNumberCount) {
        return null;
    }

    @Override
    public Boolean isMatching(List<String> cardNumbers) {
        for(int i=0;i<cardNumbers.size()-1;i++) {
            int cardIndexCurrent = CommonConstants.cardNumbers.indexOf(cardNumbers.get(i));
            int cardIndexNext = CommonConstants.cardNumbers.indexOf(cardNumbers.get(i+1));
            if(cardIndexCurrent+1!=cardIndexNext){
                return false;
            }
        }
        return true;
    }

    @Override
    public String label() {
        return "Straight flush";
    }
}
