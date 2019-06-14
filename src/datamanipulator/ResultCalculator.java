package datamanipulator;

import datahander.CommonConstants;
import datahander.HandDetails;
import exception.PokerException;
import model.Match;
import patterns.MultiPattern;
import patterns.PairPattern;
import patterns.ThreeOfKindPattern;

import java.util.List;
import java.util.logging.Logger;

public class ResultCalculator {
    private HandDetails player1;
    private HandDetails player2;
    public ResultCalculator(HandDetails player1hand,HandDetails player2hand){
 this.player1=player1hand;
 this.player2=player2hand;
    }
    public Boolean player1Won() throws PokerException {
        if(player1.getPatternsMatched().size()==0 && player2.getPatternsMatched().size()==0){
            //both players got high cards only
            return player1WonByHighCard(player1.getHighCards(),player2.getHighCards());
        }else if(player1.getPatternsMatched().size()==0 && player2.getPatternsMatched().size()>0){
            //player1 got high cards only, so player 2 wins
            return false;
        }else if(player1.getPatternsMatched().size()>0 && player2.getPatternsMatched().size()==0){
            //player2 got high cards only, so player 1 wins
            return true;
        }
        Logger.getLogger(ResultCalculator.class.getName()).info("Finding Rank of player 1");
       int rankPlayer1=getRank(player1.getPatternsMatched());
        Logger.getLogger(ResultCalculator.class.getName()).info("Finding Rank of player 2");
       int rankPlayer2=getRank(player2.getPatternsMatched());
       if(rankPlayer1>rankPlayer2){
           return true;
       }else if(rankPlayer1<rankPlayer2){
           return false;
       }else{ //tie in Rank
           int weightPlayer1=getWeight(player1.getPatternsMatched());
           int weightPlayer2=getWeight(player2.getPatternsMatched());
           if( weightPlayer1 > weightPlayer2){
               return  true;
           }else if(weightPlayer1<weightPlayer2){
               return false;
           }else { //tie in weight also
               return player1WonByHighCard(player1.getHighCards(),player2.getHighCards());
           }
       }
    }

    private int getRank(List<Match> matches){
        if(matches.size()==1){
            Logger.getLogger(ResultCalculator.class.getName()).info("Matching pattern "+matches.get(0).getPattern().label() + " with rank "+matches.get(0).getPattern().getRank());
            return matches.get(0).getPattern().getRank();
        }else{
            //fullHouse (3+2)
            if((matches.get(0).getPattern() instanceof ThreeOfKindPattern && matches.get(1).getPattern() instanceof PairPattern)
                    || (matches.get(1).getPattern() instanceof ThreeOfKindPattern && matches.get(0).getPattern() instanceof PairPattern))
            {
                Logger.getLogger(ResultCalculator.class.getName()).info("Matching pattern "+MultiPattern.FULL_HOUSE.name() + " with rank "+MultiPattern.FULL_HOUSE.getRank());
                return MultiPattern.FULL_HOUSE.getRank();
            }else if((matches.get(0).getPattern() instanceof PairPattern && matches.get(1).getPattern() instanceof PairPattern)){
                Logger.getLogger(ResultCalculator.class.getName()).info("Matching pattern "+MultiPattern.TWO_PAIR.name()+" with rank "+MultiPattern.TWO_PAIR.getRank());
                return MultiPattern.TWO_PAIR.getRank(); //twoPari (2+2)
            }
        }
       return -1;
    }

    private int getWeight(List<Match> matches){
        return matches.stream().mapToInt(match->match.getWeight()).sum();
    }

    private Boolean player1WonByHighCard(List<String> highCardp1, List<String> highCardp2) throws PokerException{
        while(!highCardp1.isEmpty()&& !highCardp2.isEmpty()){
            int maxp1=findMax(highCardp1);
            int maxp2=findMax(highCardp2);
            if(maxp1>maxp2){
                return true;
            }else if(maxp1<maxp2){
                return false;
            }else{
                //remove equal high cards from list to be compared
               highCardp1.remove(CommonConstants.cardNumbers.get(maxp1));
               highCardp2.remove(CommonConstants.cardNumbers.get(maxp2));
            }
        }
        //tie in high cards too
        throw new PokerException("Player1 and Player2 input are same.");
    }
    private int findMax(List<String> cardStrs){
        return cardStrs.stream()
                .mapToInt(input -> CommonConstants.cardNumbers.indexOf(input))
                .max().getAsInt();
    }
}
