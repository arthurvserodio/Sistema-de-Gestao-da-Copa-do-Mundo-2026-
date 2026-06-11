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

public class PartidaEliminatoriaBuilder {
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
    private boolean prorrogacao;
    private boolean penaltis;
    private int golsPenaltiCasa;
    private int golsPenaltiVisitante;
    public PartidaEliminatoriaBuilder prorrogacao(boolean prorrogacao){
        this.prorrogacao=prorrogacao;
        return this;
    }
    public PartidaEliminatoriaBuilder penaltis(boolean penaltis){
        this.penaltis=penaltis;
        return this;
    }
    public PartidaEliminatoriaBuilder golsPenaltiCasa(int golsPenaltiCasa){
        this.golsPenaltiCasa=golsPenaltiCasa;
        return this;
    }
    public PartidaEliminatoriaBuilder golsPenaltiVisitante(int golsPenaltiVisitante){
        this.golsPenaltiVisitante=golsPenaltiVisitante;
        return this;
    }
    public PartidaEliminatoriaBuilder id(int id){
        this.id=id;
        return this;
    }
    public PartidaEliminatoriaBuilder data(LocalDate data){
        this.data=data;
        return this;
    }
    public PartidaEliminatoriaBuilder horario(String horario){
        this.horario=horario;
        return this;
    }
    public PartidaEliminatoriaBuilder publico(int publico){
        this.publico=publico;
        return this;
    }
    public PartidaEliminatoriaBuilder estadio(Estadio estadio){
        this.estadio=estadio;
        return this;
    }
    public PartidaEliminatoriaBuilder arbitro(Arbitro arbitro){
        this.arbitro=arbitro;
        return this;
    }
    public PartidaEliminatoriaBuilder MVP(Jogadores jogador){
        this.MVP=jogador;
        return this;
    }
    public PartidaEliminatoriaBuilder Casa(Selecoes casa){
        this.selecaoCasa=casa;
        return this;
    }
    public PartidaEliminatoriaBuilder Visitante(Selecoes visitante){
        this.selecaoVisitante=visitante;
        return this;
    }
    public PartidaEliminatoriaBuilder tecnicoCasa(Tecnico casa){
        this.tecnicoCasa=casa;
        return this;
    }
    public PartidaEliminatoriaBuilder tecnicoVisitante(Tecnico visitante){
        this.tecnicoVisitante=visitante;
        return this;
    }
    public PartidaEliminatoriaBuilder fase(Fase fase){
        this.fase=fase;
        return this;
    }
    public PartidaEliminatoriaBuilder status(StatusPartida status){
        this.status=status;
        return this;
    }
    public PartidaEliminatoriaBuilder golsVisitante(int golsVisitante){
        this.golsVisitante=golsVisitante;
        return this;
    }
    public PartidaEliminatoriaBuilder golsCasa(int golsCasa){
        this.golsCasa=golsCasa;
        return this;
    }
    public PartidaEliminatoriaBuilder estatistica(EstatisticaPartida estatistica){
        this.estatistica=estatistica;
        return this;
    }
    public PartidaEliminatoriaBuilder eventos(ArrayList<EventosOcorridos> eventos){
        this.eventos = eventos;
        return this;
    }
    public PartidaEliminatoriaBuilder escalacaoCasa(Escalacao casa){
        this.escalacaoCasa=casa;
        return this;
    }
    public PartidaEliminatoriaBuilder escalacaoVisitante(Escalacao escalacaoVisitante){
        this.escalacaoVisitante=escalacaoVisitante;
        return this;
    }
    public PartidaEliminatoria build(){
        return new PartidaEliminatoria(id, data, horario, publico, estadio, arbitro, MVP, selecaoCasa, selecaoVisitante,tecnicoCasa,tecnicoVisitante,fase,status, estatistica,eventos, escalacaoCasa, escalacaoVisitante,prorrogacao, penaltis, golsPenaltiCasa, golsPenaltiVisitante);
    }
}
