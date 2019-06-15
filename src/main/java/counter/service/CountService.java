package counter.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import counter.repository.Cache;
import counter.model.Counts;
import counter.model.Count;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CountService {
    @Autowired
    Cache cache;
    public String getCountsInJson(List<String> searchTexts){
        if(!cache.containsParagraph()){
            return null;
        }
        Counts counts=new Counts();
        for (String searchText:
             searchTexts) {
            counts.addWordCountToMap(searchText,cache.getCountOfWord(searchText));
        }
        Gson gson = new GsonBuilder().create();
        return gson.toJson(counts);
    }
    public List<Count> getTopCounts(int limit){
        if(!cache.containsParagraph() || limit==0){
            return null;
        }
        List<Count> counts=new ArrayList<>();
       HashMap<String,Integer> topCountsMap=cache.getTopOfSortedMapEntries(limit);
        for (Map.Entry entry:
                topCountsMap.entrySet()) {
            Count count=new Count();
            count.setWord((String)entry.getKey());
            count.setCount((Integer)entry.getValue());
            counts.add(count);
        }
       return counts;
    }


}
