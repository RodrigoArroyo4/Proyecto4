import java.sql.*;


public class ClienteQueries {

    public ClienteQueries(){
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:derby:BancoAA", "root", "root");
            //TODO
            //select all


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
