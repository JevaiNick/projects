import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;



public class XMLHandler extends DefaultHandler {
    public static final Integer COUNTFORINSERT = 70000;
    private static SimpleDateFormat birthDayFormat = new SimpleDateFormat("yyyy.MM.dd");
    private static SimpleDateFormat visitDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    private HashMap<ByteBuffer, WorkTime> voteStationWorkTimes;
    private HashMap<ByteBuffer, Integer> voterCounts;
    private static long count = 0;
    //    Voter voter;
    private static boolean voterCheck = false;

    public XMLHandler(HashMap<ByteBuffer, WorkTime> voteStationWorkTimes, HashMap<ByteBuffer, Integer> voterCounts) {
        this.voteStationWorkTimes = voteStationWorkTimes;
        this.voterCounts = voterCounts;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        ByteBuffer buffer = ByteBuffer.allocate(1);
        try {

            if (qName.equals("voter")) {
                voterCheck = true;
                String name = attributes.getValue("name");
                String birthDate = attributes.getValue("birthDay");
                DBConnection.countVoter(name, birthDate);
                Date birthDay = birthDayFormat.parse(attributes.getValue("birthDay"));

//                voter = new Voter(name, birthDay);
                buffer = ByteBuffer.wrap((attributes.getValue("name") + ";" + attributes.getValue("birthDay")).getBytes("windows-1251"));
                Integer count = voterCounts.get(buffer);
                voterCounts.put(buffer, count == null ? 1 : count + 1);
            } else if (qName.equals("visit") && voterCheck) {
                buffer = ByteBuffer.wrap(attributes.getValue("station").getBytes("windows-1251"));
                Date time = visitDateFormat.parse(attributes.getValue("time"));
                WorkTime workTime = voteStationWorkTimes.get(buffer);
                if (workTime == null) {
                    workTime = new WorkTime();
                    voteStationWorkTimes.put(buffer, workTime);
                }
                workTime.addVisitTime(time.getTime());
            }

        } catch (ParseException | UnsupportedEncodingException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("voter")) {
//            voter = null;
            voterCheck = false;
            count++;
            if (count > COUNTFORINSERT) {
                count = 0;
                try {
                    DBConnection.executeMultiInsert();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
}
