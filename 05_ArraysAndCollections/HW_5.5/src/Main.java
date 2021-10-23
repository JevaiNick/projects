import java.util.*;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> beautifulNumbersList = new ArrayList<>();
        generateNumbers(beautifulNumbersList);
        Collections.sort(beautifulNumbersList);
        HashSet<String> beautifulNumbersHash = new HashSet<>();
        beautifulNumbersHash.addAll(beautifulNumbersList);
        TreeSet<String> beautifulNumbersTree = new TreeSet<>();
        beautifulNumbersTree.addAll(beautifulNumbersList);
        for (String item :
                beautifulNumbersList) {
            System.out.println(item);
        }
        System.out.println("Enter number like X[0-9][0-9][0-9]YZ[1-199]:");
        Scanner scanner = new Scanner(System.in);
        String numberToSearch = scanner.nextLine();

        //прямой перебор по ArrayList (самый долгий)
        directSearch(numberToSearch, beautifulNumbersList);
        //Бинарный поиск
        binarySearch(numberToSearch, beautifulNumbersList);
        //Поиск в HashSet (самый быстрый)
        hashSearch(numberToSearch, beautifulNumbersHash);
        //Поиск в TreeSet
        treeSearch(numberToSearch, beautifulNumbersTree);

    }
    private static void generateNumbers(ArrayList<String> list)
    {
        char x = 'A';
        char y = 'A';
        char z = 'A';
        int n = 1;
        int r = 1;

        for (x = 'A'; x <= 'Z'; x++){
            for (n = 1; n <= 9; n++){
                for (y = 'A'; y <= 'Z'; y++){
                    for (z = 'A'; z <= 'Z'; z++){
                        for (r = 1; r <= 199; r++){
                            String str = x + Integer.toString(n) + Integer.toString(n) + Integer.toString(n) + y + z + Integer.toString(r);
                            list.add(str);
                        }
                    }
                }
            }
        }

    }
    private static void directSearch(String numberToSearch ,ArrayList<String> list){
        long start = System.nanoTime();
        long duration;
        boolean flag = true;
        for (String item : list){
            if (numberToSearch.equals(item)){
                duration = System.nanoTime() - start;
                System.out.println("Поиск перебором: Номер найден. Поиск занял " + duration + "нс");
                flag = false;
                break;
            }
        }
        if (flag){
            duration = System.nanoTime() - start;
            System.out.println("Поиск перебором: Номер НЕ найден. Поиск занял " + duration + "нс");
        }
    }
    private static void binarySearch(String numberToSearch, ArrayList<String> list){
        long duration;
        long start = System.nanoTime();
        int searchResult = Collections.binarySearch(list,numberToSearch);
        duration = System.nanoTime() -  start;
        if (searchResult >= 0){
            System.out.println("Бинарный поиск: Номер найден. Поиск занял " + duration + "нс");
        }
        else
        {
            System.out.println("Бинарный поиск: Номер НЕ найден. Поиск занял " + duration + "нс");
        }
    }
    private static void hashSearch(String numberToSearch, HashSet<String> set){
        long duration;
        long start = System.nanoTime();
        if (set.contains(numberToSearch)){
            duration = System.nanoTime() - start;
            System.out.println("Поиск в HashSet: Номер найден. Поиск занял " + duration + "нс");
        }
        else{
           duration = System.nanoTime() -  start;
            System.out.println("Поиск в HashSet: Номер НЕ найден. Поиск занял " + duration + "нс");
        }
    }
    private static  void treeSearch(String numberToSearch, TreeSet<String> set){
        long duration;
        long start = System.nanoTime();
        if (set.contains(numberToSearch)){
            duration = System.nanoTime() - start;
            System.out.println("Поиск в TreeSet: Номер найден. Поиск занял " + duration + "нс");
        }
        else{
            duration = System.nanoTime() -  start;
            System.out.println("Поиск в TreeSet: Номер НЕ найден. Поиск занял " + duration + "нс");
        }
    }
}
