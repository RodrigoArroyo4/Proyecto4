import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.derby.client.am.Decimal;
import org.apache.derby.impl.sql.execute.CurrentDatetime;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CuentaQueries {

    private PreparedStatement selectCuentas;
    private PreparedStatement selectSaldoofCuenta;
    private PreparedStatement withdraFunds;
    private PreparedStatement transaction;
    private PreparedStatement depostitFunds;
    private PreparedStatement getCuenta;

    public CuentaQueries()
    {
        try{
            Connection connection = DriverManager.getConnection(
                    "jdbc:derby:Banco", "rodrigo", "root");

            selectCuentas = connection.prepareStatement("SELECT * FROM cuentas");
            selectSaldoofCuenta = connection.prepareStatement(
                    "SELECT saldo FROM cuentas WHERE cliente_id = ?");
            withdraFunds = connection.prepareStatement("UPDATE cuentas SET saldo = ? WHERE cliente_id = ?");
            depostitFunds = connection.prepareStatement("UPDATE cuentas SET saldo = ? WHERE cliente_id = ?");
            transaction = connection.prepareStatement("INSERT INTO transacciones (cuenta_id, tipo, valor, fecha) VALUES (?, ?, ?, ? )");
            getCuenta = connection.prepareStatement("SELECT cuenta_id FROM cuentas WHERE cliente_id = ?");
            //depostiFunds = connection.prepareStatement("");

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

    public int getCuentaCliente(int cliente_id)
    {
        List < Integer > results = null;
        ResultSet resultSet = null;


        try{
            getCuenta.setInt(1,cliente_id);
            resultSet = getCuenta.executeQuery();
            results = new ArrayList<Integer>();

            while (resultSet.next()){
                results.add(
                        resultSet.getInt("cuenta_id")
                );
            }

        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        finally {
            try{
                resultSet.close();
            } catch(SQLException sqlException){
                sqlException.printStackTrace();
                //close();
            }
        }
        return results.get(0);
    }

    public Boolean withdraFunds(int cliente_id, Double value) throws Exception
    {
        List < Double > saldoLista = getSaldoOfCuenta(cliente_id);
        Double saldo = saldoLista.get(0);
        java.sql.Date date = new CurrentDatetime().getCurrentDate();

        if (value < saldo)
        {
            saldo = saldo - value;
            BigDecimal valueDecimal = new BigDecimal(saldo);
            withdraFunds.setBigDecimal(1, valueDecimal);
            withdraFunds.setInt(2, cliente_id);
            int re = withdraFunds.executeUpdate();
            transaction.setInt(1, getCuentaCliente(cliente_id));
            transaction.setString(2, "Retiro");
            transaction.setBigDecimal(3, valueDecimal);
            transaction.setDate(4, date);
            int ra = transaction.executeUpdate();
            return true;
        }
        else
            return false;
    }

    public Boolean depositFunds(int cliente_id, Double value) throws Exception
    {
        List < Double > saldoLista = getSaldoOfCuenta(cliente_id);
        Double saldo = saldoLista.get(0);
        java.sql.Date date = new CurrentDatetime().getCurrentDate();
        saldo = saldo + value;
        BigDecimal valueDecimal = new BigDecimal(saldo);
        depostitFunds.setBigDecimal(1, valueDecimal);
        depostitFunds.setInt(2, cliente_id);
        int re = depostitFunds.executeUpdate();
        transaction.setInt(1, getCuentaCliente(cliente_id));
        transaction.setString(2, "Depósito");
        transaction.setBigDecimal(3, valueDecimal);
        transaction.setDate(4, date);
        int ra = transaction.executeUpdate();
        return true;
    }

    public List< Double >  getSaldoOfCuenta(Integer cliente_id){
        List < Double > results = null;
        ResultSet resultSet = null;

        try{
            selectSaldoofCuenta.setInt(1,cliente_id);
            resultSet = selectSaldoofCuenta.executeQuery();
            results = new ArrayList<Double>();

            while (resultSet.next()){
                results.add(
                        resultSet.getDouble("saldo")
                );
            }

        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        finally {
            try{
                resultSet.close();
            } catch(SQLException sqlException){
                sqlException.printStackTrace();
                //close();
            }
        }
        return results;
    }
}
