package services;

import builder.ArbitroBuilder;
import builder.PartidaBuilder;
import builder.SelecaoBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import matches.Partida;
import nationsAndPlayers.nations.Selecoes;
import services.matches.CadastroPartidaService;
import services.matches.CarregaArquivoService;
import stadiumAndRefeering.DesignacaoArbitragem;
import users.Arbitro;

import java.util.ArrayList;
import java.util.List;

public class DesignacaoService {
    private List<Selecoes> ListSelecoes = new ArrayList<>();
    public ObservableList<DesignacaoArbitragem> listaDesignacoes() {
        ListSelecoes=CarregaArquivoService.carregaArquivo("/database/SelecoesNaCopa.txt", parte->new SelecaoBuilder().nome(parte[0]).grupo(parte[1]).build());
        List<DesignacaoArbitragem> lista = CarregaArquivoService.carregaArquivo(
                "/database/partida_grupo.txt",
                parte -> {
                    Arbitro a = new ArbitroBuilder().nome(parte[5]).build();
                  Partida p = new PartidaBuilder().Casa(CadastroPartidaService.buscaPeloNome(ListSelecoes,Selecoes::getNome,parte[7])).Visitante(CadastroPartidaService.buscaPeloNome(ListSelecoes,Selecoes::getNome,parte[8])).build();

                    DesignacaoArbitragem d = new DesignacaoArbitragem();
                    d.setArbitro(a);
                    d.setPartida(p);
                    return d;
                }
        );


        return FXCollections.observableArrayList(lista);
    }
}
