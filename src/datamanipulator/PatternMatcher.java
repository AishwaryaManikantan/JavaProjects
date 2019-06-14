package datamanipulator;

import datahander.CommonConstants;
import datahander.HandDetails;
import model.Match;
import patterns.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PatternMatcher {
    private List<AbstractPattern> numberPatterns=new ArrayList<>();
    private List<AbstractPattern> suitPatterns=new ArrayList<>();
    public PatternMatcher(){
    numberPatterns.add(new PairPattern());
    numberPatterns.add(new ThreeOfKindPattern());
    numberPatterns.add(new FourOfKindPattern());

    suitPatterns.add(new RoyalFlushPattern());// order matters
    suitPatterns.add(new StraightFlushPattern());
    suitPatterns.add(new FlushPattern());//should be placed last -> if all cards are of same suit, then by default its flush

    }
    /*
 to identify list of patterns identified for given card combinations in the hand
     */
    public void calculateMatchingPatterns(HandDetails handDetails){
        List<String> highCards=new ArrayList<>();
        List<Match> matchingPattern=new ArrayList<>();

        if(handDetails.hasSameSuite()) {
            for (AbstractPattern suitPattern : suitPatterns) {
                List<String> cards=new ArrayList<>();
                cards.addAll(handDetails.getCardNumberCountMap().keySet());
                if (suitPattern.isMatching(cards)) {
                    int weight=suitPattern.calculateWeight(handDetails.getCardNumberCountMap().keySet());
                    matchingPattern.add(new Match(suitPattern,weight));
                    break; //only one suitePattern can match
                }
            }
        }

        if(matchingPattern.isEmpty()) {
            handDetails.getCardNumberCountMap().forEach((key,value)-> {
                if (value == 1) {//high card pattern
                    highCards.add(key);
                }else {
                    for (AbstractPattern pattern : numberPatterns) {
                        if (pattern.isMatching(value)) { //other than HighCards
                            int weight = pattern.calculateWeight(CommonConstants.cardNumbers.indexOf(key), value);
                            matchingPattern.add(new Match(pattern, weight));
                            break;
                        }
                    }
                }
            });
        }
        if(highCards.size()==handDetails.getCardNumberCountMap().size()){ //all cards having 1 count
            //can possibly be StraightPattern
            AbstractPattern straightPattern=new StraightPattern();
            List<String> cards=new ArrayList<>();
            cards.addAll(handDetails.getCardNumberCountMap().keySet());
            if(straightPattern.isMatching(cards)){
                int weight=straightPattern.calculateWeight(handDetails.getCardNumberCountMap().keySet());
                matchingPattern.add(new Match(straightPattern,weight));
                highCards.clear();
            }
        }
        handDetails.setPatternsMatched(matchingPattern);
        handDetails.setHighCards(highCards);
        if(matchingPattern.isEmpty()){
            Logger.getLogger(PatternMatcher.class.getName()).info("Only high cards present");
        }
    }

}
