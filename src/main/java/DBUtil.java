import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtil {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/Words?useSSL=false&characterEncoding=utf8";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "coderslab";

    public static void main(String[] args) throws SQLException {}

    public static Connection connect() throws SQLException{
        return DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);
    }

    // method to display table
    public static void printData(Connection conn, String query, String... columnNames) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                for (String param : columnNames) {
                    System.out.println(resultSet.getString(param));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List <String> DataArray(Connection conn, String query, String... columnNames) throws SQLException {
        List<String> CategoryList = new ArrayList<>();
        try (PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery();) {

            while (resultSet.next()) {
                    CategoryList.add(resultSet.getString(1));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CategoryList;
    }

    public static List<String> getWords(Connection conn, String query, String name, String... columnNames) throws SQLException {
        List<String> wordsFromCat = new ArrayList<>();
        try (PreparedStatement statement = conn.prepareStatement(query)){
             statement.setString(1,name);
             ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                wordsFromCat.add(resultSet.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wordsFromCat;
    };

    public static String getHint(Connection conn, String query, String name, String... columnNames) throws SQLException {
        String hint = null;
        try (PreparedStatement statement = conn.prepareStatement(query)){
            statement.setString(1,name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                hint = resultSet.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hint;
    };

}