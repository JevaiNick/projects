
public class Loader
{
    public static void main(String[] args)
    {
        /*int counter = 0;
        Cat sonya = new Cat();
        Cat vasya = new Cat();
        System.out.println("Состояние Сони: " + sonya.getStatus());
        System.out.println("Состояние Васи: " + vasya.getStatus());
        System.out.println("Количесвто кошек: " + Cat.count);
        while (!sonya.getStatus().equals("Exploded"))
        {
            sonya.feed(10*Math.random());
            sonya.drink(10*Math.random());
            System.out.println("Соня трапезничает...");
        }
        System.out.println("Соня лопнула при весе "+ sonya.getWeight());
        System.out.println("Количесвто кошек: " + Cat.count);
        while (!vasya.getStatus().equals("Dead"))
        {
            vasya.meow();
            counter++;
        }
        System.out.println("Вася замяукался после того как мяукнул " + counter + " раз((((");
        System.out.println("Количесвто кошек: " + Cat.count);
        Cat borka = new Cat();
        System.out.println("Количесвто кошек: " + Cat.count);
        borka.feed(150.00);
        for (int i = 0; i < 5; i++)
        {
            borka.pee();
        }
        System.out.println("Питомец съел " + borka.getFeedAmount() + "грамм корма!");*/

        /*System.out.println("Количесвто кошек: " + Cat.count);
        Cat myrka1 = getKitten();
        System.out.println("Количесвто кошек: " + Cat.count);
        Cat myrka2 = getKitten();
        Cat myrka3 = getKitten();
        System.out.println("Количесвто кошек: " + Cat.count);*/
        Cat sonya = new Cat();
        sonya.setColors(Colors.WHITE);
        Cat vasya = new Cat();
        vasya.setColors(Colors.BLACK);
        System.out.println("Sonya: " + sonya.getWeight() + "; " + sonya.getColors() + "; " + sonya.getOriginWeight() + ";");
        System.out.println("Vasya: " + vasya.getWeight() + "; " + vasya.getColors() + "; " + vasya.getOriginWeight() + ";");
        cloneCat(vasya,sonya);
        System.out.println("Cloning Vasya => Sonya.........");
        System.out.println("Sonya: " + sonya.getWeight() + "; " + sonya.getColors() + "; " + sonya.getOriginWeight() + ";");
        System.out.println("Vasya: " + vasya.getWeight() + "; " + vasya.getColors() + "; " + vasya.getOriginWeight() + ";");

    }
    private static Cat getKitten()
    {
        return new Cat(1100);
    }

    private  static  void cloneCat (Cat originaCat, Cat cloneCat)
    {
        cloneCat.setColors(originaCat.getColors());
        cloneCat.setFeedAmount(originaCat.getFeedAmount());
        cloneCat.setOriginWeight(originaCat.getOriginWeight());
        cloneCat.setWeight(originaCat.getWeight());
    }
}