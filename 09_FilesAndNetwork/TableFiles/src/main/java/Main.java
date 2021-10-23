import org.apache.commons.csv.CSVRecord;
import parsingFiles.MyCSVParser;
import parsingFiles.Parsing;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    static final Path PATH = Paths.get("E:\\SkillBox\\movementList.csv");
    public static void main(String[] args) {
        try {
            Parsing expr = MyCSVParser::getList;
            Double sumReplenishment = countingSumReplenishment(expr);
            Double sumConsumption = countingSumConsumption(expr);
            HashMap<String, Double> costsByOrganizations = countingcostsByOrganizations(expr);
            System.out.printf("Общая сумма расходов: %.2f%n"+
                    "Общая сумма приходов: %.2f%n%n",sumConsumption,sumReplenishment);
            System.out.println("Суммы расходов по организациям:");
            for (Map.Entry<String, Double> item : costsByOrganizations.entrySet()){
                System.out.printf("%s - %.2f%n", item.getKey(), item.getValue());
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public static HashMap<String, Double> countingcostsByOrganizations(Parsing parsing) throws IOException {
        List<CSVRecord> csvRecords = parsing.parseFile(PATH);
        HashMap<String, Double> costsByOrganizations = new HashMap<>();
        for (CSVRecord item: csvRecords) {
            String type = item.get(5).split("\\s+")[1];
            if (costsByOrganizations.containsKey(type)){
                costsByOrganizations.put(type, (costsByOrganizations.get(type) + Double.parseDouble(item.get(7).replace(',','.'))));
            }else {
                costsByOrganizations.put(type, (Double.parseDouble(item.get(7).replace(',','.'))));
            }
        }
        return costsByOrganizations;
    }
    private static Double countingSumReplenishment(Parsing parsing) throws IOException {
        List<CSVRecord> csvRecords = parsing.parseFile(PATH);
        Double sumReplenishment = 0.0;
        for (CSVRecord item: csvRecords){
            sumReplenishment += Double.parseDouble(item.get(6).replace(',','.'));
        }
        return sumReplenishment;
    }
    private static Double countingSumConsumption(Parsing parsing) throws IOException {
        List<CSVRecord> csvRecords = parsing.parseFile(PATH);
        Double sumConsumption = 0.0;
        for (CSVRecord item: csvRecords){
            sumConsumption += Double.parseDouble(item.get(7).replace(',','.'));
        }
        return sumConsumption;
    }

}
