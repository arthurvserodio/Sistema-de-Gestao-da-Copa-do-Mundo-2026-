package controller;

import Enums.Funcao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import users.Sessao;
import users.Usuario;

public class ControllerHistoria {
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
        SceneController.mudaDeTela( "/designAndScreens/telaEstadios/telaEstadioAdm.fxml");
    }
    @FXML
    //Passa da História para a tela de login
    private void irParaLogin(ActionEvent e) {SceneController.mudaDeTela( "/designAndScreens/login/login.fxml");
    }
    @FXML
    //Passa da História para a tela de campeoes da copa
    private void irParaCampeoes(ActionEvent e) {SceneController.mudaDeTela( "/designAndScreens/telaInicial/campeoes.fxml");
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
    private void onLogout(ActionEvent e){
        Sessao.getInstancia().logout();
        SceneController.mudaDeTela( "/designAndScreens/telaInicial/paginaInicial.fxml");
    }

    @FXML private Button botaoLogin;
    @FXML private MenuButton menuUsuario;
    @FXML private Text botaoUsuario;
    @FXML private Text botaoArbitro;

    public void initialize() {
        Usuario u = Sessao.getInstancia().getUsuarioLogado();
        if (u != null) {
            menuUsuario.setText(u.getNome() );
            menuUsuario.setVisible(true);
            botaoLogin.setVisible(false);
            if(u.getFuncao() == Funcao.ADMINISTRADOR){
                botaoUsuario.setVisible(true);
                botaoArbitro.setVisible(true);
            }
        } else {
            botaoLogin.setVisible(true);
            menuUsuario.setVisible(false);

        }
    }


}

