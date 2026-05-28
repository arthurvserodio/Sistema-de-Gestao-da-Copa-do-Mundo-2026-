package stadiumAndRefeering;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Estadio {
    private String nome;
    private int capacidade;
    private String local;
    Set<LocalDate> datasOcupadas=new HashSet<>(); //Utilizado para saber as datas que terá jogo em tal estádio, evita de ocorrer duas partidas no mesmo dia no mesmo estádio

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
    public Set<LocalDate> getDatasOcupadas(){ return datasOcupadas; } //Pega o set de datas ocupadas
    //ToString será necessário para o Combobox do cadastro de Partida
    public String toString(){
        return nome;
    }
}