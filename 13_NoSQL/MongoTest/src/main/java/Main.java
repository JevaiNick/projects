import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.Document;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;


public class Main {
    public static void main(String[] args) throws IOException {
        MongoClient mongoClient = new MongoClient("127.0.0.1", 27017);
        MongoDatabase database = mongoClient.getDatabase("local");
        MongoCollection<Document> collection = database.getCollection("TestSkillDemo");
        collection.drop();

        try (BufferedReader csvReader = new BufferedReader
                (new FileReader("C:\\Users\\Jevai\\IdeaProjects\\java_basics\\13_NoSQL\\MongoTest\\mongo.csv"))) {
            String row;
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",", 3);

                Document document = new Document()
                        .append("Name", data[0])
                        .append("age", Integer.parseInt(data[1]))
                        .append("courses", data[2]);

                collection.insertOne(document);
            }

        }
        //общее количество студентов в базе
        System.out.println(collection.countDocuments());
        System.out.println("=====================================================");
        //количество студентов старше 40 лет
        BsonDocument query = BsonDocument.parse("{age: {$gt: 40}}");
        AtomicInteger count = new AtomicInteger();
        collection.find(query).forEach((Consumer<Document>) document -> {
            count.getAndIncrement();
        });
        System.out.println(count.get());
        System.out.println("=====================================================");
        //имя самого молодого студента.
        query = BsonDocument.parse("{age: 1}");
        collection.find().sort(query).limit(1).forEach((Consumer<Document>) document -> {
            System.out.println(document.get("Name"));
            //System.out.println(" " + document.get("age"));
        });
        System.out.println("=====================================================");
        //список курсов самого старого студента
        query = BsonDocument.parse("{age: -1}");
        collection.find().sort(query).limit(1).forEach((Consumer<Document>) document -> {
            System.out.println(document.get("courses"));
        });
    }
}
