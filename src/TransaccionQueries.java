import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.derby.client.am.Decimal;

import java.sql.*;

public class TransaccionQueries
{
    private static final String URL = "jdbc:derby:Banco";
    private static final String USERNAME = "rodrigo";
    private static final String PASSWORD = "root";

    private Connection connection;
    private PreparedStatement selectTransaccion;

    public TransaccionQueries()
    {
        try{
            Connection connection = DriverManager.getConnection(
                    "jdbc:derby:Banco", "rodrigo", "root");
            selectTransaccion = connection.prepareStatement("SELECT * FROM transacciones");

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

}
