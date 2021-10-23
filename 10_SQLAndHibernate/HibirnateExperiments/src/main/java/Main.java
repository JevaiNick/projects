import models.Course;
import models.LinkedPurchaseList;
import models.Purchases;
import models.Student;
import models.Result;
import models.keys.KeyLinkedPurchase;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.mapping.Array;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();


        try (SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
                Session session = sessionFactory.openSession();) {


            Transaction transaction = session.beginTransaction();
            String sql = "select new " + Result.class.getName() + "(s.id, c.id)"
                    + " from " + Purchases.class.getSimpleName() + " p "
                    + "inner join " + Student.class.getSimpleName() + " s on s.name = p.id.studentName "
                    + "inner join " + Course.class.getSimpleName() + " c on c.name = p.id.courseName "
                    + "order by s.name ";

            Query<Result> query = session.createQuery(sql);
            List<Result> results = query.list();
            for(int i = 0; i < results.size(); i ++) {
                System.out.println(results.get(i));
                LinkedPurchaseList obj = new LinkedPurchaseList();
                obj.setId(new KeyLinkedPurchase(results.get(i).studentId,results.get(i).courseId));
                session.save(obj);
            }
            transaction.commit();
        }
    }

    }
