import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ControllerCliente {

    @FXML
    private Button ConnectButton;
    @FXML
    private Button TransactionButton;
    @FXML
    private TextField Cedula;
    @FXML
    private TextField SaldoText;


    private String host = "localhost";
    private DataInputStream fromServer;
    private DataOutputStream toServer;
    private Boolean isConnected = false;



    public ControllerCliente(){
    }

    @FXML
    public void connectToServer(){
        isConnected = true;
        ConnectButton.setDisable(true);
        Cedula.setDisable(true);
        String clienteID = Cedula.getText();


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
                toServer.writeUTF(clienteID);
                double saldoInicial = fromServer.readDouble();
                SaldoText.setText(Double.toString(saldoInicial));
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }).start();

    }


    @FXML
    public void withdrawFunds () throws Exception{
        toServer.writeInt(200);

    }

}
