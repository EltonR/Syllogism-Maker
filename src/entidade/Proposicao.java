package entidade;

import java.util.ArrayList;

public class Proposicao {

    private ArrayList<String> listaVariaveis;
    private ArrayList<String> listaConstantes;
    private ArrayList<String> listaPredicados;
    private ArrayList<String> listaConetivos;
    private ArrayList<String> listaQuantificadores;

    public Proposicao() {
        listaConetivos = new ArrayList<>();
        listaConstantes = new ArrayList<>();
        listaPredicados = new ArrayList<>();
        listaQuantificadores = new ArrayList<>();
        listaVariaveis = new ArrayList<>();
    }
    
    
    

    public ArrayList<String> getListaVariaveis() {
        return listaVariaveis;
    }

    public void setListaVariaveis(ArrayList<String> listaVariaveis) {
        this.listaVariaveis = listaVariaveis;
    }

    public ArrayList<String> getListaConstantes() {
        return listaConstantes;
    }

    public void setListaConstantes(ArrayList<String> listaConstantes) {
        this.listaConstantes = listaConstantes;
    }

    public ArrayList<String> getListaPredicados() {
        return listaPredicados;
    }

    public void setListaPredicados(ArrayList<String> listaPredicados) {
        this.listaPredicados = listaPredicados;
    }

    public ArrayList<String> getListaConetivos() {
        return listaConetivos;
    }

    public void setListaConetivos(ArrayList<String> listaConetivos) {
        this.listaConetivos = listaConetivos;
    }

    public ArrayList<String> getListaQuantificadores() {
        return listaQuantificadores;
    }

    public void setListaQuantificadores(ArrayList<String> listaQuantificadores) {
        this.listaQuantificadores = listaQuantificadores;
    }

}
