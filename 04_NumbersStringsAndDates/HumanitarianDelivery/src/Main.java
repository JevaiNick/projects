import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter amount of boxes...");
        int amountOfBoxes = scanner.nextInt();
        int amountOfContainers = (int) Math.ceil( (double) amountOfBoxes / Constants.MAX_BOX_IN_CONTAINER);
        int amontOfTrucks = (int) Math.ceil( (double) amountOfContainers / Constants.MAX_CONTAINER_IN_TRUCK);
        int numberOfCurrentBox = 1;
        int numberOfCurrentContainer = 1;
        for (int i = 1; i <= amontOfTrucks; i++)
        {
            System.out.println("Truck " + i);
            for (int j = 1; j <= Constants.MAX_CONTAINER_IN_TRUCK; j++)
            {
                if (numberOfCurrentContainer <= amountOfContainers){
                    System.out.println("\tContainer " + numberOfCurrentContainer);
                    numberOfCurrentContainer++;
                    for (int k = 1; k <= Constants.MAX_BOX_IN_CONTAINER; k++)
                    {
                        if (numberOfCurrentBox <= amountOfBoxes){
                            System.out.println("\t\tBox " + numberOfCurrentBox);
                            numberOfCurrentBox++;
                        }else {break;}
                    }

                }else {break;}  
            }
        }
        System.out.println("Necessary:");
        System.out.println("Trucks - " + amontOfTrucks);
        System.out.println("Containers - " + amountOfContainers);
    }
}
