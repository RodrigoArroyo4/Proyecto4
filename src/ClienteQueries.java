import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;


public class ClienteQueries {

    private PreparedStatement selectCliente;

    public ClienteQueries(){
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:derby:Banco", "rodrigo", "root");
            //TODO
            selectCliente = connection.prepareStatement("SELECT * FROM cliente");
            //select all


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public ObservableList< Cliente > getAllCliente(){
        ObservableList<Cliente> results = FXCollections.observableArrayList();
        ResultSet resultSet = null;
        try
        {
            // executeQuery returns ResultSet containing matching entries
            resultSet = selectCliente.executeQuery();
            //results = new ObservableList< Faculty >();

            while (resultSet.next())
            {
                results.add(new Cliente(
                        resultSet.getInt("cliente_id"),
                        resultSet.getString("nombre_cliente")));

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
