package entidade;

import dao.ProposicaoCategoricaDAO;
import dao.ProposicaoSingularDAO;
import java.util.List;

public class ProposicaoCategorica extends Thread {

    private long idproposicao;
    private String quantificador;
    private Predicado predicado1;
    private Predicado predicado2;
    private String operador;
    private long inferida1;
    private long inferida2;
    private int metodo;

    @Override
    public void run() {
        if (predicado1.getIdpredicado() != predicado2.getIdpredicado()) {
            idproposicao = new ProposicaoCategoricaDAO().insertCategorica(this);
            if (idproposicao != 0) {
                if (quantificador.equals("∀")) {
                    ProposicaoCategorica p2 = new ProposicaoCategorica();
                    ProposicaoCategorica p3 = new ProposicaoCategorica();
                    p2.setInferida1(idproposicao);
                    p3.setInferida1(idproposicao);
                    p2.setQuantificador("∃");
                    p3.setQuantificador("∃");
                    p2.setOperador("^");
                    p3.setOperador("^");
                    p2.setPredicado1(predicado1);
                    p2.setPredicado2(predicado2);
                    p3.setPredicado1(predicado2);
                    p3.setPredicado2(predicado1);
                    p2.start();
                    p3.start();
                } else if (quantificador.equals("¬∃")) {
                    ProposicaoCategorica p2 = new ProposicaoCategorica();
                    ProposicaoCategorica p3 = new ProposicaoCategorica();
                    ProposicaoCategorica p4 = new ProposicaoCategorica();
                    p2.setInferida1(idproposicao);
                    p3.setInferida1(idproposicao);
                    p4.setInferida1(idproposicao);
                    p2.setQuantificador("¬∀");
                    p3.setQuantificador("¬∀");
                    p4.setQuantificador("¬∃");
                    p2.setOperador("^");
                    p3.setOperador("^");
                    p4.setOperador("^");
                    p2.setPredicado1(predicado1);
                    p2.setPredicado2(predicado2);
                    p3.setPredicado1(predicado2);
                    p3.setPredicado2(predicado1);
                    p4.setPredicado1(predicado2);
                    p4.setPredicado2(predicado1);
                    p2.start();
                    p3.start();
                    p4.start();
                } else if (quantificador.equals("∃")) {
                    ProposicaoCategorica p2 = new ProposicaoCategorica();
                    p2.setInferida1(idproposicao);
                    p2.setQuantificador("∃");
                    p2.setOperador("^");
                    p2.setPredicado1(predicado2);
                    p2.setPredicado2(predicado1);
                    p2.start();
                }
                List<ProposicaoCategorica> lista = new ProposicaoCategoricaDAO().getPropsRelacionadas(this);
                for (int i = 0; i < lista.size(); i++) {
                    checaSilogismos(this, lista.get(i));
                    checaSilogismos(lista.get(i), this);
                }
                if (quantificador.equals("¬∃") || quantificador.equals("∀")) {
                    checaSingulares();
                }
            }
        }
    }

