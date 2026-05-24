package builder;

import nationsAndPlayers.nations.Campeoes;

public class CampeoesBuilder {
    private String selecao; //Quem ganhou a copa
    private String ano; //O ano em que isso aconteceu
    private String local; //Em que cidade ocorreu a final, Ex: Brasília,Brasil

    public CampeoesBuilder selecao(String selecao){
        this.selecao=selecao;
        return this;
    }
    public CampeoesBuilder ano(String ano){
        this.ano=ano;
        return this;
    }
    public CampeoesBuilder local(String local){
        this.local=local;
        return this;
    }

    public Campeoes build(){
        return new Campeoes(selecao,ano,local);
    }
}
