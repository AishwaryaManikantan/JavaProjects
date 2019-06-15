package counter.util;



import com.opencsv.CSVWriter;
import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvException;
import counter.model.Count;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringReader;

import java.util.List;


public class CsvUtil {
        private static final Logger LOGGER = LoggerFactory.getLogger(CsvUtil.class);
        public static void writeCountsToCsv(PrintWriter writer,List<Count> counts ) {

            try {

                ColumnPositionMappingStrategy mapStrategy
                        = new ColumnPositionMappingStrategy();

                mapStrategy.setType(Count.class);

                String[] columns = new String[]{"word","count"};
                mapStrategy.setColumnMapping(columns);

                StatefulBeanToCsv btcsv = new StatefulBeanToCsvBuilder(writer)
                        .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                        .withMappingStrategy(mapStrategy)
                        .withSeparator('|')
                        .build();
                btcsv.write(counts);

            } catch (CsvException ex) {

                LOGGER.error("Error mapping Bean to CSV", ex);
            }
        }

        public static List<Count> getCountsFromCsv(String csv){
            ColumnPositionMappingStrategy strategy
                    = new ColumnPositionMappingStrategy();
            strategy.setType(Count.class);
            strategy.setColumnMapping("word","count");

            CsvToBean csvToBean = new CsvToBeanBuilder(new StringReader(csv))
                    .withType(Count.class)
                    .withMappingStrategy(strategy)
                    .withSeparator('|')
                    .build();

            List<Count> counts = csvToBean.parse();
            return counts;
        }
    }