    private void checaSilogismos(ProposicaoCategorica p1, ProposicaoCategorica p2) {
        ProposicaoCategorica p = new ProposicaoCategorica();
        p.setInferida1(p1.getIdproposicao());
        p.setInferida2(p2.getIdproposicao());
        if (p1.getPredicado1().getIdpredicado() == p2.getPredicado2().getIdpredicado()) {
            if (p1.getQuantificador().equals("∀") && p2.getQuantificador().equals("∀")) { //BARBARA
                p.setQuantificador("∀");
                p.setPredicado1(p2.getPredicado1());
                p.setPredicado2(p1.getPredicado2());
                p.setOperador("→");
                p.setMetodo(1);
                System.out.println("BARBARA: " + p1.toString() + p2.toString() + p.toString());
                p.start();
            } else if (p1.getQuantificador().equals("¬∃") && p2.getQuantificador().equals("∀")) { //CELARENT
                p.setQuantificador("¬∃");
                p.setPredicado1(p2.getPredicado1());
                p.setPredicado2(p1.getPredicado2());
                p.setOperador("^");
                p.setMetodo(2);
                System.out.println("CELARENT: " + p1.toString() + p2.toString() + p.toString());
                p.start();
            } else if (p1.getQuantificador().equals("∀") && p2.getQuantificador().equals("∃")) { //DARII
                p.setQuantificador("∃");
                p.setPredicado1(p2.getPredicado1());
                p.setPredicado2(p1.getPredicado2());
                p.setOperador("^");
                p.setMetodo(3);
                System.out.println("DARII: " + p1.toString() + p2.toString() + p.toString());
                p.start();
            } else if (p1.getQuantificador().equals("¬∃") && p2.getQuantificador().equals("∃")) { //FERIO
                p.setQuantificador("¬∀");
                p.setPredicado1(p2.getPredicado1());
                p.setPredicado2(p1.getPredicado2());
                p.setOperador("^");
                p.setMetodo(4);
                System.out.println("FERIO: " + p1.toString() + p2.toString() + p.toString());
                p.start();
            }
        } else if (p1.getPredicado2().getIdpredicado() == p2.getPredicado2().getIdpredicado()) {
            if (p1.getQuantificador().equals("¬∃") && p2.getQuantificador().equals("∀")) { //CESARE
                p.setQuantificador("¬∃");
                p.setPredicado1(p2.getPredicado1());
                p.setPredicado2(p1.getPredicado1());
                p.setOperador("^");
                p.setMetodo(5);
                System.out.println("CESARE: " + p1.toString() + p2.toString() + p.toString());
                p.start();
            } else if (p1.getQuantificador().equals("∀") && p2.getQuantificador().equals("¬∃")) { //CAMESTRES
                p.setQuantificador("¬∃");
                p.setPredicado1(p2.getPredicado1());
                p.setPredicado2(p1.getPredicado1());
                p.setOperador("^");
                p.setMetodo(6);
                System.out.println("CAMESTRES: " + p1.toString() + p2.toString() + p.toString());
                p.start();
            } else if (p1.getQuantificador().equals("¬∃") && p2.getQuantificador().equals("∃")) { //FESTINO
                p.setQuantificador("¬∀");
                p.setPredicado1(p2.getPredicado1());
                p.setPredicado2(p1.getPredicado1());
                p.setOperador("^");
                p.setMetodo(7);
                System.out.println("FESTINO: " + p1.toString() + p2.toString() + p.toString());
                p.start();
            } else if (p1.getQuantificador().equals("∀") && p2.getQuantificador().equals("¬∀")) { //BAROCO
                p.setQuantificador("¬∀");
                p.setPredicado1(p2.getPredicado1());
                p.setPredicado2(p1.getPredicado1());
                p.setOperador("^");
                p.setMetodo(8);
                System.out.println("BAROCO: " + p1.toString() + p2.toString() + p.toString());
                p.start();
            }
        } else if (p1.getPredicado1().getIdpredicado() == p2.getPredicado1().getIdpredicado()) {
            if (p1.getQuantificador().equals("∀") && p2.getQuantificador().equals("∀")) { //DARAPTI
                p.setQuantificador("∃");
                p.setPredicado1(p2.getPredicado2());
                p.setPredicado2(p1.getPredicado2());
                p.setOperador("^");
                p.setMetodo(9);
                System.out.println("DARAPTI: " + p1.toString() + p2.toString() + p.toString());
                p.start();
            } else if (p1.getQuantificador().equals("¬∃") && p2.getQuantificador().equals("∀")) { //FELAPTON
                p.setQuantificador("¬∀");
                p.setPredicado1(p2.getPredicado2());
                p.setPredicado2(p1.getPredicado2());
                p.setOperador("^");
                p.setMetodo(10);
                System.out.println("FELAPTON: " + p1.toString() + p2.toString() + p.toString());
                p.start();
            } else if (p1.getQuantificador().equals("∃") && p2.getQuantificador().equals("∀")) { //DISAMIS
                p.setQuantificador("∃");
                p.setPredicado1(p2.getPredicado2());
                p.setPredicado2(p1.getPredicado2());
                p.setOperador("^");
                p.setMetodo(11);
                System.out.println("DISAMIS: " + p1.toString() + p2.toString() + p.toString());
                p.start();
            } else if (p1.getQuantificador().equals("¬∀") && p2.getQuantificador().equals("∀")) { //BOCARDO
                p.setQuantificador("¬∀");
                p.setPredicado1(p2.getPredicado2());
                p.setPredicado2(p1.getPredicado2());
                p.setOperador("^");
                p.setMetodo(12);
                System.out.println("BOCARDO: " + p1.toString() + p2.toString() + p.toString());
                p.start();
            } else if (p1.getQuantificador().equals("∀") && p2.getQuantificador().equals("∃")) { //DATISI
                p.setQuantificador("∃");
                p.setPredicado1(p2.getPredicado2());
                p.setPredicado2(p1.getPredicado2());
                p.setOperador("^");
                p.setMetodo(13);
                System.out.println("DATISI: " + p1.toString() + p2.toString() + p.toString());
                p.start();
            } else if (p1.getQuantificador().equals("¬∃") && p2.getQuantificador().equals("∃")) { //FERISON
                p.setQuantificador("¬∀");
                p.setPredicado1(p2.getPredicado2());
                p.setPredicado2(p1.getPredicado2());
                p.setOperador("^");
                p.setMetodo(14);
                System.out.println("FERISON: " + p1.toString() + p2.toString() + p.toString());
                p.start();
            }
        } else if (p1.getPredicado2().getIdpredicado() == p2.getPredicado1().getIdpredicado()) {
            if (p1.getQuantificador().equals("∀") && p2.getQuantificador().equals("∀")) { //BAMALIP
                p.setQuantificador("∃");
                p.setPredicado1(p2.getPredicado2());
                p.setPredicado2(p1.getPredicado1());
                p.setOperador("^");
                p.setMetodo(15);
                System.out.println("BAMALIP: " + p1.toString() + p2.toString() + p.toString());
                p.start();
            } else if (p1.getQuantificador().equals("∀") && p2.getQuantificador().equals("¬∃")) { //CALEMES
                p.setQuantificador("¬∃");
                p.setPredicado1(p2.getPredicado2());
                p.setPredicado2(p1.getPredicado1());
                p.setOperador("^");
                p.setMetodo(16);
                System.out.println("CALEMES: " + p1.toString() + p2.toString() + p.toString());
                p.start();
            } else if (p1.getQuantificador().equals("∃") && p2.getQuantificador().equals("∀")) { //DIMATIS
                p.setQuantificador("∃");
                p.setPredicado1(p2.getPredicado2());
                p.setPredicado2(p1.getPredicado1());
                p.setOperador("^");
                p.setMetodo(17);
                System.out.println("DIMATIS: " + p1.toString() + p2.toString() + p.toString());
                p.start();
            } else if (p1.getQuantificador().equals("¬∀") && p2.getQuantificador().equals("∀")) { //FESAPO
                p.setQuantificador("¬∀");
                p.setPredicado1(p2.getPredicado2());
                p.setPredicado2(p1.getPredicado1());
                p.setOperador("^");
                p.setMetodo(18);
                System.out.println("FESAPO: " + p1.toString() + p2.toString() + p.toString());
                p.start();
            } else if (p1.getQuantificador().equals("¬∃") && p2.getQuantificador().equals("∃")) { //FRESISON
                p.setQuantificador("¬∀");
                p.setPredicado1(p2.getPredicado2());
                p.setPredicado2(p1.getPredicado1());
                p.setOperador("^");
                p.setMetodo(19);
                System.out.println("FRESISON: " + p1.toString() + p2.toString() + p.toString());
                p.start();
            }
        }
    }

