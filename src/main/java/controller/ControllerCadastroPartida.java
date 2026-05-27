package controller;

import builder.ArbitroBuilder;
import builder.EstadioBuilder;
import builder.SelecaoBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import nationsAndPlayers.nations.Selecoes;
import services.matches.CadastroPartidaService;
import services.matches.CarregaArquivoService;
import stadiumAndRefeering.Estadio;
import users.Arbitro;

import java.util.ArrayList;
import java.util.List;

public class ControllerCadastroPartida {
    private List<Selecoes> ListSelecoes = new ArrayList<>();
    private List<Arbitro> ListArbitros = new ArrayList<>();
    private List<Estadio> ListEstadio = new ArrayList<>();
    private CadastroPartidaService partidaService = new CadastroPartidaService();
    @FXML
    private ComboBox<Selecoes> choiceSelecao1;

    @FXML
    private ComboBox<Selecoes> choiceSelecao2;

    @FXML
    private ComboBox<Arbitro> choiceArbitro;

    @FXML
    private ComboBox<Estadio> choiceEstadio;

    @FXML
    private ComboBox<String> choiceFase;

    @FXML
    private ComboBox<String> choiceGrupo;

    @FXML
    public void initialize() {
        //Lendo dos arquivos para obter as Seleções, Árbitros e Estádios
        ListSelecoes = CarregaArquivoService.carregaArquivo("/database/SelecoesNaCopa.txt", parte->new SelecaoBuilder().nome(parte[0]).grupo(parte[1]).build());
        ListArbitros=CarregaArquivoService.carregaArquivo("/database/arbitrosNaCopa",parte->new ArbitroBuilder().nome(parte[0]).build());
        ListEstadio=CarregaArquivoService.carregaArquivo("/database/Estadios.txt", parte->new EstadioBuilder().nome(parte[0]).build());
        //Colocando No ChoiceBox
        choiceSelecao1.getItems().addAll(ListSelecoes);
        choiceSelecao2.getItems().addAll(ListSelecoes);
        choiceArbitro.getItems().addAll(ListArbitros);
        choiceEstadio.getItems().addAll(ListEstadio);
        choiceFase.getItems().addAll("Fase De Grupos", "Playoffs","Oitavas-de-finais","Quartas-de-finais","Semi-final","Final");
        choiceGrupo.getItems().addAll("Fase De Grupos", "Playoffs","Oitavas-de-finais","Quartas-de-finais","Semi-final","Final");
        //Atualiza a ComboBox das seleções caso esteja na Fase De Grupos
        //Qualquer mudança feita na comboBox 1
        choiceSelecao1.getSelectionModel().selectedItemProperty().addListener((obs, antigo, novo) -> {
            if(choiceSelecao2.getValue()==null){
                partidaService.atualizarComboBox(choiceSelecao1, choiceSelecao2, ListSelecoes);
            }});
        //Qualquer mudança feita na comboBox 2
        choiceSelecao2.getSelectionModel().selectedItemProperty().addListener((obs, antigo, novo) -> {
            if(choiceSelecao1.getValue()==null){
                partidaService.atualizarComboBox(choiceSelecao1, choiceSelecao2, ListSelecoes);
            }});
    }
}
