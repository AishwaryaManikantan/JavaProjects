package unit;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import counter.Application;
import counter.SecurityConfig;

import counter.model.Count;
import counter.model.Counts;
import counter.util.CsvUtil;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@Import(SecurityConfig.class)
public class CounterControllerTest {


    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mvc;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @Before
    @Test
    public void updateParagraphTest() throws Exception {
        String uri = "/count-api/updateParagraph";
        String inputJson = "This is sample test para for test purpose.This is sample .sample-case 123.";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void getCountInCsvTest()
            throws Exception {

        String uri = "/count-api/top/2";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept("text/csv")).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);
        List<Count> counts=CsvUtil.getCountsFromCsv(content);
        assertTrue(counts.size()== 2);
        assertTrue(counts.get(0).getWord().equals("sample") && counts.get(0).getCount()==3);
        assertTrue(counts.get(1).getWord().equals("This") && counts.get(1).getCount()==2);
    }


    @Test
    public void searchTextsTest()
            throws Exception {

        String uri = "/count-api/search";

        String inputJson = "{\"searchText\":[\"is\",\"sample\"]}.";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Counts counts = mapFromJson(content,Counts.class);
        assertTrue(counts.getCounts().size()== 2);
        assertTrue(counts.getCounts().get("is").intValue()==2);
        assertTrue(counts.getCounts().get("sample").intValue()==3);
    }
    private String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
    private <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }
}
