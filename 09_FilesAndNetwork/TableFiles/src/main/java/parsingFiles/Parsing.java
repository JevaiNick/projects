package parsingFiles;

import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface Parsing {
    public  List<CSVRecord> parseFile(Path path) throws IOException;
}
