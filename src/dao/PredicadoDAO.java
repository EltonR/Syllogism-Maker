package dao;

import entidade.Predicado;
import factory.ConexaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PredicadoDAO {

    public int insertPredicado(String nome, int aridade) throws SQLException {
        Connection connection = ConexaoFactory.conectar();
        PreparedStatement sql = connection.prepareStatement("INSERT INTO predicado(nome, aridade) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
        sql.setString(1, nome);
        sql.setInt(2, aridade);
        sql.executeUpdate();
        ResultSet generatedKeys = sql.getGeneratedKeys();
        int n = 0;
        if (generatedKeys.next()) {
            n = generatedKeys.getInt(1);
        }
        connection.close();
        return n;
    }

    public Predicado getPredicadoById(int id) throws SQLException {
        Predicado p = null;
        Connection connection = ConexaoFactory.conectar();
        PreparedStatement sql = connection.prepareStatement("SELECT * FROM predicado WHERE idpredicado = ?");
        sql.setInt(1, id);
        ResultSet resultSet = sql.executeQuery();
        if (resultSet.next()) {
            p = new Predicado();
            p.setIdpredicado(resultSet.getInt("idpredicado"));
            p.setNome(resultSet.getString("nome"));
            p.setAridade(resultSet.getInt("aridade"));
            connection.close();
        }
        return p;
    }

    public Predicado getPredicadoByNome(String nome) throws SQLException {
        Predicado p = null;
        Connection connection = ConexaoFactory.conectar();
        PreparedStatement sql = connection.prepareStatement("SELECT * FROM predicado WHERE nome = ?");
        sql.setString(1, nome);
        ResultSet resultSet = sql.executeQuery();
        if (resultSet.next()) {
            p = new Predicado();
            p.setIdpredicado(resultSet.getInt("idpredicado"));
            p.setNome(resultSet.getString("nome"));
            p.setAridade(resultSet.getInt("aridade"));
            connection.close();
        }
        if(p==null){
            p = new Predicado();
            p.setAridade(0);
            p.setIdpredicado(insertPredicado(nome, 0));
            p.setNome(nome);
        }
        return p;
    }
    
   
    
}
