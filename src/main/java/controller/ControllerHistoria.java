package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

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
        SceneController.mudaDeTela( "/designAndScreens/telaEstadios/tela1.0.fxml");
    }
    @FXML
    //Passa da História para a tela de login
    private void irParaLogin(ActionEvent e) {SceneController.mudaDeTela( "/designAndScreens/login/login.fxml");
    }
    @FXML
    //Passa da História para a tela de campeoes da copa
    private void irParaCampeoes(ActionEvent e) {SceneController.mudaDeTela( "/designAndScreens/telaInicial/campeoes.fxml");
    }
}

