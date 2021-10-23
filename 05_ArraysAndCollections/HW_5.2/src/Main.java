import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> todoList =  new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите команду в консоль (Если хотите закончить работу введите exit):");
        String[] command;
        String task;
        do {
            command = scanner.nextLine().split("\\s+");
            switch (command[0].toLowerCase()) {
                case ("add"):
                    todoList = addTask(command, todoList);
                    break;
                case ("list"):
                    printList(todoList);
                    break;
                case ("delete"):
                    todoList = deleteTask(command, todoList);
                    break;
                case ("edit"):
                    todoList = editTask(command, todoList);
                    break;
                case ("exit"):
                    break;
                default:
                    System.out.println("Несуществующая команда! Попробуйте еще раз...");
            }
        }while(!command[0].equalsIgnoreCase("exit"));
    }

    private static void printList(ArrayList<String> list)
    {
        for (int i = 0; i < list.size(); i++)
        {
            System.out.println(i + " " + list.get(i));
        }
    }
    private static String makeTaskText(String[] text, int startIndex)
    {
        String taskText = "";
        for (int i = startIndex; i < text.length; i++)
        {
            taskText += text[i] + " ";
        }
        return  taskText;
    }
    private static boolean isDigit(String s) throws NumberFormatException {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private static ArrayList<String> addTask(String[] command, ArrayList<String> todoList)
    {
        if (isDigit(command[1]))
        {
            if (Integer.parseInt(command[1]) >= todoList.size()){
                todoList.add(makeTaskText(command,2));
            }else{
                todoList.add(Integer.parseInt(command[1]),makeTaskText(command,2));
            }
        }else{
            todoList.add(makeTaskText(command,1));
        }
        return todoList;
    }
    private static ArrayList<String> deleteTask(String[] command, ArrayList<String> todoList)
    {
        if (Integer.parseInt(command[1]) >= todoList.size()){
            todoList.remove(todoList.size() - 1);
        }else
        {
            todoList.remove(Integer.parseInt(command[1]));
        }
        return todoList;
    }
    public static ArrayList<String> editTask(String[] command, ArrayList<String> todoList)
    {
        todoList = deleteTask(command,todoList);
        todoList = addTask(command, todoList);
        return todoList;
    }
}
