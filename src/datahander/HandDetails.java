package datahander;

import exception.PokerException;
import model.Match;

import java.util.*;

public class HandDetails {

    private Map<String,Integer> cardNumberCountMap=new TreeMap<>(Comparator.comparingInt(card -> CommonConstants.cardNumbers.indexOf(card)));
    private Boolean hasSameSuite=null;
    private List<Match> patternsMatched=new ArrayList<>();
    private List<String> highCards=new ArrayList<>();


    public Map<String, Integer> getCardNumberCountMap() {
        return cardNumberCountMap;
    }


    public List<Match> getPatternsMatched() {
        return patternsMatched;
    }

    public void setPatternsMatched(List<Match> patternsMatched) {
        this.patternsMatched = patternsMatched;
    }

    public List<String> getHighCards() {
        return highCards;
    }

    public void setHighCards(List<String> highCards) {
        this.highCards = highCards;
    }

    public HandDetails(List<String> cardCombinations) throws PokerException {
        preprocess(cardCombinations);
    }

// to preprocess input card combinations and populate maps
    private void preprocess(List<String> cardCombinations) throws PokerException {
        char suit=cardCombinations.get(0).charAt(cardCombinations.get(0).length()-1);
        for (String card : cardCombinations) {
            if(!CommonConstants.cardSuits.contains(suit)){
                throw new PokerException("No such Suit present :"+suit);
            }
            String key = card.substring(0, card.length() - 1);
            if(!CommonConstants.cardNumbers.contains(key)){
                throw new PokerException("No such card number supported (case-sensitive) :"+key);
            }
            if (cardNumberCountMap.containsKey(key)) {
                cardNumberCountMap.put(key, cardNumberCountMap.get(key) + 1);
            } else {
                cardNumberCountMap.put(key, 1);
            }
            if(suit!=card.charAt(card.length()-1)){
                hasSameSuite=false;
            }
        }
        if(hasSameSuite==null){
            hasSameSuite=true;
        }
    }
    public Boolean hasSameSuite(){
        return hasSameSuite;
    }
}
