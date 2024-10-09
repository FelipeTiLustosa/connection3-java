package src.db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {
    private  static Connection con=null;

    public static Connection getConnection(){
        try {
            if(con==null){
                Properties prop = loadProperties();
                String url = prop.getProperty("dburl");
                con=DriverManager.getConnection(url,prop);
            }

        } catch (SQLException e) { // SQLException e derivada da exception no caso vc seria obrigado a trata, mais no caso
            throw new DbException(e.getMessage());// mais como no caso eu tenho uma DbException q e derivada de RuntimeException ent no caso ñ no caso nao sou obrigado a fica tratando
        }
        return  con;
    }

    public static Properties loadProperties() {
        try (FileInputStream fs = new FileInputStream("db.properties")){
            Properties pros = new Properties();
            pros.load(fs);
            return  pros;
        } catch (IOException e) {
            throw new DbException(e.getMessage());
        }
    }
    public static void closeConnection() {
        if(con!=null) {
            try {
                con.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }

        }
    }
    public static void closeStatement(Statement st){
        if(st!=null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }

    }
    public static void closeResultset(ResultSet rs){
        if(rs!=null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }

    }
}
