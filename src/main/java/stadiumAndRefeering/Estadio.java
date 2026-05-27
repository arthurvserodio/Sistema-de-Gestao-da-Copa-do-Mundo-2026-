package stadiumAndRefeering;


public class Estadio {
    private String nome;
    private int capacidade;
    private String local;

    public Estadio(String nome, int  capacidade, String local) {
        this.nome = nome;
        this.capacidade = capacidade;
        this.local = local;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getNome() { return nome; }
    public int getCapacidade() { return capacidade; }
    public String getLocal() { return local; }
    //ToString será necessário para o Combobox do cadastro de Partida
    public String toString(){
        return nome;
    }
}