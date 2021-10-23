import com.sun.source.tree.Tree;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter name or number (To show whole numbers enter \"list\" or to finish enter \"exit\"):");
        String text;
        //В качестве ключа используем номер т.к. он уникален.
        //При этом у пользователя может быть несколько номеров,  но у номера несколько абонентов быть не может.
        TreeMap<Long, String> phoneBook = new TreeMap<>();
        for (;;){
            text = scanner.nextLine();
            if (text.equalsIgnoreCase("exit")){
                break;
            }
            if (text.equalsIgnoreCase("list")){
                printMap(phoneBook);
                continue;
            }
            if (!isName(text)){
                Long number = normalizationNumber(text);
                if (number == -1){
                    System.out.println("Wrong number format!");
                    continue;
                }
                if (phoneBook.containsKey(number)){
                    showInfoByNumber(number, phoneBook);
                }else{
                    addNewName(number, phoneBook);
                }
                continue;
            }else{
                String name = text;
                if (phoneBook.containsValue(name)){
                    showInfoByName(name, phoneBook);
                }else{
                    addNewNumber(name, phoneBook);
                }
                continue;
            }

        }
    }

    private static Long normalizationNumber(String phoneNumber){
        phoneNumber = phoneNumber.replaceAll("[^0-9]","");
        if (phoneNumber.length() == 11 && (phoneNumber.charAt(0) == '7' || phoneNumber.charAt(0) == '8') )
        {
            if (phoneNumber.charAt(0) == '8'){ phoneNumber = '7' + phoneNumber.substring(1);}
            return Long.parseLong(phoneNumber);
        }else{
            return (long) -1;
        }
    }
    private static void printMap(TreeMap<Long, String> map){
        for (Long key : map.keySet()){
            System.out.println(key + " => " + map.get(key));
        }
    }
    private static void showInfoByNumber(Long number ,TreeMap<Long, String> map){
        System.out.println(number + " => " + map.get(number));
    }
    private static void addNewName(Long number, TreeMap<Long, String> map){
        System.out.println("Enter name for new person:");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        if (name.trim().length() > 0){
            map.put(number, name);
        }else
        {
            System.out.print("Name is empty...");
            addNewName(number, map);
        }

    }
    private static void showInfoByName(String name, TreeMap<Long, String> map){
        System.out.print("This person has numbers: ");
        for (Long key : map.keySet()){
            if (map.get(key).equals(name)){
                System.out.print(key + "; ");
            }
        }
    }
    private static void addNewNumber(String name, TreeMap<Long, String> map) {
        System.out.println("Enter number for new person:");
        Scanner scanner = new Scanner(System.in);
        String numberStr = scanner.nextLine();
        Long number = normalizationNumber(numberStr.trim());
        if (number != -1) {
            map.put(number, name);
        } else {
            System.out.print("Wrong number format! ");
            System.out.println("Person not added!");
        }
    }
    private  static  boolean isName(String text){
        Pattern pattern = Pattern.compile("\\D+");
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }
}
