package counter.controller;

import counter.model.Count;
import counter.model.SearchTexts;
import counter.repository.Cache;
import counter.service.CountService;
import counter.util.CsvUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class CounterController {

    @Autowired
    private CountService countService;
    @Autowired
    private Cache cache;

    @PostMapping("/count-api/search")
    public String getCountOfWords(@RequestBody SearchTexts searchTexts,HttpServletResponse response) {
        String result=countService.getCountsInJson(searchTexts.getSearchText());
        if(result==null){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Please update the paragraph";
        }
        return result;
    }
    @PostMapping("/count-api/updateParagraph")
    public void updateParagraph(@RequestBody String paragraph,HttpServletResponse response) throws IOException {
        if(paragraph.trim().isEmpty()){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Paragraph cannot be empty");
            return;
        }
        cache.updateParagraph(paragraph);
    }

    @RequestMapping(value = "/count-api/top/{limit}", produces = "text/csv")
    public void getTop(@PathVariable Integer limit,HttpServletResponse response) throws IOException {
        if(limit<=0){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Limit cannot be 0 or less");
            return;
        }
        List<Count> counts=countService.getTopCounts(limit);
        if(counts==null){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Please update the paragraph");
            return;
        }
        CsvUtil.writeCountsToCsv(response.getWriter(),counts);
    }
}