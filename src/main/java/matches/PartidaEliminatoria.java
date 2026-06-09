package matches;

import Enums.Fase;
import Enums.StatusPartida;
import nationsAndPlayers.players.Jogadores;
import nationsAndPlayers.nations.Selecoes;
import nationsAndPlayers.players.Tecnico;
import stadiumAndRefeering.Estadio;
import users.Arbitro;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PartidaEliminatoria extends Partida{
    private boolean prorrogacao;
    private boolean penaltis;
    private int golsPenaltiCasa;
    private int golsPenaltiVisitante;

    public PartidaEliminatoria(int id,LocalDate data,String horario, int publico, Estadio estadio, Arbitro arbitro, Jogadores MVP, Selecoes selecaoCasa, Selecoes selecaoVisitante, Tecnico tecnicoCasa, Tecnico tecnicoVisitante, Fase fase, StatusPartida status, EstatisticaPartida estatistica, ArrayList<EventosOcorridos> eventos, Escalacao escalacaoCasa, Escalacao escalacaoVisitante, boolean prorrogacao, boolean penaltis, int golsPenaltiCasa, int golsPenaltiVisitante) {
        super(id,data,horario, publico, estadio, arbitro, MVP, selecaoCasa, selecaoVisitante, tecnicoCasa, tecnicoVisitante, fase, status, estatistica, eventos, escalacaoCasa, escalacaoVisitante);
        this.prorrogacao = prorrogacao;
        this.penaltis = penaltis;
        this.golsPenaltiCasa = golsPenaltiCasa;
        this.golsPenaltiVisitante = golsPenaltiVisitante;
    }
    public boolean isProrrogacao() {
        return prorrogacao;
    }
    public void setProrrogacao(boolean prorrogacao) {
        this.prorrogacao = prorrogacao;
    }

    public boolean isPenaltis() {
        return penaltis;
    }
    public void setPenaltis(boolean penaltis) {
        this.penaltis = penaltis;
    }

    public int getGolsPenaltiCasa() {
        return golsPenaltiCasa;
    }
    public void setGolsPenaltiCasa(int golsPenaltiCasa) {
        this.golsPenaltiCasa = golsPenaltiCasa;
    }

    public int getGolsPenaltiVisitante() {
        return golsPenaltiVisitante;
    }
    public void setGolsPenaltiVisitante(int golsPenaltiVisitante) {
        this.golsPenaltiVisitante = golsPenaltiVisitante;
    }
}
