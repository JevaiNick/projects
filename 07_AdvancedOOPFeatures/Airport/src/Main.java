import com.skillbox.airport.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Airport airport = Airport.getInstance();
        List<Terminal> terminals = airport.getTerminals();
        List<Flight> flights = terminals.stream().flatMap(terminal -> terminal.getFlights().stream()).collect(Collectors.toList());

        flights.stream()
                .filter(f -> f.getDate().getTime() >= getTime(0) && f.getDate().getTime() <= getTime(2))
                .forEach(System.out::println);
    }
    private static long getTime(int addHours){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR, addHours);
        return calendar.getTime().getTime();
    }

}
