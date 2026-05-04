package controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControllerTelasEstadios {
    @FXML
    private void irParaTela1(ActionEvent e) {
        SceneController.mudaDeTela( "/designAndScreens/telaEstadios/tela1.fxml");
    }

    @FXML
    private void irParaTela2(ActionEvent e) {
        SceneController.mudaDeTela( "/designAndScreens/telaEstadios/tela2.fxml");
    }

    @FXML
    private void irParaTela3(ActionEvent e) {
        SceneController.mudaDeTela( "/designAndScreens/telaEstadios/tela3.fxml");
    }

    @FXML
    private void irParaTela4(ActionEvent e) {
        SceneController.mudaDeTela( "/designAndScreens/telaEstadios/tela4.fxml");
    }

    @FXML
    //Passa do Menu para a tela inicial ao clicar no simbolo da copa
    private void irParaInicio(MouseEvent e) {
        SceneController.mudaDeTela( "/designAndScreens/telaInicial/paginaInicial.fxml");
    }
}

