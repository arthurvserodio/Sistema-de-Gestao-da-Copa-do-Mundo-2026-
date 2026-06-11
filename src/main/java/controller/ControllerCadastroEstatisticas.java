package controller;

import Enums.Fase;
import Enums.StatusPartida;
import Enums.TipoEvento;
import builder.JogadorBuilder;
import builder.SelecaoBuilder;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import matches.*;
import nationsAndPlayers.nations.Selecoes;
import nationsAndPlayers.players.Jogadores;
import services.matches.CadastroPartidaService;
import services.matches.CarregaArquivoService;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ControllerCadastroEstatisticas {
    private Partida partida;
    private List<Jogadores> todosOsJogadores;
    private List<Jogadores> jogadoresNaPartidaCasa;
    private List<Jogadores> jogadoresNaPartidaVisitante;
    private List<Selecoes> ListSelecoes;
    private List<String[]> TodosOsEventos;
    ControllerCadastroPartidaTabela controllerTabela;
    @FXML
    private ImageView selecaoCasa;
    @FXML
    private ImageView selecaoVisitante;
    @FXML
    private ImageView imageCasa;
    @FXML
    private ImageView imageVisitante;
    @FXML
    private Label casa;
    @FXML
    private Label visitante;

    //Todos Os spinners da estatistica
    @FXML
    private Spinner<Integer> golsCasa,golsVisitante;
    @FXML
    private Spinner<Integer> chutesCasa,chutesVisitante;
    @FXML
    private Spinner<Integer> chutesAGolCasa,chutesAGolVisitante;
    @FXML
    private Spinner<Integer> posseDeBolaCasa,posseDeBolaVisitante;
    @FXML
    private Spinner<Integer> passesCasa,passesVisitante;
    @FXML
    private Spinner<Integer> precisaoPCasa,precisaoPVisitante;
    @FXML
    private Spinner<Integer> faltasCasa,faltasVisitante;
    @FXML
    private Spinner<Integer> amareloCasa,amareloVisitante;
    @FXML
    private Spinner<Integer> vermelhoCasa,vermelhoVisitante;
    @FXML
    private Spinner<Integer> impedimentoCasa,impedimentoVisitante;
    @FXML
    private Spinner<Integer> escanteioCasa,escanteioVisitante;
    private EstadoDaCopa faseAtual; //Verificação de qual fase está a copa
    //Vbox da tabela de notas dos jogadores
    @FXML
    private VBox notasCasa;
    @FXML
    private VBox notasVisitante;
    //VBOX de eventos
    @FXML
    private VBox tabelaEventos;
    //ChoiceBox dos eventos
    @FXML
    private ComboBox<String> eventos;
    @FXML
    private ComboBox<Selecoes> selecoes;
    @FXML
    private ComboBox<Jogadores> jogadores;
    //TextField do evento
    @FXML
    private TextField tempo;
    //Button de adicionar evento
    @FXML
    private Button adicionarEvento;
    //Botão de salvar estatistica
    @FXML
    private Button salvarEstatistica;

    public void setControllerTabela(ControllerCadastroPartidaTabela controllerTabela) {
        this.controllerTabela = controllerTabela;
    }

    public void setPartida(Partida partida){
        this.partida=partida;
        carregaDados();
        carregarJogadoresDaPartida();
        selecoes.getItems().addAll(partida.getSelecaoCasa(),partida.getSelecaoVisitante());
        mostrarJogadores();
    }
    public void carregaDados(){
        casa.setText(partida.getSelecaoCasa().getNome());
        visitante.setText(partida.getSelecaoVisitante().getNome());
        //Seta o logo da seleção da CASA
        File is = new File("target/classes/images/Logos/" + partida.getSelecaoCasa().getNome().toLowerCase().replace(" ", "_") + ".png");
        if(is.exists()){
            System.out.println("Achei");
            Image imagem=new Image(is.toURI().toString());
            selecaoCasa.setImage(imagem);
            imageCasa.setImage(imagem);
        }
        else {
            System.out.println("Nao achei");
            Image imagemPadrao = new Image(getClass().getResourceAsStream("/images/Logos/brasil.png"));
            selecaoCasa.setImage(imagemPadrao);
            imageCasa.setImage(imagemPadrao);
        }
        //Seta o logo da seleção da VISITANTE
        is = new File("target/classes/images/Logos/" + partida.getSelecaoVisitante().getNome().toLowerCase().replace(" ", "_") + ".png");
        if(is.exists()){
            System.out.println("Achei");
            Image imagem=new Image(is.toURI().toString());
            selecaoVisitante.setImage(imagem);
            imageVisitante.setImage(imagem);
        }
        else {
            System.out.println("Nao achei");
            Image imagemPadrao = new Image(getClass().getResourceAsStream("/images/Logos/brasil.png"));
            selecaoVisitante.setImage(imagemPadrao);
            imageVisitante.setImage(imagemPadrao);
        }
    }
    @FXML
    public void initialize(){
        //Leitura da fae atual da copa
        List<EstadoDaCopa> estadosDaCopa = CarregaArquivoService.carregaArquivo("/database/estado_copa.txt", parte -> new EstadoDaCopa(Fase.valueOf(parte[0]), LocalDate.parse(parte[1]), LocalDate.parse(parte[2])));
        faseAtual = estadosDaCopa.get(0);
        //Setando o valor inicial para cada spinner
        golsCasa.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 60, 0));
        golsVisitante.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 60, 0));
        chutesCasa.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 60, 0));
        chutesVisitante.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 60, 0));
        chutesAGolCasa.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 60, 0));
        chutesAGolVisitante.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 60, 0));
        posseDeBolaCasa.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        posseDeBolaVisitante.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        passesCasa.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1300, 0));
        passesVisitante.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1300, 0));
        precisaoPCasa.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        precisaoPVisitante.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        faltasCasa.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, 0));
        faltasVisitante.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, 0));
        amareloCasa.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 0));
        amareloVisitante.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 0));
        vermelhoCasa.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, 0));
        vermelhoVisitante.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, 0));
        impedimentoCasa.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 60, 0));
        impedimentoVisitante.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 60, 0));
        escanteioCasa.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 0));
        escanteioVisitante.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 0));
        //Lendo os jogadores do Arquivo
        ListSelecoes = CarregaArquivoService.carregaArquivo("/database/SelecoesNaCopa.txt", parte->new SelecaoBuilder().nome(parte[0]).build());
        //Carrega todos os jogadores presentes na base de dados
        todosOsJogadores = CarregaArquivoService.carregaArquivo("/database/testeJogadores.txt", parte->new JogadorBuilder().nome(parte[0]).selecao(CadastroPartidaService.buscaPeloNome(ListSelecoes,Selecoes::getNome,parte[2])).posicao(parte[5]).numero(Integer.parseInt(parte[6])).build());
        jogadoresNaPartidaCasa = new ArrayList<>();
        jogadoresNaPartidaVisitante = new ArrayList<>();
        //Carrega elementos na comboBox de eventos
        eventos.getItems().addAll("GOL", "ASSISTENCIA","CARTAO_AMARELO","CARTAO_VERMELHO","PENALTI","GOL_CONTRA","SUBSTITUICAO");
        //Carregando os jogadores daquela seleção
        selecoes.valueProperty().addListener((obs,antigo,novo)->{
            jogadores.getItems().clear();
            if(novo== null) return;
            if(novo.equals(partida.getSelecaoCasa())){
                jogadores.getItems().addAll(jogadoresNaPartidaCasa);
            }
            else{
                jogadores.getItems().addAll(jogadoresNaPartidaVisitante);
            }
        });
        //Excessão para a minutagem do evento
        tempo.focusedProperty().addListener((obj,tinhaFoco,temFoco)->{
            //O usuario ativou o textField e saiu dele,ai fazemos a verificação
            if(!temFoco){
                try{
                    int time = Integer.parseInt(tempo.getText());
                    //Se digitar um numero menor que 0 e maior que 90 dá erro
                    if (time<0 || time>90){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Atenção");
                        alert.setHeaderText(null);
                        alert.setContentText("O tempo deve estar entre 0 e 90.");
                        alert.showAndWait();
                        tempo.clear();
                    }
                }
                //Caso não tenha digitado um numero
                catch(NumberFormatException e){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Atenção");
                    alert.setHeaderText(null);
                    alert.setContentText("Digite um número válido.");
                    alert.showAndWait();
                    tempo.clear();
                }
            }
        });
        //Adicionando Evento
        adicionarEvento.setOnAction(s->{
            if(tempo.getText()==null || eventos.getValue()==null || selecoes.getValue()==null || jogadores.getValue()==null){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Atenção");
                alert.setHeaderText(null);
                alert.setContentText("Preencha todos os campos!");
                alert.showAndWait();
                return;
            }
            CadastroPartidaService.salvarEvento(new EventosOcorridos(Integer.parseInt(tempo.getText()),TipoEvento.valueOf(eventos.getValue()),jogadores.getValue()),partida.getId(),selecoes.getValue(),"src/main/resources/database/eventos.txt");
            CadastroPartidaService.salvarEvento(new EventosOcorridos(Integer.parseInt(tempo.getText()),TipoEvento.valueOf(eventos.getValue()),jogadores.getValue()),partida.getId(),selecoes.getValue(),"target/classes/database/eventos.txt");
            atualizaTabela();
        });
        //Salvando as estatisticas
        salvarEstatistica.setOnAction(s->{
            EstatisticaTime estatCasa = new EstatisticaTime(
                    golsCasa.getValue(),
                    chutesCasa.getValue(),
                    chutesAGolCasa.getValue(),
                    posseDeBolaCasa.getValue(),
                    passesCasa.getValue(),
                    precisaoPCasa.getValue(),
                    faltasCasa.getValue(),
                    amareloCasa.getValue(),
                    vermelhoCasa.getValue(),
                    impedimentoCasa.getValue(),
                    escanteioCasa.getValue()
            );
            EstatisticaTime estatVisitante = new EstatisticaTime(
                    golsVisitante.getValue(),
                    chutesVisitante.getValue(),
                    chutesAGolVisitante.getValue(),
                    posseDeBolaVisitante.getValue(),
                    passesVisitante.getValue(),
                    precisaoPVisitante.getValue(),
                    faltasVisitante.getValue(),
                    amareloVisitante.getValue(),
                    vermelhoVisitante.getValue(),
                    impedimentoVisitante.getValue(),
                    escanteioVisitante.getValue()
            );
            EstatisticaPartida estatisticaPartida = new EstatisticaPartida(estatCasa, estatVisitante);
            partida.setEstatistica(estatisticaPartida);
            CadastroPartidaService.salvarEstatisticaPartida(partida, "src/main/resources/database/estatisticas_partida.txt");
            CadastroPartidaService.salvarEstatisticaPartida(partida, "target/classes/database/estatisticas_partida.txt");

            //Salvando as notas
            List<JogadorPartida> notas = new ArrayList<>();

            // Jogadores da casa
            for(int i = 0; i < jogadoresNaPartidaCasa.size(); i++) {
                //Puxa a nota da HBOX criada
                HBox linha = (HBox) notasCasa.getChildren().get(i);
                Spinner<Double> spinner = (Spinner<Double>) linha.getChildren().get(3);
                notas.add(new JogadorPartida(jogadoresNaPartidaCasa.get(i), spinner.getValue()));
            }
            // Jogadores visitantes
            for(int i = 0; i < jogadoresNaPartidaVisitante.size(); i++) {
                HBox linha = (HBox) notasVisitante.getChildren().get(i);
                Spinner<Double> spinner = (Spinner<Double>) linha.getChildren().get(3);
                notas.add(new JogadorPartida(jogadoresNaPartidaVisitante.get(i), spinner.getValue()));
            }
            CadastroPartidaService.salvarNotasJogadores(partida.getId(), notas, "src/main/resources/database/notas_jogadores.txt");
            CadastroPartidaService.salvarNotasJogadores(partida.getId(), notas, "target/classes/database/notas_jogadores.txt");
            CadastroPartidaService.atualizaStatusDaPartida(partida.getId(),partida,faseAtual);
            controllerTabela.atualizaTabela();
            fecharJanela();
        });
        //mostra as tabelas
        mostrarJogadores();
    }
    private void carregarJogadoresDaPartida(){
        for(Jogadores j:todosOsJogadores){
            //Coloca os jogadores na lista dos jogadores da casa
            if(j.getSelecao().getNome().equalsIgnoreCase(partida.getSelecaoCasa().getNome())){
                jogadoresNaPartidaCasa.add(j);
            }
            else if(j.getSelecao().getNome().equalsIgnoreCase(partida.getSelecaoVisitante().getNome())){
                jogadoresNaPartidaVisitante.add(j);
            }
        }
    }
    private HBox criaLinhaNotaJogadores(Jogadores j){
        HBox linha = new HBox();
        linha.setAlignment(Pos.CENTER_LEFT);
        linha.setSpacing(20);
        linha.setPrefHeight(80);
        linha.setStyle(""" 
                -fx-background-color: white; 
                -fx-border-color: #EEEEEE; 
                -fx-padding: 10 20 10 20;
                """);
        //Numero Jogador
        Label num=new Label(String.valueOf(j.getNumeracao()));
        num.setStyle(""" 
                -fx-font-size: 12; 
                -fx-text-fill: black;
                -fx-font-family: 'Inter 18pt Medium';""");
        num.setPrefWidth(35);
        //Nome jogador
        Label nome=new Label(j.getNome());
        nome.setStyle(""" 
                -fx-font-size: 12; 
                -fx-text-fill: black;
                -fx-font-family: 'Inter 18pt Medium';""");
        nome.setPrefWidth(140);
        //Posição do Jogador
        Label pos=criarTagPosicao(j.getPosicao());
        pos.setPrefWidth(60);
        //Nota
        Spinner<Double> nota = new Spinner<>();
        nota.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 10.0, 0.0, 0.5));
        nota.getStyleClass().add("spinner");
        nota.setPrefWidth(70);
        //Adicionando
        linha.getChildren().addAll(num,nome,pos,nota);
        return linha;
    }
    private Label criarTagPosicao(String posicao){
        Label tag = new Label(posicao);
        tag.setAlignment(Pos.CENTER);
        tag.setPrefSize(50,35);
        switch(posicao){
            case "GOL":
                tag.getStyleClass().add("tag-gol");
                break;
            case "LD":
            case "LE":
                tag.getStyleClass().add("tag-lateral");
                break;
            case "ZAG":
                tag.getStyleClass().add("tag-zagueiro");
                break;
            case "MC":
                tag.getStyleClass().add("tag-volante");
                break;
            case "PD":
            case "PE":
            case "ATA":
                tag.getStyleClass().add("tag-ataque");
                break;
        }
        return tag;
    }
    private void mostrarJogadores(){
        notasCasa.getChildren().clear();
        notasVisitante.getChildren().clear();
        for(Jogadores j: jogadoresNaPartidaCasa){
            HBox linha=criaLinhaNotaJogadores(j);
            notasCasa.getChildren().add(linha);
        }
        for(Jogadores j: jogadoresNaPartidaVisitante){
            HBox linha=criaLinhaNotaJogadores(j);
            notasVisitante.getChildren().add(linha);
        }
    }
    private HBox criaLinhaEventos(String[] e){
        HBox linha = new HBox();
        linha.setAlignment(Pos.CENTER_LEFT);
        linha.setSpacing(20);
        linha.setPrefHeight(40);
        linha.setStyle(""" 
                -fx-background-color: white; 
                -fx-border-color: #EEEEEE; 
                -fx-padding: 10 20 10 20;
                """);
        //id;tipo;tipoDeEvento;minuto;seleção;jogador
        //Minuto
        Label min=new Label(e[3]);
        min.setStyle(""" 
                -fx-font-size: 12; 
                -fx-text-fill: black;
                -fx-font-family: 'Inter 18pt Medium';""");
        min.setPrefWidth(80);
        //Tipo de Evento
        Label tipoE=new Label(e[2]);
        tipoE.setStyle(""" 
                -fx-font-size: 10; 
                -fx-text-fill: black;
                -fx-font-family: 'Inter 18pt Medium';""");
        tipoE.setPrefWidth(140);
        //Selecao
        Label selecao=criarTagPosicao(e[4]);
        selecao.setStyle(""" 
                -fx-font-size: 10; 
                -fx-text-fill: black;
                -fx-font-family: 'Inter 18pt Medium';""");
        selecao.setPrefWidth(120);
        //Jogador
        Label j=new Label(e[5]);
        j.setStyle(""" 
                -fx-font-size: 10; 
                -fx-text-fill: black;
                -fx-font-family: 'Inter 18pt Medium';""");
        j.setPrefWidth(120);
        Button Excluir = new Button();
        //Botão de excluir
        ImageView lixo = new ImageView(new Image(getClass().getResourceAsStream("/images/trash.png")));
        lixo.setFitWidth(12);
        lixo.setFitHeight(12);
        Excluir.setGraphic(lixo);
        Excluir.getStyleClass().add("btn-excluir2");
        Excluir.setOnAction(s->{
            TodosOsEventos.remove(e);
            CadastroPartidaService.reescreverArquivo(TodosOsEventos,"src/main/resources/database/eventos.txt");
            CadastroPartidaService.reescreverArquivo(TodosOsEventos,"target/classes/database/eventos.txt");
            atualizaTabela();
        });
        //Adicionando
        linha.getChildren().addAll(min,tipoE,selecao,j,Excluir);
        return linha;
    }
    //Atualiza a tabela após salvar uma nova partida
    public void atualizaTabela(){
        TodosOsEventos = CarregaArquivoService.carregaArquivo("/database/eventos.txt", partes -> partes);
        mostraEvento();
    }
    public void mostraEvento(){
        tabelaEventos.getChildren().clear();
        for(String[] e : TodosOsEventos){
            if(partida.getId()==Integer.parseInt(e[0])){
                HBox linha = criaLinhaEventos(e);
                tabelaEventos.getChildren().add(linha);
            }
        }
    }
    //Fechando ao clicar no x
    //Metodo de fechar a janela usado ao clicar no X ou salvamento com sucesso
    private void fecharJanela() {
        Stage stage = (Stage) salvarEstatistica.getScene().getWindow();
        stage.close();
    }
    //Fecha o popUp ao clicar no X
    @FXML
    private void fecharPopUp(MouseEvent e){
        fecharJanela();
    }
}