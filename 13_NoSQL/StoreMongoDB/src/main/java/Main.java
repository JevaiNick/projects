import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates.*;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import static com.mongodb.client.model.Accumulators.*;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.lt;

public class Main {
    private static MongoClient mongoClient = new MongoClient("127.0.0.1", 27017);
    private static MongoDatabase database = mongoClient.getDatabase("local");
    private static MongoCollection<Document> storesCollection = database.getCollection("Stores");
    private static MongoCollection<Document> productsCollection = database.getCollection("Products");
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        //storesCollection.drop();
        //productsCollection.drop();
        System.out.println("Enter commands: Add_shop *ShopName*; Add_product *ProductName* *Cost*;\n" +
                "Exhibit_product *ProductName* *ShopName*; Statistics; Exit.");
        for (int i = 0;i==0;){
            String[] command = scanner.nextLine().split(" ");
            switch (command[0].toLowerCase()){
                case "add_shop": addStore(command[1]);break;
                case "add_product": addProduct(command[1],Integer.parseInt(command[2]));break;
                case "exhibit_product": insertProduct(command[1], command[2]); break;
                case "statistics": showStatistic(); break;
                case "exit":i++;break;
            }
        }
        System.out.println("GoodBye!");

    }

    private static void addStore(String storeName) {
        Document document = new Document()
                .append("name", storeName)
                .append("products", new ArrayList<>());
        storesCollection.insertOne(document);
        System.out.println("Done!");
    }

    private static void addProduct(String productName, Integer cost) {
        Document document = new Document()
                .append("name", productName)
                .append("cost", cost);
        productsCollection.insertOne(document);
        System.out.println("Done!");
    }

    private static void insertProduct(String productName, String storeName) {
        BsonDocument storeBSON = BsonDocument.parse("{name: \"" + storeName + "\" }");
        BsonDocument productionBSON = BsonDocument.parse("{name: \"" + productName + "\" }");
        if (storesCollection.countDocuments(storeBSON) > 0
                && productsCollection.countDocuments(productionBSON) > 0) {
            ArrayList<String> products = new ArrayList<>();
            storesCollection.find(storeBSON).forEach((Consumer<Document>) document -> {
                products.addAll((Collection<? extends String>) document.get("products"));
                products.add(productName);
                document.append("products", products);
                storesCollection.updateOne(new Document("name", document.get("name")),
                        new Document(Map.of("$set", new Document("products", products))));

            });
            System.out.println("Done!");
        }else   {
            System.out.println("Denied!. Shop or product doesn't exist!");
        }

    }

    private static void showStatistic() {
        //общее количество товаров
        List<Document> resultOfTotalCount = storesCollection.aggregate(Arrays.asList(lookup("Products", "products", "name", "productName"),
                unwind("$productName"), group("$name", sum("count", 1L))))
                .into(new ArrayList<>());
        resultOfTotalCount.forEach(document -> {
            System.out.println(document.get("_id") + " : " + document.get("count"));
        });
        System.out.println("=======================");
        //среднюю цену товара
        List<Document> resultOfAvgCost = storesCollection.aggregate(Arrays.asList(lookup("Products", "products", "name", "productList"),
                unwind("$productList"), group("$name",
                        avg("avg", "$productList.cost")))).into(new ArrayList<>());
        resultOfAvgCost.forEach(document -> {
            System.out.println(document.get("_id") + " : " + document.get("avg"));
        });
        System.out.println("=======================");

        //самый дорогой и самый дешевый товар
        List<Document> resultOfMinCost = storesCollection.aggregate(Arrays.asList(lookup("Products", "products", "name", "productList"),
                unwind("$productList"), group("$name", min("min", "$productList.cost")))).into(new ArrayList<>());
        System.out.println("Most cheapest one's: ");
        resultOfMinCost.forEach(document -> {
            System.out.println(document.get("_id") + " : " + document.get("min"));
        });
        List<Document> resultOfMaxCost = storesCollection.aggregate(Arrays.asList(lookup("Products", "products", "name", "productList"),
                unwind("$productList"), group("$name", max("max", "$productList.cost")))).into(new ArrayList<>());
        System.out.println("Most expensive one's: ");
        resultOfMaxCost.forEach(document -> {
            System.out.println(document.get("_id") + " : " + document.get("max"));
        });
        System.out.println("=======================");

        //количество товаров дешевле 100 рублей
        List<Document> resultOfLtHundred = storesCollection.aggregate(Arrays.asList(lookup("Products", "products", "name", "productsList"),
                unwind("$productsList"), match(lt("productsList.cost", 100L)), group("$name", sum("count", 1L))))
                .into(new ArrayList<>());
        resultOfLtHundred.forEach(document -> {
            System.out.println(document.get("_id") + " : " + document.get("count"));
        });
        System.out.println("=======================");

    }
}
