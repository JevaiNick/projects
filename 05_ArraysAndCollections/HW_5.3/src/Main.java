import java.util.Scanner;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter command into the console (To finish enter \"exit\"):");
        String[] command;
        TreeSet<String> addresses = new TreeSet<>();
        EmailValidator emailValidator = new EmailValidator();
        do {
            command = scanner.nextLine().split("\\s+");
            switch (command[0].toLowerCase()) {
                case ("list"):
                    printTreeSet(addresses);
                    break;
                case ("add"):
                    if (emailValidator.validate(command[1])){
                        addresses.add(command[1]);
                    }else{
                        System.out.println("Invalid address.");
                    }
                case ("exit"):
                    break;
                default:
                    System.out.println("Wrong command! Try again...");
            }
        }while(!command[0].equalsIgnoreCase("exit"));
    }
    private static void printTreeSet(TreeSet<String> treeSet){
        for (String item : treeSet)
        {
            System.out.println(item);
        }
    }
}
