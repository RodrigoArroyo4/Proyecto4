import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.*;
import java.io.*;

import java.net.Socket;

public class MainCliente extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private String host = "localhost";
    private DataInputStream fromServer;
    private DataOutputStream toServer;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("GUICliente.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 482));
        primaryStage.show();

        //connectToServer();
    }

    private void connectToServer() {
        try
        {
            // Create a socket to connect to the server
            Socket socket = new Socket(host, 8080);

            // Create an input stream to receive data from the server
            fromServer = new DataInputStream(socket.getInputStream());

            // Create an output stream to send data to the server
            toServer = new DataOutputStream(socket.getOutputStream());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        // Control the game on a separate thread
        new Thread(() ->
        {
            try
            {
                toServer.writeInt(10);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }).start();
    }

}
