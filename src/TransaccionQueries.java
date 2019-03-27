import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
//import org.apache.derby.client.am.Decimal;
//import org.apache.derby.client.am.Decimal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransaccionQueries
{
    private static final String URL = "jdbc:derby:Banco";
    private static final String USERNAME = "rodrigo";
    private static final String PASSWORD = "root";

    private Connection connection;
    private PreparedStatement selectTransaccion;
    private PreparedStatement getTransaccionesFromCuenta;

    public TransaccionQueries()
    {
        try{
            Connection connection = DriverManager.getConnection(
                    "jdbc:derby:Banco", "rodrigo", "root");
            selectTransaccion = connection.prepareStatement("SELECT * FROM transacciones");
            getTransaccionesFromCuenta = connection.prepareStatement(
                    "SELECT * FROM transacciones WHERE cuenta_id in (" +
                            "SELECT cuenta_id FROM cuentas WHERE cliente_id = ?)");

        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    public ObservableList< Transaccion > getAllTransacciones(){
        ObservableList<Transaccion> results = FXCollections.observableArrayList();
        ResultSet resultSet = null;
        try
        {
            // executeQuery returns ResultSet containing matching entries
            resultSet = selectTransaccion.executeQuery();
            //results = new ObservableList< Faculty >();

            while (resultSet.next())
            {
                results.add(new Transaccion(
                        resultSet.getInt("transaccion_id"),

                        resultSet.getInt("cuenta_id"),
                        resultSet.getString("tipo"),
                        resultSet.getBigDecimal("valor"),
                        resultSet.getString("fecha")));
            }
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        finally
        {
            try
            {
                resultSet.close();
            }
            catch (SQLException sqlException)
            {
                sqlException.printStackTrace();
                //close();
            }
        }

        return results;
    }

    public List< Transaccion > getTransaccionesfromCuenta(Integer cliente_id){
        List<Transaccion> results = null;
        ResultSet resultSet = null;
        try
        {
            // executeQuery returns ResultSet containing matching entries
            getTransaccionesFromCuenta.setInt(1,cliente_id);
            resultSet = getTransaccionesFromCuenta.executeQuery();
            results = new ArrayList<Transaccion>();

            while (resultSet.next())
            {
                results.add(new Transaccion(
                        resultSet.getInt("transaccion_id"),
                        resultSet.getInt("cuenta_id"),
                        resultSet.getString("tipo"),
                        resultSet.getBigDecimal("valor"),
                        resultSet.getString("fecha")));
            }
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        finally
        {
            try
            {
                resultSet.close();
            }
            catch (SQLException sqlException)
            {
                sqlException.printStackTrace();
                //close();
            }
        }

        return results;
    }




}
