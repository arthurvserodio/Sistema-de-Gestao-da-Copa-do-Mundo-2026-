package controller;

import Enums.Fase;
import Enums.Funcao;
import Enums.StatusPartida;
import builder.EstadioBuilder;
import builder.EstatisticaTimeBuilder;
import builder.PartidaGrupoBuilder;
import builder.SelecaoBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import matches.EstatisticaTime;
import matches.Partida;
import nationsAndPlayers.nations.Selecoes;
import services.matches.CadastroPartidaService;
import services.matches.CarregaArquivoService;
import stadiumAndRefeering.Estadio;
import users.Arbitro;
import users.Sessao;
import users.Usuario;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ControllerPartida {
    @FXML
    private VBox containerPartidas;
    @FXML
    private Label labelData;
    private final Map<LocalDate, List<Partida>> partidasPorData = new TreeMap<>();
    private List<LocalDate> datasComPartidas;
    private List<Selecoes> ListSelecoes;
    private List<EstatisticaTime> EstatisticasCASA;
    private List<EstatisticaTime> EstatisticasVISITANTE;
    private List<Estadio> ListEstadio;
    private int indiceDataAtual = 0;

    @FXML
    private void irPaginaInicial(MouseEvent e) {

        Usuario u = Sessao.getInstancia().getUsuarioLogado();
        if (u == null) {
            SceneController.mudaDeTela("/designAndScreens/telaInicial/paginaInicial.fxml");
        } else {
            SceneController.mudaDeTela("/designAndScreens/telasAdministrador/telaPrincipalUsuarios.fxml");
        }
    }//Passa do Menu para a página que conta a historia da copa

    @FXML
    private void irParaHistoria(MouseEvent e) {
        SceneController.mudaDeTela("/designAndScreens/telaInicial/historia.fxml");
    }

    @FXML //Passa do Menu para a tela de equipes presentes na copa de 2026
    private void irParaEquipes(MouseEvent e) {
        SceneController.mudaDeTela("/designAndScreens/telaInicial/equipesNaCopa.fxml");
    }

    @FXML //Passa do Menu para a tela de grupos da copa 2026
    private void irParaClassificacao(MouseEvent e) {
        SceneController.mudaDeTela("/designAndScreens/telaInicial/classificacao.fxml");
    }

    @FXML

    private void irParaEstadios(MouseEvent e) {
        Usuario u = Sessao.getInstancia().getUsuarioLogado();
        if(u == null || u.getFuncao() == Funcao.ARBITRO){
            SceneController.mudaDeTela( "/designAndScreens/telaEstadios/telaEstadioNormal.fxml");
        }
        else{
            SceneController.mudaDeTela( "/designAndScreens/telaEstadios/telaEstadioAdm.fxml");
        }
    }

    @FXML //Helena ta fazendo, depois adiciona o trocaTela + fxml
    private void irParaLogin(ActionEvent e) {
        SceneController.mudaDeTela("/designAndScreens/login/login.fxml");
    }

    @FXML
    private void irParaUsuarios(MouseEvent e){
        SceneController.mudaDeTela( "/designAndScreens/telasAdministrador/usuarios.fxml");
    }

    @FXML
    private void irParaArbitros(MouseEvent e){
        Usuario u = Sessao.getInstancia().getUsuarioLogado();
        if( u == null) {
            SceneController.mudaDeTela("/designAndScreens/Arbitragem/telaArbitroNormal.fxml");
        }else {
            if (u.getFuncao() != Funcao.ARBITRO) {
                SceneController.mudaDeTela("/designAndScreens/Arbitragem/telaArbitroAdm.fxml");
            } else {
                SceneController.mudaDeTela("/designAndScreens/Arbitragem/telaDesignacao.fxml");
            }
        }
    }

    @FXML
    public void initialize() {
        carregarPartidas();
        if (!datasComPartidas.isEmpty()) {
            atualizarTela();
        }
    }

    private void carregarPartidas() {
        ListEstadio=CarregaArquivoService.carregaArquivo("/database/Estadios.txt", parte->new EstadioBuilder().nome(parte[0]).build());
        ListSelecoes = CarregaArquivoService.carregaArquivo("/database/SelecoesNaCopa.txt", parte->new SelecaoBuilder().nome(parte[0]).build());
        List<Partida> partidas = CarregaArquivoService.carregaArquivo("/database/partida_grupo.txt", parte->new PartidaGrupoBuilder().id(Integer.parseInt(parte[0])).data(LocalDate.parse(parte[2])).horario(parte[3]).Casa(CadastroPartidaService.buscaPeloNome(ListSelecoes,Selecoes::getNome,parte[7])).Visitante(CadastroPartidaService.buscaPeloNome(ListSelecoes,Selecoes::getNome,parte[8])).fase(Fase.valueOf(parte[9])).status(StatusPartida.valueOf(parte[10])).estadio(CadastroPartidaService.buscaPeloNome(ListEstadio,Estadio::getNome,parte[4])).build());
        partidas.addAll(CarregaArquivoService.carregaArquivo("/database/partida_eliminatoria.txt", parte->new PartidaGrupoBuilder().id(Integer.parseInt(parte[0])).data(LocalDate.parse(parte[2])).horario(parte[3]).Casa(CadastroPartidaService.buscaPeloNome(ListSelecoes,Selecoes::getNome,parte[6])).Visitante(CadastroPartidaService.buscaPeloNome(ListSelecoes,Selecoes::getNome,parte[7])).fase(Fase.valueOf(parte[8])).status(StatusPartida.valueOf(parte[9])).estadio(CadastroPartidaService.buscaPeloNome(ListEstadio,Estadio::getNome,parte[4])).build()));
        EstatisticasCASA = CarregaArquivoService.carregaArquivo("/database/estatisticas_partida.txt",parte->new EstatisticaTimeBuilder().id(Integer.parseInt(parte[0])).gols(Integer.parseInt(parte[1])).build());
        EstatisticasVISITANTE = CarregaArquivoService.carregaArquivo("/database/estatisticas_partida.txt",parte->new EstatisticaTimeBuilder().id(Integer.parseInt(parte[0])).gols(Integer.parseInt(parte[2])).build());
        for (Partida p : partidas) {
            if(!partidasPorData.containsKey(p.getData())){
                partidasPorData.put(p.getData(), new ArrayList<>());
            }
            partidasPorData.get(p.getData()).add(p);
        }
        datasComPartidas = new ArrayList<>(partidasPorData.keySet());
    }

    private void atualizarTela() {
        LocalDate dataAtual = datasComPartidas.get(indiceDataAtual);
        labelData.setText(formatarData(dataAtual));
        carregarPartidasDoDia(dataAtual);
    }
    //Mostra na tela as partidas que ocorreram naquele dia
    private void carregarPartidasDoDia(LocalDate data) {
        containerPartidas.getChildren().clear();
        List<Partida> partidas = partidasPorData.get(data);
        for (Partida partida : partidas) {
            containerPartidas.getChildren().add(criarLinhaPartida(partida));
        }
    }

    private HBox criarLinhaPartida(Partida partida) {
        HBox linha = new HBox();
        linha.setAlignment(Pos.CENTER_LEFT);
        linha.setSpacing(30);
        linha.setPrefHeight(80);
        linha.setStyle("""
                -fx-background-color: white;
                -fx-background-radius: 15;
                -fx-border-color: #D4A437;
                -fx-border-radius: 15;
                -fx-padding: 15;
                """);
        File is = new File("target/classes/images/Logos/" + partida.getSelecaoCasa().getNome().toLowerCase().replace(" ", "_") + ".png");
        System.out.println(is.getAbsolutePath());
        //Pega o logo da seleção da CASA
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
        //Horario da partida
        Label horario = new Label(partida.getHorario());
        horario.setAlignment(Pos.CENTER);
        horario.getStyleClass().add("horario-label");
        //Nome da seleção CASA
        Label mandante = new Label(partida.getSelecaoCasa().getNome());
        mandante.setStyle(""" 
                -fx-font-size: 16; 
                -fx-text-fill: black;
                -fx-font-family: 'Roboto Condensed Black';""");
        HBox timeCasa = new HBox(12);
        timeCasa.setPrefWidth(300);
        timeCasa.setAlignment(Pos.CENTER_RIGHT);
        timeCasa.getChildren().addAll(mandante,logoSelecao);
        //Placar de Acordo com a fase
        Label placar = new Label();
        if(partida.getStatus().equals(StatusPartida.AGENDADA)){
            placar.setText("VS");
            placar.setPrefWidth(90);
        }
        else if(partida.getStatus().equals(StatusPartida.EM_ANDAMENTO)){
            placar.setText("Ao Vivo");
            placar.setPrefWidth(130);
        }
        else{
            int golsCasa=0,golsVisitante=0;
            for(EstatisticaTime e : EstatisticasCASA){
                if(e.getId()==partida.getId()){
                    golsCasa=e.getGols();
                    break;
                }
            }
            for(EstatisticaTime e : EstatisticasVISITANTE){
                if(e.getId()==partida.getId()){
                    golsVisitante=e.getGols();
                    break;
                }
            }
            placar.setText(golsCasa + " - " + golsVisitante);
            placar.setPrefWidth(90);
        }
        placar.getStyleClass().add("placar-label");
        placar.setOnMouseClicked(event -> {
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/designAndScreens/telasPartidas/UIpartida.fxml"));
                Parent root = loader.load();
                ControllerUiPartida controller = loader.getController();
                controller.setPartida(partida);
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(new Scene(root));
                stage.show();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        });
        //Nome da seleção VISITANTE
        Label visitante = new Label(partida.getSelecaoVisitante().getNome());
        visitante.setStyle(""" 
                -fx-font-size: 16; 
                -fx-text-fill: black;
                -fx-font-family: 'Roboto Condensed Black';""");
        HBox timeVisitante = new HBox(12);
        timeVisitante.setAlignment(Pos.CENTER_LEFT);
        timeVisitante.setPrefWidth(300);
        timeVisitante.getChildren().addAll(logoSelecao2, visitante);
        horario.setPrefWidth(70);
        linha.getChildren().addAll(horario, timeCasa, placar,timeVisitante);
        return linha;
    }
    //Vai para a próxima data que tem jogo
    @FXML
    private void proximaData() {
        if (indiceDataAtual < datasComPartidas.size() - 1) {
            indiceDataAtual++;
            atualizarTela();
        }
    }
    //Volta para a data anterior que tem jogo
    @FXML
    private void dataAnterior() {
        if (indiceDataAtual > 0) {
            indiceDataAtual--;
            atualizarTela();
        }
    }
    //Mostra a data no formato dia/mês/ano
    private String formatarData(LocalDate data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return data.format(formatter);
    }
}
