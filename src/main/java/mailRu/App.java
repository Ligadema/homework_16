package mailRu;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class App
{
    public static void main(String[] args) throws
            ClassNotFoundException
    {
        String url = "jdbc:mysql://localhost:3306/sys?useSSL=false";
    String user = "root";
        String password = "root";
        String query = "SELECT VERSION()";

        try (Connection con = DriverManager.getConnection(url,
                user, password);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query))
        {
            if(rs.next())
            {
                System.out.println(rs.getString(1));
            }
            rs.close();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }
}
