package matches;

import Enums.TipoEvento;
import nationsAndPlayers.players.Jogadores;

public class EventosOcorridos {
    private int minuto;
    private TipoEvento tipo;
    private Jogadores jogador;
    private int idPartida;
    private String selecao;
    public EventosOcorridos(int minuto, TipoEvento tipo, Jogadores jogador) {
        this.minuto = minuto;
        this.tipo = tipo;
        this.jogador = jogador;
    }

    public EventosOcorridos(int idPartida,int minuto, TipoEvento tipo, Jogadores jogador, String selecao) {
        this.minuto = minuto;
        this.tipo = tipo;
        this.jogador = jogador;
        this.idPartida = idPartida;
        this.selecao = selecao;
    }

    public int getMinuto() {
        return minuto;
    }
    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }

    public TipoEvento getTipo() {
        return tipo;
    }
    public void setTipo(TipoEvento tipo) {
        this.tipo = tipo;
    }

    public Jogadores getJogador() {
        return jogador;
    }
    public void setJogador(Jogadores jogador) {
        this.jogador = jogador;
    }

    public int getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }

    public String getSelecao() {
        return selecao;
    }

    public void setSelecao(String selecao) {
        this.selecao = selecao;
    }
}
