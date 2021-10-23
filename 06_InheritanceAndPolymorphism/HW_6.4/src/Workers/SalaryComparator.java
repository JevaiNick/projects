package Workers;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.EnumMap;

public class SalaryComparator implements Comparator<Employee> {

    @Override
    public int compare(Employee o1, Employee o2) {
        if (o1.getMonthSalary() > o2.getMonthSalary()){
            return -1;
        }
        if (o1.getMonthSalary() == o2.getMonthSalary()){
            return 0;
        }
        return 1;
    }
}
