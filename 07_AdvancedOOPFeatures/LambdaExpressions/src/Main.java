import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.*;
import java.util.stream.Stream;

public class Main
{
    private static String staffFile = "data/staff.txt";
    private static String dateFormat = "dd.MM.yyyy";

    public static void main(String[] args)
    {
        ArrayList<Employee> staff = loadStaffFromFile();
        Collections.sort(staff, (s1, s2) -> s1.getSalary().compareTo(s2.getSalary()) == 0 ? s1.getName().compareTo(s2.getName()) : s1.getSalary().compareTo(s2.getSalary()));

        System.out.println("Full list of employees: ");
        staff.forEach((item) -> System.out.printf("%s%n",item.toString()));

        Integer yearForSearch = 2017;
        System.out.printf("Employee with max salary that starting working in 2017: ");
        staff.stream()
                .filter(employee -> getYear(employee.getWorkStart()) == yearForSearch)
                .max(Comparator.comparing(Employee::getSalary))
                .ifPresent(System.out::println);
    }

    private static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    private static ArrayList<Employee> loadStaffFromFile()
    {
        ArrayList<Employee> staff = new ArrayList<>();
        try
        {
            List<String> lines = Files.readAllLines(Paths.get(staffFile));
            for(String line : lines)
            {
                String[] fragments = line.split("\t");
                if(fragments.length != 3) {
                    System.out.println("Wrong line: " + line);
                    continue;
                }
                staff.add(new Employee(
                    fragments[0],
                    Integer.parseInt(fragments[1]),
                    (new SimpleDateFormat(dateFormat)).parse(fragments[2])
                ));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return staff;
    }
}