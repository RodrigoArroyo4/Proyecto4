import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.*;
import javafx.scene.control.cell.PropertyValueFactory;


public class Controller implements Initializable
{

    //Global socket variable holders
    private int clients = 0;

    //Class initializers for queries
    ClienteQueries clq = new ClienteQueries();
    CuentaQueries cq = new CuentaQueries();
    TransaccionQueries tq = new TransaccionQueries();

    public Controller(){}
    //Conections textArea
    @FXML
    private TextArea conexiones;

    //Tables
    @FXML
    private TableView<Cliente> clienteTable;
    @FXML
    private TableColumn<Cliente,Integer> cliente_id_column;
    @FXML
    private TableColumn<Cliente,Integer> nombre_cliente_column;

    @FXML
    private TableView<Cuenta> cuentasTable;
    @FXML
    private TableColumn<Cuenta,Integer> cuenta_id_column;
    @FXML
    private TableColumn<Cuenta,Integer> cliente_id_cuenta_column;
    @FXML
    private TableColumn<Cuenta,Double> saldo_column;

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



    @Override
    public void initialize(URL url, ResourceBundle rb){

        //socket Initializer
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
                    new Thread(new Controller.HandleAclient(socket)).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();





        //populate Clientes Table
        cliente_id_column.setCellValueFactory(new PropertyValueFactory<Cliente,Integer>("cliente_id"));
        nombre_cliente_column.setCellValueFactory(new PropertyValueFactory<Cliente,Integer>("nombre_cliente"));
        clienteTable.setItems(clq.getAllCliente());

        //Populate Cuentas Table
        cuenta_id_column.setCellValueFactory(new PropertyValueFactory<Cuenta,Integer>("cuenta_id"));
        cliente_id_cuenta_column.setCellValueFactory(new PropertyValueFactory<Cuenta,Integer>("cliente_id"));
        saldo_column.setCellValueFactory(new PropertyValueFactory<Cuenta,Double>("saldo"));
        cuentasTable.setItems(cq.getAllCuentas());

        //Populate Transacciones Table
        transaccion_id_column.setCellValueFactory(new PropertyValueFactory<Transaccion,Integer>("transaccion_id"));
        transaccion_cuenta_id_column.setCellValueFactory(new PropertyValueFactory<Transaccion,Integer>("cuenta_id"));
        transaccion_tipo_column.setCellValueFactory(new PropertyValueFactory<Transaccion,String>("tipo"));
        transaccion_valor_column.setCellValueFactory(new PropertyValueFactory<Transaccion,BigDecimal>("valor"));
        transaccion_fecha_column.setCellValueFactory(new PropertyValueFactory<Transaccion,String>("fecha"));
        transaccionTableView.setItems(tq.getAllTransacciones());


    }

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
                while(true)
                {
                    //run the query that is desired here weather its Withdraw... etc. with inputFromClient


                    //send confirmation of succesful/unsuccesful transaction, using outputToClient

                    int mensaje = inputFromClient.readInt();
                    System.out.println(mensaje);

                    //show results in GUI with Platform.runLater()...


                }



            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

}


