import datahander.HandDetails;
import datamanipulator.PatternMatcher;
import datamanipulator.ResultCalculator;
import exception.PokerException;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
    public static void main(String args[]){


        //maintaining count of wins
        int player1WinCount=0,player2WinCount=0;

        //read stdIn until EOF

        //for each line, split strings by space and then divide by 2 to form two user inputs
        //in loop
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()) {
            //read line by line
            String gameInput=sc.nextLine();
            List<String> inputs= Arrays.asList(gameInput.split("\\s"));
            if(inputs.size()!=10){
                Logger.getLogger(Main.class.getName()).severe("Invalid input "+gameInput);
            }
            Logger.getLogger(Main.class.getName()).info("Game:"+gameInput);
            List<String> player1Input = inputs.subList(0,5);
            List<String> player2Input = inputs.subList(5,10);
            PatternMatcher patternMatcher = new PatternMatcher();

            Logger.getLogger(Main.class.getName()).info("Finding match for player1");
            HandDetails player1Hand = null;
            try {
                player1Hand = new HandDetails(player1Input);
            } catch (PokerException e) {
                Logger.getLogger(Main.class.getName()).severe(e.getMessage());
                continue;
            }
            patternMatcher.calculateMatchingPatterns(player1Hand);

            Logger.getLogger(Main.class.getName()).info("Finding match for player2");
            HandDetails player2Hand = null;
            try {
                player2Hand = new HandDetails(player2Input);
            } catch (PokerException e) {
                Logger.getLogger(Main.class.getName()).severe(e.getMessage());
                continue;
            }
            patternMatcher.calculateMatchingPatterns(player2Hand);
            try {
                ResultCalculator resultCalculator = new ResultCalculator(player1Hand, player2Hand);
                if (resultCalculator.player1Won()) {
                    player1WinCount++;
                    Logger.getLogger(Main.class.getName()).info("Game:"+gameInput+" - Player 1 won");
                } else {
                    player2WinCount++;
                    Logger.getLogger(Main.class.getName()).info("Game:"+gameInput+" - Player 2 won");
                }
            }
            catch (PokerException e){
                Logger.getLogger(Main.class.getName()).warning(e.getMessage());
            }
        }
        System.out.println("Player1 :"+ player1WinCount);
        System.out.println("Player2 :"+ player2WinCount);
    }
}
