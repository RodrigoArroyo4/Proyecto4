import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.text.TabableView;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.List;

public class ControllerCliente {

    @FXML
    private Button ConnectButton;
    @FXML
    private Button TransactionButton;
    @FXML
    private TextField Cedula;
    @FXML
    private TextField SaldoText;

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
        toServer.writeInt(200);

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

}
