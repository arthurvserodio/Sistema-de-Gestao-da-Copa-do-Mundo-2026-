package controller;

import Enums.Fase;
import Enums.StatusPartida;
import Enums.TipoEvento;
import builder.*;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import matches.*;
import nationsAndPlayers.nations.Selecoes;
import nationsAndPlayers.players.Jogadores;
import services.matches.CadastroPartidaService;
import services.matches.CarregaArquivoService;
import services.matches.EstatisticaService;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import stadiumAndRefeering.Estadio;

import java.io.File;
import java.time.LocalDate;
import java.util.*;

import static services.matches.CadastroPartidaService.adicionandoNoCampoPartida;

public class ControllerUiPartida {
    private Partida partida;
    @FXML
    private Label Casa;
    @FXML
    private Label Visitante;
    @FXML
    private Label data;
    @FXML
    private Label estadio;
    @FXML
    private Label fase;
    @FXML
    private Label golsC;
    @FXML
    private Label golsV;
    @FXML
    private Text formC;
    @FXML
    private Text formV;
    @FXML
    private AnchorPane escalacaoC;
    @FXML
    private AnchorPane escalacaoV;
    @FXML
    private ImageView volta;
    //Estatisticas
    @FXML
    private Label lblPosseCasa;
    @FXML
    private Label lblPosseVisitante;
    @FXML
    private Label lblPassesCasa;
    @FXML
    private Label lblPassesVisitante;
    @FXML
    private Label lblFinalizacoesCasa;
    @FXML
    private Label lblFinalizacoesVisitante;
    @FXML
    private Label lblFinalizacoesAlvoCasa;
    @FXML
    private Label lblFinalizacoesAlvoVisitante;
    @FXML
    private Label lblGolsCasa;
    @FXML
    private Label lblGolsVisitante;
    @FXML
    private Label lblPrecisaoCasa;
    @FXML
    private Label lblPrecisaoVisitante;
    @FXML
    private Label lblFaltasCasa;
    @FXML
    private Label lblFaltasVisitante;
    @FXML
    private Label lblAmarelosCasa;
    @FXML
    private Label lblAmarelosVisitante;
    @FXML
    private Label lblVermelhosCasa;
    @FXML
    private Label lblVermelhosVisitante;
    @FXML
    private Label lblEscanteiosCasa;
    @FXML
    private Label lblEscanteiosVisitante;
    @FXML
    private Label lblImpedimentosCasa;
    @FXML
    private Label lblImpedimentosVisitante;
    //Progress Bar
    @FXML
    private ProgressBar pbGols;

    @FXML
    private ProgressBar pbPosse;

    @FXML
    private ProgressBar pbFinalizacoes;

    @FXML
    private ProgressBar pbFinalizacoesAlvo;

    @FXML
    private ProgressBar pbPasses;

    @FXML
    private ProgressBar pbPrecisao;

    @FXML
    private ProgressBar pbFaltas;

    @FXML
    private ProgressBar pbAmarelos;

    @FXML
    private ProgressBar pbVermelhos;

    @FXML
    private ProgressBar pbImpedimentos;

    @FXML
    private ProgressBar pbEscanteios;

