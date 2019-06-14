Steps to generate build:
1) cd to /src/build (Which has build.xml)
2) command - ant -v
3) pocker.jar would be generated in /dist

Steps to run project:

way 1) Run java -jar pocker.jar and type in game inputs one by one. On each enter command, we would see result of game.
On ctr+D program terminates showing cumulative count of wins for each player.

way 2) giving file input using command
cat testInput.txt | java -jar pocker.jar
Logs of game progress and final result with win count would be printed.


Supports config changes with minimal code changes-> change priority of card by changing index of card,add new card, add new suit,etc in CommonConstants.java ;  change in rank of pattern, adding new pattern

Possible extension of code->
calculate result in parallel, move rank/cardNumber/cardSuit to config file for end user to change, change in user interface


