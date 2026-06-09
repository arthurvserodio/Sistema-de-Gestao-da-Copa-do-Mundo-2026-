package nationsAndPlayers.players;

import nationsAndPlayers.nations.Selecoes;

public class Jogadores {
    private String nome;
    private int idade;
    private String posicao;
    private int numeracao;
    private Selecoes selecao;
    private boolean lesionado;
    private boolean suspenso;

    public Jogadores(String nome, int idade, Selecoes selecao, boolean lesionado, boolean suspenso) {
        this.nome = nome;
        this.idade = idade;
        this.selecao = selecao;
        this.lesionado = lesionado;
        this.suspenso = suspenso;
    }
    //Criei esse construtor para adicionar a psoição e pq não sei se o construtor de cima é usado em algum lugar
    public Jogadores(String nome, int idade, Selecoes selecao, boolean lesionado, boolean suspenso,String posicao,int numeracao) {
        this.nome = nome;
        this.posicao=posicao;
        this.numeracao=numeracao;
        this.idade = idade;
        this.selecao = selecao;
        this.lesionado = lesionado;
        this.suspenso = suspenso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public Selecoes getSelecao() {
        return selecao;
    }

    public void setSelecao(Selecoes selecao) {
        this.selecao = selecao;
    }

    public boolean isLesionado() {
        return lesionado;
    }

    public void setLesionado(boolean lesionado) {
        this.lesionado = lesionado;
    }

    public boolean isSuspenso() {
        return suspenso;
    }

    public void setSuspenso(boolean suspenso) {
        this.suspenso = suspenso;
    }

    public String getPosicao() {
        return posicao;
    }
    public int getNumeracao() {
        return numeracao;
    }
}
