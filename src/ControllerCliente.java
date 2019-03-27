import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
//import org.apache.derby.client.am.Decimal;

import javax.swing.text.TabableView;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerCliente implements Initializable
{

    @FXML
    private Button ConnectButton;
    @FXML
    private Button Disconnect;
    @FXML
    private Button TransactionButton;
    @FXML
    private Button WithdrawButton;
    @FXML
    private Button DepositButton;
    @FXML
    private TextField Cedula;
    @FXML
    private TextField SaldoText;
    @FXML
    private TextField valorAction;


    //Table
    @FXML
    private TableView<Transaccion> transaccionTableView;
    @FXML
    private TableColumn<Transaccion,Integer> transaccion_id_column;
    @FXML
    private TableColumn<Transaccion,Integer> transaccion_cuenta_id_column;
    @FXML
    private TableColumn<Transaccion,String> transaccion_tipo_column;
    @FXML
    private TableColumn<Transaccion,BigDecimal> transaccion_valor_column;
    @FXML
    private TableColumn<Transaccion,String> transaccion_fecha_column;


    private String host = "localhost";
    private DataInputStream fromServer;
    private DataOutputStream toServer;
    private ObjectInputStream fromServerObject;
    private Boolean isConnected = false;
    private Integer thisCliente = 0;



    public ControllerCliente()
    {

    }

    @FXML
    public void connectToServer(){
        isConnected = true;
        ConnectButton.setDisable(true);
        Cedula.setDisable(true);
        String clienteID = Cedula.getText();
        thisCliente = Integer.parseInt(clienteID);
        valorAction.setDisable(false);
        DepositButton.setDisable(false);
        WithdrawButton.setDisable(false);
        Disconnect.setDisable(false);



        try
        {
            // Create a socket to connect to the server
            Socket socket = new Socket(host, 8080);

            // Create an input stream to receive data from the server
            fromServer = new DataInputStream(socket.getInputStream());

            // Create an output stream to send data to the server
            toServer = new DataOutputStream(socket.getOutputStream());

            fromServerObject = new ObjectInputStream(socket.getInputStream());
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
                List<Transaccion> currTransacciones = (List<Transaccion>) fromServerObject.readObject();
                populateTransaccionTable(currTransacciones);




            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }).start();

    }



    @FXML
    public void withdrawFunds () throws Exception{

        Double valortoAction = new Double(valorAction.getText());

        toServer.writeUTF("W");
        toServer.writeInt(thisCliente);
        toServer.writeDouble(valortoAction);
        Double saldoTemp = fromServer.readDouble();
        SaldoText.setText(String.valueOf(saldoTemp));
        List<Transaccion> currTransacciones = (List<Transaccion>) fromServerObject.readObject();
        populateTransaccionTable(currTransacciones);

    }

    @FXML
    public void depositFunds() throws Exception
    {
        Double valortoAction = new Double(valorAction.getText());

        toServer.writeUTF("D");
        toServer.writeInt(thisCliente);
        toServer.writeDouble(valortoAction);
        Double saldoTemp = fromServer.readDouble();
        SaldoText.setText(String.valueOf(saldoTemp));
        List<Transaccion> currTransacciones = (List<Transaccion>) fromServerObject.readObject();
        populateTransaccionTable(currTransacciones);
    }

    public void populateTransaccionTable(List<Transaccion> currTransacciones){
        //Populate Transacciones Table
        ObservableList<Transaccion> cTransaccion = FXCollections.observableArrayList(currTransacciones);
        transaccion_id_column.setCellValueFactory(new PropertyValueFactory<Transaccion,Integer>("transaccion_id"));
        transaccion_cuenta_id_column.setCellValueFactory(new PropertyValueFactory<Transaccion,Integer>("cuenta_id"));
        transaccion_tipo_column.setCellValueFactory(new PropertyValueFactory<Transaccion,String>("tipo"));
        transaccion_valor_column.setCellValueFactory(new PropertyValueFactory<Transaccion,BigDecimal>("valor"));
        transaccion_fecha_column.setCellValueFactory(new PropertyValueFactory<Transaccion,String>("fecha"));
        transaccionTableView.setItems(cTransaccion);
    }

    //Disconnect client
    @FXML
    public void disconnect() throws Exception
    {
        toServer.writeUTF("Disconnect");
        toServer.writeInt(thisCliente);
        ConnectButton.setDisable(false);
        Cedula.setDisable(false);
        transaccionTableView.setItems(null);
        SaldoText.setText("");
        valorAction.setDisable(true);
        Cedula.setText("");
        DepositButton.setDisable(true);
        WithdrawButton.setDisable(true);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        DepositButton.setDisable(true);
        WithdrawButton.setDisable(true);
        SaldoText.setDisable(true);
        valorAction.setDisable(true);
        Disconnect.setDisable(true);
    }
}
