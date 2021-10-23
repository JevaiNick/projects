public class Main
{
    public static void main(String[] args)
    {
        Container container = new Container();
        container.count += 7843;
        Integer number = 12345;
        System.out.println("Sum of digits a the number " + number + " is " + sumDigits(number));
        number = 10;
        System.out.println("Sum of digits a the number " + number + " is " + sumDigits(number));
        number = 5059191;
        System.out.println("Sum of digits a the number " + number + " is " + sumDigits(number));
    }

    public static Integer sumDigits(Integer number)
    {
        String str = number.toString();
        Integer sum = 0;
        for (int i = 0; i < str.length(); i++)
        {
//            sum +=  Integer.parseInt(String.valueOf(str.charAt(i)));
            sum += Character.getNumericValue(str.charAt(i));
        }
        return sum;
    }
}
