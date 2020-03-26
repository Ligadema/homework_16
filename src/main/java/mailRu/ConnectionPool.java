package mailRu;

import models.User;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String JDBC_DB_URL = "jdbc:mysql://localhost:3306/mailru?useSSL=false";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "ABC13579!";
    private static GenericObjectPool pool = null;

    @SuppressWarnings("unused")
    public DataSource setUpPool() throws Exception {
        Class.forName(JDBC_DRIVER);
        pool = new GenericObjectPool();
        pool.setMaxActive(5);
        ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(JDBC_DB_URL, JDBC_USER, JDBC_PASSWORD);
        PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory
                (connectionFactory, pool, null, null, false, true);
        return new PoolingDataSource(pool);
    }

    public static List<User> getDataBaseUsers() {
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ConnectionPool connectionPool = new ConnectionPool();
        List<User> users = new ArrayList<>();

        try {
            DataSource dataSource = connectionPool.setUpPool();
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM mailru.users");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(Integer.parseInt(resultSet.getString("id")));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                users.add(user);
            }
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                if(resultSet != null) {
                    resultSet.close();
                }
                if(preparedStatement != null) {
                    preparedStatement.close();
                }
                if(connection != null) {
                    connection.close();
                }
            } catch(Exception sqlException) {
                sqlException.printStackTrace();
            }
        }
        return users;
    }
}
