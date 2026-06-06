package services;

import builder.ArbitroBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import matches.Partida;
import services.matches.CarregaArquivoService;
import stadiumAndRefeering.DesignacaoArbitragem;
import users.Arbitro;

import java.util.List;

public class DesignacaoService {
    public ObservableList<DesignacaoArbitragem> listaDesignacoes() {

        List<DesignacaoArbitragem> lista = CarregaArquivoService.carregaArquivo(
                "/database/partidas.txt",
                parte -> {
                    Arbitro a = new ArbitroBuilder().nome(parte[1]).build();
//                    Partida p = new PartidaBuilder().selecaoCasa(parte[]).selecaoFora(parte[1]).build();

                    DesignacaoArbitragem d = new DesignacaoArbitragem();
                    d.setArbitro(a);
//                    d.setPartida(p);
                    return d;
                }
        );


        return FXCollections.observableArrayList(lista);
    }
}
