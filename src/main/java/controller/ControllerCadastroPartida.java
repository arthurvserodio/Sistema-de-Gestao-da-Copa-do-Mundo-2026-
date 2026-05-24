package controller;

import builder.ArbitroBuilder;
import builder.EstadioBuilder;
import builder.SelecaoBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import nationsAndPlayers.nations.Selecoes;
import services.matches.CarregaArquivoService;
import stadiumAndRefeering.Estadio;
import users.Arbitro;

import java.util.ArrayList;
import java.util.List;

public class ControllerCadastroPartida {
    private List<Selecoes> ListSelecoes = new ArrayList<>();
    private List<Arbitro> ListArbitros = new ArrayList<>();
    private List<Estadio> ListEstadio = new ArrayList<>();
    @FXML
    private ComboBox<String> choiceSelecao1;

    @FXML
    private ComboBox<String> choiceSelecao2;

    @FXML
    private ComboBox<String> choiceArbitro;

    @FXML
    private ComboBox<String> choiceEstadio;

    @FXML
    private ComboBox<String> choiceFase;

    @FXML
    private ComboBox<String> choiceGrupo;

    @FXML
    public void initialize() {
        ListSelecoes = CarregaArquivoService.carregaArquivo("/database/SelecoesNaCopa.txt", parte->new SelecaoBuilder().nome(parte[0]).grupo(parte[1]).build());
        ListArbitros=CarregaArquivoService.carregaArquivo("/database/arbitrosNaCopa",parte->new ArbitroBuilder().nome(parte[0]).build());
        ListEstadio=CarregaArquivoService.carregaArquivo("/database/SelecoesNaCopa.txt", parte->new EstadioBuilder().nome(parte[0]).build());
        choiceSelecao1.getItems().addAll("Brasil", "Argentina", "França", "Alemanha", "Portugal","Brasil","Brasil","Brasil","Brasil","Brasil", "Argentina", "França", "Alemanha", "Portugal","Brasil","Brasil","Brasil","Brasil","Brasil", "Argentina", "França", "Alemanha", "Portugal","Brasil","Brasil","Brasil","Brasil");
    }
}
