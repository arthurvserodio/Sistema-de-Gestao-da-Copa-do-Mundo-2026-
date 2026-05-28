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

public class Partida {
    private LocalDate data;
    private String horario;
    private int publico;
    //Gustavo
    private Estadio estadio;
    private Arbitro arbitro;
    //Joao
    private Jogadores MVP;
    private Selecoes selecaoCasa;
    private Selecoes selecaoVisitante;
    private Tecnico tecnicoCasa;
    private Tecnico tecnicoVisitante;
    //Enums
    private Fase fase;
    private StatusPartida status;
    //Placar
    private int golsCasa;
    private int golsVisitante;
    //Estatisticas
    private EstatisticaPartida estatistica;
    //Eventos
    private ArrayList<EventosOcorridos> eventos;
    //Escalação dos times
    private Escalacao escalacaoCasa;
    private Escalacao escalacaoVisitante;

    public Partida(LocalDate data,String horario, int publico, Estadio estadio, Arbitro arbitro, Jogadores MVP, Selecoes selecaoCasa, Selecoes selecaoVisitante, Tecnico tecnicoCasa, Tecnico tecnicoVisitante, Fase fase, StatusPartida status, int golsCasa, int golsVisitante, EstatisticaPartida estatistica, ArrayList<EventosOcorridos> eventos, Escalacao escalacaoCasa, Escalacao escalacaoVisitante) {
        this.data = data;
        this.horario=horario;
        this.publico = publico;
        this.estadio = estadio;
        this.arbitro = arbitro;
        this.MVP = MVP;
        this.selecaoCasa = selecaoCasa;
        this.selecaoVisitante = selecaoVisitante;
        this.tecnicoCasa = tecnicoCasa;
        this.tecnicoVisitante = tecnicoVisitante;
        this.fase = fase;
        this.status = status;
        this.golsCasa = golsCasa;
        this.golsVisitante = golsVisitante;
        this.estatistica = estatistica;
        this.eventos = eventos;
        this.escalacaoCasa = escalacaoCasa;
        this.escalacaoVisitante = escalacaoVisitante;
    }
    public LocalDate getData() {
        return data;
    }
}
