package dao;

import entidade.TermoSingular;
import factory.ConexaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TermoSingularDAO {

    public TermoSingular getTermoSingularByNome(String nome) throws SQLException {
        TermoSingular t = null;
        Connection connection = ConexaoFactory.conectar();
        PreparedStatement sql = connection.prepareStatement("SELECT * FROM termoSingular WHERE nome = ?");
        sql.setString(1, nome);
        ResultSet resultSet = sql.executeQuery();
        if (resultSet.next()) {
            t = new TermoSingular();
            t.setIdTermo(resultSet.getInt("idtermoSingular"));
            t.setNome(resultSet.getString("nome"));
            connection.close();
        }
        if (t == null) {
            t = new TermoSingular();
            t.setNome(nome);
            t.setIdTermo(insertTermoSingular(nome));
        }
        return t;
    }

    public TermoSingular getTermoSingularById(int idtermo) throws SQLException {
        TermoSingular t = null;
        Connection connection = ConexaoFactory.conectar();
        PreparedStatement sql = connection.prepareStatement("SELECT * FROM termoSingular WHERE idtermoSingular = ?");
        sql.setInt(1, idtermo);
        ResultSet resultSet = sql.executeQuery();
        if (resultSet.next()) {
            t = new TermoSingular();
            t.setIdTermo(resultSet.getInt("idtermoSingular"));
            t.setNome(resultSet.getString("nome"));
            connection.close();
        }
        return t;
    }
    
    public int insertTermoSingular(String nome) throws SQLException {
        Connection connection = ConexaoFactory.conectar();
        PreparedStatement sql = connection.prepareStatement("INSERT INTO termoSingular(nome) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
        sql.setString(1, nome);
        sql.executeUpdate();
        ResultSet generatedKeys = sql.getGeneratedKeys();
        int n = 0;
        if (generatedKeys.next()) {
            n = generatedKeys.getInt(1);
        }
        connection.close();
        return n;
    }
}
