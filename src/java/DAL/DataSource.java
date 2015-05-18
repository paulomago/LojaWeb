/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Paulo
 */
public class DataSource {
    private Connection con;
    
    public DataSource() {
        String url, user, pass;
        
        url = "jdbc:mysql://localhost:3306/LojaWeb";
        user = "root";
        pass = "";
        
        try {
            con = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            System.err.println("Falha na Conexao com o BD");
            System.err.println(e);
            System.exit(1);
        }
    }
    
    public Connection getCon() {
        return con;
    }
}
