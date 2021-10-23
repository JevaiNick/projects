import java.sql.*;

public class Main {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/skillbox?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String pass = "testtest";

        try {
            Connection connection = DriverManager.getConnection(url, user, pass);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select course_name,count(*)/(max(month(subscription_date))-min(month(subscription_date)) + 1) as per_by_period from purchaselist group by course_name;");

            while (resultSet.next()) {
                String result = resultSet.getString("course_name") + " : " + resultSet.getString("per_by_period");

                System.out.println(result);
            }
            connection.close();
            statement.close();
            resultSet.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
