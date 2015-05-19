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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Paulo
 */
public class EstoqueDAO {
    private Connection con;
    
    public EstoqueDAO() {
        con = new DataSource().getCon();
    }    
    
    public void baixarProduto(Produto obj, int quantidade) {
        atualizar(obj, Math.abs(quantidade) * -1); // em java não existe uint
    }
    
    public void estocarProduto(Produto obj, int quantidade) {
        atualizar(obj, Math.abs(quantidade)); // em java não existe uint
    }
    
    public void inserir(Produto obj, int quantidade) {
        PreparedStatement stm;
        
        try 
        {
            stm = con.prepareStatement("INSERT INTO estoque (cod_produto, quantidade) VALUES (LAST_INSERT_ID(), ?)");
            stm.setInt(1, quantidade);
            stm.execute();
        } catch (SQLException e) {
            System.err.println("Falha na Inserção");
            System.err.println(e);
            System.exit(1);
        }
    }
    
    private void atualizar(Produto obj, int quantidade) {
        PreparedStatement stm;
        
        try 
        {
            stm = con.prepareStatement("UPDATE estoque SET quantidade = quantidade + ? WHERE cod_produto = ?");
            stm.setInt(1, quantidade);
            stm.setInt(2, obj.getCodigo());
            stm.execute();
        } catch (SQLException e) {
            System.err.println("Falha na atualização do estoque.");
            System.err.println(e);
            System.exit(1);
        }
    }
                
    public void removerProduto(Produto obj) {
        /*
            Verificar com o cliente se os produtos devem ser removidos do estoque ou se é necessários
            realizar um procedimento de movimentação de estoque zerando sua quantidade.
        */
        
        throw new UnsupportedOperationException("Método não implementado!");
    }
    
    public List<Produto> selecionarProdutosEmEstoque() {
        List<Produto> res = new ArrayList<Produto>();
        
        try {
            PreparedStatement stm = con.prepareStatement("SELECT B.codigo, B.nome, B.descricao, B.valor, B.foto FROM estoque A INNER JOIN produto B ON A.cod_produto = B.codigo WHERE A.quantidade > 0");           
            ResultSet rs = stm.executeQuery();
            while(rs.next()) {
                Produto obj = new Produto();
                obj.setNome(rs.getString("nome"));
                obj.setDescricao(rs.getString("descricao"));
                obj.setCodigo(rs.getInt("codigo"));
                obj.setFoto(rs.getString("foto"));
                obj.setValor(rs.getDouble("valor"));
                res.add(obj);
            }
        } catch (SQLException e) {
            System.err.println("Falha na insercao");
            System.err.println(e);
            System.exit(1);
        }
        return res;    
    }
}
