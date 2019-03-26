import com.sun.security.ntlm.Server;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.Date;

public class Main extends Application
{

    private static final String URL = "jdbc:derby:Banco";
    private static final String USERNAME = "rodrigo";
    private static final String PASSWORD = "root";

    private int clients = 0;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("GUIServidor.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

    }

    public static void main(String[] args)
    {

        final String DATABASE_URL = "jdbc:derby:Banco";
        final String SELECT_QUERY =
                "SELECT * from Transacciones";

        try(
                Connection connection = DriverManager.getConnection(
                        URL, USERNAME, PASSWORD);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SELECT_QUERY))
        {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();


            // display the names of the columns in the ResultSet
            for (int i = 1; i <= numberOfColumns; i++)
                System.out.printf("%-8s\t", metaData.getColumnName(i));
            System.out.println();

            // display query results
            while (resultSet.next())
            {
                for (int i = 1; i <= numberOfColumns; i++)
                    System.out.printf("%-8s\t", resultSet.getObject(i));
                System.out.println();
            }
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        launch(args);
    }
}
