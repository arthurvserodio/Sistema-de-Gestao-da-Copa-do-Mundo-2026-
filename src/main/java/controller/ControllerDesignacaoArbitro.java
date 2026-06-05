package controller;

import Enums.Funcao;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import matches.Partida;
import stadiumAndRefeering.DesignacaoArbitragem;
import stadiumAndRefeering.Estadio;
import users.Arbitro;
import users.Sessao;
import users.Usuario;

import javax.swing.text.TabableView;
import javax.swing.text.html.ImageView;

public class ControllerDesignacaoArbitro {

    @FXML TableView<DesignacaoArbitragem> TabelaDesignacao;
    @FXML private Label labelMensagem;
    @FXML private TableColumn<DesignacaoArbitragem, Partida> colPartida;
    @FXML private TableColumn<DesignacaoArbitragem, Arbitro> colArbitros;
    @FXML private TextField txtBusca;
    @FXML private MenuButton menuUsuario;
    @FXML private Text botaoUsuario;
    @FXML private Button botaoLogin;




    public void initialize(){
        Usuario u = Sessao.getInstancia().getUsuarioLogado();
        if (u != null) {
            menuUsuario.setText(u.getNome());
            menuUsuario.setVisible(true);
            botaoLogin.setVisible(false);
            if(u.getFuncao()==Funcao.ADMINISTRADOR){
                botaoUsuario.setVisible(true);
            }
        } else {
            botaoLogin.setVisible(true);
            menuUsuario.setVisible(false);
        }

    }



    // Mensagens de erro e sucesso :
    private void mostrarErro(String str) {
        labelMensagem.setText(str);
        labelMensagem.setStyle("-fx-text-fill: #d32f2f; -fx-font-size: 14px; -fx-font-weight: bold;");
        labelMensagem.setOpacity(1.0);

        tornarMensagemTemporaria();
    }

    private void mostrarSucesso(String str) {
        labelMensagem.setText(str);
        labelMensagem.setStyle("-fx-text-fill: #388e3c; -fx-font-size: 14px; -fx-font-weight: bold;");
        labelMensagem.setOpacity(1.0);

        tornarMensagemTemporaria();
    }

    /// Funçao para mensagem aparecer e sumir da tela
    private void tornarMensagemTemporaria() {

        FadeTransition fade = new FadeTransition(Duration.seconds(1.0), labelMensagem);
        fade.setFromValue(1.0); // Totalmente visível
        fade.setToValue(0.0);   // Totalmente invisível


        fade.setDelay(Duration.seconds(2.0));


        fade.setOnFinished(event -> labelMensagem.setText(""));

        fade.play(); // Inicia o temporizador/efeito
    }







    @FXML
    //Passa para tela de login
    private void irParaLogin(ActionEvent e) {
        SceneController.mudaDeTela( "/designAndScreens/login/login.fxml");
    }





    @FXML
    //Passa do Menu para a tela de grupos da copa 2026
    private void irParaClassificacao(MouseEvent e) {
        SceneController.mudaDeTela( "/designAndScreens/telaInicial/classificacao.fxml");
    }


    @FXML
    //Muda para tela de inicio
    private void irParaInicio(MouseEvent e) {
        SceneController.mudaDeTela( "/designAndScreens/telaInicial/paginaInicial.fxml");
    }
    @FXML
    //Passa do Menu para a tela de equipes presentes na copa de 2026
    private void irParaEquipes(MouseEvent e) {
        SceneController.mudaDeTela( "/designAndScreens/telaInicial/equipesNaCopa.fxml");
    }


    @FXML
    //Passa para tela de história
    private void irParaHistoria(MouseEvent e){
        SceneController.mudaDeTela("/designAndScreens/telaInicial/historia.fxml");
    }

    @FXML
    private void onLogout(ActionEvent e){
        Sessao.getInstancia().logout();
        SceneController.mudaDeTela( "/designAndScreens/telaInicial/paginaInicial.fxml");
    }


    @FXML
    private void irParaUsuarios(MouseEvent e){
        SceneController.mudaDeTela( "/designAndScreens/telasAdministrador/usuarios.fxml");
    }
    @FXML
    private void irParaArbitros(MouseEvent e){
        Usuario u = Sessao.getInstancia().getUsuarioLogado();
        if(u.getFuncao() != Funcao.ARBITRO){
            SceneController.mudaDeTela( "/designAndScreens/Arbitragem/telaArbitroAdm.fxml");
        }
        else{
            SceneController.mudaDeTela( "/designAndScreens/Arbitragem/telaArbitroNormal.fxml");
        }
    }


}
