package Workers;

public class Manager implements Employee {

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    private double salary;

    private final double BONUS = 0.05;
    private final double MAX_EARN = 140000.0;
    private final double MIN_EARN = 115000.0;

    public Manager(Company company) {
        setSalary(company.getManagerDefaultSalary() + (getRevenue() * BONUS));
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
        return MIN_EARN + (Math.random() * (MAX_EARN - MIN_EARN));
    }
}
