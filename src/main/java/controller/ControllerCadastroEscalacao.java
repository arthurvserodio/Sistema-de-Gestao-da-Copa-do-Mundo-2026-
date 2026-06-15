package controller;

import Enums.Fase;
import Enums.StatusPartida;
import builder.JogadorBuilder;
import builder.PartidaGrupoBuilder;
import builder.SelecaoBuilder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import matches.Escalacao;
import matches.EstadoDaCopa;
import matches.JogadorPartida;
import matches.Partida;
import nationsAndPlayers.nations.Selecoes;
import nationsAndPlayers.players.Jogadores;
import services.files.JogadoresFile;
import services.files.SelecoesFile;
import services.matches.CadastroPartidaService;
import services.matches.CarregaArquivoService;
import stadiumAndRefeering.Estadio;
import users.Arbitro;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;

public class ControllerCadastroEscalacao {
    private Partida partida;
    private List<Jogadores> jogadoresDaSelecao=new ArrayList<>();
    private List<Jogadores> ListaJogadores=new ArrayList<>();
    private List<Selecoes> ListSelecoes = new ArrayList<>();
    //Usado para o desenho da formação
    private Map<Jogadores, VBox> jogadoresNoCampo = new HashMap<>();
    private Map<String, Queue<Point2D>> vagas = new HashMap<>(); //Usado para a formação
    private Map<Jogadores, Point2D> posicoesOcupadas = new HashMap<>();
    //Armazena os titulares e reservas
    List<Jogadores> titulares=new ArrayList<>();
    //Armazena qual popUp já foi chamado
    private boolean casaEscalada=false;
    ControllerCadastroPartidaTabela controllerTabela;
    private EstadoDaCopa faseAtual; //Verificação de qual fase está a copa
    @FXML
    private Button btnCancela;
    @FXML
    private Button btnSalvar;
    @FXML
    private VBox listJogadores;
    @FXML
    private ComboBox formacao;
    @FXML
    private ImageView logoSele;
    @FXML
    private Label labelSele;
    @FXML
    private AnchorPane campo;
    @FXML
    public void initialize(){
        //Leitura da fae atual da copa
        List<EstadoDaCopa> estadosDaCopa = CarregaArquivoService.carregaArquivo("/database/estado_copa.txt", parte -> new EstadoDaCopa(Fase.valueOf(parte[0]), LocalDate.parse(parte[1]), LocalDate.parse(parte[2])));
        faseAtual = estadosDaCopa.get(0);
        formacao.getItems().addAll("4-2-3-1","4-2-4","5-2-3");
        ListSelecoes = CarregaArquivoService.carregaArquivo("/database/SelecoesNaCopa.txt", parte->new SelecaoBuilder().nome(parte[0]).grupo(parte[1]).build());
        ListaJogadores=CarregaArquivoService.carregaArquivo("/database/Jogadores.txt", parte-> new JogadorBuilder().nome(parte[0]).selecao(CadastroPartidaService.buscaPeloNome(ListSelecoes,Selecoes::getNome,parte[2])).lesionado(Boolean.parseBoolean(parte[3])).suspenso(Boolean.parseBoolean(parte[4])).numero(Integer.parseInt(parte[6])).posicao(parte[5]).build());
        btnCancela.setOnAction(e -> {
            Stage stage = (Stage) btnCancela.getScene().getWindow();
            stage.close();
        });
        btnSalvar.setOnAction(e->{
            if(jogadoresNoCampo.size()<11){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Atenção");
                alert.setHeaderText(null);
                alert.setContentText("Você deve selecionar exatamente 11 jogadores");
                alert.showAndWait();
                return;
            }
            //Cria o objeto NEW ESCALACAO para não dá NULLPOINTER
            if(!casaEscalada){
                partida.setEscalacaoCasa(new Escalacao(partida.getId(),partida.getSelecaoCasa(),formacao.getValue().toString(),new ArrayList<>(),new ArrayList<>()));
            }
            else{
                partida.setEscalacaoVisitante(new Escalacao(partida.getId(),partida.getSelecaoVisitante(),formacao.getValue().toString(),new ArrayList<>(),new ArrayList<>()));
            }
            for(Jogadores j : titulares){
                if(!casaEscalada){
                    partida.getEscalacaoCasa().getTitulares().add(new JogadorPartida(j,0));
                }
                else{
                    partida.getEscalacaoVisitante().getTitulares().add(new JogadorPartida(j,0));
                }
            }
            for(Jogadores j : jogadoresDaSelecao){
                if(!casaEscalada){
                    if(!titulares.contains(j)){
                        partida.getEscalacaoCasa().getReservas().add(new JogadorPartida(j,0));
                    }
                }
                else{
                    if(!titulares.contains(j)){
                        partida.getEscalacaoVisitante().getReservas().add(new JogadorPartida(j,0));
                    }
                }
            }
            if(!casaEscalada){
                //Abre a tela de Escalação VISITANTE
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/designAndScreens/telasPartidas/popUpEscalacao.fxml"));
                    Parent root = loader.load();
                    ControllerCadastroEscalacao controller = loader.getController();
                    controller.setPartidaVisitante(partida);
                    controller.setControllerTabela(controllerTabela);
                    Stage stage = new Stage();
                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setScene(new Scene(root));
                    stage.show();
                }
                catch (IOException i) {
                    i.printStackTrace();
                }
                //Fecha a tela de escalação da CASA
                Stage stage = (Stage) btnSalvar.getScene().getWindow();
                stage.close();
            }
            else{
                //Mensagem de sucesso
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso");
                alert.setHeaderText(null);
                alert.setContentText("Escalações salvas com sucesso!");
                alert.showAndWait();
                CadastroPartidaService.salvarEscalacao(partida,"src/main/resources/database/escalacao.txt");
                CadastroPartidaService.salvarEscalacao(partida,"target/classes/database/escalacao.txt");
                CadastroPartidaService.atualizaStatusDaPartida(partida.getId(),partida,faseAtual);
                controllerTabela.atualizaTabela();
                //Lógica para salvar no arquivo
                //fecha a tela
                Stage stage = (Stage) btnSalvar.getScene().getWindow();
                stage.close();
            }
        });
        formacao.setOnAction(e->{
            limpaEscalacao();//Usado para caso o usuario escalar o time e depois trocar a formação
            //Configuração para o 4-2-3-1
            if(formacao.getValue().equals("4-2-3-1")){
                formacao4231();
            }
            //Configuração para o 4-2-4
            else if(formacao.getValue().equals("4-2-4")){
                formacao424();
            }
            //Configuração para o 5-2-3
            else{
                formacao523();
            }
        });
    }
    public void setControllerTabela(ControllerCadastroPartidaTabela controllerTabela) {
        this.controllerTabela = controllerTabela;
    }
    //Vai ser 2 PopUps, 1 para a seleção da Casa e outro para a Visitante, por isso a separação dos Sets
    public void setPartidaCasa(Partida partida){
        this.partida = partida;
        //Seta o nome da seleção na escalação
        labelSele.setText(partida.getSelecaoCasa().getNome());
        //Seta o logo da seleção na escalação
        File is = new File("target/classes/images/Logos/" + partida.getSelecaoCasa().getNome().toLowerCase().replace(" ", "_") + ".png");
        System.out.println(is.getAbsolutePath());
        if(is.exists()){
            System.out.println("Achei");
            Image imagem=new Image(is.toURI().toString());
            logoSele.setImage(imagem);
        }
        else {
            System.out.println("Nao achei");
            Image imagemPadrao = new Image(getClass().getResourceAsStream("/images/Logos/brasil.png"));
            logoSele= new javafx.scene.image.ImageView(imagemPadrao);
        }
        for(Jogadores j: ListaJogadores){
            if(j.getSelecao().getNome().equalsIgnoreCase(partida.getSelecaoCasa().getNome())){
                jogadoresDaSelecao.add(j);
            }
        }
        //VAI SER USADO,APÓS MUDANÇAS DO JOÃO
        /*for(Jogadores j: JogadoresFile.getInstancia().getListaJogadores()){
            if(j.getSelecao().getNome().equalsIgnoreCase(partida.getSelecaoCasa().getNome())){
                jogadoresDaSelecao.add(j);
            }
        }*/
        mostrarJogadores();
    }
    //Seleção Visitante
    public void setPartidaVisitante(Partida partida){
        casaEscalada=true;
        this.partida = partida;
        labelSele.setText(partida.getSelecaoVisitante().getNome());
        //Seta o logo da seleção na escalação
        File is = new File("target/classes/images/Logos/" + partida.getSelecaoVisitante().getNome().toLowerCase().replace(" ", "_") + ".png");
        System.out.println(is.getAbsolutePath());
        if(is.exists()){
            System.out.println("Achei");
            Image imagem=new Image(is.toURI().toString());
            logoSele.setImage(imagem);
        }
        else {
            System.out.println("Nao achei");
            Image imagemPadrao = new Image(getClass().getResourceAsStream("/images/Logos/brasil.png"));
            logoSele= new javafx.scene.image.ImageView(imagemPadrao);
        }
        for(Jogadores j: ListaJogadores){
            if(j.getSelecao().getNome().equalsIgnoreCase(partida.getSelecaoVisitante().getNome())){
                jogadoresDaSelecao.add(j);
            }
        }
        /*for(Jogadores j: JogadoresFile.getInstancia().getListaJogadores()){
            if(j.getSelecao().getNome().equalsIgnoreCase(partida.getSelecaoVisitante().getNome())){
                jogadoresDaSelecao.add(j);
            }
        }*/
        mostrarJogadores();
    }
    private HBox criaLinhaJogadores(Jogadores j){
        HBox linha = new HBox();
        linha.setAlignment(Pos.CENTER_LEFT);
        linha.setSpacing(20);
        linha.setPrefHeight(80);
        linha.setStyle(""" 
                -fx-background-color: white; 
                -fx-border-color: #EEEEEE; 
                -fx-padding: 10 20 10 20;
                """);
        //Posição dos jogadores
        Label pos=criarTagPosicao(j.getPosicao());
        pos.setPrefWidth(50);
        //Nome do jogador
        Label nome = new Label(j.getNome());
        nome.setStyle(""" 
                -fx-font-size: 16; 
                -fx-text-fill: black;
                -fx-font-family: 'Inter 18pt Medium';""");
        nome.setPrefWidth(150);
        //Lesão
        ImageView lesao=new ImageView();
        lesao.setFitWidth(18);
        lesao.setFitHeight(18);
        lesao.setStyle("-fx-border-color: red;");
        if(j.isLesionado()){
            Image imagem = new Image(getClass().getResourceAsStream("/images/hospital.png"));
            lesao.setImage(imagem);
            lesao.setPreserveRatio(true);
        }
        //Expulso
        ImageView expulso=new ImageView();
        expulso.setFitWidth(18);
        expulso.setFitHeight(18);
        if(j.isSuspenso()){
            Image imagem = new Image(getClass().getResourceAsStream("/images/red.png"));
            expulso.setImage(imagem);
            expulso.setPreserveRatio(true);
        }
        HBox jogador=new HBox(5);
        jogador.setAlignment(Pos.CENTER_LEFT);
        jogador.getChildren().addAll(nome,lesao,expulso);
        //Número da camisa
        Label num=new Label(String.valueOf(j.getNumeracao()));
        num.setStyle(""" 
                -fx-font-size: 16; 
                -fx-text-fill: black;
                -fx-font-family: 'Inter 18pt Medium';""");
        num.setPrefWidth(60);
        //CheckBox para confirmar se é titular ou não
        CheckBox cheak=new CheckBox();
        cheak.setUserData(j);
        cheak.setOnAction(e->{
            if(formacao.getValue()==null){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Atenção");
                alert.setHeaderText(null);
                alert.setContentText("Selecione uma Formação primeiro");
                alert.showAndWait();
                cheak.setSelected(false);
            }
            if(j.isLesionado() || j.isSuspenso()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Atenção");
                alert.setHeaderText(null);
                alert.setContentText("O jogador não pode ser selecionado para entrar em campo, pois está lesionado ou expulso");
                alert.showAndWait();
                cheak.setSelected(false);
            }
            if(cheak.isSelected()){
                Queue<Point2D> fila=vagas.get(j.getPosicao());
                if(fila.isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Atenção");
                    alert.setHeaderText(null);
                    alert.setContentText("Não há mais vagas para a posição " + j.getPosicao());
                    alert.showAndWait();
                    cheak.setSelected(false);
                }
                if(fila!=null && !fila.isEmpty()){
                    Point2D p = fila.poll();
                    VBox visual=CadastroPartidaService.adicionandoNoCampo(j,campo,p.getX(),p.getY());
                    jogadoresNoCampo.put(j,visual);
                    posicoesOcupadas.put(j,p);
                    titulares.add(j);
                }
            }
            else{
                VBox visual=jogadoresNoCampo.get(j);
                CadastroPartidaService.removeJogadorDoCampo(visual,campo);
                jogadoresNoCampo.remove(j);
                titulares.remove(j);
                Point2D abriuVaga=posicoesOcupadas.remove(j);
                if(abriuVaga != null){
                    vagas.get(j.getPosicao()).add(abriuVaga);
                }
            }
        });
        linha.getChildren().addAll(pos,jogador,num,cheak);
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
        listJogadores.getChildren().clear();
        for(Jogadores j: jogadoresDaSelecao){
            HBox linha=criaLinhaJogadores(j);
            listJogadores.getChildren().add(linha);
        }
    }
    //Limpa a escalação e o MAP
    private void limpaEscalacao(){
        //Limpando todos os MAPS
        jogadoresNoCampo.clear();
        posicoesOcupadas.clear();
        vagas.clear();
        titulares.clear();
        //Limpando visualmente o anchorPanel
        campo.getChildren().clear();
        //Limpando as cheaks box
        for(Node node : listJogadores.getChildren()){
            if(node instanceof HBox linha){
                for(Node filho : linha.getChildren()){
                    if(filho instanceof CheckBox cheak){
                        cheak.setSelected(false);
                    }
                }
            }
        }
    }
    //CRIANDO AS FORMAÇÕES QUE APARECEM
    private void formacao4231(){
        vagas.clear();
        vagas.put("GOL",new LinkedList<>(List.of(new Point2D(160,345))));
        vagas.put("ZAG",new LinkedList<>(List.of(new Point2D(85,300),new Point2D(210,300))));
        vagas.put("LE",new LinkedList<>(List.of(new Point2D(0,280))));
        vagas.put("LD",new LinkedList<>(List.of(new Point2D(315,280))));
        vagas.put("MC",new LinkedList<>(List.of(new Point2D(85,190),new Point2D(210,190),new Point2D(143,100))));
        vagas.put("PE",new LinkedList<>(List.of(new Point2D(0,100))));
        vagas.put("PD",new LinkedList<>(List.of(new Point2D(315,100))));
        vagas.put("ATA",new LinkedList<>(List.of(new Point2D(145,0))));
    }
    private void formacao424(){
        vagas.clear();
        vagas.put("GOL",new LinkedList<>(List.of(new Point2D(160,345))));
        vagas.put("ZAG",new LinkedList<>(List.of(new Point2D(85,300),new Point2D(210,300))));
        vagas.put("LE",new LinkedList<>(List.of(new Point2D(0,280))));
        vagas.put("LD",new LinkedList<>(List.of(new Point2D(315,280))));
        vagas.put("MC",new LinkedList<>(List.of(new Point2D(85,190),new Point2D(210,190))));
        vagas.put("PE",new LinkedList<>(List.of(new Point2D(0,100))));
        vagas.put("PD",new LinkedList<>(List.of(new Point2D(315,100))));
        vagas.put("ATA",new LinkedList<>(List.of(new Point2D(95,0),new Point2D(220,0))));
    }
    private void formacao523(){
        vagas.clear();
        vagas.put("GOL",new LinkedList<>(List.of(new Point2D(160,345))));
        vagas.put("ZAG",new LinkedList<>(List.of(new Point2D(70,270),new Point2D(230,270),new Point2D(150,270))));
        vagas.put("LE",new LinkedList<>(List.of(new Point2D(0,230))));
        vagas.put("LD",new LinkedList<>(List.of(new Point2D(315,230))));
        vagas.put("MC",new LinkedList<>(List.of(new Point2D(85,170),new Point2D(210,170))));
        vagas.put("PE",new LinkedList<>(List.of(new Point2D(0,100))));
        vagas.put("PD",new LinkedList<>(List.of(new Point2D(315,100))));
        vagas.put("ATA",new LinkedList<>(List.of(new Point2D(145,0))));
    }
}
