package matches;

import nationsAndPlayers.nations.Selecoes;

import java.util.ArrayList;

public class Escalacao {
    private int id;
    private String casaOuVisitante;
    private Selecoes selecao;
    private String formacao;
    private ArrayList<JogadorPartida> titulares;
    private ArrayList<JogadorPartida> reservas;

    public Escalacao(int id, Selecoes selecao, String formacao, ArrayList<JogadorPartida> titulares, ArrayList<JogadorPartida> reservas) {
        this.id=id;
        this.selecao = selecao;
        this.formacao = formacao;
        this.titulares = titulares;
        this.reservas = reservas;
    }
    public Escalacao(int id,String casaOuVisitante, String formacao, ArrayList<JogadorPartida> titulares, ArrayList<JogadorPartida> reservas) {
        this.id=id;
        this.casaOuVisitante=casaOuVisitante;
        this.formacao = formacao;
        this.titulares = titulares;
        this.reservas = reservas;
    }

    public String getCasaOuVisitante() {
        return casaOuVisitante;
    }

    public void setCasaOuVisitante(String casaOuVisitante) {
        this.casaOuVisitante = casaOuVisitante;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Selecoes getSelecao() {
        return selecao;
    }
    public void setSelecao(Selecoes selecao) {
        this.selecao = selecao;
    }

    public String getFormacao() {
        return formacao;
    }
    public void setFormacao(String formacao) {
        this.formacao = formacao;
    }

    public ArrayList<JogadorPartida> getTitulares() {
        return titulares;
    }
    public void setTitulares(ArrayList<JogadorPartida> titulares) {
        this.titulares = titulares;
    }

    public ArrayList<JogadorPartida> getReservas() {
        return reservas;
    }
    public void setReservas(ArrayList<JogadorPartida> reservas) {
        this.reservas = reservas;
    }
}
