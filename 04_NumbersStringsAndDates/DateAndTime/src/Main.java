import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DateFormat dateFormat =  new SimpleDateFormat( "dd.MM.yyyy");
        System.out.println("Enter ur date of birth:");
        System.out.print("year:");
        int year = scanner.nextInt();
        System.out.print("month:");
        int month = scanner.nextInt();
        System.out.print("day:");
        int day = scanner.nextInt();
        Calendar calendar = new GregorianCalendar(year,month-1,day);
        for (int i = 0; i <= getCurrentYear() - year; i++ )
        {
            System.out.println(i + " - " + dateFormat.format(calendar.getTime()) + " - " + new SimpleDateFormat("EEE", Locale.ENGLISH).format(calendar.getTime()));
            calendar.roll(Calendar.YEAR, 1);
        }
    }
    public static int getCurrentYear()
    {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(java.util.Calendar.YEAR);
    }
}
