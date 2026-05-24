package builder;

import nationsAndPlayers.nations.Selecoes;

public class SelecaoBuilder {
    private String nome;
    private String grupo;
    private String ranking;
    private String participacao;
    private String titulo;

    public SelecaoBuilder nome(String nome){
        this.nome=nome;
        return this;
    }
    public SelecaoBuilder grupo(String grupo){
        this.grupo=grupo;
        return this;
    }
    public SelecaoBuilder ranking(String ranking){
        this.ranking=ranking;
        return this;
    }
    public SelecaoBuilder participacao(String nome){
        this.participacao=participacao;
        return this;
    }
    public SelecaoBuilder titulo(String nome){
        this.titulo=titulo;
        return this;
    }
    public Selecoes build(){
        return new Selecoes(nome,grupo,ranking,participacao,titulo);
    }
}
