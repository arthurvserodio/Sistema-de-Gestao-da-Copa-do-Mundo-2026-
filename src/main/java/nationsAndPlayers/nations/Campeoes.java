package nationsAndPlayers.nations;
//Classe usada unicamente para a tela de campeões da copa.
public class Campeoes {
    private String selecao; //Quem ganhou a copa
    private String ano; //O ano em que isso aconteceu
    private String local; //Em que cidade ocorreu a final, Ex: Brasília,Brasil
    public Campeoes(String selecao, String ano, String local) {
        this.selecao = selecao;
        this.ano = ano;
        this.local = local;
    }
    public String getSelecao() {
        return selecao;
    }
    public String getAno() {
        return ano;
    }
    public String getLocal() {
        return local;
    }
}
