package builder;

import Enums.Fase;
import Enums.StatusPartida;
import matches.Escalacao;
import matches.EstatisticaPartida;
import matches.EventosOcorridos;
import matches.Partida;
import nationsAndPlayers.nations.Selecoes;
import nationsAndPlayers.players.Jogadores;
import nationsAndPlayers.players.Tecnico;
import stadiumAndRefeering.Estadio;
import users.Arbitro;

import java.time.LocalDate;
import java.util.ArrayList;

public class PartidaBuilder {
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
    //Estatisticas
    protected EstatisticaPartida estatistica;
    //Eventos
    protected ArrayList<EventosOcorridos> eventos;
    //Escalação dos times
    protected Escalacao escalacaoCasa;
    protected Escalacao escalacaoVisitante;

    public PartidaBuilder id(int id){
        this.id=id;
        return this;
    }
    public PartidaBuilder data(LocalDate data){
        this.data=data;
        return this;
    }
    public PartidaBuilder horario(String horario){
        this.horario=horario;
        return this;
    }
    public PartidaBuilder publico(int publico){
        this.publico=publico;
        return this;
    }
    public PartidaBuilder estadio(Estadio estadio){
        this.estadio=estadio;
        return this;
    }
    public PartidaBuilder arbitro(Arbitro arbitro){
        this.arbitro=arbitro;
        return this;
    }
    public PartidaBuilder MVP(Jogadores jogador){
        this.MVP=jogador;
        return this;
    }
    public PartidaBuilder Casa(Selecoes casa){
        this.selecaoCasa=casa;
        return this;
    }
    public PartidaBuilder Visitante(Selecoes visitante){
        this.selecaoVisitante=visitante;
        return this;
    }
    public PartidaBuilder tecnicoCasa(Tecnico casa){
        this.tecnicoCasa=casa;
        return this;
    }
    public PartidaBuilder tecnicoVisitante(Tecnico visitante){
        this.tecnicoVisitante=visitante;
        return this;
    }
    public PartidaBuilder fase(Fase fase){
        this.fase=fase;
        return this;
    }
    public PartidaBuilder status(StatusPartida status){
        this.status=status;
        return this;
    }
    public PartidaBuilder estatistica(EstatisticaPartida estatistica){
        this.estatistica=estatistica;
        return this;
    }
    public PartidaBuilder eventos(ArrayList<EventosOcorridos> eventos){
        this.eventos = eventos;
        return this;
    }
    public PartidaBuilder escalacaoCasa(Escalacao casa){
        this.escalacaoCasa=casa;
        return this;
    }
    public PartidaBuilder escalacaoVisitante(Escalacao escalacaoVisitante){
        this.escalacaoVisitante=escalacaoVisitante;
        return this;
    }


    public Partida build(){
        return new Partida(id, data, horario, publico, estadio, arbitro, MVP, selecaoCasa, selecaoVisitante,tecnicoCasa,tecnicoVisitante,fase,status,estatistica,eventos, escalacaoCasa, escalacaoVisitante);
    }


}
