package Workers;

public class Operator implements Employee {
    private double salary;

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Operator(Company company) {
        setSalary(company.getOperatorDefaultSalary());
    }

    @Override
    public double getMonthSalary() {
        return getSalary();
    }
    @Override
    public void printSalary(){
        System.out.printf("%.2f%n", getMonthSalary());
    }

    @Override
    public double getRevenue() {
        return 0;
    }
}
