package matches;

import nationsAndPlayers.players.Jogadores;

public class JogadorPartida {
    private Jogadores jogador;
    private double nota;

    public JogadorPartida(Jogadores jogador, double nota) {
        this.jogador = jogador;
        this.nota = nota;
    }

    public Jogadores getJogador() {
        return jogador;
    }
    public void setJogador(Jogadores jogador) {
        this.jogador = jogador;
    }

    public double getNota() {
        return nota;
    }
    public void setNota(double nota) {
        this.nota = nota;
    }
}
