package entidade;

import dao.ProposicaoCategoricaDAO;
import dao.ProposicaoSingularDAO;
import java.util.List;

public class ProposicaoSingular extends Thread {

    private long idproposicao;
    private TermoSingular termoSingular;
    private Predicado predicado;
    private long idpropcateginferida;
    private long idpropsinginferida;
    private boolean pertence;
    private int idinferencia;

    @Override
    public String toString() {
        String s = termoSingular.getNome();
        if (pertence) {
            s += " é ";
        } else {
            s += " não é ";
        }
        s += predicado.getNome();
        return s;
    }

    private void testaSilogismos() {
        List<ProposicaoCategorica> listaCat = new ProposicaoCategoricaDAO().getPropsRelacionadas(predicado);
        for (int i = 0; i < listaCat.size(); i++) {
            ProposicaoSingular p = new ProposicaoSingular();
            if (listaCat.get(i).getPredicado1().getIdpredicado() == predicado.getIdpredicado()) {
                if (listaCat.get(i).getQuantificador().equals("∀")) {
                    p.setPredicado(listaCat.get(i).getPredicado2());
                    p.setPertence(true);
                    p.setTermoSingular(termoSingular);
                    p.setIdpropcateginferida(listaCat.get(i).getIdproposicao());
                    p.setIdpropsinginferida(idproposicao);
                    p.setIdinferencia(20);
                    p.start();
                } else if (listaCat.get(i).getQuantificador().equals("¬∃")) {
                    p.setPredicado(listaCat.get(i).getPredicado2());
                    p.setPertence(false);
                    p.setTermoSingular(termoSingular);
                    p.setIdpropcateginferida(listaCat.get(i).getIdproposicao());
                    p.setIdpropsinginferida(idproposicao);
                    p.setIdinferencia(21);
                    p.start();
                }
            }
        }
    }

    @Override
    public void run() {
        idproposicao = new ProposicaoSingularDAO().insertSingular(this);
        if (idproposicao != 0) {
            testaSilogismos();
        }
    }

    public TermoSingular getTermoSingular() {
        return termoSingular;
    }

    public void setTermoSingular(TermoSingular termoSingular) {
        this.termoSingular = termoSingular;
    }

    public Predicado getPredicado() {
        return predicado;
    }

    public void setPredicado(Predicado predicado) {
        this.predicado = predicado;
    }

    public long getIdpropcateginferida() {
        return idpropcateginferida;
    }

    public void setIdpropcateginferida(long idpropcateginferida) {
        this.idpropcateginferida = idpropcateginferida;
    }

    public boolean isPertence() {
        return pertence;
    }

    public void setPertence(boolean pertence) {
        this.pertence = pertence;
    }

    public long getIdpropsinginferida() {
        return idpropsinginferida;
    }

    public void setIdpropsinginferida(long idpropsinginferida) {
        this.idpropsinginferida = idpropsinginferida;
    }

    public int getIdinferencia() {
        return idinferencia;
    }

    public void setIdinferencia(int idinferencia) {
        this.idinferencia = idinferencia;
    }

    public long getIdproposicao() {
        return idproposicao;
    }

    public void setIdproposicao(long id) {
        this.idproposicao = id;
    }

}
