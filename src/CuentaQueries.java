import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class CuentaQueries {

    private PreparedStatement selectCuentas;

    public CuentaQueries(){
        try{
            Connection connection = DriverManager.getConnection(
                    "jdbc:derby:Banco", "rodrigo", "root");

            selectCuentas = connection.prepareStatement("SELECT * FROM cuentas");



        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public ObservableList< Cuenta > getAllCuentas(){
        ObservableList<Cuenta> results = FXCollections.observableArrayList();
        ResultSet resultSet = null;
        try
        {
            // executeQuery returns ResultSet containing matching entries
            resultSet = selectCuentas.executeQuery();
            //results = new ObservableList< Faculty >();

            while (resultSet.next())
            {
                results.add(new Cuenta(
                        resultSet.getInt("cuenta_id"),
                        resultSet.getInt("cliente_id"),
                        resultSet.getDouble("saldo")));
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
