package builder;

import stadiumAndRefeering.Estadio;

public class EstadioBuilder {
    private String nome;
    private int capacidade;
    private String local;

    public EstadioBuilder nome(String nome){
        this.nome=nome;
        return this;
    }
    public EstadioBuilder capacidade(int capacidade){
        this.capacidade=capacidade;
        return this;
    }
    public EstadioBuilder local(String local){
        this.local=local;
        return this;
    }

    public Estadio build(){
        return new Estadio(nome,capacidade,local);
    }
}
