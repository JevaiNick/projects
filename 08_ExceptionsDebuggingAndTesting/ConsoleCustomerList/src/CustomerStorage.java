import java.io.IOException;
import java.util.HashMap;

public class CustomerStorage
{
    private HashMap<String, Customer> storage;

    public CustomerStorage()
    {
        storage = new HashMap<>();
    }

    public void addCustomer(String data)
    {
        EmailValidator emailValidator = new EmailValidator();
        String[] components = data.split("\\s+");
        if  (components.length != 4){
            throw new IllegalArgumentException("Wrong format. Correct format: "
                    + "add Василий Петров vasily.petrov@gmail.com +79215637722");
        }
        if (!isNormalNumber(components[3])){
            throw new IllegalArgumentException("Wrong format of phone number. Correct format: +79215637722");
        }
        if (!emailValidator.validate(components[2])){
            throw new IllegalArgumentException("Wrong e-mail format. Correct format: vasily.petrov@gmail.com");
        }
        String name = components[0] + " " + components[1];
        storage.put(name, new Customer(name, components[3], components[2]));
    }

    public void listCustomers()
    {
        storage.values().forEach(System.out::println);
    }

    public void removeCustomer(String name)
    {
        if (!storage.containsKey(name)){
            throw new IllegalArgumentException("Name does not exist!");
        }
        storage.remove(name);
    }

    public int getCount()
    {
        return storage.size();
    }
    private static boolean isNormalNumber(String phoneNumber)  {
        phoneNumber = phoneNumber.replaceAll("[^0-9]","");
        if (phoneNumber.length() == 11 && (phoneNumber.charAt(0) == '7' || phoneNumber.charAt(0) == '8') )
        {
            return true;
        }
        return false;
    }
}