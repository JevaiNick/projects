package Workers;

public class TopManager implements Employee {
    private double salary;

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public TopManager(Company company) {
        setSalary(company.getTopManagerPremia() + company.getTopManagerDefaultSalary());
    }

    public double getMonthSalary() {
        return getSalary();
    }
    public void printSalary(){
        System.out.printf("%.2f%n", getMonthSalary());
    }

    @Override
    public double getRevenue() {
        return 0;
    }
}
