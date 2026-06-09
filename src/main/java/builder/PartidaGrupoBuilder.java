package builder;

import Enums.Fase;
import Enums.StatusPartida;
import matches.*;
import nationsAndPlayers.nations.Selecoes;
import nationsAndPlayers.players.Jogadores;
import nationsAndPlayers.players.Tecnico;
import stadiumAndRefeering.Estadio;
import users.Arbitro;

import java.time.LocalDate;
import java.util.ArrayList;

public class PartidaGrupoBuilder {
    protected String grupo;
    protected int rodada;
    protected int id;
    protected LocalDate data;
    protected String horario;
    protected int publico;
    //Gustavo
    protected Estadio estadio;
    protected Arbitro arbitro;
    //Joao
    protected Jogadores MVP;
    protected Selecoes selecaoCasa;
    protected Selecoes selecaoVisitante;
    protected Tecnico tecnicoCasa;
    protected Tecnico tecnicoVisitante;
    //Enums
    protected Fase fase;
    protected StatusPartida status;
    //Placar
    protected int golsCasa;
    protected int golsVisitante;
    //Estatisticas
    protected EstatisticaPartida estatistica;
    //Eventos
    protected ArrayList<EventosOcorridos> eventos;
    //Escalação dos times
    protected Escalacao escalacaoCasa;
    protected Escalacao escalacaoVisitante;

    public PartidaGrupoBuilder grupo(String grupo){
        this.grupo = grupo;
        return this;
    }

    public PartidaGrupoBuilder rodada(int rodada){
        this.rodada = rodada;
        return this;
    }
    public PartidaGrupoBuilder id(int id){
        this.id=id;
        return this;
    }
    public PartidaGrupoBuilder data(LocalDate data){
        this.data=data;
        return this;
    }
    public PartidaGrupoBuilder horario(String horario){
        this.horario=horario;
        return this;
    }
    public PartidaGrupoBuilder publico(int publico){
        this.publico=publico;
        return this;
    }
    public PartidaGrupoBuilder estadio(Estadio estadio){
        this.estadio=estadio;
        return this;
    }
    public PartidaGrupoBuilder arbitro(Arbitro arbitro){
        this.arbitro=arbitro;
        return this;
    }
    public PartidaGrupoBuilder MVP(Jogadores jogador){
        this.MVP=jogador;
        return this;
    }
    public PartidaGrupoBuilder Casa(Selecoes casa){
        this.selecaoCasa=casa;
        return this;
    }
    public PartidaGrupoBuilder Visitante(Selecoes visitante){
        this.selecaoVisitante=visitante;
        return this;
    }
    public PartidaGrupoBuilder tecnicoCasa(Tecnico casa){
        this.tecnicoCasa=casa;
        return this;
    }
    public PartidaGrupoBuilder tecnicoVisitante(Tecnico visitante){
        this.tecnicoVisitante=visitante;
        return this;
    }
    public PartidaGrupoBuilder fase(Fase fase){
        this.fase=fase;
        return this;
    }
    public PartidaGrupoBuilder status(StatusPartida status){
        this.status=status;
        return this;
    }
    public PartidaGrupoBuilder golsVisitante(int golsVisitante){
        this.golsVisitante=golsVisitante;
        return this;
    }
    public PartidaGrupoBuilder golsCasa(int golsCasa){
        this.golsCasa=golsCasa;
        return this;
    }
    public PartidaGrupoBuilder estatistica(EstatisticaPartida estatistica){
        this.estatistica=estatistica;
        return this;
    }
    public PartidaGrupoBuilder eventos(ArrayList<EventosOcorridos> eventos){
        this.eventos = eventos;
        return this;
    }
    public PartidaGrupoBuilder escalacaoCasa(Escalacao casa){
        this.escalacaoCasa=casa;
        return this;
    }
    public PartidaGrupoBuilder escalacaoVisitante(Escalacao escalacaoVisitante){
        this.escalacaoVisitante=escalacaoVisitante;
        return this;
    }
    public PartidaGrupo build(){
        return new PartidaGrupo(id, data, horario, publico, estadio, arbitro, MVP, selecaoCasa, selecaoVisitante,tecnicoCasa,tecnicoVisitante,fase,status, estatistica,eventos, escalacaoCasa, escalacaoVisitante,grupo,rodada);
    }
}
