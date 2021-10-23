import com.skillbox.airport.Airport;

public class Main {
    public static void main(String[] args) {
        Airport airport = Airport.getInstance();
        System.out.println("Список самолетов: " + airport.getAllAircrafts());
        System.out.println("Количество самолетов: " + airport.getAllAircrafts().size());
    }
}
