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
    private int id;
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
    //Estatisticas
    private EstatisticaPartida estatistica;
    //Eventos
    private ArrayList<EventosOcorridos> eventos;
    //Escalação dos times
    private Escalacao escalacaoCasa;
    private Escalacao escalacaoVisitante;

    public Partida(int id,LocalDate data,String horario, int publico, Estadio estadio, Arbitro arbitro, Jogadores MVP, Selecoes selecaoCasa, Selecoes selecaoVisitante, Tecnico tecnicoCasa, Tecnico tecnicoVisitante, Fase fase, StatusPartida status, EstatisticaPartida estatistica, ArrayList<EventosOcorridos> eventos, Escalacao escalacaoCasa, Escalacao escalacaoVisitante) {
        this.id=id;
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
        this.estatistica = estatistica;
        this.eventos = eventos;
        this.escalacaoCasa = escalacaoCasa;
        this.escalacaoVisitante = escalacaoVisitante;
    }
    public LocalDate getData() {
        return data;
    }
    public Selecoes getSelecaoCasa() {
        return selecaoCasa;
    }

    public Selecoes getSelecaoVisitante() {
        return selecaoVisitante;
    }

    public int getId() {
        return id;
    }

    public String getHorario() {
        return horario;
    }

    public int getPublico() {
        return publico;
    }

    public Estadio getEstadio() {
        return estadio;
    }

    public Arbitro getArbitro() {
        return arbitro;
    }

    public Jogadores getMVP() {
        return MVP;
    }

    public Tecnico getTecnicoCasa() {
        return tecnicoCasa;
    }

    public Tecnico getTecnicoVisitante() {
        return tecnicoVisitante;
    }

    public Fase getFase() {
        return fase;
    }

    public StatusPartida getStatus() {
        return status;
    }

    public EstatisticaPartida getEstatistica() {
        return estatistica;
    }

    public ArrayList<EventosOcorridos> getEventos() {
        return eventos;
    }

    public Escalacao getEscalacaoCasa() {
        return escalacaoCasa;
    }

    public Escalacao getEscalacaoVisitante() {
        return escalacaoVisitante;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEscalacaoCasa(Escalacao escalacaoCasa) {
        this.escalacaoCasa = escalacaoCasa;
    }

    public void setEscalacaoVisitante(Escalacao escalacaoVisitante) {
        this.escalacaoVisitante = escalacaoVisitante;
    }



}
