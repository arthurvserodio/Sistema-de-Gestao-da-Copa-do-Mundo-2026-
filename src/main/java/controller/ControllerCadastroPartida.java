package controller;

import Enums.Fase;
import Enums.StatusPartida;
import builder.*;
import exceptions.IllegalIntervaloEntrePartidaException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import matches.EstadoDaCopa;
import matches.Partida;
import matches.PartidaEliminatoria;
import matches.PartidaGrupo;
import nationsAndPlayers.nations.Selecoes;
import services.matches.CadastroPartidaService;
import services.matches.CarregaArquivoService;
import stadiumAndRefeering.Estadio;
import users.Arbitro;

import javax.swing.text.html.ImageView;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.spi.CalendarDataProvider;

public class ControllerCadastroPartida {
    private List<Selecoes> ListSelecoes = new ArrayList<>();
    private List<Arbitro> ListArbitros = new ArrayList<>();
    private List<Estadio> ListEstadio = new ArrayList<>();
    private List<Partida> ListPartida = new ArrayList<>();
    private CadastroPartidaService partidaService = new CadastroPartidaService();
    private EstadoDaCopa faseAtual; //Verificação de qual fase está a copa
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
        //Leitura da fae atual da copa
        List<EstadoDaCopa> estadosDaCopa = CarregaArquivoService.carregaArquivo("/database/estado_copa.txt", parte -> new EstadoDaCopa(Fase.valueOf(parte[0]), LocalDate.parse(parte[1]), LocalDate.parse(parte[2])));
        faseAtual = estadosDaCopa.get(0);
        //Desabilita os campos choiceArbitro e choiceEstadio até que o usuario coloque uma data
        choiceArbitro.setDisable(true);
        choiceEstadio.setDisable(true);
        choiceArbitro.setPromptText("Escolha uma data primeiro");
        choiceEstadio.setPromptText("Escolha uma data primeiro");
        //Lendo dos arquivos para obter as Seleções, Árbitros e Estádios
        ListSelecoes = CarregaArquivoService.carregaArquivo("/database/SelecoesNaCopa.txt", parte->new SelecaoBuilder().nome(parte[0]).grupo(parte[1]).build());
        ListArbitros = CarregaArquivoService.carregaArquivo("/database/arbitrosNaCopa.txt", parte->new ArbitroBuilder().nome(parte[0]).pais(parte[1]).build());
        ListEstadio=CarregaArquivoService.carregaArquivo("/database/Estadios.txt", parte->new EstadioBuilder().nome(parte[0]).build());
        if(faseAtual.getFaseAtual()==Fase.FASE_DE_GRUPOS){
            ListPartida = CarregaArquivoService.carregaArquivo("/database/partida_grupo.txt",parte->new PartidaGrupoBuilder().id(Integer.parseInt(parte[0])).data(LocalDate.parse(parte[2])).horario(parte[3]).estadio(CadastroPartidaService.buscaPeloNome(ListEstadio,Estadio::getNome,parte[4])).arbitro(CadastroPartidaService.buscaPeloNome(ListArbitros,Arbitro::getNome,parte[5])).grupo(parte[6]).Casa(CadastroPartidaService.buscaPeloNome(ListSelecoes,Selecoes::getNome,parte[7])).Visitante(CadastroPartidaService.buscaPeloNome(ListSelecoes,Selecoes::getNome,parte[8])).fase(Fase.valueOf(parte[9])).status(StatusPartida.valueOf(parte[10])).build());
        }
        else{
            ListPartida = CarregaArquivoService.carregaArquivo("/database/partida_eliminatoria.txt",parte->new PartidaEliminatoriaBuilder().id(Integer.parseInt(parte[0])).data(LocalDate.parse(parte[2])).horario(parte[3]).estadio(CadastroPartidaService.buscaPeloNome(ListEstadio,Estadio::getNome,parte[4])).arbitro(CadastroPartidaService.buscaPeloNome(ListArbitros,Arbitro::getNome,parte[5])).Casa(CadastroPartidaService.buscaPeloNome(ListSelecoes,Selecoes::getNome,parte[6])).Visitante(CadastroPartidaService.buscaPeloNome(ListSelecoes,Selecoes::getNome,parte[7])).fase(Fase.valueOf(parte[8])).status(StatusPartida.valueOf(parte[9])).build());
        }
        for (Partida p : ListPartida) {
            p.getEstadio().getDatasOcupadas().add(p.getData());
            p.getArbitro().getApitando().add(p);
        }
        //Colocando No ChoiceBox
        choiceSelecao1.getItems().addAll(ListSelecoes);
        choiceArbitro.getItems().addAll(ListArbitros);
        choiceFase.getItems().add(faseAtual.toString());
        if(faseAtual.getFaseAtual()==Fase.FASE_DE_GRUPOS){
            choiceGrupo.getItems().addAll("A", "B","C","D","E","F","G","H","I","J","K","L");
        }
        else{choiceGrupo.setDisable(true);}
        //Atualiza a ComboBox das seleções caso esteja na Fase De Grupos
        //Qualquer mudança feita na comboBox 1
        choiceSelecao1.getSelectionModel().selectedItemProperty().addListener((obs, antigo, novo) -> {
            choiceSelecao2.setValue(null);
            choiceSelecao2.getSelectionModel().clearSelection();
            if(choiceSelecao2.getValue()==null && faseAtual.getFaseAtual()==Fase.FASE_DE_GRUPOS){
                //Só vai deixar o grupo da seleção escolhida na combobox de Grupo
                Selecoes escolhida= choiceSelecao1.getValue();
                if(escolhida==null){
                    return;
                }
                choiceGrupo.getItems().clear();
                choiceGrupo.getItems().add(escolhida.getGrupo());
                Data.setValue(null);
                //Atualiza a outro combobox para ser as seleções presentes no mesmo grupo
                partidaService.atualizarComboBox(choiceSelecao1, choiceSelecao2, ListSelecoes,faseAtual.getFaseAtual());
            }
            else if(choiceSelecao2.getValue()==null && faseAtual.getFaseAtual()!=Fase.FASE_DE_GRUPOS){
                partidaService.atualizarComboBox(choiceSelecao1, choiceSelecao2, ListSelecoes,faseAtual.getFaseAtual());
            }
        });
        //Impede que o usuário escolha uma data fora do escopo da fase atual
        Data.setDayCellFactory(dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    return;
                }
                setDisable(item.isBefore(faseAtual.getInicio()) || item.isAfter(faseAtual.getFim()));}});
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
            partidaService.arbitroDisponivel(ListArbitros,novo,choiceArbitro,choiceSelecao1.getValue().getNome(),choiceSelecao2.getValue().getNome());});
        salvarPartida.setOnAction(s->{
            //Verificar se tem algum campo vazio
            if(choiceSelecao1.getValue()==null || choiceSelecao2.getValue()==null || choiceArbitro.getValue()==null || choiceEstadio.getValue()==null || choiceFase.getValue()==null || (choiceGrupo.getValue()==null && faseAtual.getFaseAtual()==Fase.FASE_DE_GRUPOS) || Data.getValue()==null || horario.getText().isBlank()){
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
            //Verifica o horário da partida
            try{
                LocalTime horarioValido= LocalTime.parse(horario.getText());
            }
            catch(DateTimeParseException e){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Atenção");
                alert.setHeaderText(null);
                alert.setContentText("Horário inválido! Use HH:mm");
                alert.showAndWait();
                return;
            }
            //Verifica se as seleções selecionada podem jogar naquela data
            try {
                partidaService.validarIntervalo(choiceSelecao1.getValue(),Data.getValue(),ListPartida);
                partidaService.validarIntervalo(choiceSelecao2.getValue(),Data.getValue(),ListPartida);
            } catch (IllegalIntervaloEntrePartidaException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Atenção");
                alert.setHeaderText(null);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
                return;
            }
            if(faseAtual.getFaseAtual() == Fase.FASE_DE_GRUPOS) {
                PartidaGrupo partida = new PartidaGrupoBuilder()
                        .id(partidaService.gerarProximoIdGlobal())
                        .data(Data.getValue())
                        .horario(horario.getText())
                        .estadio(choiceEstadio.getValue())
                        .arbitro(choiceArbitro.getValue())
                        .Casa(choiceSelecao1.getValue())
                        .Visitante(choiceSelecao2.getValue())
                        .fase(Fase.FASE_DE_GRUPOS)
                        .status(StatusPartida.AGENDADA)
                        .grupo(choiceGrupo.getValue())
                        .build();
                CadastroPartidaService.salvarPartida(partida, "src/main/resources/database/partida_grupo.txt");
                CadastroPartidaService.salvarPartida(partida, "target/classes/database/partida_grupo.txt");
                choiceArbitro.getValue().getApitando().add(partida);
                choiceEstadio.getValue().getDatasOcupadas().add(partida.getData());
                System.out.println("Partida salva!");
                fecharJanela();
            }
            else{
                PartidaEliminatoria partida = new PartidaEliminatoriaBuilder()
                        .id(partidaService.gerarProximoIdGlobal())
                        .data(Data.getValue())
                        .horario(horario.getText())
                        .estadio(choiceEstadio.getValue())
                        .arbitro(choiceArbitro.getValue())
                        .Casa(choiceSelecao1.getValue())
                        .Visitante(choiceSelecao2.getValue())
                        .fase(faseAtual.getFaseAtual())
                        .status(StatusPartida.AGENDADA)
                        .build();
                CadastroPartidaService.salvarPartida(partida, "src/main/resources/database/partida_eliminatoria.txt");
                CadastroPartidaService.salvarPartida(partida, "target/classes/database/partida_eliminatoria.txt");
                choiceArbitro.getValue().getApitando().add(partida);
                choiceEstadio.getValue().getDatasOcupadas().add(partida.getData());
                System.out.println("Partida salva!");
                fecharJanela();
            }
        });
    }
    //Metodo de fechar a janela usado ao clicar no X ou salvamento com sucesso
    private void fecharJanela() {
        Stage stage = (Stage) salvarPartida.getScene().getWindow();
        stage.close();
    }
    //Fecha o popUp ao clicar no X
    @FXML
    private void fecharPopUp(MouseEvent e){
        fecharJanela();
    }

    @FXML
    //Lógica do botão de cancelar
    private void limparCampos(ActionEvent e) {
        //Limpa todos os campos
        choiceSelecao1.setValue(null);
        choiceSelecao2.setValue(null);
        choiceArbitro.setValue(null);
        choiceEstadio.setValue(null);
        choiceFase.setValue(null);
        choiceGrupo.setValue(null);

        Data.setValue(null);

        horario.clear();

        choiceSelecao2.getItems().clear();

        choiceArbitro.setDisable(true);
        choiceEstadio.setDisable(true);

        choiceArbitro.setPromptText("Escolha uma data primeiro");
        choiceEstadio.setPromptText("Escolha uma data primeiro");
    }
}