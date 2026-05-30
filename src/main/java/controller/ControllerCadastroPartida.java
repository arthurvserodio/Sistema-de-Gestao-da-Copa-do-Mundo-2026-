package controller;

import builder.ArbitroBuilder;
import builder.EstadioBuilder;
import builder.SelecaoBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import matches.Partida;
import nationsAndPlayers.nations.Selecoes;
import services.matches.CadastroPartidaService;
import services.matches.CarregaArquivoService;
import stadiumAndRefeering.Estadio;
import users.Arbitro;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class ControllerCadastroPartida {
    private List<Selecoes> ListSelecoes = new ArrayList<>();
    private List<Arbitro> ListArbitros = new ArrayList<>();
    private List<Estadio> ListEstadio = new ArrayList<>();
    private List<Partida> ListPartida = new ArrayList<>();
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
    private DatePicker Data; //Data digitada no Cadastro

    @FXML
    private ComboBox<String> choiceFase;

    @FXML
    private ComboBox<String> choiceGrupo;

    @FXML
    private Button salvarPartida;

    @FXML
    private TextField horario;

    @FXML
    public void initialize() {
        //Desabilita os campos choiceArbitro e choiceEstadio até que o usuario coloque uma data
        choiceArbitro.setDisable(true);
        choiceEstadio.setDisable(true);
        choiceArbitro.setPromptText("Escolha uma data primeiro");
        choiceEstadio.setPromptText("Escolha uma data primeiro");
        //Lendo dos arquivos para obter as Seleções, Árbitros e Estádios
        ListSelecoes = CarregaArquivoService.carregaArquivo("/database/SelecoesNaCopa.txt", parte->new SelecaoBuilder().nome(parte[0]).grupo(parte[1]).build());
        ListArbitros = CarregaArquivoService.carregaArquivo("/database/arbitrosNaCopa.txt", parte->new ArbitroBuilder().nome(parte[0]).build());
        ListEstadio=CarregaArquivoService.carregaArquivo("/database/Estadios.txt", parte->new EstadioBuilder().nome(parte[0]).build());
        //Colocando No ChoiceBox
        choiceSelecao1.getItems().addAll(ListSelecoes);
        choiceSelecao2.getItems().addAll(ListSelecoes);
        choiceArbitro.getItems().addAll(ListArbitros);
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
        //Toda vez que for escrito alguma coisa no text ele chama a função de verificar os estadios disponiveis
        Data.valueProperty().addListener((obs, antigo, novo) -> {
            if(novo==null) {
                return;
            }
            choiceEstadio.setValue(null);
            choiceEstadio.getSelectionModel().clearSelection();
            choiceEstadio.getEditor().clear();

            choiceArbitro.setValue(null);
            choiceArbitro.getSelectionModel().clearSelection();
            choiceArbitro.getEditor().clear();
            // Libera ComboBox
            choiceArbitro.setDisable(false);
            choiceEstadio.setDisable(false);
            choiceArbitro.setPromptText("Selecione um árbitro");
            choiceEstadio.setPromptText("Selecione um estádio");
            partidaService.estadiosDisponivel(ListEstadio, novo,choiceEstadio);
            partidaService.arbitroDisponivel(ListArbitros,novo,choiceArbitro);});
        //Verifica o horário da partida
        salvarPartida.setOnAction(s->{
            //Verificar se tem algum campo vazio
            if(choiceSelecao1.getValue()==null || choiceSelecao2.getValue()==null || choiceArbitro.getValue()==null || choiceEstadio.getValue()==null || choiceFase.getValue()==null || choiceGrupo.getValue()==null || Data.getValue()==null || horario.getText().isBlank()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Atenção");
                alert.setHeaderText(null);
                alert.setContentText("Preencha todos os campos!");
                alert.showAndWait();
                return;
            }
            //Verifica se a Partida já não foi criada
            if(partidaService.partidaJaExiste(choiceSelecao1.getValue(),choiceSelecao2.getValue(),ListPartida)){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Atenção");
                alert.setHeaderText(null);
                alert.setContentText("Essa partida já foi cadastrada!");
                alert.showAndWait();
                return;
            }
            try{
                LocalTime horarioValido= LocalTime.parse(horario.getText());
            }
            catch(DateTimeParseException e){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Atenção");
                alert.setHeaderText(null);
                alert.setContentText("Horário inválido! Use HH:mm");
                alert.showAndWait();
            }
        });
    }
}