    //Eventos
    @FXML
    private VBox boxEventos;
    //Listas
    private List<EventosOcorridos> eventos;
    private List<Selecoes> ListSelecoes;
    private List<Jogadores> ListaJogadores;
    private List<EstatisticaTime> EstatisticasCASA;
    private List<EstatisticaTime> EstatisticasVISITANTE;
    private List<Escalacao> todasAsEscalacao;
    private List<Estadio> ListEstadio;
    private Map<String, Queue<Point2D>> vagas = new HashMap<>(); //Usado para a formação
    //ImageView
    @FXML
    private ImageView logoC;
    @FXML
    private ImageView logoV;
    public void setPartida(Partida partida){
        this.partida=partida;
        ListEstadio=CarregaArquivoService.carregaArquivo("/database/Estadios.txt", parte->new EstadioBuilder().nome(parte[0]).build());
        ListSelecoes = CarregaArquivoService.carregaArquivo("/database/SelecoesNaCopa.txt", parte->new SelecaoBuilder().nome(parte[0]).build());
        List<Partida> partidas = CarregaArquivoService.carregaArquivo("/database/partida_grupo.txt", parte->new PartidaGrupoBuilder().id(Integer.parseInt(parte[0])).data(LocalDate.parse(parte[2])).horario(parte[3]).Casa(CadastroPartidaService.buscaPeloNome(ListSelecoes, Selecoes::getNome,parte[7])).Visitante(CadastroPartidaService.buscaPeloNome(ListSelecoes,Selecoes::getNome,parte[8])).fase(Fase.valueOf(parte[9])).status(StatusPartida.valueOf(parte[10])).build());
        partidas.addAll(CarregaArquivoService.carregaArquivo("/database/partida_eliminatoria.txt", parte->new PartidaGrupoBuilder().id(Integer.parseInt(parte[0])).data(LocalDate.parse(parte[2])).horario(parte[3]).Casa(CadastroPartidaService.buscaPeloNome(ListSelecoes,Selecoes::getNome,parte[6])).Visitante(CadastroPartidaService.buscaPeloNome(ListSelecoes,Selecoes::getNome,parte[7])).fase(Fase.valueOf(parte[8])).status(StatusPartida.valueOf(parte[9])).build()));
        EstatisticasCASA = CarregaArquivoService.carregaArquivo("/database/estatisticas_partida.txt",parte->new EstatisticaTimeBuilder().id(Integer.parseInt(parte[0])).gols(Integer.parseInt(parte[1])).chutes(Integer.parseInt(parte[3])).chutesAGol(Integer.parseInt(parte[5])).posseDeBola(Integer.parseInt(parte[7])).passes(Integer.parseInt(parte[9])).precisaoDosPasses(Integer.parseInt(parte[11])).faltas(Integer.parseInt(parte[13])).cartoesAmarelos(Integer.parseInt(parte[15])).cartoesVermelhos(Integer.parseInt(parte[17])).impedimentos(Integer.parseInt(parte[19])).escanteios(Integer.parseInt(parte[21])).build());
        EstatisticasVISITANTE = CarregaArquivoService.carregaArquivo("/database/estatisticas_partida.txt",parte->new EstatisticaTimeBuilder().id(Integer.parseInt(parte[0])).gols(Integer.parseInt(parte[2])).chutes(Integer.parseInt(parte[4])).chutesAGol(Integer.parseInt(parte[6])).posseDeBola(Integer.parseInt(parte[8])).passes(Integer.parseInt(parte[10])).precisaoDosPasses(Integer.parseInt(parte[12])).faltas(Integer.parseInt(parte[14])).cartoesAmarelos(Integer.parseInt(parte[16])).cartoesVermelhos(Integer.parseInt(parte[18])).impedimentos(Integer.parseInt(parte[20])).escanteios(Integer.parseInt(parte[22])).build());
        ListaJogadores=CarregaArquivoService.carregaArquivo("/database/Jogadores.txt", parte-> new JogadorBuilder().nome(parte[0]).selecao(CadastroPartidaService.buscaPeloNome(ListSelecoes,Selecoes::getNome,parte[2])).numero(Integer.parseInt(parte[6])).posicao(parte[5]).build());
        eventos = CarregaArquivoService.carregaArquivo("/database/eventos.txt", parte -> new EventosOcorridos(Integer.parseInt(parte[0]),Integer.parseInt(parte[3]),TipoEvento.valueOf(parte[2]),CadastroPartidaService.buscaPeloNome(ListaJogadores,Jogadores::getNome,parte[5]),parte[4]));
        EstatisticaPartida estatistica = EstatisticaService.buscarPorPartida(EstatisticasCASA,EstatisticasVISITANTE,partida.getId());
        Map<String, Double> notas = EstatisticaService.carregarNotasPartida(partida.getId());
        todasAsEscalacao = CarregaArquivoService.carregaArquivo("/database/escalacao.txt", parte->{
            //Lista de jogadores
            ArrayList<JogadorPartida> titulares = new ArrayList<>();
            ArrayList<JogadorPartida> reservas = new ArrayList<>();
            //Como a escalação começa a partir do parte[] temos:
            for(int i=3;i<parte.length;i++){
                //Nome| T OU R
                String[] infoJogador = parte[i].split("\\|");
                String nome = infoJogador[0];
                String tipo = infoJogador[1];
                Jogadores j = CadastroPartidaService.buscaPeloNome(ListaJogadores,Jogadores::getNome,nome);
                if(j==null) continue;
                JogadorPartida jp = new JogadorPartida(j, notas.get(j.getNome()));
                if(tipo.equalsIgnoreCase("T")){
                    titulares.add(jp);
                }
                else{
                    reservas.add(jp);
                }
            }
            return new Escalacao(Integer.parseInt(parte[0]),parte[1],parte[2],titulares,reservas);
        });
        carregarCabecalho(estatistica.getEstatisticaCasa().getGols(), estatistica.getEstatisticaVisitante().getGols());
        carregarEstatisticas(estatistica);
        Escalacao casa = todasAsEscalacao.stream()
                .filter(e -> e.getId() == partida.getId())
                .filter(e -> e.getCasaOuVisitante().equals("CASA"))
                .findFirst()
                .orElse(null);
        Escalacao visitante = todasAsEscalacao.stream()
                .filter(e -> e.getId() == partida.getId())
                .filter(e -> e.getCasaOuVisitante().equals("VISITANTE"))
                .findFirst()
                .orElse(null);
        if(casa != null){
            carregarEscalacao(escalacaoC, casa);
            formC.setText(casa.getFormacao());
        }
        if(visitante != null){
            carregarEscalacao(escalacaoV, visitante);
            formV.setText(visitante.getFormacao());
        }
        carregarEventos();
    }
    //Carraga as informações do cabeçalho da partida
    private void carregarCabecalho(int golsCasa,int golsVisi){
        Casa.setText(partida.getSelecaoCasa().toString());
        Visitante.setText(partida.getSelecaoVisitante().toString());
        data.setText(partida.getData().toString());
        estadio.setText(partida.getEstadio().toString());
        fase.setText(partida.getFase().toString());
        golsC.setText(String.valueOf(golsCasa));
        golsV.setText(String.valueOf(golsVisi));
        //Seta o logo da seleção CASA
        File is = new File("target/classes/images/Logos/" + partida.getSelecaoCasa().getNome().toLowerCase().replace(" ", "_") + ".png");
        if(is.exists()){
            System.out.println("Achei");
            Image imagem=new Image(is.toURI().toString());
            logoC.setImage(imagem);
        }
        else {
            System.out.println("Nao achei");
            Image imagemPadrao = new Image(getClass().getResourceAsStream("/images/Logos/brasil.png"));
            logoC= new javafx.scene.image.ImageView(imagemPadrao);
        }
        //Seta o logo da seleção VISITANTE
        is = new File("target/classes/images/Logos/" + partida.getSelecaoVisitante().getNome().toLowerCase().replace(" ", "_") + ".png");
        if(is.exists()){
            System.out.println("Achei");
            Image imagem=new Image(is.toURI().toString());
            logoV.setImage(imagem);
        }
        else {
            System.out.println("Nao achei");
            Image imagemPadrao = new Image(getClass().getResourceAsStream("/images/Logos/brasil.png"));
            logoV= new javafx.scene.image.ImageView(imagemPadrao);
        }
    }
    //ESCALAÇÃO
    private void carregarEscalacao(AnchorPane campo, Escalacao escalacao){
        campo.getChildren().clear();
        if(escalacao.getFormacao().equalsIgnoreCase("4-2-3-1")){
            formacao4231();
        }
        else if(escalacao.getFormacao().equalsIgnoreCase("5-2-3")){
            formacao523();
        }
        else{
            formacao424();
        }
        for(JogadorPartida jp : escalacao.getTitulares()){
            Queue<Point2D> fila = vagas.get(jp.getJogador().getPosicao());
            if(fila == null || fila.isEmpty()){
                continue;
            }
            Point2D ponto = fila.poll();
            adicionandoNoCampoPartida(jp, campo, ponto.getX(), ponto.getY());
        }
    }
    //CRIANDO AS FORMAÇÕES QUE APARECEM
    private void formacao4231(){
        vagas.clear();
        vagas.put("GOL",new LinkedList<>(List.of(new Point2D(140,355))));
        vagas.put("ZAG",new LinkedList<>(List.of(new Point2D(80,300),new Point2D(170,300))));
        vagas.put("LE",new LinkedList<>(List.of(new Point2D(0,230))));
        vagas.put("LD",new LinkedList<>(List.of(new Point2D(255,230))));
        vagas.put("MC",new LinkedList<>(List.of(new Point2D(65,170),new Point2D(180,170),new Point2D(125,100))));
        vagas.put("PE",new LinkedList<>(List.of(new Point2D(0,100))));
        vagas.put("PD",new LinkedList<>(List.of(new Point2D(245,100))));
        vagas.put("ATA",new LinkedList<>(List.of(new Point2D(125,0))));
    }
    private void formacao424(){
        vagas.clear();
        vagas.put("GOL",new LinkedList<>(List.of(new Point2D(125,355))));
        vagas.put("ZAG",new LinkedList<>(List.of(new Point2D(50,280),new Point2D(200,280))));
        vagas.put("LE",new LinkedList<>(List.of(new Point2D(0,230))));
        vagas.put("LD",new LinkedList<>(List.of(new Point2D(240,230))));
        vagas.put("MC",new LinkedList<>(List.of(new Point2D(65,170),new Point2D(180,170))));
        vagas.put("PE",new LinkedList<>(List.of(new Point2D(0,100))));
        vagas.put("PD",new LinkedList<>(List.of(new Point2D(240,100))));
        vagas.put("ATA",new LinkedList<>(List.of(new Point2D(95,0),new Point2D(220,0))));
    }
    private void formacao523(){
        vagas.clear();
        vagas.put("GOL",new LinkedList<>(List.of(new Point2D(125,355))));
        vagas.put("ZAG",new LinkedList<>(List.of(new Point2D(50,280),new Point2D(200,280),new Point2D(120,280))));
        vagas.put("LE",new LinkedList<>(List.of(new Point2D(0,230))));
        vagas.put("LD",new LinkedList<>(List.of(new Point2D(240,230))));
        vagas.put("MC",new LinkedList<>(List.of(new Point2D(65,170),new Point2D(180,170))));
        vagas.put("PE",new LinkedList<>(List.of(new Point2D(0,100))));
        vagas.put("PD",new LinkedList<>(List.of(new Point2D(240,100))));
        vagas.put("ATA",new LinkedList<>(List.of(new Point2D(125,0))));
    }
    //ESTATISTICAS
    //Coloca o numero de cada estatistica nas labeis
    private void atualizaEstatistica(Label lblCasa, Label lblVisitante, ProgressBar barra, double valorCasa, double valorVisitante){
        lblCasa.setText(String.valueOf((int)valorCasa));
        lblVisitante.setText(String.valueOf((int)valorVisitante));
        barra.setProgress(calculaProporcao(valorCasa, valorVisitante));
    }
    //Calcula a proporção para saber o quanto a progress bar precisa ser enchida
    private double calculaProporcao(double casa, double visitante){
        if(casa + visitante == 0){
            return 0.5;
        }
        return casa / (casa + visitante);
    }
    private void carregarEstatisticas(EstatisticaPartida stats){
        // Gols
        atualizaEstatistica(lblGolsCasa, lblGolsVisitante, pbGols, stats.getEstatisticaCasa().getGols(), stats.getEstatisticaVisitante().getGols());

        // Finalizações
        atualizaEstatistica(lblFinalizacoesCasa, lblFinalizacoesVisitante, pbFinalizacoes, stats.getEstatisticaCasa().getChutes(), stats.getEstatisticaVisitante().getChutes());

        // Finalizações no alvo
        atualizaEstatistica(lblFinalizacoesAlvoCasa, lblFinalizacoesAlvoVisitante, pbFinalizacoesAlvo, stats.getEstatisticaCasa().getChutesAGol(), stats.getEstatisticaVisitante().getChutesAGol());

        // Posse de bola
        lblPosseCasa.setText(stats.getEstatisticaCasa().getPosseDeBola() + "%");
        lblPosseVisitante.setText(stats.getEstatisticaVisitante().getPosseDeBola() + "%");
        pbPosse.setProgress(stats.getEstatisticaCasa().getPosseDeBola() / 100.0);

        // Passes
        atualizaEstatistica(lblPassesCasa, lblPassesVisitante, pbPasses, stats.getEstatisticaCasa().getPasses(), stats.getEstatisticaVisitante().getPasses());

        // Precisão dos passes
        lblPrecisaoCasa.setText(stats.getEstatisticaCasa().getPrecisaoDosPasses() + "%");
        lblPrecisaoVisitante.setText(stats.getEstatisticaVisitante().getPrecisaoDosPasses() + "%");
        pbPrecisao.setProgress(calculaProporcao(stats.getEstatisticaCasa().getPrecisaoDosPasses(), stats.getEstatisticaVisitante().getPrecisaoDosPasses()));

        // Faltas
        atualizaEstatistica(lblFaltasCasa, lblFaltasVisitante, pbFaltas, stats.getEstatisticaCasa().getFaltas(), stats.getEstatisticaVisitante().getFaltas());

        // Cartões amarelos
        atualizaEstatistica(lblAmarelosCasa, lblAmarelosVisitante, pbAmarelos, stats.getEstatisticaCasa().getCartoesAmarelos(), stats.getEstatisticaVisitante().getCartoesAmarelos());

        // Cartões vermelhos
        atualizaEstatistica(lblVermelhosCasa, lblVermelhosVisitante, pbVermelhos, stats.getEstatisticaCasa().getCartoesVermelhos(), stats.getEstatisticaVisitante().getCartoesVermelhos());

        // Impedimentos
        atualizaEstatistica(lblImpedimentosCasa, lblImpedimentosVisitante, pbImpedimentos, stats.getEstatisticaCasa().getImpedimentos(), stats.getEstatisticaVisitante().getImpedimentos());

        // Escanteios
        atualizaEstatistica(lblEscanteiosCasa, lblEscanteiosVisitante, pbEscanteios, stats.getEstatisticaCasa().getEscanteios(), stats.getEstatisticaVisitante().getEscanteios());
    }
    //EVENTOS
    private HBox criarLinhaEvento(EventosOcorridos evento){
        ImageView icone = getIconeEvento(evento.getTipo().toString());
        Label texto = new Label(evento.getMinuto() + "' - ");
        texto.setStyle(""" 
                -fx-font-size: 14; 
                -fx-text-fill: #aa7e2a;
                -fx-font-family: 'Roboto Condensed Black';""");
        VBox jogador=new VBox(0.5);
        Label nome=new Label(evento.getJogador().getNome());
        nome.setStyle(""" 
                -fx-font-size: 12; 
                -fx-text-fill: black;
                -fx-font-family: 'Roboto Condensed';""");
        Label selecao= new Label(evento.getSelecao());
        selecao.setStyle(""" 
                -fx-font-size: 9; 
                -fx-text-fill: #c9c9c9;
                -fx-font-family: 'Inter 18pt';""");
        jogador.getChildren().addAll(nome,selecao);
        HBox linha = new HBox(10);
        linha.setAlignment(Pos.CENTER_LEFT);
        linha.getChildren().addAll(icone, texto, jogador);
        return linha;
    }
    private void carregarEventos() {
        boxEventos.getChildren().clear();
        boxEventos.setSpacing(10);
        for(EventosOcorridos e : eventos){
            HBox linha = criarLinhaEvento(e);
            boxEventos.getChildren().add(linha);
        }
    }
    private ImageView getIconeEvento(String tipo){
        String caminho;
        switch (tipo){
            case "GOL":
                caminho = "/images/goal.png";
                break;
            case "AMARELO":
                caminho = "/images/yellow-card.png";
                break;
            case "VERMELHO":
                caminho = "/images/cards.png";
                break;
            case "SUBSTITUICAO":
                caminho = "/images/refresh.png";
                break;
            case "ASSISTENCIA":
                caminho = "/images/football.png";
                break;
            case "PENALTI":
                caminho = "/images/penalty-kick.png";
                break;
            case "GOL_CONTRA":
                caminho = "/images/soccer-ball.png";
                break;
            default:
                caminho = "/images/goal.png";
        }
        ImageView imagem = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(caminho))));
        imagem.setFitWidth(18);
        imagem.setFitHeight(18);
        return imagem;
    }
    @FXML
    private void fecharPopup() {
        Stage stage = (Stage) volta.getScene().getWindow();
        stage.close();
    }
}
