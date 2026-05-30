package builder;

import nationsAndPlayers.nations.Selecoes;
import nationsAndPlayers.players.Jogadores;

public class JogadorBuilder {

    private String nome;
    private int idade;
    private Selecoes selecao;
    private boolean lesionado;
    private boolean suspenso;

    public JogadorBuilder nome(String nome){
        this.nome = nome;
        return this;
    }

    public JogadorBuilder idade(int idade){
        this.idade = idade;
        return this;
    }
    public JogadorBuilder selecao(Selecoes selecao){
        this.selecao = selecao;
        return this;
    }
    public JogadorBuilder lesionado(boolean lesionado){
        this.lesionado = lesionado;
        return this;
    }
    public JogadorBuilder suspenso(boolean suspenso){
        this.suspenso = suspenso;
        return this;
    }
    
    public Jogadores build(){
        return new Jogadores(nome,idade,selecao,lesionado,suspenso);
    }

}
