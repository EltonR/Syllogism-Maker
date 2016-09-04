package dao;

import entidade.Predicado;
import entidade.ProposicaoSingular;
import entidade.TermoSingular;
import factory.ConexaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProposicaoSingularDAO {
    
    public long insertSingular(ProposicaoSingular proposicaoSingular) {
        try {
            Connection connection = ConexaoFactory.conectar();
            PreparedStatement sql = connection.prepareStatement("INSERT INTO proposicaoSingular(idtermoSingular, pertence, predicado, idpropcateginferida, idpropsinginferida, idinferencia) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            sql.setInt(1, proposicaoSingular.getTermoSingular().getIdTermo());
            sql.setBoolean(2, proposicaoSingular.isPertence());
            sql.setInt(3, proposicaoSingular.getPredicado().getIdpredicado());
            sql.setLong(4, proposicaoSingular.getIdpropcateginferida());
            sql.setLong(5, proposicaoSingular.getIdpropsinginferida());
            sql.setInt(6, proposicaoSingular.getIdinferencia());
            sql.executeUpdate();
            ResultSet generatedKeys = sql.getGeneratedKeys();
            long n = 0;
            if(generatedKeys.next()){
                n = generatedKeys.getLong(1);
            }
            connection.close();
            return n;
        } catch (SQLException ex) {
            //Logger.getLogger(ProposicaoCategoricaDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Já exsites PROP SINGULAR: "+proposicaoSingular.toString());
        }
        return 0;
    }
    
    public List<ProposicaoSingular> getPropsRelacionadas(TermoSingular termoSingular, Predicado predicado) {
        ArrayList<ProposicaoSingular> lista = new ArrayList<>();
        try {
            Connection connection = ConexaoFactory.conectar();
            PreparedStatement sql = connection.prepareStatement("SELECT * FROM proposicaoSingular WHERE predicado = ? OR idtermoSingular = ?");
            sql.setInt(1, predicado.getIdpredicado());
            sql.setInt(2, termoSingular.getIdTermo());
            ResultSet resultSet = sql.executeQuery();
            while (resultSet.next()) {
                ProposicaoSingular p = new ProposicaoSingular();
                p.setPredicado(new PredicadoDAO().getPredicadoById(resultSet.getInt("predicado")));
                p.setPertence(resultSet.getBoolean("pertence"));
                p.setTermoSingular(new TermoSingularDAO().getTermoSingularById(resultSet.getInt("idtermoSingular")));
                p.setIdpropcateginferida(resultSet.getLong("idpropcateginferida"));
                p.setIdpropsinginferida(resultSet.getLong("idpropsinginferida"));
                p.setIdinferencia(resultSet.getInt("idinferencia"));
                lista.add(p);
            }
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProposicaoCategoricaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    
    public List<ProposicaoSingular> getPropsPredicado(Predicado predicado) {
        ArrayList<ProposicaoSingular> lista = new ArrayList<>();
        try {
            Connection connection = ConexaoFactory.conectar();
            PreparedStatement sql = connection.prepareStatement("SELECT * FROM proposicaoSingular WHERE predicado = ?");
            sql.setInt(1, predicado.getIdpredicado());
            ResultSet resultSet = sql.executeQuery();
            while (resultSet.next()) {
                ProposicaoSingular p = new ProposicaoSingular();
                p.setPredicado(new PredicadoDAO().getPredicadoById(resultSet.getInt("predicado")));
                p.setPertence(resultSet.getBoolean("pertence"));
                p.setTermoSingular(new TermoSingularDAO().getTermoSingularById(resultSet.getInt("idtermoSingular")));
                p.setIdpropcateginferida(resultSet.getLong("idpropcateginferida"));
                p.setIdpropsinginferida(resultSet.getLong("idpropsinginferida"));
                p.setIdinferencia(resultSet.getInt("idinferencia"));
                lista.add(p);
            }
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProposicaoCategoricaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
}
