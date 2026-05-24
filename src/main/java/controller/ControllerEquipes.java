package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nationsAndPlayers.nations.Selecoes;
import services.matches.CarregaArquivoService;
import users.Sessao;
import users.Usuario;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class ControllerEquipes {
    @FXML private Button botaoLogin;
    @FXML private MenuButton menuUsuario;

    private List<Selecoes> ListSelecoes = new ArrayList<>();
    @FXML
    private VBox listaSelecoes;

    @FXML
    private TextField pesquisa;

    @FXML
    public void initialize() {
        //Lê as seleções que estão cadastradas(dentro do arquivo) usando o serviço CarregaArquivoService
        ListSelecoes = CarregaArquivoService.carregaArquivo("/database/SelecoesNaCopa.txt",parte->new Selecoes(parte[0],parte[1],parte[2],parte[3],parte[4]));
        //Mostra as seleções na tela
        mostraSelecao();
        //Toda vez que for escrito alguma coisa no textField ele chama a função de pesquisa
        pesquisa.textProperty().addListener((obs, antigo, novo) -> {pesquisarSelecao();});
        Usuario u = Sessao.getInstancia().getUsuarioLogado();
        if (u != null) {
            menuUsuario.setText(u.getNome() );
            menuUsuario.setVisible(true);
            botaoLogin.setVisible(false);
        } else {
            botaoLogin.setVisible(true);
            menuUsuario.setVisible(false);
        }
    }

    @FXML
    private void irPaginaInicial(MouseEvent e) {
        SceneController.mudaDeTela("/designAndScreens/telaInicial/paginaInicial.fxml");
    } //Passa do Menu para a página que conta a historia da copa

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

    @FXML //Gustavo ta fazendo, depois adiciona o trocaTela + fxml
    private void irParaEstadios(MouseEvent e) {
        SceneController.mudaDeTela("/designAndScreens/telaEstadios/telaEstadioAdm.fxml");
    }

    @FXML //Helena ta fazendo, depois adiciona o trocaTela + fxml
    private void irParaLogin(ActionEvent e) {
        SceneController.mudaDeTela("/designAndScreens/login/login.fxml");
    }



    @FXML
    private void onLogout(ActionEvent e){
        Sessao.getInstancia().logout();
        SceneController.mudaDeTela( "/designAndScreens/telaInicial/paginaInicial.fxml");
    }

    private HBox criarLinha(Selecoes selecao) {
        HBox linha = new HBox();
        linha.setAlignment(Pos.CENTER_LEFT);
        linha.setSpacing(40);
        linha.setPrefHeight(80);
        linha.setStyle(""" 
                -fx-background-color: white; 
                -fx-border-color: #EEEEEE; 
                -fx-padding: 10 20 10 20;
                """);
        //Tem que ver como faz o upload de imagem, mas deixa para depois
        String caminhoImagem = "/images/" + selecao.getNome().toLowerCase().replace(" ", "_") + ".png";
        InputStream is = getClass().getResourceAsStream(caminhoImagem);
        ImageView logoSelecao;
        if(is != null){
            Image imagem=new Image(is);
            logoSelecao = new ImageView(imagem);
        }
        else {
            Image imagemPadrao = new Image(getClass().getResourceAsStream("/images/brasil.png"));
            logoSelecao = new ImageView(imagemPadrao);
        }
        logoSelecao.setPreserveRatio(true);
        logoSelecao.setFitWidth(55);
        logoSelecao.setFitHeight(55);
        //Pegando os dados da seleção
        // Nome
        Label nome = new Label(selecao.getNome());
        nome.setStyle(""" 
                -fx-font-size: 20; 
                -fx-text-fill: black;
                -fx-font-family: 'Inter 18pt Medium';""");
        nome.setPrefWidth(252);
        //Grupo
        Label grupo = new Label("Grupo " + selecao.getGrupo());
        grupo.setStyle(""" 
                -fx-font-size: 13; 
                -fx-text-fill: black;
                -fx-font-family: 'Inter 18pt Medium';""");
        grupo.setPrefWidth(90);
        //Ranking
        Label ranking = new Label(selecao.getRanking());
        ranking.setStyle(""" 
                -fx-font-size: 11; 
                -fx-text-fill: black;
                -fx-font-family: 'Inter 18pt Medium';""");
        ranking.setPrefWidth(70);
        //Participações
        Label participacao = new Label(selecao.getParticipacao());
        participacao.setStyle(""" 
                -fx-font-size: 11; 
                -fx-text-fill: black;
                -fx-font-family: 'Inter 18pt Medium';""");
        participacao.setPrefWidth(95);
        //Títulos
        Label titulos = new Label(selecao.getTitulo());
        titulos.setStyle(""" 
                -fx-font-size: 11; 
                -fx-text-fill: black;
                -fx-font-family: 'Inter 18pt Medium';""");
        titulos.setPrefWidth(50);
        //Colocando todas as labeis na linha
        linha.getChildren().addAll(logoSelecao, nome, grupo, ranking, participacao, titulos);
        return linha;
    }

    private void mostraSelecao() {
        for (Selecoes s : ListSelecoes) {
            HBox linha = criarLinha(s);
            listaSelecoes.getChildren().add(linha);
        }
    }
    private void pesquisarSelecao(){
        String pais = pesquisa.getText().trim().toLowerCase();
        //Vou pegar a Vbox e destrinchar ela, ou seja, pegar cada Hbox criado
        for(Node node : listaSelecoes.getChildren()){
            HBox linha=(HBox)node;
            //Destrincha os elementos presentes na HBOX(labeis) e pega a primeira referente ao nome da seleção
            Label nome=(Label) linha.getChildren().get(1);
            //Verifica se o que foi no textfield está presente em algum nome de seleção, se sim mostra e se não oculta a HBOX
            //StartWith verifica se o texto digitado está na string, considerando que a ordem importa
            if (!nome.getText().toLowerCase().startsWith(pais)){
                linha.setManaged(false);
                linha.setVisible(false);
            }
            else{
                linha.setManaged(true);
                linha.setVisible(true);
            }
        }
    }
}
