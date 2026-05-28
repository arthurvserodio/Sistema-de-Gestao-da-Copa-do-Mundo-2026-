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

public class PartidaGrupo extends Partida{
    private String grupo;
    private int rodada;

    public PartidaGrupo(LocalDate data, String horario, int publico, Estadio estadio, Arbitro arbitro, Jogadores MVP, Selecoes selecaoCasa, Selecoes selecaoVisitante, Tecnico tecnicoCasa, Tecnico tecnicoVisitante, Fase fase, StatusPartida status, int golsCasa, int golsVisitante, EstatisticaPartida estatistica, ArrayList<EventosOcorridos> eventos, Escalacao escalacaoCasa, Escalacao escalacaoVisitante, String grupo, int rodada) {
        super(data,horario, publico, estadio, arbitro, MVP, selecaoCasa, selecaoVisitante, tecnicoCasa, tecnicoVisitante, fase, status, golsCasa, golsVisitante, estatistica, eventos, escalacaoCasa, escalacaoVisitante);
        this.grupo = grupo;
        this.rodada = rodada;
    }
    public String getGrupo() {
        return grupo;
    }
    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public int getRodada() {
        return rodada;
    }

    public void setRodada(int rodada) {
        this.rodada = rodada;
    }
}
