package nationsAndPlayers.nations;

public class Selecoes {
    private String nome;
    private String grupo;
    private String ranking;
    private String participacao;
    private String titulo;

    public Selecoes(String nome, String grupo, String ranking, String participacao, String titulo){
        this.nome=nome;
        this.grupo=grupo;
        this.ranking=ranking;
        this.participacao=participacao;
        this.titulo=titulo;
    }

    public String getGrupo() {
        return grupo;
    }
    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getParticipacao() {
        return participacao;
    }

    public void setParticipacao(String participacao) {
        this.participacao = participacao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    //ToString será necessário para o Combobox do cadastro de Partida
    @Override
    public String toString(){
        return nome;
    }
}
