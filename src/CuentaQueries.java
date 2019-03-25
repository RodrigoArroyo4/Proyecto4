import java.sql.*;

public class CuentaQueries {

    public CuentaQueries(){
        try{
            Connection connection = DriverManager.getConnection(
                    "jdbc:derby:BancoAA", "root", "root");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
