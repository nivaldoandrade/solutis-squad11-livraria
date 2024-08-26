package db;

import java.io.*;
import java.sql.*;
import java.util.Properties;

public class DB {
    private static Connection conn = null;

    public static Connection getConnection() {
        Properties props = loadProperties();

        String url = props.getProperty("dburl");
        String dataBaseName = props.getProperty("databaseName");
        String username = props.getProperty("user");
        String password = props.getProperty("password");

        url = url + dataBaseName;

        if(conn == null) {
            try {
                conn = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        return conn;
    }

    public static void initializeDatabase() {
        Properties props = loadProperties();

        String databaseUrl = props.getProperty("dburl");
        String username = props.getProperty("user");
        String password = props.getProperty("password");
        String databaseName = props.getProperty("databaseName");

        String url = String.format("%s?user=%s&password=%s&allowMultiQueries=true", databaseUrl, username, password);

        try (Connection connection = DriverManager.getConnection(url)) {
            try (Statement st = connection.createStatement()) {
                String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS " + databaseName;
                st.execute(createDatabaseSQL);
            }

            url = String.format("%s%s?user=%s&password=%s&allowMultiQueries=true", databaseUrl, databaseName, username, password);
            try (Connection dbConnection = DriverManager.getConnection(url)) {

                InputStream inputStream = DB.class.getClassLoader().getResourceAsStream("schema.sql");
                if (inputStream == null) {
                    throw new IllegalArgumentException("Arquivo schema.sql não encontrado.");
                }

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                    StringBuilder sqlBuilder = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        sqlBuilder.append(line).append("\n");
                    }

                    try (Statement statement = dbConnection.createStatement()) {
                        statement.execute(sqlBuilder.toString());
                    } catch (SQLException e) {
                        throw new SQLException(e.getMessage());
                    }
                }
            }

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    private static Properties loadProperties() {
        ClassLoader classLoader = DB.class.getClassLoader();

        try(InputStream fs = classLoader.getResourceAsStream("db.properties")) {
            Properties props = new Properties();

            props.load(fs);

            return props;

        } catch (IOException e) {
            throw new DBException(e.getMessage());
        }
    }

    public static void closeConnection() {
        if(conn != null) {
            try {
                conn.close();
            }catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public static void closeStatement(Statement st) {
        if(st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if(rs != null) {
            try {
                rs.close();
            } catch(SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}
