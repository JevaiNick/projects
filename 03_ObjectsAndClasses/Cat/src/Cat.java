
public class Cat
{
    public static final int COUNT_EYES = 2;
    private double originWeight;
    private double weight;
    private double feedAmount;
    public static final double MIN_WEIGHT = 1000;
    public static final double MAX_WEIGHT = 9000;

    public void setFeedAmount(double feedAmount){
        this.feedAmount = feedAmount;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setOriginWeight(double originWeight) {
        this.originWeight = originWeight;
    }

    public double getOriginWeight() {
        return originWeight;
    }

    static int count = 0;

    private Colors colors;

    public void setColors(Colors colors)
    {
        this.colors = colors;
    }
    public Colors getColors()
    {
        return colors;
    }

    public static int getCount(){ return count; }
    public Cat()
    {
        weight = 1500.0 + 3000.0 * Math.random();
        originWeight = weight;
        feedAmount=0;
        count++;
    }
    public Cat (double weight)
    {
        this();
        this.weight = weight;
        originWeight = weight;
    }

    public void meow()
    {
        if (weight > MAX_WEIGHT && weight < MIN_WEIGHT){
            System.out.println("Питомец погиб!(");
        }else {
            weight = weight - 1;
            System.out.println("Meow");
            if (weight < MIN_WEIGHT) {
                count--;
                System.out.println("Питомец умер!");
            }
        }
    }

    public void feed(Double amount)
    {
        if (weight > MAX_WEIGHT && weight < MIN_WEIGHT){
            System.out.println("Питомец погиб!(");
        }else {
            weight = weight + amount;
            feedAmount += amount;
            if (weight > MAX_WEIGHT) {
                count--;
                System.out.println("Питомец взорвался!");
            }
        }

    }

    public void drink(Double amount)
    {
        if (weight > MAX_WEIGHT && weight < MIN_WEIGHT){
            System.out.println("Питомец погиб!(");
        }else {
            weight = weight + amount;
            if (weight > MAX_WEIGHT) {
                count--;
                System.out.println("Питомец взорвался!");
            }
        }

    }

    public double getFeedAmount(){ return  feedAmount;}

    public void pee()
    {
        if (weight > MAX_WEIGHT && weight < MIN_WEIGHT){
            System.out.println("Питомец погиб!(");
        }else {
            weight -= 10 * Math.random();
            System.out.println("Питомец стал легче!)");
            if (weight < MIN_WEIGHT) {
                count--;
                System.out.println("Питомец умер!");
            }
        }
    }

    public Double getWeight()
    {
        return weight;
    }

    public String getStatus()
    {
        if(weight < MIN_WEIGHT) {
            return "Dead";
        }
        else if(weight > MAX_WEIGHT) {
            return "Exploded";
        }
        else if(weight > originWeight) {
            return "Sleeping";
        }
        else {
            return "Playing";
        }
    }
}