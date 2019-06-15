package counter.model;


import java.util.LinkedHashMap;

public class Counts {
    private final LinkedHashMap<String,Integer> counts=new LinkedHashMap<>();
    // to print in format {"counts": [{"Duis": 11}, {"Sed": 16}, {"Donec": 8}, {"Augue": 7}, {"Pellentesque": 6},
    //{"123": 0}]}

    public void addWordCountToMap(String word,int count){
        counts.put(word,count);
    }

    public LinkedHashMap<String, Integer> getCounts() {
        return counts;
    }
}
