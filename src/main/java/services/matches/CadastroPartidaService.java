package services.matches;

import exceptions.IllegalIntervaloEntrePartidaException;
import javafx.scene.control.ComboBox;
import matches.Partida;
import nationsAndPlayers.nations.Selecoes;
import stadiumAndRefeering.Estadio;
import users.Arbitro;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CadastroPartidaService {
    //O metodo atualizaComboBox faz com que não haja uma exceção em relação a fase de grupos, visto que seleções de grupos diferentes não podem se enfrentar nessa fase
    //Assim, caso o usuario selecione uma seleção em um dos ComboBox destinado a escolha da seleção, a outra ComboBox atualiza e só dá opções de seleções que estão no mesmo grupo da selecionada
    public void atualizarComboBox(ComboBox<Selecoes> origem,ComboBox<Selecoes> destino, List<Selecoes> ListSelecoes){
        Selecoes selecionada= origem.getValue(); //Pega a seleção da comboBox
        if(selecionada==null) return;
        String grupo= selecionada.getGrupo();
        //Filtra a lista de todas as seleções para ver os países que estão no mesmo grupo da selecionada
        List<Selecoes> mesmoGrupo=ListSelecoes.stream().filter(s -> s.getGrupo().equals(grupo) && !s.toString().equals(selecionada.toString())).toList();
        destino.getItems().clear(); //Limpa o que tava na comboBox anteriormente
        destino.getItems().addAll(mesmoGrupo); //Adiciona a nova lista
    }
    //Pega a lista com todos os estadios cadastrados e recebe a data para verificar disponibilidade
    public void estadiosDisponivel(List<Estadio> estadios, LocalDate dataPartida, ComboBox<Estadio> disponiveis){
        disponiveis.getItems().clear();
        for (Estadio e : estadios){
            //Se a data não foi encontrada no set, então é pq tem disponibilidade
            if(!e.getDatasOcupadas().contains(dataPartida)){
                disponiveis.getItems().add(e);
            }
        }
    }
    //Pega a lista com todos os arbitros cadastrados e recebe a data para verificar disponibilidade
    public void arbitroDisponivel(List<Arbitro> arbitros, LocalDate dataPartida, ComboBox<Arbitro> disponiveis,String paisSelecao1,String paisSelecao2){
        disponiveis.getItems().clear();
        for (Arbitro a : arbitros){
            //Se ao chamar o metodo estaDisponivel do arbitro e estiver true, então tem disponibilidade
            boolean estaDisponivel = a.estaDisponivel(dataPartida);
            boolean neutro = !a.getPais().equalsIgnoreCase(paisSelecao1) &&
                    !a.getPais().equalsIgnoreCase(paisSelecao2);
            if (estaDisponivel && neutro) {
                disponiveis.getItems().add(a);
            }
        }
    }
    //Verifica se a partida que está sendo cadastrada já existe
    public boolean partidaJaExiste(Selecoes s1, Selecoes s2, List<Partida> jogos){
        for(Partida p : jogos){
            boolean mesmaOrdem= p.getSelecaoCasa().equals(s1) && p.getSelecaoVisitante().equals(s2); //Verifica se o usuario colocou as seleções na mesma Ordem
            boolean inverteOrdem= p.getSelecaoCasa().equals(s2) && p.getSelecaoVisitante().equals(s1); //Verifica se colocou as seleções em ordem invertida
            if(mesmaOrdem || inverteOrdem){
                return true; //A partida já existe e não podera ser cadastrada outra
            }
        }
        return false; //A partida não existe, pode cadastrar
    }
    //Verifica se o cadastro respeita o intervalo de 72H para uma nova partida de uma seleção estabelecido pela FIFA
    public void validarIntervalo(Selecoes s, LocalDate novaData, List<Partida> jogos) throws IllegalIntervaloEntrePartidaException {
        for(Partida p : jogos){
            if(p.getSelecaoCasa().equals(s) || p.getSelecaoVisitante().equals(s)){
                long dias = Math.abs(ChronoUnit.DAYS.between(p.getData(),novaData));
                if(dias<3){
                    throw new IllegalIntervaloEntrePartidaException(s.getNome() + " jogou no dia " + p.getData() + ". O jogo não poderá ser realizado na data: " + novaData + ", pois a seleção precisa de 3 dias de descanso");
                }
            }
        }
    }
}
