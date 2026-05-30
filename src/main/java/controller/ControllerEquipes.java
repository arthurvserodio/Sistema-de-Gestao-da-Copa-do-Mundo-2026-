package controller;

import Enums.Funcao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nationsAndPlayers.nations.Selecoes;
import services.files.SelecoesFile;
import users.Sessao;
import users.Usuario;

import java.io.*;


public class ControllerEquipes {
    @FXML private Button botaoLogin;
    @FXML private MenuButton menuUsuario;
    @FXML private Button botaoCadastrarSelecao;

    @FXML
    private VBox listaSelecoes;

    @FXML
    private TextField pesquisa;

    @FXML private Text botaoUsuario;

    @FXML private Text botaoArbitro;


    @FXML
    public void initialize() {
        SelecoesFile.getInstance().getListaSelecoes();
        //Mostra as seleções na tela
        mostraSelecao();
        //Toda vez que for escrito alguma coisa no textField ele chama a função de pesquisa
        pesquisa.textProperty().addListener((obs, antigo, novo) -> {pesquisarSelecao();});
        Usuario u = Sessao.getInstancia().getUsuarioLogado();
        if (u != null) {
            menuUsuario.setText(u.getNome() );
            menuUsuario.setVisible(true);
            botaoLogin.setVisible(false);
            if(u.getFuncao() == Funcao.ADMINISTRADOR){
                botaoUsuario.setVisible(true);
                botaoArbitro.setVisible(true);
                botaoCadastrarSelecao.setVisible(true);
            }
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
    private void irParaUsuarios(MouseEvent e){
        SceneController.mudaDeTela( "/designAndScreens/telasAdministrador/usuarios.fxml");
    }
    @FXML
    private void irParaArbitros(MouseEvent e){
        SceneController.mudaDeTela( "/designAndScreens/Arbitragem/telaArbitroAdm.fxml");
    }
    @FXML
    private void irParaCadastroDeSelecoes(MouseEvent e){

        try{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/designAndScreens/telasAdministrador/telaCadastrarSelecao.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.showAndWait();
        }catch(IOException ex){
            System.err.println("Falha ao abrir o telaCadastrarSelecao: " + ex.getMessage());
        }
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
        /*refatoracao para upload de imagens*/
        File is = new File("target/classes/images/Logos/" + selecao.getNome().toLowerCase().replace(" ", "_") + ".png");
        System.out.println(is.getAbsolutePath());

        ImageView logoSelecao;
        if(is.exists()){
            System.out.println("Achei");
            Image imagem=new Image(is.toURI().toString());
            logoSelecao = new ImageView(imagem);
            logoSelecao.setOnMouseClicked(event ->{
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/designAndScreens/telaJogadores/jogadores.fxml"));
                    Parent tela = loader.load();
                    ControllerJogadores controller = loader.getController();
                    controller.setSelecoes(selecao);
                    ImageView source = (ImageView) event.getSource();
                    Stage stage = (Stage) source.getScene().getWindow();
                    stage.setScene(new Scene(tela));
                } catch(IOException e){
                    e.printStackTrace();
                }
            });
        }
        else {
            System.out.println("Nao achei");
            Image imagemPadrao = new Image(getClass().getResourceAsStream("/images/Logos/brasil.png"));
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
        listaSelecoes.getChildren().clear(); //limpa a tela para nao sobrepor visuais que serão atualizados
        for (Selecoes s : SelecoesFile.getInstance().getListaSelecoes()) { //alteracao para usar a lista final do SelecoesFile
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
