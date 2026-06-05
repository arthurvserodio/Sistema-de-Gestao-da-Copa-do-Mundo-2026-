package stadiumAndRefeering;

import matches.Partida;
import users.Arbitro;

public class DesignacaoArbitragem {
    private Partida partida;
    private Arbitro arbitro;

    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    public Arbitro getArbitro() {
        return arbitro;
    }

    public void setArbitro(Arbitro arbitro) {
        this.arbitro = arbitro;
    }
}
