import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class Loader {
    private static SimpleDateFormat birthDayFormat = new SimpleDateFormat("yyyy.MM.dd");
    private static SimpleDateFormat visitDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

    private static HashMap<ByteBuffer, WorkTime> voteStationWorkTimes = new HashMap<>();
    private static HashMap<ByteBuffer, Integer> voterCounts = new HashMap<>();

    public static void main(String[] args) throws Exception {

        String fileName = "C:\\Users\\Jevai\\IdeaProjects\\java_basics\\14_Performance\\VoteAnalyzer\\res\\data-0.2M.xml";
        long usage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long start = System.currentTimeMillis();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLHandler handler = new XMLHandler(voteStationWorkTimes, voterCounts);
        parser.parse(new File(fileName), handler);//4598880
        //parseFile(fileName);//88673280
        DBConnection.executeMultiInsert();
        DBConnection.setIndexes();
        usage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - usage;
        insertCounts();
        System.out.println("Parsing to BD: " + (System.currentTimeMillis() - start));
        System.out.println("RAM:" + usage);


        //Printing results
        DBConnection.printVoterCounts();
        DBConnection.getConnection().close();
//        System.out.println("Voting station work times: ");
//
//        for(ByteBuffer votingStation : voteStationWorkTimes.keySet())
//        {
//            WorkTime workTime = voteStationWorkTimes.get(votingStation);
//            System.out.println("\t" + new String(votingStation.array(),"windows-1251") + " - " + workTime);
//        }
//
//        System.out.println("Duplicated voters: ");
//        for(ByteBuffer voter : voterCounts.keySet())
//        {
//            Integer count = voterCounts.get(voter);
//            if(count > 1) {
//                System.out.println("\t" + new String(voter.array(),"windows-1251") + " - " + count);
//            }
//        }
    }

    private static void insertCounts() throws UnsupportedEncodingException, SQLException {
        try {
            DBConnection.getConnection().setAutoCommit(false);
            for (ByteBuffer voter : voterCounts.keySet()) {
                Integer count = voterCounts.get(voter);
                String[] nameDate = new String(voter.array(), "windows-1251").split(";");
                if (count > 1) {
                    DBConnection.setCounts(nameDate[0], nameDate[1], count);
//                System.out.println("\t" + nameDate[0] + " - " + count);
                }
            }
            DBConnection.getConnection().commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /*private static void parseFile(String fileName) throws Exception
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new File(fileName));

        findEqualVoters(doc);
        fixWorkTimes(doc);
    }

    private static void findEqualVoters(Document doc) throws Exception
    {
        NodeList voters = doc.getElementsByTagName("voter");
        int votersCount = voters.getLength();
        for(int i = 0; i < votersCount; i++)
        {
            Node node = voters.item(i);
            NamedNodeMap attributes = node.getAttributes();

            String name = attributes.getNamedItem("name").getNodeValue();
            Date birthDay = birthDayFormat.parse(attributes.getNamedItem("birthDay").getNodeValue());

            Voter voter = new Voter(name, birthDay);
            Integer count = voterCounts.get(voter);
            voterCounts.put(voter, count == null ? 1 : count + 1);
        }
    }

    private static void fixWorkTimes(Document doc) throws Exception
    {
        NodeList visits = doc.getElementsByTagName("visit");
        int visitCount = visits.getLength();
        for(int i = 0; i < visitCount; i++)
        {
            Node node = visits.item(i);
            NamedNodeMap attributes = node.getAttributes();

            Integer station = Integer.parseInt(attributes.getNamedItem("station").getNodeValue());
            Date time = visitDateFormat.parse(attributes.getNamedItem("time").getNodeValue());
            WorkTime workTime = voteStationWorkTimes.get(station);
            if(workTime == null)
            {
                workTime = new WorkTime();
                voteStationWorkTimes.put(station, workTime);
            }
            workTime.addVisitTime(time.getTime());
        }
    }*/
}