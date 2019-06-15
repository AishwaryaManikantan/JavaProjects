package counter.repository;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class Cache {
    private String paragraph;
    private LinkedHashMap<String,Integer> wordCountMap=new LinkedHashMap<>();
    private LinkedHashMap<String,Integer> sortedWordCountMap=new LinkedHashMap<>();

    private void populateWordCountMap(String input,String delimiter){
        String[] words =input.split(delimiter);
        for (int i = 0; i < words.length; i++) {
            Pattern p = Pattern.compile("[^\\w]");
            Matcher m = p.matcher(words[i]);
            if(m.find())
            {
                words[i] = words[i].replaceAll("[^\\w]", ":");
                populateWordCountMap(words[i],":");
                continue;
            }
            if(!words[i].isEmpty()) {
              //  System.out.println("word is ["+words[i]+"]");
                if (wordCountMap.containsKey(words[i])) {
                    wordCountMap.put(words[i], wordCountMap.get(words[i]) + 1);
                } else {
                    wordCountMap.put(words[i], 1);
                }
            }
        }
    }
    private void populateMapEntrySetSortedByCount() {
        sortedWordCountMap = wordCountMap
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
    }
    public void updateParagraph(String paragraph){
        clearCache();
        this.paragraph=paragraph;
        populateWordCountMap(paragraph,"\\s+");
        populateMapEntrySetSortedByCount();
    }

    public int getCountOfWord(String word){
        if(!wordCountMap.containsKey(word)) return 0;
        return wordCountMap.get(word);
    }
    public Boolean containsParagraph(){
        if(paragraph!=null) return true;
        return false;
    }
    public HashMap<String,Integer> getTopOfSortedMapEntries(int limit){
        return sortedWordCountMap
                .entrySet()
                .stream()
                .limit(limit)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                LinkedHashMap::new));
    }

    private void clearCache(){
        this.paragraph=null;
        wordCountMap.clear();
        sortedWordCountMap.clear();
    }
}
