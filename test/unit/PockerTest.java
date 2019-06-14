package unit;


import datahander.HandDetails;
import datamanipulator.PatternMatcher;
import datamanipulator.ResultCalculator;
import exception.PokerException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class PockerTest {
    @DataProvider(name = "GameInputs") //input and ifPlayer1Won?

    public static Object[][] credentials() {

        return new Object[][] { { "TH 2S AD 6H 7S 6H 7S 7H 2S 3H" ,false}, //all high cards (Vs) pair
                {"5H 2H 6H 3H 4H 6H 7S TH 2S 3H",true}, //straight flush (Vs) all high cards
                {"5H 2C 6H 3C 4H 6H 7S TH 2S 3H",true}, //straight (Vs) all high cards
                {"5H 8H 6H 3H 4H 6H 7S TH 2S 3H",true}, //flush (Vs) all high cards
                {"6H 7S TH 2S 3H JH KH AH TH QH",false},//all high cards (Vs) royal flush
                {"4H 4C 5H 5C 5D JH 6H AH TH QH",true}, //full house (Vs) flush
                {"4H 4C 5H 5C 3D JH 6H AH TH QH",false}};//two pair (Vs) flush

    }
    @Test(dataProvider = "GameInputs")
    public void testPair(String gameInput,Boolean ifPlayer1Won) throws PokerException {
        List<String> inputs= Arrays.asList(gameInput.split("\\s"));
        List<String> player1Input = inputs.subList(0,5);
        List<String> player2Input = inputs.subList(5,10);
        HandDetails player1=new HandDetails(player1Input);
        HandDetails player2=new HandDetails(player2Input);
        PatternMatcher patternMatcher=new PatternMatcher();
        patternMatcher.calculateMatchingPatterns(player1);
        patternMatcher.calculateMatchingPatterns(player2);
        ResultCalculator resultCalculator=new ResultCalculator(player1,player2);
        assertEquals(ifPlayer1Won,resultCalculator.player1Won());
    }
}
