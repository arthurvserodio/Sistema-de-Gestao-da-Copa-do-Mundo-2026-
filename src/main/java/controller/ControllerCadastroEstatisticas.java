package controller;

import builder.JogadorBuilder;
import builder.SelecaoBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import matches.JogadorPartida;
import matches.Partida;
import nationsAndPlayers.nations.Selecoes;
import nationsAndPlayers.players.Jogadores;
import services.files.SelecoesFile;
import services.matches.CadastroPartidaService;
import services.matches.CarregaArquivoService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ControllerCadastroEstatisticas {
    private Partida partida;
    private List<Jogadores> todosOsJogadores;
    private List<Jogadores> jogadoresNaPartidaCasa;
    private List<Jogadores> jogadoresNaPartidaVisitante;
    private List<Selecoes> ListSelecoes;
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
    //Vbox da tabela de notas dos jogadores
    @FXML
    private VBox notasCasa;
    @FXML
    private VBox notasVisitante;

    public void setPartida(Partida partida){
        this.partida=partida;
        carregaDados();
        carregarJogadoresDaPartida();
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
}
