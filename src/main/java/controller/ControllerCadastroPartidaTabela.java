package controller;

import Enums.Fase;
import Enums.Funcao;
import Enums.StatusPartida;
import builder.ArbitroBuilder;
import builder.EstadioBuilder;
import builder.PartidaGrupoBuilder;
import builder.SelecaoBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import matches.EstadoDaCopa;
import matches.Partida;
import matches.PartidaGrupo;
import nationsAndPlayers.nations.Selecoes;
import services.files.SelecoesFile;
import services.matches.CadastroPartidaService;
import services.matches.CarregaArquivoService;
import stadiumAndRefeering.Estadio;
import users.Arbitro;
import users.Sessao;
import users.Usuario;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ControllerCadastroPartidaTabela {
    private List<PartidaGrupo> todasAsPartidas=new ArrayList<>();
    private EstadoDaCopa faseAtual;
    private List<Selecoes> ListSelecoes = new ArrayList<>();
    private List<Arbitro> ListArbitros = new ArrayList<>();
    private List<Estadio> ListEstadio = new ArrayList<>();
    @FXML
    private Button botaoLogin;
    @FXML private MenuButton menuUsuario;
    @FXML private Button botaoCadastrarSelecao;
    //VBOX DE PARTIDAS AGENDADAS
    @FXML
    private VBox listPartida;
    //VBOX DE PARTIDAS AOVIVO
    @FXML
    private VBox aoVivo;
    @FXML private Text botaoUsuario;

    @FXML
    public void initialize() {
        //ATENÇÃO, TENHO QUE RETIRAR ISSO MAIS TARDE
        faseAtual = new EstadoDaCopa(
                Fase.FASE_DE_GRUPOS,
                LocalDate.of(2026,6,11),
                LocalDate.of(2026,6,27)
        );
        ListSelecoes = CarregaArquivoService.carregaArquivo("/database/SelecoesNaCopa.txt", parte->new SelecaoBuilder().nome(parte[0]).grupo(parte[1]).build());
        ListArbitros = CarregaArquivoService.carregaArquivo("/database/arbitrosNaCopa.txt", parte->new ArbitroBuilder().nome(parte[0]).build());
        ListEstadio=CarregaArquivoService.carregaArquivo("/database/Estadios.txt", parte->new EstadioBuilder().nome(parte[0]).local(parte[2]).build());
        if(faseAtual.getFaseAtual()==Fase.FASE_DE_GRUPOS){
            todasAsPartidas = CarregaArquivoService.carregaArquivo("/database/partida_grupo.txt", parte->new PartidaGrupoBuilder().id(Integer.parseInt(parte[0])).data(LocalDate.parse(parte[2])).horario(parte[3]).estadio(CadastroPartidaService.buscaPeloNome(ListEstadio, Estadio::getNome,parte[4])).arbitro(CadastroPartidaService.buscaPeloNome(ListArbitros, Arbitro::getNome,parte[5])).grupo(parte[6]).Casa(CadastroPartidaService.buscaPeloNome(ListSelecoes,Selecoes::getNome,parte[7])).Visitante(CadastroPartidaService.buscaPeloNome(ListSelecoes,Selecoes::getNome,parte[8])).fase(Fase.valueOf(parte[9])).status(StatusPartida.valueOf(parte[10])).build());
        }
        //Mostra as partidas na tabela
        mostraPartida();
        Usuario u = Sessao.getInstancia().getUsuarioLogado();
        if (u != null) {
            menuUsuario.setText(u.getNome() );
            menuUsuario.setVisible(true);
            botaoLogin.setVisible(false);
            if(u.getFuncao() == Funcao.ADMINISTRADOR){
                botaoUsuario.setVisible(true);
                botaoCadastrarSelecao.setVisible(true);
            }
        } else {
            botaoLogin.setVisible(true);
            menuUsuario.setVisible(false);
        }
    }
    //Abre o popUp para cadastrar a partida
    @FXML
    private void irParaCadastro(ActionEvent e) {abrirPopUp();}
    @FXML
    private void onLogout(ActionEvent e){
        Sessao.getInstancia().logout();
        SceneController.mudaDeTela( "/designAndScreens/telaInicial/paginaInicial.fxml");
    }
    @FXML
    private void abrirPopUp(){
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/designAndScreens/telasPartidas/backup.fxml"));
            Parent root = loader.load();
            Stage popup = new Stage();
            popup.initStyle(StageStyle.TRANSPARENT);
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.setScene(new Scene(root));
            popup.showAndWait();
            atualizaTabela();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    private HBox criarLinha(PartidaGrupo partida) {
        HBox linha = new HBox();
        linha.setAlignment(Pos.CENTER_LEFT);
        linha.setSpacing(10);
        linha.setPrefHeight(80);
        linha.setStyle(""" 
                -fx-background-color: white; 
                -fx-border-color: #EEEEEE; 
                -fx-padding: 10 20 10 20;
                """);
        //Tem que ver como faz o upload de imagem, mas deixa para depois
        /*refatoracao para upload de imagens*/
        File is = new File("target/classes/images/Logos/" + partida.getSelecaoCasa().getNome().toLowerCase().replace(" ", "_") + ".png");
        System.out.println(is.getAbsolutePath());

        ImageView logoSelecao;
        if(is.exists()){
            System.out.println("Achei");
            Image imagem=new Image(is.toURI().toString());
            logoSelecao = new ImageView(imagem);
        }
        else {
            System.out.println("Nao achei");
            Image imagemPadrao = new Image(getClass().getResourceAsStream("/images/Logos/brasil.png"));
            logoSelecao = new ImageView(imagemPadrao);
        }
        logoSelecao.setPreserveRatio(true);
        logoSelecao.setFitWidth(50);
        logoSelecao.setFitHeight(50);
        //Pegando os dados da seleção
        // Nome
        Label nome = new Label(partida.getSelecaoCasa().getNome() + "  X");
        nome.setStyle(""" 
                -fx-font-size: 16; 
                -fx-text-fill: black;
                -fx-font-family: 'Inter 18pt Medium';""");

        Label nome2 = new Label(partida.getSelecaoVisitante().getNome());
        nome2.setStyle(""" 
                -fx-font-size: 16; 
                -fx-text-fill: black;
                -fx-font-family: 'Inter 18pt Medium';""");
        //Imagem seleção 2
        /*refatoracao para upload de imagens*/
        is = new File("target/classes/images/Logos/" + partida.getSelecaoVisitante().getNome().toLowerCase().replace(" ", "_") + ".png");
        System.out.println(is.getAbsolutePath());

        ImageView logoSelecao2;
        if(is.exists()){
            System.out.println("Achei");
            Image imagem=new Image(is.toURI().toString());
            logoSelecao2 = new ImageView(imagem);
        }
        else {
            System.out.println("Nao achei");
            Image imagemPadrao = new Image(getClass().getResourceAsStream("/images/Logos/brasil.png"));
            logoSelecao2 = new ImageView(imagemPadrao);
        }
        logoSelecao2.setPreserveRatio(true);
        logoSelecao2.setFitWidth(50);
        logoSelecao2.setFitHeight(50);
        HBox colunaPartida = new HBox(10, logoSelecao, nome, logoSelecao2, nome2);
        colunaPartida.setPrefWidth(390);
        colunaPartida.setAlignment(Pos.CENTER_LEFT);
        //Data
        Image imagem=new Image(getClass().getResourceAsStream("/images/calendar2.png"));
        ImageView calendario = new ImageView(imagem);
        calendario.setFitWidth(15);
        calendario.setFitHeight(15);
        Label data = new Label(partida.getData().toString());
        data.setStyle(""" 
                -fx-font-size: 13; 
                -fx-text-fill: black;
                -fx-font-family: 'Inter 18pt Medium';""");
        HBox colunaData = new HBox(10, calendario, data);
        colunaData.setPrefWidth(210);
        colunaData.setAlignment(Pos.CENTER_LEFT);
        //Horario
        imagem=new Image(getClass().getResourceAsStream("/images/clock3.png"));
        ImageView relogio = new ImageView(imagem);
        relogio.setFitWidth(15);
        relogio.setFitHeight(15);
        Label horario = new Label(partida.getHorario());
        horario.setStyle(""" 
                -fx-font-size: 13; 
                -fx-text-fill: black;
                -fx-font-family: 'Inter 18pt Medium';""");
        HBox colunaHorario = new HBox(10, relogio, horario);
        colunaHorario.setPrefWidth(180);
        colunaHorario.setAlignment(Pos.CENTER_LEFT);
        //estadio
        VBox stadium=new VBox();
        imagem=new Image(getClass().getResourceAsStream("/images/estadio.png"));
        ImageView estadio = new ImageView(imagem);
        estadio.setFitWidth(15);
        estadio.setFitHeight(15);
        Label local = new Label(partida.getEstadio().getLocal());
        Label nomeStadium = new Label(partida.getEstadio().getNome());
        local.setStyle(""" 
                -fx-font-size: 13; 
                -fx-text-fill: #c9c9c9;
                -fx-font-family: 'Inter 18pt Medium';""");
        nomeStadium.setStyle(""" 
                -fx-font-size: 15; 
                -fx-text-fill: black;
                -fx-font-family: 'Inter 18pt Medium';""");
        stadium.setTranslateY(10);
        stadium.getChildren().addAll(nomeStadium,local);
        HBox colunaEstadio = new HBox(10, estadio, stadium);
        colunaEstadio.setPrefWidth(250);
        colunaEstadio.setAlignment(Pos.CENTER_LEFT);
        //Fase
        VBox fase=new VBox();
        Label qualFase;
        Label grupo;
        if(partida.getFase()== Fase.FASE_DE_GRUPOS){
            qualFase=new Label("Fase de Grupos");
            grupo=new Label("Grupo " + partida.getGrupo());
        }
        else if(partida.getFase()== Fase.PLAYOFFS){
            qualFase=new Label("Playoffs");
            grupo=new Label("-");
        }
        else if(partida.getFase()== Fase.OITAVAS){
            qualFase=new Label("Oitavas");
            grupo=new Label("-");
        }
        else if(partida.getFase()== Fase.QUARTAS){
            qualFase=new Label("Quartas");
            grupo=new Label("-");
        }
        else if(partida.getFase()== Fase.SEMIFINAL){
            qualFase=new Label("Semifinal");
            grupo=new Label("-");
        }
        else{
            qualFase=new Label("Final");
            grupo=new Label("-");
        }
        qualFase.setStyle(""" 
                -fx-font-size: 15; 
                -fx-text-fill: black;
                -fx-font-family: 'Inter 18pt Medium';""");

        grupo.setStyle(""" 
                -fx-font-size: 13; 
                -fx-text-fill: #c9c9c9;
                -fx-font-family: 'Inter 18pt Medium';""");

        fase.setTranslateY(10);
        fase.getChildren().addAll(qualFase,grupo);
        HBox colunaFase = new HBox(10, fase);
        colunaFase.setPrefWidth(200);
        colunaFase.setAlignment(Pos.CENTER_LEFT);
        //Ação Editar
        ImageView caneta = new ImageView(new Image(getClass().getResourceAsStream("/images/play-button.png")));
        caneta.setFitWidth(16);
        caneta.setFitHeight(16);
        Button Editar = new Button();
        Editar.setGraphic(caneta);
        Editar.getStyleClass().add("btn-editar");
        //Chama o popUp de Escalação e o seu controller
        Editar.setOnAction(e->{
            if(partida.getStatus()==StatusPartida.AGENDADA){
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/designAndScreens/telasPartidas/popUpEscalacao.fxml"));
                    Parent root = loader.load();
                    ControllerCadastroEscalacao controller = loader.getController();
                    controller.setControllerTabela(this);
                    controller.setPartidaCasa(partida);
                    Stage stage = new Stage();
                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setScene(new Scene(root));
                    stage.show();
                }
                catch (IOException i) {
                    i.printStackTrace();
                }
            }
            else if(partida.getStatus()==StatusPartida.EM_ANDAMENTO){
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/designAndScreens/telasPartidas/estatisticas.fxml"));
                    Parent root = loader.load();
                    Stage stage = new Stage();
                    ControllerCadastroEstatisticas controller= loader.getController();
                    controller.setPartida(partida);
                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setScene(new Scene(root));
                    stage.show();
                }
                catch (IOException i) {
                    i.printStackTrace();
                }
            }
        });
        Button Excluir = new Button();
        HBox colunaAcoes=new HBox(15);
        if(partida.getStatus()==StatusPartida.AGENDADA){
            //Botão de excluir
            ImageView lixo = new ImageView(new Image(getClass().getResourceAsStream("/images/trash.png")));
            lixo.setFitWidth(16);
            lixo.setFitHeight(16);
            Excluir.setGraphic(lixo);
            Excluir.getStyleClass().add("btn-excluir");
            colunaAcoes.getChildren().addAll(Editar,Excluir);
            Excluir.setOnAction(e->{
                //Alerta para não apagar uma partida sem querer
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Excluir Partida");
                alert.setHeaderText("Deseja excluir esta partida?");
                Optional<ButtonType> resultado = alert.showAndWait();
                //Se for OK, exclui
                if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                    excluiDaTabela(partida.getId());
                }
            });
        }else{colunaAcoes.getChildren().addAll(Editar);}

        colunaAcoes.setPrefWidth(120);
        colunaAcoes.setAlignment(Pos.CENTER_LEFT);
        //Colocando todas as labeis na linha
        linha.getChildren().addAll(colunaPartida,colunaData,colunaHorario,colunaEstadio,colunaFase,colunaAcoes);
        return linha;
    }
    @FXML
    public void excluiDaTabela(int id){
        CadastroPartidaService.removePartidaDoTXT(id,"target/classes/database/partida_grupo.txt");
        CadastroPartidaService.removePartidaDoTXT(id,"src/main/resources/database/partida_grupo.txt");
        atualizaTabela();
    }
    //Atualiza a tabela após salvar uma nova partida
    public void atualizaTabela(){
        if(faseAtual.getFaseAtual()==Fase.FASE_DE_GRUPOS){
            todasAsPartidas = CarregaArquivoService.carregaArquivo("/database/partida_grupo.txt", parte->new PartidaGrupoBuilder().id(Integer.parseInt(parte[0])).data(LocalDate.parse(parte[2])).horario(parte[3]).estadio(CadastroPartidaService.buscaPeloNome(ListEstadio, Estadio::getNome,parte[4])).arbitro(CadastroPartidaService.buscaPeloNome(ListArbitros, Arbitro::getNome,parte[5])).grupo(parte[6]).Casa(CadastroPartidaService.buscaPeloNome(ListSelecoes,Selecoes::getNome,parte[7])).Visitante(CadastroPartidaService.buscaPeloNome(ListSelecoes,Selecoes::getNome,parte[8])).fase(Fase.valueOf(parte[9])).status(StatusPartida.valueOf(parte[10])).build());
        }
        mostraPartida();
    }
    private void mostraPartida() {
        //limpa a tela para nao sobrepor visuais que serão atualizados
        listPartida.getChildren().clear();
        aoVivo.getChildren().clear();
        for (PartidaGrupo p : todasAsPartidas) { //alteracao para usar a lista final do SelecoesFile
            HBox linha = criarLinha(p);
            if(p.getStatus()==StatusPartida.AGENDADA){
                listPartida.getChildren().add(linha);
            }
            else if(p.getStatus()==StatusPartida.EM_ANDAMENTO){
                aoVivo.getChildren().add(linha);
            }
        }
    }
}
