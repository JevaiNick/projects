import Workers.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Company company1 = new Company(10000.0,50000.0,3000.0);
        hireSeveralOperators(180,company1);
        hireSeveralManagers(80, company1);
        hireSeveralTopManagers(10,company1);
        System.out.printf("Company income is %.2f\n", company1.getCompanyIncome());

        System.out.println("Самые высокие зарплаты: ");
        for (Employee employee : company1.getTopSalaryStaff(10)){
            employee.printSalary();
        }
        System.out.println("Самые низкие зарплаты: ");
        for (Employee employee : company1.getLowestSalaryStaff(30)){
            employee.printSalary();
        }

        for (int i = 0; i < company1.getCountOfWorkers(); i += 2){
            company1.fire(i);
        }

        System.out.println("Самые высокие зарплаты: ");
        for (Employee employee : company1.getTopSalaryStaff(10)){
            employee.printSalary();
        }
        System.out.println("Самые низкие зарплаты: ");
        for (Employee employee : company1.getLowestSalaryStaff(30)){
            employee.printSalary();
        }

    }
    private static void hireSeveralOperators(int count, Company company){
        List<Employee> staffArrayList = new ArrayList<>();
        for (int i = 0; i < count; i++){
            staffArrayList.add(new Operator(company));
        }
        company.hireAll(staffArrayList);
    }
    private static void hireSeveralManagers(int count, Company company){
        List<Employee> staffArrayList = new ArrayList<>();
        for (int i = 0; i < count; i++){
            staffArrayList.add(new Manager(company));
        }
        company.hireAll(staffArrayList);
    }
    private static void hireSeveralTopManagers(int count, Company company){
        for (int i = 0; i < count; i++){
            company.hire(new TopManager(company));
        }
    }

}
