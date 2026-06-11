package matches;

import Enums.Fase;

import java.time.LocalDate;

public class EstadoDaCopa {
    private Fase faseAtual=Fase.NAO_COMECOU;
    private LocalDate inicio;
    private LocalDate fim;

    public EstadoDaCopa(Fase faseAtual, LocalDate inicio, LocalDate fim){
        this.faseAtual=faseAtual;
        this.inicio=inicio;
        this.fim=fim;
    }
    public Fase getFaseAtual(){
        return faseAtual;
    }
    public LocalDate getInicio(){
        return inicio;
    }
    public LocalDate getFim(){
        return fim;
    }
    public void setInicio(LocalDate inicio){
        this.inicio=inicio;
    }
    public void setFim(LocalDate fim){
        this.fim=fim;
    }
    public void proximoEstado(){
        Fase[] fases= Fase.values(); //Pega as fases e coloca em um array com seu valores numericos
        int proximo= this.faseAtual.ordinal() + 1; //Pega o numero da fase atual e soma 1 para ir para a próxima
        //Se não tiver chegado ao fim do array de fases só muda de fase
        if(proximo < fases.length){
            this.faseAtual=fases[proximo];
        }
        //Se chegou ao fim, volta para o começo
        else{
            this.faseAtual=Fase.NAO_COMECOU;
        }
    }
    public String toString(){
        return getFaseAtual().toString();
    }
}
