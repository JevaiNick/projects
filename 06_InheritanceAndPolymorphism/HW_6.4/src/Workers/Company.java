package Workers;

import java.lang.reflect.Array;
import java.util.*;

public class Company {
    private final double BONUS = 150.0;
    private final double COMPANY_INCOME_FOR_BONUS = 10000000.0;

    boolean isIncomeOverPlan(){
        return (getIncome() > COMPANY_INCOME_FOR_BONUS);
    }

    double getTopManagerPremia(){
        return (isIncomeOverPlan())? topManagerDefaultSalary * BONUS : 0.0;
    }
    private Double managerDefaultSalary;
    private Double topManagerDefaultSalary;
    private Double operatorDefaultSalary;


    public double getIncome() {
        double result = 0;
        for (int i = 0; i < employees.size(); i ++) {
            result += employees.get(i).getRevenue();
        }
        return result;
    }



    public Double getManagerDefaultSalary() {
        return managerDefaultSalary;
    }

    public void setManagerDefaultSalary(Double managerDefaultSalary) {
        this.managerDefaultSalary = managerDefaultSalary;
    }

    public Double getTopManagerDefaultSalary() {
        return topManagerDefaultSalary;
    }

    public void setTopManagerDefaultSalary(Double topManagerDefaultSalary) {
        this.topManagerDefaultSalary = topManagerDefaultSalary;
    }

    public Double getOperatorDefaultSalary() {
        return operatorDefaultSalary;
    }

    public void setOperatorDefaultSalary(Double operatorDefaultSalary) {
        this.operatorDefaultSalary = operatorDefaultSalary;
    }


    public Company(Double managerDefaultSalary, Double topManagerDefaultSalary, Double operatorDefaultSalary){
        this.managerDefaultSalary = managerDefaultSalary;
        this.topManagerDefaultSalary = topManagerDefaultSalary;
        this.operatorDefaultSalary = operatorDefaultSalary;
    }
    private List<Employee> employees = new ArrayList<>();

    public int getCountOfWorkers(){
        return employees.size();
    }

    public void hire(Employee employee){
        employees.add(employee);
    }

    public void hireAll(List<Employee> employeeList){
        employees.addAll(employeeList);
    }

    public void fire(int index){
        employees.remove(index);

    }

    public List<Employee> getTopSalaryStaff(int count){
        if (count <= 0) {
            System.out.println("Неверный количество элементов списка." +
                    "\n Установленно значение длины списка по умолчанию: 1");
            count = 1;
        }
        if (count > employees.size()){
            System.out.println("Введенно значение превышающее максимальное." +
            "\n Установленно максимальное значение");
            count = employees.size();
        }
        employees.sort(new SalaryComparator());
        return employees.subList(0,count - 1);
    }

    public List<Employee> getLowestSalaryStaff(int count){
        if (count <= 0) {
            System.out.println("Неверный количество элементов списка." +
                    "\n Установленно значение длины списка по умолчанию: 1");
            count = 1;
        }
        if (count > employees.size()){
            System.out.println("Введенно значение превышающее максимальное." +
                    "\n Установленно максимальное значение");
            count = employees.size();
        }

        employees.sort(new SalaryComparator().reversed());
        return employees.subList(0,count - 1);
    }
}
