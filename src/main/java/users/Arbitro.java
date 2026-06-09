package users;

import matches.Partida;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Arbitro extends Usuario {
    private int experiencia;
    private List<Partida> apitando= new ArrayList<>(); //Usado para armazenar as partidas que um árbitro irá apitar
    public Arbitro(String nome, String funcao, String pais, String status, String senha, int experiencia) {
        super(nome, funcao, pais, status, senha);
        this.experiencia = experiencia;
    }

    public Arbitro(String nome, String pais, int experiencia) {
        super(nome, pais);
        this.experiencia = experiencia;
    }

    public List<Partida> getApitando() {
        return apitando;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    //ToString será necessário para o Combobox do cadastro de Partida
    @Override
    public String toString(){
        return getNome();
    }
    //Se sair True é pq o arbitro esta disponivel e se sair false é pq não ta disponivel
    public boolean estaDisponivel(LocalDate data){
        for(Partida p : apitando){
            if(p.getData().equals(data)){
                return false;
            }
        }
        return true;
    }
}
