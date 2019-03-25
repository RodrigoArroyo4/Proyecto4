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

        /*
        Maybe rename this class? to MultiThreadServer As it will be handling server threads.

        This class will create the server socket,
        handle clients (one thread per client).

        Extra class must be defined that will "handleAclient", meaning it will do what the client asks to do
        transaction wise probably

        */

        //Main thread that will handle the opening and listening of new sockets
        new Thread( ()-> {
            try {
                //Start Server with desired port on the socket
                ServerSocket serverSocket = new ServerSocket(8080);

                //Maybe add info to pop up in terminal that server has been created
                System.out.println("Server Started: at " + new Date() + "\n");

                //runLater commands can be avoided unless required for th GUI

                //loop to maintain server up and running

                while (true) {
                    //accept a new socket connection
                    Socket socket = serverSocket.accept();

                    //count Clients
                    clients++;

                    //maybe terminal command to show a new connection was made

                    System.out.println("Starting thread for client " + clients +
                            " at " + new Date() + '\n');


                    //main thread handler
                    new Thread(new HandleAclient(socket)).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }


    //Class to handle everything done by server when it gets a client
    class HandleAclient implements Runnable{
        private Socket socket;

        public HandleAclient(Socket socket){
            this.socket = socket;
        }

        //thread to be run
        public void run(){
            try {
                //input/output handler might have to change to objectInput...
                DataInputStream inputFromClient = new DataInputStream(
                        socket.getInputStream());
                DataOutputStream outputToClient = new DataOutputStream(
                        socket.getOutputStream());

                //servicing the client
                while(true){
                    //run the query that is desired here weather its Withdraw... etc. with inputFromClient


                    //send confirmation of succesful/unsuccesful transaction, using outputToClient




                    //show results in GUI with Platform.runLater()...


                }



            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }


    public static void main(String[] args)
    {

        final String DATABASE_URL = "jdbc:derby:BancoAA";
        final String SELECT_QUERY =
                "SELECT * from cliente";

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
