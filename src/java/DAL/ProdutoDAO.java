/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Paulo
 */
public class ProdutoDAO {
    private Connection con;
    
    public ProdutoDAO() {
        con = new DataSource().getCon();
    }
    
    public void inserir(Produto obj, int quantidade) {
        PreparedStatement stm;
        
        try 
        {
            stm = con.prepareStatement("INSERT INTO produto (nome, descricao, foto, valor) VALUES (?, ?, ?, ?)");
            stm.setString(1, obj.getNome());
            stm.setString(2, obj.getDescricao());
            stm.setString(3, obj.getFoto());
            stm.setDouble(4, obj.getValor());
            stm.execute();

            new EstoqueDAO().inserir(obj, quantidade);
        } catch (SQLException e) {
            System.err.println("Falha na insercao");
            System.err.println(e);
            System.exit(1);
        }
    }
    
    public Produto selecionar(int codigo) {
        Produto res = new Produto();
        
        try {        
            PreparedStatement stm = con.prepareStatement("SELECT nome, descricao, foto, valor FROM produto WHERE codigo = ?");
            stm.setInt(1, codigo);            
            ResultSet rs = stm.executeQuery();
            
            rs.next();
            
            res.setNome(rs.getString("nome"));
            res.setDescricao(rs.getString("descricao"));
            res.setCodigo(codigo);
            res.setFoto(rs.getString("foto"));
            res.setValor(rs.getDouble("valor"));
        } catch (SQLException e) {
            System.err.println("Falha ao selecionar o produto.");
            System.err.println(e);
            System.exit(1);
        }
        
        return res;        
    }
}
