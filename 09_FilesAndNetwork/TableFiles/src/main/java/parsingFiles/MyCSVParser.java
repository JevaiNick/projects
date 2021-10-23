package parsingFiles;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class MyCSVParser  {

    public static List<CSVRecord> getList(Path path) throws IOException {
        Reader reader = Files.newBufferedReader(path);
        org.apache.commons.csv.CSVParser csvParser = new org.apache.commons.csv.CSVParser(reader, CSVFormat.DEFAULT);
        List<CSVRecord> csvRecords = csvParser.getRecords();
        csvRecords.remove(0);
        return  csvRecords;
    }
}
