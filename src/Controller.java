import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.*;
import javafx.scene.control.cell.PropertyValueFactory;


public class Controller implements Initializable
{
    //Class initializers for queries
    ClienteQueries clq = new ClienteQueries();
    CuentaQueries cq = new CuentaQueries();
    TransaccionQueries tq = new TransaccionQueries();

    public Controller(){}
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

}
