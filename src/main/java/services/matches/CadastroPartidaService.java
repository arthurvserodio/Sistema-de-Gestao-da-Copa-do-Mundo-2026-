package services.matches;

import javafx.scene.control.ComboBox;
import nationsAndPlayers.nations.Selecoes;

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
}
