package dao;

import entidade.Predicado;
import entidade.ProposicaoCategorica;
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

public class ProposicaoCategoricaDAO {

    public long insertCategorica(ProposicaoCategorica proposicao) {
        if(proposicao.getPredicado1().equals(proposicao.getPredicado2()))
            return 0;
        try {
            Connection connection = ConexaoFactory.conectar();
            PreparedStatement sql = connection.prepareStatement("INSERT INTO proposicaoCategorica(quantificador, predicado1, predicado2, operador, inferida1, inferida2, idinferencia) VALUES (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            sql.setString(1, proposicao.getQuantificador());
            sql.setInt(2, proposicao.getPredicado1().getIdpredicado());
            sql.setInt(3, proposicao.getPredicado2().getIdpredicado());
            sql.setString(4, proposicao.getOperador());
            sql.setLong(5, proposicao.getInferida1());
            sql.setLong(6, proposicao.getInferida2());
            sql.setInt(7, proposicao.getMetodo());
            sql.executeUpdate();
            ResultSet generatedKeys = sql.getGeneratedKeys();
            long n = 0;
            if(generatedKeys.next()){
                n = generatedKeys.getLong(1);
            }
            connection.close();
            return n;
        } catch (SQLException ex) {
            //Logger.getLogger(ProposicaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(proposicao.toString()+" [já existe]");
        }
        return 0;
    }
    
    

    public boolean isContraditorio(ProposicaoCategorica p){
        try {
            Connection connection = ConexaoFactory.conectar();
            PreparedStatement sql;
            if (p.getQuantificador().equals("∀")) {
                sql = connection.prepareStatement("SELECT * FROM proposicaoCategorica WHERE (predicado1 = ? AND predicado2 = ?) AND (quantificador = ? OR quantificador = ?)");
                sql.setInt(1, p.getPredicado1().getIdpredicado());
                sql.setInt(2, p.getPredicado2().getIdpredicado());
                sql.setString(3, "¬∃");
                sql.setString(4, "¬∀");
                System.out.println("Universal Afirmativa...");
            } else if (p.getQuantificador().equals("¬∃")) {
                sql = connection.prepareStatement("SELECT * FROM proposicaoCategorica WHERE (predicado1 = ? AND predicado2 = ?) AND (quantificador = ? OR quantificador = ?)");
                sql.setInt(1, p.getPredicado1().getIdpredicado());
                sql.setInt(2, p.getPredicado2().getIdpredicado());
                sql.setString(3, "∃");
                sql.setString(4, "∀");
                System.out.println("Universal Negativa...");
            } else if (p.getQuantificador().equals("∃")) {
                sql = connection.prepareStatement("SELECT * FROM proposicaoCategorica WHERE (predicado1 = ? AND predicado2 = ?) AND quantificador = ?");
                sql.setInt(1, p.getPredicado1().getIdpredicado());
                sql.setInt(2, p.getPredicado2().getIdpredicado());
                sql.setString(3, "¬∃");
                System.out.println("Particular Afirmativa...");
            } else if (p.getQuantificador().equals("¬∀")) {
                sql = connection.prepareStatement("SELECT * FROM proposicaoCategorica WHERE (predicado1 = ? AND predicado2 = ?) AND quantificador = ?");
                sql.setInt(1, p.getPredicado1().getIdpredicado());
                sql.setInt(2, p.getPredicado2().getIdpredicado());
                sql.setString(3, "∀");
                System.out.println("Particular Negativa...");
            } else {
                System.out.println("Sem Quantificador...");
                return true;
            }
            ResultSet resultSet = sql.executeQuery();
            if (resultSet.next()) {
                connection.close();
                return true;
            }
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProposicaoCategoricaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public List<ProposicaoCategorica> getPropsRelacionadas(ProposicaoCategorica proposicao) {
        ArrayList<ProposicaoCategorica> lista = new ArrayList<>();
        try {
            Connection connection = ConexaoFactory.conectar();
            PreparedStatement sql = connection.prepareStatement("SELECT * FROM proposicaoCategorica WHERE predicado1 = ? OR predicado2 = ? OR predicado1 = ? OR predicado2 = ?");
            sql.setInt(1, proposicao.getPredicado1().getIdpredicado());
            sql.setInt(2, proposicao.getPredicado2().getIdpredicado());
            sql.setInt(3, proposicao.getPredicado2().getIdpredicado());
            sql.setInt(4, proposicao.getPredicado1().getIdpredicado());
            ResultSet resultSet = sql.executeQuery();
            while (resultSet.next()) {
                ProposicaoCategorica p = new ProposicaoCategorica();
                p.setIdproposicao(resultSet.getLong("idproposicao"));
                p.setQuantificador(resultSet.getString("quantificador"));
                p.setPredicado1(new PredicadoDAO().getPredicadoById(resultSet.getInt("predicado1")));
                p.setPredicado2(new PredicadoDAO().getPredicadoById(resultSet.getInt("predicado2")));
                p.setOperador(resultSet.getString("operador"));
                p.setInferida1(resultSet.getLong("inferida1"));
                p.setInferida2(resultSet.getLong("inferida2"));
                p.setMetodo(resultSet.getInt("idinferencia"));
                lista.add(p);
            }
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProposicaoCategoricaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    public List<ProposicaoCategorica> getPropsRelacionadas(Predicado predicado) {
        ArrayList<ProposicaoCategorica> lista = new ArrayList<>();
        try {
            Connection connection = ConexaoFactory.conectar();
            PreparedStatement sql = connection.prepareStatement("SELECT * FROM proposicaoCategorica WHERE predicado1 = ? OR predicado2 = ?");
            sql.setInt(1, predicado.getIdpredicado());
            sql.setInt(2, predicado.getIdpredicado());
            ResultSet resultSet = sql.executeQuery();
            while (resultSet.next()) {
                ProposicaoCategorica p = new ProposicaoCategorica();
                p.setIdproposicao(resultSet.getLong("idproposicao"));
                p.setQuantificador(resultSet.getString("quantificador"));
                p.setPredicado1(new PredicadoDAO().getPredicadoById(resultSet.getInt("predicado1")));
                p.setPredicado2(new PredicadoDAO().getPredicadoById(resultSet.getInt("predicado2")));
                p.setOperador(resultSet.getString("operador"));
                p.setInferida1(resultSet.getLong("inferida1"));
                p.setInferida2(resultSet.getLong("inferida2"));
                p.setMetodo(resultSet.getInt("idinferencia"));
                lista.add(p);
            }
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProposicaoCategoricaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    
    
    
}
