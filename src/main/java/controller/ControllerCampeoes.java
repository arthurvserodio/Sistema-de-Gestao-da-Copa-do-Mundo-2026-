package controller;

import builder.CampeoesBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import nationsAndPlayers.nations.Campeoes;
import nationsAndPlayers.nations.Selecoes;
import services.matches.CarregaArquivoService;
import users.Sessao;
import users.Usuario;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ControllerCampeoes {

    @FXML private Button botaoLogin;
    @FXML private MenuButton menuUsuario;


    //A lista erá usada para armazenar os campeoes lidos do arquivo
    private List<Campeoes> ListCampeoes = new ArrayList<>();
    @FXML
    private FlowPane cardCampeoes;
    //Inicializa a tela
    @FXML
    public void initialize() {
        //Lê os campeões que estão cadastrados(dentro do arquivo)
        ListCampeoes= CarregaArquivoService.carregaArquivo("/database/Campeoes.txt",partes->new CampeoesBuilder().selecao(partes[0]).ano(partes[1]).local(partes[2]).build());
        //Mostra o card do campeao
        mostraCampeoes();
        cardCampeoes.setHgap(50);   // espaço horizontal entre cards
        cardCampeoes.setVgap(50);   // espaço vertical entre linhas

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
    //Volta para a pagina inicial de menu clicando no logo da copa
    @FXML
    private void irPaginaInicial(MouseEvent e){
        SceneController.mudaDeTela("/designAndScreens/telaInicial/paginaInicial.fxml");
    }
    //Passa da História para a própria página
    @FXML
    private void irParaHistoria(MouseEvent e){
        SceneController.mudaDeTela("/designAndScreens/telaInicial/historia.fxml");
    }
    @FXML
    //Passa da História para a tela de equipes presentes na copa de 2026
    private void irParaEquipes(MouseEvent e) {
        SceneController.mudaDeTela("/designAndScreens/telaInicial/equipesNaCopa.fxml");
    }
    @FXML
    //Passa da História para a tela de grupos da copa
    private void irParaClassificacao(MouseEvent e) {
        SceneController.mudaDeTela( "/designAndScreens/telaInicial/classificacao.fxml");
    }
    @FXML
    //Passa da História para a tela de estádios presentes na copa
    private void irParaEstadios(MouseEvent e) {
        SceneController.mudaDeTela( "/designAndScreens/telaEstadios/tela1.0.fxml");
    }
    @FXML
    //Passa da História para a tela de login
    private void irParaLogin(ActionEvent e) {
        SceneController.mudaDeTela("/designAndScreens/login/login.fxml");
    }

    @FXML
    private void onLogout(ActionEvent e){
        Sessao.getInstancia().logout();
        SceneController.mudaDeTela( "/designAndScreens/telaInicial/paginaInicial.fxml");
    }

    private AnchorPane criaCard(Campeoes campeao){
        //Cria o card no formato de anchorPanel
        AnchorPane card = new AnchorPane();
        card.getStyleClass().add("card-campeao"); //Pega a configuração do card feita no css
        card.setPrefSize(250, 250); // Para o AnchorPane respeitar o tamanho
        card.setMaxSize(250, 250); // Impede que expanda além
        card.setMinSize(250, 250);  // Impede que encolha
        //TOPO= Linha 1 + Ano + Linha 2
        HBox topo = new HBox();
        topo.setAlignment(Pos.CENTER); //Posiciona a Hbox no centro do card
        topo.setPrefWidth(250);//Ocupa toda a largura do card
        topo.setSpacing(15);
        topo.setLayoutX(0);
        topo.setLayoutY(20);
        //Cria as linhas
        Line linha1 = new Line(-100,0,-64,6.103515625E-5);
        linha1.getStyleClass().add("linha-dourada"); //Pega a conf do css

        Line linha2 = new Line(-100,0,-64,6.103515625E-5);
        linha2.getStyleClass().add("linha-dourada"); //Pega a conf do css
        Label ano = new Label(campeao.getAno()); //Pega o Ano em que foi campeao
        ano.getStyleClass().add("text-ano"); //Pega a conf do css
        topo.getChildren().addAll(linha1,ano,linha2); //Coloca tudo na Hbox
        //LOGO DA SELEÇÃO
        //Depois a gente vê
        String caminhoImagem = "/images/" + campeao.getSelecao().toLowerCase().replace(" ", "_") + ".png";
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
        logoSelecao.setFitWidth(83);
        logoSelecao.setFitHeight(112);
        logoSelecao.setLayoutX(86);
        logoSelecao.setLayoutY(69);
        logoSelecao.getStyleClass().add("escudo"); //Pega a conf do css
        //NOME DO CAMPEÃO
        Label nome = new Label(campeao.getSelecao());
        nome.getStyleClass().add("text-selecao"); //Pega a conf do css
        nome.setPrefWidth(250);
        nome.setAlignment(Pos.CENTER);
        nome.setLayoutX(0);
        nome.setLayoutY(188);
        //CIDADE DA FINAL
        Label cidade = new Label(campeao.getLocal());
        cidade.getStyleClass().add("text-cidade"); //Pega a conf do css
        cidade.setPrefWidth(250);
        cidade.setAlignment(Pos.CENTER);
        cidade.setLayoutX(0);
        cidade.setLayoutY(220);
        card.getChildren().addAll(topo, logoSelecao, nome, cidade); //Adiciona tudo no card
        return card;
    }
    //Cria os cards pegando os campeões da lista
    private void mostraCampeoes() {
        for (Campeoes s : ListCampeoes) {
            AnchorPane card = criaCard(s);
            cardCampeoes.getChildren().add(card);
        }
    }
}
