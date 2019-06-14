package patterns;

import datahander.CommonConstants;

import java.util.List;

public class RoyalFlushPattern extends AbstractPattern {

    public RoyalFlushPattern() {
        super(10);
    }
    private List<String> cardNumbers;


    @Override
    public Boolean isMatching(int cardNumberCount) {
        return null;
    }

    @Override
    public Boolean isMatching(List<String> cardNumbers) {
        if(!cardNumbers.contains("A")){
            return false;
        }
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
        return "Royal flush";
    }

    public void setCardNumbers(List<String> cardNumbers){
        this.cardNumbers=cardNumbers;
    }
}