    private void checaSingulares() {
        List<ProposicaoSingular> listaSing = new ProposicaoSingularDAO().getPropsPredicado(predicado1);
        for (int i = 0; i < listaSing.size(); i++) {
            ProposicaoSingular p = new ProposicaoSingular();
            p.setPredicado(predicado2);
            if (quantificador.equals("∀")) {
                p.setPertence(true);
                p.setIdinferencia(20);
            } else {
                p.setPertence(false);
                p.setIdinferencia(21);
            }
            p.setTermoSingular(p.getTermoSingular());
            p.setIdpropcateginferida(idproposicao);
            p.start();
        }
    }

    @Override
    public String toString() {
        return toString2();
    }

    public String toString2() {
        String s = "";
        switch (quantificador) {
            case "∀":
                s = "Todo " + predicado1.getNome() + " é " + predicado2.getNome();
                break;
            case "¬∃":
                s = "Nenhum " + predicado1.getNome() + " é " + predicado2.getNome();
                break;
            case "∃":
                s = "Algum " + predicado1.getNome() + " é " + predicado2.getNome();
                break;
            case "¬∀":
                s = "Algum " + predicado1.getNome() + " não é " + predicado2.getNome();
                break;
        }
        return s;
    }

    public String getQuantificador() {
        return quantificador;
    }

    public void setQuantificador(String quantificador) {
        this.quantificador = quantificador;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public long getInferida1() {
        return inferida1;
    }

    public void setInferida1(long inferida1) {
        this.inferida1 = inferida1;
    }

    public long getInferida2() {
        return inferida2;
    }

    public void setInferida2(long inferida2) {
        this.inferida2 = inferida2;
    }

    public long getIdproposicao() {
        return idproposicao;
    }

    public void setIdproposicao(long idproposicao) {
        this.idproposicao = idproposicao;
    }

    public int getMetodo() {
        return metodo;
    }

    public void setMetodo(int metodo) {
        this.metodo = metodo;
    }

    public Predicado getPredicado1() {
        return predicado1;
    }

    public void setPredicado1(Predicado predicado1) {
        this.predicado1 = predicado1;
    }

    public Predicado getPredicado2() {
        return predicado2;
    }

    public void setPredicado2(Predicado predicado2) {
        this.predicado2 = predicado2;
    }

}
