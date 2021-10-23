import java.lang.reflect.Array;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Loader
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        //Task #2
        String text = "Вася заработал 5000 рублей, Петя - 1000 рубля, а Маша - 33443 рублей";
        int sum = 0;
        Pattern p = Pattern.compile("\\d+");
        Matcher matcher = p.matcher(text);
        while (matcher.find()) {
            sum += Integer.parseInt(text.substring(matcher.start(), matcher.end()));
        }
        System.out.println(text + "\nСумма зарплат составляет: " + sum);
        System.out.println("=======================\n");

        //Task #1
        for (char c='A'; c <= 'z'; c++)
        {
            if (Character.isLetter(c)) {
                System.out.print(c + " " + (int) c + "\t");
            }
        }
        System.out.println("\n=======================\n");

        //Task #3
        System.out.print("Введите ФИО: ");
        String fullName = scanner.nextLine();
        fullName = fullName.trim();
        String[] name = fullName.split("\\s+");
        System.out.println("Фамилия: " + (!name[0].isEmpty() ? name[0] : "Фамилии нет"));
        System.out.println("Имя: " + (name.length >= 2 ? name[1] : "Имени нет"));
        System.out.println("Отчество: " + (name.length >= 3 ? name[2] : "Отчества нет"));
        System.out.println("=======================\n");

        //Task #*
        //Номер кредитной карты <4008 1234 5678> 8912
        System.out.println("Введите текст. Конфиденциальная информацию нужно обособить в <>");
        String orignText = scanner.nextLine();
        System.out.println("Введите на что заменить конфиденциальнаю информацию.");
        String placeholder = scanner.nextLine();
        String safe = searchAndReplaceDiamonds(orignText,placeholder);
        System.out.println(safe);
        System.out.println("=======================\n");

        //Возьмите английский текст (не менее 100 слов) и напишите программу,
        //которая будет разбивать его на слова и печатать слова в консоли. Знаки препинания не являются частью слова.

        System.out.println("Введите текст...");
        String article = scanner.nextLine();
        String[] words = article.split("\\W+");
        for (String item: words)
        {
            System.out.println(item);
        }
        System.out.println("=======================\n");

        /*Реализуйте  удаление лишних символов при вводе номера телефона в консоли и проверку соответствия номера формату мобильных номеров России.
         Если введённую строку нельзя привести к формату мобильного номера — выводите сообщение о неверном вводе.
         Телефон может быть введен не только в формате 79091234567, но и с лишними символами. */
        System.out.println("Введите номер телефона в любом формате:");
        String phoneNumber = scanner.nextLine();
        phoneNumber = phoneNumber.replaceAll("[^0-9]","");
        if (phoneNumber.length() == 11 && (phoneNumber.charAt(0) == '7' || phoneNumber.charAt(0) == '8') )
        {
            if (phoneNumber.charAt(0) == '8'){ phoneNumber = '7' + phoneNumber.substring(1);}
            System.out.println("Сохраненный номер: " + phoneNumber);
        }else{
            System.out.println("Неверный формат номера!");
        }

    }
    private static String  searchAndReplaceDiamonds(String text, String placeholder)
    {
        Pattern pattern = Pattern.compile("<.+>");
        Matcher matcher = pattern.matcher(text);
        String safe = matcher.replaceAll(placeholder);
        return safe;
    }

    private static boolean isDigit(String s) throws NumberFormatException {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}