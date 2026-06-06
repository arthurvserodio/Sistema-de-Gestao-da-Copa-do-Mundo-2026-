package stadiumAndRefeering;

import matches.Partida;
import users.Arbitro;

public class DesignacaoArbitragem {
    private Partida partida;
    private Arbitro arbitro;

    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    public Arbitro getArbitro() {
        return arbitro;
    }

    public void setArbitro(Arbitro arbitro) {
        this.arbitro = arbitro;
    }
    public String getNomePartida(){
        if ((this.partida.getSelecaoVisitante().getNome() != null) && (this.partida.getSelecaoVisitante().getNome()!= null)) {
            String nome = this.partida.getSelecaoCasa().getNome() + " X " + this.partida.getSelecaoVisitante().getNome();
            return nome;
        }
        else{
            return "Sem partida";
        }

    }

    public String getNomeArbitro(){
        if(this.arbitro != null){
            return  this.arbitro.getNome();
        }
        else{
            return  "Sem arbitro";
        }
    }
}
